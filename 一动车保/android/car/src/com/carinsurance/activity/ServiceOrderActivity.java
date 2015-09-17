package com.carinsurance.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.infos.Content;
import com.carinsurance.infos.CouponItemModel;
import com.carinsurance.infos.DanShangpingModel;
import com.carinsurance.infos.MyCarInfosModel;
import com.carinsurance.infos.PriceModel;
import com.carinsurance.infos.ProductDefaultItemModel;
import com.carinsurance.infos.UpDingDan;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.RepeatClick;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

//服务订单 liqi
public class ServiceOrderActivity extends BaseActivity {
	private final int REQUEST_TO_CARMANAGER_FOR_WASHCAR = 100; // 洗车订单页面请求Carmanager
	@ViewInject(R.id.return_btn)
	ImageView return_btn;
	@ViewInject(R.id.xu_line0)
	LinearLayout xu_line0;
	@ViewInject(R.id.xu_line1)
	LinearLayout xu_line1;
	@ViewInject(R.id.xu_line2)
	LinearLayout xu_line2;
	@ViewInject(R.id.xu_line3)
	LinearLayout xu_line3;
	@ViewInject(R.id.xu_line4)
	LinearLayout xu_line4;
	@ViewInject(R.id.xu_line5)
	LinearLayout xu_line5;
	@ViewInject(R.id.xu_line6)
	LinearLayout xu_line6;
	@ViewInject(R.id.xu_line7)
	LinearLayout xu_line7;
	@ViewInject(R.id.setting_yuyueshijian)
	LinearLayout setting_yuyueshijian;
	@ViewInject(R.id.get_diqu)
	LinearLayout get_diqu;
	@ViewInject(R.id.youhuiquan)
	LinearLayout youhuiquan;
	@ViewInject(R.id.tijiao)
	FrameLayout tijiao;// 提交
	// @ViewInject(R.id.address)
	// TextView address;
	@ViewInject(R.id.lianxiren_phone)
	EditText lianxiren_phone;
	@ViewInject(R.id.lianxiren_name)
	EditText lianxiren_name;

	@ViewInject(R.id.yuyuetime)
	TextView yuyuetime;

	@ViewInject(R.id.tv_shipeichexing)
	TextView tv_shipeichexing;
	@ViewInject(R.id.btn_tv_shipeichexing)
	FrameLayout btn_tv_shipeichexing;
	SortModel sortModel;
	UpDingDan updingdan;// 封装需要上传的数据

	@ViewInject(R.id.fuwuimg)
	ImageView fuwuimg;

	boolean is_mendian = false;

	@ViewInject(R.id.tv_car_card)
	TextView tv_car_card;
	@ViewInject(R.id.tv_color)
	TextView tv_color;
	@ViewInject(R.id.address)
	TextView address;

	@ViewInject(R.id.r0)
	RadioButton r0;
	@ViewInject(R.id.r1)
	RadioButton r1;
	@ViewInject(R.id.r2)
	RadioButton r2;
	@ViewInject(R.id.r3)
	RadioButton r3;
	@ViewInject(R.id.r4)
	RadioButton r4;
	@ViewInject(R.id.r5)
	RadioButton r5;
	@ViewInject(R.id.r6)
	RadioButton r6;
	String type = "0";// 0默认是上门洗车，这里就不用Content.enter_ChaiceCarFlags了 1
						// 1是保养流程
						// 2是从特色市场----商品详情点击购买进入的
						// 3是单商品订单详情页面
	// 在这个界面只用区分上门服务和 门店服务

	// 下面的是上门保养需要用的数据
	@ViewInject(R.id.xc_0)
	LinearLayout xc_0;
	@ViewInject(R.id.xc_1)
	LinearLayout xc_1;
	@ViewInject(R.id.xc_2)
	LinearLayout xc_2;
	@ViewInject(R.id.by_00)
	LinearLayout by_00;
	@ViewInject(R.id.by_01)
	LinearLayout by_01;

	@ViewInject(R.id.iv_0)
	ImageView iv_0;
	@ViewInject(R.id.iv_1)
	ImageView iv_1;
	@ViewInject(R.id.iv_2)
	ImageView iv_2;

	@ViewInject(R.id.gongduoshaojian)
	TextView gongduoshaojian;
	@ViewInject(R.id.by_0)
	LinearLayout by_0;
	// UpDingDan_baoyang upDingDan_baoyang;

	@ViewInject(R.id.yuyue_time)
	TextView yuyue_time;

	@ViewInject(R.id.scprice)
	TextView scprice;
	@ViewInject(R.id.scremark2)
	TextView scremark2;
	@ViewInject(R.id.scremark1)
	TextView scremark1;
	@ViewInject(R.id.by_1)
	LinearLayout by_1;
	@ViewInject(R.id.by_beizhu)
	LinearLayout by_beizhu;

	@ViewInject(R.id.tv_fuwufeiyong)
	TextView tv_fuwufeiyong;
	@ViewInject(R.id.tv_shangpingjiage)
	TextView tv_shangpingjiage;
	@ViewInject(R.id.tv_youhuijuan)
	TextView tv_youhuijuan;
	@ViewInject(R.id.tv_shifukuan)
	TextView tv_shifukuan;
	@ViewInject(R.id.tv_beizhu)
	TextView tv_beizhu;
	// 2是从特色市场----商品详情点击购买进入的
	ProductDefaultItemModel teshe_shangping;
	@ViewInject(R.id.ts)
	TextView ts;

	@ViewInject(R.id.lyc_0)
	LinearLayout lyc_0;
	@ViewInject(R.id.lyc_1)
	LinearLayout lyc_1;
	@ViewInject(R.id.lyc_2)
	LinearLayout lyc_2;
	@ViewInject(R.id.lyc_3)
	LinearLayout lyc_3;
	@ViewInject(R.id.get_diqu2)
	LinearLayout get_diqu2;
	@ViewInject(R.id.address2)
	TextView address2;
	com.carinsurance.utils.Dialog dialog;

	// 优惠卷要用 #隔开
	String pids = "";
	// 服务的Id #隔开
	String scids = "";
	// 获取价格要用
	String pids_num = "";

	String fenge = "_";

	@ViewInject(R.id.scrollView1)
	private ScrollView sv;

	String oid;
	DanShangpingModel danShangpingModel;

	@ViewInject(R.id.see_xc01)
	LinearLayout see_xc01;
	@ViewInject(R.id.tv_ostatus)
	TextView tv_ostatus;

	@ViewInject(R.id.smxc)
	TextView smxc;
	@ViewInject(R.id.ll_zhifu1)
	LinearLayout ll_zhifu1;
	@ViewInject(R.id.tv_payWay)
	TextView tv_payWay;
	@ViewInject(R.id.is_can_user)
	TextView is_can_user;

	@ViewInject(R.id.right_img)
	ImageView right_img;

	@ViewInject(R.id.ll_zhifufangshi)
	LinearLayout ll_zhifufangshi;

