//
//  JVaffirmServicesOrderViewController.h
//  HD_Car
//
//  Created by xingso on 15/8/9.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVserviceOrderSubmitModel.h"
@interface JVaffirmServicesOrderViewController : UIViewController

/**提交订单 model*/
@property(nonatomic,strong)JVserviceOrderSubmitModel* model;
/**展示商品 图片以及信息 array*/
@property(nonatomic,copy)NSArray* showProductArray;
//价格数组
@property(nonatomic,strong)NSArray* aboutPricesArray;


@property(nonatomic,strong)NSString* servicesCount;


@property(nonatomic,strong)NSArray* dispalyProductArray;
@end
