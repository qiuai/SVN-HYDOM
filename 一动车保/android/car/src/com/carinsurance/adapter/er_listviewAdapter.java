package com.carinsurance.adapter;

import java.util.List;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.activity.SelectModels1Activity;
import com.carinsurance.infos.CarseriesItemModel;
import com.carinsurance.infos.CarseriesItemitemModel;
import com.carinsurance.utils.JumpUtils;
import com.carinsurancer.car.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class er_listviewAdapter implements ExpandableListAdapter {
	Context context;
	List<CarseriesItemModel> carlist;
	SortModel sortModel;
	public er_listviewAdapter(Context context, List<CarseriesItemModel> list,SortModel sortModel) {
		// TODO Auto-generated constructor stub
		this.context = context;
		carlist = list;
		this.sortModel=sortModel;
		Log.v("aaa", "carlist=" + carlist);
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.er_list_group_item, null);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		holder.text.setText("" + carlist.get(groupPosition).getTopname());
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.er_list_child_item, null);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.item = (LinearLayout) convertView.findViewById(R.id.item);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		holder.text.setText("" + carlist.get(groupPosition).getCslist().get(childPosition).getCsname());
		holder.item.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CarseriesItemitemModel mm = (CarseriesItemitemModel)getChild(groupPosition, childPosition);

				sortModel.setType("1");
				sortModel.setCs_name(mm.getCsname());
				sortModel.setCsid(mm.getCsid());
				sortModel.setTopName(""+carlist.get(groupPosition).getTopname());
				JumpUtils.jumpto(context, SelectModels1Activity.class, sortModel,false);
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		TextView text;
		LinearLayout item;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return carlist.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return carlist.get(groupPosition).getCslist().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return carlist.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return carlist.get(groupPosition).getCslist().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getCombinedChildId(long groupId, long childId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCombinedGroupId(long groupId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		// TODO Auto-generated method stub

	}
}
