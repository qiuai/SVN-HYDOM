//
//  carManagerCell.m
//  HD_Car
//
//  Created by xingso on 15/7/28.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "carManagerCell.h"
#import "carModel.h"
#import "HDApiUrl.h"
#import "UIImageView+WebCache.h"
@interface carManagerCell()
@end
@implementation carManagerCell



-(void)awakeFromNib{
    [self.defaultCarView addTapGestureRecognizerWithTarget:self action:@selector(setDefault:)];
    [self.deleteView addTapGestureRecognizerWithTarget:self action:@selector(deleteCarModel:)];
    [self.updateView addTapGestureRecognizerWithTarget:self action:@selector(updateCarModel:)];
    self.isDefault=NO;
}


-(void)setDefault:(UIGestureRecognizer*)gez{
    if (_isDefault==YES) {
        return;
    }
  carManagerCell* cell=[UtilityMethod getTableViewCell:gez.view class:[carManagerCell class]];
    [self.viewController performSelector:self.changeDefaultMethod withObject:cell];
}


-(void)deleteCarModel:(UIGestureRecognizer*)gez{
//    UITableViewCell* cell=(UITableViewCell *)gez.view.superview.superview.superview;
     carManagerCell* cell=[UtilityMethod getTableViewCell:gez.view class:[carManagerCell class]];
    if (self.viewController!=nil&&self.deleteMethod!=nil) {
        //发送消息给 viewController 并传递cell
        [self.viewController performSelector:self.deleteMethod withObject:cell];
    }
}

-(void)updateCarModel:(UIGestureRecognizer*)gez{
//    UITableViewCell* cell=(UITableViewCell *)gez.view.superview.superview.superview;
     carManagerCell* cell=[UtilityMethod getTableViewCell:gez.view class:[carManagerCell class]];
    if (self.viewController!=nil&&self.updateMethod!=nil) {
        //发送消息给 viewController 并传递cell
        [self.viewController performSelector:self.updateMethod withObject:cell];
    }
}

//改变默认 图标
-(void)changeDefault:(BOOL)bl{
    if (bl) {
        self.isDefault=YES;
        [self.defaultImageView setImage:[UIImage imageNamed:@"redSingle1"]];
    }
    else{
        self.isDefault=NO;
        [self.defaultImageView setImage:[UIImage imageNamed:@"redSingle2"] ];
    }
}

//数据 model
-(void)initWithModel:(carModel* )carmodel{
    [self.carImageView sd_setImageWithURL:imageURLWithPath(carmodel.cbimageURL) placeholderImage:[UIImage imageNamed:FillImage]];
    self.carName.text=carmodel.cbname;
    self.carModelText.text=carmodel.cmname;
    self.carNumberText.text=carmodel.plateNumber;
    self.carKMtext.text=[NSString stringWithFormat:@"%@",carmodel.drange];
    self.carRunwayText.text=carmodel.date;
    self.carColorText.text=carmodel.color;
    self.carOutPutText.text= [NSString stringWithFormat:@"%@",carmodel.engines];
    self.carWearText.text=[NSString stringWithFormat:@"%@",carmodel.fuel];
    
    if ([carmodel.defaultCar integerValue]==1) {
        [self.defaultImageView setImage:[UIImage imageNamed:@"redSingle1"] ];
        self.isDefault=YES;
    }
    else{ [self.defaultImageView setImage:[UIImage imageNamed:@"redSingle2"] ];
       self.isDefault=NO;
    }
}

@end
