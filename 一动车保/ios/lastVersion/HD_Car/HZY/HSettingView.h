//
//  HSettingView.h
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SettingModel.h"

@interface HSettingView : UIView
@property (weak, nonatomic) IBOutlet UIImageView *headImageView;
@property (weak, nonatomic) IBOutlet UILabel *userNameLabel;
@property (weak, nonatomic) IBOutlet UIButton *logoutButton;
@property (weak, nonatomic) IBOutlet UIButton *changeImageButton;

@property (weak, nonatomic) IBOutlet UIButton *changeNickNameButton;
-(void)setUpUserInterfaceWith:(SettingModel *)model;


@property(weak,nonatomic)id viewController;



@property(assign,nonatomic)SEL Method;

@end
