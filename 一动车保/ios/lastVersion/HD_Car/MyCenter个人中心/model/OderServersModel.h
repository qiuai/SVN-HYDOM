//
//  OderServersModel.h
//  HD_Car
//
//  Created by hydom on 15-8-4.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//
typedef NS_ENUM(NSUInteger, OrderServerStatus) {
//    有服务
    Subscribe = 1,//派单
    Allocation = 2,//路途中
    ServersEnd,//完成
//    只有商品
    Overbooking,//下单
    AllocationCommodity,//配货,送货
    SendCommodity,//送货完成
    CommodityEnd,//取消订单完成
    //取消订单
    CancellationOrder,//
    CancellationOrderFaile,
    CancellationOrderSuc,
};
#import <Foundation/Foundation.h>

@interface OderServersModel : NSObject
@property (nonatomic,strong)NSString * orderNum;
@property (nonatomic,assign)OrderServerStatus status;
@property (nonatomic,strong)NSArray * commodityArray;
@property (nonatomic,assign)BOOL isServers;
@property (nonatomic,strong)NSString * statusTitle;
@property (nonatomic,strong)NSString * payMoney;
@property (nonatomic,strong)NSString * name;
@property (nonatomic,strong)NSString * phone;
@property (nonatomic,strong)NSString * serversImageUrl;
@property (nonatomic,strong)NSString * serversName;
@property (nonatomic,strong)NSString * serversMoney;


@end

@interface Commodity : NSObject
@property (nonatomic,strong)NSString * money;
@property (nonatomic,strong)NSString * title;
@property (nonatomic,strong)NSString * imageUrl;
@property (nonatomic,strong)NSString * num;


@end
