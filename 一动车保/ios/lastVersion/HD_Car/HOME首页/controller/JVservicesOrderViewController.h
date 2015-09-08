//
//  servicesOrderViewController.h
//  HD_Car
//
//  Created by xingso on 15/7/20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
@class carModel;
@interface JVservicesOrderViewController : UIViewController
/**车model*/
@property(nonatomic,strong)carModel* selectCarmodel;

@property(nonatomic,strong)NSArray* dataArray;
@end
