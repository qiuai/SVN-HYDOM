//
//  YC_VipModel.h
//  HD_Car
//
//  Created by hydom on 9/11/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YC_VipModel : NSObject

/**
 *优惠券包ID
 */
@property (nonatomic,strong)NSString * cppid;

/**
 *优惠券包名称
 */
@property (nonatomic,strong)NSString * cppname;

/**
 *优惠券包展示图
 */
@property (nonatomic,strong)NSString * cppimg;

/**
 *优惠券包价格
 */
@property (nonatomic,strong)NSString * cppprice;

/**
 *包含的优惠名称
 */
@property (nonatomic,strong)NSString * cpname;

/**
 *包含的优惠数量
 */
@property (nonatomic,strong)NSNumber * cpnum;


@end
