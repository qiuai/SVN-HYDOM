package com.carinsurance.activity;

import java.util.List;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.er_listviewAdapter;
import com.carinsurance.infos.CarXModel;
import com.carinsurance.infos.CarXitemModel;
import com.carinsurance.infos.CarseriesModel;
import com.carinsurance.infos.Content;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择车型或车系
 * 
 * 2015-7-13
 * 
 * @author Administrator
 *
 */
public class SelectModels1Activity extends BaseActivity {

	String type = "0";// 0:选车系， 1选车型
	@ViewInject(R.id.head_img)
	ImageView head_img;
	@ViewInject(R.id.head_cheming)
	TextView head_cheming;
	@ViewInject(R.id.er_ListView)
	ExpandableListView er_ListView;
	@ViewInject(R.id.yi_listView)
	ListView yi_listView;

	@ViewInject(R.id.center_view)
	LinearLayout center_view;
	@ViewInject(R.id.topname)
	TextView topName;
	SortModel sortModel;

	CarseriesModel carseriesModel;// 车系的model
	CarXModel carXModel;// 车型的model
	er_listviewAdapter adapter;

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);

		if (task.equals(Task.GET_GETCARSERIVES)) {
			try {
				String results = message;
				carseriesModel = JsonUtil.getEntityByJsonString(results, CarseriesModel.class);
				if (carseriesModel.getResult().equals("" + NetUtils.NET_SUCCESS_001)) {
					adapter = new er_listviewAdapter(this, carseriesModel.getList(), sortModel);
					er_ListView.setAdapter(adapter);
					// 展开所有
					for (int i = 0, length = adapter.getGroupCount(); i < length; i++) {
						er_ListView.expandGroup(i);
					}
				}
			} catch (Exception e) {
			}
		} else if (task.equals(Task.GET_GETCARMODEL)) {
			try {
				String results = message;
				carXModel = JsonUtil.getEntityByJsonString(results, CarXModel.class);

				switch (carXModel.getResult()) {
				case NetUtils.NET_SUCCESS_001:
					success();
					break;
				case NetUtils.NET_DEFAIL_103:
					try {
						Utils.showMessage(SelectModels1Activity.this, "网络错误");
					} catch (Exception e) {
						// TODO: handle exception
					}
					break;
				case NetUtils.NET_WEIZHI_000:
					try {
						Utils.showMessage(SelectModels1Activity.this, "未知异常");
					} catch (Exception e) {
						// TODO: handle exception
					}
					break;
				}

			} catch (Exception e) {
			}

		}
	}

	/**
	 * 成功
	 */
	private void success() {
		// TODO Auto-generated method stub
		try {

			List<CarXitemModel> carlist = carXModel.getList();
			yi_listView.setAdapter(new AbstractBaseAdapter<CarXitemModel>(carlist) {

				@Override
				public View getAdapterViewAtPosition(final int position, View convertView, ViewGroup parent) {

					if (convertView == null) {
						convertView = SelectModels1Activity.this.getLayoutInflater().inflate(R.layout.er_list_child_item, parent, false);
					}

					CarXitemModel model = getItem(position);
					TextView name = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.text);
					LinearLayout item = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.item);

					name.setText(model.getCmname());
					item.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							SortModel ss = sortModel;
							// Log.v("aaa","我是sortmodel"+ss.toString());
							CarXitemModel car = (CarXitemModel) (yi_listView.getAdapter().getItem(position));
							ss.setCm_name(car.getCmname());
							ss.setCmid(car.getCmid());
							Log.v("aaa", "我是sortmodel" + ss.toString());

							if (Content.enter_ch == 1) {
								Content.sortModel = ss;
								finish();
							} else {//if (Content.enter_ChaiceCarFlags == 0 || Content.enter_ChaiceCarFlags == 2) {
								// 如果是洗车服务
								JumpUtils.jumpto(SelectModels1Activity.this, CarInfosActivity.class, ss, false);
							}

							// else
							// if(Content.enter_ChaiceCarFlags==2)
							// {
							// Content.sortModel=ss;
							// // finish();
							// JumpUtils.jumpto(SelectModels1Activity.this,
							// BrandActivity.class, ss, true);
							// }

						}
					});

					return convertView;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_models);

		ViewUtils.inject(this);
		this.findViewById(R.id.return_btn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpfinish(SelectModels1Activity.this);
			}
		});
		sortModel = (SortModel) JumpUtils.getSerializable(SelectModels1Activity.this);
		// Log.v("aaa", "sortModel=" + sortModel);
		// Log.v("aaa", "sortModel=" + sortModel.toString());
		type = sortModel.getType();
		if (type.equals("0")) {
			center_view.setVisibility(View.GONE);
			new xUtilsImageLoader(this).display(head_img, sortModel.getCbimage());
			head_cheming.setText("" + sortModel.getName());
			initNetchexi();
		} else if (type.equals("1")) {
			topName.setText("" + sortModel.getTopName());
			yi_listView.setVisibility(View.VISIBLE);
			new xUtilsImageLoader(this).display(head_img, sortModel.getCbimage());
			head_cheming.setText("" + sortModel.getName() + sortModel.getCs_name());
			initNetchexing();
		}

	}

	private void initNetchexing() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(SelectModels1Activity.this)) && !StringUtil.isNullOrEmpty(Utils.getToken(SelectModels1Activity.this))) {
			params.addBodyParameter("uid", Utils.getUid(SelectModels1Activity.this));
			params.addBodyParameter("token", Utils.getToken(SelectModels1Activity.this));
		}
		params.addBodyParameter("csid", sortModel.getCsid());
		Log.v("aaa", "uid=" + Utils.getUid(SelectModels1Activity.this));
		Log.v("aaa", "token=" + Utils.getToken(SelectModels1Activity.this));
		Log.v("aaa", "csid=" + sortModel.getCsid());
		NetUtils.getIns().post(Task.GET_GETCARMODEL, params, handler);
	}

	private void initNetchexi() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(SelectModels1Activity.this)) && !StringUtil.isNullOrEmpty(Utils.getToken(SelectModels1Activity.this))) {
			params.addBodyParameter("uid", Utils.getUid(SelectModels1Activity.this));
			params.addBodyParameter("token", Utils.getToken(SelectModels1Activity.this));
		}
		params.addBodyParameter("cbid", sortModel.getCbid());
		Log.v("aaa", "" + params.toString());
		Log.v("aaa", "cbid" + sortModel.getCbid() + "///" + Utils.getUid(SelectModels1Activity.this));
		Log.v("aaa", "" + "//Token==>" + Utils.getToken(SelectModels1Activity.this));

		NetUtils.getIns().post(Task.GET_GETCARSERIVES, params, handler);
	}
}
// 从这里完成后的数据
// 我是sortmodelSortModel[
// id=null,
// cbid=cf4f24af-0554-4185-a063-803cb78fd648,
// name=奥迪,
// fletter=ad,
// cbimage=null,
// type=1,
// csid=66cf5961-1015-40fc-b69a-a34dba513773,
// cs_name=奥迪A4L,
// cmid=65377a5e-081f-4a28-a5ba-1d80c53932a4,
// cm_name=2015款35TFSI百万纪念智领型,
// topName=一汽-大众奥迪,
// SeriverTypeitemModel0=SeriverTypeitemModel[
// id=null,
// sctop=2,
// scid=1,
// scname=上门洗车,
// scimage=upload/img/2015/6/16/0b4f207e-8b43-4ada-ad13-c8ea8798d87f.jpg,
// scremark=随时随地，上门服务
// ],
// sortLetters=null
// ]
