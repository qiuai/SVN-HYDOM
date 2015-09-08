package com.carinsurance.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carinsurance.activity.OrderConfirmationActivity;
import com.carinsurance.activity.ServiceOrderActivity;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.MyOrdeErListAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.MyOrderModel;
import com.carinsurance.infos.OrderModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.Dialog;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurance.utils.Dialog.DialogClistener;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CancelOrderFragment extends BaseFragment {
	// 判断我的订单的种类
	int type;// 0"待审核", 1"审核中", 2"退款中", 3"已完结"
	int canceltype;
	@ViewInject(R.id.myListView)
	XListView xlistView;
	int page = 1;
	int maxresult = 10;
	AbstractBaseAdapter<OrderModel> adapter1;

	MyOrderModel myOrderModel;
	//
	List<OrderModel> list;

	public CancelOrderFragment(int canceltype) {
		// TODO Auto-generated constructor stub
		this.type = canceltype;
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
		init();
		return view;
	}

	private void init() {
		// TODO Auto-generated method stub
		page = 1;
		list = new ArrayList<OrderModel>();
		initViewtype0();

		getCancelOrder(page++, maxresult);

	}

	// 获取用户已取消订单
	private void getCancelOrder(int page, int maxresult) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(getActivity()));
		map.put("token", Utils.getToken(getActivity()));
		map.put("page", "" + page);
		map.put("maxresult", "" + maxresult);
		map.put("type", "" + (type + 1));

		NetUtils.getIns().post(Task.GET_ORDER_CANCEL, map, handler);
	}

	/**
	 * 要取消的订单编号
	 * 
	 * @param oid
	 */
	private void cancelOrder(String oid) {

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(getActivity()));
		map.put("token", Utils.getToken(getActivity()));
		map.put("oid", oid);
		NetUtils.getIns().post(Task.USER_CANCEL_ORDER, map, handler);
	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		if (task.equals(Task.GET_PROCEED_ORDER) || task.equals(Task.GET_USER_FINISH_ORDER)) {
			try {
				xlistView.stopRefresh();
				xlistView.stopLoadMore();
				page--;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		// 获取已完成和已取消的列表
		if (task.equals(Task.GET_ORDER_CANCEL)) {
			// Log.i("", "result=" + message);
			Log.d("aaa", "yunxing1");

			try {
				xlistView.stopRefresh();
				xlistView.stopLoadMore();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				String result = (new JSONObject(message)).getString("result");
				if (result.equals("001")) {
					Log.d("aaa", "yunxing2");
					myOrderModel = JsonUtil.getEntityByJsonString(message, MyOrderModel.class);
					list.addAll(myOrderModel.getList());
					adapter1.notifyDataSetChanged();
					// }
				} else {
					Log.d("aaa", "yunxing3");
					Utils.showMessage(getActivity(), "提交失败！错误码" + result);
					page--;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				page--;
				Log.d("aaa", "解析出错");
			}

		} else if (task.equals(Task.USER_CANCEL_ORDER)) {// 取消订单
			try {
				String result = (new JSONObject(message)).getString("result");
				if (result.equals("001")) {
					// Log.d("aaa", "yunxing2");
					// myOrderModel = JsonUtil.getEntityByJsonString(message,
					// MyOrderModel.class);
					// list.addAll(myOrderModel.getList());
					// adapter1.notifyDataSetChanged();
					// }
					Utils.showMessage(getActivity(), "订单取消成功！");
					// page=1;
					init();
				} else if (result.equals("009")) {
					Utils.showMessage(getActivity(), "取消订单失败,该订单不能被取消！");
				} else {
					Utils.showMessage(getActivity(), "取消订单失败:错误码" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
	}

	private void initViewtype0() {
		// TODO Auto-generated method stub
		initAdapter();
		xlistView.setPullLoadEnable(true);
		xlistView.setPullRefreshEnable(true);
		xlistView.setDivider(null);
		xlistView.setDividerHeight((int) DisplayUtil.getDip(getActivity(), 20));
		xlistView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				list.clear();
				page = 1;

				getCancelOrder(page++, maxresult);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				getCancelOrder(page++, maxresult);
			}
		});
		xlistView.setAdapter(adapter1);

	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		adapter1 = new AbstractBaseAdapter<OrderModel>(list) {

			@Override
			public int getItemViewType(int position) {
				// TODO Auto-generated method stub
				if (list.get(position).getOtype().equals("2")) {
					return 1;
				}

				return 0;
			}

			@Override
			public int getViewTypeCount() {
				// TODO Auto-generated method stub
				return 3;
			}

			@Override
			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				int daaptertype = getItemViewType(position);
				if (convertView == null) {

					if (daaptertype == 0)// 洗车订单
					{
						convertView = getActivity().getLayoutInflater().inflate(R.layout.myorder_adapter_item, null);
					} else// 保养订单
					{
						convertView = getActivity().getLayoutInflater().inflate(R.layout.myorder_adapter_item1, null);
					}

				}
				OrderModel ordermodel = getItem(position);
				if (daaptertype == 0) {
					// 初始化洗车后者商品订单
					initXiCheOrCunShangpingdingdan(convertView, position, ordermodel);
				} else {
					initDuoshangpingdingdan(convertView, position, ordermodel);
				}
				// otype
				return convertView;
			}

			/**
			 * 多商品订单
			 * 
			 * @param convertView
			 * @param position
			 * @param ordermodel
			 */
			private void initDuoshangpingdingdan(View convertView, int position, final OrderModel ordermodel) {
				// TODO Auto-generated method stub

				MyOrdeErListAdapter adapter = new MyOrdeErListAdapter(getActivity(), ordermodel.getSclist(), type, ordermodel.getOid(), 1);

				ExpandableListView e_listview = ViewHolder.get(convertView, R.id.e_listview);

				e_listview.setAdapter(adapter);
				// 展开所有
				for (int i = 0, length = adapter.getGroupCount(); i < length; i++) {
					e_listview.expandGroup(i);
				}
				e_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
						// TODO Auto-generated method stub
						return true;// 默认为false，设为true时，点击事件不会展开Group
					}
				});

				TextView tv_onum = ViewHolder.get(convertView, R.id.tv_onum);
				tv_onum.setText("订单编号:" + ordermodel.getOnum());
				TextView tv_ostatus = ViewHolder.get(convertView, R.id.tv_ostatus);
				tv_ostatus.setText(ordermodel.getOstatus());// 订单状态
				TextView tv_oprice = ViewHolder.get(convertView, R.id.tv_oprice);
				tv_oprice.setText("￥" + ordermodel.getOprice());
				TextView tv_ocanop = ViewHolder.get(convertView, R.id.tv_ocanop);
				LinearLayout llbtn_ocanop = ViewHolder.get(convertView, R.id.llbtn_ocanop);
				if (type == 3 && ordermodel.getOcanop().equals("1")) {
					llbtn_ocanop.setVisibility(View.VISIBLE);
					tv_ocanop.setText("取消订单");
					llbtn_ocanop.setOnClickListener(new View.OnClickListener() {// 取消订单

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Dialog dialog = new Dialog();
									dialog.CreateDialog(getActivity(), "取消订单", "是否取消该订单？");
									dialog.setOnDialogClistener(new DialogClistener() {

										@Override
										public void ret() {
											// TODO Auto-generated method stub

										}

										@Override
										public void ok() {
											// TODO Auto-generated method stub
											cancelOrder(ordermodel.getOid());
										}
									});
								}
							});
				} else {
					llbtn_ocanop.setVisibility(View.GONE);
				}
			}

			/**
			 * 洗车订单的item @Link(myorder_adapter_item)
			 * 
			 * @param convertView
			 * @param position
			 * @param ordermodel
			 */
			private void initXiCheOrCunShangpingdingdan(View convertView, int position, final OrderModel ordermodel) {
				// TODO Auto-generated method stub

				TextView tv_pname = ViewHolder.get(convertView, R.id.tv_pname);

				tv_pname.setText(ordermodel.getSclist().get(0).getPlist().get(0).getPname());

				LinearLayout dsp0 = ViewHolder.get(convertView, R.id.dsp0);

				TextView tv_premark = ViewHolder.get(convertView, R.id.tv_premark);
				tv_premark.setText("");
				// 订单类型：1=洗车订单、3=纯商品订单
				if (ordermodel.getOtype().equals("1")) {

					dsp0.setVisibility(View.GONE);

					tv_premark.setText(ordermodel.getSclist().get(0).getPlist().get(0).getPremark());

				} else if (ordermodel.getOtype().equals("3")) {// 如果是存商品订单
					dsp0.setVisibility(View.VISIBLE);
					TextView item_price = ViewHolder.get(convertView, R.id.item_price);
					TextView tv_number = ViewHolder.get(convertView, R.id.tv_number);

					String pnum = ordermodel.getSclist().get(0).getPlist().get(0).getPnum();
					try {
						String temp = pnum.substring(0, pnum.lastIndexOf("."));
						Integer pnumInt = Integer.valueOf(temp);
						tv_number.setText("x" + pnumInt);
					} catch (Exception e) {
						tv_number.setText("x" + pnum);
					}

					item_price.setText("" + ordermodel.getSclist().get(0).getPlist().get(0).getPprice());
				}
				// 跳转：：：1=洗车订单、2=保养订单、3=纯商品订单
				if (ordermodel.getOtype().equals("1")) {// 洗车订单
					LinearLayout ll_jump = ViewHolder.get(convertView, R.id.ll_jump);
					ll_jump.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							HashMap<String, String> map = new HashMap<String, String>();
							map.put("type", "2");
							map.put("oid", ordermodel.getOid());
							JumpUtils.jumpto(getActivity(), OrderConfirmationActivity.class, map);
						}
					});
				}
				// else if (ordermodel.getOtype().equals("2")) {//2=保养订单
				// LinearLayout ll_jump = ViewHolder.get(convertView,
				// R.id.ll_jump);
				// ll_jump.setOnClickListener(new View.OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// HashMap<String, String> map=new HashMap<String, String>();
				// map.put("type", "3");
				// map.put("oid", ordermodel.getOid());
				// JumpUtils.jumpto(getActivity(),
				// OrderConfirmationActivity.class, ordermodel,map);
				// }
				// });
				// }
				else if (ordermodel.getOtype().equals("3")) {// 3=纯商品订单
					LinearLayout ll_jump = ViewHolder.get(convertView, R.id.ll_jump);
					ll_jump.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("type", "3");
							map.put("oid", ordermodel.getOid());
							JumpUtils.jumpto(getActivity(), ServiceOrderActivity.class, map);
						}
					});
				}

				ImageView iv_pimg = ViewHolder.get(convertView, R.id.iv_pimg);

				new xUtilsImageLoader(getActivity()).display(iv_pimg, ordermodel.getSclist().get(0).getPlist().get(0).getPimg());

				TextView tv_onum = ViewHolder.get(convertView, R.id.tv_onum);
				tv_onum.setText("订单编号:" + ordermodel.getOnum());
				TextView tv_ostatus = ViewHolder.get(convertView, R.id.tv_ostatus);
				tv_ostatus.setText(ordermodel.getOstatus());// 订单状态
				TextView tv_oprice = ViewHolder.get(convertView, R.id.tv_oprice);
				tv_oprice.setText("￥" + ordermodel.getOprice());
				TextView tv_ocanop = ViewHolder.get(convertView, R.id.tv_ocanop);
				LinearLayout llbtn_ocanop = ViewHolder.get(convertView, R.id.llbtn_ocanop);

				TextView tv_ocanop2 = ViewHolder.get(convertView, R.id.tv_ocanop2);
				LinearLayout llbtn_ocanop2 = ViewHolder.get(convertView, R.id.llbtn_ocanop2);
				// 如果是审核完成的那一项 并且能取消订单
				if (type == 3 && ordermodel.getOcanop().equals("1")) {
					llbtn_ocanop.setVisibility(View.VISIBLE);
					tv_ocanop.setText("取消订单");
					llbtn_ocanop.setOnClickListener(new View.OnClickListener() {// 取消订单

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Dialog dialog = new Dialog();
									dialog.CreateDialog(getActivity(), "取消订单", "是否取消该订单？");
									dialog.setOnDialogClistener(new DialogClistener() {

										@Override
										public void ret() {
											// TODO Auto-generated method stub

										}

										@Override
										public void ok() {
											// TODO Auto-generated method stub
											cancelOrder(ordermodel.getOid());
										}
									});
								}
							});
				} else {
					llbtn_ocanop.setVisibility(View.GONE);
				}

			}
		};
	}
}