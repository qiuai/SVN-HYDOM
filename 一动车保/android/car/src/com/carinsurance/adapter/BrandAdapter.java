package com.carinsurance.adapter;


import com.carinsurance.activity.BrandActivity;
import com.carinsurance.activity.ProductDetailsActivity;
import com.carinsurancer.car.R;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BrandAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private BrandActivity mContext;
	
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
		return 0;
	}

	@Override
	public View getView(int position, View rootView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(rootView == null){
			rootView = inflater.inflate(R.layout.brand_layouts, null);
		}
		
		rootView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mContext.startActivity(new Intent(mContext,ProductDetailsActivity.class));
			}
		});
		
		return rootView;
	}
	

}
