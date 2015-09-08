package com.carinsurance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.carinsurancer.car.R;

public class Shop_detailstab2_adapter extends BaseAdapter {

	Context context;

	public Shop_detailstab2_adapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
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

			convertView = inflater.inflate(R.layout.shop_details_tab2_adapter_item, null);
			// holder.img=
//			holder.jump=(FrameLayout)convertView.findViewById(R.id.jump);
//			holder.img=(ImageView)convertView.findViewById(R.id.img);
//			holder.is_like=(ImageView)convertView.findViewById(R.id.is_like);
//			holder.title=(TextView)convertView.findViewById(R.id.title);
//			holder.likenum=(TextView)convertView.findViewById(R.id.likenum);
//			holder.time=(TextView)convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		}
		holder=(ViewHolder)convertView.getTag();
//		holder.is_like.setSelected(false);
////		new BitmapHelp().displayImage(context, holder.img, url);
//		
//		holder.jump.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent=new Intent(context,Find_DetailsActivity.class);
//				context.startActivity(intent);
//				((Activity)(context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
//			}
//		});
		//
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
		TextView title;
		TextView time;
		TextView likenum;
		ImageView is_like;
		FrameLayout jump;
	}
}