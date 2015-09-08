package com.carinsuran.car.config;

/**
 * 服务器地地址
 * 
 * @author Administrator
 *
 */
public class HttpUrl
{

	public static final String IP = "192.168.0.136:8081";
	
//	public static final String IP = "192.168.0.234:8080";
	
//	public static final String IP = "www.yidongchebao.com";
	public static final String BASE_URL = "http://" + IP + "/api/technician/";

	public static final String BASE_IMAGE_URL = "http://" + IP;
	
	/**
	 * 服务器地地址
	 */
//	public static final String BASE_URL = "http://" + IP + "/api/technician/";
	
	/**
	 * 图片地址
	 */
	public static final String IMAGE_LOAD = "http://" + IP + "/";
	
	/**
	 * 用户更新经纬度
	 */
	public static final String UPDATE_LAT_LNG ="setLngAndLat";
	
	/**
	 * 用户登陆
	 */
	public static final String LOGIN ="login";
	/**
	 * 技师上下班
	 */
	public static final String UPLOADIMAGE ="work";
	/**
	 * 修改密码
	 */
	public static final String CHECK_AFANDA = "modifyPWD";
	
	/**
	 * 拒绝订单
	 */
	public static final String XING_ORDER ="refuseOrder";
	/**
	 * 接受订单
	 */
	public static final String LOADMEMID_ORDER = "acceptOrder";
	/**
	 * 上传图片
	 */
	public static final String MY_FRAGMENT = "imageUpload";
	/**
	 * 开始服务
	 */
	public static final String CIRCLE_OF_FRENDS = "serviceStart";
	/**
	 * 获取系统当前时间
	 */
	public static final String TIME_HUO = "getDate";
	
	/**
	 * 当前技师订单
	 */
	public static final String ORDER = "CurrentOrder";
    /**
     * 结束服务
     */
    public static final String STOP_SERVICE="serviceEnd";
    /**
     * 根据分类查询订单
     */
    public static final String ORDERS ="selectOrder";
   /**
    * 订单详情
    */
    public static final String DELETEORDER="getOrderInfo";
    /**
     * 推送过来的订单判断当前订单有没有过期
     */
    public static final String ORDERTRYEORFALES = "order/status";
}
