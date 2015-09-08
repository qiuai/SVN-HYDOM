//
//  YC_SelectServerViewController.h
//  HD_Car
//
//  Created by hydom on 7/30/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "carModel.h"
#import "JVbaseViewController.h"
@interface YC_SelectServerViewController : JVbaseViewController

@property (nonatomic, strong) carModel * carModel;
@property (nonatomic, strong) NSArray * selectArray;

@end
