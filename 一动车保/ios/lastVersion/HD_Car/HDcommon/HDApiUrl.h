//
//  HDApiUrl.h
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/24.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "sumPricesModel.h"
@interface HDApiUrl : NSObject



/**天气API*/
#define weatherAPI @"http://api.map.baidu.com/telematics/v3/weather"

//#define weatherKEY @"P8TB4IQ6AAg9efkpDGy4nGXQ"

#define weatherKEY @"KGbPYjVBZiWKRjh0XTBjxse5"











/**imageURL*/
#define imageURLWithPath(path) [NSURL URLWithString:[NSString stringWithFormat:@"%@/%@",rootUrl,(path)]]


/**HTML URL*/
#define htmlWithPath(path) [NSString stringWithFormat:@"%@/api%@",rootUrl,(path)]


/**用户注册、登陆*/
#define loginAPI   [rootUrl stringByAppendingString:@"/api/user/signin"]


/**退出*/
#define signoutAPI   [rootUrl stringByAppendingString:@"/api/user/signout"]



/**首页获取服务 以及图标*/
#define homeGetServicesAPI   [rootUrl stringByAppendingString:@"/api/common/index"]
//#define homeGetServicesAPI   [rootUrl stringByAppendingString:@"/api/server/category"]

/**获取用户车型信息*/
#define userCarModelAPI   [rootUrl stringByAppendingString:@"/api/user/car"]


/**获取用户车型list*/
#define userCarListAPI   [rootUrl stringByAppendingString:@"/api/user/cars"]

/**发送 登陆验证码*/
#define sendCaptchaAPI   [rootUrl stringByAppendingString:@"/api/common/icode"]

/**车辆品牌信息*/
#define getCarBrandAPI   [rootUrl stringByAppendingString:@"/api/server/carbrand"]
/**车系信息*/
#define getCarseriesAPI   [rootUrl stringByAppendingString:@"/api/server/carseries"]
/**车型信息*/
#define getCarModelAPI   [rootUrl stringByAppendingString:@"/api/server/carmodel"]

/**发现列表*/
#define discoverListAPI  [rootUrl stringByAppendingString:@"/api/views/list.do"]


/**获取预约时间*/
#define getSubscribeTimeAPI  [rootUrl stringByAppendingString:@"/api/server/subscribe"]


/**获取贵阳市区信息*/
#define getArealistAPI  [rootUrl stringByAppendingString:@"/api/server/arealist"]

/**获取贵阳市街道街道*/
#define getStreetlistAPI  [rootUrl stringByAppendingString:@"/api/server/streetlist"]


/**保存用户车型*/
#define carsaveAPI  [rootUrl stringByAppendingString:@"/api/user/carsave"]



/**修改用户车型*/
#define carEditAPI  [rootUrl stringByAppendingString:@"/api/user/caredit"]

/**删除用户车型*/
#define careDeleteAPI  [rootUrl stringByAppendingString:@"/api/user/cardelete"]


/**获取商品品牌列表*/
#define getbrandAPI  [rootUrl stringByAppendingString:@"/api/product/brand"]

/**选择服务表单**/
#define getSelectOrderAPI  [rootUrl stringByAppendingString:@"/api/product/default"]

/**据服务类型和用户车辆信息获取商品列表**/
#define getSelectOrderGoodsAPI [rootUrl stringByAppendingString:@"/api/product/list"]

/**所有保养服务类型**/
#define getAllServicesAPI  [rootUrl stringByAppendingString:@"/api/server/maintenance"]



/**获取商品详情**/
#define getProductInfoAPI  [rootUrl stringByAppendingString:@"/api/product/detail"]

/**获取技师**/
#define getTechnicianAPI  [rootUrl stringByAppendingString:@"/api/server/technician"]

/**洗车订单提交*/
#define submitWashCarAPI  [rootUrl stringByAppendingString:@"/api/order/carwash/save"]

/**保养服务订单提交*/
#define OrderSaveForServicesAPI  [rootUrl stringByAppendingString:@"/api/order/server/save"]


/**获取服务时间*/
#define getServerDataAPI  [rootUrl stringByAppendingString:@"/api/server/date"]


/**进行中订单*/
#define getDoingOrderAPI  [rootUrl stringByAppendingString:@"/api/user/order/proceed"]

/**完成的订单*/
#define getFinishOrderAPI  [rootUrl stringByAppendingString:@"/api/user/order/finish"]

/**取消的订单*/
#define getDidCancelOrderAPI  [rootUrl stringByAppendingString:@"/api/user/order/cancel"]

