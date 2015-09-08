package com.carinsurance.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.infos.Content;
import com.carinsurance.infos.MyCarInfosModel;
import com.carinsurance.infos.SeriverTypeitemModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;

public class CarInfosActivity extends BaseActivity implements OnClickListener {
	private TextView cbname, date;
	ImageView cbimg;
	private EditText drange, fuel, engines, car_card, car_color;
	private String cmid;
	private ProgressDialog loadingDialog = null;

	private SortModel sortModel;

	int type = 0;// 0、从主页洗车选择车型跳过来的,需要跳到服务订单界面(为保存操作) 1、用户编辑车型 2\(直接在车管家中)用户新增车型
					// 3\是保养流程
					// 4是保养（单商品流程）
	String ucid;// 用户车辆id
	MyCarInfosModel module;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_infos);

		cbimg = (ImageView) findViewById(R.id.cbimg);
		cbname = (TextView) findViewById(R.id.car_cbname_csname);
		date = (TextView) findViewById(R.id.car_date);
		drange = (EditText) findViewById(R.id.car_drange);
		fuel = (EditText) findViewById(R.id.car_fuel);
		engines = (EditText) findViewById(R.id.car_engines);
		car_card = (EditText) findViewById(R.id.car_card);
		car_color = (EditText) findViewById(R.id.car_color);

		findViewById(R.id.btn_save).setOnClickListener(this);
		findViewById(R.id.return_btn).setOnClickListener(this);
		cbname.setOnClickListener(this);
		date.setOnClickListener(this);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date.setText(sdf.format(new Date()));
		try {
			sortModel = (SortModel) JumpUtils.getSerializable(CarInfosActivity.this);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// type = 0;
		if (sortModel != null) {
			if (Content.enter_ChaiceCarFlags == 0 || Content.enter_ChaiceCarFlags == 2 || Content.enter_ChaiceCarFlags == 3 || Content.enter_ChaiceCarFlags == 4 || Content.enter_ChaiceCarFlags == 5 || Content.enter_ChaiceCarFlags == 6) {
				cmid = sortModel.getCmid();
				type = 0;
				cbname.setText("" + sortModel.getName() + sortModel.getCs_name());
				new xUtilsImageLoader(CarInfosActivity.this).display(cbimg, sortModel.getCbimage());
			}
		}

		// type==2的时候 用户新增车型
		if (Content.enter_ChaiceCarFlags == 2) {
			type = 2;
		}
		if (Content.enter_ChaiceCarFlags == 3) {
			type = 3;
		}
		if (Content.enter_ChaiceCarFlags == 4) {
			type = 4;
		}
		if (Content.enter_ChaiceCarFlags == 5) {
			type = 5;
		}
		if (Content.enter_ChaiceCarFlags == 6) {
			type = 6;
		}
		// type==1的时候
		module = (MyCarInfosModel) getIntent().getSerializableExtra("MyCarInfosModel");
		setCarInfos(module);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (date == v) {
			showDateTimeDialog(date.getText().toString());
		} else if (v == cbname) {
			HashMap<String, String> map1 = new HashMap<String, String>();
			// map1.put("fuwutypeid", updingdan.getScid());
			Content.enter_ch = 1;
			Content.sortModel = null;
			JumpUtils.jumpActivityForResult(CarInfosActivity.this, ChoiceCarActivity.class, map1, 3);

		} else if (v.getId() == R.id.btn_save) {

			saveCar();
			// // 选择汽车服务
			// Intent intent = new Intent(CarInfosActivity.this,
			// CarServiceActivity.class);
			// startActivity(intent);

		} else if (v.getId() == R.id.return_btn)
			onBackPressed();
	}

	private void setCarInfos(MyCarInfosModel module) {
		if (module != null) {
			type = 1;
			date.setText(module.getDate());
			drange.setText(module.getDrange());
			engines.setText(module.getEngines());
			fuel.setText(module.getFuel());

			setCarNames(module.getCbname(), module.getCsname(), module.getCmname());

			car_card.setText(module.getPlateNumber());
			car_color.setText(module.getColor());
			cmid = module.getCmid();
		}
	}

	private void setCarNames(String cbname, String csname, String cmname) {
		// SpannableStringBuilder strBuilder = new
		// SpannableStringBuilder(cbname);
		// strBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
		// strBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// if (!TextUtils.isEmpty(csname)) {
		// strBuilder.append("\n");
		// strBuilder.append(csname);
		// if (!TextUtils.isEmpty(cmname)) {
		// strBuilder.append("-");
		// strBuilder.append(cmname);
		// }
		// strBuilder.setSpan(new
		// ForegroundColorSpan(getResources().getColor(R.color.text_color_1)),
		// cbname.length(), strBuilder.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// strBuilder.setSpan(new AbsoluteSizeSpan(20), cbname.length(),
		// strBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// }
		// this.cbname.setText(strBuilder);
		this.cbname.setText("" + cbname + " " + csname);
		// Content.sortModel.getName() + Content.sortModel.getCs_name()
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 3 && arg1 == 0) {
			if (Content.sortModel != null) {
				// Log.v("aaa", "--------------->" +
				// Content.sortModel.toString());
				Log.v("aaa", "Con===>" + Content.sortModel.toString());
				cbname.setText("" + Content.sortModel.getName() + Content.sortModel.getCs_name());
				cmid = Content.sortModel.getCmid();
				if (sortModel != null)
					sortModel.setCmid(Content.sortModel.getCmid());
				// setCarNames(Content.sortModel.getName(),
				// Content.sortModel.getCs_name(),
				// Content.sortModel.getCm_name());
			}
			Content.sortModel = null;
			Content.enter_ch = 0;
		}
	}

	private void showDateTimeDialog(String dateStr) {
		int year = 0, month = 0, day = 0;
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			d = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			d = new Date();
		}
		Calendar c = Calendar.getInstance();
		d.setTime(d.getTime());
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
			}
		}, year, month, day);
		datePickerDialog.show();
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		if (task.equals(Task.USER_CAR_EDIT)) {
			Log.i("", "result=" + message);

			if (loadingDialog.isShowing()) {
				loadingDialog.dismiss();
				try {
					String result = (new JSONObject(message)).getString("result");
					if (result.equals("001")) {
						Toast.makeText(this, "编辑成功", Toast.LENGTH_SHORT).show();
						// onBackPressed();
						setResult(1);

						JumpUtils.jumpfinish(CarInfosActivity.this);
					} else
						Toast.makeText(this, "编辑失败", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else if (task.equals(Task.GET_CARSAVE))// 保存车型操作
		{
			Utils.ExitPrgress(CarInfosActivity.this);
			try {
				String result = (new JSONObject(message)).getString("result");
				if (result.equals("001")) {
					Utils.showMessage(CarInfosActivity.this, "保存成功");
					ucid = (new JSONObject(message)).getString("ucid");
					if (type == 0) {
						// 如果是从选择车型那边进来，跳转到服务订单
						sortModel.setUcid(ucid);
						sortModel.setColor(car_color.getText().toString().trim());
						sortModel.setPlateNumber(car_card.getText().toString().trim());
						try {
							closeActivity(1, 2, 3);
						} catch (Exception e) {
							// TODO: handle exception
						}
						JumpUtils.jumpto(CarInfosActivity.this, ServiceOrderActivity.class, sortModel);

					} else if (type == 2) {
						Log.v("aaa", "保存成功231");
						try {
							List<Activity> a = getActivities();
							closeActivity((a.size() - 2), (a.size() - 3), (a.size() - 4));
						} catch (Exception e) {
							// TODO: handle exception
						}

						JumpUtils.jumpResultfinish(CarInfosActivity.this, 1, null, true);
					} else if (type == 3) {
						sortModel.setUcid(ucid);
						sortModel.setColor(car_color.getText().toString().trim());
						sortModel.setPlateNumber(car_card.getText().toString().trim());
						try {
							closeActivity(1, 2, 3);
						} catch (Exception e) {
							// TODO: handle exception
						}
						JumpUtils.jumpto(CarInfosActivity.this, CarServiceActivity.class, sortModel, true);
					} else if (type == 4) {
						sortModel.setUcid(ucid);
						sortModel.setColor(car_color.getText().toString().trim());
						sortModel.setPlateNumber(car_card.getText().toString().trim());
						try {
							closeActivity(1, 2, 3);
						} catch (Exception e) {
							// TODO: handle exception
						}
						List<SeriverTypeitemModel> ser = new ArrayList<SeriverTypeitemModel>();
						ser.add(sortModel.getSeriverTypeitemModel0());
						sortModel.setSelectSeriverTypeitemList(ser);
						JumpUtils.jumpto(CarInfosActivity.this, SelectServer1Activity.class, sortModel, true);
					} else if (type == 5) {
						sortModel.setUcid(ucid);
						sortModel.setColor(car_color.getText().toString().trim());
						sortModel.setPlateNumber(car_card.getText().toString().trim());
						try {
							closeActivity(1, 2, 3);
						} catch (Exception e) {
							// TODO: handle exception
						}
						// List<SeriverTypeitemModel> ser = new
						// ArrayList<SeriverTypeitemModel>();
						// ser.add(sortModel.getSeriverTypeitemModel0());

						HashMap<String, String> map = new HashMap<String, String>();

						map.put("id", sortModel.getPci_id());
						map.put("name", sortModel.getPci_name());

						JumpUtils.jumpto(CarInfosActivity.this, HotCategoryListActivity.class, sortModel, map);
					} else if (type == 6) {
						sortModel.setUcid(ucid);
						sortModel.setColor(car_color.getText().toString().trim());
						sortModel.setPlateNumber(car_card.getText().toString().trim());
						try {
							closeActivity(1, 2, 3);
						} catch (Exception e) {
							// TODO: handle exception
						}
						// List<SeriverTypeitemModel> ser = new
						// ArrayList<SeriverTypeitemModel>();
						// ser.add(sortModel.getSeriverTypeitemModel0());

						JumpUtils.jumpto(CarInfosActivity.this, ClassificationOfGoodsActivity.class, sortModel, null);
					}
				} else {
					Utils.showMessage(CarInfosActivity.this, "保存失败" + result);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void saveCar() {

		Log.v("aaa", "type====>" + type);
		if (StringUtil.isNullOrEmpty(cmid)) {
			Utils.showMessage(CarInfosActivity.this, "请选择车型！");
			return;
		}
		if (StringUtil.isNullOrEmpty(car_color.getText().toString().trim())) {
			Utils.showMessage(CarInfosActivity.this, "请填写车的颜色！");
			return;
		}
		if (StringUtil.isNullOrEmpty(car_card.getText().toString().trim())) {
			Utils.showMessage(CarInfosActivity.this, "请填写车牌号！");
			return;
		}
//		if (StringUtil.isNullOrEmpty(fuel.getText().toString().trim())) {
//			Utils.showMessage(CarInfosActivity.this, "请将信息填写完整！");
//			return;
//		}
//		if (StringUtil.isNullOrEmpty(drange.getText().toString().trim())) {
//			Utils.showMessage(CarInfosActivity.this, "请将信息填写完整！");
//			return;
//		}
//		if (StringUtil.isNullOrEmpty(engines.getText().toString().trim())) {
//			Utils.showMessage(CarInfosActivity.this, "请将信息填写完整！");
//			return;
//		}
		if (StringUtil.isNullOrEmpty(date.getText().toString().trim())) {
			Utils.showMessage(CarInfosActivity.this, "请将信息填写完整！");
			return;
		}
		// type==1的时候是编辑
		if (type == 1) {

			if (TextUtils.isEmpty(cmid)) {
				Toast.makeText(this, "请选择车型！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (loadingDialog == null) {
				loadingDialog = new ProgressDialog(this);
				loadingDialog.setCanceledOnTouchOutside(false);
			}
			loadingDialog.show();
			HashMap<String, String> rp = new HashMap<String, String>();
			rp.put("uid", Utils.getUid(this));
			rp.put("token", Utils.getToken(this));
			rp.put("ucid", module.getUcid());
			rp.put("defaultCar", module.getDefaultCar());

			rp.put("color", car_color.getText().toString().trim());
			rp.put("plateNumber", car_card.getText().toString().trim());

			rp.put("fuel", fuel.getText().toString().trim());
			rp.put("drange", drange.getText().toString().trim());
			rp.put("engines", engines.getText().toString().trim());
			rp.put("date", date.getText().toString().trim());
			rp.put("cmid", cmid);
			NetUtils.getIns().post(Task.USER_CAR_EDIT, rp, this.handler);
		} else if (type == 0 || type == 2 || type == 3 || type == 4 || type == 5 || type == 6) {// 保存
			Utils.showPrgress(CarInfosActivity.this);
			HashMap<String, String> rp = new HashMap<String, String>();
			rp.put("uid", Utils.getUid(this));
			rp.put("token", Utils.getToken(this));
			rp.put("defaultCar", "1");// 1就是设为默认
			rp.put("color", car_color.getText().toString().trim());// 颜色
			rp.put("plateNumber", car_card.getText().toString().trim());// 车牌号
			rp.put("fuel", fuel.getText().toString().trim());
			rp.put("drange", drange.getText().toString().trim());
			rp.put("engines", engines.getText().toString().trim());
			rp.put("date", date.getText().toString().trim());
			rp.put("cmid", cmid);
			// Log.v("aaa", "我要提交的数据是" + rp.toString());
			NetUtils.getIns().post(Task.GET_CARSAVE, rp, this.handler);
		}

	}
}
