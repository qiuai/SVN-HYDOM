//package com.carinsurance.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.carinsurance.adapter.AbstractBaseAdapter;
//import com.carinsurance.infos.LocationInfos;
//import com.carinsurance.infos.StoreChildModel;
//import com.carinsurance.infos.StoreGroupModel;
//import com.carinsurance.net.NetUtils;
//import com.carinsurance.net.Task;
//import com.carinsurance.utils.JsonUtil;
//import com.carinsurance.utils.JumpUtils;
//import com.carinsurance.utils.Utils;
//import com.carinsurance.utils.xUtilsImageLoader;
//import com.carinsurance.xlistview.XListView;
//import com.carinsurance.xlistview.XListView.IXListViewListener;
//import com.carinsurancer.car.R;
//import com.lidroid.xutils.ViewUtils;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.view.annotation.ViewInject;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//
////评价 activity_evaluation  lq	2015-7-22
//public class EvaluationActivity extends BaseActivity implements OnClickListener {
//
//	@ViewInject(R.id.myListView)
//	XListView myListView;
//	@ViewInject(R.id.return_btn)
//	ImageView return_btn;
//
//	// 保存经纬度
//	LocationInfos locationInfos;
//
//	List<StoreChildModel> childModel_list;
//
//	xUtilsImageLoader imageloader;
//	int page = 1;
//	int pageNum = 10;
//
//	AbstractBaseAdapter<StoreChildModel> adapter;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_evaluation);
//		ViewUtils.inject(this);
//		imageloader = new xUtilsImageLoader(EvaluationActivity.this);
//		locationInfos = (com.carinsurance.infos.LocationInfos) JumpUtils.getSerializable(EvaluationActivity.this);
//		return_btn.setOnClickListener(this);
//		childModel_list = new ArrayList<StoreChildModel>();
//		adapter = new AbstractBaseAdapter<StoreChildModel>(childModel_list) {
//
//			@Override
//			public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
//				// TODO Auto-generated method stub
//				if (convertView == null) {
//					convertView = EvaluationActivity.this.getLayoutInflater().inflate(R.layout.shop_details_tab2_adapter_item, parent, false);
//				}
////				StoreChildModel item = getItem(position);
////				ImageView img = ViewHolder.get(convertView, R.id.img);
////				imageloader.display(img, item.getSoimage());
////				// 门店名称
////				TextView soname = ViewHolder.get(convertView, R.id.soname);
////				TextView socomts = ViewHolder.get(convertView, R.id.socomts);
////				TextView soaddres = ViewHolder.get(convertView, R.id.soaddres);
////				TextView sodistance = ViewHolder.get(convertView, R.id.sodistance);
////				LinearLayout yuyue = ViewHolder.get(convertView, R.id.yuyue);
////				RatingBar ratingBar = ViewHolder.get(convertView, R.id.ratingBar);
////				ratingBar.setProgress(Integer.parseInt(item.getSostar()));
////				soname.setText("" + item.getSoname());
////				socomts.setText("" + item.getSocomts() + "评价");
////				soaddres.setText("" + item.getSoaddress());
////				sodistance.setText("" + item.getSodistance() + "km");
////				yuyue.setOnClickListener(new View.OnClickListener() {
////
////					@Override
////					public void onClick(View v) {
////						// TODO Auto-generated method stub
////
////					}
////				});
//
//				return convertView;
//			}
//		};
//		myListView.setAdapter(adapter);
//		myListView.setXListViewListener(new IXListViewListener() {
//
//			@Override
//			public void onRefresh() {
//				// TODO Auto-generated method stub
//				childModel_list.clear();
//				page = 1;
//				getStore("sc1", locationInfos.getLongitude(), locationInfos.getLatitude(), page++, pageNum);
//			}
//
//			@Override
//			public void onLoadMore() {
//				// TODO Auto-generated method stub
//
//				getStore("sc1", locationInfos.getLongitude(), locationInfos.getLatitude(), page++, pageNum);
//			}
//		});
//		myListView.startLoadMore();
//	}
//
//	/**
//	 * 根据经纬度获取商店列表
//	 * 
//	 * @param scid
//	 * @param lng
//	 *            经度
//	 * @param lat
//	 * @param page
//	 * @param maxresult
//	 */
//	public void getStore(String scid, String lng, String lat, int page, int maxresult) {
//		String uid = Utils.getUid(EvaluationActivity.this);
//		String token = Utils.getToken(EvaluationActivity.this);
//		RequestParams params = new RequestParams();
//		params.addBodyParameter("uid", uid);
//		params.addBodyParameter("token", token);
//		params.addBodyParameter("scid", scid);
//		params.addBodyParameter("lng", lng);
//		params.addBodyParameter("lat", lat);
//		params.addBodyParameter("page", "" + page);
//		params.addBodyParameter("maxresult", "" + maxresult);
//		params.addBodyParameter("uid", uid);
//		params.addBodyParameter("uid", uid);
//		params.addBodyParameter("uid", uid);
//		NetUtils.getIns().post(Task.GET_STORE, params, handler);
//	}
//
//	@Override
//	public void initNetmessageFailure(String message, String task) {
//		// TODO Auto-generated method stub
//		super.initNetmessageFailure(message, task);
//		try {
//			if (task.equals(Task.GET_STORE)) {
//				page--;
//				myListView.stopLoadMore();
//				myListView.stopRefresh();
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//
//	@Override
//	public void initNetmessageSuccess(String message, String task) {
//		// TODO Auto-generated method stub
//		super.initNetmessageSuccess(message, task);
//		switch (task) {
//		case Task.GET_STORE:
//			try {
//				String result = message;
//
//				StoreGroupModel model = JsonUtil.getEntityByJsonString(result, StoreGroupModel.class);
//				if (model.getResult().equals("" + NetUtils.NET_SUCCESS_001)) {
//
//					if (!model.getList().isEmpty()) {
//						childModel_list.addAll(model.getList());
//						adapter.notifyDataSetChanged();
//						myListView.stopLoadMore();
//						myListView.stopRefresh();
//					} else {
//						page--;
//						Utils.showMessage(EvaluationActivity.this, "已加载全部数据！");
//						myListView.stopLoadMore();
//						myListView.stopRefresh();
//					}
//				}
//
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			break;
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.return_btn:
//			JumpUtils.jumpfinish(EvaluationActivity.this);
//			break;
//		}
//	}
//
//}