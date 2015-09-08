//
//  EdtingCarInforViewController.h
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "carModel.h"
#import "ZHPickView.h"
typedef NS_ENUM(NSInteger, pushStyle)
{
    pushStyleForInsert = 0, ////添加汽车
    pushStyleForUpdate//更新
};


@interface EdtingCarInforViewController : UIViewController
@property (nonatomic,strong)carModel * model;
//可传递 在进行编辑 车型信息的时候
@property(nonatomic,assign)pushStyle pushVcStyle;
@property (nonatomic, strong) ZHPickView * pickView;
- (void)datePickViewSelector;
@end
