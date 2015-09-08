package com.carinsurance.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.activity.Brand_RecommendationActivity;
import com.carinsurance.activity.CarServiceActivity;
import com.carinsurance.activity.ChoiceCarActivity;
import com.carinsurance.activity.ClassificationOfGoodsActivity;
import com.carinsurance.activity.HotCategoryListActivity;
import com.carinsurance.activity.LoginActivity;
import com.carinsurance.activity.SelectServer1Activity;
import com.carinsurance.activity.ServiceOrderActivity;
import com.carinsurance.activity.TianTianTejiaActivity;
import com.carinsurance.adapter.HomePage0Adapter;
import com.carinsurance.adapter.HomePage0Adapter.OnFuWuClistener;
import com.carinsurance.adapter.HomePage0Adapter.OnMySMXCClistener;
import com.carinsurance.infos.BaiduWeatherModel;
import com.carinsurance.infos.Content;
import com.carinsurance.infos.MyCarInfosGroupModel;
import com.carinsurance.infos.MyCarInfosModel;
import com.carinsurance.infos.PcModel;
import com.carinsurance.infos.SeriverTypeModel;
import com.carinsurance.infos.SeriverTypeitemModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.AMapLocationUtils;
import com.carinsurance.utils.AMapLocationUtils.getAMapLocation;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.RepeatClick;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomePage0Fragment extends BaseFragment implements IXListViewListener {
	private static HomePage0Fragment homePage0Fragment;
	XListView xListView;
	FragmentManager manager;
	HomePage0Adapter adapter;
	// 获取天气的
	BaiduWeatherModel baiduWeatherModel;
	// 服务种类
	SeriverTypeModel seriverTypeModel;
	boolean is_gettianqi_finish = false;// 天气获取是否完成
	boolean is_getfuwu_finish = false;// 服务获取是否完成

	boolean is_getUser_Car_Info = false;// 获取用户的车型信息
	MyCarInfosGroupModel myCarGroupInfosModel;
	SeriverTypeitemModel SeriverTypeitemModel0;
	// SeriverTypeitemModel SeriverTypeitemModel1;
	// SeriverTypeitemModel SeriverTypeitemModelitem;
	String type = "";// 点击的是服务还是商品 0是服务，1是商品

	String id;
	String name;

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);

		switch (task) {
		case Task.GET_COMMON_INDEX:
			try {
				xListView.stopLoadMore();
				xListView.stopRefresh();
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}

	}

	@Override
	public void initNetmessageSuccess(Message msg, String message, String task) {
		super.initNetmessageSuccess(message, task);
		Log.v("aaa", "运行到了天气这task=" + task);
		switch (task) {
		case Task.GETTIANQI:
			try {
				Log.v("aaa", "运行到了天气这108");
				String results = message;
				Log.v("aaa", "运行到了天气这110");
				baiduWeatherModel = JsonUtil.getEntityByJsonString(results, BaiduWeatherModel.class);
				Log.v("aaa", "运行到了天气这112");
				adapter.setWeather(baiduWeatherModel);
				Log.v("aaa", "运行到了天气这114");
				is_gettianqi_finish = true;
				Log.v("aaa", "运行到了天气这116");
				// if (is_getfuwu_finish == true && is_gettianqi_finish == true)
				// {
				Log.v("aaa", "运行到了天气这119");
				adapter.notifyDataSetChanged();
				Log.v("aaa", "运行到了天气这121");
				// }
				// xListView.stopLoadMore();
				// xListView.stopRefresh();
			} catch (Exception e) {
				Log.v("aaa", "运行到了天气这126");
			}
			break;
		case Task.GET_COMMON_INDEX:

			try {
				String results = message;
				seriverTypeModel = JsonUtil.getEntityByJsonString(results, SeriverTypeModel.class);
				adapter.setFuwuModel(seriverTypeModel);
				is_getfuwu_finish = true;
				// if (is_getfuwu_finish == true && is_gettianqi_finish == true)
				// {
				adapter.notifyDataSetChanged();
				// }
				xListView.stopLoadMore();
				xListView.stopRefresh();
			} catch (Exception e) {
			}
			break;
		case Task.GET_USERCARS:
			try {
				// Content.enter_ChaiceCarFlags = 0;
				String results = message;
				try {
					myCarGroupInfosModel = JsonUtil.getEntityByJsonString(results, MyCarInfosGroupModel.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				is_getUser_Car_Info = true;
				// String sctop = SeriverTypeitemModel0.getSctop();
				// 判断客户是否设置了车型
				if (myCarGroupInfosModel.getResult().equals("001")) {
					if (myCarGroupInfosModel.getList() != null) {
						Log.v("aaa", "运行了1");
						if (!myCarGroupInfosModel.getList().isEmpty()) {
							Log.v("aaa", "运行了2");
							SortModel sortModel = new SortModel();
							// sortModel.set
							sortModel.setSeriverTypeitemModel0(SeriverTypeitemModel0);
							List<MyCarInfosModel> mylist = myCarGroupInfosModel.getList();
							// 遍历list，如果有默认车型
							for (int i = 0; i < mylist.size(); i++) {
								if (mylist.get(i).getDefaultCar().equals("1")) {
									// Log.v("aaa", "有默认车型");
									sortModel.setUcid(myCarGroupInfosModel.getList().get(i).getUcid());
									sortModel.setName(myCarGroupInfosModel.getList().get(i).getCbname());
									sortModel.setCs_name(myCarGroupInfosModel.getList().get(i).getCsname());
									sortModel.setCmid(myCarGroupInfosModel.getList().get(i).getCmid());
									sortModel.setColor(myCarGroupInfosModel.getList().get(i).getColor());
									sortModel.setPlateNumber(myCarGroupInfosModel.getList().get(i).getPlateNumber());
									sortModel.setCm_name(myCarGroupInfosModel.getList().get(i).getCmname());

									if (Content.enter_ChaiceCarFlags == 0)
										JumpUtils.jumpto(getActivity(), ServiceOrderActivity.class, sortModel);
									else if (Content.enter_ChaiceCarFlags == 3)// 上门保养流程(多商品)
									{
										JumpUtils.jumpto(getActivity(), CarServiceActivity.class, sortModel);
									} else if (Content.enter_ChaiceCarFlags == 4)// 上门保养流程(dan商品)
									{

										List<SeriverTypeitemModel> list = new ArrayList<SeriverTypeitemModel>();
										list.add(SeriverTypeitemModel0);
										sortModel.setSelectSeriverTypeitemList(list);
										JumpUtils.jumpto(getActivity(), SelectServer1Activity.class, sortModel);
									} else if (Content.enter_ChaiceCarFlags == 5)// 5热卖分类走的选车流程
									{
										HashMap<String, String> map = new HashMap<String, String>();
										map.put("id", id);
										map.put("name", name);

										JumpUtils.jumpto(getActivity(), HotCategoryListActivity.class, sortModel, map);
									} else if (Content.enter_ChaiceCarFlags == 6)// 5热卖分类走的选车流程
									{
										HashMap<String, String> map = new HashMap<String, String>();
										// map.put("id", id);
										// map.put("name", name);
										JumpUtils.jumpto(getActivity(), ClassificationOfGoodsActivity.class, sortModel, map);
									}
									break;
								}
								if (i == mylist.size() - 1) {
									Log.v("aaa", "无默认车型");
									try {
										Log.v("aaa", "运行了3");

										SortModel sortModel1 = new SortModel();

										sortModel.setPci_id(id);
										sortModel.setPci_name(name);

										sortModel1.setSeriverTypeitemModel0(SeriverTypeitemModel0);
										JumpUtils.jumpto(getActivity(), ChoiceCarActivity.class, sortModel);
										// Utils.showMessage(getActivity(),
										// "暂无车型信息！");
									} catch (Exception e) {
										// TODO: handle exception
									}
								}
							}

						} else {
							Log.v("aaa", "运行了3");
							// Content.enter_ChaiceCarFlags = 0;
							SortModel sortModel = new SortModel();
							sortModel.setSeriverTypeitemModel0(SeriverTypeitemModel0);
							// sortModel.set
							sortModel.setPci_id(id);
							sortModel.setPci_name(name);
							JumpUtils.jumpto(getActivity(), ChoiceCarActivity.class, sortModel);
							// Utils.showMessage(getActivity(), "暂无车型信息！");
						}
					} else {
						Log.v("aaa", "运行了4");
						// Content.enter_ChaiceCarFlags = 0;
						SortModel sortModel = new SortModel();
						sortModel.setSeriverTypeitemModel0(SeriverTypeitemModel0);
						sortModel.setPci_id(id);
						sortModel.setPci_name(name);
						JumpUtils.jumpto(getActivity(), ChoiceCarActivity.class, sortModel);
						// Utils.showMessage(getActivity(), "暂无车型信息！");
					}
				} else {
					Utils.showMessage(getActivity(), "未知异常：错误码" + myCarGroupInfosModel.getResult());
				}

			} catch (Exception e) {
				Log.v("aaa", "运行了5");
			}

			break;
		}
	}

	public HomePage0Fragment(FragmentManager manager) {
		this.manager = manager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.xlistview, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		xListView = (XListView) view.findViewById(R.id.myListView);
		xListView.setVerticalScrollBarEnabled(false);// 不活动的时候隐藏，活动的时候也隐藏
		adapter = new HomePage0Adapter(getActivity(), manager);
		adapter.setOnTop2FuWuClistener(new OnMySMXCClistener() {

			@Override
			public void toprightButtonClistener(SeriverTypeitemModel SeriverTypeitem1, View v) {
				// TODO Auto-generated method stub
				// HashMap<String, String> map = new HashMap<String, String>();
				// map.put("fuwutypeid", SeriverTypeitemModel0.getScid());
				// Content.enter_ChaiceCarFlags = 0;
				// JumpUtils.jumpto(context, ChoiceCarActivity.class, map);
				// SeriverTypeitemModel1 = SeriverTypeitem1;
				SeriverTypeitemModel0 = SeriverTypeitem1;
				RepeatClick.setRepeatClick(v);
				Content.enter_ChaiceCarFlags = 3;
				if (SeriverTypeitemModel0 != null)
					getUser_CheInfo(SeriverTypeitem1.getSctop());
			}

			@Override
			public void topleftButtonClistener(SeriverTypeitemModel SeriverTypeitem0, View v) {
				// TODO Auto-generated method stub
				SeriverTypeitemModel0 = SeriverTypeitem0;
				RepeatClick.setRepeatClick(v);
				Content.enter_ChaiceCarFlags = 0;
				if (SeriverTypeitemModel0 != null)
					getUser_CheInfo(SeriverTypeitem0.getSctop());

			}

			// ///特色市场那一部分的点击事件
			@Override
			public void teSheClistener(View v, int type) {
				// TODO Auto-generated method stub
				// if (Utils.isLogin(getActivity())) {
				RepeatClick.setRepeatClick(v);
				if (type == 2) {// 如果是天天特价
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("type", "" + type);
					JumpUtils.jumpto(getActivity(), TianTianTejiaActivity.class, map);
				} else {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("type", "" + type);
					JumpUtils.jumpto(getActivity(), Brand_RecommendationActivity.class, map);
				}

			}

			// 热卖分类
			@Override
			public void remaiFenleiClistener(PcModel pcmodel, View v, int position) {
				// TODO Auto-generated method stub
				RepeatClick.setRepeatClick(v);
				Content.enter_ChaiceCarFlags = 5;
				id = pcmodel.getPcid();
				name = pcmodel.getPcname();
				Log.v("aaa", "id=" + id + "/name=" + name);
				getUser_CheInfo("5");
			}

			@Override
			public void remai_more(View v) {
				// TODO Auto-generated method stub
				Content.enter_ChaiceCarFlags = 6;
				RepeatClick.setRepeatClick(v);
				getUser_CheInfo("6");

				// JumpUtils.jumpto(context,
				// ClassificationOfGoodsActivity.class, null);
			}
		});
		adapter.setOnFuWuItemClistener(new OnFuWuClistener() {

			@Override
			public void fuwuButtonClistener(int position, ViewGroup parent, SeriverTypeitemModel model, View v) {
				// TODO Auto-generated method stub
				RepeatClick.setRepeatClick(v);
				Content.enter_ChaiceCarFlags = 4;
				SeriverTypeitemModel0 = model;
				// SeriverTypeitemModelitem=model;
				getUser_CheInfo(model.getSctop());
			}
		});
		xListView.setAdapter(adapter);
		xListView.setDivider(null);
		xListView.setPullLoadEnable(false);
		xListView.setPullRefreshEnable(true);
		xListView.setXListViewListener(this);
		onRefresh();

	}

	@Override
	public void onRefresh() {
		is_gettianqi_finish = false;
		is_getfuwu_finish = false;
		location();
		getFuwu();

	}

	/**
	 * 获取服务
	 */
	private void getFuwu() {
		// TODO Auto-generated method stub]
		HashMap<String, String> params = new HashMap<String, String>();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(getActivity())) && !StringUtil.isNullOrEmpty(Utils.getToken(getActivity()))) {
			params.put("uid", Utils.getUid(getActivity()));
			params.put("token", Utils.getToken(getActivity()));
		}
		NetUtils.getIns().post(Task.GET_COMMON_INDEX, params, handler);
	}

	@Override
	public void onLoadMore() {

	}

	/**
	 * 可以是城市名称，也可以是城市编码
	 * 
	 * @param cityName
	 */
	public void getWeather(final String cityName) {
		// String path = "http://api.map.baidu.com/telematics/v3/weather" +
		// "?location=" + cityName + "&output=json&ak=" +
		// "P8TB4IQ6AAg9efkpDGy4nGXQ";
		// Log.v("bbb","startTime="+System.currentTimeMillis());
		// NetUtils.get_async(path, handler, Task.GETTIANQI);
		// if(StringUtil.isNullOrEmpty(""))

		NetUtils.getIns().getWeather(cityName, handler, Task.GETTIANQI);

	}

	/**
	 * 获取用户车型信息
	 * 
	 * @param cityName
	 */
	public void getUser_CheInfo(String type) {
		// String path = "http://api.map.baidu.com/telematics/v3/weather" +
		// "?location=" + cityName + "&output=json&ak=" +
		// "P8TB4IQ6AAg9efkpDGy4nGXQ";
		// Log.v("bbb","startTime="+System.currentTimeMillis());
		// NetUtils.get_async(path, handler, Task.GETTIANQI);
		this.type = type;
		if (Utils.isLogin(getActivity())) {
			RequestParams requestParams = new RequestParams();
			requestParams.addBodyParameter("uid", Utils.getUid(getActivity()));
			requestParams.addBodyParameter("token", Utils.getToken(getActivity()));
			NetUtils.getIns().post(Task.GET_USERCARS, requestParams, handler);
		} else {
			Utils.showMessage(getActivity(), "请登录！");
			JumpUtils.jumpto(getActivity(), LoginActivity.class, null);
		}

	}

	public void location() {
		// 这只是一个使用接口的模板
		final AMapLocationUtils aMapLocationUtils = new AMapLocationUtils(getActivity());
		new Thread(new Runnable() {
			boolean isRun = true;

			@Override
			public void run() {

				while (isRun) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					aMapLocationUtils.setOnAMapLocationClistener(new getAMapLocation() {

						@Override
						public void getAMapLocation(AMapLocation aMapLocation, boolean islocation) {
							// Log.i("aaa", "" + aMapLocation);
							if (aMapLocation != null) {
								Log.i("aaa", "经度-》" + aMapLocation.getLongitude() + "纬度=》" + aMapLocation.getLatitude());
								// Bundle locBundle =
								// aMapLocation.getExtras();
								String cityName = aMapLocation.getCity();
								Utils.setCityName(getActivity(), cityName);
								// Log.v("aaa","citycode="+cityName);
								getWeather(cityName);
								isRun = false;
							}
							Log.i("aaa", "是否正在定位：===》"+islocation);
							if (islocation == false) {
								isRun = false;
								getWeather(getResources().getString(R.string.guiyang));
							}

						}
					});
				}

			}
		}).start();

	}

}
