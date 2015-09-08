//
//  loginViewController.h
//  HD_Car
//
//  Created by xingso on 15/7/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVshowkeyboardTextFiled.h"
typedef void (^loginBlock)(void);

@interface loginViewController : UIViewController

/**登陆成功回调*/
@property(nonatomic,copy)loginBlock successLogin;
@end
