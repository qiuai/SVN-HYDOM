package com.carinsurance.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.CouponItemModel;
import com.carinsurance.infos.CouponModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyCouponFragment extends BaseFragment {
	// 判断我的优惠卷的总类
	int type;// 0.我的优惠卷 1.过期优惠卷
	@ViewInject(R.id.myListView)
	XListView xlistView;
	int page = 1;
	int maxresult = 10;
	AbstractBaseAdapter<CouponItemModel> adapter1;

	CouponModel couponModel;

	List<CouponItemModel> list;

	public MyCouponFragment(int Type) {
		// TODO Auto-generated constructor stub
		type = Type;
		list = new ArrayList<CouponItemModel>();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.xlistview, null);

		ViewUtils.inject(this, view);

		initView();
		getUserOrderProceed(page++, maxresult);
		return view;
	}

	// 获取用户进行中订单
	private void getUserOrderProceed(int page, int maxresult) {

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(getActivity()));
		map.put("token", Utils.getToken(getActivity()));
		map.put("page", "" + page);
		map.put("maxresult", "" + maxresult);
		if (type == 0) {
			map.put("type", "" + 1);
		} else if (type == 1) {
			map.put("type", "" + 0);
		}
		NetUtils.getIns().post(Task.GET_COUPON_ORDER, map, handler);
	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		try {
			xlistView.stopLoadMore();
			xlistView.stopRefresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);

		try {
			xlistView.stopLoadMore();
			xlistView.stopRefresh();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (task.equals(Task.GET_COUPON_ORDER)) {
			Log.i("", "result=" + message);

			try {
				String result = (new JSONObject(message)).getString("result");
				if (result.equals("001")) {

					couponModel = JsonUtil.getEntityByJsonString(message, CouponModel.class);
					try {
						if (page > Integer.parseInt(couponModel.getPages())) {
							Utils.showMessage(getActivity(), "已加载全部数据!");
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					list.addAll(couponModel.getList());
					if (adapter1 != null) {
						adapter1.notifyDataSetChanged();
					} else {
						initView();
					}
					// initView();
				} else {
					Utils.showMessage(getActivity(), "提交失败！错误码" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		// List<String> list = new ArrayList<String>();
		// list.add("123");
		// list.add("123");
		// list.add("123");
		// list.add("123");
		adapter1 = new AbstractBaseAdapter<CouponItemModel>(list) {

			@Override
			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				if (convertView == null) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.my_coupon_item, null);
				}
				CouponItemModel puCouponItemModel = getItem(position);

				String stype = puCouponItemModel.getCptype();
				
				
				ImageView yiguoqi= ViewHolder.get(convertView, R.id.yiguoqi);
				ImageView image= ViewHolder.get(convertView, R.id.image);
				if(type==1)
				{
					yiguoqi.setVisibility(View.VISIBLE);
				}else
				{
					yiguoqi.setVisibility(View.GONE);
				}
				// if())
				LinearLayout ll_youhuijuanbg = ViewHolder.get(convertView, R.id.ll_youhuijuanbg);
				TextView tv_yang = ViewHolder.get(convertView, R.id.tv_yang);
				TextView tv_cpname = ViewHolder.get(convertView, R.id.tv_cpname);
				TextView tv_cpintroduction = ViewHolder.get(convertView, R.id.tv_cpintroduction);
				// 1=满额打折、 2=满额减免、3=免额多少
				if (stype.equals("3")) {
					tv_yang.setVisibility(View.VISIBLE);
//					ll_youhuijuanbg.setBackgroundColor(Color.parseColor("#00AF81"));
				} else if (stype.equals("1")) {
					tv_yang.setVisibility(View.GONE);
//					ll_youhuijuanbg.setBackgroundColor(Color.parseColor("#D7584F"));
				} else {
					tv_yang.setVisibility(View.GONE);
//					ll_youhuijuanbg.setBackgroundColor(Color.parseColor("#00AF81"));
				}

				new xUtilsImageLoader(getActivity()).display(image, puCouponItemModel.getCpimg());
				
				tv_cpname.setText(puCouponItemModel.getCpname());
				tv_cpintroduction.setText(puCouponItemModel.getCpintroduction());
				return convertView;
			}
		};

		xlistView.setPullLoadEnable(true);
		xlistView.setPullRefreshEnable(true);
		xlistView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				list.clear();
				page = 1;
				getUserOrderProceed(page++, maxresult);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				getUserOrderProceed(page++, maxresult);
			}
		});
		xlistView.setAdapter(adapter1);

	}
}