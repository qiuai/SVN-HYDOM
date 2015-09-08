//
//  YC_MarketModel.m
//  HD_Car
//
//  Created by hydom on 8/10/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_MarketModel.h"

@implementation YC_MarketModel

+(instancetype)getMarketModelFromDic:(NSDictionary *)dic
{
    YC_MarketModel * model = [[YC_MarketModel alloc] init];
    model.pid = dic[@"pid"];
    model.pname = dic[@"pname"];
    model.pimage = dic[@"pimage"];
    model.pbuynum = dic[@"pbuynum"];
    model.pcomts = dic[@"pcomts"];
    model.price = dic[@"price"];
    model.ptotalnum = dic[@"ptotalnum"];
    model.salenum = dic[@"pbuynum"];
    model.poriprice = dic[@"poriprice"];
    model.pdisprice = dic[@"pdisprice"];
    model.pdiscount = dic[@"pdiscount"];
    return model;
}


@end
