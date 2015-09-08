//
//  HOrderWashTableViewCell.h
//  HD_Car
//
//  Created by hydom on 15-8-4.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "OrderCarWashModel.h"

@interface HOrderWashTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *orderNum;
@property (weak, nonatomic) IBOutlet UILabel *statusLabel;
@property (weak, nonatomic) IBOutlet UIImageView *orderImageView;
@property (weak, nonatomic) IBOutlet UILabel *detailsLabel;
@property (weak, nonatomic) IBOutlet UILabel *serversNameLabel;
@property (weak, nonatomic) IBOutlet UILabel *payLabel;
@property (weak, nonatomic) IBOutlet UIButton *cancleButton;
@property (weak, nonatomic) IBOutlet UILabel *technicianLabel;
@property (weak, nonatomic) IBOutlet UILabel *phoneLabel;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *detailsHeight;
@property (weak, nonatomic) IBOutlet UILabel *phoneTitleLabel;
@property (weak, nonatomic) IBOutlet UIImageView *rigthImageView;
@property (weak, nonatomic) IBOutlet UILabel *nameTitleLabel;

- (void)setUpUserInterfaceWith:(OrderCarWashModel *)model;
+ (CGFloat)cellHeight:(OrderCarWashModel *)model;
@end
