package com.carinsurance.activity;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.CouponItemModel;
import com.carinsurance.infos.CouponModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.Dialog;
import com.carinsurance.utils.Dialog.DialogClistener;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyPointsActivity extends BaseActivity {

	@ViewInject(R.id.tv_jifen)
	TextView tv_jifen;

	CouponModel couponModel;
	List<CouponItemModel> list;
	@ViewInject(R.id.myListView)
	ListView myListView;

	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_points);
		ViewUtils.inject(this);
		getCouponList();
		dialog = new Dialog();
	}

	/**
	 * 获取列表和个人积分
	 */
	private void getCouponList() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(MyPointsActivity.this));
		map.put("token", Utils.getToken(MyPointsActivity.this));
		NetUtils.getIns().post(Task.MY_COUPON, map, handler);
	}

	@OnClick(R.id.return_btn)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(MyPointsActivity.this);
			break;
		}
	}

	/**
	 * 获取优惠卷
	 */
	private void getCoupon(String cpid) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(MyPointsActivity.this));
		map.put("token", Utils.getToken(MyPointsActivity.this));
		map.put("cpid", cpid);

		NetUtils.getIns().post(Task.GET_COUPON, map, handler);
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		if (task.equals(Task.MY_COUPON)) {
			try {
				String results = message;
				Log.v("aaa", "运行到了这3");
				String result = new JSONObject(results).getString("result");
				if (result.equals("001")) {

					couponModel = JsonUtil.getEntityByJsonString(results, CouponModel.class);
					list = couponModel.getList();
					initView();
				} else {
					Utils.showMessage(MyPointsActivity.this, "服务器错误，错误码:" + result);
				}
			} catch (Exception e) {
			}
		} else if (task.equals(Task.GET_COUPON)) {
			try {
				String results = message;
				// Log.v("aaa", "运行到了这3");
				String result = new JSONObject(results).getString("result");
				if (result.equals("001")) {

					// couponModel = JsonUtil.getEntityByJsonString(results,
					// CouponModel.class);
					// list = couponModel.getList();
					// initView();
					Utils.showMessage(MyPointsActivity.this, "兑换成功！");
					getCouponList();
				} else {
					Utils.showMessage(MyPointsActivity.this, "服务器错误，错误码:" + result);
				}
			} catch (Exception e) {
			}
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_jifen.setText("" + couponModel.getScore() + "分");
		myListView.setDividerHeight((int) DisplayUtil.getDip(MyPointsActivity.this, 1));
		myListView.setDivider(null);
		myListView.setAdapter(new AbstractBaseAdapter<CouponItemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = MyPointsActivity.this.getLayoutInflater().inflate(R.layout.duihuan_coupon_item, null);
				}
				final CouponItemModel c = getItem(position);
				ImageView iv = ViewHolder.get(convertView, R.id.imageView1);

				new xUtilsImageLoader(MyPointsActivity.this).display(iv, c.getCpimg());

				iv.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Double d = null;
						Double x = null;
						try {
							d = Double.parseDouble(couponModel.getScore());
							x = Double.parseDouble(c.getCpexscore());
						} catch (Exception e) {
							// TODO: handle exception
							Utils.showMessage(MyPointsActivity.this, "网络错误，请重试！");
							return;
						}

						if (x > d) {
							Utils.showMessage(MyPointsActivity.this, "积分不足！");
							return;
						}

						dialog.CreateDialog(MyPointsActivity.this, "", "你当前的积分是" + couponModel.getScore() + ",兑换该优惠卷需要" + c.getCpexscore() + "积分,是否兑换？");
						dialog.setOnDialogClistener(new DialogClistener() {

							@Override
							public void ret() {
								// TODO Auto-generated method stub

							}

							@Override
							public void ok() {
								// TODO Auto-generated method stub
								getCoupon(c.getCpid());
							}
						});
					}
				});

				return convertView;
			}
		});
	}
}
