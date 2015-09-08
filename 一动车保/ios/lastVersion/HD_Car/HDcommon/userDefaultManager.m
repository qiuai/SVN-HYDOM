//
//  userDefaultManager.m
//  HD_Car
//
//  Created by xingso on 15/7/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "userDefaultManager.h"


static userDefaultManager *_sharedClass;
@interface userDefaultManager()
//用户名 token
@property(nonatomic,strong)NSDictionary* userTokenDict;

//用户名 头像
@property(nonatomic,strong)JVuserModel* user;

@end
@implementation userDefaultManager

+(void)creatShareUserDefault{
    static dispatch_once_t token;
    dispatch_once(&token,^{
        _sharedClass = [[userDefaultManager alloc] init];
    });
}



/**获取用户ID  token*/
+(NSDictionary*)getUserWithToken{
    [userDefaultManager creatShareUserDefault];
    if (_sharedClass.userTokenDict==nil) {
        NSUserDefaults *userDefaultes = [NSUserDefaults standardUserDefaults];
        NSString *myUserID = [userDefaultes stringForKey:@"myUserID"];
        NSString *myToken = [userDefaultes stringForKey:@"myToken"];
        if (myToken==nil||userDefaultes==nil) {
            _sharedClass.userTokenDict=nil;
        }else{
            NSDictionary* dict=[NSDictionary dictionaryWithObjects:@[myUserID,myToken] forKeys:@[@"uid",@"token"]];
            _sharedClass.userTokenDict=[dict copy];
        }
    }
    return _sharedClass.userTokenDict;
}

/**保存用户ID  token*/
+(void)setUser:(NSString*)userId Token:(NSString*)token{
    
    [userDefaultManager creatShareUserDefault];
    NSDictionary* d=[NSDictionary dictionaryWithObjectsAndKeys:userId,@"uid",token,@"token", nil];
    _sharedClass.userTokenDict=[d copy];
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    //存储时，除NSNumber类型使用对应的类型意外，其他的都是使用setObject:forKey:
    [userDefaults setObject:userId forKey:@"myUserID"];
    [userDefaults setObject:token forKey:@"myToken"];
    [userDefaults synchronize];
}

/**获取联系人  电话*/
+(NSDictionary*)getPersonNumber{
    NSUserDefaults *userDefaultes = [NSUserDefaults standardUserDefaults];
    NSString *myPerson = [userDefaultes stringForKey:@"myPerson"];
    NSString *myPhone = [userDefaultes stringForKey:@"myPhone"];
    if (myPerson==nil||myPhone==nil) {
        return  nil;
    }else{
        NSDictionary* dict=[NSDictionary dictionaryWithObjects:@[myPerson,myPhone] forKeys:@[@"myPerson",@"myPhone"]];
        return dict;
    }
    

}
/**保存联系人  电话*/
+(void)setPerson:(NSString*)person number:(NSString*)number{
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    //存储时，除NSNumber类型使用对应的类型意外，其他的都是使用setObject:forKey:
    [userDefaults setObject:person forKey:@"myPerson"];
    [userDefaults setObject:number forKey:@"myPhone"];
    [userDefaults synchronize];
}


/**清空联系人电话*/
+(void)logoutPersonNumber{
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults removeObjectForKey:@"myPerson"];
    [userDefaults removeObjectForKey:@"myPhone"];
    [userDefaults synchronize];
}




/**注销 */
+(void)logout{
    [userDefaultManager creatShareUserDefault];
    _sharedClass.userTokenDict=nil;
  NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults removeObjectForKey:@"myUserID"];
    [userDefaults removeObjectForKey:@"myToken"];
    [userDefaults synchronize];
}




/**获取用户信息*/
+(JVuserModel*)getUserModel{
    [userDefaultManager creatShareUserDefault];
    if (_sharedClass.user==nil) {
        return nil;
        }
    return _sharedClass.user;
}


/**保存用户信息*/
+(void)saveUserModel:(JVuserModel*)mo{
    [userDefaultManager creatShareUserDefault];
    _sharedClass.user=mo;
}

@end
