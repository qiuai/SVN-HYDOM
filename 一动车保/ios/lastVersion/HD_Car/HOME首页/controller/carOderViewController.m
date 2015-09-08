//
//  carOderViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/14.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "carOderViewController.h"
#import "JVconfirmOrderViewController.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "MBProgressHUD.h"
#import "userDefaultManager.h"
#import "selectCarController.h"
#import "servicesOrderInfoView.h"
#import "servicesOrderGoHomeView.h"
#import "storesOrderPriceView.h"
#import "UIImage+MJ.h"
#import "servicesOrderBystoresView.h"
#import "signValueObject.h"
#import "carListViewController.h"
#import "JVwashCarInfoView.h"
#import "JVCommonSelectCarView.h"
#import "HDSelectUserLocationViewController.h"
#import "submitOrderModel.h"
#import "globalServicesModel.h"
#import "UIImageView+WebCache.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "signValueObject.h"
#import "globalServicesModel.h"
#import "YC_SelectDiscountViewController.h"

@interface carOderViewController ()
{
    BOOL selectDiscount;
    BOOL useCoupon;
}
@property(nonatomic,strong) HDNavigationView* titleView;
@property(nonatomic,strong)JVCommonSelectCarView* selectCarView;
@property(nonatomic,strong)servicesOrderInfoView* servicesInfoView;
@property(nonatomic,strong)JVwashCarInfoView* washCarInfoView;
@property(nonatomic,strong)storesOrderPriceView* pricesView;

@property(nonatomic,strong)UIView* submitView;
/**金额*/
@property(nonatomic,strong)UILabel* pricesLabel;
/**提交订单*/
@property(nonatomic,strong)UIButton* submitBtn;
@property(nonatomic,strong)UIScrollView* osScrollView;

@property(nonatomic,strong)UIView* backView;

/**纬度*/
@property(nonatomic,strong)NSString* latitude;
/**经度*/
@property(nonatomic,strong)NSString* longitude;
/**优惠卷id*/
@property(nonatomic,strong)NSString* cpid;

/**地址信息*/
@property(nonatomic,strong)NSString* locationInfo;

/**地图相关*/
@property (nonatomic, strong) MAMapView *mapView;
@property (nonatomic, strong) AMapSearchAPI *search;
@property (nonatomic, strong) CLLocationManager *locationManager;
@property(nonatomic,strong)MBProgressHUD *HUD;


/**金额相关*/
@property(nonatomic,strong)NSString* CT_allPrices;

@property(nonatomic,strong)NSString*  CT_realyPrices;

@property(nonatomic,strong)NSString*  CT_privilegePrices;

@property(nonatomic,strong)sumPricesModel* pricesModel;



@end

@implementation carOderViewController
#pragma -mark once load

-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view.window];
    }
    return _HUD;
}
-(UIView *)backView{
    if (!_backView) {
        _backView=[[UIView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT)];
        _backView.backgroundColor=[UIColor whiteColor];
        //        _blackView.alpha=0.3;
        [self.view addSubview:_backView];
    }
    return _backView;
}

-(UIView *)submitView{
    if (!_submitView) {
        _submitView=[[UIView alloc]initWithFrame:CGRectMake(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 50)];
        _submitView.backgroundColor=[UIColor blackColor];
    }
    return _submitView;
}

-(UIScrollView *)osScrollView{
    if (!_osScrollView) {
        CGFloat  titleViewY=CGRectGetMaxY(self.titleView.frame);
        _osScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, titleViewY, SCREEN_WIDTH, SCREEN_HEIGHT-50-titleViewY)];
        _osScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 800);
        _osScrollView.backgroundColor=HDfillColor;
    }
    return _osScrollView;
}

#pragma -VIEW LOAD
- (void)viewDidLoad {
    [super viewDidLoad];
    useCoupon=NO;
    self.titleView=[HDNavigationView navigationViewWithTitle:@"服务订单"];
    WEAKSELF;
    [self.titleView tapLeftViewWithImageName:nil tapBlock:^{
                [weakSelf.view endEditing:YES];
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:self.titleView];
    
    [self layoutServicesOrderView];
//    //    提交订单view
    [self submitViewLayout];
    
}


-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self initData];
}

