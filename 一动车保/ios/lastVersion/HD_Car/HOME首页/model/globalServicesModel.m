//
//  globalServicesModel.m
//  HD_Car
//
//  Created by xingso on 15/8/5.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "globalServicesModel.h"

@implementation globalServicesModel
/**字典转模型*/
+(instancetype)globalServicesModelWithDictionary:(NSDictionary*)dictionary{
    globalServicesModel* ser=[[globalServicesModel alloc]initWithDictionary:dictionary];
    return ser;
}



//替换
-(void)setValue:(id)value forUndefinedKey:(NSString *)key{

}

//kvc 字典转模型
-(instancetype)initWithDictionary:(NSDictionary*)dictionary{
    self = [super init];
    if(self){
        [self setValuesForKeysWithDictionary:dictionary];
    }
    return self;
}


-(void)setScprice:(NSNumber *)scprice{
    _scprice=scprice;
    self.servicesPrices=[NSString stringWithFormat:@"%@",_scprice];
}

@end
