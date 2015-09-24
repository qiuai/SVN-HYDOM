package com.carinsurance.net;

public class Task {
	// 内网域名
	// http://192.168.0.61:8080/cbms/api/
	// public static final String url =
	// "http://192.168.0.61:8080/cbms/api/";//// liudun

//	// 内网
//	public static final String url = "http://192.168.0.136:8081/api/";// heou
//	public static final String img_url = "http://192.168.0.136:8081/";
////	http://qq826552818.imwork.net:27989
//	// (支付商品)内网支付回调（第一个一个是内网，一个是外网）//57702
//	public static final String ALIPAY_RETURN_URL = "http://qq826552818.imwork.net:27989/cbms/web/pay/alipay_return";//
//	// 内网回调地址
//	// (充值) 内网充值回调地址
//	public static final String ALIPAY_RECHARGE_RETURN_URL = "http://qq826552818.imwork.net:27989/cbms/web/pay/alipay_recharge_return";//
	// 内网充值地址

	// // 外网
	public static final String url = "http://www.yidongchebao.com/api/";
	public static final String img_url = "http://www.yidongchebao.com/";
	// (支付商品)外网支付回调地址
	public static final String ALIPAY_RETURN_URL = "http://www.yidongchebao.com/web/pay/alipay_return";// 内网回调地址
	// (充值) 外网充值回调地址
	public static final String ALIPAY_RECHARGE_RETURN_URL = "http://www.yidongchebao.com/web/pay/alipay_recharge_return";// 内网充值地址

	public static final String GETTIANQI = "101";// (首页的第一个界面)获取天气的标识

	public static final int GETMENDIANLIST = 102;// 获取门店列表
	public static final String USER_CAR_EDIT = "user/caredit";// 撤管理编辑；
	public static final String GET_USERCARS = "user/cars";// 获取用户车型列表

	public static final String GET_CARSAVE = "user/carsave";//
	public static final String USER_LOGIN = "user/signin";// 用户登录
	public static final String GET_CODE = "common/icode";// 获取验证码
	public static final String GET_SERVICE = "server/category";// 首页获取服务
	public static final String GET_CARBRAND = "server/carbrand";// 获取车辆品牌信息
	public static final String GET_GETCARSERIVES = "server/carseries";// 根据品牌信息获取车系；
	public static final String GET_GETCARMODEL = "server/carmodel";// 获取车型
	public static final String GET_GETDIQU = "server/arealist";// 获取贵阳市下属所有地区信息
	public static final String GET_GETQU = "server/streetlist";// 获取指定地区下所有区的信息
	public static final String GET_GETTIME = "server/subscribe";// 获取车队有空的预约的时间
	public static final String SEND_DINGDAN = "order/save";// 提交订单的接口

	public static final String GET_USERCAR = "user/car";// 获取用户默认车辆信息
	public static final String GET_STORE = "server/store";// 根据经纬度获取附近的门店
	public static final String GET_PINGPAI_List = "product/brand";// 获取商品品牌列表
	public static final String GET_FIND_List = "extra/news";// 获取发现列表

	public static final String GET_FEEDBACK = "user/feedback";// 反馈
	public static final String GET_ISHASTECHNICIAN = "server/technician";// 查询是否有可以服务的技师
	public static final String TIJIAO_XICHE_ORDER = "order/carwash/save";// 上门洗车提交的订单
	public static final String GET_MAINTENANCE = "server/maintenance";// 获取所有保养服务类型
	public static final String GET_PRODUCTDEFAULT = "product/default";// 根据服务类型和用户车辆信息默认的一件推荐商品

