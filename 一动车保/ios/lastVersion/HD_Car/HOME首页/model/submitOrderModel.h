//
//  submitOrderModel.h
//  HD_Car
//
//  Created by xingso on 15/8/4.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface submitOrderModel : NSObject
@property(nonatomic,strong) NSString* ucid;
@property(nonatomic,strong) NSString* scid;
@property(nonatomic,strong) NSString* contact;
@property(nonatomic,strong) NSString* phone;
@property(nonatomic,strong) NSString* plateNumber;
@property(nonatomic,strong) NSString* color;
@property(nonatomic,strong) NSString*lat;
@property(nonatomic,strong) NSString* lng;
@property(nonatomic,strong) NSString*address;
@property(nonatomic,strong) NSString*cpid;
@property(nonatomic,strong) NSString* cleanType;
@property(nonatomic,strong) NSString* payWay;
@property(nonatomic,strong)NSString* carDescription;
@property(nonatomic,strong)NSString* serviceDescription;
@property(nonatomic,strong) NSString* payWayDescription;


/**应付金额*/
@property(nonatomic,strong) NSString* payPrcies;

/**优惠金额*/
@property(nonatomic,strong) NSString* couponsPrices;

/**实付*/
@property(nonatomic,strong) NSString* realityPrices;


@property(nonatomic,strong)NSString* isUseCoupon;

@end
