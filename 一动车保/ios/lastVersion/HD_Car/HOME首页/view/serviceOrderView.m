//
//  serviceOrderView.m
//  HD_Car
//
//  Created by xingso on 15/7/20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "serviceOrderView.h"
@interface serviceOrderView()
@property (weak, nonatomic) IBOutlet UIView *line1;
@property (weak, nonatomic) IBOutlet UIView *line2;
@property (weak, nonatomic) IBOutlet UIView *line3;
@property (weak, nonatomic) IBOutlet UIView *line4;

@end
@implementation serviceOrderView

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
}

@end
