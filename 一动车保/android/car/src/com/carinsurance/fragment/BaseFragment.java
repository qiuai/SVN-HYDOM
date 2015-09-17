package com.carinsurance.fragment;

import java.util.HashMap;

import org.json.JSONObject;

import com.carinsurance.activity.BaseActivity;
import com.carinsurance.activity.LoginActivity;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;

public abstract class BaseFragment extends Fragment {

	public android.os.Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {

			initHandeMessage(msg);

		}

	};

	/**
	 * 从网络返回的消息
	 * 
	 * @param message
	 * @param Task
	 *            Task里面的判断是那个
	 */
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub

	}

	protected void initHandeMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == NetUtils.Net_Failure) {
			String tag = msg.getData().getString(NetUtils.GET_TAG, "");
			switch (tag) {

			case Task.USER_LOGIN:
				try {
					JumpUtils.jumpResultfinish(getActivity(), 1, null);
					Utils.showMessage(getActivity(), "请检查网络连接");

				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case Task.GET_SERVICE:
				try {

					Utils.showMessage(getActivity(), "请检查网络连接");

				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			}
			String message = msg.getData().getString(NetUtils.GET_MSG, "");
			initNetmessageFailure(message, tag);
		}
		if (msg.what == NetUtils.Net_SUCCESS) {
			Log.v("aaa", "测试网络");
			String tag = msg.getData().getString(NetUtils.GET_TAG, "");
			try {
				String message = msg.getData().getString(NetUtils.GET_MSG, "");
				JSONObject js = new JSONObject(message);
				if (js.getString("result").equals("101")) {
					Utils.showMessage(getActivity(), "你的账户已经被停用！");
//					Utils.setUid(getActivity(), "");
//					Utils.setToken(getActivity(), "");
//					Utils.setUserName(getActivity(), "");
//					Utils.setUid(getActivity(), "");
//					HashMap<String, String> map = new HashMap<String, String>();
//					map.put("is_to_souye", "1");
					return ;
					// close_0_sizejian1();
					// JumpUtils.jumpto(BaseActivity.this, LoginActivity.class,
					// map,true);

					// exit();
				} else if (js.getString("result").equals("102")) {
					Utils.showMessage(getActivity(), "手机号不存在！");
					return ;
//					Utils.setUid(getActivity(), "");
//					Utils.setToken(getActivity(), "");
//					Utils.setUserName(getActivity(), "");
//					Utils.setUid(getActivity(), "");
//					HashMap<String, String> map = new HashMap<String, String>();
//					map.put("is_to_souye", "1");
//
////					((BaseActivity) (getActivity())).exit();
//					// close_0_sizejian1();
//					JumpUtils.jumpto(getActivity(), LoginActivity.class, map, true);

					// exit();
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				Log.v("aaa", "测试网络95");
				String message = msg.getData().getString(NetUtils.GET_MSG, "");
				Log.v("aaa", "测试网络97");
				initNetmessageSuccess(message, tag);
				Log.v("aaa", "测试网络98");
				initNetmessageSuccess(msg, message, tag);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub

	}

	public void initNetmessageSuccess(Message msg, String message, String tag) {
		// TODO Auto-generated method stub

	};
}
