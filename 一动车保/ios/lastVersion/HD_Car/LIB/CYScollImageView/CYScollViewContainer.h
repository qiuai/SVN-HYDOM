//
//  CYScollViewContainer.h
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/17.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CYScollViewContainer : UIViewController

//初始化方法
-(void)setImageArray:(NSArray*)array;

//点击 回调
@property(nonatomic,copy)void (^clickBlock)(NSInteger);
@end
