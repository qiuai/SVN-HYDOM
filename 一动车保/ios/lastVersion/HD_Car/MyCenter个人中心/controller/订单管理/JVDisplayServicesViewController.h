//
//  JVcommonDisplayServicesViewController.h
//  HD_Car
//
//  Created by xingso on 15/8/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVcommonOrderSericesProductMOdel.h"
#import "JVcommonOrderSericesModel.h"

@interface JVDisplayServicesViewController : UIViewController

//判断纯服务 （YES纯服务）
@property(nonatomic,assign)BOOL pureServer;
@property(nonatomic,strong)NSArray* dataSoure;
@property(nonatomic,strong)UILabel * priceLabel;
@property(nonatomic,assign)CGFloat ViewHeight;
@end