-(void)initData{

    self.washCarInfoView.carColor.text=self.selectCarmodel.color;
    self.washCarInfoView.carNumber.text=self.selectCarmodel.plateNumber;
    
    signValueObject* signV=[signValueObject shareSignValue];
    globalServicesModel* model=[signV getSelectGlobalServiceModel];
    [self.servicesInfoView.serviceImageView sd_setImageWithURL:imageURLWithPath(model.scimage) placeholderImage:[UIImage imageNamed:FillImage]];
    self.servicesInfoView.servicePrices.text=[NSString stringWithFormat:@"￥ %@",globalPrices(model.servicesPrices)];
    UILabel* titleLB=[[UILabel alloc]initWithFrame:CGRM(0, 0, self.servicesInfoView.servicesInfoView.frame.size.width, 20)];
    titleLB.text=model.scremark1;
    titleLB.textColor=[UIColor blackColor];
    titleLB.font=FONT14;
    [self.servicesInfoView.servicesInfoView addSubview:titleLB];
    UILabel* infoLB=[[UILabel alloc]initWithFrame:CGRM(0, 20, self.servicesInfoView.servicesInfoView.frame.size.width, 20)];
    infoLB.text=model.scremark2;
    infoLB.textColor=[UIColor grayColor];
    infoLB.font=[UIFont systemFontOfSize:13];
    [self.servicesInfoView.servicesInfoView addSubview:infoLB];
    //获取联系人电话
    self.washCarInfoView.personTextField.text=[[userDefaultManager getPersonNumber] objectForKey:@"myPerson"];
    self.washCarInfoView.phoneNumberTextField.text=[[userDefaultManager getPersonNumber] objectForKey:@"myPhone"];
    
    [self sendHttpPrices];
    
    
    
    
}
#pragma -mark 从服务端计算价格
-(void)sendHttpPrices{
    sumPricesModel* pricesModel=[sumPricesModel sumPrices];
    self.pricesModel=pricesModel;
    signValueObject* signV=[signValueObject shareSignValue];
    globalServicesModel* model=[signV getSelectGlobalServiceModel];
    pricesModel.scidArray=@[model.scid];
    pricesModel.otype=[NSNumber numberWithInteger:1];
    
//    _po(pricesModel);
    
    
    _po([UtilityMethod JVDebugUrlWithdict:[UtilityMethod getObjectData:pricesModel] nsurl:sumPricesAPI]);
    
  [HTTPconnect sendGETWithUrl:sumPricesAPI parameters:[UtilityMethod getObjectData:pricesModel] success:^(id responseObject) {
      if (![responseObject isKindOfClass:[NSString class]]) {
          self.CT_allPrices=globalPrices(responseObject[@"orimoney"]);
          self.pricesView.allPrices.text=[UtilityMethod addRMB:globalPrices(responseObject[@"orimoney"])];
          if (selectDiscount==NO) {
              self.pricesView.privilegePrices.text=[UtilityMethod addSubRMB:globalPrices(responseObject[@"cpmoney"])];
              self.CT_realyPrices=globalPrices(responseObject[@"paymoney"]);
              self.pricesLabel.text=[UtilityMethod addRMB:globalPrices(responseObject[@"paymoney"])];
              self.CT_privilegePrices=globalPrices(responseObject[@"cpmoney"]);
          }
          //判断是否有可用优惠卷
          pricesModel.money=globalPrices(self.pricesView.allPrices.text);
//          _po([UtilityMethod JVDebugUrlWithdict:[UtilityMethod getObjectData:pricesModel] nsurl:getDiscountAbleListAPI]);
          [HTTPconnect sendPOSTHttpWithUrl:getDiscountAbleListAPI parameters:[UtilityMethod getObjectData:pricesModel] success:^(id responseObject) {
               if (![responseObject isKindOfClass:[NSString class]]) {
               //优惠卷可用
                  NSArray *array=responseObject[@"list"];
                  if (array.count>0) {
                      selectDiscount=YES;
                      if([self.washCarInfoView.couponsLabel.text isEqualToString:@"不可用"]){
                      self.washCarInfoView.couponsLabel.text=@"可用";
                      }
                  }
              }else{
                  warn(responseObject);
              }
          } failure:^(NSError *error) {
              warn(@"网络错误");
          }];
      }else{
          warn(responseObject);
      }
      
      
  } failure:^(NSError *error) {
      warn(@"网络错误");
  }];
}



