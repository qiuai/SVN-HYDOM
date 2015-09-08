//
//  EdtingCarsInforView.h
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "carModel.h"
#import "JVshowkeyboardTextFiled.h"
#import "EdtingCarInforViewController.h"
@interface EdtingCarsInforView : UIView
@property (weak, nonatomic) IBOutlet UIView *comeBackCarView;
@property (weak, nonatomic) IBOutlet UIImageView *carImageView;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *carOutPutTextField;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *carOilTextField;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *carRunTextField;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *startTimeTextField;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *carNumberTextField;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *carColorTextField;
@property (nonatomic,weak)UIViewController * pushVC;
@property (nonatomic,weak)carModel * model;
@property (weak, nonatomic) IBOutlet UIButton *nameButton;
@property (weak, nonatomic)EdtingCarInforViewController * vc;

- (void)setUpUserInterfaceWith:(carModel *)model;
@end
