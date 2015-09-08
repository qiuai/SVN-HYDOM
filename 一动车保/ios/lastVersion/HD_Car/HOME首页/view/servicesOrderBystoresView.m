//
//  servicesOrderBystoresView.m
//  HD_Car
//
//  Created by xingso on 15/7/22.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "servicesOrderBystoresView.h"
@interface servicesOrderBystoresView()

@property (weak, nonatomic) IBOutlet UIView *line1;
@property (weak, nonatomic) IBOutlet UIView *line2;
@property (weak, nonatomic) IBOutlet UIView *line3;
@property (weak, nonatomic) IBOutlet UIView *line4;
@end
@implementation servicesOrderBystoresView

/**添加虚线*/
-(void)addline:(UIView*)view{
    UIImage* backgroundImage=[UIImage imageNamed:@"HDxuxian"];
    UIColor *backgroundColor = [UIColor colorWithPatternImage:backgroundImage];
    view.backgroundColor=backgroundColor;
}


-(void)awakeFromNib{
    // 初始化代码
    [self addline:self.line1];
    [self addline:self.line2];
    [self addline:self.line3];
    [self addline:self.line4];
    self.personText.tag=10;
    self.phoneText.tag=11;
    self.personText.textAlignment=2;
    self.phoneText.textAlignment=2;
    [self.personText setWordsMaxLenght:4];
    [self.phoneText setWordsMaxLenght:11];
    self.phoneText.keyboardType=UIKeyboardTypePhonePad;
}


-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event{
    if ([self.personText isFirstResponder]) {
        [self.personText resignFirstResponder];
    }
    if ([self.phoneText isFirstResponder]) {
        [self.phoneText resignFirstResponder];
    }
}

@end
