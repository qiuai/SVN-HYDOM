package com.carinsuran.car.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.carinsuran.car.R;
import com.carinsuran.car.config.HttpUrl;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Pic1Activity extends BaseHttpActivity {

	ImageView imageView;
	String pic;
	
	 public ImageLoader imageLoader = ImageLoader.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_activity);
		findView();
		setListener();
		fillData();
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findView() {
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		pic = extras.getString("url2");
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageView = (ImageView) findViewById(R.id.imageView);
		ImageLoader.getInstance().displayImage(HttpUrl.IMAGE_LOAD +pic,
				imageView);
		  Toast("点击图片即可返回");
		  
		  imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Pic1Activity.this.finish();
			}
		});
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub

	}

}
