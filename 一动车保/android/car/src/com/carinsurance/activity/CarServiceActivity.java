package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.MyCarInfosModel;
import com.carinsurance.infos.SeriverTypeModel;
import com.carinsurance.infos.SeriverTypeitemModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 
 * 选择汽车服务 2015-8-4 liqi
 * 
 * @author Administrator
 * 
 */
public class CarServiceActivity extends BaseActivity {
	private final int REQUEST_CODE_TO_CARMANAGER = 100;
	private final int REQUEST_CODE_TO_SELECT_SERVICE = 101;
	@ViewInject(R.id.listView)
	private ListView listview;

	@ViewInject(R.id.queding)
	TextView queding;
	// private CarServiceAdapter carserviceadapter;
	List<SeriverTypeitemModel> list;

	private int selectItem = -1;// 选中项目
	TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();

	SortModel sortModel;

	@ViewInject(R.id.tv_shipeichexing)
	TextView tv_shipeichexing;
	@ViewInject(R.id.btn_tv_shipeichexing)
	FrameLayout btn_tv_shipeichexing;

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		if (task.equals(Task.GET_MAINTENANCE))// 保存车型操作
		{
			try {
				String result = (new JSONObject(message)).getString("result");
				if (result.equals("001")) {
					try {
						SeriverTypeModel smodel = JsonUtil
								.getEntityByJsonString(message,
										SeriverTypeModel.class);
						list = smodel.getList();
						initView();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					Utils.showMessage(CarServiceActivity.this, "服务器出错,错误码:"
							+ result);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_service_list);
		ViewUtils.inject(this);

		sortModel = (SortModel) JumpUtils.getSerializable(this);
		if (sortModel != null
				&& !StringUtil.isNullOrEmpty(sortModel.getCm_name())) {
			tv_shipeichexing.setText(getString(R.string.adpater_chexing,
					sortModel.getCs_name()));
		}
		try {
			Log.v("aaa", sortModel.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

		// initView();
		getServiceMaintenance();
	}

	public void getServiceMaintenance() {
		HashMap<String, String> rp = new HashMap<String, String>();
		rp.put("uid", Utils.getUid(this));
		rp.put("token", Utils.getToken(this));

		NetUtils.getIns().post(Task.GET_MAINTENANCE, rp, this.handler);
	}

	@OnClick({ R.id.queding, R.id.return_btn, R.id.btn_tv_shipeichexing })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.queding:
			// sortModel
			List<SeriverTypeitemModel> selectItemModel = new ArrayList<SeriverTypeitemModel>();
			if (!map.isEmpty()) {
				for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
					// System.out.println("key= " + entry.getKey() +
					// " and value= " + entry.getValue());
					selectItemModel.add(list.get(entry.getKey()));
				}
				sortModel.setSelectSeriverTypeitemList(selectItemModel);
				JumpUtils.jumpActivityForResult(this, SelectServer1Activity.class, sortModel, REQUEST_CODE_TO_SELECT_SERVICE,false);
//				JumpUtils.jumpto(CarServiceActivity.this,
//						SelectServer1Activity.class, sortModel);
			} else {
				Utils.showMessage(CarServiceActivity.this, "请至少选择一项服务！");
			}

			break;
		case R.id.return_btn:
			JumpUtils.jumpfinish(CarServiceActivity.this);
			break;
		case R.id.btn_tv_shipeichexing:
			JumpUtils.jumpActivityForResult(CarServiceActivity.this,
					CarManageActivity.class, null, REQUEST_CODE_TO_CARMANAGER);
			break;
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		listview.setAdapter(new AbstractBaseAdapter<SeriverTypeitemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(final int position,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = getLayoutInflater().inflate(
							R.layout.car_service_item, parent, false);
				}
				SeriverTypeitemModel item = getItem(position);
				ImageView scimage = ViewHolder.get(convertView, R.id.scimage);

				CheckBox in_the_spark_plug = ViewHolder.get(convertView,
						R.id.in_the_spark_plug);

				// int select=map.get(position);
				if (map.containsKey(position)) {
					in_the_spark_plug.setChecked(true);
				} else {
					in_the_spark_plug.setChecked(false);
				}

				TextView scname = ViewHolder.get(convertView, R.id.scname);
				LinearLayout user_center_book_business = ViewHolder.get(
						convertView, R.id.user_center_book_business);
				new xUtilsImageLoader(CarServiceActivity.this).display(scimage,
						item.getScimage());
				scname.setText(item.getScname());
				user_center_book_business
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								Log.v("aaa", "车市结果" + map.toString());
								// TODO Auto-generated method stub
								if (map.containsKey(position) == true) {
									map.remove(position);
									notifyDataSetChanged();
								} else {
									map.put(position, position);
									notifyDataSetChanged();
								}

								// Utils.showMessage(CarServiceActivity.this,
								// "成功");
							}
						});
				return convertView;
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_TO_CARMANAGER
				&& resultCode == RESULT_OK) {
			MyCarInfosModel model = (MyCarInfosModel) data
					.getSerializableExtra("data");
			if (model == null) {
				tv_shipeichexing.setText(getString(R.string.adpater_chexing,
						"无"));
				sortModel.setCmid(null); // 关键值 在网络请求数据时需要传递
				sortModel.setCbid(null);
				sortModel.setCm_name(null);
				sortModel.setColor(null);
				sortModel.setCs_name(null);
				sortModel.setCsid(null);
				sortModel.setName(null);
				sortModel.setUcid(null);
			} else {

				tv_shipeichexing.setText(getString(R.string.adpater_chexing,
						model.getCsname()));
				// 将CarInfoModel转化为SortMode 传递给下一个界面
				sortModel.setCbid(model.getCbid());
				sortModel.setCm_name(model.getCmname());
				sortModel.setColor(model.getColor());
				sortModel.setCs_name(model.getCsname());
				sortModel.setCsid(model.getCsid());
				sortModel.setName(model.getCbname());
				sortModel.setUcid(model.getUcid());

				sortModel.setCmid(model.getCmid()); // 关键值 在网络请求数据时需要传递
			}
		} else if (requestCode == REQUEST_CODE_TO_SELECT_SERVICE
				&& resultCode == RESULT_OK) {
			SortModel sortModel = (SortModel) data.getSerializableExtra("data");
			this.sortModel = sortModel;
			if (sortModel!=null && !StringUtil.isNullOrEmpty(sortModel.getCm_name())) {
				tv_shipeichexing.setText(getString(R.string.adpater_chexing,
						sortModel.getCs_name()));
			}else{
				tv_shipeichexing.setText(getString(R.string.adpater_chexing,
						"无"));
			}
		}
	}
}
