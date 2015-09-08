//
//  EdtingCarsInforView.h
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CarManagerCellModel.h"

@interface EdtingCarsInforView : UIView
@property (weak, nonatomic) IBOutlet UIImageView *carImageView;
@property (weak, nonatomic) IBOutlet UITextField *carOutPutTextField;
@property (weak, nonatomic) IBOutlet UITextField *carOilTextField;
@property (weak, nonatomic) IBOutlet UITextField *carRunTextField;
@property (weak, nonatomic) IBOutlet UITextField *startTimeTextField;
@property (nonatomic,weak)UIViewController * pushVC;
@property (nonatomic,weak)CarManagerCellModel * model;
@property (weak, nonatomic) IBOutlet UIButton *saveButton;
@property (weak, nonatomic) IBOutlet UIButton *nameButton;

- (void)setUpUserInterfaceWith:(CarManagerCellModel *)model;
@end
