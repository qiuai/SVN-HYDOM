//
//  carListViewController.h
//  HD_Car
//
//  Created by xingso on 15/7/30.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
@class carModel;
@interface carListViewController : UIViewController

//选择默认车型  回调
@property(nonatomic,copy)void (^defaultCarModel)(carModel *);

@end
