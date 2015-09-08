//
//  HOrderServersTableViewCell.h
//  HD_Car
//
//  Created by hydom on 15-8-4.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "OderServersModel.h"

@interface HOrderServersTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *orderNumLabel;
@property (weak, nonatomic) IBOutlet UILabel *statusLabel;
@property (weak, nonatomic) IBOutlet UIImageView *serverImageView;
@property (weak, nonatomic) IBOutlet UILabel *serversNameLabel;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *commodityViewTop;



@property (weak, nonatomic) IBOutlet UILabel *serversMoneyLabel;
@property (weak, nonatomic) IBOutlet UIView *commodityView;
@property (weak, nonatomic) IBOutlet UILabel *payNumLabel;
@property (weak, nonatomic) IBOutlet UIButton *cancleButton;
@property (weak, nonatomic) IBOutlet UILabel *technicianNameLabel;
@property (weak, nonatomic) IBOutlet UILabel *phoneLabel;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *commodityViewHeight;

@property (weak, nonatomic) IBOutlet UIView *technicianInforView;
@property (weak, nonatomic) IBOutlet UIView *serversView;

- (void)setUpUserInterfaceWith:(OderServersModel *)model;
+ (CGFloat)cellHeight:(OderServersModel *)model;
@end
