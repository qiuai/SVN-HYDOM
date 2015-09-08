//
//  globalServicesModel.h
//  HD_Car
//
//  Created by xingso on 15/8/5.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface globalServicesModel : NSObject


/*字段注释请查看接口文档*/


@property(nonatomic,strong)NSString* scid;
@property(nonatomic,strong)NSString* scname;
@property(nonatomic,strong)NSNumber* scprice;
@property(nonatomic,strong)NSString* scimage;
@property(nonatomic,strong)NSString* scremark;
@property(nonatomic,strong)NSString* scremark1;
@property(nonatomic,strong)NSString* scremark2;

/**价格string类型*/
@property(nonatomic,strong)NSString* servicesPrices;

/**字典转模型*/
+(instancetype)globalServicesModelWithDictionary:(NSDictionary*)dictionary;
@end
