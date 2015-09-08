package com.carinsurance.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbstractBaseAdapter<T> extends BaseAdapter {

	private List<T> datas;

	public AbstractBaseAdapter(List<T> datas) {
		this.datas = datas;
	}

	public AbstractBaseAdapter() {

	}

	public void setDatas(List<T> datas) {
		if (datas == null)
			return;
		this.datas = datas;
		notifyDataSetChanged();
	}

	public void addDatas(List<T> datas) {
		if (datas == null)
			return;
		datas.addAll(datas);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}
	
	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}
	
	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount();
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getAdapterViewAtPosition(position, convertView, parent);
	}

	public abstract View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent);

}
