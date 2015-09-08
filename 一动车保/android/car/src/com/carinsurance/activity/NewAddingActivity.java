package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.ProductDefaultItemModel;
import com.carinsurance.infos.ProductDefaultModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewAddingActivity extends BaseActivity {

	@ViewInject(R.id.myListView)
	XListView xlistview;
	int page = 1;
	int maxresult = 10;

	public static int RESULT_OK = 100;

	SortModel sortModel;
	String scid;

	// 所有选中的产品的id
	List<String> yixuanshangping_id;
	ProductDefaultModel productDefaultModel;
	// TextvIEW queding
	List<ProductDefaultItemModel> list;
	AbstractBaseAdapter<ProductDefaultItemModel> adapter;

	int select_position = -1;
	String gr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_adding);

		ViewUtils.inject(this);
		sortModel = (SortModel) JumpUtils.getSerializable(NewAddingActivity.this);

		gr = JumpUtils.getString(NewAddingActivity.this, "groupPosition");
		scid = JumpUtils.getString(NewAddingActivity.this, "scid");
		Log.v("aaa", "gr====>=>" + gr);
		int groupPosition = Integer.parseInt(gr);
		// scid=JumpUtils.getString(NewAddingActivity.this,"scid");

		yixuanshangping_id = new ArrayList<String>();

		for (int j = 0; j < sortModel.getSelectSeriverTypeitemList().get(groupPosition).getProductDefaultItemModel_list().size(); j++) {
			if (sortModel.getSelectSeriverTypeitemList().get(groupPosition).getProductDefaultItemModel_list().get(j) != null) {
				yixuanshangping_id.add(sortModel.getSelectSeriverTypeitemList().get(groupPosition).getProductDefaultItemModel_list().get(j).getPid());
			}
		}
		Log.v("aaa", "yixuanshangping_id===>" + yixuanshangping_id.toString());
		list = new ArrayList<ProductDefaultItemModel>();
		initView();
		// getProjectList(page, maxresult);
	}

	private void initView() {
		// TODO Auto-generated method stub

		adapter = new AbstractBaseAdapter<ProductDefaultItemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = getLayoutInflater().inflate(R.layout.adding_sp_item, null);
				}
				ProductDefaultItemModel p = getItem(position);

				ImageView near_icon = ViewHolder.get(convertView, R.id.near_icon);
				new xUtilsImageLoader(NewAddingActivity.this).display(near_icon, p.getPimage());
				
				final LinearLayout ll = ViewHolder.get(convertView, R.id.ll);
				ll.setBackgroundColor(Color.parseColor("#ffffff"));
				if (select_position == position) {
					ll.setBackgroundColor(Color.parseColor("#61E7F1"));
				}

				ll.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						select_position = position;
//						ll.setBackgroundColor(Color.parseColor("#61E7F1"));
						notifyDataSetChanged();
					}
				});
				TextView pname = ViewHolder.get(convertView, R.id.pname);
				pname.setText("" + p.getPname());

				TextView pcomts = ViewHolder.get(convertView, R.id.pcomts);
				pcomts.setText("" + p.getPcomts() + "评价");
				TextView price = ViewHolder.get(convertView, R.id.price);
				price.setText("￥" + p.getPrice());
				TextView pbuynum = ViewHolder.get(convertView, R.id.pbuynum);
				pbuynum.setText("" + p.getPbuynum() + "人购买");

				return convertView;
			}
		};

		xlistview.setAdapter(adapter);

		xlistview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				list.clear();
				page = 1;
				getProjectList(page++, maxresult);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				// if (page > Integer.parseInt(productDefaultModel.getPages()))
				// {
				// Utils.showMessage(NewAddingActivity.this, "已加载全部数据");
				//
				// } else
				getProjectList(page++, maxresult);
			}
		});
		xlistview.startLoadMore();
	}

	/**
	 * 根据服务类型和用户车辆信息获取商品列表
	 */
	public void getProjectList(int page, int maxresult) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(NewAddingActivity.this));
		map.put("token", Utils.getToken(NewAddingActivity.this));

		if (!StringUtil.isNullOrEmpty(scid))
			map.put("scid", scid);
		else {
			Utils.showMessage(NewAddingActivity.this, "车型信息不能为空！");
			return;
		}

		StringBuffer buff = new StringBuffer("");

		for (int i = 0; i < yixuanshangping_id.size(); i++) {
			if (i != yixuanshangping_id.size() - 1)
				buff.append(yixuanshangping_id.get(i) + "#");
			else
				buff.append(yixuanshangping_id.get(i));
		}
		if (!StringUtil.isNullOrEmpty(buff.toString())) {
			map.put("pids", buff.toString());
		}

		map.put("page", "" + page);
		map.put("maxresult", "" + maxresult);

		NetUtils.getIns().post(Task.GET_PRODUCTLIST, map, handler);

	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		try {
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		try {
			Utils.ExitPrgress(NewAddingActivity.this);
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// android:listSelector="@drawable/list_item_color_bg"
		if (task.equals(Task.GET_PRODUCTLIST)) {
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
					}else
					{
						Utils.showMessage(NewAddingActivity.this, "已加载全部数据！");
					}
				} else {
					Log.v("aaa", "运行到了这4");
					Utils.showMessage(NewAddingActivity.this, "服务器错误，错误码:" + result);
				}

				// carType = JsonUtil.getEntityByJsonString(results,
				// CarType.class);
				// if (carType.getResult().equals("" +
				// NetUtils.NET_SUCCESS_001)) {
				// initViews();
				//
				// }

				// Updata();
				// adapter.setFuwuModel(seriverTypeModel);
				// is_getfuwu_finish = true;
				// if (is_getfuwu_finish == true && is_gettianqi_finish == true)
				// {
				// adapter.notifyDataSetChanged();
				// }
				// xListView.stopLoadMore();
				// xListView.stopRefresh();
			} catch (Exception e) {
				Log.v("aaa", "运行到了这6");
			}
		}
	}

	@OnClick({ R.id.return_btn, R.id.queding })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(NewAddingActivity.this);
			break;
		case R.id.queding:
			if (select_position != -1) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("groupPosition", gr);
				JumpUtils.jumpResultfinish(NewAddingActivity.this, RESULT_OK, list.get(select_position), map);
			} else {
				Utils.showMessage(NewAddingActivity.this, "请选择需要添加的商品！");
			}
			break;
		}

	}

}
