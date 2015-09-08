package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.carinsurance.adapter.CarManageAdapter;
import com.carinsurance.adapter.CarManageAdapter.OnClickItemDataClistenr;
import com.carinsurance.infos.Content;
import com.carinsurance.infos.MyCarInfosGroupModel;
import com.carinsurance.infos.MyCarInfosModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.Dialog;
import com.carinsurance.utils.Dialog.DialogClistener;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class CarManageActivity extends BaseActivity implements OnClickListener {

	private XListView listview = null;
	private CarManageAdapter adapter = null;
	// private Button btnAddUpdate = null;
	@ViewInject(R.id.add)
	TextView add;
	List<MyCarInfosModel> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_manage);
		ViewUtils.inject(this);
		listview = (XListView) findViewById(R.id.listview);
		listview.setPullRefreshEnable(true);
		listview.setPullLoadEnable(false);
		adapter = new CarManageAdapter(this);
		adapter.setOnClickItemDateClistener(new OnClickItemDataClistenr() {

			@Override
			public void Delete(int position) {
				// TODO Auto-generated method stub
				final MyCarInfosModel my = adapter.getItem(position);
				if (my.getDefaultCar().equals("1")) {
					Utils.showMessage(CarManageActivity.this, "默认车型不能删除！");
					return;
				} else {
					Dialog d = new Dialog();
					d.CreateDialog(CarManageActivity.this, "", "是否删除该车型?");
					d.setOnDialogClistener(new DialogClistener() {

						@Override
						public void ret() {
							// TODO Auto-generated method stub

						}

						@Override
						public void ok() {
							// TODO Auto-generated method stub
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("uid", Utils.getUid(CarManageActivity.this));
							map.put("token", Utils.getToken(CarManageActivity.this));
							map.put("ucid", my.getUcid());
							NetUtils.getIns().post(Task.GET_DELETE_CAR_XING, map, handler);
						}
					});

				}
			}

			@Override
			public void selectMoren(MyCarInfosModel module, int position, int defaultCar) {
				// TODO Auto-generated method stub
				HashMap<String, String> rp = new HashMap<String, String>();
				rp.put("uid", Utils.getUid(CarManageActivity.this));
				rp.put("token", Utils.getToken(CarManageActivity.this));
				rp.put("ucid", module.getUcid());
				rp.put("defaultCar", "" + defaultCar);

				rp.put("color", module.getColor());
				rp.put("plateNumber", module.getPlateNumber());

				rp.put("fuel", module.getFuel());
				rp.put("drange", module.getDrange());
				rp.put("engines", module.getEngines());
				rp.put("date", module.getDate());
				rp.put("cmid", module.getCmid());
				NetUtils.getIns().post(Task.USER_CAR_EDIT, rp, CarManageActivity.this.handler);
			}
		});
		listview.setAdapter(adapter);

		// btnAddUpdate = (Button) findViewById(R.id.btn_add_update);
		// add = (TextView) findViewById(R.id.add);
		// btnAddUpdate.setVisibility(View.GONE);
		// btnAddUpdate.setOnClickListener(this);
		findViewById(R.id.return_btn).setOnClickListener(this);

		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Content.enter_ChaiceCarFlags = 2;
				// JumpUtils.jumpActivityForResult(CarManageActivity.this,
				// CarInfosActivity.class, null, 1);

				Content.enter_ChaiceCarFlags = 2;
				JumpUtils.jumpActivityForResult(CarManageActivity.this, ChoiceCarActivity.class, null, 1);

			}
		});

		listview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				getUserCars();
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub

			}
		});

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				setResultData();
			}
		});
	}

	public void getUserCars() {
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("uid", Utils.getUid(this));
		rp.addBodyParameter("token", Utils.getToken(this));
		NetUtils.getIns().post(Task.GET_USERCARS, rp, handler);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getUserCars();
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		if (task.equals(Task.GET_USERCARS)) {
			JSONObject obj;
			try {
				obj = new JSONObject(message);
				listview.stopRefresh();
				if (obj.getInt("result") == 104) {
					Toast.makeText(CarManageActivity.this, "没有可用车辆信息", Toast.LENGTH_SHORT).show();
				} else {
					data = new ArrayList<MyCarInfosModel>();

					MyCarInfosGroupModel myCarInfosGroupModels = JsonUtil.getEntityByJsonString(obj.toString(), MyCarInfosGroupModel.class);

					data.addAll(myCarInfosGroupModels.getList());
					adapter.setDatas(data);
					adapter.notifyDataSetChanged();

				}
				// btnAddUpdate.setText(adapter.getCount() >= 1 ? "编辑" : "添加");
				// btnAddUpdate.setVisibility(View.VISIBLE);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (task.equals(Task.GET_DELETE_CAR_XING)) {
			try {
				JSONObject obj = new JSONObject(message);
				if (obj.getString("result").equals("001")) {
					Utils.showMessage(CarManageActivity.this, "删除成功！");
					getUserCars();
				} else {
					Utils.showMessage(CarManageActivity.this, "删除失败！错误码：" + obj.getString("result"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (task.equals(Task.USER_CAR_EDIT)) {
			{

				try {
					String result = (new JSONObject(message)).getString("result");
					if (result.equals("001")) {
						Toast.makeText(this, "编辑成功", Toast.LENGTH_SHORT).show();
						// onBackPressed();
						getUserCars();
					} else
						Toast.makeText(this, "编辑失败", Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.return_btn:
			// onBackPressed();
			setResultData();
			finish();
			break;
		// case R.id.btn_add_update:
		// Intent intent = new Intent(this, CarInfosActivity.class);
		// if (adapter.getCount() > 0) {
		// intent.putExtra("MyCarInfosModel", adapter.getItem(0));
		// }
		// startActivity(intent);
		// break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 1 && arg1 == 1) {

			getUserCars();

			Content.enter_ChaiceCarFlags = -1;
		}
		// 编辑后回到这个界面
		if (arg0 == 80 && arg1 == 1) {
			getUserCars();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResultData();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setResultData() {
		System.out.println(">>>runned");
		Intent res = new Intent();
		MyCarInfosModel defaultMode = null;
		if (data == null) {
			return;
		}
		for (MyCarInfosModel model : data) {
			if (model.isDefaultCar()) {
				defaultMode = model;
				break;
			}
		}
		res.putExtra("data", defaultMode); // 若无默认车辆则返回NULL
		setResult(RESULT_OK, res);
	}
}
// 进入到这个界面的
// 我是sortmodelSortModel [id=null, cbid=cf4f24af-0554-4185-a063-803cb78fd648,
// name=奥迪, fletter=ad, cbimage=null, type=1,
// csid=66cf5961-1015-40fc-b69a-a34dba513773, cs_name=奥迪A4L,
// cmid=65377a5e-081f-4a28-a5ba-1d80c53932a4, cm_name=2015款 35 TFSI 百万纪念智领型,
// topName=一汽-大众奥迪, SeriverTypeitemModel0=SeriverTypeitemModel [id=null,
// sctop=2, scid=1, scname=上门洗车,
// scimage=upload/img/2015/6/16/0b4f207e-8b43-4ada-ad13-c8ea8798d87f.jpg,
// scremark=随时随地，上门服务], sortLetters=null]