	@ViewInject(R.id.fuwujine)
	TextView fuwujine;

	@ViewInject(R.id.ll_shifukuan)
	LinearLayout ll_shifukuan;
	//

	@ViewInject(R.id.ll_bottom_shifukuan)
	LinearLayout ll_bottom_shifukuan;
	@ViewInject(R.id.tv_shifukuan2)
	TextView tv_shifukuan2;

	@ViewInject(R.id.select_address)
	TextView select_address;

	@ViewInject(R.id.iv_youjiantou)
	ImageView iv_youjiantou;

	// 是否在计算了价格之后还要看是否有可使用的优惠券
	boolean is_see_youhuijuan = true;

	public static int ENTER_PAY = 1;// 进入支付页面

	@ViewInject(R.id.youjiantou__x)
	ImageView youjiantou__x;

	// @ViewInject(R.id.fuwuimg)
	// ImageView fuwuimg;
	private void initDanShangpingOrder() {
		// ll_shifukuan.setVisibility(View.VISIBLE);
		// ll_bottom_shifukuan.setVisibility(View.GONE);
		// lyc_0.setVisibility(View.GONE);
		// see_xc01.setVisibility(View.VISIBLE);
		// btn_tv_shipeichexing.setVisibility(View.GONE);
		// ts.setVisibility(View.VISIBLE);
		// get_diqu2.setVisibility(View.VISIBLE);
		// lyc_1.setVisibility(View.GONE);
		// lyc_2.setVisibility(View.GONE);
		// get_diqu.setVisibility(View.GONE);
		// ll_zhifu1.setVisibility(View.VISIBLE);
		// right_img.setVisibility(View.GONE);
		// by_beizhu.setVisibility(View.GONE);
		// xc_2.setVisibility(View.GONE);
		// ll_zhifufangshi.setVisibility(View.GONE);
		// by_1.setVisibility(View.GONE);
		fuwujine.setText("服务金额");
		tv_fuwufeiyong.setText("" + danShangpingModel.getOrimoney());
		tv_youhuijuan.setText("￥" + danShangpingModel.getCpmoney());

		tv_shifukuan2.setText("￥" + danShangpingModel.getPaymoney());

		tv_ostatus.setText(danShangpingModel.getOstatus());

		smxc.setText("订单编号:" + danShangpingModel.getOnum());

		new xUtilsImageLoader(this).display(fuwuimg, danShangpingModel.getOpimgpath());
		scremark1.setText(danShangpingModel.getOpname());

		scprice.setText("￥" + danShangpingModel.getOprice());

		ts.setText("x" + danShangpingModel.getOpnum());

		address2.setText(danShangpingModel.getAddress());

		lianxiren_name.setText(danShangpingModel.getOcontact());
		lianxiren_phone.setText(danShangpingModel.getOphone());

		if ((danShangpingModel.getPayway()).equals("1")) {
			tv_payWay.setText("会员卡支付");
		} else if ((danShangpingModel.getPayway()).equals("2")) {
			tv_payWay.setText("支付宝");
		} else if ((danShangpingModel.getPayway()).equals("3")) {
			tv_payWay.setText("银联");
		} else if ((danShangpingModel.getPayway()).equals("4")) {
			tv_payWay.setText("微信");
		} else if ((danShangpingModel.getPayway()).equals("5")) {
			tv_payWay.setText("现场pos机刷卡");
		}
		is_can_user.setText(danShangpingModel.getUsecoup());

		// tv_payWay.setText(danShangpingModel.);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			sv.post(new Runnable() {

				@Override
				public void run() {
					sv.scrollTo(0, 0);
				}
			});
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_order);
		ViewUtils.inject(this);
		type = JumpUtils.getString(ServiceOrderActivity.this, "type");
		if (StringUtil.isNullOrEmpty(type)) {
			type = "0";
		}
		if (type.equals("3")) {
			oid = JumpUtils.getString(ServiceOrderActivity.this, "oid");
		}

		try {
			teshe_shangping = (ProductDefaultItemModel) JumpUtils.getSerializable(ServiceOrderActivity.this);
			// 说明type为2；
			// sortModel=new SortModel();
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			sortModel = (SortModel) JumpUtils.getSerializable(ServiceOrderActivity.this);
			// Log.v("aaa", "进入到这个介么中的" + sortModel.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

		init();
	}

	/**
	 * 获取用户是否有能够使用的优惠卷
	 * 
	 * @param pid
	 * @param money
	 */
	private void getCanUserCon(String pid) {

		// 商品价格
		String shangpingjiage = tv_shangpingjiage.getText().toString().trim();
		// 服务费用
		String fuwufeiyong = tv_fuwufeiyong.getText().toString().trim();
		// 商品价格
		String a = shangpingjiage.substring(1, shangpingjiage.length());
		// 服务费用
		String b = fuwufeiyong.substring(1, fuwufeiyong.length());

		Double money = Double.parseDouble(a) + Double.parseDouble(b);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(ServiceOrderActivity.this));
		map.put("token", Utils.getToken(ServiceOrderActivity.this));

		if (type.equals("2")) {
			pids = pid;
		}

		if (!StringUtil.isNullOrEmpty(pids)) {
			map.put("pid", pids);
		}
		map.put("money", "" + money);
		// 0默认是上门洗车，这里就不用Content.enter_ChaiceCarFlags了 1
		// 1是保养流程
		// 2是从特色市场----商品详情点击购买进入的
		// 3是单商品订单详情页面
		if (type.equals("0")) {// 0默认是上门洗车，这里就不用Content.
			map.put("otype", "1");
		} else if (type.equals("1")) { // 1是保养流程
			map.put("otype", "2");
		} else if (type.equals("2")) {// 2
			map.put("otype", "3");
		}
		NetUtils.getIns().post(Task.GET_CAN_USER_COUPON, map, handler);
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

	}

	private void init() {
		updingdan = new UpDingDan();
		// upDingDan_baoyang=new UpDingDan_baoyang();
		// return_btn.setOnClickListener(this);
		xu_line0.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		xu_line1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		xu_line2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		xu_line3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		xu_line4.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		xu_line5.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		xu_line6.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		xu_line7.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		initJieMian();

	}

	private void initJieMian() {
		// TODO Auto-generated method stub
		// 1是保养流程
		if (type.equals("1")) {
			youjiantou__x.setVisibility(View.GONE);
			initBaoyang();

		} else if (type.equals("0"))// 如果是洗车
		{

			r6.setVisibility(View.GONE);
			/**
			 * pid 商品ID，多个以#分隔 scid 服务ID，多个以#分隔 cpid 优惠券ID otype
			 * 订单类型1=洗车订单；2=保养订单3=商品订单
			 */
			getOrder_Price("", sortModel.getSeriverTypeitemModel0().getScid(), "", "1");
			by_1.setVisibility(View.GONE);
			by_beizhu.setVisibility(View.GONE);
			scprice.setText("￥" + sortModel.getSeriverTypeitemModel0().getScprice());
			scremark1.setText("" + sortModel.getSeriverTypeitemModel0().getScremark1());
			scremark2.setText("" + sortModel.getSeriverTypeitemModel0().getScremark2());
			// tv_fuwufeiyong.setText("￥" +
			// sortModel.getSeriverTypeitemModel0().getScprice());
			// tv_shifukuan.setText("￥" +
			// sortModel.getSeriverTypeitemModel0().getScprice());
			// getCanUserCon(null);
		} else if (type.equals("2")) {// 2是从特色市场----商品详情点击购买进入的
			r6.setVisibility(View.GONE);
			/**
			 * pid 商品ID，多个以#分隔 scid 服务ID，多个以#分隔 cpid 优惠券ID otype
			 * 订单类型1=洗车订单；2=保养订单3=商品订单
			 */
			if (StringUtil.isNullOrEmpty(teshe_shangping.getNumber())) {
				teshe_shangping.setNumber("1");
			}
			Log.d("aaa", "teshe===>" + teshe_shangping.toString());

			getOrder_Price(teshe_shangping.getPid() + fenge + teshe_shangping.getNumber(), teshe_shangping.getScid(), "", "3");
			xc_0.setVisibility(View.GONE);
			ts.setVisibility(View.VISIBLE);
			lyc_0.setVisibility(View.GONE);
			lyc_1.setVisibility(View.GONE);
			xu_line3.setVisibility(View.GONE);
			lyc_2.setVisibility(View.GONE);
			xu_line4.setVisibility(View.GONE);
			get_diqu.setVisibility(View.GONE);
			xu_line0.setVisibility(View.GONE);
			by_beizhu.setVisibility(View.GONE);
			xc_2.setVisibility(View.GONE);
			lyc_3.setVisibility(View.GONE);
			get_diqu2.setVisibility(View.VISIBLE);
			ts.setText("x" + teshe_shangping.getNumber());
			new xUtilsImageLoader(this).display(fuwuimg, teshe_shangping.getPimage()); // teshe_shangping
			scremark1.setText("" + teshe_shangping.getPname());
			scprice.setText("￥" + teshe_shangping.getPrice());
			btn_tv_shipeichexing.setVisibility(View.GONE);
			// getCanUserCon(teshe_shangping.getPid());
		} else if (type.equals("3")) {// 3是查看单商品订单详情
			// see_xc01.setVisibility(View.VISIBLE);
			// tv_ostatus.setText()
			select_address.setText("送货地址(当前只支持贵阳市地区)");
			iv_youjiantou.setVisibility(View.GONE);
			lianxiren_name.setFocusable(false);
			lianxiren_name.setEnabled(false);

			lianxiren_phone.setFocusable(false);
			lianxiren_phone.setEnabled(false);

			ll_shifukuan.setVisibility(View.VISIBLE);
			ll_bottom_shifukuan.setVisibility(View.GONE);
			lyc_0.setVisibility(View.GONE);
			see_xc01.setVisibility(View.VISIBLE);
			btn_tv_shipeichexing.setVisibility(View.GONE);
			ts.setVisibility(View.VISIBLE);
			get_diqu2.setVisibility(View.VISIBLE);
			lyc_1.setVisibility(View.GONE);
			lyc_2.setVisibility(View.GONE);
			get_diqu.setVisibility(View.GONE);
			ll_zhifu1.setVisibility(View.VISIBLE);
			right_img.setVisibility(View.GONE);
			by_beizhu.setVisibility(View.GONE);
			xc_2.setVisibility(View.GONE);
			ll_zhifufangshi.setVisibility(View.GONE);
			by_1.setVisibility(View.GONE);
			getDanShangpingOrder();
		}
		if (sortModel != null) {
			String nsr = sortModel.getCm_name();
			if (!StringUtil.isNullOrEmpty(nsr)) {
				tv_shipeichexing.setText(getString(R.string.adpater_chexing, sortModel.getCs_name()));// +sortModel.getCm_name()
			} else {
				tv_shipeichexing.setText(getString(R.string.adpater_chexing, "无"));// +sortModel.getCm_name()
			}

			if (sortModel.getSeriverTypeitemModel0() != null) {
				new xUtilsImageLoader(ServiceOrderActivity.this).display(fuwuimg, sortModel.getSeriverTypeitemModel0().getScimage());
			}
		}

		lianxiren_name.setText("" + Utils.getUserName(ServiceOrderActivity.this));
		lianxiren_phone.setText("" + Utils.getPhoneNumber(ServiceOrderActivity.this));
		if (sortModel != null) {
			tv_car_card.setText("" + sortModel.getPlateNumber());
			tv_color.setText("" + sortModel.getColor());
		}

		// if(type.equals("0"))
		// getCanUserCon(null);
		// else
		// if(type.equals("1"))
		// {
		//
		// }
	}

	/**
	 * 获取订单价格
	 * 
	 * @param pid
	 *            商品ID，多个以#分隔
	 * @param scid
	 *            服务ID，多个以#分隔
	 * @param cpid
	 *            优惠券ID
	 * @param otype
	 *            订单类型1=洗车订单；2=保养订单3=商品订单
	 */
	private void getOrder_Price(String pid, String scid, String cpid, String otype) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		map.put("pid", pid);
		map.put("scid", scid);
		map.put("cpid", cpid);
		map.put("otype", otype);
		NetUtils.getIns().post(Task.ORDER_PRICE_CALC, map, handler);
	}

	private void getDanShangpingOrder() {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		map.put("oid", oid);
		NetUtils.getIns().post(Task.GET_SHANGPING_ORDER_XIANGQING, map, handler);
	}

	/**
	 * 如果type=”1“
	 */
	private void initBaoyang() {
		// TODO Auto-generated method stub

		by_beizhu.setVisibility(View.VISIBLE);
		xc_0.setVisibility(View.GONE);
		xc_1.setVisibility(View.GONE);
		xc_2.setVisibility(View.GONE);
		// 如果是自备配件
		if (sortModel.getIs_zibeipeijian().equals("1")) {
			wushangping();
			// sdsd
			getOrder_Price("", scids, "", "2");
		} else {// 有商品的时候
			youshangping();
			Log.d("ccc", "pids=====>" + pids);
			getOrder_Price(pids_num, scids, "", "2");
		}
		by_0.setVisibility(View.VISIBLE);

		by_0.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
//				JumpUtils.jumpActivityForResult(ServiceOrderActivity.this, SelectSETimeActivity.class, null, 5);
				JumpUtils.jumpActivityForResult(ServiceOrderActivity.this, ChooseDataActivity.class, null, 5);
			}
		});
	}

	/**
	 * 如果没有商品
	 */
	private void wushangping() {
		// TODO Auto-generated method stub
		by_1.setVisibility(View.GONE);
		by_00.setVisibility(View.GONE);
		by_01.setVisibility(View.VISIBLE);

		by_01.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub】
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "1");

				JumpUtils.jumpto(ServiceOrderActivity.this, SelectServer1Activity.class, sortModel, map);
			}
		});
		// 没有商品
		// 1需要服务费用
		double fuwu_fei = 0;
		scids = "";
		for (int i = 0; i < sortModel.getSelectSeriverTypeitemList().size(); i++) {
			fuwu_fei += Double.parseDouble(sortModel.getSelectSeriverTypeitemList().get(i).getScprice());
			scids += sortModel.getSelectSeriverTypeitemList().get(i).getScid() + "#";
			for (int j = 0; j < sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().size(); j++) {

				if (sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list() != null) {
					if (!sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().isEmpty()) {

					}
				}

			}
		}
		tv_fuwufeiyong.setText("￥" + fuwu_fei);
		if (!StringUtil.isNullOrEmpty(scids)) {
			// try {
			String cc = scids.substring(scids.length() - 1, scids.length());
			if (cc.equals("#")) {
				scids = scids.substring(0, scids.length() - 1);
			}
		}
		// fuwu_fei
	}

	/**
	 * 如果有商品
	 */
	private void youshangping() {
		// TODO Auto-generated method stub
		by_00.setVisibility(View.VISIBLE);

		by_1.setVisibility(View.VISIBLE);
		by_01.setVisibility(View.GONE);
		int cc = 0;// 只显示三张图
		int size = 0;// 共多少件商品
		double shangpingfeiyong = 0;// 商品费用
		// 1需要服务费用
		double fuwu_fei = 0;
		pids = "";
		scids = "";
		pids_num = "";
		for (int i = 0; i < sortModel.getSelectSeriverTypeitemList().size(); i++) {
			scids += sortModel.getSelectSeriverTypeitemList().get(i).getScid() + "#";
			fuwu_fei += Double.parseDouble(sortModel.getSelectSeriverTypeitemList().get(i).getScprice());

			for (int j = 0; j < sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().size(); j++) {
				// gongduoshaojian.setText();

				if (sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list() != null) {
					if (!sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().isEmpty()) {
						// sd #
						pids += sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getPid() + "#";
						if (StringUtil.isNullOrEmpty(sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getNumber())) {
							sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).setNumber("1");
						}
						pids_num += sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getPid() + "_" + sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getNumber() + "#";
						size++;
						String num = sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getNumber();
						Double number = 1.0;
						if (!StringUtil.isNullOrEmpty(num)) {
							Log.v("aaa", "num=====305==>" + num);
							number = Double.parseDouble(num);
						}
						shangpingfeiyong += (number * Double.parseDouble(sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getPrice()));

						ProductDefaultItemModel p = sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j);
						if (cc == 2) {
							cc++;
							iv_2.setVisibility(View.VISIBLE);
							new xUtilsImageLoader(ServiceOrderActivity.this).display(iv_2, p.getPimage());
						} else if (cc == 1) {
							cc++;
							iv_1.setVisibility(View.VISIBLE);
							new xUtilsImageLoader(ServiceOrderActivity.this).display(iv_1, p.getPimage());
						} else if (cc == 0) {
							cc++;
							iv_0.setVisibility(View.VISIBLE);
							new xUtilsImageLoader(ServiceOrderActivity.this).display(iv_0, p.getPimage());
						}
					}
				}
			}

		}
		if (!StringUtil.isNullOrEmpty(scids)) {
			// try {
			String dd = scids.substring(scids.length() - 1, scids.length());
			if (dd.equals("#")) {
				scids = scids.substring(0, scids.length() - 1);
			}
		}
		if (!StringUtil.isNullOrEmpty(pids)) {
			// try {
			String dd = pids.substring(pids.length() - 1, pids.length());
			if (dd.equals("#")) {
				pids = pids.substring(0, pids.length() - 1);
			}
		}
		if (!StringUtil.isNullOrEmpty(pids_num)) {
			// try {
			String dd = pids_num.substring(pids_num.length() - 1, pids_num.length());
			if (dd.equals("#")) {
				pids_num = pids_num.substring(0, pids_num.length() - 1);
			}
		}
		// tv_shangpingjiage.setText("￥" + shangpingfeiyong);
		// tv_fuwufeiyong.setText("￥" + fuwu_fei);
		gongduoshaojian.setText("共" + size + "件");
		by_00.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "0");

				JumpUtils.jumpto(ServiceOrderActivity.this, SelectServer1Activity.class, sortModel, map);
			}
		});
	}

	@OnClick({ R.id.return_btn, R.id.btn_tv_shipeichexing, R.id.setting_yuyueshijian, R.id.get_diqu, R.id.tijiao, R.id.get_diqu2 })
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.return_btn:
			JumpUtils.jumpfinish(ServiceOrderActivity.this);
			break;
		case R.id.btn_tv_shipeichexing:
			// btn_tv_shipeichexing
			// JumpUtils.jumpto(ServiceOrderActivity.this,
			// ChoiceCarActivity.class, null, true);
			// HashMap<String, String> map1 = new HashMap<String, String>();
			// map1.put("fuwutypeid", updingdan.getScid());
			// Content.enter_ch = 2;
			// Content.sortModel = null;
			// JumpUtils.jumpActivityForResult(ServiceOrderActivity.this,
			// CarManageActivity.class, map1, 3);
			// 若是洗车类型订单,可以重选车型
			if (type.equals("0")) { // 洗车
				JumpUtils.jumpActivityForResult(this, CarManageActivity.class, null, REQUEST_TO_CARMANAGER_FOR_WASHCAR);
			} else if (type.equals("1")) {// 保养

			} else if (type.equals("2")) {// 商城

			}

			break;
		// case R.id.setting_yuyueshijian:
		// HashMap<String, String> map = new HashMap<String, String>();
		// if (StringUtil.isNullOrEmpty(updingdan.getLat()) ||
		// StringUtil.isNullOrEmpty(updingdan.getAddress()) ||
		// StringUtil.isNullOrEmpty(updingdan.getLng())) {
		// Utils.showMessage(ServiceOrderActivity.this, "请先选择地址！");
		// return;
		// }
		// if
		// (StringUtil.isNullOrEmpty(sortModel.getSeriverTypeitemModel0().getScid()))
		// {
		// Utils.showMessage(ServiceOrderActivity.this, "服务类型id不能为空!");
		// return;
		// }
		// map.put("sid", updingdan.getSid());
		// map.put("fuwutypeid",
		// sortModel.getSeriverTypeitemModel0().getScid());
		// Content.startTime = null;
		// Content.endTime = null;
		// JumpUtils.jumpActivityForResult(ServiceOrderActivity.this,
		// CalendarActivity.class, map, 1, false);
		// break;
		case R.id.get_diqu:// 地图上的
			// JumpUtils.jumpActivityForResult(ServiceOrderActivity.this,
			// ChoiceDiquActivity.class, null, 2, false);
			if (type.equals("3")) {

			} else
				JumpUtils.jumpActivityForResult(ServiceOrderActivity.this, LocationActivity.class, null, 2, false);
			break;
		case R.id.get_diqu2:
			if (type.equals("3")) {

			} else
				JumpUtils.jumpActivityForResult(ServiceOrderActivity.this, ChoiceAreaAcitivity.class, null, 8, false);
			// JumpUtils.jumpActivityForResult(ServiceOrderActivity.this,
			// LocationActivity.class, null, 2, false);
			break;

		case R.id.tijiao:
			RepeatClick.setRepeatClick(v);
			// 读得多

			if (lianxiren_phone.getText().toString().trim().length() != 11) {
				Utils.showMessage(ServiceOrderActivity.this, "手机号格式不正确！");
				return;
			}
			Utils.setUserName(ServiceOrderActivity.this, lianxiren_name.getText().toString().trim());
			Utils.setPhoneNumber(ServiceOrderActivity.this, lianxiren_phone.getText().toString().trim());

			// JumpUtils.jumpto(ServiceOrderActivity.this,
			// OrderConfirmationActivity.class, null);
			if (type.equals("0"))
				tijiao();
			else if (type.equals("1")) {
				tijiao_baoyang();
			} else if (type.equals("2")) {
				tijiao_only_shangping();
			}
			break;

		}
	}

	/**
 * 
 */
	private void tijiao_only_shangping() {
		// TODO Auto-generated method stub
		updingdan.setPid(teshe_shangping.getPid());
		updingdan.setPnumber(teshe_shangping.getNumber());

		updingdan.setPhone(lianxiren_phone.getText().toString().trim());
		updingdan.setContact(lianxiren_name.getText().toString().trim());

		if (r6.isChecked() == true) {
			// 现场pos机刷卡
			updingdan.setPayWay("5");
		} else if (r2.isChecked() == true) {
			// 微信
			updingdan.setPayWay("4");
		} else if (r3.isChecked() == true) {
			// 银联
			updingdan.setPayWay("3");
		} else if (r4.isChecked() == true) {
			// 支付宝
			updingdan.setPayWay("2");
		} else if (r5.isChecked() == true) {
			// 会员卡
			updingdan.setPayWay("1");
		}

		if (StringUtil.isNullOrEmpty(updingdan.getContact()) || StringUtil.isNullOrEmpty(updingdan.getPhone())) {
			Utils.showMessage(ServiceOrderActivity.this, "请将联系人信息填写完整！");
			return;
		}

		if (StringUtil.isNullOrEmpty(updingdan.getAddress()) || StringUtil.isNullOrEmpty(updingdan.getAid())) {
			Utils.showMessage(ServiceOrderActivity.this, "请选择地址！");
			return;
		}
		tesheshangpingzhifu();
	}

	/**
	 * 购买特色商品等等。。。支付
	 */
	private void tesheshangpingzhifu() {
		// TODO Auto-generated method stub
		Utils.showPrgress_Noclose(ServiceOrderActivity.this);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		map.put("pid", updingdan.getPid());
		map.put("pnumber", updingdan.getPnumber());
		map.put("phone", updingdan.getPhone());

		map.put("contact", updingdan.getContact());

		map.put("aid", updingdan.getAid());

		map.put("address", updingdan.getAddress());
		if (!StringUtil.isNullOrEmpty(updingdan.getCpid()))
			map.put("cpid", updingdan.getCpid());

		map.put("payWay", updingdan.getPayWay());

		// map.put("scid",
		// upDingDan.getSortModel().getSeriverTypeitemModel0().getScid());
		// map.put("cleanType", upDingDan.getCleanType());
		NetUtils.getIns().post(Task.TIIJAO_PRODUCT_ORDER, map, handler);
	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);
		switch (task) {
		case Task.TIIJAO_PRODUCT_ORDER:
			Utils.ExitPrgress_Noclose(ServiceOrderActivity.this);
			break;
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		switch (task) {
		case Task.TIIJAO_PRODUCT_ORDER:

			try {

				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				if (result.equals("001")) {

					String oid = js.getString("oid");
					String onum = js.getString("onum");
					String payAction = js.getString("payAction");

					if (payAction.equals("2"))
						Utils.zhifuchenggongTishi(ServiceOrderActivity.this, 1, dialog, handler, 1);
					else if (payAction.equals("3")) {
						Utils.showMessage(ServiceOrderActivity.this, "余额不足！");
					} else if (payAction.equals("1")) {
						pay(onum);
					} else {
						Utils.showMessage(ServiceOrderActivity.this, "支付失败,错误码：" + result + payAction);
					}

					// if (updingdan.getPayWay().equals("1")) {
					// // tv_payWay.setText("货到付款");
					// Utils.zhifuchenggongTishi(ServiceOrderActivity.this, 1,
					// dialog, handler, 1);
					// } else if (updingdan.getPayWay().equals("2")) {
					// // tv_payWay.setText("支付宝");
					// HashMap<String, String> map = new HashMap<String,
					// String>();
					// map.put("dingdanhao", oid);
					// map.put("body", "商品订单");
					// String price = tv_shifukuan.getText().toString().trim();
					// String pprice = price.replaceAll("￥", "");
					// map.put("price", pprice);
					// map.put("infos", "商品订单");
					// map.put("huidiaoNet", "");
					// //
					// JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this,
					// // MyPayActivity.class, map);
					// JumpUtils.jumpActivityForResult(ServiceOrderActivity.this,
					// MyPayActivity.class, map, ENTER_PAY);
					//
					// } else if (updingdan.getPayWay().equals("3")) {
					// // tv_payWay.setText("银联");
					// } else if (updingdan.getPayWay().equals("4")) {
					// // tv_payWay.setText("微信");
					// }

				} else {
					Utils.showMessage(ServiceOrderActivity.this, "订单提交失败,错误码:" + result);
				}
				Utils.ExitPrgress_Noclose(ServiceOrderActivity.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Task.GET_CAN_USER_COUPON:
			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				String usable = js.getString("usable");

				if (result.equals("001")) {

					if (usable.equals("1")) {
						is_can_user.setText("可用");

						youhuiquan.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// JumpUtils.jumpto(ServiceOrderActivity.this,
								// See, map);
								HashMap<String, String> map = new HashMap<String, String>();

								String a = tv_fuwufeiyong.getText().toString().substring(1, tv_fuwufeiyong.getText().toString().trim().length());

								String b = tv_shangpingjiage.getText().toString().substring(1, tv_shangpingjiage.getText().toString().trim().length());

								map.put("money", "" + ((Double.parseDouble(a)) + Double.parseDouble(b)));
								map.put("pid", pids);

								// 0默认是上门洗车，这里就不用Content.enter_ChaiceCarFlags了 1
								// 1是保养流程
								// 2是从特色市场----商品详情点击购买进入的
								// 3是单商品订单详情页面

								if (type.equals("0")) {
									map.put("otype", "1");
								} else if (type.equals("1")) {
									map.put("otype", "2");
								} else if (type.equals("2")) {
									map.put("otype", "3");
								}
								Log.v("aaa", "我点的这927");
								JumpUtils.jumpActivityForResult(ServiceOrderActivity.this, ChooseACouponActivity.class, map, 88);
							}
						});
					}
				} else {

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Task.GET_SHANGPING_ORDER_XIANGQING:

			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				// String usable = js.getString("usable");

				if (result.equals("001")) {
					danShangpingModel = JsonUtil.getEntityByJsonString(message, DanShangpingModel.class);

					initDanShangpingOrder();

				} else {
					Utils.showMessage(ServiceOrderActivity.this, "未知异常：错误码:" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Task.ORDER_PRICE_CALC:
			try {
				JSONObject js = new JSONObject(message);
				String result = js.getString("result");
				// String usable = js.getString("usable");

				if (result.equals("001")) {
					PriceModel p = JsonUtil.getEntityByJsonString(message, PriceModel.class);

					// danShangpingModel =
					// JsonUtil.getEntityByJsonString(message,
					// DanShangpingModel.class);
					tv_fuwufeiyong.setText("￥" + p.getOcmoney());
					tv_shangpingjiage.setText("￥" + p.getOpmoney());
					tv_youhuijuan.setText("￥" + p.getCpmoney());
					tv_shifukuan.setText("￥" + p.getPaymoney());

					if (is_see_youhuijuan == true) {
						is_see_youhuijuan = false;
						if (type.equals("0"))// 如果是洗车
						{
							getCanUserCon(null);
						}
						if (type.equals("2")) {// 2是从特色市场----商品详情点击购买进入的
							Log.i("aaa", "sssss>>>>" + teshe_shangping.toString());
							getCanUserCon(teshe_shangping.getPid());
						}
						if (type.equals("1"))// 1是保养订单
						{
							if (sortModel.getIs_zibeipeijian().equals("1")) {
								getCanUserCon(null);
							} else {
								getCanUserCon(pids);
							}
						}

					} else {

					}
				} else {
					Utils.showMessage(ServiceOrderActivity.this, "未知异常：错误码:" + result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}
	}

	/**
	 * 调用支付接口
	 */
	private void pay(String onum) {
		// TODO Auto-generated method stub

		if (updingdan.getPayWay().equals("1")) {
			// tv_payWay.setText("会员卡支付");
			// zhifuchenggongTishi(0);支付动作：
			// 1=调用支付接口；
			// 2=支付完成【】；
			// 3=余额不足【会员卡支付】

		} else if (updingdan.getPayWay().equals("2")) {// 这是调用支付宝的
			// tv_payWay.setText("支付宝");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("dingdanhao", onum);
			map.put("body", "洗车订单");
			String price = tv_shifukuan.getText().toString().trim();
			String pprice = price.replaceAll("￥", "");
			map.put("price", pprice);
			map.put("infos", "洗车订单");
			map.put("huidiaoNet", Task.ALIPAY_RETURN_URL);
			// JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this,
			// MyPayActivity.class, map);
			JumpUtils.jumpActivityForResult(ServiceOrderActivity.this, MyPayActivity.class, map, ENTER_PAY);
		} else if (updingdan.getPayWay().equals("3")) {
			// tv_payWay.setText("银联");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "2");
			map.put("dingdanhao", onum);
			// JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this,
			// MyPayActivity.class, map);
			JumpUtils.jumpActivityForResult(ServiceOrderActivity.this, MyPayActivity.class, map, ENTER_PAY);
		} else if (updingdan.getPayWay().equals("4")) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "1");
			map.put("dingdanhao", onum);
			// JumpUtils.jumpActivityForResult(OrderConfirmationActivity.this,
			// MyPayActivity.class, map);
			JumpUtils.jumpActivityForResult(ServiceOrderActivity.this, MyPayActivity.class, map, ENTER_PAY);
			// tv_payWay.setText("微信");
		} else if (updingdan.getPayWay().equals("5")) {
			// tv_payWay.setText("现场pos机刷卡");
		}
	}

	private void tijiao_baoyang() {
		// TODO Auto-generated method stub
		updingdan.setUcid(sortModel.getUcid());
		updingdan.setScid(sortModel.getSeriverTypeitemModel0().getScid());

		// updingdan.setStime("?");
		updingdan.setPhone(lianxiren_phone.getText().toString().trim());
		updingdan.setContact(lianxiren_name.getText().toString().trim());
		updingdan.setPalateNumber(tv_car_card.getText().toString().trim());
		updingdan.setColor(tv_color.getText().toString().trim());
		updingdan.setRemark(tv_beizhu.getText().toString().trim());
		// JSONObject js = new JSONObject();

		JSONObject js1 = new JSONObject();
		JSONObject js2;
		// List<SeriverTypeitemModel>
		// seriver_list=sortModel.getSelectSeriverTypeitemList();
		// List<ProductDefaultItemModel> product_list=sortModel

		String is_zibeipeijian = sortModel.getIs_zibeipeijian();
		for (int i = 0; i < sortModel.getSelectSeriverTypeitemList().size(); i++) {
			js2 = new JSONObject();
			for (int j = 0; j < sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().size(); j++) {

				String number = sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getNumber();

				try {

					if (!is_zibeipeijian.equals("1"))
						if (!StringUtil.isNullOrEmpty(number)) {
							js2.put(sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getPid(), Integer.parseInt(number));
						} else {
							js2.put(sortModel.getSelectSeriverTypeitemList().get(i).getProductDefaultItemModel_list().get(j).getPid(), 1);
						}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			try {
				js1.put(sortModel.getSelectSeriverTypeitemList().get(i).getScid(), js2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.v("aaa", "js1===>" + js1.toString());
		// 微信
		if (r6.isChecked() == true) {
			updingdan.setPayWay("5");
		} else if (r2.isChecked() == true) {
			updingdan.setPayWay("4");
		} else if (r3.isChecked() == true) {
			// 银联
			updingdan.setPayWay("3");
		} else if (r4.isChecked() == true) {
			// 支付宝
			updingdan.setPayWay("2");
		} else if (r5.isChecked() == true) {
			// 会员卡
			updingdan.setPayWay("1");
		}
		if (StringUtil.isNullOrEmpty(updingdan.getUcid())) {
			Utils.showMessage(ServiceOrderActivity.this, "当前没有任何车型！");
			return;
		}
		if (StringUtil.isNullOrEmpty(updingdan.getStime()) || StringUtil.isNullOrEmpty(updingdan.getEtime())) {
			Utils.showMessage(ServiceOrderActivity.this, "请选择预约服务时间！");
			return;
		}
		// if(StringUtil.isNullOrEmpty(updingdan.getScid()))
		// {
		// Utils.showMessage(ServiceOrderActivity.this, "没有服务类型！");
		// return ;
		// }
		if (StringUtil.isNullOrEmpty(updingdan.getContact()) || StringUtil.isNullOrEmpty(updingdan.getPhone())) {
			Utils.showMessage(ServiceOrderActivity.this, "请将联系人信息填写完整！");
			return;
		}

		if (StringUtil.isNullOrEmpty(updingdan.getPalateNumber()) || StringUtil.isNullOrEmpty(updingdan.getColor())) {
			Utils.showMessage(ServiceOrderActivity.this, "车辆信息不全！");
			return;
		}

		if (StringUtil.isNullOrEmpty(updingdan.getLat()) || StringUtil.isNullOrEmpty(updingdan.getLng()) || StringUtil.isNullOrEmpty(updingdan.getAddress())) {
			Utils.showMessage(ServiceOrderActivity.this, "请选择地址！");
			return;
		}
		// HashMap<String, String> map=new HashMap<String, String>();
		// NetUtils.getIns().post(Task.GET_ISHASTECHNICIAN, map, handler);

		updingdan.setHttpData("" + js1.toString());
		updingdan.setSortModel(sortModel);
		String type = "1";
		updingdan.setTv_fuwufeiyong(tv_fuwufeiyong.getText().toString().trim());
		updingdan.setTv_shangpingjiage(tv_shangpingjiage.getText().toString().trim());
		updingdan.setTv_shifukuan(tv_shifukuan.getText().toString().trim());
		updingdan.setTv_youhuijuan(tv_youhuijuan.getText().toString().trim());
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("type", type);
		JumpUtils.jumpto(ServiceOrderActivity.this, OrderConfirmationActivity.class, updingdan, map);

	}

	// 提交洗车订单
	private void tijiao() {
		// TODO Auto-generated method stub

		updingdan.setUcid(sortModel.getUcid());
		updingdan.setScid(sortModel.getSeriverTypeitemModel0().getScid());
		updingdan.setContact(lianxiren_name.getText().toString().trim());

		updingdan.setPhone(lianxiren_phone.getText().toString().trim());
		updingdan.setPalateNumber(tv_car_card.getText().toString().trim());

		updingdan.setColor(tv_color.getText().toString().trim());

		// updingdan httpData

		// sortModel.getSelectSeriverTypeitemList().get(1).getProductDefaultItemModel_list().get(1).getScid();

		// updingdan.cpid
		// clearType
		if (r0.isChecked() == true) {
			updingdan.setCleanType("1");
		} else {
			updingdan.setCleanType("2");
		}
		// 微信
		if (r6.isChecked() == true) {
			updingdan.setPayWay("5");
		} else if (r2.isChecked() == true) {
			updingdan.setPayWay("4");
		} else if (r3.isChecked() == true) {
			// 银联
			updingdan.setPayWay("3");
		} else if (r4.isChecked() == true) {
			// 支付宝
			updingdan.setPayWay("2");
		} else if (r5.isChecked() == true) {
			// 会员卡
			updingdan.setPayWay("1");
		}

		if (StringUtil.isNullOrEmpty(updingdan.getUcid())) {
			Utils.showMessage(ServiceOrderActivity.this, "当前没有任何车型！");
			return;
		}

		if (StringUtil.isNullOrEmpty(updingdan.getContact()) || StringUtil.isNullOrEmpty(updingdan.getPhone())) {
			Utils.showMessage(ServiceOrderActivity.this, "请将联系人信息填写完整！");
			return;
		}

		if (StringUtil.isNullOrEmpty(updingdan.getPalateNumber()) || StringUtil.isNullOrEmpty(updingdan.getColor())) {
			Utils.showMessage(ServiceOrderActivity.this, "车辆信息不全！");
			return;
		}

		if (StringUtil.isNullOrEmpty(updingdan.getLat()) || StringUtil.isNullOrEmpty(updingdan.getLng()) || StringUtil.isNullOrEmpty(updingdan.getAddress())) {
			Utils.showMessage(ServiceOrderActivity.this, "请选择地址！");
			return;
		}
		// HashMap<String, String> map=new HashMap<String, String>();
		// NetUtils.getIns().post(Task.GET_ISHASTECHNICIAN, map, handler);

		updingdan.setTv_fuwufeiyong(tv_fuwufeiyong.getText().toString().trim());
		updingdan.setTv_shangpingjiage(tv_shangpingjiage.getText().toString().trim());
		updingdan.setTv_shifukuan(tv_shifukuan.getText().toString().trim());
		updingdan.setTv_youhuijuan(tv_youhuijuan.getText().toString().trim());
		updingdan.setSortModel(sortModel);
		JumpUtils.jumpto(ServiceOrderActivity.this, OrderConfirmationActivity.class, updingdan);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// Log.d("aaa", "SelectSETimeActivity.Result_ok=" +
		// SelectSETimeActivity.Result_ok);
		Log.v("aaa", "requestCode" + requestCode + "/resultCode=" + resultCode + "/data=" + data);
		// 这里的说单商品支付
		if (requestCode == ENTER_PAY && resultCode == MyPayActivity.PAY_OK) {
			if (data != null) {

				if (data.getStringExtra(MyPayActivity.DATA).equals(MyPayActivity.PAY_SUCCESS)) {
					Utils.zhifuchenggongTishi(ServiceOrderActivity.this, 1, dialog, handler, 1);
				} else if (data.getStringExtra(MyPayActivity.DATA).equals(MyPayActivity.PAY_CANCEL)) {
					Utils.showMessage(ServiceOrderActivity.this, "支付已取消");
				} else {
					Utils.showMessage(ServiceOrderActivity.this, "支付失败:"+data.getStringExtra(MyPayActivity.DATA));//,错误码:" + data
				}
			}
		}

		/**
		 * 获取优惠卷
		 */
		if (requestCode == 88 && resultCode == RESULT_OK) {
			if (data != null) {
				Log.v("aaa", "11111");
				CouponItemModel model = (CouponItemModel) data.getSerializableExtra(JumpUtils.SerializableKey);
				Log.v("aaa", "11222");
				updingdan.setCpid(model.getCpid());
				Log.v("aaa", "12222");
				updingdan.setCouponItemModel(model);
				Log.v("aaa", "12223");
				// 0默认是上门洗车，这里就不用Content.enter_ChaiceCarFlags了 1
				// 1是保养流程
				// 2是从特色市场----商品详情点击购买进入的
				// 3是单商品订单详情页面
				Log.v("aaa", "这里不为空");
				is_can_user.setText("已使用");
				Log.v("aaa", "12224");
				if (type.equals("0")) {
					Log.v("aaa", "12225");
					getOrder_Price("", sortModel.getSeriverTypeitemModel0().getScid(), updingdan.getCpid(), "1");
				} else if (type.equals("1")) {
					Log.v("aaa", "12226");
					getOrder_Price(pids_num, scids, updingdan.getCpid(), "2");
				} else if (type.equals("2")) {
					Log.v("aaa", "12227");
					/**
					 * pid 商品ID，多个以#分隔 scid 服务ID，多个以#分隔 cpid 优惠券ID otype
					 * 订单类型1=洗车订单；2=保养订单3=商品订单
					 */
					if (StringUtil.isNullOrEmpty(teshe_shangping.getNumber())) {
						teshe_shangping.setNumber("1");
					}
					getOrder_Price(teshe_shangping.getPid() + fenge + teshe_shangping.getNumber(), teshe_shangping.getScid(), updingdan.getCpid(), "3");
				}

			}
		}

		// 车管家设置
		if (requestCode == REQUEST_TO_CARMANAGER_FOR_WASHCAR && resultCode == RESULT_OK) {
			MyCarInfosModel model = (MyCarInfosModel) data.getSerializableExtra("data");
			if (model == null) {

				Toast.makeText(this, "请必须选择一个车辆信息", Toast.LENGTH_SHORT).show();
				return;

				// tv_shipeichexing.setText(getString(R.string.adpater_chexing,
				// "无"));
				// sortModel.setCmid(null); //关键值 在网络请求数据时需要传递
				// sortModel.setCbid(null);
				// sortModel.setCm_name(null);
				// sortModel.setColor(null);
				// sortModel.setCs_name(null);
				// sortModel.setCsid(null);
				// sortModel.setName(null);
				// sortModel.setUcid(null);
			} else {

				tv_shipeichexing.setText(getString(R.string.adpater_chexing, model.getCsname()));
				// 将CarInfoModel转化为SortMode 传递给下一个界面
				sortModel.setCbid(model.getCbid());
				sortModel.setCm_name(model.getCmname());
				sortModel.setColor(model.getColor());
				sortModel.setCs_name(model.getCsname());
				sortModel.setCsid(model.getCsid());
				sortModel.setName(model.getCbname());
				sortModel.setUcid(model.getUcid());

				sortModel.setCmid(model.getCmid());
				sortModel.setPlateNumber(model.getPlateNumber());// 设置车牌
																	// 必须设置，更新界面相关
				sortModel.setColor(model.getColor()); // 设置车辆颜色 必须设置，更新界面相关

				// 更新页面
				initJieMian();
			}
		}

		// 设置预约时间
		if (requestCode == 5 && resultCode == SelectSETimeActivity.Result_ok) {
			Log.d("aaa", "测试arg0===>" + requestCode + "/arg1===>" + resultCode);
			if (data != null) {
				Log.d("aaa", "进入到了");
				String startTime = data.getStringExtra("startTime");
				String endTime = data.getStringExtra("endTime");
				String date = data.getStringExtra("date");
				// updingdan.setLng(Longitude);
				// updingdan.setLat(Latitude);
				// updingdan.setAddress(addre);
				updingdan.setStime(startTime);
				updingdan.setEtime(endTime);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date start = sdf.parse(startTime);
					Date end = sdf.parse(endTime);
					SimpleDateFormat sdf0 = new SimpleDateFormat("MM月dd日");
					SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
					String startMd = sdf0.format(start);
					String startHMStr = sdf1.format(start);
					String endHMStr = sdf1.format(end);
					yuyue_time.setText(startMd + "(" + startHMStr + "-" + endHMStr + ")");// 7月30日（9:00-11:00）
				} catch (Exception e) {
					// 兼容之前版本
					yuyue_time.setText("" + startTime + "-" + endTime);// xxxx-xx-xx
																		// xx:xx-xxxx-xx-xx
																		// xx:xx）
				}

			}
		}

		// 获取地图中的数据
		if (requestCode == 2 && resultCode == LocationActivity.Result_ok) {
			if (data != null) {
				String Longitude = data.getStringExtra("Longitude");
				String Latitude = data.getStringExtra("Latitude");
				String addre = data.getStringExtra("result");
				updingdan.setLng(Longitude);
				updingdan.setLat(Latitude);
				updingdan.setAddress(addre);

				address.setText("已选择");
			}
		}

		Log.v("aaa", "运行了吗？" + "arg0=" + requestCode + "arg1=" + resultCode);
		// // 这是获取开始时间和结束时间
		// if (arg0 == 1 && arg1 == 0) {
		// if (!StringUtil.isNullOrEmpty(Content.startTime) &&
		// !StringUtil.isNullOrEmpty(Content.endTime)) {
		// try {
		// String starttime = Content.startTime;
		// String endtime = Content.endTime;
		// updingdan.setStime(starttime);
		// updingdan.setEtime(endtime);
		// String[] str1 = starttime.split(" ");
		// String[] str2 = endtime.split(" ");
		// Log.v("aaa", "str1=" + str1.length);
		// yuyuetime.setText("" + str1[0] + " (" + str1[1] + "-" + str2[1] +
		// ")");
		// Log.v("aaa", "运行了吗？" + "Content.startTime=" + Content.startTime +
		// "/Content.endTime=" + Content.endTime);
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		//
		// }
		// }
		// updingdan.setCmid(sortModel.getCmid());// 车型id
		// updingdan.setScid(sortModel.getFuwutypeid());
		// 从新选择车型信息
		if (requestCode == 3 && resultCode == 0) {
			if (Content.sortModel != null) {
				this.sortModel = Content.sortModel;
				updingdan.setUcid(sortModel.getUcid());// 车型id
				updingdan.setScid(sortModel.getSeriverTypeitemModel0().getScid());
				tv_shipeichexing.setText("适配车型:" + sortModel.getName() + sortModel.getCs_name());// +sortModel.getCm_name()
				Content.sortModel = null;
				Content.enter_ChaiceCarFlags = 0;
			} else {
				Content.sortModel = null;
				Content.enter_ChaiceCarFlags = 0;
			}
		}
		// 获取地区的
		if (requestCode == 8 && resultCode == RESULT_OK) {
			if (data != null) {
				// map.put("diquname", diquname);//地区名吃
				// map.put("jname", jname);//街道名称
				// map.put("diquid", diquid);//地区id
				// map.put("jiedaoid", jiedaoid);//街道id
				// map.put("quanbu_dizhi",
				// quanbu_dizhi.getText().toString().trim());//详细地址

				String diquname = data.getStringExtra("diquname");//
				// arg2.getString("diquname");
				String jname = data.getStringExtra("jname");
				String diquid = data.getStringExtra("diquid");
				String jiedaoid = data.getStringExtra("jiedaoid");
				String quanbu_dizhi = data.getStringExtra("quanbu_dizhi");
				Log.v("aaa", diquname + jname + diquid + jiedaoid + quanbu_dizhi);
				StringBuffer buff = new StringBuffer("");
				String[] address = data.getStringArrayExtra("addr");
				if (address != null) {
					if (address.length > 0) {
						for (int i = 0; i < address.length; i++) {
							buff.append(address[i]);
						}
					}
				}

				address2.setText(buff.toString() + " " + quanbu_dizhi);
				// updingdan.setSid(jiedaoid);
				updingdan.setAid(jiedaoid);
				updingdan.setAddress(quanbu_dizhi);

			}
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
				JumpUtils.jumpfinish(ServiceOrderActivity.this);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
