//
//  YC_LimitGoodsCell.m
//  HD_Car
//
//  Created by hydom on 8/8/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_LimitGoodsCell.h"

@implementation YC_LimitGoodsCell

- (void)awakeFromNib {
    // Initialization code
    self.buyLabel.layer.masksToBounds = YES;
    self.buyLabel.layer.cornerRadius = 8;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
