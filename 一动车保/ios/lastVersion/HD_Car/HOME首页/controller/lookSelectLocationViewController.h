//
//  lookSelectLocationViewController.h
//  HD_Car
//
//  Created by xingso on 15/8/6.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "BaseMapViewController.h"

@interface lookSelectLocationViewController : BaseMapViewController

//传递展示的位置
@property (nonatomic, readwrite) CLLocationCoordinate2D locationCoordinate;

@end
