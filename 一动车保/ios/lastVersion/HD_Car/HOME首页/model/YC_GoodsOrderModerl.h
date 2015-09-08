//
//  YC_GoodsOrderModerl.h
//  HD_Car
//
//  Created by hydom on 8/12/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YC_GoodsOrderModerl : NSObject

/**商品ID*/
@property(nonatomic,copy)NSString* pID;

/**商品名字*/
@property(nonatomic,copy)NSString* pName;

/**商品价格*/
@property(nonatomic,copy)NSString* pPrice;

/**购买数量*/
@property(nonatomic,copy)NSString* pNumber;

/**图片地址*/
@property(nonatomic,copy)NSString* pImage;

/**付款价格*/
@property(nonatomic,copy)NSString* pPay;

/**购买人*/
@property(nonatomic,copy)NSString* pUser;

/**购买人电话*/
@property(nonatomic,copy)NSString* pPhone;

/**购买人地区ID*/
@property(nonatomic,copy)NSString* pAid;

/**购买人地址*/
@property(nonatomic,copy)NSString* pAddress;

/**优惠券ID*/
@property(nonatomic,copy)NSString* pCpid;

/**支付方式  1=货到付款；2=支付宝
 3=银联
*/
@property(nonatomic,copy)NSString* payWay;
@end
