//
//  BaseMapViewController.h
//  SearchV3Demo
//
//  Created by songjian on 13-8-14.
//  Copyright (c) 2013年 songjian. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MAMapKit/MAMapKit.h>
#import <AMapSearchKit/AMapSearchAPI.h>
@class HDNavigationView;

@interface BaseMapViewController : UIViewController<MAMapViewDelegate, AMapSearchDelegate>

@property(nonatomic,strong)HDNavigationView* navView;
@property (nonatomic, strong) MAMapView *mapView;

@property (nonatomic, strong) AMapSearchAPI *search;

- (void)returnAction;

- (NSString *)getApplicationName;
- (NSString *)getApplicationScheme;
@end
