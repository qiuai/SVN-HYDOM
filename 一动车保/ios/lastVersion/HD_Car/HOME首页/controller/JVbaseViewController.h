//
//  JVbaseViewController.h
//  HD_Car
//
//  Created by xingso on 15/8/11.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HDNavigationView.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "MBProgressHUD.h"
#import "UIImageView+WebCache.h"
@interface JVbaseViewController : UIViewController
@property(nonatomic,strong)HDNavigationView* navView;
@property(nonatomic,strong)MBProgressHUD *HUD;

/**viewController 标题*/
-(void)initNavViewWithtitle:(NSString*)title;

@end
