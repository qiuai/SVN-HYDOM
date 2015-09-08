package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carinsurance.infos.Content;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.nodes.BalanceNode;
import com.carinsurance.nodes.ConsumeRecords;
import com.carinsurance.nodes.ConsumeTypeNode;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 余额
 * 
 * @author Administrator
 *
 */
public class TheBalanceOfActivity extends BaseWithBundleActivity {
	@ViewInject(R.id.spinner)
	Spinner spinner;

	@ViewInject(R.id.consume_records)
	private XListView recordsList;

	@ViewInject(R.id.add)
	private TextView add;

	@ViewInject(R.id.blance)
	private TextView balance;

	@ViewInject(R.id.loading)
	private View loading;

	private List<ConsumeRecords> datas = new ArrayList<>();
	private BaseAdapter adapter;

	private int curPage = 0;
	private int pageCount = 0;
	private boolean isLoading = false;
	private final static int MAX_RESULT = 20;

	private int curRecordsType = ConsumeTypeNode.RECORDS_TYPE_ALL; // 当前充值记录类型
																	// 默认为ALL
	@ViewInject(R.id.select_mode)
	private TextView selectModeView;

	private PopupWindow popWindow;

	private PopupMenu menu;
	private String[] items;

	@OnClick({ R.id.return_btn, R.id.add })
	private void clickBack(View view) {

		switch (view.getId()) {
		case R.id.return_btn:
			finish();
			break;
		case R.id.add:
			Content.is_refresh = false;

			JumpUtils.jumpActivityForResult(TheBalanceOfActivity.this, SelectRechargeActivity.class, null, 12);
			break;
		}
	}

	@OnClick({ R.id.select_mode })
	private void clicked(View view) {
		getPoupMenu().showAsDropDown(view, 0, DisplayUtil.dip2px(this, 5));

		// menu = new PopupMenu(this, view);
		// new PopupMenu(this, view, Gravity.BOTTOM, R.style.Theme_PopupMenu,
		// R.style.Theme_PopupMenu);
		// menu.getMenuInflater().inflate(R.menu.consume_records_mode,
		// menu.getMenu());
		// menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
		//
		// @Override
		// public boolean onMenuItemClick(MenuItem item) {
		// if (isLoading) {
		// Toast.makeText(getApplicationContext(), "上个数据还在加载中,请稍后再试！",
		// Toast.LENGTH_SHORT).show();
		// popWindow.dismiss();
		// return false;
		// }
		//
		// int tempType = -100;
		// switch (item.getItemId()) {
		// case R.id.consume_records_mode_all:
		// tempType = ConsumeTypeNode.RECORDS_TYPE_ALL;
		// selectModeView.setText("全部");
		// break;
		// case R.id.consume_records_mode_charge:
		// tempType = ConsumeTypeNode.RECORDS_TYPE_RECHARGE;
		// selectModeView.setText("充值");
		// break;
		// case R.id.consume_records_mode_consume:
		// tempType = ConsumeTypeNode.RECORDS_TYPE_CONSUME;
		// selectModeView.setText("消费");
		// break;
		//
		// default:
		// break;
		// }
		//
		// if (curRecordsType != tempType && tempType != -1) {
		// isLoading = true;
		// curRecordsType = tempType;
		// loading.setVisibility(View.VISIBLE);
		// loadContent(1, false);// 加载首页
		// }
		// return false;
		// }
		// });
		//
		// menu.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_the_balance_of);

		ViewUtils.inject(this);

		init();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		try {
			if (Content.is_refresh == true) {
				init();
				Content.is_refresh = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void init() {
		// TODO Auto-generated method stub
		adapter = new ConsumeRecordsAdapter(datas, this);
		recordsList.setAdapter(adapter);
		recordsList.setPullLoadEnable(true);
		recordsList.setPullRefreshEnable(true);
		recordsList.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// 如果正在刷新或加载更多 则返回
				if (isLoading) {
					stopLoading();
					Toast.makeText(getApplicationContext(), "已在加载,请稍后再试！", Toast.LENGTH_SHORT).show();
					return;
				}
				isLoading = true;
				loadContent(1, false);// 加载首页〉
			}

			@Override
			public void onLoadMore() {
				// 如果正在刷新或加载更多 则返回
				if (isLoading) {
					stopLoading();
					Toast.makeText(getApplicationContext(), "已在加载,请稍后再试！", Toast.LENGTH_SHORT).show();
					return;
				}

				if (curPage >= pageCount) { // 当前页是否为最后一页
					stopLoading();
					Toast.makeText(getApplicationContext(), "已无更多内容！", Toast.LENGTH_SHORT).show();
					return;
				}

				isLoading = true;
				loadContent(curPage + 1, true);// 加载下一页
			}
		});

		loading.setVisibility(View.VISIBLE); // 显示加载中进度条
		loadContent(1, false);
	}

