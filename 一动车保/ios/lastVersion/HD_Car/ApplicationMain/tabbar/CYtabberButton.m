//
//  CYtabberButton.m
//  tabbarContainer
//
//  Created by cc on 15/5/9.
//  Copyright (c) 2015年 cc. All rights reserved.
//

#import "CYtabberButton.h"

@implementation CYtabberButton


// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.

-(instancetype)init{
  self=[super init];
  [self setBackgroundColor:ShareBackColor];
    return self;
}


-(void)changeState{
    if (self.selected) {
        self.selected=NO;
        [self setBackgroundColor:ShareBackColor];
    }else{
        self.selected=YES;
        [self setBackgroundColor:[UIColor whiteColor]];
    }

}
- (void)drawRect:(CGRect)rect {
    [super drawRect:rect];
    self.titleLabel.font=FONT12;
    self.titleLabel.textAlignment=NSTextAlignmentCenter;
    // Drawing code
}

-(CGRect)imageRectForContentRect:(CGRect)contentRect{
    CGFloat w=20.0;
    CGFloat h=20.0;
    CGFloat contentW=contentRect.size.width;
    CGFloat x=(contentW-w)/2.0;
    CGFloat y=8;
    return CGRectMake(x, y, w, h);
}
-(CGRect)titleRectForContentRect:(CGRect)contentRect{
    CGFloat w=contentRect.size.width;
    CGFloat h=10.0;
    CGFloat x=0;
    CGFloat y=32;
    return CGRectMake(x, y, w, h);
}


@end
