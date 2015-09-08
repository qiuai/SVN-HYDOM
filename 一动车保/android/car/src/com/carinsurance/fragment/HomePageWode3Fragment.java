package com.carinsurance.fragment;

import java.util.HashMap;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carinsurance.adapter.HomePageWode3Adapter;
import com.carinsurance.infos.UserModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;

public class HomePageWode3Fragment extends BaseFragment implements IXListViewListener {
	private static HomePageWode3Fragment homePage2Fragment;

	private XListView list;

	UserModel user;
	HomePageWode3Adapter apater;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		user = new UserModel();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.xlistview, null);
		initView(view);
		return view;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getUserData();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		try {
			getUserData();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		list = (XListView) view.findViewById(R.id.myListView);
		list.setPullLoadEnable(false);
		list.setPullRefreshEnable(true);
		list.setDivider(null);
		list.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				getUserData();
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub

			}
		});
		list.setAdapter(apater = new HomePageWode3Adapter(getActivity(), user));
		getUserData();
	}

	public void getUserData() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(getActivity())) && !StringUtil.isNullOrEmpty(Utils.getToken(getActivity()))) {
			map.put("uid", Utils.getUid(getActivity()));
			map.put("token", Utils.getToken(getActivity()));
			NetUtils.getIns().post(Task.GET_USER_DATA, map, handler);
		}

	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		if (task.equals(Task.GET_USER_DATA)) {
			list.stopRefresh();
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);

		if (task.equals(Task.GET_USER_DATA)) {
			try {

				list.stopRefresh();

				String result = new JSONObject(message).getString("result");
				if (result.equals("001")) {

					user = JsonUtil.getEntityByJsonString(message, UserModel.class);

					apater.setUser(user);
					apater.notifyDataSetChanged();
				} else {
					list.stopRefresh();
				}

			} catch (Exception e) {
				// TODO: handle exception
				list.stopRefresh();
			}
		}

	}

	public static HomePageWode3Fragment getInstance() {
		if (homePage2Fragment == null) {
			return new HomePageWode3Fragment();
		}
		return homePage2Fragment;
	}

}