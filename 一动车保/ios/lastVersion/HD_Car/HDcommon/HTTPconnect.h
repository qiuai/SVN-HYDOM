//
//  HTTPconnect.h
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/24.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface HTTPconnect : NSObject
/**发送GET*/
+(void)getHttpWithUrl:(NSString*)url parameters:(id)parameters success:(void (^)(id responseObject))success  failure:(void (^)(NSError *error))failure;

/**发送POST*/
+(void)postHttpWithUrl:(NSString*)url parameters:(id)parameters success:(void (^)(id responseObject))success  failure:(void (^)(NSError *error))failure;


/**发送GET 并解析状态码*/
+(void)sendGETWithUrl:(NSString*)url parameters:(id)parameters success:(void (^)(id responseObject))success  failure:(void (^)(NSError *error))failure;

/**发送POST 并解析状态码*/
+(void)sendPOSTHttpWithUrl:(NSString*)url parameters:(id)parameters success:(void (^)(id responseObject))success  failure:(void (^)(NSError *error))failure;


@end
