package com.carinsurance.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.carinsurance.infos.UserModel;
import com.carinsurance.maputil.CircleImageView;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 个人中心设置
 * 
 * @author Administrator
 * 
 */
public class MyCenterActivity extends BaseActivity implements OnClickListener {
	private final static int REQUEST_TO_MODIFYNAME = 100;
	UserModel usermodel;
	CircleImageView circleImageView;
	TextView name;
	Button tuichudenglu;
	public ImageLoader imageLoader = ImageLoader.getInstance();
	String userName;
	String photoUrl;
	RelativeLayout mingcheng;
	Dialog dialog;

	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final int Result__ook = 110; // 返回给Home
	Bitmap photo;
	private boolean isUpdatedImage = false;
	private boolean isUpdatedNickName = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_center);
		// 获得意图
		Intent intent = getIntent();
		this.findViewById(R.id.return_btn).setOnClickListener(this);
		// 读取数据
		userName = intent.getStringExtra("name");
		photoUrl = intent.getStringExtra("photo");

		findView();

		// name.setText(userName);
		// imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		// System.out.println(">>>" + Task.img_url + photoUrl);
		// ImageLoader.getInstance().displayImage(Task.img_url + photoUrl,
		// circleImageView);
		fillData();
	}

	private void findView() {
		// TODO Auto-generated method stub
		circleImageView = (CircleImageView) findViewById(R.id.circleImageView);
		name = (TextView) findViewById(R.id.name);
		mingcheng = (RelativeLayout) findViewById(R.id.mingcheng);
		name.setText(userName);
		tuichudenglu = (Button) findViewById(R.id.tuichudenglu);
		circleImageView.setOnClickListener(this);
		tuichudenglu.setOnClickListener(this);
		mingcheng.setOnClickListener(this);
	}

	private void fillData() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		NetUtils.getIns().post(Task.GET_USER_DATA, map, handler);
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.GET_USER_DATA:
			try {
				String results = message;
				System.out.println(">>>" + results);
				usermodel = JsonUtil.getEntityByJsonString(results,
						UserModel.class);
				imageLoader.init(ImageLoaderConfiguration.createDefault(this));
				ImageLoader.getInstance().displayImage(
						Task.img_url + usermodel.getPhoto(), circleImageView);
				userName = usermodel.getNickname();
				name.setText(userName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Task.USER_SIGNOUT: {
			try {

				String results = message;
				JSONObject js = new JSONObject(message);
////				if (js.getString("result").equals("001")) {
//					Utils.setUid(MyCenterActivity.this, "");
//					Utils.setToken(MyCenterActivity.this, "");
//					Utils.setUserName(MyCenterActivity.this, "");
//					Utils.setPhoneNumber(MyCenterActivity.this, "");
//					// JumpUtils.jumpActivityForResult(MyCenterActivity.this,LoginActivity.class,
//					// 110, true);
//					JumpUtils.jumpActivityForResult(MyCenterActivity.this,
//							LoginActivity.class, null, Result__ook);
////				}

			} catch (Exception e) {
				// TODO: handle exception
			}

			break;
		}
		case Task.UPDATE_PROFILE: {
			try {
				JSONObject obj = new JSONObject(message);
				String result = obj.getString("result");
				if (NetUtils.NET_SUCCESS_001.equals(result)) {
					Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
					isUpdatedImage = true;
					circleImageView.setImageBitmap(photo);
					
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
			break;
		}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.mingcheng:
			JumpUtils.jumpActivityForResult(MyCenterActivity.this,
					ModifyNameActivity.class, null, REQUEST_TO_MODIFYNAME);
			break;
		case R.id.circleImageView:
			showDialog();
			break;
		case R.id.tuichudenglu:
			SignOut();
			// JumpUtils.jumpto(MyCenterActivity.this, LoginActivity.class,
			// null);
			break;
		case R.id.return_btn:
			setResultData();
			JumpUtils.jumpfinish(MyCenterActivity.this);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResultData();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setResultData() {
		if (isUpdatedImage || isUpdatedNickName) {
			setResult(RESULT_OK);
		}
	}

	public void SignOut() {
		// user/signout
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(MyCenterActivity.this));
		map.put("token", Utils.getToken(MyCenterActivity.this));
		NetUtils.getIns().post(Task.USER_SIGNOUT, map, handler);
		
		Utils.setUid(MyCenterActivity.this, "");
		Utils.setToken(MyCenterActivity.this, "");
		Utils.setUserName(MyCenterActivity.this, "");
		Utils.setPhoneNumber(MyCenterActivity.this, "");
		// JumpUtils.jumpActivityForResult(MyCenterActivity.this,LoginActivity.class,
		// 110, true);
		JumpUtils.jumpActivityForResult(MyCenterActivity.this,
				LoginActivity.class, null, Result__ook);
	
	}

	private void showDialog() {
		// TODO Auto-generated method stub
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
				null);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
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

	public void on_click(View v) {
		switch (v.getId()) {
		case R.id.openCamera:
			dialog.cancel();
			openCamera();
			break;
		case R.id.openPhones:
			dialog.cancel();
			openPhones();
			break;
		case R.id.cancel:
			dialog.cancel();
			break;
		default:
			break;
		}
	}

	/**
	 * 打开相册
	 */
	private void openPhones() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_UNSPECIFIED);
		startActivityForResult(intent, PHOTOZOOM);
	}

	/**
	 * 打开照相机
	 */
	private void openCamera() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), "temp.jpg")));
		System.out.println("============="
				+ Environment.getExternalStorageDirectory());
		startActivityForResult(intent, PHOTOHRAPH);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

