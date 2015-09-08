package com.carinsurance.activity;

import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.SystemUtils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends BaseActivity {

	@ViewInject(R.id.banbenhao)
	TextView banbenhao;
	@ViewInject(R.id.return_btn)
	ImageView return_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		ViewUtils.inject(this);
//		String release = android.os.Build.VERSION.RELEASE; // android系统版本号
		banbenhao.setText("" + SystemUtils.getVersion(AboutActivity.this));
		return_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpfinish(AboutActivity.this);
			}
		});
	}

	private String getHandSetInfo() {
		String handSetInfo = "手机型号:" + android.os.Build.MODEL + ",SDK版本:" + android.os.Build.VERSION.SDK + ",系统版本:" + android.os.Build.VERSION.RELEASE + ",软件版本:" + getAppVersionName(AboutActivity.this);
		return handSetInfo;

	}

	// 获取当前版本号
	private String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo("cn.testgethandsetinfo", 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

}
