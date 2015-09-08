package com.carinsurance.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.carinsurance.infos.UserModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyNameActivity extends BaseActivity implements OnClickListener{

    private TextView send;
    private EditText name;
    String userName;
    ImageView return_btn;
    UserModel usermodel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_name);
		findView();
	}

	private void findView() {
		// TODO Auto-generated method stub
		name = (EditText) findViewById(R.id.name);
		userName = name.getText().toString();
		send = (TextView) findViewById(R.id.send);
		return_btn = (ImageView) findViewById(R.id.return_btn);
		return_btn.setOnClickListener(this);
		send.setOnClickListener(this);
	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		if(task.equals(Task.UPDATE_PROFILE)){
			Toast.makeText(this, "昵称修改失败", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.UPDATE_PROFILE:
			try {
				JSONObject obj = new JSONObject(message);
				String result = obj.getString("result");
				if(NetUtils.NET_SUCCESS_001.equals(result)){
					Toast.makeText(this, "昵称修改成功", Toast.LENGTH_SHORT).show();
					Intent data = new Intent();
					data.putExtra("data", userName);
					setResult(RESULT_OK,data);
					finish();
				}else{
					Toast.makeText(this, "昵称修改失败", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
//			try {
//				String results = message;
//				usermodel = JsonUtil.getEntityByJsonString(results, UserModel.class);
//				JumpUtils.jumpto(ModifyNameActivity.this, MyCenterActivity.class, null);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			break;
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.send:
			if(userName == null){
				Toast.makeText(this, "请输入新昵称", Toast.LENGTH_SHORT).show();
			}else{
//				HashMap<String, String> map = new HashMap<String, String>();
//				map.put("uid", Utils.getUid(this));
//				map.put("token", Utils.getToken(this));
//				map.put("nickname", userName);
//				RequestParams 
//				NetUtils.getIns().post(Task.GET_USER_NICK, map, handler);
//				updateData(null,userName);
				userName = name.getEditableText().toString().trim();
				new Thread(){
					@Override
					public void run() {
						update(userName,handler);
					};
				}.start();
//				updateData(null, userName);
			}
			break;
		case R.id.return_btn:
			JumpUtils.jumpto(ModifyNameActivity.this, MyCenterActivity.class, null);
			break;
		}
	}
	private void update(String name,Handler handler){
		String uid = Utils.getUid(this);
		String token = Utils.getToken(this);
		String url = Task.url;
		HttpPost post = new HttpPost(url + Task.UPDATE_PROFILE);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		builder.addBinaryBody("uid", uid.getBytes());
		builder.addBinaryBody("token", token.getBytes());
		builder.addBinaryBody("nickname", name.getBytes());
		builder.setMode(HttpMultipartMode.STRICT);
		post.setEntity(builder.build());
		
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			if(statusCode == 200){
				StringBuffer sb = new StringBuffer();
				InputStream is = resp.getEntity().getContent();
				byte[] buf = new byte[1024];
				int len = is.read(buf);
				while(len != -1){
					sb.append(new String(buf, 0, len));
					len = is.read(buf);
				}
				
				Message msg = new Message();
				msg.what = NetUtils.Net_SUCCESS;
				Bundle bun = new Bundle();
				bun.putString(NetUtils.GET_TAG, Task.UPDATE_PROFILE);
				bun.putString(NetUtils.GET_MSG, sb.toString());
				msg.setData(bun);
				handler.sendMessage(msg);
				
				return;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Message msg = new Message();
		msg.what = NetUtils.Net_Failure;
		Bundle bun = new Bundle();
		bun.putString(NetUtils.GET_TAG, Task.UPDATE_PROFILE);
		bun.putString(NetUtils.GET_MSG, "update fail");
		msg.setData(bun);
		handler.sendMessage(msg);
	}
	
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
		
//		params.setContentType("image/jpeg");
//		params.setContentType("application/octet-stream");
//		params.setContentType("multipart/form-data");
//		params.setHeader("enctype", "multipart/form-data");

		NetUtils.getIns().post(Task.UPDATE_PROFILE, params, handler);
	}
	
}
