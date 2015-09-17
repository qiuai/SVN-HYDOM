package com.carinsurance.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.fragment.SelectSETimeFragment;
import com.carinsurance.fragment.SelectSETimeFragment.CalendarGridViewAdapter;
import com.carinsurance.fragment.SelectSETimeFragment.CalendarGridViewListener;
import com.carinsurance.fragment.SelectSETimeFragment.CalendarOkCancelListener;
import com.carinsurance.infos.CouponItemModel;
import com.carinsurance.infos.CouponModel;
import com.carinsurance.infos.YTime;
import com.carinsurance.infos.YTimeModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.CalendarTools;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择日期的activity
 * 
 * @author Administrator
 *
 */
public class ChooseDataActivity extends BaseActivity {

	@ViewInject(R.id.myListView)
	ListView myListView;

	// String pid;
	// String money;
	// String otype;
	@ViewInject(R.id.return_btn)
	ImageView return_btn;
	// List<CouponItemModel> list;
	@ViewInject(R.id.title)
	TextView title;
	ArrayList<YTimeModel> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chooseacoupon);
		ViewUtils.inject(this);
		title.setText("选择预约日期");
		HashMap<String, String> params = new HashMap<String, String>();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(ChooseDataActivity.this)) && !StringUtil.isNullOrEmpty(Utils.getToken(ChooseDataActivity.this))) {
			params.put("uid", Utils.getUid(ChooseDataActivity.this));
			params.put("token", Utils.getToken(ChooseDataActivity.this));
		}

		NetUtils.getIns().post(Task.GET_SERVER_DATA, params, handler);

		getMembershipCardList();
	}

	public void getMembershipCardList() {
		// GET_COUPON_LIST
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(ChooseDataActivity.this));
		map.put("token", Utils.getToken(ChooseDataActivity.this));
		NetUtils.getIns().post(Task.GET_COUPONPAC_List, map, handler);
	}

	@OnClick(R.id.return_btn)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(ChooseDataActivity.this);
			break;
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);

		if (task.equals(Task.GET_SERVER_DATA)) {
			try {
				list = new ArrayList<YTimeModel>();

				YTime results = JsonUtil.getEntityByJsonString(message, YTime.class);
				if (results.getResult().equals("" + NetUtils.NET_SUCCESS_001)) {
					// list = results.getList();
					final String riqi = results.getDate();
					list.add(new YTimeModel(riqi));

					String[] ri = riqi.split("-");
					Calendar calendar = Calendar.getInstance();
					calendar.set(Integer.parseInt(ri[0]), (Integer.parseInt(ri[1]) - 1), Integer.parseInt(ri[2]));
					// Log.v("aaa", "sss===>" +
					// CalendarTools.getFormatTime(calendar.getTimeInMillis(),
					// "yyyy-MM-dd"));
					/**
					 * 
					 */
					for (int i = 0; i < 6; i++) {
						// SimpleDateFormat simple1=new
						// SimpleDateFormat("yyyy-MM-dd");

						Date pp = CalendarTools.addDays(calendar.getTime(), (i + 1));

						pp.getTime();

						String d1 = CalendarTools.getFormatTime(pp.getTime(), "yyyy-MM-dd");
						list.add(new YTimeModel(d1));
						// Log.v("aaa",""+);
					}
					// list.add(new YTimeModel("09:00", "11:00"));
					// list.add(new YTimeModel("11:00", "13:00"));
					// list.add(new YTimeModel("13:00", "15:00"));
					// list.add(new YTimeModel("15:00", "17:00"));
					// list.add(new YTimeModel("17:00", "19:00"));
					// 2015-08-19

					initListView();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void initListView() {
		// TODO Auto-generated method stub
		myListView.setDivider(getResources().getDrawable(R.color.bj_f0f0f0));
		myListView.setDividerHeight((int) DisplayUtil.getDip(ChooseDataActivity.this, 1));
		myListView.setAdapter(new AbstractBaseAdapter<YTimeModel>(list) {

			@Override
			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = ChooseDataActivity.this.getLayoutInflater().inflate(R.layout.choose_data_item, null);
				}
				final YTimeModel ymodel = getItem(position);

				TextView data = ViewHolder.get(convertView, R.id.data);
				// FrameLayout iv_use = ViewHolder.get(convertView,
				// R.id.iv_use);
				data.setText(ymodel.getYuyueyear_month_day());
				LinearLayout ll = ViewHolder.get(convertView, R.id.ll);
				ll.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// JumpUtils.jumpResultfinish(MembershipCardActivity.this,
						// RESULT_OK, model);
						// JumpUtils.jumpto(ChooseDataActivity.this,
						// MembershipCardBuyActivity.class, ymodel);
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("date", ymodel.getYuyueyear_month_day());
						// JumpUtils.jumpto(ChooseDataActivity.this,
						// SelectSETimeActivity.class, null);
						JumpUtils.jumpActivityForResult(ChooseDataActivity.this, SelectSETimeActivity.class, map, 1, false);
					}
				});
				// new xUtilsImageLoader(ChooseDataActivity.this).display(img,
				// ymodel.getStime());

				return convertView;
			}
		});
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 1 && arg1 == SelectSETimeActivity.Result_ok) {
			setResult(SelectSETimeActivity.Result_ok, arg2);
			finish();
		}

	}

}
