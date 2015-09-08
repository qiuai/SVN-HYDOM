package com.carinsurance.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import com.carinsurance.activity.Shop_detailsActivity;
import com.carinsurance.adapter.HomeMenDIanSpinnderAdapter;
import com.carinsurance.adapter.HomePageMenDian2Adapter;
import com.carinsurance.infos.MendianListModel;
import com.carinsurance.infos.MendianModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.PopWindowUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;

public class HomePageMenDian2Fragment extends BaseFragment implements IXListViewListener {
	private static HomePageMenDian2Fragment homePage2Fragment;

	private XListView xListView;
	// 顶部的三个spinnder
	FrameLayout spinner1;
	Spinner spinner2, spinner3;
	HomePageMenDian2Adapter adapter;
	HomeMenDIanSpinnderAdapter homeMenDIanSpinnderAdapternew;
	// public android.os.Handler handler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// if (msg.what == NetUtils.Net_Failure) {
	// String tag = msg.getData().getString(NetUtils.GET_TAG, -1);
	// switch (tag) {
	// case Task.GETMENDIANLIST:
	// try {
	// Utils.showMessage(getActivity(), "请检查网络连接");
	// page--;
	// xListView.stopLoadMore();
	// xListView.stopRefresh();
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// break;
	// }
	// }
	// if (msg.what == NetUtils.Net_SUCCESS) {
	// int tag = msg.getData().getInt(NetUtils.GET_TAG, -1);
	// switch (tag) {
	// case Task.GETMENDIANLIST:
	// try {
	// String results = msg.getData().getString(NetUtils.GET_MSG, "");
	// Result result = JsonUtil.getEntityByJsonString(results, Result.class);
	// list = new MendianListModel();
	// list = JsonUtil.getEntityByJsonString(result.getData(),
	// MendianListModel.class);
	// if (result.getCode().equals("0")) {
	// mendianModels.addAll(list.getData());
	// adapter.notifyDataSetChanged();
	// xListView.stopLoadMore();
	// xListView.stopRefresh();
	// } else if (result.getCode().equals("1")) {
	// Utils.showMessage(getActivity(), "已加载全部数据！");
	// page--;
	// xListView.stopLoadMore();
	// xListView.stopRefresh();
	// }
	// // mendianModel=listModel.getData().get(0);
	// // baiduWeatherModel =
	// // JsonUtil.getEntityByJsonString(results,
	// // BaiduWeatherModel.class);
	// // adapter.setWeather(baiduWeatherModel);
	// // adapter.notifyDataSetInvalidated();
	// // xListView.stopLoadMore();
	// // xListView.stopRefresh();
	// // Gson gs=new Gson();
	// // weather_list = (List<Weather>)
	// // msg.getData().getString(NetUtils.GET_MSG);
	// // Log.v("aaa", "序列化成功==》" + weather_list.toString());
	// // // adapter.setWeather(weather_list);
	// // adapter.notifyDataSetInvalidated();
	// // xlistView.stopLoadMore();
	// // xlistView.stopRefresh();
	// } catch (Exception e) {
	// // TODO: handle exception
	// xListView.stopLoadMore();
	// xListView.stopRefresh();
	// }
	// break;
	// }
	// }
	//
	// };
	// };

	MendianListModel list;
	List<MendianModel> mendianModels;
	int page = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.homepage_center2, null);
		initView(view);
		initPopWindow(view);
		return view;
	}

	/**
	 * 
	 * @param areaId
	 *            区域ID
	 * @param orderType
	 *            排序方式
	 * @param storeType
	 *            门店类型
	 * @param page2
	 *            当前页码
	 */
	private void getNetData(String areaId, String orderType, String storeType, int page2) {
		// if(String)
		StringBuilder buff = new StringBuilder("");
		buff.append("api/store/list.do?" + page2);
		if (!StringUtil.isNullOrEmpty(areaId)) {
			buff.append("&orderType=" + areaId);
		}
		if (!StringUtil.isNullOrEmpty(orderType)) {
			buff.append("&orderType=" + orderType);
		}
		if (!StringUtil.isNullOrEmpty(storeType)) {
			buff.append("&storeType=" + storeType);
		}
		NetUtils net = NetUtils.getIns();

//		net.get(buff.toString(), handler);
	}

	private void initPopWindow(final View view) {
		// TODO Auto-generated method stub
		spinner1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final FrameLayout import_layout = (FrameLayout) getActivity().findViewById(R.id.import_layout);
				// ViewTreeObserver vto2 = import_layout.getViewTreeObserver();
				// vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				// @Override
				// public void onGlobalLayout() {
				// Log.v("bbb","aaaa1");
				// import_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// //
				// textView.append("\n\n"+imageView.getHeight()+","+imageView.getWidth());
				// }
				// });

				PopWindowUtils.showPop(getActivity(), v, import_layout.getHeight());

			}
		});
		// popupWindow.showAtLocation(view.findViewById(R.id.spinner1),
		// Gravity.BOTTOM, 500,
		// 0);
	}

	private void initView(View view) {
		homeMenDIanSpinnderAdapternew = new HomeMenDIanSpinnderAdapter(getActivity());
		adapter = new HomePageMenDian2Adapter(getActivity(), mendianModels);
		// TODO Auto-generated method stub
		// spinner1=(Spinner) view.findViewById(R.id.spinner1);
		spinner1 = (FrameLayout) view.findViewById(R.id.spinner1);
		spinner2 = (Spinner) view.findViewById(R.id.spinner2);
		spinner3 = (Spinner) view.findViewById(R.id.spinner3);
		// spinner1.setAdapter(new );
		SimpleAdapter spinneradapter = new SimpleAdapter(getActivity(), getData(), R.layout.spinner_item_bg, new String[] { "content" }, new int[] { R.id.content });

		spinner2.setAdapter(spinneradapter);
		spinner3.setAdapter(spinneradapter);
		xListView = (XListView) view.findViewById(R.id.mylistview);
		xListView.setAdapter(adapter);
		xListView.setXListViewListener(this);
		xListView.startLoadMore();
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
//				JumpUtils.jumpto(getActivity(), Shop_detailsActivity.class, null);
				JumpUtils.jumpto(getActivity(), Shop_detailsActivity.class, null);
			}
		});

	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", "G1");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("content", "G2");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("content", "G3");
		list.add(map);

		return list;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		list = new MendianListModel();
		mendianModels = new ArrayList<MendianModel>();
		page = 1;
		 getNetData(null, null, null, page++);

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		// getNetData(null, null, null, page++);
	}

	public static HomePageMenDian2Fragment getInstance() {
		if (homePage2Fragment == null) {
			return new HomePageMenDian2Fragment();
		}
		return homePage2Fragment;
	}

}