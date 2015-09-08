//
//  CarManagerView.m
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import "CarManagerView.h"

@implementation CarManagerView

- (void)setUpUserInterfaceWith:(CarManagerCellModel *)model{
#warning 设置车图片
//    self.carImageView setImage:
    self.carNameLabel.text = model.carName;
    self.carOilLabel.text = model.carOil;
    self.carOutPutLabel.text = model.carOutPut;
    self.carRunLabel.text = model.carRun;
    self.carVersionLabel.text = model.carVersion;
    self.startTimeLabel.text = model.carStartTime;
    


}

@end
