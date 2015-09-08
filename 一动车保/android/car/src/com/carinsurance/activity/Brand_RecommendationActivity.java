package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carinsurance.abcpinying.SortModel;
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
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 特色市场 的 品牌推荐 限量精品 绿色出行
 * 
 * @author Administrator
 *
 */
public class Brand_RecommendationActivity extends BaseActivity {

	@ViewInject(R.id.myListView)
	XListView xlistview;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.queding)
	TextView queding;

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

	// int select_position = -1;
	String gr;
	String type = "0";// 0,品牌推荐 1限量精品 3绿色出行

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_adding);

		ViewUtils.inject(this);

		type = JumpUtils.getString(Brand_RecommendationActivity.this, "type");

		initView();
		// getProjectList(page, maxresult);
	}

	private void initView() {
		// TODO Auto-generated method stub
		if (type.equals("0"))
			title.setText("品牌推荐");
		else if (type.equals("1"))
			title.setText("限量精品");
		else if (type.equals("3"))
			title.setText("绿色出行");
		queding.setVisibility(View.GONE);
		sortModel = (SortModel) JumpUtils.getSerializable(Brand_RecommendationActivity.this);

		// gr = JumpUtils.getString(Brand_RecommendationActivity.this,
		// "groupPosition");
		// scid = JumpUtils.getString(Brand_RecommendationActivity.this,
		// "scid");
		// Log.v("aaa", "gr====>=>" + gr);
		// int groupPosition = Integer.parseInt(gr);
		// scid=JumpUtils.getString(NewAddingActivity.this,"scid");
		list = new ArrayList<ProductDefaultItemModel>();
		adapter = new AbstractBaseAdapter<ProductDefaultItemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					if (type.equals("0") || type.equals("3"))
						convertView = getLayoutInflater().inflate(R.layout.adding_sp_item, null);
					else
						convertView = getLayoutInflater().inflate(R.layout.xianliangjingping, null);
				}
				ProductDefaultItemModel p = getItem(position);
				// 精品推荐
				if (type.equals("0") || type.equals("3"))
					initType0(p, position, convertView);
				else if (type.equals("1"))
					initType1(p, position, convertView);

				return convertView;
			}

			// 限量金瓶
			private void initType1(final ProductDefaultItemModel p, int position, View convertView) {
				// TODO Auto-generated method stub
				
				ImageView near_icon = ViewHolder.get(convertView, R.id.near_icon);
				new xUtilsImageLoader(Brand_RecommendationActivity.this).display(near_icon, p.getPimage());

				final FrameLayout ll = ViewHolder.get(convertView, R.id.ll);
				ll.setSelected(false);
				TextView tv_anniu = ViewHolder.get(convertView, R.id.tv_anniu);
				LinearLayout lll_item = ViewHolder.get(convertView, R.id.lll_item);
				int yq = Integer.parseInt(p.getPtotalnum());// 总的数量
				int g = Integer.parseInt(p.getPbuynum());// 已抢
				if (g == yq) {
					tv_anniu.setText("已抢完");
					ll.setSelected(true);
					ll.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						}
					});
					lll_item.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						}
					});
				} else {
					tv_anniu.setText("立刻抢");
					ll.setSelected(false);
					ll.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							JumpUtils.jumpto(Brand_RecommendationActivity.this, ProductDetailsActivity.class, p);
						}
					});
					lll_item.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							JumpUtils.jumpto(Brand_RecommendationActivity.this, ProductDetailsActivity.class, p);
						}
					});
				}
				TextView pname = ViewHolder.get(convertView, R.id.pname);
				pname.setText("" + p.getPname());

				TextView pcomts = ViewHolder.get(convertView, R.id.pcomts);
				pcomts.setText("共" + p.getPtotalnum() + "份");
				TextView price = ViewHolder.get(convertView, R.id.price);
				price.setText("￥" + p.getPrice());
				TextView pbuynum = ViewHolder.get(convertView, R.id.pbuynum);
				pbuynum.setText("已抢" + p.getPbuynum() + "份");
			}

			// 精品推荐和绿色出行
			private void initType0(final ProductDefaultItemModel p, int position, View convertView) {
				// TODO Auto-generated method stub
				ImageView near_icon = ViewHolder.get(convertView, R.id.near_icon);
				new xUtilsImageLoader(Brand_RecommendationActivity.this).display(near_icon, p.getPimage());

				final LinearLayout ll = ViewHolder.get(convertView, R.id.ll);
				ll.setBackgroundResource(R.drawable.layout_click_item);
				ll.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						JumpUtils.jumpto(Brand_RecommendationActivity.this, ProductDetailsActivity.class, p);
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
			}
		};

		xlistview.setAdapter(adapter);
		xlistview.setPullLoadEnable(true);
		xlistview.setPullRefreshEnable(true);
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

		map.put("uid", Utils.getUid(Brand_RecommendationActivity.this));
		map.put("token", Utils.getToken(Brand_RecommendationActivity.this));

		map.put("page", "" + page);
		map.put("maxresult", "" + maxresult);
		if (type.equals("0")) {
			NetUtils.getIns().post(Task.GET_PRODUCT_RECOMMEND_LIST, map, handler);
		}
		// 限量商品
		if (type.equals("1")) {
			NetUtils.getIns().post(Task.GET_PRODUCT_BOUTIQUE_LIST, map, handler);
		}
		// 绿色出行
		if (type.equals("3")) {
			NetUtils.getIns().post(Task.GET_PRODUCT_GERRN_LIST, map, handler);
		}
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
			Utils.ExitPrgress(Brand_RecommendationActivity.this);
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// android:listSelector="@drawable/list_item_color_bg"
		if (task.equals(Task.GET_PRODUCT_RECOMMEND_LIST) || task.equals(Task.GET_PRODUCT_BOUTIQUE_LIST) || task.equals(Task.GET_PRODUCT_GERRN_LIST)) {
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
						Utils.showMessage(Brand_RecommendationActivity.this, "已加载全部数据！");
					}
				} else {
					Utils.showMessage(Brand_RecommendationActivity.this, "服务器错误，错误码:" + result);
				}
			} catch (Exception e) {
			}
		}
	}

	@OnClick({ R.id.return_btn, R.id.queding })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(Brand_RecommendationActivity.this);
			break;
		// case R.id.queding:
		// // if (select_position != -1) {
		// // HashMap<String, String> map = new HashMap<String, String>();
		// // map.put("groupPosition", gr);
		// // JumpUtils.jumpResultfinish(Brand_RecommendationActivity.this,
		// RESULT_OK, list.get(select_position), map);
		// // } else {
		// // Utils.showMessage(Brand_RecommendationActivity.this,
		// "请选择需要添加的商品！");
		// // }
		// break;
		}

	}

}