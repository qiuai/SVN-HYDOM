//
//  HDSelectUserLocationViewController.h
//  officialDemo2D
//
//  Created by xingso on 15/7/20.
//  Copyright (c) 2015年 AutoNavi. All rights reserved.
//

#import "BaseMapViewController.h"
@interface HDSelectUserLocationViewController : BaseMapViewController

@property(nonatomic,copy) void(^locationBlock) (NSDictionary* mo);

@end
