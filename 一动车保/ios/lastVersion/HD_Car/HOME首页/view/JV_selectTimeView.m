//
//  JV_selectTimeView.m
//  HD_Car
//
//  Created by xingso on 15/8/8.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JV_selectTimeView.h"
#import "MBProgressHUD.h"
@interface JV_selectTimeView()

@property (weak, nonatomic) IBOutlet UILabel *time1;
@property (weak, nonatomic) IBOutlet UILabel *time2;
@property (weak, nonatomic) IBOutlet UILabel *time3;
@property (weak, nonatomic) IBOutlet UILabel *time4;
@property (weak, nonatomic) IBOutlet UILabel *time5;

@property(weak,nonatomic)UILabel* seLabel;

@property(strong,nonatomic)MBProgressHUD* HUD;
@end

@implementation JV_selectTimeView

-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.window];
    }
    return _HUD;
}

-(void)awakeFromNib{
    [self addboder:self.time1];
    [self addboder:self.time2];
    [self addboder:self.time3];
    [self addboder:self.time4];
    [self addboder:self.time5];
    self.cancel.layer.cornerRadius = 2;
    self.trueButton.layer.cornerRadius = 2;
    self.cancel.backgroundColor = SUBMITCOLOR;
    self.trueButton.backgroundColor = SUBMITCOLOR;
    [self.cancel addTarget:self action:@selector(cancelTap) forControlEvents:UIControlEventTouchUpInside];
    [self.trueButton addTarget:self action:@selector(selectTImeTap) forControlEvents:UIControlEventTouchUpInside];
}

//添加边框圆角 手势
-(void)addboder:(UILabel*)lb{
    lb.layer.cornerRadius = 8;
    lb.layer.masksToBounds = YES;
    lb.layer.borderWidth = 1;
    lb.layer.borderColor = COLOR(225, 225, 225, 1.0).CGColor;
    [lb addTapGestureRecognizerWithTarget:self action:@selector(selectThisTime:)];
}


-(void)selectThisTime:(UIGestureRecognizer*)gez{
    if (self.seLabel!=nil) {
    self.seLabel.layer.borderColor = COLOR(225, 225, 225, 1.0).CGColor;
    }
    gez.view.layer.borderColor=[UIColor redColor].CGColor;
    self.seLabel=(UILabel*)gez.view;
}

-(void)selectTImeTap{
    if (self.seLabel==nil) {
        [self.window addSubview:self.HUD];
        self.HUD.labelText =@"请选择时间段";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2);
        } completionBlock:^{
            [self.HUD removeFromSuperview];
        }];
    }else{
        if (self.selectTimeBlock!=nil) {
            self.selectTimeBlock(self.seLabel.text);
        }
        [self setHidden:YES];
    }
}

-(void)cancelTap{
    [self setHidden:YES];
}
@end
