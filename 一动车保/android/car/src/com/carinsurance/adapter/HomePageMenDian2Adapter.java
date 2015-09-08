package com.carinsurance.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carinsurance.infos.MendianModel;
import com.carinsurancer.car.R;

public class HomePageMenDian2Adapter extends BaseAdapter {

	Context context;
//	MendianListModel listModel;
	List<MendianModel> listModel;
	public HomePageMenDian2Adapter(Context context,List<MendianModel> listModel) {
		this.context = context;
//		this.listModel=listModel;
		this.listModel=listModel;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(listModel==null)
		{
			return 10;
		}
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.homepage2adapter_item, null);
			holder.img=(ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
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
