package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.infos.MyCarInfosModel;
import com.carinsurance.infos.ProductDefaultItemModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.nodes.GoodNode;
import com.carinsurance.nodes.HotCategoryListResp;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HotCategoryListActivity extends BaseWithBundleActivity implements
		OnItemClickListener {
	private final int REQUEST_TO_CARMANAGER = 100;

	private XListView list;
	private int page = 0; // 当前页码
	private final static int MAX_RESULT = 20;
	private List<GoodNode> datas = new ArrayList<GoodNode>();
	private BaseAdapter adapter;

	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.loading)
	private View loading;

	@ViewInject(R.id.btn_tv_shipeichexing)
	private View btn_shipeichexing;
	@ViewInject(R.id.tv_shipeichexing)
	private TextView tv_shipeichexing;

	private boolean isLoading = false;
	private int maxPage = 0;
	private String curPcId = "";

	private String curCmid = ""; // 当前适配的车型ID

	private xUtilsImageLoader imageLoader;

	private SortModel model;

	@OnClick(R.id.return_btn)
	private void returnClick(View view) {
		finish();
	}

	@OnClick(R.id.btn_tv_shipeichexing)
	private void adpterCarInfos(View view) {
		// 判断是否登录
		if (!StringUtil.isNullOrEmpty(Utils.getUid(getApplicationContext()))
				&& !StringUtil.isNullOrEmpty(Utils
						.getToken(getApplicationContext()))) {
			JumpUtils.jumpActivityForResult(this, CarManageActivity.class,
					null, REQUEST_TO_CARMANAGER);
		} else {
			JumpUtils.jumpto(this, LoginActivity.class, null);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot_category_list);
		ViewUtils.inject(this);
		imageLoader = new xUtilsImageLoader(this);

		list = (XListView) findViewById(R.id.hot_category_list_lv);
		adapter = new HotCategoryListAdapter(datas, this, imageLoader);
		list.setAdapter(adapter);
		Intent intent = getIntent();
		curPcId = intent.getStringExtra("id");

		if (curPcId == null || curPcId.equals("")) {
			Toast.makeText(this, "非法进入,产品分类ID为空", Toast.LENGTH_SHORT).show();
			finish();
		}

		String name = intent.getStringExtra("name");
		model = (SortModel) JumpUtils.getSerializable(this);
		if (model == null) {
			Toast.makeText(this, "非法进入,上级数据为空", Toast.LENGTH_SHORT).show();
			finish();
		}
		// 车型适配数据
		tv_shipeichexing.setText("适配车型:"+model.getCs_name());
		curCmid = model.getCmid();

		title.setText(name);
		list.setPullLoadEnable(true);
		list.setPullRefreshEnable(true);
		list.setOnItemClickListener(this);
		// TODO设置滑动时不加载图片
		list.setOnScrollListener(new PauseOnScrollListener(imageLoader
				.getBitmapUtils(), false, false));
		list.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// 如果正在刷新或加载更多 则返回
				if (isLoading) {
					stopLoading();
					Toast.makeText(getApplicationContext(), "正在加载,请稍后再试！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				isLoading = true;
				loadCotent(1, curPcId, curCmid, false);// 加载首页〉
			}

			@Override
			public void onLoadMore() {
				// 如果正在刷新或加载更多 则返回
				if (isLoading) {
					stopLoading();
					Toast.makeText(getApplicationContext(), "正在加载,请稍后再试！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (page >= maxPage) { // 当前页是否为最后一页
					stopLoading();
					Toast.makeText(getApplicationContext(), "已无更多内容！",
							Toast.LENGTH_SHORT).show();
					return;
				}

				isLoading = true;
				loadCotent(page + 1, curPcId, curCmid, true);// 加载下一页
			}
		});

		loading.setVisibility(View.VISIBLE);
		loadCotent(1, curPcId, curCmid, false);
	}

	// 加载内容
	// private void loadCotent(int page, String pcid){
	// loadCotent(page, pcid, null);
	// }
	/***
	 * 
	 * @param page
	 * @param pcid
	 * @param cmId
	 *            车型ID
	 */
	private void loadCotent(int pageNum, String pcid, String cmId,
			boolean isLoadMore) {
		HashMap<String, String> params = new HashMap<>();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(getApplicationContext()))
				&& !StringUtil.isNullOrEmpty(Utils
						.getToken(getApplicationContext()))) {
			params.put("uid", Utils.getUid(getApplicationContext()));
			params.put("token", Utils.getToken(getApplicationContext()));
		}
		params.put("page", pageNum + "");
		params.put("maxresult", MAX_RESULT + "");
		params.put("pcid", pcid + "");
		if (!StringUtil.isNullOrEmpty(cmId)) {
			// 传递车型数据 适配内容
			// TODO 热卖商品适配车型
		}
		System.out.println(">>>cur request page:" + pageNum);
		Bundle bundle = new Bundle();
		bundle.putInt(NetUtils.GET_TAG_PAGECODE, pageNum);
		bundle.putBoolean(NetUtils.GET_TAG_ISLOADMORE, isLoadMore);

		NetUtils.getIns().post(Task.GET_HOT_CATEGORY_LIST, params, handler,
				bundle);
	}

	// 网络请求成功
	@Override
	public void initNetmessageSuccess(String message, String task, Bundle tags) {
		super.initNetmessageSuccess(message, task, tags);
		if (task.equals(Task.GET_HOT_CATEGORY_LIST)) {
			loading.setVisibility(View.GONE);
			stopLoading();

			HotCategoryListResp resp = handleHttpResult(message);
			if (resp == null) {

			} else {
				boolean isLoadMore = tags
						.getBoolean(NetUtils.GET_TAG_ISLOADMORE);
				if (!isLoadMore) {
					datas.clear();
				}
				datas.addAll(resp.getDatas());
				maxPage = resp.getTotalPageCount();
				adapter.notifyDataSetChanged();
				page = tags.getInt(NetUtils.GET_TAG_PAGECODE); // 更新当前页码
				System.out.println(">>>update page:" + page);
			}

			isLoading = false;
		}
	}

	private void stopLoading() {
		list.stopLoadMore();
		list.stopRefresh();
	}

	// 网络请求失败
	@Override
	public void initNetmessageFailure(String message, String task, Bundle tags) {
		super.initNetmessageFailure(message, task, tags);
		if (task.equals(Task.GET_HOT_CATEGORY_LIST)) {
			loading.setVisibility(View.GONE);
			stopLoading();
			isLoading = false;
		}
	}

	// 解析网络请求返回值
	private HotCategoryListResp handleHttpResult(String data) {

		try {
			HotCategoryListResp resp = new HotCategoryListResp();
			List<GoodNode> ds = new ArrayList<GoodNode>();

			JSONObject obj = new JSONObject(data);
			String result = obj.getString("result");
			if (!NetUtils.NET_SUCCESS_001.equals(result)) {
				return null;
			}

			resp.setTotalPageCount(obj.getInt("pages"));
			JSONArray lists = obj.getJSONArray("list");
			if (lists != null) {
				for (int i = 0; i < lists.length(); i++) {
					JSONObject info = lists.getJSONObject(i);
					GoodNode node = new GoodNode();
					node.setPid(info.getString("pid"));
					node.setPname(info.getString("pname"));
					node.setPimage(info.getString("pimage"));
					node.setPbuynum(info.getInt("pbuynum"));
					node.setPrice(info.getDouble("price"));
					node.setPcomts(info.getInt("pcomts"));

					ds.add(node);
				}
			}

			resp.setDatas(ds);
			return resp;
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println(">>>error");
		}
		return null;
	}

	private class HotCategoryListAdapter extends BaseAdapter {
		private class ViewHolder {
			ImageView img;
			TextView name;
			TextView buycounts;
			TextView price;
			TextView comts;
		}

		private List<GoodNode> datas;
		private LayoutInflater inflater;
		private xUtilsImageLoader imageLoader;

		public HotCategoryListAdapter(List<GoodNode> datas, Context context,
				xUtilsImageLoader imageLoader) {
			this.datas = datas;
			inflater = LayoutInflater.from(context);
			this.imageLoader = imageLoader;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.hot_category_list_item,
						null);
				holder = new ViewHolder();
				holder.img = (ImageView) convertView
						.findViewById(R.id.hot_category_list_item_img);
				holder.name = (TextView) convertView
						.findViewById(R.id.hot_category_list_item_name);
				holder.buycounts = (TextView) convertView
						.findViewById(R.id.hot_category_list_item_buynum);
				holder.price = (TextView) convertView
						.findViewById(R.id.hot_category_list_item_price);
				holder.comts = (TextView) convertView
						.findViewById(R.id.hot_category_list_item_comts);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			imageLoader.display(holder.img, datas.get(position).getPimage());

			holder.name.setText(datas.get(position).getPname());
			String buynum = getString(R.string.hot_category_buynum,
					datas.get(position).getPbuynum());
			holder.buycounts.setText(buynum);
			String price = getString(R.string.hot_category_price,
					datas.get(position).getPrice());
			holder.price.setText(price);
			String comts = getString(R.string.hot_category_comts,
					datas.get(position).getPcomts());
			holder.comts.setText(comts);

			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		GoodNode node = (GoodNode) list.getItemAtPosition(position);
		String pid = node.getPid();
		ProductDefaultItemModel mode = new ProductDefaultItemModel();
		mode.setPid(pid);
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("is_show_shipeichexing", "0");
		
		JumpUtils.jumpto(this, ProductDetailsActivity.class, mode,map);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// 车管家返回值
		if (requestCode == REQUEST_TO_CARMANAGER && resultCode == RESULT_OK) {
			MyCarInfosModel model = (MyCarInfosModel) data
					.getSerializableExtra("data");
			if (model == null) {
				tv_shipeichexing.setText(getString(R.string.adpater_chexing,
						"无"));
				// 更新数据
				if (StringUtil.isNullOrEmpty(curCmid)) { // 如果当前本身就没有适配任何车型
					// 不做任何操作
				} else {
					curCmid = "";
					loadCotent(1, curPcId, curCmid, false); // 加载首页
				}
			} else {
				tv_shipeichexing.setText(getString(R.string.adpater_chexing,
						model.getCsname()));
				// 更新数据
				if (model.getCmid().equals(curCmid)) { // 如果与当前适配车型一致
					// 不做任何操作
				} else {
					curCmid = model.getCmid();
					loadCotent(1, curPcId, model.getCmid(), false); // 加载首页
				}
			}

		}
	}
}
