package com.carinsurance.my_view;

import com.carinsurancer.car.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActionBar extends FrameLayout implements OnMyActionBar {

	View v;
	ImageView left_img;
	ImageView right_img;
	TextView left_title;
	TextView right_title;
	TextView center_title;

	// OnActionBar
	public MyActionBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context, null);
	}

	public MyActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = LayoutInflater.from(context);
		v = inflater.inflate(R.layout.my_actionbar, null);
		left_img = (ImageView) v.findViewById(R.id.left_img);
		right_img = (ImageView) v.findViewById(R.id.right_img);
		left_title = (TextView) v.findViewById(R.id.left_title);
		right_title = (TextView) v.findViewById(R.id.right_title);
		center_title = (TextView) v.findViewById(R.id.title);
		addView(v);
	}

	@Override
	public void setLeftImage(int left_img_id) {
		// TODO Auto-generated method stub
		left_img.setImageResource(left_img_id);
		left_img.setVisibility(View.VISIBLE);
	}

	@Override
	public void setRightImage(int right_img_id) {
		// TODO Auto-generated method stub
		right_img.setImageResource(right_img_id);
		right_img.setVisibility(View.VISIBLE);
	}

	@Override
	public void setLeftTitle(String left_title_content) {
		// TODO Auto-generated method stub
		left_title.setText(left_title_content);
		left_title.setVisibility(View.VISIBLE);
	}

	@Override
	public void setTitle(String title_content) {
		// TODO Auto-generated method stub
		center_title.setText(title_content);
		center_title.setVisibility(View.VISIBLE);
	}

	@Override
	public void setRightTitle(String right_title_content) {
		// TODO Auto-generated method stub
		right_title.setText(right_title_content);
		right_title.setVisibility(View.VISIBLE);
	}

	public void setOnMyActionBarClistener(final OnMyActionBarClistener onMyActionBarClistener) {
		// TODO Auto-generated method stub
		if (onMyActionBarClistener != null) {
			left_img.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onMyActionBarClistener.setOnLeftImageClistener(v);
				}
			});
			right_img.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onMyActionBarClistener.setOnRightImageClistener(v);
				}
			});
			left_title.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onMyActionBarClistener.setOnLeftTitleClistener(v);
				}
			});
			right_title.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onMyActionBarClistener.setOnRightTitleClistener(v);
				}
			});
		}
	}

	public interface OnMyActionBarClistener {
		void setOnLeftImageClistener(View v);

		void setOnRightImageClistener(View v);

		void setOnLeftTitleClistener(View v);

		void setOnRightTitleClistener(View v);
	}
}

interface OnMyActionBar {
	void setLeftImage(int left_img);

	void setRightImage(int right_img);

	void setLeftTitle(String left_title);

	void setRightTitle(String rigth_title);

	void setTitle(String title);
}

