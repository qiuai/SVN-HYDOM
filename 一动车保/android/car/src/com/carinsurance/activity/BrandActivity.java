package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.BrandAdapter;
import com.carinsurance.infos.BrandItemModel;
import com.carinsurance.infos.BrandModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurance.xlistview.XListView;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 服务订单的选择商品
 * 
 * @author Administrator
 *
 */
public class BrandActivity extends BaseActivity {

	private BrandAdapter brandadapter;

	@ViewInject(R.id.myListView)
	private XListView myListView;

	SortModel SortModel;

	@ViewInject(R.id.all)
	Spinner all;

	@ViewInject(R.id.return_btn)
	ImageView return_btn;
	BrandModel brandmodel;
	// 第一个Spinner的数据
	List<BrandItemModel> spinner1_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand_activity);
		ViewUtils.inject(this);
		SortModel = (com.carinsurance.abcpinying.SortModel) JumpUtils.getSerializable(BrandActivity.this);
		Log.v("aaa", "sortMode" + SortModel.toString());
		return_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpfinish(BrandActivity.this);
			}
		});
		initSpinner1();

		initListView();
	}

	private void initListView() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("fff");

		myListView.setAdapter(new AbstractBaseAdapter<String>() {

			@Override
			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = BrandActivity.this.getLayoutInflater().inflate(R.layout.brand_layouts, null);
				}

				return convertView;
			}
		});
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
//				BrandItemModel
				JumpUtils.jumpto(BrandActivity.this, ProductDetailsActivity.class, SortModel);
//			    JumpUtils.jumpActivityForResult(BrandActivity.this, ProductDetailsActivity.class, SortModel, 1, true);
			}
		});
	}

	private void initSpinner1() {
		String uid = Utils.getUid(this);
		String token = Utils.getToken(this);
		String scid = SortModel.getSeriverTypeitemModel0().getScid();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", uid);
		map.put("token", token);
		map.put("scid", scid);

		NetUtils.getIns().post(Task.GET_PINGPAI_List, map, handler);
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.GET_PINGPAI_List:

			try {
				String results = message;
				brandmodel = JsonUtil.getEntityByJsonString(results, BrandModel.class);

				if (brandmodel.getResult().equals("" + NetUtils.NET_SUCCESS_001)) {
					spinner1_list = brandmodel.getList();
					all.setAdapter(new AbstractBaseAdapter<BrandItemModel>(spinner1_list) {

						@Override
						public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
							// TODO Auto-generated method stub
							if (convertView == null) {
								convertView = BrandActivity.this.getLayoutInflater().inflate(R.layout.spinner_bg, parent, false);
							}
							BrandItemModel model = getItem(position);
							TextView name = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.name);
							// ImageView img =
							// com.carinsurance.adapter.ViewHolder.get(convertView,
							// R.id.img);

							name.setText("" + model.getBname());
							// new xUtilsImageLoader(context).display(img,
							// model.getScimage());

							return convertView;
						}
					});
				} else {
					Utils.showMessage(BrandActivity.this, "未知异常,错误码:" + brandmodel.getResult());
				}
			} catch (Exception e) {
			}
			break;
		}

	}

}
