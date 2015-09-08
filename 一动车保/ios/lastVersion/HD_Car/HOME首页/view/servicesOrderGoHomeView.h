//
//  servicesOrderGoHomeView.h
//  HD_Car
//
//  Created by xingso on 15/7/22.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVshowkeyboardTextFiled.h"
@interface servicesOrderGoHomeView : UIView
@property (weak, nonatomic) IBOutlet UILabel *selectServicesLabel;
@property (weak, nonatomic) IBOutlet UILabel *selectAddress;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *phoneLabel;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *personLabel;
@property (weak, nonatomic) IBOutlet UILabel *selectTimeLabel;
@end
