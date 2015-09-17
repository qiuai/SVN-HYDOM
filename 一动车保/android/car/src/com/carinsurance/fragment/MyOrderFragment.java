package com.carinsurance.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.carinsurance.activity.DiscussCommitActivity;
import com.carinsurance.activity.OrderConfirmationActivity;
import com.carinsurance.activity.ServiceOrderActivity;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.MyOrdeErListAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.Content;
import com.carinsurance.infos.MyOrderModel;
import com.carinsurance.infos.OrderModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.Dialog;
import com.carinsurance.utils.Dialog.DialogClistener;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurance.xlistview.XListView;
import com.carinsurance.xlistview.XListView.IXListViewListener;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyOrderFragment extends BaseFragment {
	// 判断我的订单的种类
	int type;// 0.进行中 1.已完成
	@ViewInject(R.id.myListView)
	XListView xlistView;
	int page = 1;
	int maxresult = 10;
	AbstractBaseAdapter<OrderModel> adapter1;

	MyOrderModel myOrderModel;
	//
	List<OrderModel> list;

	public MyOrderFragment(int Type) {
		// TODO Auto-generated constructor stub
		type = Type;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (type == 1) {
			Content.is_refresh = false;
		}

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

		if (type == 0)
			getUserOrderProceed(page++, maxresult);
		else if (type == 1) {
			getUserFinishOrder(page++, maxresult);
		}

	}

	// 获取用户已完成订单
	private void getUserFinishOrder(int page, int maxresult) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(getActivity()));
		map.put("token", Utils.getToken(getActivity()));
		map.put("page", "" + page);
		map.put("maxresult", "" + maxresult);
		NetUtils.getIns().post(Task.GET_USER_FINISH_ORDER, map, handler);
	}

	// 获取用户进行中订单
	private void getUserOrderProceed(int page, int maxresult) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(getActivity()));
		map.put("token", Utils.getToken(getActivity()));
		map.put("page", "" + page);
		map.put("maxresult", "" + maxresult);
		NetUtils.getIns().post(Task.GET_PROCEED_ORDER, map, handler);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		try {
			if (Content.is_refresh == true && type == 1) {
				Content.is_refresh = false;
				list.clear();
				page = 1;
				if (type == 0)
					getUserOrderProceed(page++, maxresult);
				else if (type == 1) {
					getUserFinishOrder(page++, maxresult);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
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

	/**
	 * 确认收货
	 * 
	 * @param oid
	 */
	private void OkOrder(String oid) {

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("uid", Utils.getUid(getActivity()));
		map.put("token", Utils.getToken(getActivity()));
		map.put("oid", oid);
		NetUtils.getIns().post(Task.USER_CONFIRM_ORDER, map, handler);
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
		if (task.equals(Task.GET_PROCEED_ORDER) || task.equals(Task.GET_USER_FINISH_ORDER)) {
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
					Utils.showMessage(getActivity(), "网络异常！错误码" + result);
					page--;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				page--;
				Log.d("aaa", "解析出错");
			}

		} else if (task.equals(Task.USER_CANCEL_ORDER) || task.equals(Task.USER_CONFIRM_ORDER)) {// 取消订单
			try {
				String result = (new JSONObject(message)).getString("result");
				if (result.equals("001")) {
					// Log.d("aaa", "yunxing2");
					// myOrderModel = JsonUtil.getEntityByJsonString(message,
					// MyOrderModel.class);
					// list.addAll(myOrderModel.getList());
					// adapter1.notifyDataSetChanged();
					// }
					Utils.showMessage(getActivity(), "操作成功！");
					init();
				} else if (result.equals("009")) {
					Utils.showMessage(getActivity(), "操作失败!");
				} else {
					Utils.showMessage(getActivity(), "操作失败:错误码" + result);
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

		// xlistView.setDivider(null);
		xlistView.setDivider(null);
		xlistView.setDividerHeight((int) DisplayUtil.getDip(getActivity(), 10));
		xlistView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				list.clear();
				page = 1;

				if (type == 0)
					getUserOrderProceed(page++, maxresult);
				else if (type == 1) {
					getUserFinishOrder(page++, maxresult);
				}
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				if (type == 0)
					getUserOrderProceed(page++, maxresult);
				else if (type == 1) {
					getUserFinishOrder(page++, maxresult);
				}
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

					if (daaptertype == 0)// 洗车订单车或者商品订单
					{
						convertView = getActivity().getLayoutInflater().inflate(R.layout.myorder_adapter_item, null);
					} else// 保养订单
					{
						convertView = getActivity().getLayoutInflater().inflate(R.layout.myorder_adapter_item1, null);
					}

				}
				OrderModel ordermodel = getItem(position);
				if (daaptertype == 0) {
					// 初始化洗车或者商品订单
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

				MyOrdeErListAdapter adapter = new MyOrdeErListAdapter(getActivity(), ordermodel.getSclist(), type, ordermodel.getOid(), 0);

				ExpandableListView e_listview = ViewHolder.get(convertView, R.id.e_listview);

				e_listview.setAdapter(adapter);
				e_listview.setDivider(getActivity().getResources().getDrawable(R.color.bj_f0f0f0));
				e_listview.setChildDivider(getActivity().getResources().getDrawable(R.color.bj_f0f0f0));
				e_listview.setDividerHeight((int) DisplayUtil.getDip(getActivity(), 1));
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

				if (type == 1) {
					tv_ostatus.setText("");
				} else
					tv_ostatus.setText(ordermodel.getOstatus());// 订单状态
				TextView tv_oprice = ViewHolder.get(convertView, R.id.tv_oprice);
				tv_oprice.setText("￥" + ordermodel.getOprice());
				TextView tv_ocanop = ViewHolder.get(convertView, R.id.tv_ocanop);
				LinearLayout llbtn_ocanop = ViewHolder.get(convertView, R.id.llbtn_ocanop);
				LinearLayout bottom_jishiinfos = ViewHolder.get(convertView, R.id.bottom_jishiinfos);
				TextView jishi_name = ViewHolder.get(convertView, R.id.jishi_name);
				TextView jishi_phone = ViewHolder.get(convertView, R.id.jishi_phone);
//				RatingBar jishi_star = ViewHolder.get(convertView, R.id.jishi_star);

//				jishi_star.setProgress(0);
				if (!StringUtil.isNullOrEmpty(ordermodel.getOcontact()) && !StringUtil.isNullOrEmpty(ordermodel.getOphone())) {
					bottom_jishiinfos.setVisibility(View.VISIBLE);
					jishi_name.setText(ordermodel.getOcontact());
					jishi_phone.setText("联系电话:" + ordermodel.getOphone());
//					try {
//						jishi_star.setProgress((int) (Double.parseDouble(ordermodel.getOstar()) * 10));
//					} catch (Exception e) {
//						jishi_star.setProgress(0);
//					}

					bottom_jishiinfos.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							JumpUtils.Call_DIAL(getActivity(), ordermodel.getOphone());
						}
					});

				} else {
					bottom_jishiinfos.setVisibility(View.GONE);
				}

				if (type == 0) {// 进行中

					String anop = ordermodel.getOcanop();// 用户能进行的操作：0=不能进行任何操作、1=能取消订单、2=能确认收货

					if (anop.equals("0")) {
						tv_ocanop.setText("￥" + ordermodel.getOprice());
						llbtn_ocanop.setVisibility(View.GONE);
						llbtn_ocanop.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

							}
						});
					} else if (anop.equals("1")) {
						tv_ocanop.setText("取消订单");
						llbtn_ocanop.setOnClickListener(new View.OnClickListener() {

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
					} else if (anop.equals("2")) {
						tv_ocanop.setText("确认收货");
						llbtn_ocanop.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Dialog dialog = new Dialog();
								dialog.CreateDialog(getActivity(), "确认收货", "是否确认收货？");
								dialog.setOnDialogClistener(new DialogClistener() {

									@Override
									public void ret() {
										// TODO Auto-generated method stub

									}

									@Override
									public void ok() {
										// TODO Auto-generated method stub
										OkOrder(ordermodel.getOid());
									}
								});
							}
						});
					}
				} else if (type == 1) {
					llbtn_ocanop.setVisibility(View.GONE);
				}
			}

			private void initXiCheOrCunShangpingdingdan(View convertView, final int position, final OrderModel ordermodel) {
				// TODO Auto-generated method stub
				LinearLayout ll_jump = ViewHolder.get(convertView, R.id.ll_jump);

				if (ordermodel.getOtype().equals("3"))// 订单类型：1=洗车订单、2=保养订单、3=纯商品订单
				{
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
				} else if (ordermodel.getOtype().equals("1")) {
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

				Log.v("aaa", "type===>" + type);
				TextView tv_pname = ViewHolder.get(convertView, R.id.tv_pname);

				tv_pname.setText(ordermodel.getSclist().get(0).getPlist().get(0).getPname());

				LinearLayout dsp0 = ViewHolder.get(convertView, R.id.dsp0);

				TextView tv_premark = ViewHolder.get(convertView, R.id.tv_premark);
				LinearLayout bottom_jishiinfos = ViewHolder.get(convertView, R.id.bottom_jishiinfos);
				TextView jishi_name = ViewHolder.get(convertView, R.id.jishi_name);
				TextView jishi_phone = ViewHolder.get(convertView, R.id.jishi_phone);
				
				RatingBar jishi_star = ViewHolder.get(convertView, R.id.jishi_star);
				tv_premark.setText("");
				
				bottom_jishiinfos.setVisibility(View.GONE);
				jishi_star.setProgress(0);
				if (ordermodel.getOtype().equals("1")) {
					// 订单类型：1=洗车订单、3=纯商品订单
					dsp0.setVisibility(View.GONE);
					tv_premark.setText(ordermodel.getSclist().get(0).getPlist().get(0).getPremark());

					if (!StringUtil.isNullOrEmpty(ordermodel.getOcontact()) && !StringUtil.isNullOrEmpty(ordermodel.getOphone())) {
						bottom_jishiinfos.setVisibility(View.VISIBLE);
						jishi_name.setText(ordermodel.getOcontact());
						jishi_phone.setText("联系电话:" + ordermodel.getOphone());
						try {
							jishi_star.setProgress((int) (Double.parseDouble(ordermodel.getOstar()) * 10));
						} catch (Exception e) {
							jishi_star.setProgress(0);
						}
						bottom_jishiinfos.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								JumpUtils.Call_DIAL(getActivity(), ordermodel.getOphone());
							}
						});
					} else {
						bottom_jishiinfos.setVisibility(View.GONE);
					}

				} else if (ordermodel.getOtype().equals("3")) {
					dsp0.setVisibility(View.VISIBLE);
					TextView item_price = ViewHolder.get(convertView, R.id.item_price);
					TextView tv_number = ViewHolder.get(convertView, R.id.tv_number);
					// Double dou=Double.parseDouble();
					// int a=;
					// if((""+ordermodel.getSclist().get(0).getPlist().get(0).getPnum()).equals("."))
					// {
					//
					// String
					// ccc=""+ordermodel.getSclist().get(0).getPlist().get(0).getPnum();
					// String a[]=
					// }else
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

				ImageView iv_pimg = ViewHolder.get(convertView, R.id.iv_pimg);

				new xUtilsImageLoader(getActivity()).display(iv_pimg, ordermodel.getSclist().get(0).getPlist().get(0).getPimg());

				TextView tv_onum = ViewHolder.get(convertView, R.id.tv_onum);
				tv_onum.setText("订单编号:" + ordermodel.getOnum());
				TextView tv_ostatus = ViewHolder.get(convertView, R.id.tv_ostatus);
				if (type == 1) {
					tv_ostatus.setText("");
				} else
					tv_ostatus.setText(ordermodel.getOstatus());// 订单状态
				TextView tv_oprice = ViewHolder.get(convertView, R.id.tv_oprice);
				tv_oprice.setText("￥" + ordermodel.getOprice());
				TextView tv_ocanop = ViewHolder.get(convertView, R.id.tv_ocanop);
				LinearLayout llbtn_ocanop = ViewHolder.get(convertView, R.id.llbtn_ocanop);

				TextView tv_ocanop2 = ViewHolder.get(convertView, R.id.tv_ocanop2);
				LinearLayout llbtn_ocanop2 = ViewHolder.get(convertView, R.id.llbtn_ocanop2);
				if (type == 0) {// 进行中
					llbtn_ocanop.setVisibility(View.VISIBLE);

					String anop = ordermodel.getOcanop();// 用户能进行的操作：0=不能进行任何操作、1=能取消订单、2=能确认收货

					if (anop.equals("0")) {
						tv_ocanop.setText("￥" + ordermodel.getOprice());
						llbtn_ocanop.setVisibility(View.GONE);
						llbtn_ocanop.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

							}
						});
					} else if (anop.equals("1")) {
						tv_ocanop.setText("取消订单");
						llbtn_ocanop.setOnClickListener(new View.OnClickListener() {

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
					} else if (anop.equals("2")) {
						tv_ocanop.setText("确认收货");
						llbtn_ocanop.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub Dialog dialog
								Dialog dialog = new Dialog();
								dialog.CreateDialog(getActivity(), "确认收货", "是否确认收货？");
								dialog.setOnDialogClistener(new DialogClistener() {

									@Override
									public void ret() {
										// TODO Auto-generated method stub

									}

									@Override
									public void ok() {
										// TODO Auto-generated method stub
										OkOrder(ordermodel.getOid());
									}
								});

							}
						});
					}

				} else if (type == 1)// 已完成订单
				{
					llbtn_ocanop.setVisibility(View.GONE);
					llbtn_ocanop2.setVisibility(View.VISIBLE);

					String pcomt = ordermodel.getSclist().get(0).getPlist().get(0).getPcomt();
					Log.v("aaa", "我是是================================》" + pcomt);
					if (pcomt.equals("1")) {
						tv_ocanop2.setText("评论");

						llbtn_ocanop2.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// 订单类型：1=洗车订单、3=纯商品订单
								// Utils.showMessage(getActivity(), "537" +
								// ordermodel.getOtype());
								if (ordermodel.getOtype().equals("1")) {

									HashMap<String, String> map = new HashMap<String, String>();
									map.put("type", "" + DiscussCommitActivity.DISCUSS_TYPE_FOR_SERVER);
									map.put("oid", "" + ordermodel.getOid());
									map.put("pid", "" + ordermodel.getSclist().get(0).getPlist().get(0).getPid());
									map.put("imageUrl", ordermodel.getSclist().get(0).getPlist().get(0).getPimg());
									map.put("pname", ordermodel.getSclist().get(0).getPlist().get(0).getPname());
									Log.v("aaa", "传入的数据是" + DiscussCommitActivity.DISCUSS_TYPE_FOR_SERVER + "/" + ordermodel.getOid() + "/" + ordermodel.getSclist().get(0).getPlist().get(0).getPid());
									JumpUtils.jumpto(getActivity(), DiscussCommitActivity.class, map);
								} else if (ordermodel.getOtype().equals("3")) {
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("type", "" + DiscussCommitActivity.DISCUSS_TYPE_FOR_GOODS);
									map.put("oid", "" + ordermodel.getOid());
									map.put("pid", "" + ordermodel.getSclist().get(0).getPlist().get(0).getPid());
									map.put("imageUrl", ordermodel.getSclist().get(0).getPlist().get(0).getPimg());
									map.put("pname", ordermodel.getSclist().get(0).getPlist().get(0).getPname());
									Log.v("aaa", "传入的数据是" + DiscussCommitActivity.DISCUSS_TYPE_FOR_GOODS + "/" + ordermodel.getOid() + "/" + ordermodel.getSclist().get(0).getPlist().get(0).getPid());
									JumpUtils.jumpto(getActivity(), DiscussCommitActivity.class, map);
								}
							}
						});

						// llbtn_ocanop2.setOnClickListener(new
						// View.OnClickListener() {
						//
						// @Override
						// public void onClick(View v) {
						// // TODO Auto-generated method stub
						// // /在这里跳转到评论界面
						// // TODO Auto-generated method stub
						//
						// }
						// });

					} else if (pcomt.equals("0")) {
						tv_ocanop2.setText("已评论");
						llbtn_ocanop2.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

							}
						});
					}

				}
			}
		};
	}
}
