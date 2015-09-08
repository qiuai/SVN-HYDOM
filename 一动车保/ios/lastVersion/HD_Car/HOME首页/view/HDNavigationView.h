//
//  HDNavigationView.h
//  HD_InsuranceCar
//
//  Created by cc on 15/6/13.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HDNavigationView : UIView

/**点击左边*/
-(void)tapLeftViewWithImageName:(NSString*)imageN tapBlock:(void(^)(void))block;

/**点击右边*/
-(void)tapRightViewWithImageName:(NSString*)text tapBlock:(void(^)(void))block;
//标题
+(instancetype)navigationViewWithTitle:(NSString*)title;
@end
