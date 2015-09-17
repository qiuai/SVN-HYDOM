package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.nodes.GoodsCommentListNode;
import com.carinsurance.nodes.GoodsCommentNode;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.MathUtils;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/***
 * 
 * @author Administrator 商品的评论数据展示
 */
public class GoodsDiscussShowActivity extends BaseWithBundleActivity {
	@ViewInject(R.id.goods_discuss_list)
	private XListView listView;
	private List<GoodsCommentNode> datas = new ArrayList<>();
	private BaseAdapter adapter;

	@ViewInject(R.id.loading)
	private View loading;

	private int curPage = 0;
	private int pageCount = -1;
	private boolean isLoading = false;
	private final static int MAX_RESULT = 20;

	private String pid; // 产品ID

	private xUtilsImageLoader imgeLoader;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_discuss_list);
		ViewUtils.inject(this);
		pid = getIntent().getStringExtra("data");
		if (StringUtil.isNullOrEmpty(pid)) {
			Toast.makeText(this, "产品编码不能为空", Toast.LENGTH_SHORT).show();
			finish();
		}

		imgeLoader = new xUtilsImageLoader(this);
		adapter = new GoodsDiscussAdapter(datas, this, imgeLoader);
		listView.setAdapter(adapter);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setOnScrollListener(new PauseOnScrollListener(imgeLoader.getBitmapUtils(), false, false));
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				if (isLoading) {
					stopLoading();
					Toast.makeText(GoodsDiscussShowActivity.this, "已在加载中,请勿重复加载", Toast.LENGTH_SHORT).show();
					return;
				}

				isLoading = true;
				loadContent(1, pid, false);// 加载首页〉
			}

			@Override
			public void onLoadMore() {
				if (isLoading) {
					stopLoading();
					Toast.makeText(GoodsDiscussShowActivity.this, "已在加载中,请勿重复加载", Toast.LENGTH_SHORT).show();
					return;
				}
				if (curPage >= pageCount) { // 当前页是否为最后一页
					stopLoading();
					Toast.makeText(getApplicationContext(), "已无更多内容！", Toast.LENGTH_SHORT).show();
					return;
				}

				isLoading = true;
				loadContent(curPage + 1, pid, true);// 加载下一页
			}
		});

		loading.setVisibility(View.VISIBLE);
		loadContent(1, pid, false);// 加载首页
	}

	@OnClick(R.id.return_btn)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(GoodsDiscussShowActivity.this);
			break;
		}
	}

	/***
	 * 
	 * @param page
	 *            加载页码
	 * @param pid
	 *            产品ID
	 */
	private void loadContent(int page, String pid, boolean isLoadMore) {
		HashMap<String, String> params = new HashMap<>();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(getApplicationContext())) && !StringUtil.isNullOrEmpty(Utils.getToken(getApplicationContext()))) {
			params.put("uid", Utils.getUid(getApplicationContext()));
			params.put("token", Utils.getToken(getApplicationContext()));
		}
		params.put("page", page + "");
		params.put("maxresult", MAX_RESULT + "");
		params.put("pid", pid + "");

		Bundle bundle = new Bundle();
		bundle.putInt(NetUtils.GET_TAG_PAGECODE, page);
		bundle.putBoolean(NetUtils.GET_TAG_ISLOADMORE, isLoadMore);

		NetUtils.getIns().post(Task.GET_PRODUCT_COMMENTS, params, handler, bundle);
	}

	private void stopLoading() {
		listView.stopLoadMore();
		listView.stopRefresh();
	}

	@Override
	public void initNetmessageFailure(String message, String task, Bundle tags) {
		super.initNetmessageFailure(message, task, tags);
		if (task.equals(Task.GET_PRODUCT_COMMENTS)) {
			// 加载失败
			loading.setVisibility(View.GONE);
			stopLoading();
			isLoading = false;
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task, Bundle tags) {
		super.initNetmessageSuccess(message, task, tags);
		if (task.equals(Task.GET_PRODUCT_COMMENTS)) {
			loading.setVisibility(View.GONE);
			stopLoading();
			isLoading = false;
			// 网络请求成功
			GoodsCommentListNode result = handleHttpResult(message);
			if (result == null) {
				// 数据请求失败
			} else {
				// 数据请求成功
				// 更新
				boolean isLoadMore = tags.getBoolean(NetUtils.GET_TAG_ISLOADMORE);
				if (!isLoadMore) {
					datas.clear();
				}
				datas.addAll(result.getDatas());
				pageCount = result.getPages();
				adapter.notifyDataSetChanged();
				isLoading = false;
				// 更新当前页码
				curPage = tags.getInt(NetUtils.GET_TAG_PAGECODE);
			}
		}
	}

	private GoodsCommentListNode handleHttpResult(String msg) {
		try {
			JSONObject obj = new JSONObject(msg);
			String result = obj.getString("result");
			if (!NetUtils.NET_SUCCESS_001.equals(result)) {
				return null;
			}
			GoodsCommentListNode resultNode = new GoodsCommentListNode();
			int pageCount = obj.getInt("pages");
			resultNode.setPages(pageCount);

			JSONArray listNode = obj.getJSONArray("list");
			List<GoodsCommentNode> nodes = new ArrayList<>();
			for (int i = 0; i < listNode.length(); i++) {
				JSONObject n = listNode.getJSONObject(i);
				GoodsCommentNode gcn = new GoodsCommentNode();
				gcn.setCid(n.getString("cid"));
				gcn.setCmname(n.getString("cmname"));
				gcn.setContent(n.getString("content"));
				gcn.setCperson(n.getString("cperson"));
				gcn.setCpPhoto(n.getString("cphoto"));
				gcn.setCtime(n.getString("ctime"));
				gcn.setStar(Integer.parseInt(n.getString("star")));

				JSONArray imglist = n.getJSONArray("imglist");
				String[] imgs = new String[imglist.length()];
				for (int j = 0; j < imglist.length(); j++) {
					imgs[j] = imglist.getJSONObject(j).getString("img");
				}

				gcn.setImglist(imgs);
				nodes.add(gcn);
			}

			resultNode.setDatas(nodes);
			return resultNode;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private class GoodsDiscussAdapter extends BaseAdapter {
		private class ViewHolder {
			ImageView headImage;
			TextView tv_name;
			TextView tv_time;
			TextView tv_carname;
			TextView content;
			RatingBar starBar;
			GridView imagesList;
		}

		private List<GoodsCommentNode> datas = new ArrayList<>();
		private LayoutInflater inflater;
		private xUtilsImageLoader ximageLoader;
		private Context context;
		public ImageLoader imageLoader;

		public GoodsDiscussAdapter(List<GoodsCommentNode> datas, Context context, xUtilsImageLoader loader) {
			this.datas = datas;
			inflater = LayoutInflater.from(context);
			this.ximageLoader = loader;
			this.context = context;
			imageLoader = ImageLoader.getInstance();
			DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnFail(R.drawable.no_image).showImageOnLoading(R.drawable.no_image).build();
			// imageLoader.displayImage(uri, imageView, options);
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(GoodsDiscussShowActivity.this).defaultDisplayImageOptions(options).build();
			imageLoader.init(config);// ImageLoaderConfiguration.createDefault(context)
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
				convertView = inflater.inflate(R.layout.goods_discuss_list_item, null);
				holder = new ViewHolder();
				holder.content = (TextView) convertView.findViewById(R.id.goods_discuss_list_item_content);
				holder.headImage = (ImageView) convertView.findViewById(R.id.goods_discuss_list_item_headImg);
				holder.imagesList = (GridView) convertView.findViewById(R.id.goods_discuss_list_item_images);
				holder.starBar = (RatingBar) convertView.findViewById(R.id.goods_discuss_list_item_star);
				holder.tv_carname = (TextView) convertView.findViewById(R.id.goods_discuss_list_item_carname);
				holder.tv_name = (TextView) convertView.findViewById(R.id.goods_discuss_list_item_uname);
				holder.tv_time = (TextView) convertView.findViewById(R.id.goods_discuss_list_item_time);
				holder.imagesList.setSelector(new ColorDrawable(Color.TRANSPARENT));
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.content.setText(datas.get(position).getContent());

			if (!StringUtil.isNullOrEmpty(datas.get(position).getCpPhoto())) {

				imageLoader.displayImage(Task.img_url + datas.get(position).getCpPhoto(), holder.headImage);
				// imageLoader.displayImage(datas.get(position).getCpPhoto(),
				// holder.headImage);
				// imageLoader.display(holder.headImage,
				// datas.get(position).getCpPhoto());
			} else {
				holder.headImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
			}

			holder.starBar.setProgress(datas.get(position).getStar());// (int)
																		// MathUtils.mul(datas.get(position).getStar(),
																		// 10)
			holder.tv_carname.setText(datas.get(position).getCmname());
			holder.tv_name.setText(datas.get(position).getCperson());
			holder.tv_time.setText(datas.get(position).getCtime());

			// 加载评论图片集
			String[] imgs = datas.get(position).getImglist();
			if (imgs != null && imgs.length > 0) {
				holder.imagesList.setAdapter(new DiscussPicturesAdapter(imgs, context, ximageLoader));
				holder.imagesList.setOnScrollListener(new PauseOnScrollListener(ximageLoader.getBitmapUtils(), false, false));
			}
			return convertView;
		}
	}

	private class DiscussPicturesAdapter extends BaseAdapter {
		private String[] datas;
		private LayoutInflater inflater;
		private xUtilsImageLoader imageloader;

		public DiscussPicturesAdapter(String[] datas, Context context, xUtilsImageLoader imageloader) {
			this.datas = datas;
			inflater = LayoutInflater.from(context);
			this.imageloader = imageloader;
		}

		@Override
		public int getCount() {
			return datas.length;
		}

		@Override
		public Object getItem(int position) {
			return datas[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.goods_discuss_pics_list_item, null);
			}

			ImageView img = (ImageView) convertView.findViewById(R.id.goods_discuss_pics_list_item_img);
//			Log.v("aaa","");
			Log.v("aaa", "图片地址:==>"+datas[position]);
			imageloader.display(img, datas[position]);

			return convertView;
		}
	}
}
