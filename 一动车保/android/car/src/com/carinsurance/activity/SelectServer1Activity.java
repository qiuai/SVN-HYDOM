package com.carinsurance.activity;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.SelectServer1Adapter;
import com.carinsurance.adapter.SelectServer1Adapter.OnclickDelete;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.MyCarInfosModel;
import com.carinsurance.infos.ProductDefaultItemModel;
import com.carinsurance.infos.ProductDefaultModel;
import com.carinsurance.infos.SeriverTypeitemModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 选择汽车服务详细
 * 
 * @author Administrator
 *
 */
public class SelectServer1Activity extends BaseActivity {
	private final int REQUEST_CODE_TO_CARMANAGER = 100;

	@ViewInject(R.id.expan_ListView1)
	ExpandableListView expan_ListView1;
	@ViewInject(R.id.fuwu_list)
	ListView fuwu_list;

	@ViewInject(R.id.user_center_book_business)
	RelativeLayout user_center_book_business;

	@ViewInject(R.id.in_the_spark_plug)
	CheckBox in_the_spark_plug;

	SortModel sortModel;

	SelectServer1Adapter adapter;
	ProductDefaultModel productDefaultModel;
	List<SeriverTypeitemModel> list;

	@ViewInject(R.id.tv_shipeichexing)
	TextView tv_shipeichexing;
	@ViewInject(R.id.btn_tv_shipeichexing)
	FrameLayout btn_tv_shipeichexing;
	@ViewInject(R.id.bottom_layout)
	LinearLayout bottom_layout;

	@ViewInject(R.id.title)
	TextView title;
	String type = "-1";// 默认是-1，-1是走保养商品流程 , 0是看有商品订单不能改， 1是仅仅只是看无商品订单，也不能改

	// boolean is_zidingyipeijian=false;

	@ViewInject(R.id.rl_fuwufei)
	RelativeLayout rl_fuwufei;

	@ViewInject(R.id.fuwumoney)
	TextView fuwumoney;

