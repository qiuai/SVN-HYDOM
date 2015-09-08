//
//  UIView+MJ.h
//  传智微博
//
//  Created by teacher on 14-6-6.
//  Copyright (c) 2014年 itcast. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (MJ)
@property (nonatomic, assign) CGFloat x;
@property (nonatomic, assign) CGFloat y;
@property (nonatomic, assign) CGFloat centerX;
@property (nonatomic, assign) CGFloat centerY;
@property (nonatomic, assign) CGFloat width;
@property (nonatomic, assign) CGFloat height;
@property (nonatomic, assign) CGSize size;
//设置边框
-(void)settingBorderWithPX:(CGFloat)width colorR:(CGFloat)r g:(CGFloat)g b:(CGFloat)b;
/**添加Tap手势*/
-(void)addTapGestureRecognizerWithTarget:(id)target action:(SEL)action;
@end
