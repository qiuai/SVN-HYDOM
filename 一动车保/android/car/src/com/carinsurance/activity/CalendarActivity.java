package com.carinsurance.activity;

import java.util.HashMap;
import java.util.List;
import com.carinsurance.fragment.CalendarFragment;
import com.carinsurance.fragment.CalendarFragment.CalendarGridViewAdapter;
import com.carinsurance.fragment.CalendarFragment.CalendarGridViewListener;
import com.carinsurance.fragment.CalendarFragment.CalendarOkCancelListener;
import com.carinsurance.infos.YTimeModel;
import com.carinsurance.utils.CalendarTools;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.util.LogUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * 日历的选择界面 2015-7-14 liqi
 * 
 * @author Administrator
 *
 */
public class CalendarActivity extends BaseActivity {

	FragmentManager manager;

	List<YTimeModel> list;

	String fuwutypeid;// 服务类型id
	String sid;// 街道id

	// @Override
	// public void initNetmessageSuccess(String message, String task) {
	// // TODO Auto-generated method stub
	// super.initNetmessageSuccess(message, task);
	// switch (task) {
	// case Task.GET_GETTIME:
	// try {
	// list = new ArrayList<YTimeModel>();
	// Results<YTimeModel> results = JsonUtil.getEntityByJsonString(message,
	// Results.class);
	// list = results.getList();
	// // JumpUtils.jumpto(CalendarActivity.this,
	// // CalendarActivity.this, (Serializable)list);
	// HashMap<String, String> map = new HashMap<String, String>();
	//
	// JumpUtils.jumpto(CalendarActivity.this, SelectSETimeActivity.class,
	// (Serializable) list, map);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// break;
	// }
	//
	// }
	String time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		manager = getSupportFragmentManager();

		fuwutypeid = JumpUtils.getString(this, "fuwutypeid");
		sid = JumpUtils.getString(this, "sid");

		FragmentTransaction transaction = manager.beginTransaction();
		final CalendarFragment c = new CalendarFragment();
		c.setOnCalendarItemClistener(new CalendarGridViewListener() {

			@Override
			public void ItemListener(AdapterView<?> arg0, View view, int position, long arg3, CalendarGridViewAdapter calendaradapter) {
				// TODO Auto-generated method stub
				Log.v("aaa", "/yijie=" + calendaradapter.getYear(position));
				Log.v("aaa", "/yijie=" + calendaradapter.getMonth(position));
				Log.v("aaa", "/yijie=" + calendaradapter.getDay(position));
				String year = calendaradapter.getYear(position);
				String month = calendaradapter.getMonth(position);
				String day = calendaradapter.getDay(position);
				CalendarTools calendarTools = new CalendarTools();
				int y = Integer.parseInt(calendarTools.getNowYear());
				int m = Integer.parseInt(calendarTools.getNowMonth());
				int d = Integer.parseInt(calendarTools.getNowDay());

				if (y > Integer.parseInt(year)) {
					Utils.showMessage(CalendarActivity.this, "过去的时间不可选！");
					return;
				}
				if (y == Integer.parseInt(year) && m > Integer.parseInt(month)) {
					Utils.showMessage(CalendarActivity.this, "过去的时间不可选！");
					return;
				}

				if (y == Integer.parseInt(year) && m == Integer.parseInt(month) && d > Integer.parseInt(day)) {
					Utils.showMessage(CalendarActivity.this, "过去的时间不可选！");
					return;
				}

				if (c.getTv_Month().getText().toString().equals(month)) {
					int mon = Integer.parseInt(month);
					int days = Integer.parseInt(day);

					String bb = calendarTools.getNewTime(mon);
					String cc = calendarTools.getNewTime(days);
					StringBuilder buff = new StringBuilder("");
					buff.append(year);
					buff.append("-");
					buff.append(bb);
					buff.append("-");
					buff.append(cc);
					Log.v("aaa", "buff" + buff.toString());
					time = buff.toString();
					// Request

					c.setSelectorItem(position);
					calendaradapter.notifyDataSetChanged();
					// JumpUtils.jumpActivityForResult(CalendarActivity.this,
					// SelectSETimeActivity.class, map, 1, false);
					// finish();
					// JumpUtils.jumpto(CalendarActivity.this,
					// SelectSETimeActivity.class, map);
				}

			}
		});
		c.setOnOkCancelListener(new CalendarOkCancelListener() {

			@Override
			public void ok() {
				// TODO Auto-generated method stub

				if (!StringUtil.isNullOrEmpty(time)) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("fuwutypeid", fuwutypeid);
					map.put("sid", sid);
					map.put("time", time);
					JumpUtils.jumpActivityForResult(CalendarActivity.this, SelectSETimeActivity.class, map, 1, false);
					finish();
				} else {
					Utils.showMessage(CalendarActivity.this, "请选择时间！");
				}

			}

			@Override
			public void cancle() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		transaction.add(R.id.ll, c);
		transaction.commit();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		LogUtils.d("还会回到这？" + arg0 + "//" + arg1);
		Log.v("aaa", "还会回到这？" + arg0 + "//" + arg1);
	}
}
