//
//  HTTPconnect.m
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/24.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "HTTPconnect.h"
#import "AFNetworking.h"


@implementation HTTPconnect

/**发送GET*/
+(void)getHttpWithUrl:(NSString*)url parameters:(id)parameters success:(void (^)(id responseObject))success  failure:(void (^)(NSError *error))failure{
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:url parameters:parameters success:^(AFHTTPRequestOperation *operation, id responseObject) {
        success(responseObject);
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        failure(error);
    }];
}
/**发送POST*/
+(void)postHttpWithUrl:(NSString*)url parameters:(id)parameters success:(void (^)(id responseObject))success  failure:(void (^)(NSError *error))failure{
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    manager.responseSerializer = [AFHTTPResponseSerializer serializer];
    [manager POST:url parameters:parameters success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        success(responseObject);
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        
        failure(error);
    }];
}



/**发送GET 并解析状态码*/
+(void)sendGETWithUrl:(NSString*)url parameters:(id)parameters success:(void (^)(id responseObject))success  failure:(void (^)(NSError *error))failure{

    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager GET:url parameters:parameters success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSString* str=[HTTPconnect returnCodeStringWithDictionary:responseObject];
        if ([str isEqualToString:@"success"]) {
            success(responseObject);
        }else{
            success(str);
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        _po(error);
        failure(error);
    }];

}

/**发送POST 并解析状态码*/
+(void)sendPOSTHttpWithUrl:(NSString*)url parameters:(id)parameters success:(void (^)(id responseObject))success  failure:(void (^)(NSError *error))failure{
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    [manager POST:url parameters:parameters success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSString* str=[HTTPconnect returnCodeStringWithDictionary:responseObject];
        if ([str isEqualToString:@"success"]) {
            success(responseObject);
        }else{
            success(str);
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        _po(error);
        failure(error);
    }];

}



//解析 返回状态码
+(NSString*)returnCodeStringWithDictionary:(NSDictionary*)responseObject{
    NSString* codeSt=[responseObject objectForKey:@"result"];
        NSInteger code=[codeSt intValue];
        NSString* str;
        switch (code) {
            case 001:
                str=@"success";
                break;
            case 101:
                str=@"手机号被停用";
                break;
            case 102:
                str=@"手机号不存在";
                break;
            case 103:
                str=@"用户名或密码错误";
                break;
            case 104:
                str=@"用户当前车型为空";
                break;
            case 105:
                str=@"服务器错误";
                break;
            case 106:
                str=@"用户名错误";
                break;
            case 107:
                str=@"默认车不能删除";
                break;
            case 108:
                str=@"积分不足 不能兑换";
                break;
            case 109:
                str=@"订单不能取消";
                break;
            case 110:
                str=@"用户当前车型为空";
                break;
            case 1111:
                str=@"不能进行评价";
                break;
            case 601:
                str=@"验证码错误";
                break;
            case 602:
                str=@"验证码已过期";
                break;
            case 000:
                str=@"服务器异常，请重试";
                break;
            default:
                str=@"服务器错误";
                break;
        }
        return str;
}

@end
