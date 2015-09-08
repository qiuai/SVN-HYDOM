package com.carinsurance.adapter;

import com.carinsurancer.car.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeMenDIanSpinnderAdapter extends BaseAdapter {

	Context context;

	public HomeMenDIanSpinnderAdapter(Context context) {
		this.context = context;
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 88;
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
		// if(convertView==null)
		// {
		// holder=new ViewHolder();

		convertView = inflater.inflate(R.layout.spinner_item_bg, null);
		
		TextView content=(TextView)convertView.findViewById(R.id.content);
		content.setText("ddd"+position);
		// holder.img=

		// convertView.setTag(holder);
		// }
		//
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
		TextView title;
		TextView time;
		TextView like;
	}
}

