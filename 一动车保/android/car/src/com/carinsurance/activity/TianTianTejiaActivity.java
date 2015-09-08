package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.ProductDefaultItemModel;
import com.carinsurance.infos.ProductDefaultModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.miloisbadboy.view.PullToRefreshView;
import com.miloisbadboy.view.PullToRefreshView.OnFooterRefreshListener;
import com.miloisbadboy.view.PullToRefreshView.OnHeaderRefreshListener;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TianTianTejiaActivity extends BaseActivity {

	@ViewInject(R.id.main_pull_refresh_view_club)
	PullToRefreshView mPullToRefreshView;

	@ViewInject(R.id.xgridView)
	GridView xgridView;
	List<ProductDefaultItemModel> list;
	int page = 1;
	int maxresult = 10;
	AbstractBaseAdapter<ProductDefaultItemModel> adapter;
	ProductDefaultModel productDefaultModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tiantian_tejia);

		ViewUtils.inject(this);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<ProductDefaultItemModel>();
		adapter = new AbstractBaseAdapter<ProductDefaultItemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {

					convertView = getLayoutInflater().inflate(R.layout.tiantian_adapter_item, null);
				}
				ProductDefaultItemModel p = getItem(position);
				initType0(p, position, convertView);

				return convertView;
			}

			// 天天特价
			private void initType0(final ProductDefaultItemModel p, int position, View convertView) {
				// TODO Auto-generated method stub
				ImageView pimage = ViewHolder.get(convertView, R.id.pimage);
				new xUtilsImageLoader(TianTianTejiaActivity.this).display(pimage, p.getPimage());

				final LinearLayout ll = ViewHolder.get(convertView, R.id.ll);
				ll.setBackgroundResource(R.drawable.layout_click_item);
				ll.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						JumpUtils.jumpto(TianTianTejiaActivity.this, ProductDetailsActivity.class, p);
					}
				});
				TextView pname = ViewHolder.get(convertView, R.id.pname);
				pname.setText("【天天特价】" + p.getPname());

				TextView pdiscount = ViewHolder.get(convertView, R.id.pdiscount);
				pdiscount.setText("" + p.getPdiscount() + "折");
				TextView pdisprice = ViewHolder.get(convertView, R.id.pdisprice);
				pdisprice.setText("￥" + p.getPdisprice());

				TextView poriprice = ViewHolder.get(convertView, R.id.poriprice);
				poriprice.setText("￥" + p.getPoriprice());
				poriprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
		};
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view_club);
		mPullToRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				// TODO Auto-generated method stub
				list.clear();
				page = 1;
				getProjectList(page++, maxresult);
			}
		});
		mPullToRefreshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {

			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				// TODO Auto-generated method stub
				getProjectList(page++, maxresult);
			}
		});
		xgridView.setAdapter(adapter);
		xgridView.setNumColumns(2);
		
		getProjectList(page++, maxresult);
	}

	/**
	 * 
	 */
	public void getProjectList(int page, int maxresult) {

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(TianTianTejiaActivity.this));
		map.put("token", Utils.getToken(TianTianTejiaActivity.this));

		map.put("page", "" + page);
		map.put("maxresult", "" + maxresult);

		NetUtils.getIns().post(Task.GET_PRODUCT_SPECIAL_LIST, map, handler);

	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		try {
			Utils.ExitPrgress(TianTianTejiaActivity.this);
			mPullToRefreshView.onHeaderRefreshComplete();
			mPullToRefreshView.onFooterRefreshComplete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// android:listSelector="@drawable/list_item_color_bg"
		if (task.equals(Task.GET_PRODUCT_SPECIAL_LIST)) {
			try {

				String results = message;
				Log.v("aaa", "运行到了这3");
				String result = new JSONObject(results).getString("result");
				if (result.equals("001")) {
					productDefaultModel = JsonUtil.getEntityByJsonString(results, ProductDefaultModel.class);
					// initView();
					Log.v("aaa", "运行到了这2");

					if (!productDefaultModel.getList().isEmpty()) {
						list.addAll(productDefaultModel.getList());
						adapter.notifyDataSetChanged();
					} else {
						Utils.showMessage(TianTianTejiaActivity.this, "已加载全部数据！");
					}
				} else {
					Utils.showMessage(TianTianTejiaActivity.this, "服务器错误，错误码:" + result);
				}
			} catch (Exception e) {
			}
		}
	}

	@OnClick({ R.id.return_btn, R.id.queding })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(TianTianTejiaActivity.this);
			break;

		}

	}

}
