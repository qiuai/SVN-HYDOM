package com.carinsurance.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.carinsurance.activity.WebViewActivity;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.Content;
import com.carinsurance.infos.NWFindItemModel;
import com.carinsurance.infos.NWFindModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;

public class HomePage1Fragment extends BaseFragment {
	private static HomePage1Fragment homePage1Fragment;

	private XListView listview;

	int page = 1;
	int maxresult = 10;

	NWFindModel nwFindModel;
	List<NWFindItemModel> list;
	AbstractBaseAdapter<NWFindItemModel> adapter;

	public HomePage1Fragment() {
		// TODO Auto-generated constructor stub
		Content.is_refresh = false;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		try {
			if (Content.is_refresh == true) {
				Content.is_refresh = false;
				list.clear();
				page = 1;
				getFindsList(page++, maxresult);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = new ArrayList<NWFindItemModel>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.xlistview, null);

		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		listview = (XListView) view.findViewById(R.id.myListView);
		// listview.setAdapter(new HomePage1Aapger(getActivity()));
		adapter = new AbstractBaseAdapter<NWFindItemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.homepage1adapter_item, null);
				}
				final NWFindItemModel nwfind = getItem(position);
				FrameLayout jump = ViewHolder.get(convertView, R.id.jump);// (FrameLayout)convertView.findViewById(R.id.jump);
				jump.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						HashMap<String, String> map = new HashMap<String, String>();
						Content.is_refresh = false;
						map.put("title", nwfind.getNwtitle());
						map.put("url", nwfind.getNwurl());
						JumpUtils.jumpActivityForResult(getActivity(), WebViewActivity.class, map, 10);
					}
				});

				ImageView img = ViewHolder.get(convertView, R.id.img);
				new xUtilsImageLoader(getActivity()).display(img, nwfind.getNwimage());

				ImageView is_like = ViewHolder.get(convertView, R.id.is_like);
				is_like.setSelected(true);

				TextView title = ViewHolder.get(convertView, R.id.title);
				title.setText("" + nwfind.getNwtitle());
				TextView likenum = ViewHolder.get(convertView, R.id.likenum);
				likenum.setText("" + nwfind.getNwstar());

				TextView time = ViewHolder.get(convertView, R.id.time);

				String t = (nwfind.getNwdate()).replaceAll("-", ".");

				time.setText("" + t);

				return convertView;
			}
		};
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		listview.setAdapter(adapter);

		listview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				list.clear();
				page = 1;

				getFindsList(page++, maxresult);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				getFindsList(page++, maxresult);
			}
		});
		listview.startLoadMore();
	}

	/**
	 * 获取发现列表
	 * 
	 * @param page
	 *            页数
	 * @param maxresult
	 *            每页最大数量
	 */
	public void getFindsList(int page, int maxresult) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(getActivity()));
		map.put("token", Utils.getToken(getActivity()));
		map.put("page", "" + page);
		map.put("maxresult", "" + maxresult);

		NetUtils.getIns().post(Task.GET_FIND_List, map, handler);

	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		if (task.equals(Task.GET_FIND_List)) {
			try {

				listview.stopLoadMore();
				listview.stopRefresh();
				page--;

			} catch (Exception e) {
			}
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {

		super.initNetmessageSuccess(message, task);
		try {
			Utils.ExitPrgress(getActivity());
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (task.equals(Task.GET_FIND_List)) {
			try {

				listview.stopLoadMore();
				listview.stopRefresh();

				String results = message;
				nwFindModel = JsonUtil.getEntityByJsonString(results, NWFindModel.class);
				if (nwFindModel.getResult().equals("001")) {
					list.addAll(nwFindModel.getList());
					adapter.notifyDataSetChanged();
				} else {
					Utils.showMessage(getActivity(), "系统错误,错误码" + nwFindModel.getResult());
					page--;
				}

			} catch (Exception e) {
				page--;
			}
		}

	};

	public static HomePage1Fragment getInstance() {
		if (homePage1Fragment == null) {
			return new HomePage1Fragment();
		}
		return homePage1Fragment;
	}

}
