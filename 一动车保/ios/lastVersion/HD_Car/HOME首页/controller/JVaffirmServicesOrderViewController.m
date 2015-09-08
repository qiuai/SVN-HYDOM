//
//  JVconfirmOrderViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/31.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVaffirmServicesOrderViewController.h"
#import "HDNavigationView.h"
#import "JVorderImportantView.h"
#import "JVdisplayProductView.h"
#import "JVconfirmOrderServicesView.h"
#import "submitOrderModel.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "MBProgressHUD.h"
#import "signValueObject.h"
#import "UIImageView+WebCache.h"
#import "JVDisplayMaintenanceServicesViewController.h"
#import "lookSelectLocationViewController.h"
#import "JVzhifubao.h"

@interface JVaffirmServicesOrderViewController ()
@property(strong,nonatomic)UIScrollView* baseScrollView;

/**重要信息view*/
@property(strong,nonatomic)JVorderImportantView* importantView;
/**商品展示*/
@property(strong,nonatomic)JVdisplayProductView* showProductView;
/**服务确认view*/
@property(strong,nonatomic)JVconfirmOrderServicesView* confirmView;
/**确认订单*/
@property(strong,nonatomic)UIView* submitView;

@property(weak,nonatomic)UIView* backView;
@property(weak,nonatomic)UIView* warinView;
@property(strong, nonatomic)UIView* showView;

@property(nonatomic,strong)MBProgressHUD *HUD;



/**地图相关*/
@property (nonatomic, strong) MAMapView *mapView;
@property (nonatomic, strong) AMapSearchAPI *search;

@property (nonatomic, strong) CLLocationManager *locationManager;

@end

@implementation JVaffirmServicesOrderViewController
-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view.window];
    }
    return _HUD;
}


-(void)initData{

    //设置 订单信息 数据
    self.importantView.contantNameLB.text=self.model.contact;
    self.importantView.carNameLB.text=self.model.carDescription;
    self.importantView.carNumberLB.text=self.model.plateNumber;
    self.importantView.contantPhoneLB.text=self.model.phone;
    self.importantView.serviceModelLB.text=@"上门服务";
    self.importantView.carColorLB.text=self.model.color;
    //商品展示
    self.showProductView.productArray=self.showProductArray;
    [self.showProductView addTapGestureRecognizerWithTarget:self action:@selector(lookProductInfoCLick)];
    
    
    
    [self.confirmView.lookMyLocation addTapGestureRecognizerWithTarget:self action:@selector(pushLookMyLocation)];
    
    self.confirmView.payStyle.text=self.model.payWayDescriotion;
    self.confirmView.couponsLabel.text=@"已使用";
    self.confirmView.productPrcies.text=[UtilityMethod addRMB:self.aboutPricesArray[0]];
    self.confirmView.servicePrcies.text=[UtilityMethod addRMB:self.aboutPricesArray[1]];
    self.confirmView.couponsPrices.text=[UtilityMethod addSubRMB:self.aboutPricesArray[2]];
    self.confirmView.realityPrices.text=[UtilityMethod addRMB:self.aboutPricesArray[3]];
    self.confirmView.servicesTime.text=self.model.timeDescriptionStr;
    self.confirmView.payStyle.text=self.model.payWayDescriotion;
}

#pragma -mark load
- (void)viewDidLoad {
    [super viewDidLoad];
    HDNavigationView* nav=[HDNavigationView navigationViewWithTitle:@"确认订单"];
    WEAKSELF;
    [nav tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view  addSubview:nav];
    [self layoutUI];
    [self initData];
}



