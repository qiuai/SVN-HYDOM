package com.carinsurance.activity;

import java.util.HashMap;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegionDiquActivity extends BaseActivity {

	TextView diqu;
	TextView jiedao;
	String diquid;
	String jiedaoid;
	@ViewInject(R.id.save)
	Button save;
	@ViewInject(R.id.quanbu_dizhi)
	EditText quanbu_dizhi;

	@ViewInject(R.id.user_center_book_business)
	private View diquButton;

	// EditText jiedao;
	String diquname;
	String jname;

	String[] addrs;
	String aid;
	
	@OnClick(R.id.user_center_book_business)
	private void reSelect(View view){
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regiondiqu);
		ViewUtils.inject(this);
		diqu = (TextView) findViewById(R.id.diqu);
		jiedao = (TextView) findViewById(R.id.jiedao);
		this.findViewById(R.id.return_btn).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						JumpUtils.jumpfinish(RegionDiquActivity.this);
					}
				});

		addrs = getIntent().getStringArrayExtra("data");
		aid = getIntent().getStringExtra("aid");
		String str = "";

		for (String node : addrs) {
			str += node + " ";
		}

		str = str.trim();

		diqu.setText(str);
		// Bundle bundle = this.getIntent().getExtras();
		// diquname = bundle.getString("diqu");
		// jname = bundle.getString("jiedao");
		// diquid = bundle.getString("diquid");
		// jiedaoid = bundle.getString("jiedaoid");

		// diqu.setText("贵阳市" + diquname);
		// jiedao.setText(jname);
	}

	@OnClick(R.id.save)
	public void onClick(View v) {

		if (StringUtil.isNullOrEmpty(quanbu_dizhi.getText().toString())) {
			Utils.showMessage(this, "请输入详细地址");

		} else {
			HashMap<String, String> map = new HashMap<String, String>();
			// map.put("diquname", diquname);// 地区名吃
			// map.put("jname", jname);// 街道名称
			// map.put("diquid", diquid);// 地区id
			// map.put("jiedaoid", jiedaoid);// 街道id
			// map.put("quanbu_dizhi",
			// quanbu_dizhi.getText().toString().trim());// 详细地址
			// map.put("jiedaoid", aid);

			Intent intent = new Intent();
			intent.putExtra("quanbu_dizhi", quanbu_dizhi.getText().toString()
					.trim());
			intent.putExtra("addr", addrs);
			intent.putExtra("jiedaoid", aid);
			setResult(RESULT_OK, intent);
			finish();

			// JumpUtils.jumpResultfinish(RegionDiquActivity.this, RESULT_OK,
			// map);
		}
	}

}
