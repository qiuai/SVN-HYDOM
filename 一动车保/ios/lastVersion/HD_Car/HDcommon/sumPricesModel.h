//
//  sumPricesModel.h
//  HD_Car
//
//  Created by xingso on 15/8/17.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface sumPricesModel : NSObject

@property(nonatomic,strong)NSString* uid;


@property(nonatomic,strong)NSString* token;

@property(nonatomic,strong)NSString* pid;

@property(nonatomic,strong)NSString* scid;



@property(nonatomic,strong)NSString* money;

/**优惠劵id*/
@property(nonatomic,strong)NSString* cpid;


/**订单类型
 1=洗车订单；2=保养订单
 3=商品订单 
 */
@property(nonatomic,strong)NSNumber* otype;


//只需要赋值数组就ok  自动拼接
@property(nonatomic,strong)NSArray* pidArray;



//只需要赋值数组就ok  自动拼接
@property(nonatomic,strong)NSArray* scidArray;


//商品购买数量
@property(nonatomic,strong)NSString* productCount;


+(instancetype)sumPrices;



@end
