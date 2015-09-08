package com.carinsurance.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.activity.NewAddingActivity;
import com.carinsurance.activity.ProductDetailsActivity;
import com.carinsurance.infos.ProductDefaultItemModel;
import com.carinsurance.infos.SeriverTypeitemModel;
import com.carinsurance.utils.Dialog;
import com.carinsurance.utils.Dialog.DialogClistener;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;

public class SelectServer1Adapter extends BaseExpandableListAdapter {

	// private LayoutInflater inflater = null;

	// List<ProductDefaultItemModel> alist;

	//
	List<SeriverTypeitemModel> modellist;
	Context context;

	SortModel sortModel;

	String type;// -1是默认的 0是只能看的，sortModel 和1 是 只能看的，但是不是sortmodel

	public SelectServer1Adapter(Context context, List<ProductDefaultItemModel> list, SortModel sortModel, String type) {
		// TODO Auto-generated constructor stub
		List<ProductDefaultItemModel> alist = list;
		// List<SeriverTypeitemModel> list2
		this.sortModel = sortModel;
		this.modellist = sortModel.getSelectSeriverTypeitemList();
		this.context = context;
		this.type = type;
		for (int i = 0; i < modellist.size(); i++) {
			modellist.get(i).setProductDefaultItemModel_list(new ArrayList<ProductDefaultItemModel>());
			Log.v("aaa", "i===>" + i);
			for (int j = 0; j < alist.size(); j++) {
				if (modellist.get(i).getScid().equals("" + alist.get(j).getScid())) {
					modellist.get(i).getProductDefaultItemModel_list().add(alist.get(j));
					// alist.get(i).getScid();
					// Log.v("aaa","列表on个的数据是==》"+modellist.get(i).toString());
					// Log.v("aaa","i===>"+i+"////j===>"+j);
				}
			}
		}
	}

	public SelectServer1Adapter(Context context, SortModel sortModel, String type) {
		// TODO Auto-generated constructor stub
		// List<ProductDefaultItemModel> alist = list;
		// List<SeriverTypeitemModel> list2
		this.sortModel = sortModel;
		this.modellist = sortModel.getSelectSeriverTypeitemList();
		this.context = context;
		this.type = type;

	}

