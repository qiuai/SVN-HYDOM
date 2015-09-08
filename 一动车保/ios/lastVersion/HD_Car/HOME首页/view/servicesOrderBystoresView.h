//
//  servicesOrderBystoresView.h
//  HD_Car
//
//  Created by xingso on 15/7/22.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h> 
#import "JVshowkeyboardTextFiled.h"

@interface servicesOrderBystoresView : UIView
@property (weak, nonatomic) IBOutlet UILabel *selectServiceLabel;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *phoneText;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *personText;
@property (weak, nonatomic) IBOutlet UILabel *selectStoresLabel;
@property (weak, nonatomic) IBOutlet UILabel *selectTimeLabel;
@property (weak, nonatomic) IBOutlet UILabel *selectCouponsLabel;
@property (weak, nonatomic) IBOutlet UIImageView *zhiFuImageView;
@property (weak, nonatomic) IBOutlet UIImageView *zhiFuImageView1;
@property (weak, nonatomic) IBOutlet UIImageView *zhiFuImageVIew2;

@end