	public static final String GET_PRODUCTLIST = "product/list";// 根据服务类型和用户车辆信息获取商品列表
	public static final String TIIJAO_BAOYANG_ORDER = "order/server/save";// 提交保养服务订单
	public static final String GET_SERVER_DATA = "server/date";// 根据当前时间，获取服务时间
	public static final String GET_DELETE_CAR_XING = "user/cardelete";// 删除车型
	public static final String GET_PRODUCT_RECOMMEND_LIST = "product/brand/recommend/list";// 获取特色市场品牌推荐商品列表信息
	public static final String GET_PRODUCT_BOUTIQUE_LIST = "product/boutique/list";// 获取特色市场限量商品列表
	public static final String GET_PRODUCT_GERRN_LIST = "product/green/list";// 获取特色市场绿色市场
	public static final String GET_PRODUCT_SPECIAL_LIST = "product/special/list";// 获取天天特价商品列表信息
	public static final String GET_PRODUCT_DETAIL = "product/detail";// 获取商品详情
	public static final String TIIJAO_PRODUCT_ORDER = "order/product/save";// 获取商品详情
	public static final String GET_COMMON_INDEX = "common/index";// 首页获取服务，特色市场等等。。。
	public static final String GET_PROCEED_ORDER = "user/order/proceed";// 获取用户进行中订单
	public static final String GET_COUPON_ORDER = "user/coupon/exchange/list";// 获取可用的或过期的优惠卷
	public static final String MY_COUPON = "user/coupon/list";// 显示创建的优惠券列表，对应于【我的积分】

	public static final String GET_COUPON = "user/coupon/exchange";// （申请兑换优惠卷接口）
	public static final String GET_USER_FINISH_ORDER = "user/order/finish";// 获取用户取消订单
	public static final String USER_CANCEL_ORDER = "user/order/op/cancel";// 取消订单操作

	// 提交上门保养订单

	public static final String GET_HOT_CATEGORY = "product/category/more"; // 获取热卖商品分类
	public static final String GET_HOT_CATEGORY_LIST = "product/category/list";// 获取指定分类的热卖商品列表
	public static final String GET_AREA_FIRST_LEVEL = "server/area/top/list";// 获取地区的第一级
	public static final String GET_AREA_CHILD_LEVEL = "server/area/child/list";// 获取地区的子级元素

	public static final String GET_ORDER_CANCEL = "user/order/cancel";// 获取用户已取消订单

	public static final String USER_CONFIRM_ORDER = "user/order/op/confirm";// 用户确认收货
	public static final String GET_COUPON_LIST = "server/coupon/list";// 获取用户可用的优惠卷
	public static final String GET_CAN_USER_COUPON = "server/coupon";// 判断用户是否有可用优惠卷

	public static final String COMMENT_SAVE = "user/comment/save"; // 提交评论内容
	public static final String COMMENT_UPLOAD_IMAGE = "user/comment/upload"; // 评论时图片上传
	public static final String GET_USER_DATA = "user/profile"; // 获取用户资料
	public static final String GET_USER_NICK = "user/profile/edit"; // 请求修改用户昵称和头像
	public static final String GET_XICHE_ORDER_XIANGQING = "user/order/carwash/detail"; // 获取洗车订单详情
	public static final String GET_BAOYANG_ORDER_XIANGQING = "user/order/server/detail"; // 获取保养订单详情
	public static final String GET_PRODUCT_COMMENTS = "product/comment/list"; // 获取指定商品的评论数据
	public static final String GET_BALANCE_AND_RECORDS = "user/fee/record/list"; // 获取用户余额和消费记录

	public static final String GET_SHANGPING_ORDER_XIANGQING = "user/order/product/detail"; // 获取商品订单详情
	public static final String GET_ZHICHIDIQU = "extra/support/area"; // 获取商品订单详情
	public static final String USER_SIGNOUT = "user/signout"; // 退出登录
	public static final String UPDATE_PROFILE = "user/profile/edit";
	public static final String ORDER_PRICE_CALC = "order/price/calc"; // 订单价格计算
	public static final String USER_RECHAREG_NUMBER = "user/fee/rechareg/number"; // 获取用户充值编号
	public static final String GET_VERSION_NUMBER = "common/version/android"; // 获取服务端android最新版本号及下载页面url地址
	public static final String GET_COUPONPAC_List = "user/couponpack/list"; // 获取会员卡包（优惠券包）列表
	public static final String USER_PAY_COUPONPACK = "user/couponpack/buy"; // 用户购买优惠券包
	public static final String GET_PUREMAINTENANCE_PRICE="server/puremaintenance/price";//获取纯保养订单的价格
}