/**特色市场品牌推荐商品列表*/
#define getMarketSuggestAPI  [rootUrl stringByAppendingString:@"/api/product/brand/recommend/list"]

/**特色市场限量精品商品列表*/
#define getMarketLimitAPI  [rootUrl stringByAppendingString:@"/api/product/boutique/list"]

/**特色市场天天特价商品列表*/
#define getMarketSaleAPI  [rootUrl stringByAppendingString:@"/api/product/special/list"]

/**特色市场绿色出行商品列表*/
#define getMarketLifeAPI  [rootUrl stringByAppendingString:@"/api/product/green/list"]


/**首页热卖更多*/
#define getHotMoreAPI  [rootUrl stringByAppendingString:@"/api/product/category/more"]

/**首页热卖单商品列表*/
#define getHotGoodsAPI  [rootUrl stringByAppendingString:@"/api/product/category/list"]

/**用户反馈*/
#define userFeedbackAPI  [rootUrl stringByAppendingString:@"/api/user/feedback"]


/**提交产品购买订单*/
#define postOrderAPI  [rootUrl stringByAppendingString:@"/api/order/product/save"]



/**取消订单*/
#define cancelOrderAPI  [rootUrl stringByAppendingString:@"/api/user/order/op/cancel"]


/**确认收货*/
#define confirmOrderAPI  [rootUrl stringByAppendingString:@"/api/user/order/op/confirm"]




/**评论上传图片*/
#define uploadImageForEvaluationAPI  [rootUrl stringByAppendingString:@"/api/user/comment/upload"]
/**
 *  获取用户信息
 */
#define GetUserInforAPI  [rootUrl stringByAppendingString:@"/api/user/profile"]
/**
 *  更改用户信息
 */
#define ChangeUserInforAPI  [rootUrl stringByAppendingString:@"/api/user/profile/edit"]
/**
 *  余额，账单查询
 */
#define UserFreeMoneyAPI  [rootUrl stringByAppendingString:@"/api/user/fee/record/list"]
/**
 *  获取一级地区分类
 */
#define TopAreaListAPI  [rootUrl stringByAppendingString:@"/api/server/area/top/list"]
/**
 *  获取子类地区分类
 */
#define ChildAreaListAPI  [rootUrl stringByAppendingString:@"/api/server/area/child/list"]


/**评论*/
#define sendEvaluationAPI  [rootUrl stringByAppendingString:@"/api/user/comment/save"]

/**我的积分*/
#define getMyScoreAPI  [rootUrl stringByAppendingString:@"/api/user/coupon/list"]



/**保养订单详情*/
#define getOrderServicesDetailAPI  [rootUrl stringByAppendingString:@"/api/user/order/server/detail"]


/**商品详情*/
#define getOrderProductDetailAPI  [rootUrl stringByAppendingString:@"/api/user/order/product/detail"]


/**获得评论列表*/
#define getCommentListAPI  [rootUrl stringByAppendingString:@"/api/product/comment/list"]


/**获得优惠券(或过期)列表*/
#define getDiscountListAPI  [rootUrl stringByAppendingString:@"/api/user/coupon/exchange/list"]


/**洗车订单详情*/
#define getOrderWashCarDetailAPI  [rootUrl stringByAppendingString:@"/api/user/order/carwash/detail"]


/**【我的积分】兑换*/
#define postScorePayAPI  [rootUrl stringByAppendingString:@"/api/user/coupon/exchange"]

/**查询优惠券可用*/
#define getDiscountAbleAPI  [rootUrl stringByAppendingString:@"/api/server/coupon"]

/**查询优惠券可用列表*/
#define getDiscountAbleListAPI  [rootUrl stringByAppendingString:@"/api/server/coupon/list"]


/**计算价格*/
#define sumPricesAPI  [rootUrl stringByAppendingString:@"/api/order/price/calc"]


/**发现列表*/
#define getExtraNewsAPI  [rootUrl stringByAppendingString:@"/api/extra/news"]


/**支持范围*/
#define supportGareaAPI  [rootUrl stringByAppendingString:@"/api/extra/support/area"]

/**获取用户充值编号*/
#define getRechargeNumberAPI  [rootUrl stringByAppendingString:@"/api/user/fee/rechareg/number"]

/**保养服务价格**/
#define getBaoyangPriceAPI  [rootUrl stringByAppendingString:@"/api/server/puremaintenance/price"]

/**会员卡列表**/
#define getVipAPI  [rootUrl stringByAppendingString:@"/api/user/couponpack/list"]

/**会员卡购买**/
#define getBuyVipAPI  [rootUrl stringByAppendingString:@"/api/user/couponpack/buy"]

@end
