//
//  JVcommonOrderInfoViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVcommonOrderInfoViewController.h"
#import "JVDisplayServicesViewController.h"
#import "lookSelectLocationViewController.h"
#import "JVorderImportantView.h"
#import "JVconfirmOrderServicesView.h"

@interface JVcommonOrderInfoViewController ()
@property(nonatomic,weak)JVDisplayServicesViewController* displayVC;


@property(strong,nonatomic)UIScrollView* baseScrollView;


@property(strong,nonatomic)UILabel* numberLabel;
@property(strong,nonatomic)UILabel* orderStateLabel;




/**重要信息view*/
@property(strong,nonatomic)JVorderImportantView* importantView;

/**服务确认view*/
@property(strong,nonatomic)JVconfirmOrderServicesView* confirmView;

/**地图相关*/
@property (nonatomic, strong) MAMapView *mapView;
@property (nonatomic, strong) AMapSearchAPI *search;

@property (nonatomic, strong) CLLocationManager *locationManager;




@property(nonatomic,strong) NSString*lat;
@property(nonatomic,strong) NSString* lng;

@end

@implementation JVcommonOrderInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initNavViewWithtitle:@"订单详情"];
    JVDisplayServicesViewController* displayVC=[[JVDisplayServicesViewController alloc]init];
    [self addChildViewController:displayVC];
    self.displayVC=displayVC;
    [self layoutUI];
    [self initData];
}

-(void)layoutUI{
    
    self.baseScrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64)];
    self.baseScrollView.backgroundColor=HDfillColor;
    self.baseScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 600);
    [self.view addSubview:self.baseScrollView];
    UIView *vll=[[UIView alloc]initWithFrame:CGRM(0, 0, SCREEN_WIDTH, 35)];
    vll.backgroundColor=[UIColor whiteColor];
    [self.baseScrollView addSubview:vll];
    
    UILabel* bulletinLabel=[[UILabel alloc]initWithFrame:CGRM(0, 0, 80, 35)];
    bulletinLabel.font=FONT14;
    bulletinLabel.textAlignment=1;
    bulletinLabel.textColor=[UIColor blackColor];
    bulletinLabel.text=@"订单状态";
    [self.baseScrollView addSubview:bulletinLabel];
    //订单状态
    UILabel* bulletinLabel1=[[UILabel alloc]initWithFrame:CGRM(SCREEN_WIDTH-80-15, 0, 80, 35)];
    bulletinLabel1.font=FONT14;
    bulletinLabel1.textAlignment=1;
    bulletinLabel1.textColor=[UIColor redColor];
    bulletinLabel1.text=@"状态";
    self.orderStateLabel=bulletinLabel1;
    [self.baseScrollView addSubview:bulletinLabel1];
    
    
    self.importantView=[[[NSBundle mainBundle]loadNibNamed:@"JVorderImportantView" owner:nil options:nil]lastObject];
    self.importantView.frame=CGRM(0, 40, SCREEN_WIDTH, 130);
    [self.baseScrollView addSubview:self.importantView];
    

    
    UIView *v2=[[UIView alloc]initWithFrame:CGRM(0, CGRectGetMaxY(self.importantView.frame)+5, SCREEN_WIDTH, 35)];
    v2.backgroundColor=[UIColor whiteColor];
    [self.baseScrollView addSubview:v2];
    UILabel* b2=[[UILabel alloc]initWithFrame:CGRM(15, 0, 60, 35)];
    b2.font=FONT14;
    b2.textAlignment=1;
    b2.textColor=[UIColor blackColor];
    b2.text=@"订单号:";
    b2.textAlignment=0;
    [v2 addSubview:b2];
    //订单
    UILabel* b3=[[UILabel alloc]initWithFrame:CGRM(60+15+10, 0, 200, 35)];
    b3.font=FONT14;
    b3.textAlignment=1;
    b3.textColor=[UIColor redColor];
    b3.text=@"99999999";
    self.numberLabel=b3;
    [v2 addSubview:b3];
    
    self.confirmView=[[[NSBundle mainBundle]loadNibNamed:@"JVconfirmOrderServicesView" owner:nil options:nil]lastObject];
    self.confirmView.frame=CGRM(0, CGRectGetMaxY(v2.frame)+10, SCREEN_WIDTH, 285);
    [self.confirmView.lookMyLocation addTapGestureRecognizerWithTarget:self action:@selector(pushLookMyLocation)];
    [self.baseScrollView addSubview:self.confirmView];
}

