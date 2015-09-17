package com.carinsurance.scaleview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carinsurancer.car.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

@SuppressLint("NewApi") public class ImageShowFragment extends Fragment {
	private ScaleImageView image = null;
	private TextView loadingInfo = null;
	private ProgressBar progress = null;

	public static ImageShowFragment newInstance(String url) {
		ImageShowFragment f = new ImageShowFragment();
		Bundle b = new Bundle();
		b.putString("url", url);
		f.setArguments(b);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_imageshow, container, false);
		initial(view);
		return view;
	}

	protected void initial(View view) {
		// TODO Auto-generated method stub
		image = (ScaleImageView) view.findViewById(R.id.imageView1);
		loadingInfo = (TextView) view.findViewById(R.id.alert_dialog_text);
		progress = (ProgressBar) view.findViewById(R.id.alert_dialog_progressbar);
		// url=Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera/IMG_20150207_145613.jpg";
		((View) loadingInfo.getParent()).setVisibility(View.GONE);
		final String url = getArguments().getString("url");
		if (TextUtils.isEmpty(url)) {
			progress.setVisibility(View.GONE);
			loadingInfo.setText("网络地址错误");
		} else {
			Log.i("1", url);
			ViewTreeObserver vto = image.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					loadImage(url);
					image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			});
		}
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("image", "--------->onclick");
			}
		});
	}

	public void loadImage(String uri) {
		((View) loadingInfo.getParent()).setVisibility(View.VISIBLE);
		progress.setVisibility(View.VISIBLE);
		loadingInfo.setText("加载中");
		BitmapUtils b = new BitmapUtils(getActivity());//BitmapHelp.getBitmapUtils();
		b.display(image, uri, new BitmapLoadCallBack<ScaleImageView>() {

			@Override
			public void onLoadCompleted(ScaleImageView arg0, String arg1, Bitmap arg2, BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
				Log.i("", "onLoadCompleted------------");
				((View) loadingInfo.getParent()).setVisibility(View.GONE);
				arg0.setEnabled(true);
				arg0.setImageBitmap(arg2);
			}

			@Override
			public void onLoadFailed(ScaleImageView arg0, String arg1, Drawable arg2) {
				Log.i("", "onLoadFailed------------");
				arg0.setEnabled(false);
				progress.setVisibility(View.GONE);
				loadingInfo.setText("加载图片失败");
			}
		});
	}

}
