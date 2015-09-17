//
//  JVshowKeyboardManger.h
//  HD_Car
//
//  Created by xingso on 15/9/11.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "JVshowkeyboardTextFiled.h"
@interface JVshowKeyboardManger : NSObject

+(instancetype)shareClass;


/**添加通知*/
+(void)addNotificationWithTextFiled:(JVshowkeyboardTextFiled*)textFiled;

/**移除通知*/
+(void)removeNotificationWithTextFiled:(JVshowkeyboardTextFiled*)textFiled;

@end
