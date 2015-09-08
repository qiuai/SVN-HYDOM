//
//  sumPricesModel.m
//  HD_Car
//
//  Created by xingso on 15/8/17.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "sumPricesModel.h"
#import "userDefaultManager.h"
@implementation sumPricesModel

+(instancetype)sumPrices{
    sumPricesModel* model=[[sumPricesModel alloc]init];
    NSDictionary* dict=[userDefaultManager getUserWithToken];
    model.uid=dict[@"uid"];
    model.token=dict[@"token"];
    return model;
}

-(void)setPidArray:(NSArray *)pidArray{

    NSMutableString* str=[NSMutableString string];
    for (NSString* s in pidArray) {
        [str appendFormat:@"%@#",s];
    }
    NSRange range=NSMakeRange(str.length-1, 1);
    [str deleteCharactersInRange:range];
    self.pid=str;
}

-(void)setScidArray:(NSArray *)scidArray{
    NSMutableString* str=[NSMutableString string];
    for (NSString* s in scidArray) {
        [str appendFormat:@"%@#",s];
    }
    NSRange range=NSMakeRange(str.length-1, 1);
    [str deleteCharactersInRange:range];
    self.scid=str;
}


@end
