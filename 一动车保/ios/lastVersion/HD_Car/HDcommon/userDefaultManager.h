//
//  userDefaultManager.h
//  HD_Car
//
//  Created by xingso on 15/7/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "JVuserModel.h"

@interface userDefaultManager : NSObject
/**获取用户ID  token*/
+(NSDictionary*)getUserWithToken;
/**保存用户ID  token*/
+(void)setUser:(NSString*)userId Token:(NSString*)token;
/**注销 */
+(void)logout;

/**获取联系人  电话*/
+(NSDictionary*)getPersonNumber;
/**保存联系人  电话*/
+(void)setPerson:(NSString*)person number:(NSString*)number;

/**清空联系人电话*/
+(void)logoutPersonNumber;



/**获取用户信息*/
+(JVuserModel*)getUserModel;


/**保存用户信息*/
+(void)saveUserModel:(JVuserModel*)mo;



@end
