//
//  carOderViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/14.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVservicesOrderViewController.h"
#import "JVconfirmOrderViewController.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "MBProgressHUD.h"
#import "userDefaultManager.h"
#import "selectCarController.h"
#import "JVdisplayProductView.h"
#import "servicesOrderGoHomeView.h"
#import "JV_newPricesView.h"
#import "UIImage+MJ.h"
#import "servicesOrderBystoresView.h"
#import "signValueObject.h"
#import "carListViewController.h"
#import "JVproductOrderInfoView.h"
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
#import "carModel.h"
#import "JV_selectTimeView.h"
#import "JVserviceOrderSubmitModel.h"
#import "YC_GoodsModel.h"
#import "JVaffirmServicesOrderViewController.h"
#import "JVDisplayMaintenanceServicesViewController.h"
#import "YC_SelectDiscountViewController.h"
#import "YC_SelectTimeViewController.h"

@interface JVservicesOrderViewController (){
    BOOL selectDiscount;
}
@property(nonatomic,strong) HDNavigationView* titleView;
@property(nonatomic,strong)JVCommonSelectCarView* selectCarView;
@property(nonatomic,strong)JVdisplayProductView* displayProductView;
@property(nonatomic,strong)JVproductOrderInfoView* productOrderInfoView;
@property(nonatomic,strong)JV_newPricesView* pricesView;

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


//选择时间
@property(strong,nonatomic)JV_selectTimeView* selectTimeView;

//服务器时间
@property(strong,nonatomic)NSString* fromServiceTime;


//时间段
@property(copy,nonatomic)NSString* periodTime;

@property(nonatomic,strong)MBProgressHUD *HUD;


//计算价格相关
@property(nonatomic,strong)NSString* sumPrices;

@property(nonatomic,strong)NSString*  sumServicesPrices;

@property(nonatomic,strong)NSString*  sumProductPrices;

@property(nonatomic,strong)NSString*  couponPrices;

@property(nonatomic,strong)NSString* realyPrices;

//传递参数给下级页面
//展示商品 数组
@property(nonatomic,copy)NSArray *showProductArray;


@property(nonatomic,strong)NSMutableArray *aboutPricesArray;


@property(nonatomic,strong)NSString *servicesCount;




@property(nonatomic,strong)sumPricesModel* pricesModel;

@end

@implementation JVservicesOrderViewController
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
        _submitView.backgroundColor=SUBMITVIEWCOLOR;
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
    
    self.titleView=[HDNavigationView navigationViewWithTitle:@"服务订单"];
    WEAKSELF;
    [self.titleView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.view endEditing:YES];
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    selectDiscount=NO;
    [self.view addSubview:self.titleView];
    [self layoutServicesOrderView];
    //    //    提交订单view
    [self submitViewLayout];
    [self initData];
    
}


-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self initCarData];
}

-(void)initData{
    
    
//    self.pricesView.allPrices.text=model.servicesPrices;
    //获取联系人电话
    self.productOrderInfoView.personTextField.text=[[userDefaultManager getPersonNumber] objectForKey:@"myPerson"];
    self.productOrderInfoView.phoneNumberTextField.text=[[userDefaultManager getPersonNumber] objectForKey:@"myPhone"];
    
    [self serVicesSendHttpPrices];
    
}


