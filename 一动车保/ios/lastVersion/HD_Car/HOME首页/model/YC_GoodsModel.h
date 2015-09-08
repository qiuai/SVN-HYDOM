//
//  YC_GoodsModel.h
//  HD_Car
//
//  Created by hydom on 8/7/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YC_GoodsModel : NSObject

//商品model

/**
 *商品scid
 */
@property (nonatomic,strong)NSString * scid;

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
 *商品评价数
 */
@property (nonatomic,strong)NSNumber * pcomts;


/**
 *商品价格
 */
@property (nonatomic,strong)NSNumber * price;
/**
 *商品图文详情url地址
 */
@property (nonatomic,strong)NSString * purl;



/**
 *自己购买数量
 */
@property (nonatomic,assign)NSInteger myCount;


/**字典转模型*/
+(instancetype)goodsModelWithDictionary:(NSDictionary*)dictionary;

@end