//		Log.d("aaa","回退的resultCode是");
		if (requestCode == REQUEST_TO_MODIFYNAME && resultCode == RESULT_OK) {
			String nameStr = data.getStringExtra("data");

			name.setText(nameStr);
			isUpdatedNickName = true;
			return;
		}
        //直接给登录界面后按回退键后调用
		if (requestCode == Result__ook && resultCode == 0) {
			setResult(2, data);
			finish();
		}
		//直接给登录界面后登录键后调用
		if (requestCode == Result__ook && resultCode == 1) {
			setResult(1, data);
			finish();
		}
		
		
		
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/temp.jpg");
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {

				new DateFormat();
				String name = DateFormat.format("yyyyMMdd_hhmmss",
						Calendar.getInstance(Locale.CHINA))
						+ ".jpg";

				photo = (Bitmap) extras.get("data");

				File sdDir = Environment.getExternalStorageDirectory();
				File file = new File(sdDir.toString());
				if (!file.exists()) {
					file.mkdirs();
				}
				String filename = file + "/" + name;
				try {
					FileOutputStream fout = new FileOutputStream(filename);
					photo.compress(Bitmap.CompressFormat.JPEG, 100, fout);
					fout.flush();
					fout.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				File upfile = new File(filename);
				circleImageView.setImageDrawable(null);

				// HttpClient.post(HttpUrl.BASE_URL_IMAGE, createParams(upfile),
				// responseHandler);

				updateData(upfile, null);

				
			}

		}
		if (dialog != null) {
			dialog.dismiss();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/***
	 * 
	 * @param file
	 *            头像文件
	 * @param name
	 *            昵称
	 */
	private void updateData(File file, String name) {
		String uid = Utils.getUid(this);
		String token = Utils.getToken(this);

		RequestParams params = new RequestParams();
		params.addBodyParameter("uid", uid);
		params.addBodyParameter("token", token);
		if (name != null) {
			params.addBodyParameter("nickname", name);
		}
		if (file != null) {
			params.addBodyParameter("photo", file);
		}

		NetUtils.getIns().post(Task.UPDATE_PROFILE, params, handler);
	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		super.initNetmessageFailure(message, task);
		if (task.equals(Task.UPDATE_PROFILE)) {
			Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
		}
	}

	// public AsyncHttpResponseHandler responseHandler = new
	// AsyncHttpResponseHandler() {
	//
	// @Override
	// public void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// }
	//
	// @Override
	// public void onFinish() {
	// // TODO Auto-generated method stub
	// super.onFinish();
	// System.out.println("???????/");
	// }
	//
	// @Override
	// public void onSuccess(String content) {
	// // TODO Auto-generated method stub
	// super.onSuccess(content);
	// dialog.cancel();
	// Toast.makeText(MyCenterActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// @Deprecated
	// public void onFailure(Throwable error) {
	// // TODO Auto-generated method stub
	// super.onFailure(error);
	// }
	//
	// };
	//
	// private RequestParams createParams(File upfile) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	/*
	 * 裁剪图片方法实现
	 */
	private void startPhotoZoom(Uri uri) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 256);
		intent.putExtra("outputY", 256);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

}
