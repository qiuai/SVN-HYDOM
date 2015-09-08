package com.carinsurance.infos;

import com.carinsurance.abcpinying.SortModel;

public class Content {

	public static String startTime = null;
	public static String endTime = null;

	//
	public static SortModel sortModel = null;//
	//
	// 0是默认首页的洗车服务 进去的 (纯服务)，说明了逻辑流程是（客户没有车型，需要选择一次车型） 然后进入到CarInfosActivity
	// 要跳到服务订单的界面
	// 1（不能再用了）是代替了startActivityResult，很多地方用到了，比如在CarInfosActivity
	// 点击更换车型后又回到CarInfosActivity ，和1相比不需要跳到自身
	// 2是从点击车管家的添加到 走流程到保存信息那（这里和0是一样的，只是不需要跳到服务订单界面）
	// 3是上门保养流程(多商品)
	// 4是上门保养单商品（单商品）
	// 5热卖分类走的选车流程
	// 6热卖分类中点击更多后进入到的界面
	public static int enter_ChaiceCarFlags = -1;// 判断是从那个Activity
												// 进入到ChaiceCarActivity,再到后面的保存车型

	// 为了不混乱，和enter_ChaiceCarFlags区别开了 1是carInfosActivity 中的
	// 不会进入到carInfoActivity 2是进入到车管家
	public static int enter_ch = 0;

	public static boolean is_refresh = false;

	/**
	 * 处理微信支付的回调   0  不管   1支付成功   2支付取消    3支付失败
	 */
	public static int weixin_pay_return = 0;

}
