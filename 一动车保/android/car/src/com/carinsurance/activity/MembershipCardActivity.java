package com.carinsurance.activity;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.CouponItemModel;
import com.carinsurance.infos.CouponModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MembershipCardActivity extends BaseActivity {

	@ViewInject(R.id.myListView)
	ListView myListView;

	// String pid;
	// String money;
	// String otype;
	@ViewInject(R.id.return_btn)
	ImageView return_btn;
	List<CouponItemModel> list;
	@ViewInject(R.id.title)
	TextView title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chooseacoupon);
		ViewUtils.inject(this);
		title.setText("会员卡");
		getMembershipCardList();
	}

	public void getMembershipCardList() {
		// GET_COUPON_LIST
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(MembershipCardActivity.this));
		map.put("token", Utils.getToken(MembershipCardActivity.this));
		NetUtils.getIns().post(Task.GET_COUPONPAC_List, map, handler);
	}

	@OnClick(R.id.return_btn)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(MembershipCardActivity.this);
			break;
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);

		if (task.equals(Task.GET_COUPONPAC_List)) {
			try {
				String result = (new JSONObject(message)).getString("result");
				if (result.equals("001")) {
					// Toast.makeText(this, "提交成功，谢谢你的反馈!",
					// Toast.LENGTH_SHORT).show();
					// onBackPressed();
					CouponModel model = JsonUtil.getEntityByJsonString(message, CouponModel.class);
					list = model.getList();

					initListView();
				} else {
					Utils.showMessage(MembershipCardActivity.this, "系统错误，错误码:" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void initListView() {
		// TODO Auto-generated method stub
		myListView.setDivider(null);
		myListView.setAdapter(new AbstractBaseAdapter<CouponItemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = MembershipCardActivity.this.getLayoutInflater().inflate(R.layout.select_coupon_item, null);
				}
				final CouponItemModel model = getItem(position);

				ImageView img = ViewHolder.get(convertView, R.id.img);
				// FrameLayout iv_use = ViewHolder.get(convertView,
				// R.id.iv_use);

				LinearLayout ll = ViewHolder.get(convertView, R.id.ll);
				ll.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						JumpUtils.jumpResultfinish(MembershipCardActivity.this, RESULT_OK, model);
						JumpUtils.jumpto(MembershipCardActivity.this, MembershipCardBuyActivity.class, model);
						
					}
				});
				new xUtilsImageLoader(MembershipCardActivity.this).display(img, model.getCppimg());

				return convertView;
			}
		});
	}

}
