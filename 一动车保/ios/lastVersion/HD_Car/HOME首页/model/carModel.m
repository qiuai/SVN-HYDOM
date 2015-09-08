//
//  carModel.m
//  HD_Car
//
//  Created by xingso on 15/7/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "carModel.h"

@implementation carModel
/**字典转模型*/
+(instancetype)carModelWithDictionary:(NSDictionary*)dictionary{
    carModel* car=[[carModel alloc]initWithDictionary:dictionary];
    return car;
}

//替换  nsurl
-(void)setValue:(id)value forUndefinedKey:(NSString *)key{
    if ([key isEqualToString:@"cbimage"]) {
        self.cbimageURL=value;
    }
//    if ([key isEqualToString:@"defalutCar"]) {
//        self.isDefaultCar=[NSString stringWithFormat:@"%@",value];
//    }
}

//kvc 字典转模型
-(instancetype)initWithDictionary:(NSDictionary*)dictionary{
    self = [super init];
    if(self){
        [self setValuesForKeysWithDictionary:dictionary];
    }
    return self;
}


/**车型描述*/
-(NSString*)getCarDescription{
    return [NSString stringWithFormat:@"%@ %@",_cbname,_csname];
}


@end
