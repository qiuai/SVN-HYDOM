//
//  YC_SelectDiscountViewController.h
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "JVbaseViewController.h"
#import "discountModel.h"
#import "sumPricesModel.h"
@interface YC_SelectDiscountViewController : JVbaseViewController

@property(nonatomic,strong)sumPricesModel* sendModel;

//@property (nonatomic, copy) void(^selectblock)(discountModel * model);


/**返回数组回调 获取请参考接口文档 4.5.4 order/price/calc
*/
@property (nonatomic, copy) void(^pricesArrayblock)(NSArray * model);

@end
