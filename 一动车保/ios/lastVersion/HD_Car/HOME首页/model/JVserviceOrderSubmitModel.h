//
//  JVserviceOrderSubmitModel.h
//  HD_Car
//
//  Created by xingso on 15/8/8.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

/**保养订单提交*/
@interface JVserviceOrderSubmitModel : NSObject

@property(nonatomic,strong)NSString*ucid;

@property(nonatomic,strong)NSString*stime;

@property(nonatomic,strong)NSString*etime;

@property(nonatomic,strong)NSString*phone;

@property(nonatomic,strong)NSString*contact;

@property(nonatomic,strong)NSString*plateNumber;

@property(nonatomic,strong)NSString*color;

@property(nonatomic,strong)NSString*lat;

@property(nonatomic,strong)NSString*lng;

@property(nonatomic,strong)NSString*address;

@property(nonatomic,strong)NSString*cpid;

@property(nonatomic,strong)NSString*remark;

@property(nonatomic,strong)NSString* payWay;

@property(nonatomic,strong)NSMutableDictionary* httpDataDict;

@property(nonatomic,strong)NSString* httpData;

/**预约时间*/
@property(nonatomic,strong)NSString* timeDescriptionStr;

/**车型描述*/
@property(nonatomic,strong)NSString* carDescription;

/**支付方式描述*/
@property(nonatomic,strong)NSString* payWayDescriotion;
@end
