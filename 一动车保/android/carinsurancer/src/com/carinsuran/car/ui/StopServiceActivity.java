package com.carinsuran.car.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.carinsuran.car.MyApplication;
import com.carinsuran.car.R;
import com.carinsuran.car.config.HttpClient;
import com.carinsuran.car.config.HttpUrl;
import com.carinsuran.car.http.AsyncHttpResponseHandler;
import com.carinsuran.car.http.RequestParams;
import com.carinsuran.car.tool.SPUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;

public class StopServiceActivity extends BaseHttpActivity implements OnClickListener{

	Button upload_pictures;
	ImageButton imageButton1,imageButton2,imageButton3;
	private SPUtil sp;
	Dialog dialog;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	String imagename;
	File upfile;
	Context context;
	String flag,imageUrl1,imageUrl2,imageUrl3;
	String[] sport;
	Bitmap photo;
	Uri uritempFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stop_service);
		showTitleBar(true);
		findView();
		setListener();
		fillData();
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub
		 try {
				JSONObject jsonObject = new JSONObject(json);
				String result = jsonObject.getString("result");
				if("001".equals(result)){
					Toast("上传图片成功");
					openNewActivity(MainActivity.class);
				}else if("1004".equals(result)){
					Toast("该技师账号已删除");
					openNewActivity(LoginActivity.class);
				}else if("1003".equals(result)){
					openNewActivity(MainActivity.class);
					Toast("订单异常,错误码"+result);
				}else if("1005".equals(result)){
					Toast("操作的订单对象不属于当前技师");
				}else{
					Toast("网络异常,请重试");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void findView() {
		// TODO Auto-generated method stub
		sp = new SPUtil(this, SPUtil.USER_LOGIN_INFO, Context.MODE_APPEND);
		upload_pictures = (Button) findViewById(R.id.upload_pictures);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		upload_pictures.setOnClickListener(this);
		imageButton1.setOnClickListener(this);
		imageButton2.setOnClickListener(this);
		imageButton3.setOnClickListener(this);
	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
		setTitleText("结束服务");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.upload_pictures:
			if(imageUrl1==null && imageUrl2==null&&imageUrl3==null){
				Toast("请先选择图片");
			}else{
				post(HttpUrl.STOP_SERVICE, createParams());
			}
			
			break;
		case R.id.imageButton1:
			flag="1";
			//弹出选择相册、拍照
			showDialog();
			break;
		case R.id.imageButton2:
			flag="2";
			showDialog();
			break;
		case R.id.imageButton3:
			flag="3";
			showDialog();
			break;
		}
	}

	private RequestParams createParams() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		String[] sport = new String[3];
		sport[0] = imageUrl1;
		sport[1] = imageUrl2;
		sport[2] = imageUrl3;
//		for (int i = 0; i < sport.length; i++) {
//				params.put("imageUrl", sport[i]);
//		}
		params.put("imageUrl", imageUrl1+","+imageUrl2+","+imageUrl3);
		params.put("tlng", MyApplication.longitude);
		params.put("tlat", MyApplication.latitude);
		return params;
	}
	
	private void showDialog() {
		// TODO Auto-generated method stub
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	
	public void on_click(View v)
	{
		switch (v.getId())
		{
		case R.id.openCamera:
			openCamera();
			break;
		case R.id.openPhones:
			openPhones();
			break;
		case R.id.cancel:
			dialog.cancel();
			break;
		default:
			break;
		}
	}

	private void openPhones() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
		startActivityForResult(intent, PHOTOZOOM);
	}

	private void openCamera() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
		startActivityForResult(intent, PHOTOHRAPH);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH)
		{
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM)
		{
			startPhotoZoom(data.getData());
		}
		
		// 处理结果
		if (requestCode == PHOTORESOULT)
		{
			Bundle extras = data.getExtras();
			if (extras != null)
			{
				new DateFormat();
				String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
				try {
					photo = BitmapFactory.decodeStream(getContentResolver()
							.openInputStream(uritempFile));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File sdDir = Environment.getExternalStorageDirectory();
				File file = new File(sdDir.toString());
				if(file.exists()){
					file.mkdir();
				}
				String filename = file + "/" + name;
				
				try
				{
					FileOutputStream fout = new FileOutputStream(filename);
					photo.compress(Bitmap.CompressFormat.JPEG, 100, fout);
					fout.flush();
					fout.close();
				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    upfile = new File(filename);
			    if("1".equals(flag)){
			    	HttpClient.post(HttpUrl.MY_FRAGMENT, createParam(upfile), handler1);
			    	imageButton1.setImageDrawable(null);
			    }else if("2".equals(flag)){
			    	HttpClient.post(HttpUrl.MY_FRAGMENT, createParam(upfile), handler2);
			    	imageButton2.setImageDrawable(null);
			    }else if("3".equals(flag)){
			    	HttpClient.post(HttpUrl.MY_FRAGMENT, createParam(upfile), handler3);
			    	imageButton3.setImageDrawable(null);
			    }
				
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	private RequestParams createParam(File file) {
		// TODO Auto-generated method stub
		RequestParams params=new RequestParams();
		  params.put("techId", sp.getValue("techId", ""));
		try {
			params.put("imageFile", file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return params;
	}
	
public AsyncHttpResponseHandler handler1 = new AsyncHttpResponseHandler(){

		
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
		}

		@Override
		@Deprecated
		public void onFailure(Throwable error) {
			// TODO Auto-generated method stub
			super.onFailure(error);
			System.out.println("&&&&&&&&");
		}

		@Override
		public void onSuccess(String content) {
			// TODO Auto-generated method stub
			super.onSuccess(content);
			imageButton1.setImageBitmap(photo);
			dialog.cancel();
			System.out.println("???????"+content);
			try {
				JSONObject jsonObject = new JSONObject(content);
				String res = jsonObject.getString("result");
				if("1004".equals(res)){
					Toast("该技师账号已删除");
					openNewActivity(LoginActivity.class);
				}
				imageUrl1 = jsonObject.getString("imageUrl");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
	
	
public AsyncHttpResponseHandler handler2 = new AsyncHttpResponseHandler(){

		
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
		}

		@Override
		@Deprecated
		public void onFailure(Throwable error) {
			// TODO Auto-generated method stub
			super.onFailure(error);
			System.out.println("&&&&&&&&");
		}

		@Override
		public void onSuccess(String content) {
			// TODO Auto-generated method stub
			super.onSuccess(content);
			imageButton2.setImageBitmap(photo);
			dialog.cancel();
			System.out.println("???????"+content);
			try {
				JSONObject jsonObject = new JSONObject(content);
				String res = jsonObject.getString("result");
				if("1004".equals(res)){
					Toast("该技师账号已删除");
					openNewActivity(LoginActivity.class);
				}
				imageUrl2 = jsonObject.getString("imageUrl");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
	
public AsyncHttpResponseHandler handler3 = new AsyncHttpResponseHandler(){

		
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			super.onFinish();
		}

		@Override
		@Deprecated
		public void onFailure(Throwable error) {
			// TODO Auto-generated method stub
			super.onFailure(error);
			System.out.println("&&&&&&&&");
		}

		@Override
		public void onSuccess(String content) {
			// TODO Auto-generated method stub
			super.onSuccess(content);
			imageButton3.setImageBitmap(photo);
			dialog.cancel();
			System.out.println("???????"+content);
			try {
				JSONObject jsonObject = new JSONObject(content);
				String res = jsonObject.getString("result");
				if("1004".equals(res)){
					Toast("该技师账号已删除");
					openNewActivity(LoginActivity.class);
				}
				imageUrl3 = jsonObject.getString("imageUrl");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	

	private void startPhotoZoom(Uri uri) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 400);
//		intent.putExtra("return-data", true);
		uritempFile = Uri.parse("file://" + "/"
				+ Environment.getExternalStorageDirectory().getPath() + "/"
				+ "small.jpg");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, PHOTORESOULT);
	}
	

}
