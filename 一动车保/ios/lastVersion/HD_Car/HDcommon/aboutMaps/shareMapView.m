//
//  shareMapView.m
//  HD_Car
//
//  Created by xingso on 15/7/21.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "shareMapView.h"

@implementation shareMapView

-(MAMapView *)mapView{
    if (!_mapView) {
        _mapView=[[MAMapView alloc]init];
    }
    return _mapView;
}

-(AMapSearchAPI *)search{
    if (!_search) {
        _search=[[AMapSearchAPI alloc] initWithSearchKey:[MAMapServices sharedServices].apiKey Delegate:nil];
    }
    return _search;
}

+(instancetype)share{
    static shareMapView *_sharedClass;
    static dispatch_once_t token;
    dispatch_once(&token,^{
        _sharedClass = [[shareMapView alloc] init];
    });
    return _sharedClass;
}
@end
