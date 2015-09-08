//
//  HOrderServersTableViewCell.m
//  HD_Car
//
//  Created by hydom on 15-8-4.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "HOrderServersTableViewCell.h"
#import <UIImageView+WebCache.h>
#import "UtilityMethod.h"

@implementation HOrderServersTableViewCell

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (void)setUpUserInterfaceWith:(OderServersModel *)model{
    [self setSubViews];
    
    self.orderNumLabel.text = model.orderNum;
    self.statusLabel.text = model.statusTitle;
    
    if (model.isServers) {
        [self.serverImageView sd_setImageWithURL:[NSURL URLWithString:model.serversImageUrl]];
        self.serversNameLabel.text = model.serversName;
        self.serversMoneyLabel.text = model.serversMoney;
    }else{
        self.commodityViewTop.constant = 1;
        self.serversView.hidden = YES;
    }
    self.payNumLabel.text = model.payMoney;
    if (model.status == Allocation) {
        self.technicianNameLabel.text = model.name;
        self.phoneLabel.text = model.phone;
    }else{
        self.technicianInforView.hidden = YES;
    }
    self.commodityViewHeight.constant = model.commodityArray.count * 80;
    CGFloat heigth = 0;
    for (Commodity * objc in model.commodityArray) {
        heigth = [self buildCommodityViewWith:objc y:heigth];
    }
    
}
#warning 配置各种状态
- (void)setSubViews{


}

- (CGFloat)buildCommodityViewWith:(Commodity *)model y:(CGFloat)y{
    
    UIView * view = [[UIView alloc] initWithFrame:CGRectMake(0, y, SCREEN_WIDTH, 80)];
    UIImageView * imageView = [[UIImageView alloc] initWithFrame:CGRectMake(8, 10, 70, 70)];
    [imageView sd_setImageWithURL:[NSURL URLWithString:model.imageUrl]];
    [view addSubview:imageView];
    
    UILabel * titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(86, 10, SCREEN_WIDTH - 94, [UtilityMethod getHeightToLongLabel:model.title width:SCREEN_WIDTH -94])];
    titleLabel.text = model.title;
    titleLabel.font = [UIFont systemFontOfSize:13];
    titleLabel.numberOfLines = 0;
    [view addSubview:titleLabel];
    
    UILabel * moneyLabel = [[UILabel alloc]initWithFrame:CGRectMake(86, 55, 80, 25)];
    moneyLabel.text = model.money;
    moneyLabel.textColor = self.statusLabel.textColor;
    moneyLabel.font = [UIFont systemFontOfSize:13];
    [view addSubview:moneyLabel];
    
    UILabel * numLabel = [[UILabel alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-88, 55, 80, 25)];
    numLabel.text = model.num;
    moneyLabel.font = [UIFont systemFontOfSize:13];
    [view addSubview:numLabel];
    
    [self.commodityView addSubview:view];
    return CGRectGetMaxY(view.frame);
}

+ (CGFloat)cellHeight:(OderServersModel *)model{
    
    CGFloat height = 65;
    if (model.status == Allocation) {
        height += 30;
    }
    if (model.isServers) {
        height += 25;
    }
    height += model.commodityArray.count * 80;
    
    return height;
}

@end
