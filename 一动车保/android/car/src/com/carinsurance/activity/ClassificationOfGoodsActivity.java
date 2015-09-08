package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.nodes.HotCategoryNode;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 商品分类
 * 
 * @author Administrator
 * 
 */
public class ClassificationOfGoodsActivity extends BaseActivity implements OnItemClickListener {
	@ViewInject(R.id.hot_category_menuLeft)
	private ListView leftMenu; // 左边菜单栏
	@ViewInject(R.id.hot_category_menuRight)
	private ListView rightMenu; // 右边菜单栏
	@OnClick(R.id.return_btn)
	private void returnClick(View view){
		finish();
	}

	private MenuAdapter leftAdapter;
	private MenuAdapter rightAdapter;
	private List<HotCategoryNode> leftDatas = new ArrayList<HotCategoryNode>();
	private List<HotCategoryNode> rightDatas = new ArrayList<HotCategoryNode>();
	int select_position = 0;
	
	private SortModel model;

	@Override
	public void initNetmessageSuccess(String message, String task) {
		super.initNetmessageSuccess(message, task);
		if (task.equals(Task.GET_HOT_CATEGORY)) {
			// 更新一级分类数据
			List<HotCategoryNode> datas = handleHttpResult(message);
			leftDatas.clear();
			leftDatas.addAll(datas);
			leftAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void initNetmessageFailure(String message, String task) {

		if (task.equals(Task.GET_HOT_CATEGORY)) {
			System.out.println(">>>" + message);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classificationofgoods);
		ViewUtils.inject(this);

		model = (SortModel) JumpUtils.getSerializable(this);
		if(model == null){
			Toast.makeText(this, "非法进入,上级数据为空", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		leftAdapter = new MenuAdapter(leftDatas, this, 0);
		rightAdapter = new MenuAdapter(rightDatas, this, 1);
		leftMenu.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE); // 设置单选模式
		leftMenu.setClickable(true);
		leftMenu.setAdapter(leftAdapter);
		rightMenu.setAdapter(rightAdapter);
		leftMenu.setOnItemClickListener(this);
		rightMenu.setOnItemClickListener(this);

		HashMap<String, String> params = new HashMap<String, String>();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(getApplicationContext()))
				&& !StringUtil.isNullOrEmpty(Utils
						.getToken(getApplicationContext()))) {
			params.put("uid", Utils.getUid(getApplicationContext()));
			params.put("token", Utils.getToken(getApplicationContext()));
		}
		
		NetUtils.getIns().post(Task.GET_HOT_CATEGORY, params, handler);
	}

	private List<HotCategoryNode> handleHttpResult(String datas) {
		List<HotCategoryNode> toList = new ArrayList<HotCategoryNode>();
		try {
			JSONObject obj = new JSONObject(datas);
			String resultCode = obj.getString("result");
			if (!resultCode.equals(NetUtils.NET_SUCCESS_001)) {
				return toList;
			}
			//
			JSONArray tos = obj.getJSONArray("list");
			if (tos != null) {
				for (int i = 0; i < tos.length(); i++) {
					JSONObject to = tos.getJSONObject(i);
					// 获取一级分类的名字和ID
					HotCategoryNode nodeTo = new HotCategoryNode();
					nodeTo.setId(to.getString("topid"));
					nodeTo.setName(to.getString("topname"));
					// 获取对应一级分类的二级分类
					JSONArray pcs = to.getJSONArray("pclist");
					List<HotCategoryNode> pcsList = new ArrayList<HotCategoryNode>();
					if (pcs != null) {
						for (int j = 0; j < pcs.length(); j++) {
							JSONObject pc = pcs.getJSONObject(j);
							HotCategoryNode nodePc = new HotCategoryNode();
							nodePc.setId(pc.getString("pcid"));
							nodePc.setName(pc.getString("pcname"));
							pcsList.add(nodePc);
						}
					}

					nodeTo.setChildrens(pcsList);

					// 将一级分类添加至返回列表
					toList.add(nodeTo);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return toList;
	}

	class MenuAdapter extends BaseAdapter {
		private List<HotCategoryNode> nodes;
		private LayoutInflater inflater;

		private int type;

		public MenuAdapter(List<HotCategoryNode> nodes, Context context,
				int type) {
			this.nodes = nodes;
			inflater = LayoutInflater.from(context);
			this.type = type;
		}

		@Override
		public int getCount() {
			return nodes.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return nodes.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.host_category_left_menu_item, null);
			}

			TextView title = (TextView) convertView
					.findViewById(R.id.host_category_left_menu_item_title);
			LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
			title.setText(nodes.get(position).getName());
			if (type == 0) {
				if (select_position == position) {
					ll.setBackgroundColor(Color.parseColor("#ffffff"));
					title.setTextColor(Color.parseColor("#00a0ea"));
					List<HotCategoryNode> ds = leftDatas.get(position).getChildrens();
					//更新二级分类
					if (ds != null) {
						rightDatas.clear();
						rightDatas.addAll(ds);
						rightAdapter.notifyDataSetChanged();
					}

				} else {
					ll.setBackgroundColor(Color.parseColor("#e5e5e5"));
					title.setTextColor(Color.parseColor("#000000"));
				}
			}else{
				title.setTextColor(Color.parseColor("#585858"));
			}

			return convertView;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.hot_category_menuLeft: {
			select_position=position;
			leftMenu.setItemChecked(position, true);
			break;
		}
		case R.id.hot_category_menuRight: {
			HashMap<String,String> param = new HashMap<>();
			param.put("id", rightDatas.get(position).getId());
			param.put("name", rightDatas.get(position).getName());
			
			JumpUtils.jumpto(this, HotCategoryListActivity.class, model, param);
			break;
		}
		default:
			break;
		}

	}

}
