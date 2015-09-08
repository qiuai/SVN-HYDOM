//
//  signValueObject.h
//  HD_Car
//
//  Created by xingso on 15/7/15.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "globalServicesModel.h"
//typedef NS_ENUM(NSInteger, navigationStyle) {
//    navigationStyleToCreatOrder = 0,//跳转到订单
//    navigationStyleBackCreatOrder  //返回到订单页面
//};
@interface signValueObject : NSObject

//提供给外部保存数据
@property(nonatomic,strong) NSMutableDictionary  *saveDictionary;

/**服务 数据保存  array  请使用globalServicesModel*/
@property(nonatomic,strong)NSMutableArray* servicesModelArray;

/**设置选择的  服务类型 全局*/
-(void)setSelectGlobalServicesModel:(globalServicesModel*)model;

/**获取选择的  服务类型 全局*/
-(globalServicesModel*)getSelectGlobalServiceModel;

+(instancetype)shareSignValue;

//是否设置默认车型
-(void)SetDefaultCar:(BOOL)bl;


//返回 是否设置默认车型
-(BOOL)getIsDefaultCar;
@end
