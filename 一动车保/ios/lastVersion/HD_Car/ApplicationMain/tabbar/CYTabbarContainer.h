//
//  CYTabbarContainer.h
//  tabbarContainer
//
//  Created by cc on 15/5/9.
//  Copyright (c) 2015年 cc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CYTabbarContainer : UIViewController
-(void)showTabbar;
-(void)hiddenTabbar;

#pragma -mark 点击切换事件
-(void)changeControllerWithBtnTag:(NSInteger )tag;
@end
