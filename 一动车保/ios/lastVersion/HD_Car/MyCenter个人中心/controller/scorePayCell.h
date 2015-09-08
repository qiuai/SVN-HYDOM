//
//  scorePayCell.h
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "discountModel.h"
@interface scorePayCell : UITableViewCell

@property (nonatomic, strong) NSString * type;
@property (nonatomic, strong) discountModel * model;
@property (nonatomic, strong) UIImageView * cellImageView;


@end
