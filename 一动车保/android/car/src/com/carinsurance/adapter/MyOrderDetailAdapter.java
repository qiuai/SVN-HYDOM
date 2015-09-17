package com.carinsurance.adapter;

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
import com.carinsurance.infos.SeriverTypeitemModel;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;

public class MyOrderDetailAdapter extends BaseExpandableListAdapter {

	// private LayoutInflater inflater = null;

	// List<ProductDefaultItemModel> alist;

	//
	List<SeriverTypeitemModel> modellist;
	Context context;

	SortModel sortModel;

	// int type;
	String oid;
	String type;
	public MyOrderDetailAdapter(Context context, List<SeriverTypeitemModel> sclist) {
		// TODO Auto-generated constructor stub
		this.context = context;
		modellist = sclist;
	}
	public MyOrderDetailAdapter(Context context, List<SeriverTypeitemModel> sclist,String type) {
		// TODO Auto-generated constructor stub
		this.context = context;
		modellist = sclist;
		this.type=type;
	}
	/**
	 * 判断是否有商品
	 * 
	 * @return
	 */
	public boolean is_hasshangping() {
		for (int i = 0; i < modellist.size(); i++) {
			// modellist.get(i).getPlist();
			for (int j = 0; j < modellist.get(i).getPlist().size(); i++) {

				if (!modellist.get(i).getPlist().isEmpty()) {
					return true;
				}
			}
			if (i == modellist.size() - 1) {
				return false;
			}

		}

		return false;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.my_order_adapter_item_top_server, null);
		}
		TextView tv_scname = ViewHolder.get(convertView, R.id.tv_scname);
		tv_scname.setText("" + modellist.get(groupPosition).getScname());
		ImageView iv_img = ViewHolder.get(convertView, R.id.iv_img);
		new xUtilsImageLoader(context).display(iv_img, modellist.get(groupPosition).getScimg());
		TextView tv_scprice = ViewHolder.get(convertView, R.id.tv_scprice);
		tv_scprice.setText("￥" + modellist.get(groupPosition).getScprice());

		// if(type==1)//如果是已完成订单
		// {
		// LinearLayout ll_pinglun=ViewHolder.get(convertView, R.id.ll_pinglun);
		// ll_pinglun.setVisibility(View.VISIBLE);
		// TextView tv_pinglun=ViewHolder.get(convertView, R.id.tv_pinglun);
		//
		// if(modellist.get(groupPosition).getSccomt().equals("1"))//1=可以评论、0=不可评论
		// {
		// tv_pinglun.setText("评论");
		// }else
		// {
		// tv_pinglun.setText("已评论");
		// }
		// }
		LinearLayout ll_fuwufei = ViewHolder.get(convertView, R.id.ll_fuwufei);
		ll_fuwufei.setVisibility(View.GONE);
		if (is_hasshangping()) {
			ll_fuwufei.setVisibility(View.VISIBLE);
		} else {
			ll_fuwufei.setVisibility(View.GONE);
		}

		// TextView adding = ViewHolder.get(convertView, R.id.adding);
		// // 新增
		// adding.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// HashMap<String, String> map = new HashMap<String, String>();
		// map.put("scid", modellist.get(groupPosition).getScid());
		// map.put("groupPosition", "" + groupPosition);
		// JumpUtils.jumpActivityForResult(context, NewAddingActivity.class,
		// sortModel, map, NewAddingActivity.RESULT_OK, true);
		// }
		// });
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// Log.v("aaa","getChildView运行了"+groupPosition+childPosition);
		LayoutInflater inflater = LayoutInflater.from(context);
		// int type = getChildType(groupPosition, childPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.my_order_adapter_item_cen_server, null);
		}
		
		// TextView fuwu_feiyong= ViewHolder.get(convertView,
		// R.id.fuwu_feiyong);
		// fuwu_feiyong.setText("￥"+modellist);
		
		LinearLayout ll_pinglun= ViewHolder.get(convertView, R.id.ll_pinglun);
		ll_pinglun.setVisibility(View.GONE);
		ImageView near_icon = ViewHolder.get(convertView, R.id.near_icon);
		new xUtilsImageLoader(context).display(near_icon, modellist.get(groupPosition).getPlist().get(childPosition).getPimg());
		// 商品名称
		TextView pname = ViewHolder.get(convertView, R.id.pname);
		pname.setText("" + modellist.get(groupPosition).getPlist().get(childPosition).getPname());

		TextView price = ViewHolder.get(convertView, R.id.price);
		price.setText("￥" + modellist.get(groupPosition).getPlist().get(childPosition).getPprice());
		TextView tv_pnum = ViewHolder.get(convertView, R.id.tv_pnum);
		tv_pnum.setText("x" + modellist.get(groupPosition).getPlist().get(childPosition).getPnum());

		// else if (ordermodel.getOtype().equals("2")) {//2=保养订单
		LinearLayout ll_jump = ViewHolder.get(convertView, R.id.ll_top);
		// ll_jump.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// HashMap<String, String> map=new HashMap<String, String>();
		// map.put("type", "3");
		//
		// map.put("oid", oid);
		// JumpUtils.jumpto(context,OrderConfirmationActivity.class,map);
		// }
		// });
		// }
		// if(type==1)//如果是已完成订单
		// {
		// LinearLayout ll_pinglun=ViewHolder.get(convertView, R.id.ll_pinglun);
		// ll_pinglun.setVisibility(View.VISIBLE);
		// TextView tv_pinglun=ViewHolder.get(convertView, R.id.tv_pinglun);
		//
		// if(modellist.get(groupPosition).getSccomt().equals("1"))//1=可以评论、0=不可评论
		// {
		// tv_pinglun.setText("评论");
		// }else
		// {
		// tv_pinglun.setText("已评论");
		// }
		// }

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

		return (modellist.get(groupPosition).getPlist().size());
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return modellist.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return modellist.get(groupPosition).getPlist().get(childPosition);
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