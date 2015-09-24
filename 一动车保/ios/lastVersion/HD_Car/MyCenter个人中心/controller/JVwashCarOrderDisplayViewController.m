//
//  JVwashCarOrderDisplayViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVwashCarOrderDisplayViewController.h"
#import "JVorderImportantView.h"
#import "servicesOrderInfoView.h"
#import "JVconfirmWashCarView.h"
#import "signValueObject.h"
#import "UIImageView+WebCache.h"
#import "lookSelectLocationViewController.h"
#import "Global.pch"
@interface JVwashCarOrderDisplayViewController()

@property(strong,nonatomic)UIScrollView* baseScrollView;


/**订单状态*/
@property(strong,nonatomic)UILabel* orderStateLabel;

/**重要信息view*/
@property(strong,nonatomic)JVorderImportantView* importantView;
/**洗车描述*/
@property(strong,nonatomic)servicesOrderInfoView* servicesInfoView;
/**洗车确认*/
@property(strong,nonatomic)JVconfirmWashCarView* confirmView;
/**确认订单*/
@property(strong,nonatomic)UIView* submitView;

@property(weak,nonatomic)UIView* backView;
@property(weak,nonatomic)UIView* warinView;


@property (nonatomic, strong) UILabel *numberLabel;



@property(nonatomic,strong) NSString*lat;
@property(nonatomic,strong) NSString* lng;

/**地图相关*/
@property (nonatomic, strong) MAMapView *mapView;
@property (nonatomic, strong) AMapSearchAPI *search;

@property (nonatomic, strong) CLLocationManager *locationManager;



@end

@implementation JVwashCarOrderDisplayViewController


-(void)initData{

    NSMutableDictionary* parameter=[[userDefaultManager getUserWithToken]mutableCopy];
    [parameter setObject:self.orderID forKey:@"oid"];
    
    [HTTPconnect sendGETWithUrl:getOrderWashCarDetailAPI parameters:parameter success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            self.orderStateLabel.text=responseObject[@"ostatus"];
           [self.servicesInfoView.serviceImageView sd_setImageWithURL:imageURLWithPath(responseObject[@"osimgpath"]) placeholderImage:[UIImage imageNamed:FillImage]];
            self.servicesInfoView.servicePrices.text=[NSString stringWithFormat:@"￥ %@",globalPrices(responseObject[@"osprice"])];
            UILabel* titleLB=[[UILabel alloc]initWithFrame:CGRM(0, 0, self.servicesInfoView.servicesInfoView.frame.size.width, 20)];
            titleLB.text=@"上门洗车";
            titleLB.textColor=[UIColor blackColor];
            titleLB.font=FONT14;
            UILabel* infoLB=[[UILabel alloc]initWithFrame:CGRM(0, 20, self.servicesInfoView.servicesInfoView.frame.size.width, 20)];
            infoLB.text=responseObject[@"osremark"];
            infoLB.textColor=[UIColor grayColor];
            infoLB.font=FONT12;
            [self.servicesInfoView.servicesInfoView addSubview:titleLB];
            [self.servicesInfoView.servicesInfoView addSubview:infoLB];
            //设置 订单信息 数据
            self.numberLabel.text=responseObject[@"onum"];
            self.importantView.contantNameLB.text=responseObject[@"ocontact"];
            self.importantView.carNameLB.text=responseObject[@"ocmname"];
            self.importantView.carNumberLB.text=responseObject[@"oplateNum"];
            self.importantView.contantPhoneLB.text=responseObject[@"ophone"];
            
            self.importantView.serviceModelLB.text=[responseObject[@"ocleanType"] isEqualToString:@"1"]? @"清洗车身":@"内外清洗";
            self.importantView.carColorLB.text=responseObject[@"ocarcolor"];
            self.confirmView.payStyle.text=[self getPayTpyeFromNumber: responseObject[@"payway"]];
            self.confirmView.couponsLabel.text=responseObject[@"usecoup"];
            self.confirmView.payPrcies.text= [NSString stringWithFormat:@"￥ %@",globalPrices(responseObject[@"orimoney"])];
            self.confirmView.couponsPrices.text = [NSString stringWithFormat:@"-￥ %@",globalPrices(responseObject[@"cpmoney"])];
            self.confirmView.realityPrices.text= [NSString stringWithFormat:@"￥ %@",globalPrices(responseObject[@"paymoney"])];
            self.lat=responseObject[@"lat"];
            self.lng=responseObject[@"lng"];
        
        }else {
            warn(responseObject);
        }
        
        
    } failure:^(NSError *error) {
        warn(@"请求失败");
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
            break;
    }
    return string;
}

#pragma -mark load
- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self initNavViewWithtitle:@"洗车订单"];
    [self layoutUI];
    [self initData];
    
}

-(void)layoutUI{
    self.baseScrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64)];
    self.baseScrollView.backgroundColor=HDfillColor;
    self.baseScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 700);
    [self.view addSubview:self.baseScrollView];
    UIView *vll=[[UIView alloc]initWithFrame:CGRM(0, 0, SCREEN_WIDTH, 35)];
    vll.backgroundColor=[UIColor whiteColor];
    [self.baseScrollView addSubview:vll];
     UILabel* bulletinLabel=[[UILabel alloc]initWithFrame:CGRM(15, 0, 80, 35)];
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
    self.importantView.frame=CGRM(0, CGRectGetMaxY(bulletinLabel.frame), SCREEN_WIDTH, 130);
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
    self.servicesInfoView=[[[NSBundle mainBundle]loadNibNamed:@"servicesOrderInfoView" owner:nil options:nil]lastObject];
    self.servicesInfoView.frame=CGRM(0, CGRectGetMaxY(v2.frame)+10, SCREEN_WIDTH, 130);
    [self.baseScrollView addSubview:self.servicesInfoView];
    self.confirmView=[[[NSBundle mainBundle]loadNibNamed:@"JVconfirmWashCarView" owner:nil options:nil]lastObject];
    self.confirmView.frame=CGRM(0, CGRectGetMaxY(self.servicesInfoView.frame), SCREEN_WIDTH, 285);
    [self.confirmView.lookMyLocation addTapGestureRecognizerWithTarget:self action:@selector(pushLookMyLocation)];
    [self.baseScrollView addSubview:self.confirmView];

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
