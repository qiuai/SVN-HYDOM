//
//  YC_AddGoodsViewController.h
//  HD_Car
//
//  Created by hydom on 8/7/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "carModel.h"
#import "YC_GoodsModel.h"
#import "JVbaseViewController.h"
@interface YC_AddGoodsViewController : JVbaseViewController

@property (nonatomic, strong) NSString * cmid;
@property (nonatomic, strong) NSString * scid;
@property (nonatomic, strong) NSString * pids;
//新增 回调
@property(nonatomic,copy)void(^addGooldsBlock)(YC_GoodsModel *);
@end
