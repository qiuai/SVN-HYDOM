package com.carinsurance.adapter;

import com.carinsurancer.car.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CarServiceAdapter extends BaseAdapter implements OnClickListener{

	private LayoutInflater inflater;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
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
	public View getView(int position, View rootView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(rootView == null){
			rootView = inflater.inflate(R.layout.car_service_list, null);
		}
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.in_the_spark_plug:
			
			break;
		}
	}
	
	

}
