package com.carinsurance.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends BaseActivity {

	@ViewInject(R.id.et_feedback)
	EditText et_feedback;
	@ViewInject(R.id.Surplus_textNumber)
	TextView Surplus_textNumber;
	@ViewInject(R.id.return_btn)
	ImageView return_btn;
	@ViewInject(R.id.btn_feedback)
	FrameLayout btn_feedback;
	
	
//最大为limit个字符
	int limit = 50;
	// 统计editText数量
	private int textNumber = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		ViewUtils.inject(this);
		Log.v("aaa", "return_btn===>" + return_btn);
		Surplus_textNumber.setText(""+limit);
		return_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpfinish(FeedbackActivity.this);
			}
		});
		// 设置最大为50个字符
		et_feedback.setFilters(new InputFilter[] { new InputFilter.LengthFilter(limit) });

		et_feedback.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				Log.v("aaa", "onTextChanged--》s=" + s + "/" + "start=" + start + "/" + "before=" + before + "/" + "count=" + count);
				// start+count为字的总数
				// 1.字体总数为start+count
				int textNumber = s.length();
				Surplus_textNumber.setText("" + (limit - textNumber));
				if (textNumber == limit) {
					// ShowToast(SendDynamicActivity.this, "不能超过"+limit+"个字符");
					Toast t = Toast.makeText(FeedbackActivity.this, "最大字数不能超过" + limit + "字", 0);
					t.setGravity(Gravity.CENTER, 0, 0);
					t.show();
				}
				Log.v("aaa", "ssss====>" + s.length() + "/limit=" + limit);
			}

			// 在数据改变前 s是数据改变钱的，start 数据改变前的光标位置 count：0为增加
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				Log.v("aaa", "beforeTextChanged s=" + s + "/" + "start=" + start + "/" + "count=" + count + "/" + "after=" + after);

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Log.v("aaa", "beforeTextChanged s=" + s);
			}
		});

	}

	@OnClick(R.id.btn_feedback)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_feedback:
			userFeedback();
			break;
		}
	}

	/**
	 * 用户反馈
	 */
	public void userFeedback() {

		if (StringUtil.isNullOrEmpty(et_feedback.getText().toString().trim())) {
			Utils.showMessage(FeedbackActivity.this, "请输入反馈!");
			return;
		}

		HashMap<String, String> rp = new HashMap<String, String>();
		rp.put("uid", Utils.getUid(this));
		rp.put("token", Utils.getToken(this));

		rp.put("content", et_feedback.getText().toString().trim());
		// Log.v("aaa", "我要提交的数据是" + rp.toString());
		NetUtils.getIns().post(Task.GET_FEEDBACK, rp, this.handler);
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);

		if (task.equals(Task.GET_FEEDBACK)) {
			Log.i("", "result=" + message);

			try {
				String result = (new JSONObject(message)).getString("result");
				if (result.equals("001")) {
					Toast.makeText(this, "提交成功，谢谢你的反馈!", Toast.LENGTH_SHORT).show();
					onBackPressed();
				} else {
					Utils.showMessage(FeedbackActivity.this, "提交失败！错误码" + result);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
