//
//  JVcommonOrderInfoViewController.h
//  HD_Car
//
//  Created by xingso on 15/8/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVbaseViewController.h"
@interface JVcommonOrderInfoViewController : JVbaseViewController

@property (nonatomic, assign) BOOL goods;
@property (nonatomic, strong) NSString * price;//存服务价格
@property(nonatomic,strong)NSString* orderID;

@end