-(void)layoutUI{
    self.baseScrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64-55)];
    self.baseScrollView.backgroundColor=HDfillColor;
    self.baseScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 600);
    [self.view addSubview:self.baseScrollView];

    self.importantView=[[[NSBundle mainBundle]loadNibNamed:@"JVorderImportantView" owner:nil options:nil]lastObject];
    self.importantView.frame=CGRM(0, 0, SCREEN_WIDTH, 130);
    [self.baseScrollView addSubview:self.importantView];
    //判断是否展示商品
    UIView* painterView;
    if (self.showProductArray.count>0) {
    self.showProductView=[[JVdisplayProductView alloc]init];
    self.showProductView.frame=CGRM(0, CGRectGetMaxY(self.importantView.frame), SCREEN_WIDTH, 60);
    [self.baseScrollView addSubview:self.showProductView];
        painterView=self.showProductView;
    }else{
        UIView* serviceCountView=[[UIView alloc]initWithFrame:CGRM(0, CGRectGetMaxY(self.importantView.frame), SCREEN_WIDTH, 35)];
        painterView=serviceCountView;
        UILabel* lb=[[UILabel alloc]initWithFrame:CGRM(15, 0, 80, 35)];
        lb.text=@"所选服务";
        lb.textColor=[UIColor blackColor];
        lb.font=FONT14;
        [serviceCountView addSubview:lb];
        UILabel* rightlb=[[UILabel alloc]initWithFrame:CGRM(SCREEN_WIDTH-25, 0, 10, 35)];
        rightlb.text=@">";
        rightlb.textColor=[UIColor blackColor];
        rightlb.font=[UIFont systemFontOfSize:16.0];
        [serviceCountView addSubview:rightlb];
        UIButton* bu=[[UIButton alloc]initWithFrame:CGRM(CGRectGetMaxX(lb.frame)+5, 0, SCREEN_WIDTH-CGRectGetMaxX(lb.frame)-5-10-25, 35)];
        [bu setTitle:_servicesCount forState:0];
        [bu setTitleColor:[UIColor blackColor] forState:0];
        [bu addTarget:self action:@selector(lookProductInfoCLick) forControlEvents:UIControlEventTouchUpInside];
        bu.contentHorizontalAlignment = UIControlContentHorizontalAlignmentRight;
        //        bu.contentEdgeInsets = UIEdgeInsetsMake(0,0, 0, 10);
        bu.titleLabel.font=FONT14;
        [serviceCountView addSubview:bu];
        serviceCountView.backgroundColor=[UIColor whiteColor];
        [self.baseScrollView addSubview:serviceCountView];
    }
    
    self.confirmView=[[[NSBundle mainBundle]loadNibNamed:@"JVconfirmOrderServicesView" owner:nil options:nil]lastObject];
    self.confirmView.frame=CGRM(0, CGRectGetMaxY(painterView.frame), SCREEN_WIDTH, 285);
    [self.baseScrollView addSubview:self.confirmView];
    //    提交订单view
    [self submitViewLayout];
}

