//
//  HDSelectUserLocationViewController.m
//  officialDemo2D
//
//  Created by xingso on 15/7/20.
//  Copyright (c) 2015年 AutoNavi. All rights reserved.
//

#import "HDSelectUserLocationViewController.h"
#import "ReGeocodeAnnotation.h"
#import "MANaviAnnotationView.h"
#import "HDNavigationView.h"
#import "JVlookScopeViewController.h"

#define RightCallOutTag 1
#define LeftCallOutTag 2
@interface HDSelectUserLocationViewController ()<UIGestureRecognizerDelegate,UIAlertViewDelegate>
@property(nonatomic,weak)MAAnnotationView* onceAnnotation;
/**保存的位置信息*/
@property(nonatomic,strong)ReGeocodeAnnotation* myGeocodeAnnotation;

@property(nonatomic,weak)UILabel* infoLabel;

@end

@implementation HDSelectUserLocationViewController


- (void)gotoDetailForReGeocode:(AMapReGeocode *)reGeocode
{
//    if (reGeocode != nil)
//    {
//        InvertGeoDetailViewController *invertGeoDetailViewController = [[InvertGeoDetailViewController alloc] init];
//        
//        invertGeoDetailViewController.reGeocode = reGeocode;
//        
//        [self.navigationController pushViewController:invertGeoDetailViewController animated:YES];
//    }
}

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
            [self gotoDetailForReGeocode:[(ReGeocodeAnnotation*)view.annotation reGeocode]];
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
            UILabel* infoLabel=[[UILabel alloc]initWithFrame:CGRM(20, SCREEN_HEIGHT-35-15-60, SCREEN_WIDTH-40, 35)];
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


-(void)tapPress:(UITapGestureRecognizer*)tapPress{
    
        CLLocationCoordinate2D coordinate = [self.mapView convertPoint:[tapPress locationInView:self.view]
                                                  toCoordinateFromView:self.view];
        
        [self searchReGeocodeWithCoordinate:coordinate];
    
    
}

#pragma mark - Initialization

- (void)initGestureRecognizer
{
    UILongPressGestureRecognizer *longPress = [[UILongPressGestureRecognizer alloc] initWithTarget:self
                                                                                            action:@selector(handleLongPress:)];
    longPress.minimumPressDuration = 0.5;
    longPress.delegate = self;
    UITapGestureRecognizer* tapPress=[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(tapPress:)];
    tapPress.delegate=self;
    [self.view addGestureRecognizer:tapPress];
}

- (void)initToolBar
{
   
}


#pragma mark - Life Cycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self initGestureRecognizer];
    UIButton* bt=[[UIButton alloc]initWithFrame:CGRM(15, SCREEN_HEIGHT-15-45, SCREEN_WIDTH-30, 45)];
    [bt setTitle:@"点击查看支持范围" forState:0];
    [bt setTitleColor:[UIColor whiteColor] forState:0];
    [bt setBackgroundColor:ShareBackColor];
    bt.titleLabel.textAlignment=1;
    [bt addTarget:self action:@selector(lookLocation) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:bt];
    WEAKSELF;
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        
        [weakSelf returnAction];
    }];
    
    [self.navView tapRightViewWithImageName:@"确认" tapBlock:^{
        if (weakSelf.locationBlock) {
            NSMutableDictionary* dict=[NSMutableDictionary dictionary];
            NSString * location=[NSString stringWithFormat:@"%@%@",weakSelf.myGeocodeAnnotation.title,weakSelf.myGeocodeAnnotation.subtitle];
            [dict setObject:location forKey:@"locationInfo"];
            [dict setObject:[NSString stringWithFormat:@"%@", [NSNumber numberWithDouble:weakSelf.myGeocodeAnnotation.coordinate.latitude]] forKey:@"latitude"];
            [dict setObject:[NSString stringWithFormat:@"%f",weakSelf.myGeocodeAnnotation.coordinate.longitude] forKey:@"longitude"];
            weakSelf.locationBlock(dict);
            [weakSelf returnAction];
        }
    }];
    
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    self.mapView.showsUserLocation = YES;
    self.mapView.userTrackingMode = MAUserTrackingModeFollow;

}


- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
}


#pragma -mark 跳转查看  支持范围
-(void)lookLocation{
    JVlookScopeViewController* vc=[[JVlookScopeViewController alloc]init];
    [self.navigationController pushViewController:vc animated:NO];
}

@end
