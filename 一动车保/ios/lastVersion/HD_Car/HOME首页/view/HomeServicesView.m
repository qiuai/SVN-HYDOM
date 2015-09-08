//
//  HomeServicesView.m
//  HD_Car
//
//  Created by xingso on 15/7/8.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "HomeServicesView.h"

@implementation HomeServicesView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(void)awakeFromNib{
    [self addClick:self.tapServicesView1];
    [self addClick:self.tapServicesView2];
    [self addClick:self.tapServicesView3];
    [self addClick:self.tapServicesView4];
    [self addClick:self.tapServicesView5];
    [self addClick:self.tapServicesView6];
    [self addClick:self.tapServicesView7];
    [self addClick:self.tapServicesView8];
}

-(void)addClick:(UIView*)view{
  static  NSInteger vTag=50;
    [view addTapGestureRecognizerWithTarget:self action:@selector(tapForService:)];
    view.tag=++vTag;
}

-(void)tapForService:(UIGestureRecognizer*)gez{
    NSInteger tag=gez.view.tag-50;
    [self.viewController performSelector:self.tapServicesViewWithtag withObject:[NSNumber numberWithInteger:tag]];
}
@end
