//
//  YC_LimitGoodsCell.h
//  HD_Car
//
//  Created by hydom on 8/8/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "YC_MarketModel.h"
@interface YC_LimitGoodsCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *titleLabel;
@property (weak, nonatomic) IBOutlet UILabel *priceLabel;
@property (weak, nonatomic) IBOutlet UILabel *buyNumLabel;
@property (weak, nonatomic) IBOutlet UILabel *totalLabel;
@property (weak, nonatomic) IBOutlet UIImageView *leftImageView;
@property (weak, nonatomic) IBOutlet UILabel *buyLabel;


@property (nonatomic, strong) YC_MarketModel * markeModel;

@end
