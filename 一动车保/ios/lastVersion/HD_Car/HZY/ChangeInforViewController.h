//
//  ChangeInforViewController.h
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//
typedef NS_ENUM(NSUInteger, ChangeInforType) {
    ChangeImage = 1,   //勘客
    ChangeUserName = 2,//导师
    
};
#import <UIKit/UIKit.h>
#import "JVbaseViewController.h"
@protocol ChangeInforDelegate <NSObject>
@optional
- (void)ChangeWith:(ChangeInforType)type value:(id)value;

@end

@interface ChangeInforViewController : JVbaseViewController
@property (nonatomic,assign)ChangeInforType type;
@property (nonatomic,weak)id <ChangeInforDelegate> delegate;
@property (nonatomic,strong)NSData * data;
@property (nonatomic,strong)id changeValue;

@end
