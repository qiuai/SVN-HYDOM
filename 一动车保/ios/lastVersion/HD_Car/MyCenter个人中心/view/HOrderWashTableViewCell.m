//
//  HOrderWashTableViewCell.m
//  HD_Car
//
//  Created by hydom on 15-8-4.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "HOrderWashTableViewCell.h"
#import "UtilityMethod.h"


@implementation HOrderWashTableViewCell

- (void)awakeFromNib {
    
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)setUpUserInterfaceWith:(OrderCarWashModel *)model{
    self.orderNum.text = model.orderNum;
    switch (model.status) {
        case 1:self.statusLabel.text = @"派单中";[self hiddenSubViews]; break;
        case 2:self.statusLabel.text = @"路途中"; self.cancleButton.hidden = YES;break;
        case 3:self.statusLabel.text = @"服务中"; self.cancleButton.hidden = YES;break;
        case 4:self.statusLabel.hidden = YES;[self.cancleButton setTitle:@"评论" forState:0];[self hiddenSubViews]; break;
        default:
            break;
    }
    self.payLabel.text = model.pay;
    self.detailsLabel.text = model.Details;
    if (model.status != 1) {
        self.technicianLabel.text = model.technicianName;
        self.phoneLabel.text = model.phone;
    }
    self.cancleButton.layer.cornerRadius = 5;
    self.cancleButton.layer.borderWidth = 1;
    self.cancleButton.layer.borderColor = self.cancleButton.titleLabel.textColor.CGColor;
    self.cancleButton.layer.masksToBounds = YES;
    self.detailsHeight.constant = [UtilityMethod getHeightToLongLabel:model.Details width:SCREEN_WIDTH - 94];
}
+ (CGFloat)cellHeight:(OrderCarWashModel *)model{
    if (model.status == 1 || model.status == 4 ) {
        return 149;
        
    }else{
        return 179;
    }

}
- (void)hiddenSubViews{
    self.technicianLabel.hidden = YES;
    self.rigthImageView.hidden = YES;
    self.nameTitleLabel.hidden = YES;
    self.phoneLabel.hidden = YES;
    self.phoneTitleLabel.hidden = YES;
}

@end