-(void)layoutServicesOrderView{
    [self.view addSubview:self.osScrollView];
    self.selectCarView=[[[NSBundle mainBundle]loadNibNamed:@"JVCommonSelectCarView" owner:nil options:nil]lastObject];
    self.selectCarView.frame=CGRM(0, 0, SCREEN_WIDTH, 30);
    [self.selectCarView.tapChangeCarView addTapGestureRecognizerWithTarget:self action:@selector(comebackCarManager)];
    [self.osScrollView addSubview:self.selectCarView];
    _selectCarView.carNameLabel.text=[NSString stringWithFormat:@"%@",_selectCarmodel.csname];
    self.servicesInfoView=[[[NSBundle mainBundle]loadNibNamed:@"servicesOrderInfoView" owner:nil options:nil]lastObject];
    self.servicesInfoView.frame=CGRM(0, 40, SCREEN_WIDTH, 130);
    selectDiscount=NO;
    self.washCarInfoView=[[[NSBundle mainBundle]loadNibNamed:@"JVwashCarInfoView" owner:nil options:nil]lastObject];
    self.washCarInfoView.frame=CGRM(0,CGRectGetMaxY(self.servicesInfoView.frame), SCREEN_WIDTH, 510);
    [self.washCarInfoView.locationLabel addTapGestureRecognizerWithTarget:self action:@selector(pushLocationVC)];
    [self.washCarInfoView.couponsLabel  addTapGestureRecognizerWithTarget:self action:@selector(selectCountVC)];
    self.pricesView=[[[NSBundle mainBundle]loadNibNamed:@"storesOrderPriceView" owner:nil options:nil]lastObject];
    self.pricesView.frame=CGRM(0, CGRectGetMaxY(self.washCarInfoView.frame)+20, SCREEN_WIDTH, 50);
    [self.osScrollView addSubview:self.servicesInfoView];
    [self.osScrollView addSubview:self.washCarInfoView];
    [self.osScrollView addSubview:self.pricesView];
}

-(void)submitViewLayout{
    [self.view addSubview:self.submitView];
    float submitWith=0.25*SCREEN_WIDTH;
    UILabel* lb1=[[UILabel alloc]initWithFrame:CGRectMake(20, 0, 80,self.submitView.frame.size.height)];
    lb1.text=@"实付款:";
    lb1.textColor=WHITECOLOR;
    [self.submitView addSubview:lb1];
    float pricesX=CGRectGetMaxX(lb1.frame)+10;
    float pricesW=SCREEN_WIDTH-submitWith-pricesX;
    self.pricesLabel=[[UILabel alloc]initWithFrame:CGRectMake(pricesX, 0, pricesW, self.submitView.frame.size.height)];
    self.pricesLabel.text=@"";
    self.pricesLabel.textColor=WHITECOLOR;
    [self.submitView addSubview:self.pricesLabel];
    self.submitBtn=[[UIButton alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-submitWith, 0, submitWith, self.submitView.frame.size.height)];
    self.submitBtn.backgroundColor=[UIColor redColor];
    self.submitBtn.titleLabel.textColor=[UIColor whiteColor];
    [self.submitBtn setTitle:@"提交" forState:UIControlStateNormal];
    [self.submitBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.submitView addSubview:self.submitBtn];
    [self.submitBtn addTarget:self action:@selector(submitOrder) forControlEvents:UIControlEventTouchUpInside];
}


#pragma -mark pushLocationVC
-(void)pushLocationVC{
    [self initMap];
    [userDefaultManager setPerson:self.washCarInfoView.personTextField.text number:self.washCarInfoView.phoneNumberTextField.text];
    HDSelectUserLocationViewController* vc=[[HDSelectUserLocationViewController alloc]init];
    vc.mapView = self.mapView;
    vc.search  = self.search;
    vc.title=@"请单击选择位置";
    [self.navigationController pushViewController:vc animated:NO];
    //保存  经纬度 详细地址
    vc.locationBlock=^(NSDictionary* dict){
        self.latitude=[dict objectForKey:@"latitude"];
        self.longitude=[dict objectForKey:@"longitude"];
        self.locationInfo=[dict objectForKey:@"locationInfo"];
        self.washCarInfoView.locationLabel.text=@"已选择";
    };
}

