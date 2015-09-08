//
//  HDSelectUserLocationViewController.m
//  officialDemo2D
//
//  Created by xingso on 15/7/20.
//  Copyright (c) 2015年 AutoNavi. All rights reserved.
//

#import "lookSelectLocationViewController.h"
#import "ReGeocodeAnnotation.h"
#import "MANaviAnnotationView.h"
#import "HDNavigationView.h"

#define RightCallOutTag 1
#define LeftCallOutTag 2
@interface lookSelectLocationViewController ()<UIGestureRecognizerDelegate,UIAlertViewDelegate>
@property(nonatomic,weak)MAAnnotationView* onceAnnotation;
/**保存的位置信息*/
@property(nonatomic,strong)ReGeocodeAnnotation* myGeocodeAnnotation;

@property(nonatomic,weak)UILabel* infoLabel;

@end

@implementation lookSelectLocationViewController



- (void)searchReGeocodeWithCoordinate:(CLLocationCoordinate2D)coordinate
{
    AMapReGeocodeSearchRequest *regeo = [[AMapReGeocodeSearchRequest alloc] init];
    
    regeo.location = [AMapGeoPoint locationWithLatitude:coordinate.latitude longitude:coordinate.longitude];
    regeo.requireExtension = YES;
    
    [self.search AMapReGoecodeSearch:regeo];
}

#pragma mark - MAMapViewDelegate

- (void)mapView:(MAMapView *)mapView annotationView:(MAAnnotationView *)view calloutAccessoryControlTapped:(UIControl *)control
{
    if ([view.annotation isKindOfClass:[ReGeocodeAnnotation class]])
    {
        if ([control tag] == RightCallOutTag)
        {
//            [self gotoDetailForReGeocode:[(ReGeocodeAnnotation*)view.annotation reGeocode]];
        }
        else if([control tag] == LeftCallOutTag)
        {
            //            MANaviConfig * config = [[MANaviConfig alloc] init];
            //            config.destination = view.annotation.coordinate;
            //            config.appScheme = [self getApplicationScheme];
            //            config.appName = [self getApplicationName];
            //            config.strategy = MADrivingStrategyShortest;
        }
    }
}

- (MAAnnotationView *)mapView:(MAMapView *)mapView viewForAnnotation:(id<MAAnnotation>)annotation
{
    
    
    if ([annotation isKindOfClass:[ReGeocodeAnnotation class]])
    {
        if (self.onceAnnotation!=nil) {
            [self.onceAnnotation setHidden:YES];
            self.onceAnnotation=nil;
        }
        static NSString *reuseIndetifier = @"annotationReuseIndetifier";
        MAAnnotationView *annotationView = [[MAAnnotationView alloc] initWithAnnotation:annotation
                                                                        reuseIdentifier:reuseIndetifier];
        UIImage* image=[UIImage imageNamed:@"mapLabel"];
        annotationView.image = image;
        //            弹出提示
        self.myGeocodeAnnotation=(ReGeocodeAnnotation*)annotation;
        if (self.infoLabel==nil) {
            UILabel* infoLabel=[[UILabel alloc]initWithFrame:CGRM(20, SCREEN_HEIGHT-50, SCREEN_WIDTH-40, 35)];
            [self.view addSubview:infoLabel];
            self.infoLabel=infoLabel;
            infoLabel.backgroundColor=[UIColor grayColor];
            infoLabel.textAlignment=1;
            infoLabel.font=FONT12;
        }
        self.infoLabel.text=[NSString stringWithFormat:@"%@,%@",self.myGeocodeAnnotation.title,self.myGeocodeAnnotation.subtitle];
        [self.infoLabel setHidden:NO];
        self.onceAnnotation=annotationView;
        return annotationView;
    }
    return nil;
}


// Called when we cancel a view (eg. the user clicks the Home button). This is not called when the user clicks the cancel button.
// If not defined in the delegate, we simulate a click in the cancel button


#pragma mark - AMapSearchDelegate

/* 逆地理编码回调. */
- (void)onReGeocodeSearchDone:(AMapReGeocodeSearchRequest *)request response:(AMapReGeocodeSearchResponse *)response
{
    if (response.regeocode != nil)
    {
        CLLocationCoordinate2D coordinate = CLLocationCoordinate2DMake(request.location.latitude, request.location.longitude);
        ReGeocodeAnnotation *reGeocodeAnnotation = [[ReGeocodeAnnotation alloc] initWithCoordinate:coordinate
                                                                                         reGeocode:response.regeocode];
        [self.mapView addAnnotation:reGeocodeAnnotation];
        [self.mapView selectAnnotation:reGeocodeAnnotation animated:YES];
    }
}

#pragma mark - Handle Gesture

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer
{
    return YES;
}

- (void)handleLongPress:(UILongPressGestureRecognizer *)longPress
{
    if (longPress.state == UIGestureRecognizerStateBegan)
    {
        CLLocationCoordinate2D coordinate = [self.mapView convertPoint:[longPress locationInView:self.view]
                                                  toCoordinateFromView:self.view];
        
        [self searchReGeocodeWithCoordinate:coordinate];
    }
}






- (void)initToolBar
{
    
}


#pragma mark - Life Cycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    WEAKSELF;
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf returnAction];
    }];
    
    [self searchReGeocodeWithCoordinate:self.locationCoordinate];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
//    self.mapView.showsUserLocation = YES;
//    self.mapView.userTrackingMode = MAUserTrackingModeFollow;
}


- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
}

@end
