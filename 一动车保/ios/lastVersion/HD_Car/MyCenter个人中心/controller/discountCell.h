//
//  discountCell.h
//  
//
//  Created by hydom on 8/13/15.
//
//

#import <UIKit/UIKit.h>
#import "discountModel.h"

@interface discountCell : UITableViewCell

@property (nonatomic, strong) discountModel * model;
@property (nonatomic, strong) UIImageView * cellImageView;
@property (nonatomic, strong) UIImageView * cellPastImageView;
@end
