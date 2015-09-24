//
//  AppDelegate.h
//  HD_Car
//
//  Created by xingso on 15/6/26.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "WXApi.h"
@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (weak, nonatomic) id <WXApiDelegate>playViewController;

@end

