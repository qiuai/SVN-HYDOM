//
//  YC_MarketModel.h
//  HD_Car
//
//  Created by hydom on 8/10/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YC_MarketModel : NSObject

/**
 *商品ID
 */
@property (nonatomic,strong)NSString * pid;

/**
 *商品名称
 */
@property (nonatomic,strong)NSString * pname;

/**
 *商品展示图地址
 */
@property (nonatomic,strong)NSString * pimage;

/**
 *商品购买数
 */
@property (nonatomic,strong)NSNumber * pbuynum;

/**
 *商品评论数
 */
@property (nonatomic,strong)NSNumber * pcomts;

/**
 *商品价格
 */
@property (nonatomic,strong)NSNumber * price;

/**
 *商品总的数量
 */
@property (nonatomic,strong)NSNumber * ptotalnum;

/**
 *商品已抢数量   （用于限量精品购买数量）
 */
@property (nonatomic,strong)NSNumber * salenum;

/**
 *商品原价
 */
@property (nonatomic,strong)NSNumber * poriprice;

/**
 *商品折后价
 */
@property (nonatomic,strong)NSNumber * pdisprice;

/**
 *商品折扣值
 */
@property (nonatomic,strong)NSNumber * pdiscount;

//数据转换
+(instancetype)getMarketModelFromDic:(NSDictionary *)dic;



@end