#pragma -mark 洗车选择优惠卷
-(void)selectCountVC{
    
    if (selectDiscount==YES) {
        YC_SelectDiscountViewController* vc=[[YC_SelectDiscountViewController alloc]init];
        vc.sendModel=self.pricesModel;
        WEAKSELF;
        vc.pricesArrayblock=^(NSArray* array){
            weakSelf.pricesView.allPrices.text=array[2];
            weakSelf.pricesLabel.text=array[4];
            weakSelf.pricesView.privilegePrices.text=[UtilityMethod addSubRMB: array[3]];
            weakSelf.CT_allPrices=array[2];
            weakSelf.pricesView.allPrices.text=[UtilityMethod addRMB:array[2]];
            weakSelf.CT_realyPrices=array[4];
            weakSelf.pricesLabel.text=[UtilityMethod addRMB:array[4]];
            weakSelf.CT_privilegePrices=array[3];
            useCoupon=YES;
            weakSelf.washCarInfoView.couponsLabel.text=@"已使用";
        };
        [self.navigationController pushViewController:vc animated:NO];
    }
}


#pragma -mark  返回车管家
-(void)comebackCarManager{
    carListViewController* vc=[[carListViewController alloc]init];
    [self.navigationController pushViewController:vc animated:NO];
    vc.defaultCarModel=^(carModel *mo){
        self.selectCarmodel=mo;
    };
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




#pragma -mark 提交订单
-(void)submitOrder{
    //验证输入
    if (self.washCarInfoView.personTextField.text.length<1) {
        warn(@"请输入联系人");
        return;
    }
    if (self.washCarInfoView.phoneNumberTextField.text.length<1) {
        warn(@"请输入联系电话");
        return;
    }
    if (self.washCarInfoView.phoneNumberTextField.text.length!=11) {
        warn(@"联系电话手机号11位");
        return;
    }
    
    
    if (![self.washCarInfoView.locationLabel.text isEqualToString:@"已选择"]) {
        warn(@"请选择你的位置");
        return;
    }
    
    submitOrderModel* model=[submitOrderModel new];
    model.ucid=self.selectCarmodel.ucid;
    globalServicesModel* serv=[[signValueObject shareSignValue]getSelectGlobalServiceModel];
    model.scid=serv.scid;
    model.contact=self.washCarInfoView.personTextField.text;
    model.phone=self.washCarInfoView.phoneNumberTextField.text;
    model.plateNumber=self.selectCarmodel.plateNumber;
    model.color=self.selectCarmodel.color;
    model.lat=self.latitude;
    model.lng=self.longitude;
    model.address=self.locationInfo;
    model.cpid=self.cpid;
    model.cleanType=self.washCarInfoView.washStyle;
    model.payWay=self.washCarInfoView.payStyle;
    model.payWayDescription=self.washCarInfoView.payStyleDescription;
    model.carDescription=[NSString stringWithFormat:@"%@",self.selectCarmodel.csname];
    model.serviceDescription=self.washCarInfoView.washStyleDescription;
    model.payPrcies=self.CT_allPrices;
    model.couponsPrices=self.CT_privilegePrices;
    model.realityPrices=self.CT_realyPrices;
    //判断是否使用了优惠劵
    model.isUseCoupon=useCoupon==YES?@"yes":@"no";
    //保存本地用户 电话
    [userDefaultManager setPerson:self.washCarInfoView.personTextField.text number:self.washCarInfoView.phoneNumberTextField.text];
    [HTTPconnect sendPOSTHttpWithUrl:getTechnicianAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            JVconfirmOrderViewController* vc=[[JVconfirmOrderViewController alloc]init];
            vc.model=model;
            if ([[responseObject objectForKey:@"canserver"] intValue]==1) {
                vc.isTrue=YES;
            }else{
                vc.isTrue=NO;
            }
             [self.navigationController pushViewController:vc animated:NO];
        }else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        warn(@"请检查网络");
    }];
    
}




@end

