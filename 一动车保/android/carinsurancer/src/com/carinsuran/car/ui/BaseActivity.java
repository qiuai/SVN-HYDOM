package com.carinsuran.car.ui;

import java.util.List;

import com.carinsuran.car.R;
import com.carinsuran.car.widget.MyProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 界面通用类
 * 
 * @author lrf
 */
public abstract class BaseActivity extends Activity
{

	/**
	 * 响应标题栏左边返回键布局
	 */
	protected View backbtn;
	/**
	 * 响应标题栏右边按钮
	 */
	protected View rightbtn;
	/**
	 * 标题栏返回图片或文字
	 */
	protected TextView backText;
	/**
	 * 标题栏中间标题
	 */
	protected TextView titleText;

	/**
	 * 标题栏右边文本信息
	 */
	protected TextView otherText;
	/**
	 * 标题栏右边的图片按钮
	 */
	protected ImageView rightImage;

	/**
	 * 关闭activity的广播接收者
	 */
	private CloseBroadcast broadCast;
	
	/**
	 * 加载进度条
	 */
	private MyProgressDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		broadCast = new CloseBroadcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.MY_CLOSE_ACTIVITY_BROADCAST");
		registerReceiver(broadCast, filter); // 代码注册广播
	}

	// Activity创建或者从后台重新回到前台时被调用
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
	}

	// Activity从后台重新回到前台时被调用
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

	// Activity被覆盖到下面或者锁屏时被调用
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}

	// 退出当前Activity或者跳转到新Activity时被调用
	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
	}

	// 退出当前Activity时被调用,调用之后Activity就结束了
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(broadCast);// 解除注册广播
	}

	// Activity窗口获得或失去焦点时被调用,在onResume之后或onPause之后
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}

	/**
	 * Activity被系统杀死时被调用. 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死.
	 * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态. 在onPause之前被调用.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	/**
	 * Activity被系统杀死后再重建时被调用.
	 * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity.
	 * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后.
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 捕获返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		switch (keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			closeCurrentActivity();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	// <-------------------------------------分割线---------------------------------------------------->

	/**
	 * 判断网络状态
	 */
	public boolean isNetWorkConnected()
	{
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		WifiManager wifimanager = (WifiManager) getSystemService(WIFI_SERVICE);
		wifimanager.isWifiEnabled();
		wifimanager.getWifiState();
		return (info != null && info.isConnected());
	}

	/**
	 * 打开新的activity
	 */
	public void openNewActivity(Class<?> cls)
	{
		Intent intent = new Intent(this, cls);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/**
	 * 关闭当前Activity
	 */
	public void closeCurrentActivity()
	{
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

	}

	/**
	 * 显示标题栏并设置是否可以返回;setContentView之后调用
	 * 
	 * @param bool
	 */
	public void showTitleBar(boolean bool)
	{
		ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
		View titleBar = getLayoutInflater().inflate(R.layout.title_with_back_btn, null);
		titleBar = findViewById(R.id.title_with_back_btn_bk);
		titleBar.setVisibility(View.VISIBLE);
		backText = (TextView) titleBar.findViewById(R.id.tv_title_with_back_left);
		titleText = (TextView) titleBar.findViewById(R.id.title_with_back_title_btn_mid);
		otherText = (TextView) titleBar.findViewById(R.id.tv_title_with_right);
		backbtn = findViewById(R.id.title_with_back_title_btn_left);
		rightbtn = findViewById(R.id.title_with_back_title_btn_right);
		setIsBack(bool);
	}

	/**
	 * 设置标题栏右边图标显示
	 */
	public void setRightImage(int id)
	{
		rightImage.setVisibility(View.VISIBLE);
		otherText.setVisibility(View.GONE);
		rightImage.setImageResource(id);
	}

	/**
	 * 设置标题栏右边标题
	 */
	public void setRightTitle(String title)
	{
		rightImage.setVisibility(View.GONE);
		otherText.setVisibility(View.VISIBLE);
		otherText.setText(title);
	}

	/**
	 * 设置标题栏右边标题
	 */
	public void setRightListener(OnClickListener clickListener)
	{
		rightbtn.setOnClickListener(clickListener);
	}

	/**
	 * 隐藏标题栏
	 */
	public void hideTitleBar()
	{
//		View titleBar = findViewById(R.id.app_title);
//		titleBar.setVisibility(View.GONE);
	}

	/**
	 * 设置标题文本
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitleText(String title)
	{
		titleText.setText(title);
	}

	/**
	 * 设置返回文本
	 * 
	 * @param title
	 *            标题
	 */
	public void setBackText(String title)
	{
		backText.setText(title);
	}
	/**
	 * 公共方法Toast
	 */
	public void Toast(String text){
	    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
   /**
    * 换回键点击事件
    * 
    */
	public void doBack(View v) {
		finish();
	}
	/**
	 * 从EditText 获取字符
	 * 
	 * @param editText
	 * @return
	 */
	public String getStr(EditText editText) {
		return editText.getText().toString();
	}

	public boolean isNull(String str) {
		if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}

	public String getfilterString(String str) {
		String value = str.trim();
		if (isNull(str)) {
			value = " ";
		}
		return value;

	}
	/**
	 * 描述：对话框dialog （确认，取消）.
	 * 
	 * @param title
	 *            对话框标题内容
	 * @param msg
	 *            对话框提示内容
	 * @param mOkOnClickListener
	 *            点击确认按钮的事件监听
	 */
	public void showDialog(String title, String msg,
			DialogInterface.OnClickListener mOkOnClickListener) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setPositiveButton("确认", mOkOnClickListener);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public void showDialog(String title, String msg, String btn) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(msg);
		builder.setTitle(title);
		builder.setNegativeButton(btn, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public AlertDialog showDialog(String title, View view,
			DialogInterface.OnClickListener mOkOnClickListener) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton("确认", mOkOnClickListener);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog mAlertDialog = builder.create();
		mAlertDialog.show();
		return mAlertDialog;
	}

	/**
	 * 设置是否有返回键 默认可以返回
	 * 
	 * @param bool
	 *            如果可以返回为true
	 */
	private void setIsBack(boolean bool)
	{
		// TODO Auto-generated method stub
		if (bool)
		{
//			backText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.back_btn_return),
//					null, null, null);
			backbtn.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					closeCurrentActivity();
				}
			});
		} else
		{
			backText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
	}

	/**
	 * 摧毁加载进度条
	 */
	public void stopProgressDialog() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}
	
	/**
	 * 开始加载进度条
	 */
	public void startProgressDialog() {
		if (loadingDialog == null) {
			loadingDialog = MyProgressDialog.createDialog(this);
		}
		loadingDialog.show();
	}
	
	/**
	 * 手机中是否存在我所需要的intent
	 * 
	 * @param intent
	 * @return
	 */
	public boolean intentIsAvailable(Intent intent)
	{
		final PackageManager packageManager = this.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
		return list.size() > 0;
	}
	public abstract void findView();// 找到界面所需组件

	public abstract void setListener();// 设置界面监听器

	public abstract void fillData();// 设置组件所需数据适配器
	

	/**
	 * 关闭的广播
	 * 
	 * @author lrf
	 * 
	 */
	public class CloseBroadcast extends BroadcastReceiver
	{

		// 该广播代码注册与在manifest文件中静态注册相反，不是常驻型的，也就是说广播会跟随程序的生命周期。
		// 我们可以根据以上任意一种方法完成注册，当注册完成之后，这个接收者就可以正常工作了。我们可以用以下方式向其发送一条广播：

		@Override
		public void onReceive(Context context, Intent intent)
		{
			// 发广播把所有activity全部关闭
			closeCurrentActivity();
		}

	}
}
