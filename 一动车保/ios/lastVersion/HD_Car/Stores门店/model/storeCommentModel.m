//
//  storeCommentModel.m
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/23.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "storeCommentModel.h"

@implementation storeCommentModel

/*Kvc*/
-(void)dict:(NSDictionary*)dict{
    [self setValuesForKeysWithDictionary:dict];
}

/**KVC专用   当不匹配的时候调用*/
-(void)setValue:(id)value forUndefinedKey:(NSString *)key{

   
}
@end
