//
//  HDNavigationView.h
//  HD_InsuranceCar
//
//  Created by cc on 15/6/13.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#define SCREEN_WIDTH  [[UIScreen mainScreen] bounds].size.width
#define SCREEN_HEIGTHT [[UIScreen mainScreen] bounds].size.height
#define ShareBackColor [UIColor colorWithRed:arc4random() % 250 / 255.0f green:arc4random() % 250 / 255.0f blue:arc4random() % 250 / 255.0f alpha:1]
#define CGRM(_X,_Y,_W,_H) CGRectMake(_X, _Y, _W, _H)
#define COLOR(_R,_G,_B,_A) [UIColor colorWithRed:_R / 255.0f green: _G / 255.0f blue:_B / 255.0f alpha:_A]

#import <UIKit/UIKit.h>

@interface HDNavigationView : UIView
/**点击搜索框回调*/
-(void)tapSearchViewWithBlock:(void (^)(void))block;
/**点击左边*/
-(void)tapLeftViewWithImageName:(NSString*)imageN tapBlock:(void(^)(void))block;
//标题
+(instancetype)navigationViewWithTitle:(NSString*)title;
@end