-(void)initData{
    NSMutableDictionary* parameter=[[userDefaultManager getUserWithToken]mutableCopy];
    [parameter setObject:self.orderID forKey:@"oid"];
    [HTTPconnect sendPOSTHttpWithUrl:getOrderServicesDetailAPI parameters:parameter success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSArray* array=responseObject[@"sclist"];
            //初始化 界面数据
            //设置 订单信息 数据
            self.numberLabel.text=responseObject[@"onum"];
            self.importantView.contantNameLB.text=responseObject[@"ocontact"];
            self.importantView.carNameLB.text=responseObject[@"ocmname"];
            self.importantView.carNumberLB.text=responseObject[@"oplateNum"];
            self.importantView.contantPhoneLB.text=responseObject[@"ophone"];
            self.importantView.serviceModelLB.text=@"上门服务";
            self.importantView.carColorLB.text=responseObject[@"ocarcolor"];
            self.confirmView.payStyle.text= [self getPayTpyeFromNumber: responseObject[@"payway"]];
            self.confirmView.couponsPrices.text=[UtilityMethod addSubRMB:responseObject[@"cpmoney"]];
            self.confirmView.productPrcies.text=[UtilityMethod addRMB:responseObject[@"opmoney"]];
            self.confirmView.servicePrcies.text=[UtilityMethod addRMB: responseObject[@"ocmoney"]];
            self.confirmView.realityPrices.text=[UtilityMethod addRMB: responseObject[@"paymoney"]];
            self.confirmView.servicesTime.text=responseObject[@"stime"];
            self.lat=responseObject[@"lat"];
            self.lng=responseObject[@"lng"];
            NSMutableArray* dataArray=[NSMutableArray array];
            for (NSDictionary* d in array) {
                JVcommonOrderSericesModel* orderModel=[JVcommonOrderSericesModel new];
                orderModel.scimg=d[@"scimg"];
                orderModel.scname=d[@"scname"];
                orderModel.scprice=d[@"scprice"];
                NSMutableArray* mutableArray=[NSMutableArray array];
                for (NSDictionary* di in d[@"plist"]) {
                    JVcommonOrderSericesProductMOdel* proModel=[JVcommonOrderSericesProductMOdel new];
                    proModel.pimg=di[@"pimg"];
                    proModel.pname=di[@"pname"];
                    proModel.pprice=di[@"pprice"];
                    proModel.pnum=di[@"pnum"];
                    [mutableArray addObject:proModel];
                }
                orderModel.productArray=mutableArray;
                [dataArray addObject:orderModel];
            }
            _displayVC.dataSoure=dataArray;
            CGFloat height=_displayVC.ViewHeight;
            [self.baseScrollView addSubview:_displayVC.view];
            _displayVC.view.frame=CGRM(0, CGRectGetMaxY(self.importantView.frame)+10+35, SCREEN_WIDTH, height);
            self.confirmView.y=height+10+CGRectGetMaxY(self.importantView.frame)+10+35;
            self.baseScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, CGRectGetMaxY(self.confirmView.frame)+60);
        }else {
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        warn(@"请检查网络");
    }];
}


-(NSString *)getPayTpyeFromNumber:(NSString *)string
{
    switch ([string integerValue]) {
        case 1:
            string = @"会员卡支付";
            break;
        case 2:
            string = @"支付宝";
            break;
        case 3:
            string = @"银联";
            break;
        case 4:
            string = @"微信";
            break;
        default:
            string = @"现场pos机刷卡";
            break;;
    }
    return string;
}

#pragma -mark 初始化地图
-(void)initMap{
    self.mapView = [[MAMapView alloc] initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64)];
    if ([[UIDevice currentDevice].systemVersion floatValue] >= 8.0)
    {
        self.locationManager = [[CLLocationManager alloc] init];
        [self.locationManager requestAlwaysAuthorization];
    }
    self.search = [[AMapSearchAPI alloc] initWithSearchKey:[MAMapServices sharedServices].apiKey Delegate:nil];
}

//查看我的位置
-(void)pushLookMyLocation{
    
    [self initMap];
    lookSelectLocationViewController* vc=[[lookSelectLocationViewController alloc]init];
    vc.mapView = self.mapView;
    vc.search  = self.search;
    vc.title=@"您选择的位置";
    CLLocationCoordinate2D coord=CLLocationCoordinate2DMake([self.lat doubleValue], [self.lng doubleValue]);
    vc.locationCoordinate=coord;
    [self.navigationController pushViewController:vc animated:NO];
}


@end
