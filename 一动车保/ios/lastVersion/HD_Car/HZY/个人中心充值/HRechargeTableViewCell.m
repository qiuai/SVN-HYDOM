//
//  HRechargeTableViewCell.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "HRechargeTableViewCell.h"

@implementation HRechargeTableViewCell

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (void)setUpUserInterface:(ListModel *)model{
    
    self.typeLabel.text = model.ftext;
    self.timaLabel.text = model.fdate;
    self.moneyLabel.text = model.fmoney;


}
@end
