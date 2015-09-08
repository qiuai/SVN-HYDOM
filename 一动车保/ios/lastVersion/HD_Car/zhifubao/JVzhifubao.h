//
//  JVzhifubao.h
//  HD_Car
//
//  Created by xingso on 15/8/15.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "JVcommonProduct.h"
@interface JVzhifubao : NSObject



+(void)JVzfbWith:(JVcommonProduct*)product success:(void (^)(void))success failure:(void(^)(void))failure;
@end
