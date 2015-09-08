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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.nodes.AddrAreaNode;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ChoiceAreaAcitivity extends BaseActivity implements
		OnItemClickListener {
	@ViewInject(R.id.area_list)
	private ListView list;
	@ViewInject(R.id.return_btn)
	private ImageView returnBtn;

	private List<AddrAreaNode> firstLevelData = new ArrayList<>();
	private List<AddrAreaNode> datas = new ArrayList<>();
	private BaseAdapter adapter;

	private List<AddrAreaNode> selectedNodes = new ArrayList<>();

	private String curId = null;

	@OnClick(R.id.return_btn)
	private void returnClick(View view){
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice_area);
		ViewUtils.inject(this);

		adapter = new ChoiceAreaAdapter(datas, this);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		updateFirstLevel();
	}

	private class ChoiceAreaAdapter extends BaseAdapter {
		private List<AddrAreaNode> datas;
		private LayoutInflater inflater;

		public ChoiceAreaAdapter(List<AddrAreaNode> datas, Context context) {
			this.datas = datas;
			inflater = LayoutInflater.from(context);
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
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.choice_area_item, null);
			}

			TextView tv = (TextView) convertView
					.findViewById(R.id.choice_area_item_name);
			tv.setText(datas.get(position).getName());

			if (position < selectedNodes.size()) {
				// 设置选中背景色
				convertView.setBackgroundColor(Color.parseColor("#d2d2d2"));
				tv.setTextColor(Color.BLACK);
			} else {
				// 设置非选中背景色
				convertView.setBackgroundColor(Color.TRANSPARENT);
				tv.setTextColor(Color.parseColor("#8F8F8F"));
			}

			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		String curParentId = datas.get(position).getParentId();
		curId = datas.get(position).getAid();
		if (position < selectedNodes.size()) { // 更新替换
			// 更新selectedData
			if (curParentId == null) { // 第一级
				selectedNodes.clear();
			} else {
				List<AddrAreaNode> temp = new ArrayList<>();
				for (int i = 0; i < selectedNodes.size(); i++) {
					if (curParentId.equals(selectedNodes.get(i).getParentId())) {
						break;
					} else {
						temp.add(selectedNodes.get(i));
					}
				}

				selectedNodes = temp;
			}
			// 获取新级别数据
			if (datas.get(position).isFirstLevel()) { // 如果是第一级分类，数据一定存在
				System.out.println(">>>first page update");
				updateDatasByLocal(selectedNodes, firstLevelData);
			} else if (!datas.get(position).isIinited()) {
				// 网络获取数据
				System.out.println(">>>child page network");
				System.out.println(">>>curParentId:"
						+ datas.get(position).getParentId());
				System.out.println(">>>Name:" + datas.get(position).getName());

				// updateChildLevel(curParentId);
				updateChildLevel(selectedNodes.get(selectedNodes.size() - 1)
						.getAid()); // 防止PARENT_ID混乱
			} else if (datas.get(position).isIinited()) {
				updateData(datas.get(position).getChildrens());
				System.out.println(">>>child page update");
			}
		} else {
			selectedNodes.add(datas.get(position));
			// 更新子元素
			if (!datas.get(position).isIinited()) {
				// 网络获取数据
				System.out.println(">>>child page network");
				updateChildLevel(datas.get(position).getAid());
			} else if (datas.get(position).isIinited()) {
				updateData(datas.get(position).getChildrens());
				System.out.println(">>>child page update");
			}
		}

	}

	private void updateData(List<AddrAreaNode> newDatas) {
		updateDatasByLocal(selectedNodes, newDatas);
	}

	private void updateDatasByLocal(List<AddrAreaNode> head,
			List<AddrAreaNode> newDatas) {
		selectedNodes = head;
		datas.clear();
		datas.addAll(head);
		datas.addAll(newDatas);
		adapter.notifyDataSetChanged();
		if(newDatas.isEmpty()){
			//返回结果
			Intent intent = new Intent(this,RegionDiquActivity.class);
			
			String[] addrs = new String[head.size()];
			for(int i = 0; i< addrs.length; i ++){
				addrs[i] = head.get(i).getName();
			}
			
			intent.putExtra("data", addrs);
			intent.putExtra("aid", head.get(head.size() - 1).getAid());
			startActivityForResult(intent, 1);
		}
	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		super.initNetmessageFailure(message, task);
		System.out.println(">>>ERROR:" + message);
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		super.initNetmessageSuccess(message, task);
		System.out.println(">>>suc:" + message);
		if (task.equals(Task.GET_AREA_FIRST_LEVEL)) {
			List<AddrAreaNode> result = handleHttpResult(message);
			firstLevelData = result;
			updateData(result);
		} else if (task.equals(Task.GET_AREA_CHILD_LEVEL)) {
			System.out.println(">>>shuaxing:" + curId);
			if (curId != null) {
				List<AddrAreaNode> result = handleHttpResult(message, curId);
				updateData(result);
				System.out.println(">>>shuaxing:" + result);
			}
		}
	}

	private List<AddrAreaNode> handleHttpResult(String message) {
		return handleHttpResult(message, null);
	}

	private List<AddrAreaNode> handleHttpResult(String message, String parentId) {
		List<AddrAreaNode> nodes = new ArrayList<>();
		try {
			JSONObject obj = new JSONObject(message);
			int result = obj.getInt("result");
			if (result != 1) {
				return nodes;
			}
			JSONArray array = obj.getJSONArray("list");
			for (int i = 0; i < array.length(); i++) {
				JSONObject nodeJson = array.getJSONObject(i);
				String id = nodeJson.getString("aid");
				String name = nodeJson.getString("name");

				AddrAreaNode node = new AddrAreaNode();
				node.setAid(id);
				node.setName(name);
				if (parentId != null) {
					node.setParentId(parentId);
				}
				nodes.add(node);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return nodes;
	}

	private void updateFirstLevel() {
		HashMap<String, String> map = new HashMap<>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		NetUtils.getIns().post(Task.GET_AREA_FIRST_LEVEL, map, handler);
	}

	private void updateChildLevel(String aid) {
		System.out.println(">>>net req child level:" + aid);
		HashMap<String, String> map = new HashMap<>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		map.put("aid", aid);
		NetUtils.getIns().post(Task.GET_AREA_CHILD_LEVEL, map, handler);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == RESULT_OK) {
			setResult(RESULT_OK, data);
			finish();
		}
	}
}