	private void stopLoading() {
		recordsList.stopLoadMore();
		recordsList.stopRefresh();
	}

	// 网络请求失败
	@Override
	public void initNetmessageFailure(String message, String task, Bundle tags) {
		super.initNetmessageFailure(message, task, tags);
		if (task.equals(Task.GET_BALANCE_AND_RECORDS)) {
			loading.setVisibility(View.GONE);
			stopLoading();
			isLoading = false;
		}
	}

	// 网络请求成功
	@Override
	public void initNetmessageSuccess(String message, String task, Bundle tags) {
		super.initNetmessageSuccess(message, task, tags);
		if (task.equals(Task.GET_BALANCE_AND_RECORDS)) {
			loading.setVisibility(View.GONE);
			stopLoading();
			BalanceNode resp = handleHttpResult(message);
			if (resp != null) {
				// 成功
				balance.setText(resp.getBalance() + "");
				this.pageCount = resp.getPages();

				boolean isLoadMore = tags.getBoolean(NetUtils.GET_TAG_ISLOADMORE);
				if (!isLoadMore) {
					datas.clear();
				}

				datas.addAll(resp.getRecords());
				adapter.notifyDataSetChanged();
				curPage = tags.getInt(NetUtils.GET_TAG_PAGECODE);
			}

			isLoading = false;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		// 充值完成后要跳到这个 界面
//		if (arg0 == 12 && arg1 == 11) {
//
//		}
	}

	// 解析网络请求返回值
	private BalanceNode handleHttpResult(String data) {
		try {
			JSONObject obj = new JSONObject(data);
			String result = obj.getString("result");
			if (!NetUtils.NET_SUCCESS_001.equals(result)) {
				return null;
			}

			BalanceNode node = new BalanceNode();
			int pages = obj.getInt("pages");
			double balance = obj.getDouble("balance");
			node.setPages(pages);
			node.setBalance(balance);

			List<ConsumeRecords> recs = new ArrayList<>();
			JSONArray recsArray = obj.getJSONArray("list");
			for (int i = 0; i < recsArray.length(); i++) {
				JSONObject jn = recsArray.getJSONObject(i);
				ConsumeRecords rec = new ConsumeRecords();
				rec.setFtext(jn.getString("ftext"));
				rec.setFmoney(jn.getString("fmoney"));
				rec.setFdate(jn.getString("fdate"));

				recs.add(rec);
			}

			node.setRecords(recs);

			return node;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void loadContent(int page, boolean isLoadMore) {
		HashMap<String, String> params = new HashMap<>();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(getApplicationContext())) && !StringUtil.isNullOrEmpty(Utils.getToken(getApplicationContext()))) {
			params.put("uid", Utils.getUid(getApplicationContext()));
			params.put("token", Utils.getToken(getApplicationContext()));
		}
		params.put("page", page + "");
		params.put("maxresult", MAX_RESULT + "");
		params.put("type", curRecordsType + "");

		Bundle bundle = new Bundle();
		bundle.putInt(NetUtils.GET_TAG_PAGECODE, page);
		bundle.putBoolean(NetUtils.GET_TAG_ISLOADMORE, isLoadMore);

		NetUtils.getIns().post(Task.GET_BALANCE_AND_RECORDS, params, handler, bundle);
	}

	private class ConsumeRecordsAdapter extends BaseAdapter {
		private class ViewHolder {
			TextView title;
			TextView money;
			TextView date;
		}

		private List<ConsumeRecords> datas;
		private LayoutInflater inflater;

		public ConsumeRecordsAdapter(List<ConsumeRecords> datas, Context context) {
			this.datas = datas;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
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
				convertView = inflater.inflate(R.layout.consume_records_item, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.consume_records_item_title);
				holder.money = (TextView) convertView.findViewById(R.id.consume_records_item_money);
				holder.date = (TextView) convertView.findViewById(R.id.consume_records_item_date);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText(datas.get(position).getFtext());
			holder.money.setText(datas.get(position).getFmoney());
			holder.date.setText(datas.get(position).getFdate());

			return convertView;
		}
	}

	private PopupWindow getPoupMenu() {
		if (selectModeView.getText().equals("全部")) {
			items = new String[] { "充值", "消费" };
		} else if (selectModeView.getText().equals("充值")) {
			items = new String[] { "全部", "消费" };
		} else if (selectModeView.getText().equals("消费")) {
			items = new String[] { "全部", "充值" };
		}

		ListView lv = new ListView(this);
		lv.setLayoutParams(new android.widget.AbsListView.LayoutParams(android.widget.AbsListView.LayoutParams.WRAP_CONTENT, android.widget.AbsListView.LayoutParams.WRAP_CONTENT));
		lv.setSelector(new PaintDrawable(Color.TRANSPARENT));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int tempType = -100;
				if (items[position].equals("全部")) {
					tempType = ConsumeTypeNode.RECORDS_TYPE_ALL;
					selectModeView.setText("全部");
				} else if (items[position].equals("充值")) {
					tempType = ConsumeTypeNode.RECORDS_TYPE_RECHARGE;
					selectModeView.setText("充值");
				} else if (items[position].equals("消费")) {
					tempType = ConsumeTypeNode.RECORDS_TYPE_CONSUME;
					selectModeView.setText("消费");
				}

				if (curRecordsType != tempType && tempType != -100) {
					isLoading = true;
					curRecordsType = tempType;
					loading.setVisibility(View.VISIBLE);
					loadContent(1, false);// 加载首页
				}

				popWindow.dismiss();
			}
		});

		BaseAdapter adapter = new MenuAdapter(items);
		lv.setAdapter(adapter);
		lv.measure(0, 0);
		popWindow = new PopupWindow(lv, lv.getMeasuredWidth(), LayoutParams.WRAP_CONTENT);
		popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_bg1));
		popWindow.setFocusable(true);
		popWindow.setTouchable(true);
		popWindow.setOutsideTouchable(true);

		return popWindow;
	}

	private class MenuAdapter extends BaseAdapter {
		private String[] items;

		public MenuAdapter(String[] items) {
			this.items = items;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return items[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new TextView(TheBalanceOfActivity.this);
				convertView.setLayoutParams(new android.widget.AbsListView.LayoutParams(android.widget.AbsListView.LayoutParams.WRAP_CONTENT, android.widget.AbsListView.LayoutParams.WRAP_CONTENT));
				((TextView) convertView).setTextColor(Color.RED);
				((TextView) convertView).setTextSize(DisplayUtil.sp2px(TheBalanceOfActivity.this, 8));
				convertView.setPadding(DisplayUtil.dip2px(TheBalanceOfActivity.this, 20), DisplayUtil.dip2px(TheBalanceOfActivity.this, 10), DisplayUtil.dip2px(TheBalanceOfActivity.this, 20), DisplayUtil.dip2px(TheBalanceOfActivity.this, 10));
			}

			((TextView) convertView).setText(items[position]);

			return convertView;
		}

	}
}
