//
//  YC_VipView.m
//  HD_Car
//
//  Created by hydom on 9/11/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_VipView.h"
@interface YC_VipView()
@property (weak, nonatomic) IBOutlet UIView *payTypeView1;
@property (weak, nonatomic) IBOutlet UIView *payTypeView2;
@property (weak, nonatomic) IBOutlet UIView *payTypeView3;
@property (weak, nonatomic) IBOutlet UIView *payTypeView4;
@property(weak,nonatomic)UIImageView* payPointer;
@end


@implementation YC_VipView


-(void)awakeFromNib
{
    [self.payTypeView1 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTypeView1.tag=21;
    [self.payTypeView2 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTypeView2.tag=22;
    [self.payTypeView3 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTypeView3.tag=23;
    [self.payTypeView4 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTypeView4.tag=24;
    
    self.payStyle=@"2";
    self.payPointer=(UIImageView*)self.payTypeView1.subviews[0];
}

-(void)changePay:(UIGestureRecognizer* )gez{
    UIView* view=gez.view;
    UIImageView* imageV=(UIImageView*)view.subviews[0];
    if (self.payPointer!=nil) {
        self.payPointer.image=[UIImage imageNamed:@"redSingle2"];
    }
    imageV.image=[UIImage imageNamed:@"redSingle1"];
    self.payPointer=imageV;
    switch (view.tag) {
        case 21:
            self.payStyle=@"2";
//            self.payStyleDescription=@"支付宝";
            break;
        case 22:
            self.payStyle=@"4";
//            self.payStyleDescription=@"微信支付";
            break;
        case 23:
            self.payStyle=@"3";
//            self.payStyleDescription=@"银联支付";
            break;
        default:
            self.payStyle=@"1";
//            self.payStyleDescription=@"会员卡支付";
            break;
    }
}


/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
