//
//  EdtingCarsInforView.m
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import "EdtingCarsInforView.h"


@implementation EdtingCarsInforView

-(void)willMoveToSuperview:(UIView *)newSuperview{
//    self.carColorTextField.moveView=self.vc.view;
//    self.carNumberTextField.moveView=self.vc.view;
//    self.carOilTextField.moveView=self.vc.view;
//    self.carOutPutTextField.moveView=self.vc.view;
//    self.carRunTextField.moveView=self.vc.view;
//    self.startTimeTextField.moveView=self.vc.view;
    self.carColorTextField.moveView=self;
    self.carNumberTextField.moveView=self;
    self.carOilTextField.moveView=self;
    self.carOutPutTextField.moveView=self;
    self.carRunTextField.moveView=self;
    self.startTimeTextField.moveView=self;
    _carOutPutTextField.keyboardType=UIKeyboardTypeDecimalPad;
    _carOilTextField.keyboardType=UIKeyboardTypeDecimalPad;
    _carRunTextField.keyboardType=UIKeyboardTypeDecimalPad;
}

- (void)setUpUserInterfaceWith:(carModel *)model{
    self.model = model;
    if (model.engines!=nil) {
        _carOutPutTextField.text =[NSString stringWithFormat:@"%@",model.engines] ;
    }
    if (model.fuel!=nil) {
        _carOilTextField.text =[NSString stringWithFormat:@"%@",model.fuel] ;
    }
    if (model.drange!=nil) {
        _carRunTextField.text =[NSString stringWithFormat:@"%@",model.drange] ;
    }
    if (model.date!=nil) {
        _startTimeTextField.text =model.date ;
    }
    if (model.plateNumber!=nil) {
        _carNumberTextField.text =model.plateNumber ;
    }
    if (model.color!=nil) {
        _carColorTextField.text =model.color ;
    }
//    
//    _carOutPutTextField.text =[NSString stringWithFormat:@"%@",model.fuel];
//    _carRunTextField.text =[NSString stringWithFormat:@"%@",model.drange];
//    _startTimeTextField.text = model.date;
//    _carNumberTextField.text=model.plateNumber;
//    _carColorTextField.text=model.color;
    [_nameButton setTitle:model.cbname forState:0];
}

- (IBAction)pickDate:(UIButton *)sender {
    [self.vc datePickViewSelector];
}


- (IBAction)pressNameButton:(id)sender {
#warning 点击名字按钮出发事件
    [self endEditing:YES];
    
}

@end