#pragma -mark 从服务端计算价格
-(void)serVicesSendHttpPrices{
    sumPricesModel* pricesModel=[sumPricesModel sumPrices];
    signValueObject* signV=[signValueObject shareSignValue];
    globalServicesModel* model=[signV getSelectGlobalServiceModel];
    pricesModel.scidArray=@[model.scid];
    NSMutableArray* sArray=[NSMutableArray array];
    NSMutableArray* pArray=[NSMutableArray array];

    
    NSMutableString* pidMutableStr=[NSMutableString new];
    for (NSDictionary* dic in self.dataArray) {
        [sArray addObject:dic[@"scid"]];
        for (YC_GoodsModel* mPro in dic[@"array"]) {
            NSString *str=[NSString stringWithFormat:@"%@_%ld",mPro.pid,mPro.myCount];
            [pArray addObject:str];
            NSString *strCopy=[NSString stringWithFormat:@"%@",mPro.pid];
           [pidMutableStr appendFormat:@"%@#",strCopy];
        }
    }
    
    if (sArray.count>0) {
        pricesModel.scidArray=sArray;
    }
    if (pArray.count>0) {
        NSRange range=NSMakeRange(pidMutableStr.length-1, 1);
        [pidMutableStr deleteCharactersInRange:range];
        pricesModel.pidNew = pidMutableStr;
        pricesModel.pidArray=pArray;
    }
    
    pricesModel.otype=[NSNumber numberWithInteger:2];
    
 
    _po([UtilityMethod JVDebugUrlWithdict:[UtilityMethod getObjectData:pricesModel] nsurl:sumPricesAPI]);
    
    [HTTPconnect sendPOSTHttpWithUrl:sumPricesAPI parameters:[UtilityMethod getObjectData:pricesModel] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            if (selectDiscount==NO) {
                self.sumProductPrices=globalPrices(responseObject[@"opmoney"]);
                self.pricesView.productPrices.text=[UtilityMethod addRMB:globalPrices(responseObject[@"opmoney"])];
                self.realyPrices=globalPrices(responseObject[@"paymoney"]);
                self.pricesLabel.text=[UtilityMethod addRMB:globalPrices(responseObject[@"paymoney"])];
                self.sumServicesPrices=globalPrices(responseObject[@"ocmoney"]);
                self.pricesView.servicesPrices.text=[UtilityMethod addRMB:globalPrices(responseObject[@"ocmoney"])];
                self.couponPrices=globalPrices(responseObject[@"cpmoney"]);
                self.pricesView.couponsPrices.text=[NSString stringWithFormat:@"-%@",[UtilityMethod addRMB:globalPrices(responseObject[@"cpmoney"])]];
            }                //判断是否有可用优惠卷
            self.sumPrices=globalPrices(responseObject[@"orimoney"]);
            sumPricesModel* parameters=[sumPricesModel sumPrices];
            parameters.pid=pidMutableStr;
            parameters.money=self.sumPrices;
            parameters.otype=@2;
            pricesModel.money=self.sumPrices;
            self.pricesModel=pricesModel;
            [HTTPconnect sendPOSTHttpWithUrl:getDiscountAbleAPI parameters:[UtilityMethod getObjectData:parameters] success:^(id responseObject) {
                if (![responseObject isKindOfClass:[NSString class]]) {
                    //优惠卷可用
                    NSNumber *array=responseObject[@"usable"];
                    if ([array integerValue]==1) {
                        selectDiscount=YES;
                        self.productOrderInfoView.couponsLabel.text=@"可用";
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


-(void)initCarData{

    self.productOrderInfoView.carNumber.text=_selectCarmodel.plateNumber;
    self.productOrderInfoView.carColor.text=_selectCarmodel.color;
    
}

-(void)layoutServicesOrderView{
    [self.view addSubview:self.osScrollView];
    self.selectCarView=[[[NSBundle mainBundle]loadNibNamed:@"JVCommonSelectCarView" owner:nil options:nil]lastObject];
    self.selectCarView.frame=CGRM(0, 0, SCREEN_WIDTH, 30);
    [self.selectCarView.rightLabel setHidden:YES];
    _selectCarView.carNameLabel.text=[NSString stringWithFormat:@"%@",_selectCarmodel.csname];
//    [self.selectCarView.tapChangeCarView addTapGestureRecognizerWithTarget:self action:@selector(comebackCarManager)];
    [self.osScrollView addSubview:self.selectCarView];
    
    //判断 是否显示商品
    BOOL isShow=NO;
    NSMutableArray* productArray=[NSMutableArray array];
    for (NSDictionary* dic  in self.dataArray) {
        NSArray* array=dic[@"array"];
        if (array.count>0) {
            isShow=YES;
            [productArray addObjectsFromArray:array];
    }
    self.showProductArray=productArray;
    UIView* painterView;
    if (isShow) {
        self.displayProductView=[[JVdisplayProductView alloc]init];
        self.displayProductView.frame=CGRM(0, 40, SCREEN_WIDTH, 60);
        [self.displayProductView addTapGestureRecognizerWithTarget:self action:@selector(lookProductInfoCLick)];
        self.displayProductView.productArray=productArray;
        [self.osScrollView addSubview:self.displayProductView];
        painterView=self.displayProductView;
    }else{
        UIView* serviceCountView=[[UIView alloc]initWithFrame:CGRM(0, 40, SCREEN_WIDTH, 35)];
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
        self.servicesCount=[NSString stringWithFormat:@"共%ld种服务",(unsigned long)self.dataArray.count];
        [bu setTitle:_servicesCount forState:0];
        [bu setTitleColor:[UIColor blackColor] forState:0];
        [bu addTarget:self action:@selector(lookServiceCount) forControlEvents:UIControlEventTouchUpInside];
        bu.contentHorizontalAlignment = UIControlContentHorizontalAlignmentRight;
//        bu.contentEdgeInsets = UIEdgeInsetsMake(0,0, 0, 10);
        bu.titleLabel.font=FONT14;
        [serviceCountView addSubview:bu];
        serviceCountView.backgroundColor=[UIColor whiteColor];
        [self.osScrollView addSubview:serviceCountView];
    }
    
    self.productOrderInfoView=[[[NSBundle mainBundle]loadNibNamed:@"JVproductOrderInfoView" owner:nil options:nil]lastObject];
    self.productOrderInfoView.frame=CGRM(0,CGRectGetMaxY(painterView.frame)+15, SCREEN_WIDTH, 540);
    
    self.productOrderInfoView.remarksTextField.moveView=self.osScrollView;
    [self.productOrderInfoView.selectTime addTapGestureRecognizerWithTarget:self action:@selector(selectTImeClick)];
    [self.productOrderInfoView.locationLabel addTapGestureRecognizerWithTarget:self action:@selector(pushLocationVC)];
    [self.productOrderInfoView.couponsLabel addTapGestureRecognizerWithTarget:self action:@selector(pushYHJVC)];
    self.productOrderInfoView.personTextField.moveView = _osScrollView;
    self.productOrderInfoView.phoneNumberTextField.moveView = _osScrollView;
    self.pricesView=[[[NSBundle mainBundle]loadNibNamed:@"JV_newPricesView" owner:nil options:nil]lastObject];
    self.pricesView.frame=CGRM(0, CGRectGetMaxY(self.productOrderInfoView.frame)+20, SCREEN_WIDTH, 115);

    [self.osScrollView addSubview:self.productOrderInfoView];
    [self.osScrollView addSubview:self.pricesView];
    }

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
    self.pricesLabel.textColor=WHITECOLOR;
    [self.submitView addSubview:self.pricesLabel];
    self.submitBtn=[[UIButton alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-submitWith, 0, submitWith, self.submitView.frame.size.height)];
    self.submitBtn.backgroundColor=SUBMITCOLOR;
    self.submitBtn.titleLabel.textColor=[UIColor whiteColor];
    [self.submitBtn setTitle:@"提交" forState:UIControlStateNormal];
    [self.submitBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.submitView addSubview:self.submitBtn];
    [self.submitBtn addTarget:self action:@selector(submitOrder) forControlEvents:UIControlEventTouchUpInside];
}


#pragma -mark 选择优惠卷
-(void)pushYHJVC{
    if (selectDiscount==YES) {
        YC_SelectDiscountViewController* vc=[[YC_SelectDiscountViewController alloc]init];
        _po([UtilityMethod getObjectData:self.pricesModel]);
        vc.sendModel=self.pricesModel;
        WEAKSELF;
        vc.pricesArrayblock=^(NSArray* array, NSString * cpid){
            weakSelf.sumProductPrices=array[1];
            weakSelf.pricesView.productPrices.text=[UtilityMethod addRMB:array[1]];
            weakSelf.realyPrices=array[4];
            weakSelf.pricesLabel.text=[UtilityMethod addRMB:array[4]];
            weakSelf.sumServicesPrices=array[0];
            weakSelf.pricesView.servicesPrices.text=[UtilityMethod addRMB:array[0]];
            weakSelf.couponPrices=array[3];
            weakSelf.pricesView.couponsPrices.text= [UtilityMethod addSubRMB: array[3]];
            weakSelf.cpid = cpid;
            weakSelf.productOrderInfoView.couponsLabel.text=@"已使用";
        };
        [self.navigationController pushViewController:vc animated:NO];
    }
}


#pragma -mark pushLocationVC
-(void)pushLocationVC{
    [self initMap];
    HDSelectUserLocationViewController* vc=[[HDSelectUserLocationViewController alloc]init];
    vc.mapView = self.mapView;
    vc.search  = self.search;
    vc.title=@"请单击选择位置";
    [self.navigationController pushViewController:vc animated:NO];
    //保存  经纬度 详细地址
    WEAKSELF;
    vc.locationBlock=^(NSDictionary* dict){
        weakSelf.latitude=[dict objectForKey:@"latitude"];
        weakSelf.longitude=[dict objectForKey:@"longitude"];
        weakSelf.locationInfo=[dict objectForKey:@"locationInfo"];
        weakSelf.productOrderInfoView.locationLabel.text=@"已选择";
        weakSelf.productOrderInfoView.locationLabel.textColor = [UIColor blackColor];
    };
}
#pragma -mark 查看商品详情
-(void)lookProductInfoCLick{
    
    JVDisplayMaintenanceServicesViewController* vc=[[JVDisplayMaintenanceServicesViewController alloc]init];
    vc.dataArray=self.dataArray;
    [self.navigationController pushViewController:vc animated:NO];
}



#pragma -mark 查看服务详情
-(void)lookServiceCount{
    
    JVDisplayMaintenanceServicesViewController* vc=[[JVDisplayMaintenanceServicesViewController alloc]init];
    vc.price = self.pricesView.servicesPrices.text;
    vc.dataArray=self.dataArray;
    [self.navigationController pushViewController:vc animated:NO];
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

#pragma -mark 选择时间
-(void)selectTImeClick{
    YC_SelectTimeViewController * vc = [[YC_SelectTimeViewController alloc] init];
    [self.navigationController pushViewController:vc animated:NO];
    WEAKSELF;
    vc.selectViewController=^(NSString* str){
//
//        weakSelf.periodTime=str;
    
//        weakSelf.productOrderInfoView.selectTime.text=[NSString stringWithFormat:@"%@ %@",weakSelf.fromServiceTime,weakSelf.periodTime];
        weakSelf.productOrderInfoView.selectTime.text=[NSString stringWithFormat:@"%@",str];
        weakSelf.productOrderInfoView.selectTime.textColor = [UIColor blackColor];
    };
    
}



#pragma -mark 提交订单
-(void)submitOrder{
    //验证输入
    if (self.productOrderInfoView.personTextField.text.length<1) {
        warn(@"请输入联系人");
        return;
    }
    if (self.productOrderInfoView.phoneNumberTextField.text.length<1) {
        warn(@"请输入联系电话");
        return;
    }
    if (self.productOrderInfoView.phoneNumberTextField.text.length!=11) {
        warn(@"联系电话手机号11位");
        return;
    }
    if (![self.productOrderInfoView.locationLabel.text isEqualToString:@"已选择"]) {
        warn(@"请选择你的位置");
        return;
    }
    if ([self.productOrderInfoView.selectTime.text isEqualToString:@"请选择预约时间"]) {
        warn(@"请选择预约时间");
        return;
    }
    
    
    
    JVserviceOrderSubmitModel* orderModel=[[JVserviceOrderSubmitModel alloc]init];
    orderModel.ucid=self.selectCarmodel.ucid;
    //预约时间
    orderModel.timeDescriptionStr=self.productOrderInfoView.selectTime.text;
    orderModel.contact=self.productOrderInfoView.personTextField.text;
    orderModel.phone=self.productOrderInfoView.phoneNumberTextField.text;
    orderModel.plateNumber=self.selectCarmodel.plateNumber;
    orderModel.color=self.selectCarmodel.color;
    orderModel.lat=self.latitude;
    orderModel.lng=self.longitude;
    orderModel.address=self.locationInfo;
    orderModel.cpid=self.cpid==nil?@"":self.cpid;
    orderModel.payWayDescriotion=self.productOrderInfoView.payStyleDescription;
    orderModel.payWay=self.productOrderInfoView.payStyle;
    orderModel.remark=self.productOrderInfoView.remarksTextField.text;
    orderModel.carDescription=[self.selectCarmodel getCarDescription];
    
//    NSMutableArray* array=[NSMutableArray array];
//    for (NSInteger i=0; i<self.dataArray.count; i++) {
//        NSMutableDictionary* dict=[NSMutableDictionary dictionary];
//        NSMutableDictionary* proDict=[NSMutableDictionary dictionary];
//        for (YC_GoodsModel* ycMO in self.dataArray[i][@"array"]) {
//            [proDict setObject:ycMO.pbuynum forKey:ycMO.pid];
//        }
//        [dict setObject:proDict forKey:self.dataArray[i][@"scid"]];
////        [array addObject:dict];
//    }
    
    NSMutableDictionary* httpDict=[NSMutableDictionary dictionary];
    
    for (NSInteger i=0; i<self.dataArray.count; i++) {
        NSMutableDictionary* proDict=[NSMutableDictionary dictionary];
        for (YC_GoodsModel* ycMO in self.dataArray[i][@"array"]) {
            [proDict setObject:[NSNumber numberWithInteger:ycMO.myCount] forKey:ycMO.pid];
        }
        [httpDict setObject:proDict forKey:self.dataArray[i][@"scid"]];
        //        [array addObject:dict];
    }
    orderModel.httpDataDict=httpDict;
    //保存本地用户 电话
    [userDefaultManager setPerson:self.productOrderInfoView.personTextField.text number:self.productOrderInfoView.phoneNumberTextField.text];
    //传递参数给下级页面
    _aboutPricesArray=[NSMutableArray array];
    [_aboutPricesArray addObject:self.sumProductPrices];
    [_aboutPricesArray addObject:self.sumServicesPrices];
    [_aboutPricesArray addObject:self.couponPrices];
    [_aboutPricesArray addObject:self.realyPrices];
    [_aboutPricesArray addObject:self.sumPrices];
    
    JVaffirmServicesOrderViewController* vc=[[JVaffirmServicesOrderViewController alloc]init];
    vc.showProductArray=self.showProductArray;
    vc.model=orderModel;
    vc.aboutPricesArray=self.aboutPricesArray;
    vc.servicesCount=self.servicesCount;
    vc.dispalyProductArray=self.dataArray;
    [self.navigationController pushViewController:vc animated:NO];
}




@end