#pragma -mark submitView Layout
-(void)submitViewLayout{
    self.submitView=[[UIView alloc]initWithFrame:CGRectMake(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 55)];
    self.submitView.backgroundColor=[UIColor whiteColor];
    [self.view addSubview:self.submitView];
    UIButton* comebackBtn=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH/2.0, 15, 65,30)];
    [comebackBtn setTitle:@"修改" forState:0];
    [comebackBtn setTitleColor:[UIColor blackColor] forState:0];
    [self.submitView addSubview:comebackBtn];
    [comebackBtn.layer setMasksToBounds:YES];
    [comebackBtn.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [comebackBtn.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef colorref=COLOR(221, 221, 221, 1).CGColor;
    [comebackBtn.layer setBorderColor:colorref];
    comebackBtn.titleLabel.font=FONT14;
    [comebackBtn addTarget:self action:@selector(combaceClick) forControlEvents:UIControlEventTouchUpInside];
    UIButton* submitBTN=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH-20-65, 15, 65,30)];
    submitBTN.titleLabel.font=FONT14;
    [submitBTN setTitle:@"支付" forState:0];
    submitBTN.backgroundColor=[UIColor redColor];
    [submitBTN setTitleColor:[UIColor whiteColor] forState:0];
    [submitBTN.layer setMasksToBounds:YES];
    [submitBTN.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [submitBTN.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef scolorref=[UIColor redColor].CGColor;
    [submitBTN.layer setBorderColor:scolorref];
    [self.submitView addSubview:submitBTN];
    [submitBTN addTarget:self action:@selector(submitClick:) forControlEvents:UIControlEventTouchUpInside];
    
    //加阴影
    //    for (NSInteger i=0; i<5; i++) {
    //       UIView* line1=[UIView new];
    //        line1.frame=CGRM(0, 4-i, SCREEN_WIDTH, 1);
    //        line1.backgroundColor=COLOR(0, 00, 00, (5-i)/10.0f);
    //        [self.submitView addSubview:line1];
    //    }
    UIView* line1=[UIView new];
    line1.frame=CGRM(0, 0, SCREEN_WIDTH, 1);
    line1.backgroundColor=COLOR(0, 00, 00, 0.3);
    [self.submitView addSubview:line1];
    
    
    
}

#pragma -mark 相应事件
-(void)combaceClick{
    WEAKSELF;
    [weakSelf.navigationController popViewControllerAnimated:YES];
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
    CLLocationCoordinate2D coord=CLLocationCoordinate2DMake([self.model.lat doubleValue], [self.model.lng doubleValue]);
    vc.locationCoordinate=coord;
    [self.navigationController pushViewController:vc animated:NO];
}

#pragma -mark 查看商品
-(void)lookProductInfoCLick{
    
    _po(@"查看商品详情");
    JVDisplayMaintenanceServicesViewController* vc=[[JVDisplayMaintenanceServicesViewController alloc]init];
    vc.dataArray=self.dispalyProductArray;
    [self.navigationController pushViewController:vc animated:NO];
    
    
    
}

#pragma -mark 跳转支付界面
-(void)submitClick:(UIButton*)btn{
    btn.userInteractionEnabled=NO;
    NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [dict setObject:self.model.ucid forKey:@"ucid"];
    [dict setObject:self.model.stime forKey:@"stime"];
    [dict setObject:self.model.etime forKey:@"etime"];
    [dict setObject:self.model.phone forKey:@"phone"];
    [dict setObject:self.model.contact forKey:@"contact"];
    [dict setObject:self.model.plateNumber forKey:@"plateNumber"];
    [dict setObject:self.model.color forKey:@"color"];
    [dict setObject:self.model.lat forKey:@"lat"];
    [dict setObject:self.model.lng forKey:@"lng"];
    [dict setObject:self.model.address forKey:@"address"];
    [dict setObject:self.model.cpid==nil?@"":self.model.cpid forKey:@"cpid"];
    [dict setObject:self.model.remark forKey:@"remark"];
    [dict setObject:self.model.payWay forKey:@"payWay"];
    [dict setObject:self.model.httpData forKey:@"httpData"];
    
    [HTTPconnect sendPOSTHttpWithUrl:OrderSaveForServicesAPI parameters:dict success:^(id responseObject) {
        btn.userInteractionEnabled=YES;
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSInteger s=[responseObject[@"payAction"] integerValue];
            switch (s) {
                case 1:
                {
                    //支付宝
                    if ([self.model.payWay isEqualToString:@"2"]) {
                        [self JVservicesPayFromZhifubao:responseObject[@"onum"]];
                        return;
                    }
                    //微信支付
                    if ([self.model.payWay isEqualToString:@"4"]) {
                        [self JVservicesPayFromZhifubao:responseObject[@"onum"]];
                        return;
                    }
                }
                    break;
                case 2:
                {
                    [self success];
                }
                    break;
                default:
                {
                    warn(@"对不起,您的余额不足!");
                }
                    break;
            }
   
            
        }else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        btn.userInteractionEnabled=YES;
        warn(@"请检查网络");
    }];
    
}

#pragma -mark支付宝支付
-(void)JVservicesPayFromZhifubao:(NSString*)onum{
    
    JVcommonProduct* product=[JVcommonProduct new];
    product.orderID=onum;
    signValueObject* signV=[signValueObject shareSignValue];
    globalServicesModel* servModel=[signV getSelectGlobalServiceModel];
    product.productName=servModel.scname;
    product.productDescription=servModel.scremark;
    product.orderPrices=[self.aboutPricesArray[4] doubleValue];
    product.resultURL=zhiFuBaoOrderURL;
    WEAKSELF;
    [JVzhifubao JVzfbWith:product success:^{
        [weakSelf success];
    } failure:^{
        warn(@"支付失败，请重新支付");
    }];
    
}


#pragma -mark 下单成功
-(void)success{
    [self showPopView];
    [self performSelector:@selector(goBackHome) withObject:nil afterDelay:2];
}

-(void)showPopView
{
    if (!_showView) {
        _showView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
        _showView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.6];
        _showView.userInteractionEnabled = YES;
        [self.view addSubview:_showView];
        
        
        UIImageView * alertView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-80, SCREEN_WIDTH-80)];
        alertView.center = _showView.center;
        alertView.image = [UIImage imageNamed:@"ordingSuccess.png"];
        [_showView addSubview:alertView];
        
    } else {
        _showView.hidden = NO;
    }
    
}

-(void)goBackHome
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}


-(void)dealloc{
    _po(@"dealloc JVaffirm");
}


@end