	public void getCunBaoYangJiaGe() {

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(SelectServer1Activity.this));
		map.put("token", Utils.getToken(SelectServer1Activity.this));
		NetUtils.getIns().post(Task.GET_PUREMAINTENANCE_PRICE, map, handler);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_server0);
		ViewUtils.inject(this);
		// expan_ListView1.setAdapter(new )

		getCunBaoYangJiaGe();
		// is_zidingyipeijian=false;
		sortModel = (SortModel) JumpUtils.getSerializable(this);
		String nsr = sortModel.getCm_name();
		if (!StringUtil.isNullOrEmpty(nsr)) {
			tv_shipeichexing.setText(getString(R.string.adpater_chexing, sortModel.getCs_name()));// +sortModel.getCm_name()
		} else {
			tv_shipeichexing.setText(getString(R.string.adpater_chexing, "无"));// +sortModel.getCm_name()
		}

		type = JumpUtils.getString(SelectServer1Activity.this, "type");
		if (StringUtil.isNullOrEmpty(type)) {
			type = "-1";
		}
		Log.v("aaa", "type====>" + type);
		// 如果不是选商品的订单
		if (!type.equals("-1")) {
			btn_tv_shipeichexing.setVisibility(View.GONE);
			bottom_layout.setVisibility(View.GONE);
			user_center_book_business.setVisibility(View.GONE);
			title.setText("商品详情");
		}
		Log.v("aaa", "typesss===>" + type);
		in_the_spark_plug.setChecked(false);
		expan_ListView1.setVisibility(View.VISIBLE);
		fuwu_list.setVisibility(View.GONE);
		if (!type.equals("-1")) {
			if (type.equals("0")) {
				expan_ListView1.setVisibility(View.VISIBLE);
				fuwu_list.setVisibility(View.GONE);
				initExpanAbleListView();
			} else if (type.equals("1")) {
				expan_ListView1.setVisibility(View.GONE);
				fuwu_list.setVisibility(View.VISIBLE);
				initListView();

				rl_fuwufei.setVisibility(View.VISIBLE);

			}

		} else if (type.equals("-1"))
			getProductDefault();
	}

	@OnClick({ R.id.user_center_book_business, R.id.queding, R.id.return_btn, R.id.xiugai0, R.id.btn_tv_shipeichexing })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_center_book_business:
			// Log.v("aaa","getShangPingNumber==>"+adapter.getShangPingNumber());
			if (in_the_spark_plug.isChecked() == true) {

				if (adapter.getShangPingNumber() == 0) {
					in_the_spark_plug.setChecked(false);
					adapter.setIs_zidingyipeijian(false);
					
					expan_ListView1.setVisibility(View.VISIBLE);
					fuwu_list.setVisibility(View.GONE);
					adapter.notifyDataSetChanged();
					return ;
				}

				in_the_spark_plug.setChecked(false);
				// is_zidingyipeijian=false;
				rl_fuwufei.setVisibility(View.GONE);
				adapter.setIs_zidingyipeijian(false);
				adapter.notifyDataSetChanged();
				expan_ListView1.setVisibility(View.VISIBLE);
				fuwu_list.setVisibility(View.GONE);
			} else {
				in_the_spark_plug.setChecked(true);
				// is_zidingyipeijian=true;
				adapter.setIs_zidingyipeijian(true);
				rl_fuwufei.setVisibility(View.VISIBLE);
				adapter.notifyDataSetChanged();
				expan_ListView1.setVisibility(View.GONE);
				fuwu_list.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.queding:
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "1");
			if (in_the_spark_plug.isChecked() == true) {
				sortModel.setIs_zibeipeijian("1");
			} else {
				sortModel.setIs_zibeipeijian("0");
			}
			if (in_the_spark_plug.isChecked() == false) {

				try {
					String pids = "";// 除了判断是否有商品外并没什么卵用
					for (int i = 0; i < sortModel.getSelectSeriverTypeitemList().size(); i++) {
						for (int j = 0; j < sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().size(); i++) {

							if (!sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().isEmpty()) {
								// sd #
								pids += sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getPid() + "#";
							}

						}

					}
					if (pids.length() == 0) {
						sortModel.setIs_zibeipeijian("1");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			JumpUtils.jumpto(SelectServer1Activity.this, ServiceOrderActivity.class, sortModel, map);
			break;
		case R.id.return_btn:
			setResultData();
			finish();
			break;
		case R.id.xiugai0:
			JumpUtils.jumpfinish(SelectServer1Activity.this);
			break;
		case R.id.btn_tv_shipeichexing:
			JumpUtils.jumpActivityForResult(this, CarManageActivity.class, null, REQUEST_CODE_TO_CARMANAGER);
			break;

		}
	}

	/**
	 * 获取推荐的商品
	 */
	public void getProductDefault() {
		HashMap<String, String> rp = new HashMap<String, String>();
		rp.put("uid", Utils.getUid(this));
		rp.put("token", Utils.getToken(this));
		List<SeriverTypeitemModel> li = sortModel.getSelectSeriverTypeitemList();
		StringBuffer buff = new StringBuffer("");
		for (int i = 0; i < li.size(); i++) {
			if (i == li.size() - 1)
				buff.append(li.get(i).getScid());
			else
				buff.append(li.get(i).getScid() + "#");
		}
		rp.put("scid", buff.toString());
		if (!StringUtil.isNullOrEmpty(sortModel.getCmid())) {
			rp.put("cmid", sortModel.getCmid());
		} else {
			Utils.showMessage(SelectServer1Activity.this, "车型不能为空!");
			return;
		}
		NetUtils.getIns().post(Task.GET_PRODUCTDEFAULT, rp, this.handler);

	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);

		if (task.equals(Task.GET_PRODUCTDEFAULT))// 获取推荐的一个商品
		{
			try {
				String result = (new JSONObject(message)).getString("result");

				if (result.equals("001")) {
					// try {
					// initExpanAbleListView
					productDefaultModel = JsonUtil.getEntityByJsonString(message, ProductDefaultModel.class);

					initExpanAbleListView();
					initListView();

				} else {
					Utils.showMessage(SelectServer1Activity.this, "服务器出错,错误码:" + result);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v("aaa", "baocuo");
				e.printStackTrace();
			}
		} else if (task.equals(Task.GET_PUREMAINTENANCE_PRICE)) {
			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");

				if (result.equals("001")) {
					String price = js.getString("price");
					fuwumoney.setText("￥" + price);
				} else {
					Utils.showMessage(SelectServer1Activity.this, "网络错误,错误码" + result);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v("aaa", "baocuo");
				e.printStackTrace();
			}
		}

	}

	private void initExpanAbleListView() {
		// LogUtils.v("SelectServer1Activity 102");
		// mingtianlaizuozhe
		if (type.equals("-1"))
			adapter = new SelectServer1Adapter(SelectServer1Activity.this, productDefaultModel.getList(), sortModel, type);// ,
		else
			adapter = new SelectServer1Adapter(SelectServer1Activity.this, sortModel, type);// ,

		if (type.equals("-1")) {
			if (productDefaultModel.getList().isEmpty()) {
				adapter.setIs_zidingyipeijian(true);
//				in_the_spark_plug.setChecked(true);
				rl_fuwufei.setVisibility(View.VISIBLE);
			} else {
				adapter.setIs_zidingyipeijian(false);
//				in_the_spark_plug.setChecked(false);
				rl_fuwufei.setVisibility(View.GONE);
			}
		}
		adapter.setOnDeleteShangpingClistener(new OnclickDelete() {

			@Override
			public void delete(List<SeriverTypeitemModel> modellist, int groupPosition, int childPosition, int number) {
				// TODO Auto-generated method stub
				if (number == 1)// 如果商品数量只剩一件的话
				{
					modellist.get(groupPosition).getProductDefaultItemModel_list().remove(childPosition);
//					in_the_spark_plug.setChecked(true);
					rl_fuwufei.setVisibility(View.VISIBLE);
					adapter.setIs_zidingyipeijian(true);
//					sdfsfsd
					adapter.notifyDataSetChanged();
				} else {
					modellist.get(groupPosition).getProductDefaultItemModel_list().remove(childPosition);
					adapter.notifyDataSetChanged();
				}

			}
		});
		expan_ListView1.setAdapter(adapter);
		expan_ListView1.setDivider(getResources().getDrawable(R.color.bj_f0f0f0));
		expan_ListView1.setChildDivider(getResources().getDrawable(R.color.bj_f0f0f0));
		expan_ListView1.setDividerHeight((int) DisplayUtil.getDip(SelectServer1Activity.this, 1));
		expan_ListView1.setGroupIndicator(null); // 去掉父类的箭头
		// 展开所有
		for (int i = 0, length = adapter.getGroupCount(); i < length; i++) {
			expan_ListView1.expandGroup(i);
		}
		expan_ListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				// TODO Auto-generated method stub
				return true;// 默认为false，设为true时，点击事件不会展开Group
			}
		});

	}

	private void initListView() {
		// TODO Auto-generated method stub
		list = sortModel.getSelectSeriverTypeitemList();
		fuwu_list.setAdapter(new AbstractBaseAdapter<SeriverTypeitemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater.from(SelectServer1Activity.this);
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.fuwu_list_item, null);
				}
				SeriverTypeitemModel p = getItem(position);

				RelativeLayout rl_fuwufei = ViewHolder.get(convertView, R.id.rl_fuwufei);
				rl_fuwufei.setVisibility(View.GONE);
				ImageView pimage = ViewHolder.get(convertView, R.id.pimage);
				new xUtilsImageLoader(SelectServer1Activity.this).display(pimage, p.getScimage());

				TextView pname = ViewHolder.get(convertView, R.id.pname);
				pname.setText("" + p.getScname());
				// 服务费 （第一次需求更改，去掉了服务费）
				// TextView fuwu_fei = ViewHolder.get(convertView,
				// R.id.fuwu_fei);
				// fuwu_fei.setText("￥" + p.getScprice());

				return convertView;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResultData();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setResultData() {
		Intent res = new Intent();
		res.putExtra("data", sortModel); // 将当前选择返回 更新上一界面
		setResult(RESULT_OK, res);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// 新增获取数据成功
		if (requestCode == NewAddingActivity.RESULT_OK && resultCode == NewAddingActivity.RESULT_OK) {
			if (data != null) {
				String gr = data.getStringExtra("groupPosition");
				ProductDefaultItemModel p = (ProductDefaultItemModel) data.getSerializableExtra(JumpUtils.SerializableKey);
				try {
					Log.v("aaa", "返回的gr是" + gr);
					Log.v("aaa", "返回的p是" + p.toString());
					int groupPosition = Integer.parseInt(gr);
					sortModel.getSelectSeriverTypeitemList().get(groupPosition).getProductDefaultItemModel_list().add(p);
					adapter.setIs_zidingyipeijian(false);
					in_the_spark_plug.setChecked(false);
					rl_fuwufei.setVisibility(View.GONE);
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}

		// 车管家返回值
		if (requestCode == REQUEST_CODE_TO_CARMANAGER && resultCode == RESULT_OK) {
			MyCarInfosModel model = (MyCarInfosModel) data.getSerializableExtra("data");
			if (model == null) {
				// 不做数据更改 or 怎么选择不适配车型

				// 不适配任何车型

				// tv_shipeichexing.setText(getString(R.string.adpater_chexing,
				// "无"));
				// sortModel.setCmid(null); //关键值 在网络请求数据时需要传递
				// sortModel.setCbid(null);
				// sortModel.setCm_name(null);
				// sortModel.setColor(null);
				// sortModel.setCs_name(null);
				// sortModel.setCsid(null);
				// sortModel.setName(null);
				// sortModel.setUcid(null);
				// sortModel.setPlateNumber(null);
				// sortModel.setColor(null);
				//
				// getProductDefault();

			} else {
				if (sortModel.getCmid().equals(model.getCmid())) {
					return;
				}

				tv_shipeichexing.setText(getString(R.string.adpater_chexing, model.getCsname()));
				// 将CarInfoModel转化为SortMode 传递给下一个界面
				sortModel.setCbid(model.getCbid());
				sortModel.setCm_name(model.getCmname());
				sortModel.setColor(model.getColor());
				sortModel.setCs_name(model.getCsname());
				sortModel.setCsid(model.getCsid());
				sortModel.setName(model.getCbname());
				sortModel.setUcid(model.getUcid());

				sortModel.setCmid(model.getCmid());// 车型ID 必须设置 网络请求
				sortModel.setPlateNumber(model.getPlateNumber());// 设置车牌
																	// 必须设置，下一级更新界面相关
				sortModel.setColor(model.getColor()); // 设置车辆颜色 必须设置，下一级更新界面相关
				getProductDefault();
			}
		}
	}
}
