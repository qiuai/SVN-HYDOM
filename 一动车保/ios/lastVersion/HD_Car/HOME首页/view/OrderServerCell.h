//
//  OrderServerCell.h
//  HD_Car
//
//  Created by hydom on 7/20/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
//#import "OrderServerModel.h"
@interface OrderServerCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *titile;
@property (weak, nonatomic) IBOutlet UILabel *sellNumber;
@property (weak, nonatomic) IBOutlet UILabel *price;
@property (weak, nonatomic) IBOutlet UILabel *messages;
@property (weak, nonatomic) IBOutlet UIImageView *headImage;
//@property (nonatomic, strong) OrderServerModel * orderServerModer;
@end
