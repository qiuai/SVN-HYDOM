package com.carinsurance.activity;

import java.util.HashMap;

import org.json.JSONObject;

import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ServierFanWeiActivity extends BaseActivity {

	@ViewInject(R.id.content)
	TextView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fuwu_fanwei);
		ViewUtils.inject(this);
		getGET_ZHICHIDIQU();
	}

	public void getGET_ZHICHIDIQU() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(ServierFanWeiActivity.this));

		map.put("token", Utils.getToken(ServierFanWeiActivity.this));
		NetUtils.getIns().post(Task.GET_ZHICHIDIQU, map, handler);

	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.GET_ZHICHIDIQU:

			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				if (result.equals("001")) {
					String text = js.getString("text");
				    content.setText(""+text);
 
				} else {
					Utils.showMessage(ServierFanWeiActivity.this, "未知异常,错误码:" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		
		}
	}
	
	
	
	@OnClick(R.id.return_btn)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(ServierFanWeiActivity.this);
			break;
		}
	}
}