	public SelectServer1Adapter(Context context, List<SeriverTypeitemModel> modellist, String type) {
		// TODO Auto-generated constructor stub
		// List<ProductDefaultItemModel> alist = list;
		// List<SeriverTypeitemModel> list2
		// this.sortModel = sortModel;
		this.modellist = modellist;
		this.context = context;
		this.type = type;

	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.select_service_list, null);
		}
		TextView fuwufei = ViewHolder.get(convertView, R.id.fuwufei);

		TextView tv_scname = ViewHolder.get(convertView, R.id.tv_scname);
		ImageView iv_img = ViewHolder.get(convertView, R.id.iv_img);
		TextView adding = ViewHolder.get(convertView, R.id.adding);
		tv_scname.setText("" + modellist.get(groupPosition).getScname());
		new xUtilsImageLoader(context).display(iv_img, modellist.get(groupPosition).getScimage());
		// 新增
		if (type.equals("-1")) {

			adding.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("scid", modellist.get(groupPosition).getScid());
					map.put("groupPosition", "" + groupPosition);
					JumpUtils.jumpActivityForResult(context, NewAddingActivity.class, sortModel, map, NewAddingActivity.RESULT_OK, true);
				}
			});

		} else if (type.equals("0")) {
			adding.setText("￥" + modellist.get(groupPosition).getScprice());
			fuwufei.setVisibility(View.VISIBLE);
			//

		}
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// Log.v("aaa","getChildView运行了"+groupPosition+childPosition);
		LayoutInflater inflater = LayoutInflater.from(context);
		// int type = getChildType(groupPosition, childPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.selectserver_child_item, null);
		}
		// TextView number= ViewHolder.get(convertView, R.id.ll_top);
		Log.v("aaa", "childPostion--->" + childPosition + "/" + modellist.get(groupPosition).getProductDefaultItemModel_list().size());

		if (childPosition > modellist.get(groupPosition).getProductDefaultItemModel_list().size() - 1) {
			Log.d("aaa", "运行了1");
			LinearLayout ll_top = ViewHolder.get(convertView, R.id.ll_top);

			ll_top.setVisibility(View.GONE);
			LinearLayout ll_fwf = ViewHolder.get(convertView, R.id.ll_fwf);
			ll_fwf.setVisibility(View.VISIBLE);

			TextView fuwu_feiyong = ViewHolder.get(convertView, R.id.fuwu_feiyong);
			fuwu_feiyong.setText("￥" + modellist.get(groupPosition).getScprice());
		} else {
			Log.d("aaa", "运行了2");

			TextView x2 = ViewHolder.get(convertView, R.id.x2);
			LinearLayout ll_jia_or_jian = ViewHolder.get(convertView, R.id.ll_jia_or_jian);
			if (type.equals("0")) {
				x2.setVisibility(View.VISIBLE);
				ll_jia_or_jian.setVisibility(View.GONE);

				if (!StringUtil.isNullOrEmpty(modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).getNumber()))
					x2.setText("x" + modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).getNumber());
				else
					x2.setText("x" + 1);
			} else {
				ll_jia_or_jian.setVisibility(View.VISIBLE);
			}

			LinearLayout ll_fwf = ViewHolder.get(convertView, R.id.ll_fwf);
			LinearLayout ll_top = ViewHolder.get(convertView, R.id.ll_top);
			ll_top.setVisibility(View.VISIBLE);
			ll_fwf.setVisibility(View.GONE);

			ll_top.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (type.equals("0")) {
						return;
					}
					ProductDefaultItemModel m = new ProductDefaultItemModel();
					m.setPid(modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).getPid());
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("type", "1");
					map.put("is_show_shipeichexing", "0");
					JumpUtils.jumpto(context, ProductDetailsActivity.class, m, map);
				}
			});
			ImageView delete = ViewHolder.get(convertView, R.id.delete);
			if (type.equals("0")) {
				delete.setVisibility(View.INVISIBLE);
			}
			delete.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (type.equals("0")) {
						return;
					}
					Dialog d = new Dialog();
					d.CreateDialog(context, "", "确认删除该商品？");
					d.setOnDialogClistener(new DialogClistener() {

						@Override
						public void ret() {
							// TODO Auto-generated method stub

						}

						@Override
						public void ok() {
							// TODO Auto-generated method stub
							modellist.get(groupPosition).getProductDefaultItemModel_list().remove(childPosition);
							notifyDataSetChanged();
						}
					});

				}
			});

			ImageView near_icon = ViewHolder.get(convertView, R.id.near_icon);

			new xUtilsImageLoader(context).display(near_icon, modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).getPimage());
			// 商品名称
			TextView pname = ViewHolder.get(convertView, R.id.pname);
			pname.setText("" + modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).getPname());

			TextView price = ViewHolder.get(convertView, R.id.price);
			price.setText("￥" + modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).getPrice());

			ImageView jian = ViewHolder.get(convertView, R.id.jian);
			ImageView jia = ViewHolder.get(convertView, R.id.jia);
			final TextView number = ViewHolder.get(convertView, R.id.number);
			if (!StringUtil.isNullOrEmpty(modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).getNumber())) {
				number.setText("" + modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).getNumber());
			} else {
				number.setText("1");
			}
			jian.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Utils.initNumber(context, number, 0);
					modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).setNumber(number.getText().toString().trim());
				}
			});
			jia.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Utils.initNumber(context, number, 1);
					modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition).setNumber(number.getText().toString().trim());
				}
			});

		}

		return convertView;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		Log.v("aaa", "getGroupCount");
		return modellist.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		Log.v("aaa", "getChildrenCount");

		if (type.equals("-1"))
			return (modellist.get(groupPosition).getProductDefaultItemModel_list().size() + 1);
		else {
			return modellist.get(groupPosition).getProductDefaultItemModel_list().size();
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return modellist.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return modellist.get(groupPosition).getProductDefaultItemModel_list().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}