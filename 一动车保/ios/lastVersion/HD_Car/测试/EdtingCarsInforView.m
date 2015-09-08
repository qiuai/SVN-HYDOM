//
//  EdtingCarsInforView.m
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import "EdtingCarsInforView.h"

@implementation EdtingCarsInforView

- (void)setUpUserInterfaceWith:(CarManagerCellModel *)model{
#warning 设置图片
//    _carImageView setImage:<#(UIImage *)#>
    self.model = model;
    _carOilTextField.text = model.carOil;
    _carOutPutTextField.text = model.carOutPut;
    _carRunTextField.text = model.carRun;
    _startTimeTextField.text = model.carStartTime;
    [_nameButton setTitle:model.carName forState:0];
}
- (IBAction)pressSaveButton:(id)sender {
#warning 保存时需赋值self.model.carPic model.carVersion
    self.model.carName = self.nameButton.titleLabel.text;
    self.model.carOutPut = _carOutPutTextField.text;
    self.model.carOil = self.carOilTextField.text;
    self.model.carRun =  self.carRunTextField.text;
    self.model.carStartTime = self.startTimeTextField.text;
    
    [self.pushVC.navigationController popViewControllerAnimated:YES];
}
- (IBAction)pressNameButton:(id)sender {
#warning 点击名字按钮出发事件
    [self endEditing:YES];
    
}

@end
