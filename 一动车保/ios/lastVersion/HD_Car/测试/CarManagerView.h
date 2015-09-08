//
//  CarManagerView.h
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CarManagerCellModel.h"
@interface CarManagerView : UIView
@property (weak, nonatomic) IBOutlet UIImageView *carImageView;
@property (weak, nonatomic) IBOutlet UILabel *carNameLabel;
@property (weak, nonatomic) IBOutlet UILabel *carVersionLabel;
@property (weak, nonatomic) IBOutlet UILabel *carOilLabel;
@property (weak, nonatomic) IBOutlet UILabel *carRunLabel;
@property (weak, nonatomic) IBOutlet UILabel *carOutPutLabel;
@property (weak, nonatomic) IBOutlet UILabel *startTimeLabel;

- (void)setUpUserInterfaceWith:(CarManagerCellModel *)model;
@end
