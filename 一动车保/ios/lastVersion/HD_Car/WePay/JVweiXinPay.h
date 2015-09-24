//
//  JVweiXinPay.h
//  HD_Car
//
//  Created by xingso on 15/8/21.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "payRequsestHandler.h"
#import "WXApi.h"
@interface JVweiXinPay : NSObject<WXApiDelegate>

//微信支付 需要prepayID
+(void)payFromWeXin:(NSString*)prepayID;

//判断是否 安装微信
+(BOOL)isWXAppInstalled;





@end
