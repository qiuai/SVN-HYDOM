//
//  JVwashCarInfoView.m
//  HD_Car
//
//  Created by xingso on 15/7/30.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVwashCarInfoView.h"
@interface JVwashCarInfoView()
@property (weak, nonatomic) IBOutlet UIView *line1;
@property (weak, nonatomic) IBOutlet UIView *line2;
@property (weak, nonatomic) IBOutlet UIView *line3;
@property (weak, nonatomic) IBOutlet UIView *line4;
@property (weak, nonatomic) IBOutlet UIView *line5;
@property (weak, nonatomic) IBOutlet UIView *line6;

@property (weak, nonatomic) IBOutlet UIView *payTapView1;
@property (weak, nonatomic) IBOutlet UIView *payTapView2;
@property (weak, nonatomic) IBOutlet UIView *payTapView3;
@property (weak, nonatomic) IBOutlet UIView *washTap1;
@property (weak, nonatomic) IBOutlet UIView *washTap2;


@property(weak,nonatomic)UIImageView* payPointer;
@property(weak,nonatomic)UIImageView* washStylePointer;

@end
@implementation JVwashCarInfoView
/**添加虚线*/
-(void)addline:(UIView*)view{
    UIImage* backgroundImage=[UIImage imageNamed:@"HDxuxian"];
    UIColor *backgroundColor = [UIColor colorWithPatternImage:backgroundImage];
    view.backgroundColor=backgroundColor;
}


/**添加切换手势*/
-(void)addTapforChange{
    [self.payTapView1 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView1.tag=21;
    [self.payTapView2 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView2.tag=22;
    [self.payTapView3 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView3.tag=23;
    [self.washTap1 addTapGestureRecognizerWithTarget:self action:@selector(changeWash:)];
    self.washTap1.tag=11;
    [self.washTap2 addTapGestureRecognizerWithTarget:self action:@selector(changeWash:)];
    self.washTap2.tag=12;
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
            self.payStyleDescription=@"支付宝";
            break;
        case 22:
            self.payStyle=@"4";
            self.payStyleDescription=@"微信支付";
            break;
        default:
            self.payStyle=@"1";
            self.payStyleDescription=@"会员卡支付";
            break;
    }
}

-(void)changeWash:(UIGestureRecognizer* )gez{
    UIView* view=gez.view;
    UIImageView* imageV=(UIImageView*)view.subviews[0];
    if (self.washStylePointer!=nil) {
        self.washStylePointer.image=[UIImage imageNamed:@"redSingle2"];
    }
    imageV.image=[UIImage imageNamed:@"redSingle1"];
    self.washStylePointer=imageV;
    switch (view.tag) {
        case 11:
            self.washStyle=@"1";
            self.washStyleDescription=@"清洗车身";
            break;
            
        default:
            self.washStyle=@"2";
            self.washStyleDescription=@"内外清洗";
            break;
    }
}


-(void)awakeFromNib{
    // 初始化代码
    [self addline:self.line1];
    [self addline:self.line2];
    [self addline:self.line3];
    [self addline:self.line4];
    [self addline:self.line5];
    [self addline:self.line6];
    //手机号输入限制
    self.phoneNumberTextField.wordsMaxLenght=11;
    self.payStyle=@"2";
    self.payStyleDescription=@"支付宝";
    self.washStyle=@"1";
    self.washStyleDescription=@"清洗车身";
    self.payPointer=(UIImageView*)self.payTapView1.subviews[0];
    self.washStylePointer=(UIImageView*)self.washTap1.subviews[0];
    [self addTapforChange];
}



@end
