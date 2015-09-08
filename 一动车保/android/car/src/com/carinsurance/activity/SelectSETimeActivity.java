package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.carinsurance.fragment.SelectSETimeFragment;
import com.carinsurance.fragment.SelectSETimeFragment.CalendarGridViewAdapter;
import com.carinsurance.fragment.SelectSETimeFragment.CalendarGridViewListener;
import com.carinsurance.fragment.SelectSETimeFragment.CalendarOkCancelListener;
import com.carinsurance.infos.YTime;
import com.carinsurance.infos.YTimeModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.util.LogUtils;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * 选择预约时间 2015-7-15 liqi
 * 
 * @author Administrator
 *
 */
public class SelectSETimeActivity extends BaseActivity {

	List<YTimeModel> list;
	String sid;// 街道ID
	String scid;// 服务类型ID
//	String date;// 预约时间
	FragmentManager manager;
	String[] str;
	String startTime;
	String endTime;

	public static int Result_ok = 101;
	YTime results;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_settime);
		// list = (List<YTimeModel>)
		// JumpUtils.getSerializable(SelectSETimeActivity.this);
		manager = getSupportFragmentManager();

		HashMap<String, String> params = new HashMap<String, String>();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(SelectSETimeActivity.this)) && !StringUtil.isNullOrEmpty(Utils.getToken(SelectSETimeActivity.this))) {
			params.put("uid", Utils.getUid(SelectSETimeActivity.this));
			params.put("token", Utils.getToken(SelectSETimeActivity.this));
		}

		NetUtils.getIns().post(Task.GET_SERVER_DATA, params, handler);

	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.GET_SERVER_DATA:
			try {
				list = new ArrayList<YTimeModel>();

				results= JsonUtil.getEntityByJsonString(message, YTime.class);
				if (results.getResult().equals("" + NetUtils.NET_SUCCESS_001)) {
//					list = results.getList();
					list.add(new YTimeModel("09:00", "11:00"));
					list.add(new YTimeModel("11:00", "13:00"));
					list.add(new YTimeModel("13:00", "15:00"));
					list.add(new YTimeModel("15:00", "17:00"));
					list.add(new YTimeModel("17:00", "19:00"));
					// 2015-08-19
					final String riqi = results.getDate();
					str = riqi.split("-");
					FragmentTransaction transaction = manager.beginTransaction();
					final SelectSETimeFragment fragment = new SelectSETimeFragment(list);
					Bundle bundle = new Bundle();
					bundle.putInt("year", Integer.parseInt(str[0]));
					bundle.putInt("month", Integer.parseInt(str[1]));
					bundle.putInt("day", Integer.parseInt(str[2]));
					fragment.setArguments(bundle);
					fragment.setOnCalendarItemClistener(new CalendarGridViewListener() {

						@Override
						public void ItemListener(AdapterView<?> arg0, View view, int position, long arg3, CalendarGridViewAdapter calendaradapter) {
							// TODO Auto-generated method stub
							YTimeModel yy = (YTimeModel) calendaradapter.getItem(position);
							LogUtils.d("yy=" + yy.getStime() + yy.getEtime());
							StringBuffer buff = new StringBuffer(riqi);
							buff.append(" " + yy.getStime());
							startTime = buff.toString();

							StringBuffer buff2 = new StringBuffer(riqi);
							buff2.append(" " + yy.getEtime());
							endTime = buff2.toString();

							fragment.setSelectorItem(position);
							calendaradapter.notifyDataSetChanged();
							// Content.startTime=startTime;
							// Content.endTime=endTime;
							// finish();
						}
					});
					fragment.setOnOkCancelListener(new CalendarOkCancelListener() {

						@Override
						public void ok() {
							// TODO Auto-generated method stub
							if (!StringUtil.isNullOrEmpty(startTime) && !StringUtil.isNullOrEmpty(endTime)) {
								// Content.startTime = startTime;
								// Content.endTime = endTime;
								// finish();
								Log.v("aaa","我点击了ok==》"+Result_ok);
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("startTime", startTime);
								map.put("endTime", endTime);
								map.put("date", results.getDate());
								JumpUtils.jumpResultfinish(SelectSETimeActivity.this, Result_ok, map);
							} else {
								Utils.showMessage(SelectSETimeActivity.this, "请选择一个预约的时间段！");
							}

						}

						@Override
						public void cancle() {
							// TODO Auto-generated method stub
							finish();
						}
					});

					transaction.add(R.id.ll, fragment);
					transaction.commit();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}

	}
}
