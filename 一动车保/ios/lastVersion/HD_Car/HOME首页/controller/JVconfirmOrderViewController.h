//
//  JVconfirmOrderViewController.h
//  HD_Car
//
//  Created by xingso on 15/7/31.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
@class submitOrderModel;
@interface JVconfirmOrderViewController : UIViewController
//是否有技师
@property(nonatomic,assign)BOOL isTrue;
@property(nonatomic,strong)submitOrderModel* model;
@end
