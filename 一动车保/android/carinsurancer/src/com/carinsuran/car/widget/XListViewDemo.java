package com.carinsuran.car.widget;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;

/**
 * 下拉刷新使用说明</br> XListView.IXListViewListener：下拉刷新和上上拉加载更多接口</br>
 * 由于该美食app应用访问数据主要都在adpter适配器中完成
 * ，所以通常情况下都让adpter适配器去实现XListView.IXListViewListener接口，而非Activity。
 * 
 * @author zc
 * 
 */
public class XListViewDemo extends Activity implements
		XListView.IXListViewListener {
	// 下拉刷新listView组件
	private XListView refreshListView;

	public void initListView() {
		// 找到该组件设置是否能够实现下拉刷新或上拉加载
		refreshListView.setPullLoadEnable(true);
		// 设置下拉刷新和上拉加载回调函数
		refreshListView.setXListViewListener(this);
	}

	@Override
	public void onRefresh() {
		// 下拉刷新回调，一般执行访问网络的操作。完成任务之后许要调用onLoad()方法隐藏头部或底部视图

	}

	@Override
	public void onLoadMore() {
		// 上拉加载更多回调，一般执行访问网络的操作。完成任务之后许要调用onLoad()方法隐藏头部或底部视图

	}

	public void onLoad() {
		refreshListView.stopRefresh();
		refreshListView.stopLoadMore();// 刷新或加载更多完成后停止 删除listview的header和footer
		refreshListView.setRefreshTime(currentTime());
	}

	public String currentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String time = df.format(new Date());
		return time;

	}
}
