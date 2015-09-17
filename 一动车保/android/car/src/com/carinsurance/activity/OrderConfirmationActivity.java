package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.carinsurance.adapter.MyOrderDetailAdapter;
import com.carinsurance.infos.BaoyangOrderDetail;
import com.carinsurance.infos.CrashDetailModel;
import com.carinsurance.infos.LocationInfos;
import com.carinsurance.infos.ProductDefaultItemModel;
import com.carinsurance.infos.UpDingDan;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.MathUtils;
import com.carinsurance.utils.MyThreadTool;
import com.carinsurance.utils.RepeatClick;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderConfirmationActivity extends BaseActivity {

	public static int ENTER_PAY = 1;// 进入支付页面

	// @ViewInject(R.id.xu_line0)
	// LinearLayout xu_line0;
	// @ViewInject(R.id.xu_line1)
	// LinearLayout xu_line1;
	// @ViewInject(R.id.xu_line2)
	// LinearLayout xu_line2;
	// @ViewInject(R.id.xu_line3)
	// LinearLayout xu_line3;
	@ViewInject(R.id.return_btn)
	ImageView return_btn;

	UpDingDan upDingDan;
	@ViewInject(R.id.fr_nojishi)
	FrameLayout fr_nojishi;
	@ViewInject(R.id.fr_jiazaizhong)
	FrameLayout fr_jiazaizhong;
	@ViewInject(R.id.shuaxin)
	FrameLayout shuaxin;
	@ViewInject(R.id.xiugai)
	FrameLayout xiugai;

	@ViewInject(R.id.tv_contact)
	TextView tv_contact;
	@ViewInject(R.id.tv_phone)
	TextView tv_phone;
	@ViewInject(R.id.tv_csname)
	TextView tv_csname;
	@ViewInject(R.id.tv_clearType)
	TextView tv_clearType;
	@ViewInject(R.id.tv_PalateNumber)
	TextView tv_PalateNumber;
	@ViewInject(R.id.tv_Color)
	TextView tv_Color;
	@ViewInject(R.id.img)
	ImageView img;

	// @ViewInject(R.id.tv_addressName)
	// TextView tv_addressName;
	@ViewInject(R.id.xiugai0)
	FrameLayout xiugai0;

	@ViewInject(R.id.tv_payWay)
	TextView tv_payWay;
	@ViewInject(R.id.zhifu)
	FrameLayout zhifu;
	@ViewInject(R.id.tv_tishi)
	TextView tv_tishi;
	// @ViewInject(R.id.my_weizhi)
	// LinearLayout my_weizhi;

	@ViewInject(R.id.ll_yuyue_time)
	LinearLayout ll_yuyue_time;
	@ViewInject(R.id.yuyue_time)
	TextView yuyue_time;
	@ViewInject(R.id.by_00)
	LinearLayout by_00;
	@ViewInject(R.id.by_01)
	LinearLayout by_01;

	@ViewInject(R.id.xc_0)
	LinearLayout xc_0;
	@ViewInject(R.id.xc_1)
	LinearLayout xc_1;
	com.carinsurance.utils.Dialog dialog;
	@ViewInject(R.id.iv_0)
	ImageView iv_0;
	@ViewInject(R.id.iv_1)
	ImageView iv_1;
	@ViewInject(R.id.iv_2)
	ImageView iv_2;

	@ViewInject(R.id.gongduoshaojian)
	TextView gongduoshaojian;
	String type = "0";// 种类（0 上门洗车 1上门保养）默认上门洗车 2.洗车订单的看订单状态的界面 3保养看订单状态界面
	@ViewInject(R.id.scprice)
	TextView scprice;
	@ViewInject(R.id.scremark2)
	TextView scremark2;
	@ViewInject(R.id.scremark1)
	TextView scremark1;
	@ViewInject(R.id.by_1)
	LinearLayout by_1;
	@ViewInject(R.id.see_xc01)
	LinearLayout see_xc01;
	@ViewInject(R.id.see_bottom_btn)
	LinearLayout see_bottom_btn;
	@ViewInject(R.id.lyc_f_0)
	FrameLayout lyc_f_0;
	@ViewInject(R.id.order_num)
	TextView order_num;
	CrashDetailModel crashDetailModel;
	BaoyangOrderDetail baoyangOrderDetail;

	@ViewInject(R.id.tv_ostatus)
	TextView tv_ostatus;

	String oid;
	@ViewInject(R.id.tv_usecoup)
	TextView tv_usecoup;
	@ViewInject(R.id.tv_fuwufeiyong)
	TextView tv_fuwufeiyong;
	@ViewInject(R.id.tv_youhuijuan)
	TextView tv_youhuijuan;
	@ViewInject(R.id.tv_shifukuan)
	TextView tv_shifukuan;
	@ViewInject(R.id.tv_shangpingjiage)
	TextView tv_shangpingjiage;

	@ViewInject(R.id.fuwujine)
	TextView fuwujine;
	@ViewInject(R.id.youhuiquan)
	LinearLayout youhuiquan;

	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.by_000)
	LinearLayout by_000;

	private void initBaoyangxiangqing() {
		// TODO Auto-generated method stub
		LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) DisplayUtil.getDip(OrderConfirmationActivity.this, 50));
		layoutParam.setMargins(0, 0, 0, (int) DisplayUtil.getDip(OrderConfirmationActivity.this, 20));
		youhuiquan.setLayoutParams(layoutParam);

		tv_ostatus.setText("" + baoyangOrderDetail.getOstatus());
		tv_contact.setText("联系人:" + baoyangOrderDetail.getOcontact());
		tv_phone.setText("联系电话:" + baoyangOrderDetail.getOphone());
		tv_csname.setText("服务车型:" + baoyangOrderDetail.getOcmname());
		tv_clearType.setText("下单时间:" + baoyangOrderDetail.getOdate());
		tv_PalateNumber.setText("汽车车牌:" + baoyangOrderDetail.getOplateNum());
		tv_Color.setText("汽车颜色:" + baoyangOrderDetail.getOcarcolor());
		order_num.setText("订单编号:" + baoyangOrderDetail.getOnum());
		initExpandableListView();
		yuyue_time.setText("" + baoyangOrderDetail.getStime());
		tv_usecoup.setText("" + baoyangOrderDetail.getUsecoup());
		if ((baoyangOrderDetail.getPayway()).equals("1")) {
			tv_payWay.setText("会员卡支付");
		} else if ((baoyangOrderDetail.getPayway()).equals("2")) {
			tv_payWay.setText("支付宝");
		} else if ((baoyangOrderDetail.getPayway()).equals("3")) {
			tv_payWay.setText("银联");
		} else if ((baoyangOrderDetail.getPayway()).equals("4")) {
			tv_payWay.setText("微信");
		} else if ((baoyangOrderDetail.getPayway()).equals("5")) {
			tv_payWay.setText("现场pos机刷卡");
		}

		// myExpandableListViewShowAll1
		// scprice.setText("" + baoyangOrderDetail.getOsprice());
		// scremark1.setText("" + baoyangOrderDetail.getOsname());
		// scremark2.setText("" + baoyangOrderDetail.getOsremark());// /

		// tv_fuwufeiyong.setText("￥" + baoyangOrderDetail.getOcmoney());
		// tv_youhuijuan.setText("￥" + baoyangOrderDetail.getCpmoney());
		// tv_shifukuan.setText("￥" + baoyangOrderDetail.getPaymoney());
		//

		// if (baoyangOrderDetail.getOcleanType().equals("1")) {
		//
		// tv_clearType.setText("服务方式:清洗车身");
		// } else {
		// tv_clearType.setText("服务方式:内外清洗");
		// }

		//
		// new xUtilsImageLoader(OrderConfirmationActivity.this).display(img,
		// baoyangOrderDetail.getOsimgpath());
		//

		// tv_usecoup.setText("" + baoyangOrderDetail.getUsecoup());

	}

	private void initExpandableListView() {
		// TODO Auto-generated method stub

		MyOrderDetailAdapter adapter;
		ExpandableListView myExpandableListViewShowAll1 = (ExpandableListView) this.findViewById(R.id.myExpandableListViewShowAll1);
		myExpandableListViewShowAll1.setVisibility(View.VISIBLE);
		// myExpandableListViewShowAll1.setDivider(null);
		// myExpandableListViewShowAll1.setDividerHeight((int)
		// DisplayUtil.getDip(OrderConfirmationActivity.this, 1));
		myExpandableListViewShowAll1.setAdapter(adapter = new MyOrderDetailAdapter(OrderConfirmationActivity.this, baoyangOrderDetail.getSclist(), type));

		myExpandableListViewShowAll1.setDivider(getResources().getDrawable(R.color.bj_f0f0f0));
		myExpandableListViewShowAll1.setChildDivider(getResources().getDrawable(R.color.bj_f0f0f0));
		myExpandableListViewShowAll1.setDividerHeight((int) DisplayUtil.getDip(OrderConfirmationActivity.this, 1));

		myExpandableListViewShowAll1.setGroupIndicator(null); // 去掉父类的箭头
		// 展开所有
		for (int i = 0, length = adapter.getGroupCount(); i < length; i++) {
			myExpandableListViewShowAll1.expandGroup(i);
		}
		myExpandableListViewShowAll1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;// 默认为false，设为true时，点击事件不会展开Group
			}
		});
		// 如果没有商品，服务费用==实付款
		Log.v("aaa", "adapter.is_hasshangping-->" + adapter.is_hasshangping());

		tv_shangpingjiage.setText("￥" + baoyangOrderDetail.getOpmoney());

		tv_fuwufeiyong.setText("￥" + baoyangOrderDetail.getOcmoney());
		tv_youhuijuan.setText("￥" + baoyangOrderDetail.getCpmoney());
		tv_shifukuan.setText("￥" + baoyangOrderDetail.getPaymoney());
		if (adapter.is_hasshangping() == false) {
			// by_000.setVisibility(View.GONE);
			// tv_shifukuan.setText("￥" + baoyangOrderDetail.getPaymoney());
//			getCpmoney          getPaymoney
			String add=MathUtils.add(baoyangOrderDetail.getPaymoney(), baoyangOrderDetail.getCpmoney()).toString();
			tv_fuwufeiyong.setText("￥" + add);
		}
	}

	private void initXiCheLiulan() {
		// TODO Auto-generated method stub
		android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, (int) DisplayUtil.getDip(OrderConfirmationActivity.this, 50));
		params.setMargins(0, (int) DisplayUtil.getDip(OrderConfirmationActivity.this, 20), 0, 0);
		by_000.setLayoutParams(params);

		scprice.setText("￥" + crashDetailModel.getOsprice());
		scremark1.setText("" + crashDetailModel.getOsname());
		scremark2.setText("" + crashDetailModel.getOsremark());// /

		tv_fuwufeiyong.setText("￥" + crashDetailModel.getOsprice());
		tv_youhuijuan.setText("￥" + crashDetailModel.getCpmoney());
		tv_shifukuan.setText("￥" + crashDetailModel.getPaymoney());

		tv_ostatus.setText("" + crashDetailModel.getOstatus());
		tv_contact.setText("联系人:" + crashDetailModel.getOcontact());
		tv_phone.setText("联系电话:" + crashDetailModel.getOphone());
		tv_csname.setText("服务车型:" + crashDetailModel.getOcmname());

		if (crashDetailModel.getOcleanType().equals("1")) {

			tv_clearType.setText("服务方式:清洗车身");
		} else {
			tv_clearType.setText("服务方式:内外清洗");
		}
		tv_PalateNumber.setText("汽车车牌:" + crashDetailModel.getOplateNum());
		tv_Color.setText("汽车颜色:" + crashDetailModel.getOcarcolor());
		order_num.setText("订单编号:" + crashDetailModel.getOnum());

		new xUtilsImageLoader(OrderConfirmationActivity.this).display(img, crashDetailModel.getOsimgpath());

		if ((crashDetailModel.getPayway()).equals("1")) {
			tv_payWay.setText("会员卡支付");
		} else if ((crashDetailModel.getPayway()).equals("2")) {
			tv_payWay.setText("支付宝");
		} else if ((crashDetailModel.getPayway()).equals("3")) {
			tv_payWay.setText("银联");
		} else if ((crashDetailModel.getPayway()).equals("4")) {
			tv_payWay.setText("微信");
		} else if ((crashDetailModel.getPayway()).equals("5")) {
			tv_payWay.setText("现场pos机刷卡");
		}
		tv_usecoup.setText("" + crashDetailModel.getUsecoup());

		// scremark1.setText("" +
		// ordermodel.getSclist().get(0).getPlist().get(0).getPname());
		// scremark2.setText("" +
		// ordermodel.getSclist().get(0).getPlist().get(0).getPremark());// /

		// scprice.setText("￥" +
		// ordermodel.getSclist().get(0).getPlist().get(0).getPprice());

		// new xUtilsImageLoader(OrderConfirmationActivity.this).display(img,
		// upDingDan.getSortModel().getCbimage());
		// scremark1.setText("" +
		// ordermodel.getSclist().get(0).getPlist().get(0).getPname());
		// scremark2.setText("" +
		// ordermodel.getSclist().get(0).getPlist().get(0).getPremark());// /
		// skjdflksjdflksj
		// //在这里终止

		// // tv_addressName.setText(""+upDingDan.getAddress());
		// if (upDingDan.getPayWay().equals("1")) {
		// tv_payWay.setText("货到付款");
		// } else if (upDingDan.getPayWay().equals("2")) {
		// tv_payWay.setText("支付宝");
		// } else if (upDingDan.getPayWay().equals("3")) {
		// tv_payWay.setText("银联");
		// } else if (upDingDan.getPayWay().equals("4")) {
		// tv_payWay.setText("微信");
		// }
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_confirmation);
		ViewUtils.inject(this);

		type = JumpUtils.getString(OrderConfirmationActivity.this, "type");
		if (StringUtil.isNullOrEmpty(type)) {
			type = "0";
		}

		if (type.equals("0") || type.equals("1"))
			upDingDan = (UpDingDan) JumpUtils.getSerializable(OrderConfirmationActivity.this);
		if (type.equals("2")) {
			// ordermodel = (OrderModel)
			// JumpUtils.getSerializable(OrderConfirmationActivity.this);
			oid = JumpUtils.getString(OrderConfirmationActivity.this, "oid");// (OrderConfirmationActivity.this);
		}
		if (type.equals("3"))
			oid = JumpUtils.getString(OrderConfirmationActivity.this, "oid");// (OrderConfirmationActivity.this);
		// Log.v("aaa", "upDingDan" + upDingDan.toString());
		// xu_line0.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		// xu_line1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		// xu_line2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		// xu_line3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速

		if (type.equals("0")) {// 上门洗车
			checkisHasjishi();
			by_00.setVisibility(View.GONE);
			by_01.setVisibility(View.GONE);
			by_1.setVisibility(View.GONE);
			initView();
		} else if (type.equals("1")) {// 上门保养
			// checkisHasjishi();

			initBaoyang();
			initView();
		} else if (type.equals("2")) {// 洗车订单详情
			// initXiCheLiulan();
			by_1.setVisibility(View.GONE);
			see_xc01.setVisibility(View.VISIBLE);
			lyc_f_0.setVisibility(View.GONE);
			title.setText("订单详情");
			by_00.setVisibility(View.GONE);
			by_01.setVisibility(View.GONE);
			see_bottom_btn.setVisibility(View.GONE);
			getDingdanXiangqing(0);
		} else if (type.equals("3")) {// 保养订单详情
			// initXiCheLiulan();
			xc_1.setVisibility(View.GONE);
			by_00.setVisibility(View.GONE);
			ll_yuyue_time.setVisibility(View.VISIBLE);
			by_1.setVisibility(View.VISIBLE);
			fuwujine.setText("服务费用");
			by_01.setVisibility(View.GONE);
			title.setText("订单详情");
			see_xc01.setVisibility(View.VISIBLE);
			lyc_f_0.setVisibility(View.GONE);
			see_bottom_btn.setVisibility(View.GONE);
			getDingdanXiangqing(1);
		}
	}

	/**
	 * 0是洗车订单详情 1是保养订单详情
	 * 
	 * @param pos
	 */
	private void getDingdanXiangqing(int pos) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(OrderConfirmationActivity.this));
		map.put("token", Utils.getToken(OrderConfirmationActivity.this));
		map.put("oid", oid);
		if (pos == 0)
			NetUtils.getIns().post(Task.GET_XICHE_ORDER_XIANGQING, map, handler);
		else if (pos == 1) {
			NetUtils.getIns().post(Task.GET_BAOYANG_ORDER_XIANGQING, map, handler);
		}
	}

	private void initBaoyang() {
		// TODO Auto-generated method stub
		ll_yuyue_time.setVisibility(View.VISIBLE);
		yuyue_time.setText("" + upDingDan.getStime() + "-" + upDingDan.getEtime());
		xc_0.setVisibility(View.GONE);
		xc_1.setVisibility(View.GONE);

		// 如果是自备配件
		if (upDingDan.getSortModel().getIs_zibeipeijian().equals("1")) {
			by_00.setVisibility(View.GONE);
			by_01.setVisibility(View.VISIBLE);
			by_01.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("type", "1");

					JumpUtils.jumpto(OrderConfirmationActivity.this, SelectServer1Activity.class, upDingDan.getSortModel(), map);
				}
			});
			// 烦烦烦
			// by_01.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// JumpUtils.jumpto(OrderConfirmationActivity.this,
			// SelectServer1Activity.class, sortModel);
			// }
			// });
		} else {// 有商品的时候
			by_01.setVisibility(View.GONE);
			youshangping();
		}

	}

	private void youshangping() {
		// TODO Auto-generated method stub
		by_00.setVisibility(View.VISIBLE);
		by_00.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "0");

				JumpUtils.jumpto(OrderConfirmationActivity.this, SelectServer1Activity.class, upDingDan.getSortModel(), map);
			}
		});

		int cc = 0;// 只显示三张图
		int size = 0;// 共多少件商品
		for (int i = 0; i < upDingDan.getSortModel().getSelectSeriverTypeitemList().size(); i++) {
			for (int j = 0; j < upDingDan.getSortModel().getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().size(); j++) {
				// gongduoshaojian.setText();
				if (upDingDan.getSortModel().getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list() != null) {
					if (!upDingDan.getSortModel().getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().isEmpty()) {
						size++;
						Log.v("ccc", "cc=" + cc);
						ProductDefaultItemModel p = upDingDan.getSortModel().getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j);
						if (cc == 2) {
							cc++;
							iv_2.setVisibility(View.VISIBLE);
							new xUtilsImageLoader(OrderConfirmationActivity.this).display(iv_2, p.getPimage());
						} else if (cc == 1) {
							cc++;
							iv_1.setVisibility(View.VISIBLE);
							new xUtilsImageLoader(OrderConfirmationActivity.this).display(iv_1, p.getPimage());
						} else if (cc == 0) {
							cc++;
							iv_0.setVisibility(View.VISIBLE);
							new xUtilsImageLoader(OrderConfirmationActivity.this).display(iv_0, p.getPimage());
						}

					}

				}

			}
		}
		gongduoshaojian.setText("共" + size + "件");
		by_01.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpto(OrderConfirmationActivity.this, SelectServer1Activity.class, upDingDan.getSortModel());
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrEmpty(upDingDan.getCpid()))
			tv_usecoup.setText("未使用");
		else
			tv_usecoup.setText("已使用");
		tv_shangpingjiage.setText(upDingDan.getTv_shangpingjiage());
		tv_youhuijuan.setText(upDingDan.getTv_youhuijuan());
		tv_fuwufeiyong.setText(upDingDan.getTv_fuwufeiyong());
		tv_shifukuan.setText(upDingDan.getTv_shifukuan());

		scprice.setText("￥" + upDingDan.getSortModel().getSeriverTypeitemModel0().getScprice());
		scremark1.setText("" + upDingDan.getSortModel().getSeriverTypeitemModel0().getScremark1());
		scremark2.setText("" + upDingDan.getSortModel().getSeriverTypeitemModel0().getScremark2());// /
		// try {
		tv_contact.setText("联系人:" + upDingDan.getContact());
		tv_phone.setText("联系电话:" + upDingDan.getPhone());
		Log.v("aaa", "sortModel==>" + upDingDan.getSortModel());
		tv_csname.setText("服务车型:" + upDingDan.getSortModel().getCs_name());

		if (type.equals("0")) {
			if (upDingDan.getCleanType().equals("1")) {
				tv_clearType.setText("服务方式:清洗车身");
			} else {
				tv_clearType.setText("服务方式:内外清洗");
			}
		} else {
			tv_clearType.setText("服务方式:上门服务");
		}

		tv_PalateNumber.setText("汽车车牌:" + upDingDan.getPalateNumber());
		tv_Color.setText("汽车颜色:" + upDingDan.getColor());

		new xUtilsImageLoader(OrderConfirmationActivity.this).display(img, upDingDan.getSortModel().getSeriverTypeitemModel0().getScimage());
		// tv_addressName.setText(""+upDingDan.getAddress());
		if (upDingDan.getPayWay().equals("1")) {
			tv_payWay.setText("会员卡支付");
		} else if (upDingDan.getPayWay().equals("2")) {
			tv_payWay.setText("支付宝");
		} else if (upDingDan.getPayWay().equals("3")) {
			tv_payWay.setText("银联");
		} else if (upDingDan.getPayWay().equals("4")) {
			tv_payWay.setText("微信");
		} else if (upDingDan.getPayWay().equals("5")) {
			tv_payWay.setText("现场pos机刷卡");
		}

		// tv_payWay.setText(""+upDingDan.getPayWay();
		// //////
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

	}

	@OnClick({ R.id.return_btn, R.id.fr_nojishi, R.id.fr_jiazaizhong, R.id.xiugai, R.id.shuaxin, R.id.xiugai0, R.id.zhifu, R.id.my_weizhi })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(OrderConfirmationActivity.this);
			break;
		case R.id.fr_nojishi:
		case R.id.fr_jiazaizhong:
			break;
		case R.id.xiugai:
		case R.id.xiugai0:
			JumpUtils.jumpfinish(OrderConfirmationActivity.this);
			break;
		case R.id.zhifu:

			RepeatClick.setRepeatClick(v);
			if (type.equals("0"))
				xichezhifu();
			else if (type.equals("1"))
				baoyangzhifu();
			break;
		case R.id.shuaxin:
			checkisHasjishi();
			break;
		case R.id.my_weizhi:

			LocationInfos locationInfos = new LocationInfos();

			if (type.equals("2")) {
				if (crashDetailModel != null) {

					if (!StringUtil.isNullOrEmpty(crashDetailModel.getLat()) && !StringUtil.isNullOrEmpty(crashDetailModel.getLng())) {
						locationInfos.setLatitude(crashDetailModel.getLat());
						locationInfos.setLongitude(crashDetailModel.getLng());
						JumpUtils.jumpto(OrderConfirmationActivity.this, LocationOnlySeeActivity.class, locationInfos);
					}
				}
			} else if (type.equals("3")) {
				// Log.v("aaa","紧到了561=》"+baoyangOrderDetail.toString());
				if (baoyangOrderDetail != null) {

					if (!StringUtil.isNullOrEmpty(baoyangOrderDetail.getLat()) && !StringUtil.isNullOrEmpty(baoyangOrderDetail.getLng())) {

						locationInfos.setLatitude(baoyangOrderDetail.getLat());
						locationInfos.setLongitude(baoyangOrderDetail.getLng());
						JumpUtils.jumpto(OrderConfirmationActivity.this, LocationOnlySeeActivity.class, locationInfos);
					}
				}

			} else {
				if (!StringUtil.isNullOrEmpty(upDingDan.getLat()) && !StringUtil.isNullOrEmpty(upDingDan.getLng())) {
					locationInfos.setLatitude(upDingDan.getLat());
					locationInfos.setLongitude(upDingDan.getLng());
					JumpUtils.jumpto(OrderConfirmationActivity.this, LocationOnlySeeActivity.class, locationInfos);
				}
			}

			break;
		}
	}

	/**
	 * 保养支付
	 */
	private void baoyangzhifu() {
		// TODO Auto-generated method stub
		Utils.showPrgress_Noclose(OrderConfirmationActivity.this);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		map.put("ucid", upDingDan.getUcid());
		map.put("stime", upDingDan.getStime());
		map.put("etime", upDingDan.getEtime());
		map.put("phone", upDingDan.getPhone());
		map.put("contact", upDingDan.getContact());
		map.put("plateNumber", upDingDan.getPalateNumber());
		map.put("color", upDingDan.getColor());
		map.put("lat", upDingDan.getLat());
		map.put("lng", upDingDan.getLng());
		map.put("address", upDingDan.getAddress());
		if (!StringUtil.isNullOrEmpty(upDingDan.getCpid()))
			map.put("cpid", upDingDan.getCpid());

		if (!StringUtil.isNullOrEmpty(upDingDan.getRemark()))
			map.put("remark", upDingDan.getRemark());

		map.put("payWay", upDingDan.getPayWay());

		map.put("httpData", upDingDan.getHttpData());

		// map.put("scid",
		// upDingDan.getSortModel().getSeriverTypeitemModel0().getScid());
		// map.put("cleanType", upDingDan.getCleanType());
		NetUtils.getIns().post(Task.TIIJAO_BAOYANG_ORDER, map, handler);
	}

	/**
	 * 洗车流程的支付
	 */
	private void xichezhifu() {
		// TODO Auto-generated method stub
		Utils.showPrgress_Noclose(OrderConfirmationActivity.this);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		map.put("ucid", upDingDan.getUcid());
		map.put("scid", upDingDan.getSortModel().getSeriverTypeitemModel0().getScid());
		map.put("contact", upDingDan.getContact());
		map.put("phone", upDingDan.getPhone());
		map.put("plateNumber", upDingDan.getPalateNumber());
		map.put("color", upDingDan.getColor());
		map.put("lat", upDingDan.getLat());
		map.put("lng", upDingDan.getLng());
		map.put("address", upDingDan.getAddress());
		if (!StringUtil.isNullOrEmpty(upDingDan.getCpid()))
			map.put("cpid", upDingDan.getCpid());

		map.put("cleanType", upDingDan.getCleanType());
		map.put("payWay", upDingDan.getPayWay());
		NetUtils.getIns().post(Task.TIJIAO_XICHE_ORDER, map, handler);

		// upDingDan;

	}

	// 检查是否有技师为你服务
	public void checkisHasjishi() {
		showADialog(2);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(OrderConfirmationActivity.this));
		map.put("token", Utils.getToken(OrderConfirmationActivity.this));
		NetUtils.getIns().post(Task.GET_ISHASTECHNICIAN, map, handler);
	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		switch (task) {
		case Task.TIIJAO_BAOYANG_ORDER:
		case Task.TIJIAO_XICHE_ORDER:
			try {
				Utils.ExitPrgress_Noclose(OrderConfirmationActivity.this);
			} catch (Exception e) {
				// TODO: handle exception
			}

			break;
		}

	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.GET_ISHASTECHNICIAN:
			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				if (result.equals("001")) {
					String canserver = js.getString("canserver");
					if (canserver.equals("1")) {
						showADialog(1);
					} else {
						showADialog(0);
					}

				} else {
					Utils.showMessage(OrderConfirmationActivity.this, "服务器错误！错误码" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case Task.TIJIAO_XICHE_ORDER:
			try {

				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				if (result.equals("001")) {
					String oid = js.getString("oid");
					String onum = js.getString("onum");
					String payAction = js.getString("payAction");

					if (payAction.equals("2"))
						Utils.zhifuchenggongTishi(OrderConfirmationActivity.this, 0, dialog, handler, 1);
					else if (payAction.equals("3")) {
						Utils.showMessage(OrderConfirmationActivity.this, "余额不足！");
					} else if (payAction.equals("1")) {
						pay(onum);
					} else {
						Utils.showMessage(OrderConfirmationActivity.this, "支付失败,错误码：" + result + payAction);
					}

				} else if (result.equals("501")) {
					Utils.showMessage(OrderConfirmationActivity.this, "超过4点请从新选择预约时间！");
				} else {
					Utils.showMessage(OrderConfirmationActivity.this, "订单提交失败,错误码:" + result);
				}
				Utils.ExitPrgress_Noclose(OrderConfirmationActivity.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case Task.TIIJAO_BAOYANG_ORDER:

			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				if (result.equals("001")) {
					String oid = js.getString("oid");
					String onum = js.getString("onum");
					String payAction = js.getString("payAction");

					if (payAction.equals("2"))
						Utils.zhifuchenggongTishi(OrderConfirmationActivity.this, 1, dialog, handler, 1);
					else if (payAction.equals("3")) {
						Utils.showMessage(OrderConfirmationActivity.this, "余额不足！");
					} else if (payAction.equals("1")) {
						pay(onum);
					} else {
						Utils.showMessage(OrderConfirmationActivity.this, "支付失败,错误码：" + result + payAction);
					}

					// if (upDingDan.getPayWay().equals("1")) {
					// // tv_payWay.setText("货到付款");
					// Utils.zhifuchenggongTishi(OrderConfirmationActivity.this,
					// 1, dialog, handler, 1);
					// } else if (upDingDan.getPayWay().equals("2")) {
					// // tv_payWay.setText("支付宝");
					// HashMap<String, String> map = new HashMap<String,
					// String>();
					// map.put("dingdanhao", oid);
					// map.put("body", "保养订单");
					// String price = tv_shifukuan.getText().toString().trim();
					// String pprice = price.replaceAll("￥", "");
					// map.put("price", pprice);
					// map.put("infos", "保养订单");
					// map.put("huidiaoNet", "");
					// //
					// JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this,
					// // MyPayActivity.class, map);
					// JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this,
					// MyPayActivity.class, map, ENTER_PAY);
					// } else if (upDingDan.getPayWay().equals("3")) {
					// // tv_payWay.setText("银联");
					// } else if (upDingDan.getPayWay().equals("4")) {
					// // tv_payWay.setText("微信");
					// }

				} else if (result.equals("501")) {
					Utils.showMessage(OrderConfirmationActivity.this, "预约时间出错(超过4点需重新选择预约时间)！");
				} else {
					Utils.showMessage(OrderConfirmationActivity.this, "订单提交失败,错误码:" + result);
				}
				Utils.ExitPrgress_Noclose(OrderConfirmationActivity.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Task.GET_XICHE_ORDER_XIANGQING:

			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				if (result.equals("001")) {
					crashDetailModel = JsonUtil.getEntityByJsonString(message, CrashDetailModel.class);
					initXiCheLiulan();
				} else {
					Utils.showMessage(OrderConfirmationActivity.this, "未知异常,错误码:" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Task.GET_BAOYANG_ORDER_XIANGQING:

			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				if (result.equals("001")) {
					baoyangOrderDetail = JsonUtil.getEntityByJsonString(message, BaoyangOrderDetail.class);
					initBaoyangxiangqing();
				} else {
					Utils.showMessage(OrderConfirmationActivity.this, "未知异常,错误码:" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}
	}

	/**
	 * 调用支付接口 onum订单号
	 */
	private void pay(String onum) {
		// TODO Auto-generated method stub

		if (upDingDan.getPayWay().equals("1")) {
			// tv_payWay.setText("会员卡支付");
			// zhifuchenggongTishi(0);支付动作：
			// 1=调用支付接口；
			// 2=支付完成【】；
			// 3=余额不足【会员卡支付】

		} else if (upDingDan.getPayWay().equals("2")) {// 这是调用支付宝的
			// tv_payWay.setText("支付宝");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("dingdanhao", onum);
			if (type.equals("0")) {
				map.put("body", "洗车订单");
			} else if (type.equals("1")) {
				map.put("body", "保养订单");
			}
			String price = tv_shifukuan.getText().toString().trim();
			String pprice = price.replaceAll("￥", "");
			map.put("price", pprice);
			if (type.equals("0")) {
				map.put("infos", "洗车订单");
			} else if (type.equals("1")) {
				map.put("infos", "保养订单");
			}
			map.put("huidiaoNet", Task.ALIPAY_RETURN_URL);
			map.put("type", "0");
			// JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this,
			// MyPayActivity.class, map);
			JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this, MyPayActivity.class, map, ENTER_PAY);
		} else if (upDingDan.getPayWay().equals("3")) {
			// tv_payWay.setText("银联");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("dingdanhao", onum);
			map.put("type", "2");
			JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this, MyPayActivity.class, map, ENTER_PAY);
		} else if (upDingDan.getPayWay().equals("4")) {
			// tv_payWay.setText("微信");
			Log.v("aaa", "type==>" + type);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("dingdanhao", onum);
			map.put("type", "1");
			JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this, MyPayActivity.class, map, ENTER_PAY);
		} else if (upDingDan.getPayWay().equals("5")) {// 这里由服务器的payAction参数判定了的，so不会走到这一步
			// tv_payWay.setText("现场pos机刷卡");
		}
	}

	@Override
	protected void initHandeMessage(Message msg) {
		// TODO Auto-generated method stub
		super.initHandeMessage(msg);

		try {
			if (msg.what == 1) {
				Log.v("aaa", "运行了463");

				List<Activity> list = getActivities();
				List<Activity> lnt = new ArrayList<Activity>();
				for (int i = 0; i < list.size(); i++) {
					if (i != 0 && i != list.size() - 1) {
						lnt.add(list.get(i));
					}
				}
				for (int i = 0; i < lnt.size(); i++) {
					if (lnt.get(i) != null)
						lnt.get(i).finish();
				}
				JumpUtils.jumpfinish(OrderConfirmationActivity.this);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 支付成功
	/**
	 * 0是洗车支付成功 1是保养支付成功
	 * 
	 * @param pos
	 */
	private void zhifuchenggongTishi(int pos) {
		// TODO Auto-generated method stub

		try {
			View view = null;
			if (pos == 0)
				view = getLayoutInflater().inflate(R.layout.dialog2_pay_success, null);
			else
				view = getLayoutInflater().inflate(R.layout.dialog1_pay_success, null);
			dialog = new com.carinsurance.utils.Dialog();
			dialog.CreateDialog(OrderConfirmationActivity.this, view);

			MyThreadTool.fixedThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(3000);
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 
	 * @param i
	 *            0没有找到空闲技师 1.找到了空闲技师 2.加载中
	 */
	public void showADialog(int i) {

		switch (i) {
		case 0:
			try {
				fr_nojishi.setVisibility(View.VISIBLE);
				fr_jiazaizhong.setVisibility(View.GONE);
				tv_tishi.setVisibility(View.VISIBLE);

				tv_tishi.setText("");
			} catch (Exception e) {
				// TODO: handle exception
			}

			break;
		case 1:
			try {
				fr_nojishi.setVisibility(View.GONE);
				fr_jiazaizhong.setVisibility(View.GONE);
				tv_tishi.setVisibility(View.VISIBLE);

				tv_tishi.setText("附近有技师为您提供服务");
			} catch (Exception e) {
				// TODO: handle exception
			}

			break;
		case 2:
			try {
				fr_nojishi.setVisibility(View.GONE);
				fr_jiazaizhong.setVisibility(View.VISIBLE);
				tv_tishi.setVisibility(View.GONE);

			} catch (Exception e) {
				// TODO: handle exception
			}

			break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		// arg0---->1////113////Intent { (has extras) }
		Log.v("aaa", "arg0---->" + ENTER_PAY + "////" + MyPayActivity.PAY_OK + "////" + arg2);
		if (arg0 == ENTER_PAY && arg1 == MyPayActivity.PAY_OK) {
			if (arg2 != null) {

				String data = arg2.getStringExtra(MyPayActivity.DATA);
				if (type.equals("0") && data.equals(MyPayActivity.PAY_SUCCESS))
					Utils.zhifuchenggongTishi(OrderConfirmationActivity.this, 0, dialog, handler, 1);
				else if (type.equals("1") && data.equals(MyPayActivity.PAY_SUCCESS)) {
					Utils.zhifuchenggongTishi(OrderConfirmationActivity.this, 1, dialog, handler, 1);
				} else if (data.equals(MyPayActivity.PAY_CANCEL)) {
					Utils.showMessage(OrderConfirmationActivity.this, "支付已取消");
				} else {
					Utils.showMessage(OrderConfirmationActivity.this, "支付失败,错误码:" + data);
				}
			}
		}
		// if (type.equals("0"))
		// xichezhifu();
		// else if (type.equals("1"))
		// baoyangzhifu();

	}

}
