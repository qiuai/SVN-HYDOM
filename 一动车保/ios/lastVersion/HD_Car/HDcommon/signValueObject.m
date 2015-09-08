//
//  signValueObject.m
//  HD_Car
//
//  Created by xingso on 15/7/15.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "signValueObject.h"


static signValueObject *_sharedClass;
@interface signValueObject()

@property(assign,nonatomic)BOOL setCar;

@end

@implementation signValueObject

/**设置选择的  服务类型 全局*/
-(void)setSelectGlobalServicesModel:(globalServicesModel*)model{
    [self.saveDictionary setObject:model forKey:@"selectGlobalService"];
}

/**获取选择的  服务类型 全局*/
-(globalServicesModel*)getSelectGlobalServiceModel{
 return [self.saveDictionary objectForKey:@"selectGlobalService"];
    
}


+(instancetype)shareSignValue{
    static dispatch_once_t token;    
    dispatch_once(&token,^{
        _sharedClass = [[signValueObject alloc] init];
        _sharedClass.saveDictionary=[NSMutableDictionary dictionary];
        _sharedClass.servicesModelArray=[NSMutableArray array];
        _sharedClass.setCar=NO;
    });
    return _sharedClass;
}


//是否设置默认车型
-(void)SetDefaultCar:(BOOL)bl{
    self.setCar=bl;
}



-(BOOL)getIsDefaultCar{
    return self.setCar;
}

@end
