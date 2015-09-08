package com.carinsurance.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carinsurance.activity.AboutActivity;
import com.carinsurance.activity.CarManageActivity;
import com.carinsurance.activity.FeedbackActivity;
import com.carinsurance.activity.MyCenterActivity;
import com.carinsurance.activity.MyCouponActivity;
import com.carinsurance.activity.MyOrderActivity;
import com.carinsurance.activity.TheBalanceOfActivity;
import com.carinsurance.infos.UserModel;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurancer.car.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class HomePageWode3Adapter extends BaseAdapter {

	Context context;

	UserModel user;
	public ImageLoader imageLoader;

	public HomePageWode3Adapter(Context context, UserModel user) {
		this.context = context;
		this.user = user;
		imageLoader = ImageLoader.getInstance();

		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.homepage_center3, null);
			holder.l0 = (LinearLayout) convertView.findViewById(R.id.l0);
			holder.l1 = (LinearLayout) convertView.findViewById(R.id.l1);
			holder.l2 = (LinearLayout) convertView.findViewById(R.id.l2);
			holder.l3 = (LinearLayout) convertView.findViewById(R.id.l3);
			holder.l4 = (LinearLayout) convertView.findViewById(R.id.l4);
			holder.l5 = (LinearLayout) convertView.findViewById(R.id.l5);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.circleImageView = (ImageView) convertView.findViewById(R.id.photo);
			holder.setting = (ImageView) convertView.findViewById(R.id.setting);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		holder.setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nickName = user.getNickname();
				String photo = user.getPhoto();

				HashMap<String, String> param = new HashMap<>();
				param.put("name", nickName);
				param.put("photo", photo);

				JumpUtils.jumpActivityForResult(context, MyCenterActivity.class, param, 1);// (context,,
																							// null);
			}
		});
		if (user != null) {

			if (!StringUtil.isNullOrEmpty(user.getPhoto()))
			ImageLoader.getInstance().displayImage(Task.img_url + user.getPhoto(), holder.circleImageView);
			holder.name.setText("" + user.getNickname());
		}
		holder.l0.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpto(context, MyOrderActivity.class, null);
			}
		});
		holder.l1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpto(context, CarManageActivity.class, null);
			}
		});
		holder.l2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpto(context, TheBalanceOfActivity.class, null);
			}
		});
		holder.l3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpto(context, MyCouponActivity.class, null);
			}
		});
		holder.l4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpto(context, FeedbackActivity.class, null);
			}
		});
		holder.l5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpto(context, AboutActivity.class, null);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		public ImageView setting;
		public TextView name;
		public ImageView circleImageView;
		LinearLayout l0, l1, l2, l3, l4, l5;

	}
}