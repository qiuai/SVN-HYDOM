//
//  serviceOrderView.h
//  HD_Car
//
//  Created by xingso on 15/7/20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVshowkeyboardTextFiled.h"
@interface serviceOrderView : UIView
@property (weak, nonatomic) IBOutlet UIView *selectStoresView;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *phoneNumberTEXT;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *personTextField;
@property (weak, nonatomic) IBOutlet UILabel *storesAddressLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;
@property (weak, nonatomic) IBOutlet UILabel *couponLabel;

@end
