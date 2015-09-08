//
//  YC_GoodsModel.m
//  HD_Car
//
//  Created by hydom on 8/7/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_GoodsModel.h"

@implementation YC_GoodsModel
/**字典转模型*/
+(instancetype)goodsModelWithDictionary:(NSDictionary*)dictionary{
    YC_GoodsModel* class=[[YC_GoodsModel alloc]initWithDictionary:dictionary];
    return class;
}

//替换  部分不合法的
-(void)setValue:(id)value forUndefinedKey:(NSString *)key{

    //    if ([key isEqualToString:@"defalutCar"]) {
    //        self.isDefaultCar=[NSString stringWithFormat:@"%@",value];
    //    }
}

//kvc 字典转模型
-(instancetype)initWithDictionary:(NSDictionary*)dictionary{
    self = [super init];
    if(self){
        self.myCount=1;
        [self setValuesForKeysWithDictionary:dictionary];
    }
    return self;
}
@end
