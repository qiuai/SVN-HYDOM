//
//  CYtabber.h
//  tabbarContainer
//
//  Created by cc on 15/5/9.
//  Copyright (c) 2015年 cc. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void (^clickMethod)(NSInteger btnTag);
@interface CYtabbar : UIView

+(instancetype)tabbarInitWithClickBlock:(clickMethod)clickm;
/*点击调用事件*/
-(void)tabbarDidClickBlock:(clickMethod)clickB;

/**切换到上次选中*/
-(void)changeLastSelectTabbar;

/**切换选中for tag*/
-(void)changeTabbarByTag:(NSInteger)tag;
@end
