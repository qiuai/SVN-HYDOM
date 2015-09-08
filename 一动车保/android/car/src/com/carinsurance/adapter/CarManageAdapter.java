package com.carinsurance.adapter;

import com.carinsurance.activity.CarInfosActivity;
import com.carinsurance.infos.MyCarInfosModel;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarManageAdapter extends AbstractBaseAdapter<MyCarInfosModel> {
	OnClickItemDataClistenr onClickItemDataClistenr;
	private LayoutInflater inflater = null;

	Context context;

	public int select_position = -1;

	public CarManageAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View getAdapterViewAtPosition(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder h = null;
		if (convertView == null) {
			h = new Holder();
			convertView = inflater.inflate(R.layout.listitem_car_manage_layout, null);
			h.cbicon = (ImageView) convertView.findViewById(R.id.car_cbicon);
			h.cbname = (TextView) convertView.findViewById(R.id.car_cbname_csname);
			h.cmname = (TextView) convertView.findViewById(R.id.car_cmname);
			h.date = (TextView) convertView.findViewById(R.id.car_date);
			h.engines = (TextView) convertView.findViewById(R.id.car_engines);
			h.drange = (TextView) convertView.findViewById(R.id.car_drange);
			h.fuel = (TextView) convertView.findViewById(R.id.car_fuel);
			h.car_card = (TextView) convertView.findViewById(R.id.car_card);
			h.car_color = (TextView) convertView.findViewById(R.id.car_color);
			h.btn_update = (Button) convertView.findViewById(R.id.btn_update);
			h.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
			h.iv_set_moren = (ImageView) convertView.findViewById(R.id.iv_set_moren);
			h.set_moren = (LinearLayout) convertView.findViewById(R.id.set_moren);
			convertView.setTag(h);
		}
		h = (Holder) convertView.getTag();
		final MyCarInfosModel ci = getItem(position);

		if (ci.getDefaultCar().equals("1"))
			h.iv_set_moren.setImageResource(R.drawable.gougou_select_x);
		else {
			h.iv_set_moren.setImageResource(R.drawable.gougou_noselect_x);
		}
		
		new xUtilsImageLoader(context).display(h.cbicon, ci.getCbimage());
//		h.cbicon
		h.cbname.setText("" + ci.getCbname());
		if (!TextUtils.isEmpty(ci.getCsname()))
			h.cbname.setText(ci.getCbname() + "-" + ci.getCsname());
		h.cmname.setText("" + ci.getCmname());
		h.date.setText("" + ci.getDate());
		h.engines.setText("" + ci.getEngines());
		h.drange.setText("" + ci.getDrange());
		h.fuel.setText("" + ci.getFuel());
		h.car_card.setText("" + ci.getPlateNumber());
		h.car_color.setText("" + ci.getColor());

		h.set_moren.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onClickItemDataClistenr != null) {
					int def=-1;
					if(ci.getDefaultCar().equals("1"))
					{
						def=0;
					}else
					{
						def=1;
					}
					onClickItemDataClistenr.selectMoren(ci,position,def);
				}
			}
		});
		
		h.btn_delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onClickItemDataClistenr != null) {
					onClickItemDataClistenr.Delete(position);
				}

			}
		});
		h.btn_update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, CarInfosActivity.class);

				intent.putExtra("MyCarInfosModel", getItem(position));

				((Activity) (context)).startActivityForResult(intent, 80);
				((Activity) (context)).overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
				// JumpUtils.jumpto(context, CarInfosActivity.class, ci);
			}
		});
		return convertView;
	}

	public void setOnClickItemDateClistener(OnClickItemDataClistenr onClickItemDataClistenr) {
		this.onClickItemDataClistenr = onClickItemDataClistenr;
	}

	public interface OnClickItemDataClistenr {
		void Delete(int position);
		void selectMoren(MyCarInfosModel modle,int position,int defaultCar);
	}

	private class Holder {
		public LinearLayout set_moren;
		public ImageView iv_set_moren;
		public Button btn_delete;
		public TextView car_color;
		public TextView car_card;
		private TextView cbname, cmname, date, engines, drange, fuel;
		private ImageView cbicon;
		Button btn_update;
	}
}
