//
//  YC_SelectTimeViewController.h
//  HD_Car
//
//  Created by hydom on 9/16/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVbaseViewController.h"
#import "JV_selectTimeView.h"
@interface YC_SelectTimeViewController : JVbaseViewController

@property(nonatomic, weak) JV_selectTimeView * selectTimeView;
@property(copy,nonatomic)void(^selectViewController)(NSString* timeStr);

@end
