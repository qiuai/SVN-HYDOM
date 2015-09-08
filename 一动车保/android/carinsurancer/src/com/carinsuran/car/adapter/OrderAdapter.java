package com.carinsuran.car.adapter;

import java.util.List;

import com.carinsuran.car.R;
import com.carinsuran.car.model.Orderbussion;
import com.carinsuran.car.ui.DetailsActivity;
import com.carinsuran.car.ui.OrderManagementActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter implements OnClickListener{

	private LayoutInflater inflater;
	private OrderManagementActivity mContext;
	private List<Orderbussion> list;
	
	public OrderAdapter(OrderManagementActivity mContext,List<Orderbussion> list) {
		// TODO Auto-generated constructor stub
		super();
		this.inflater = LayoutInflater.from(mContext);
		this.mContext = mContext;
		this.list = list;
	}

	public void addMoreMainItem(List<Orderbussion> paramlist) {
		// TODO Auto-generated method stub
		for (Orderbussion param : paramlist) {
			this.list.add(param);
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View rootView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Orderbussion item = list.get(position);
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.near_list_item, null);
		}
		TextView the_order_no = (TextView) rootView.findViewById(R.id.the_order_no);
		the_order_no.setText(item.getOrdernum());
		
		TextView order_state = (TextView) rootView.findViewById(R.id.order_state);
		if(item.getOrderState() == 2){
			order_state.setText("(已接受)");
		}else if(item.getOrderState() == 3){
			order_state.setText("(已拒绝)");
		}
		
		TextView service_f = (TextView) rootView.findViewById(R.id.service_f);
		if(item.getCleanType() == 1){
			service_f.setText("清洗车身");
		}else if(item.getCleanType() == 2){
			service_f.setText("内外清洗");
		}
		TextView service_time = (TextView) rootView.findViewById(R.id.service_time);
		service_time.setText(item.getStartDate());
		
		setListener(rootView, item);
		return rootView;
	}

	
	private void setListener(View rootView, Orderbussion item) {
		// TODO Auto-generated method stub
		View detail = rootView.findViewById(R.id.bussion_on);
		detail.setOnClickListener(this);
		detail.setTag(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Orderbussion orderbussion = (Orderbussion) v.getTag();
		switch (v.getId()) {
		case R.id.bussion_on:
			this.mContext.startActivity(new Intent(this.mContext, DetailsActivity.class).putExtra(
					"orderId", orderbussion.getOrderId()));
			break;
		}
	}


}
