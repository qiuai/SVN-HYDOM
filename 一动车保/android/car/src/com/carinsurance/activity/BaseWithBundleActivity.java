package com.carinsurance.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;

public abstract class BaseWithBundleActivity extends BastActivity {
	public boolean isStartTwoClose = false;// 是否开启点两下回退关闭
	private long exitTime = 0;
	public android.os.Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			initHandeMessage(msg);
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (isStartTwoClose == false && keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			isStartTwoClose = true;
			// if (Constants.is_ziji) {
			// handler.sendEmptyMessage(7);
			// Constants.is_ziji = false;
			// }
			JumpUtils.jumpfinish(BaseWithBundleActivity.this);
			// overridePendingTransition(R.anim.slide_left2,
			// R.anim.slide_right2);
			return super.onKeyDown(keyCode, event);
			// return true;
			// finish();
			// return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序!",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				// finish();
				// System.exit(0);
				exit();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	};

	protected void initHandeMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == NetUtils.Net_Failure) {
			String tag = msg.getData().getString(NetUtils.GET_TAG, "");
			String msgss = msg.getData().getString(NetUtils.GET_MSG, "");
			switch (tag) {
			case Task.USER_LOGIN:
				try {
					JumpUtils.jumpResultfinish(BaseWithBundleActivity.this, 1,
							null);
					Utils.showMessage(BaseWithBundleActivity.this, "请检查网络连接"
							+ msgss);
					Utils.ExitPrgress(BaseWithBundleActivity.this);

				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case Task.GET_CODE:
			case Task.GET_GETCARMODEL:
			case Task.GET_SERVICE:
			case Task.SEND_DINGDAN:
			default:
				try {
					Utils.showMessage(BaseWithBundleActivity.this, "请检查网络连接"
							+ msgss);
					Utils.ExitPrgress(BaseWithBundleActivity.this);
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}

			String message = msg.getData().getString(NetUtils.GET_MSG, "");
			initNetmessageFailure(message, tag, msg.getData());

		}
		if (msg.what == NetUtils.Net_SUCCESS) {
			Log.v("aaa", "测试网络");
			String tag = msg.getData().getString(NetUtils.GET_TAG, "");
			try {
				String message = msg.getData().getString(NetUtils.GET_MSG, "");
				initNetmessageSuccess(message, tag, msg.getData());
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	};

	public void initNetmessageFailure(String message, String task, Bundle tags) {
		// TODO Auto-generated method stub

	}

	/**
	 * 从网络返回的消息
	 * 
	 * @param message
	 * @param Task
	 *            Task里面的判断是那个接口
	 */
	public void initNetmessageSuccess(String message, String task, Bundle tags) {
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(layoutResID);

		// setContentView(setContentViewResId());
		// initView();
	}

	// @SuppressWarnings("unchecked")
	// protected <T extends View> T getViewById(int id) {
	// return (T) findViewById(id);
	// }
	// protected abstract void initView();
	// protected abstract void initData();
	// protected abstract int setContentViewResId();
	@Override
	protected void onResume() {
		/**
		 * 设置为竖立屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

}
