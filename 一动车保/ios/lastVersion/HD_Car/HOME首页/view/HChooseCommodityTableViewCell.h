//
//  HChooseCommodityTableViewCell.h
//  HD_Car
//
//  Created by hydom on 15-7-31.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "YC_MarketModel.h"
@interface HChooseCommodityTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *leftImageView;
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;
@property (weak, nonatomic) IBOutlet UILabel *buyNumLabel;
@property (weak, nonatomic) IBOutlet UILabel *priceLabel;
@property (weak, nonatomic) IBOutlet UILabel *commentNumLabel;

@property (nonatomic, strong) YC_MarketModel * markeModel;

@end
