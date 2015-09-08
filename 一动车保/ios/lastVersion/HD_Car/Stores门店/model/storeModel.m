//
//  storeModel.m
//  HD_Car
//
//  Created by xingso on 15/7/21.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "storeModel.h"

@implementation storeModel
/*Kvc*/
-(void)storeModelWithDict:(NSDictionary*)dict{
    [self setValuesForKeysWithDictionary:dict];
}

/**KVC专用   当不匹配的时候调用*/
-(void)setValue:(id)value forUndefinedKey:(NSString *)key{
    if ([key isEqualToString:@"sclist"]) {
        NSMutableArray* array=[NSMutableArray array];
        for (NSString *str in value) {
            [array addObject:str];
        }
        self.servicesArray=array;
    }
}


/**dict to model */
+(instancetype)storeModelWithDict:(NSDictionary*)dict{
    storeModel* model=[[storeModel alloc]init];
    [model storeModelWithDict:dict];
    return model;
}
@end
