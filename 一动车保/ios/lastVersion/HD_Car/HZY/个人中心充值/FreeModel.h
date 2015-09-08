//
//  FreeModel.h
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//
typedef NS_ENUM(NSUInteger, FreeNetWorkingType) {
    All = 0,   //
    Recharge = 1,//
    Consume = 2,
    
};
#import <Foundation/Foundation.h>
#import "ListModel.h"

@interface FreeModel : NSObject
@property (nonatomic,assign)NSInteger pages;
@property (nonatomic,strong)NSString * balance;
@property (nonatomic,strong)NSArray * list;
@end





