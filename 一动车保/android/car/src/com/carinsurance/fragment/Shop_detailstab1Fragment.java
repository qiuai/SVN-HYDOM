package com.carinsurance.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.carinsurance.adapter.Shop_detailstab1_adapter;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class Shop_detailstab1Fragment extends Fragment{
	@ViewInject(R.id.myListView)
	ListView myListView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.shop_details_tab1, null);
		init(view);
		return view;
	}
	private void init(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this, view);
		myListView.setAdapter(new Shop_detailstab1_adapter(getActivity()));
	}

}
