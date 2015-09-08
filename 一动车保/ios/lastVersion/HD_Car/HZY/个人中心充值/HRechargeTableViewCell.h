//
//  HRechargeTableViewCell.h
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ListModel.h"

@interface HRechargeTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *typeLabel;
@property (weak, nonatomic) IBOutlet UILabel *timaLabel;
@property (weak, nonatomic) IBOutlet UILabel *moneyLabel;
- (void)setUpUserInterface:(ListModel *)model;
@end
