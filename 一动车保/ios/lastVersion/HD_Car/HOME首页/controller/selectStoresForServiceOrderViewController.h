//
//  selectStoresForServiceOrderViewController.h
//  HD_Car
//
//  Created by xingso on 15/7/21.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
@class storeModel;
typedef void (^storesModelBlock) (storeModel* mo);
@interface selectStoresForServiceOrderViewController : UIViewController
/**预定位置*/
@property(nonatomic,assign)CLLocationCoordinate2D coordinate;

@property(nonatomic,copy)void (^storesModelBlock) (storeModel* mo);

@end
