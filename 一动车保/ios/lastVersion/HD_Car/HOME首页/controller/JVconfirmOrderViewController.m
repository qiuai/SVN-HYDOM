//
//  JVconfirmOrderViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/31.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVconfirmOrderViewController.h"
#import "HDNavigationView.h"
#import "JVorderImportantView.h"
#import "servicesOrderInfoView.h"
#import "JVconfirmWashCarView.h"
#import "submitOrderModel.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "MBProgressHUD.h"
#import "signValueObject.h"
#import "UIImageView+WebCache.h"
#import "lookSelectLocationViewController.h"
#import "JVzhifubao.h"
#import "Global.pch"
#import "payRequsestHandler.h"
#import "WXApi.h"
#import <objc/runtime.h>
#import "JVweiXinPay.h"
#import "UPPayPlugin.h"
#import "UPPayPluginDelegate.h"
#import "AppDelegate.h"
@interface JVconfirmOrderViewController ()<WXApiDelegate,UPPayPluginDelegate>
@property(strong,nonatomic)UIScrollView* baseScrollView;
/**附近是否有技师提供服务*/
@property(strong,nonatomic)UILabel* bulletinLabel;
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

//弹出层
@property(nonatomic,strong)UIView * showView;

@property(nonatomic,strong)MBProgressHUD *HUD;



/**地图相关*/
@property (nonatomic, strong) MAMapView *mapView;
@property (nonatomic, strong) AMapSearchAPI *search;

@property (nonatomic, strong) CLLocationManager *locationManager;

@end

@implementation JVconfirmOrderViewController
-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view.window];
    }
    return _HUD;
}


-(void)initData{
    signValueObject* signV=[signValueObject shareSignValue];
    globalServicesModel* servModel=[signV getSelectGlobalServiceModel];
    [self.servicesInfoView.serviceImageView sd_setImageWithURL:imageURLWithPath(servModel.scimage) placeholderImage:[UIImage imageNamed:FillImage]];
    self.servicesInfoView.servicePrices.text=[NSString stringWithFormat:@"￥ %@",globalPrices(servModel.servicesPrices)];
    UILabel* titleLB=[[UILabel alloc]initWithFrame:CGRM(0, 0, self.servicesInfoView.servicesInfoView.frame.size.width, 20)];
    titleLB.text=servModel.scremark;
    titleLB.textColor=[UIColor blackColor];
    titleLB.font=FONT14;
    [self.servicesInfoView.servicesInfoView addSubview:titleLB];
    UILabel* infoLB=[[UILabel alloc]initWithFrame:CGRM(0, 20, self.servicesInfoView.servicesInfoView.frame.size.width, 20)];
    infoLB.text=servModel.scremark1;
    infoLB.textColor=[UIColor grayColor];
    infoLB.font=[UIFont systemFontOfSize:13];
    [self.servicesInfoView.servicesInfoView addSubview:infoLB];
    
    //设置 订单信息 数据
    self.importantView.contantNameLB.text=self.model.contact;
    self.importantView.carNameLB.text=self.model.carDescription;
    self.importantView.carNumberLB.text=self.model.plateNumber;
    self.importantView.contantPhoneLB.text=self.model.phone;
    self.importantView.serviceModelLB.text=self.model.serviceDescription;
    self.importantView.carColorLB.text=self.model.color;
    [self.confirmView.lookMyLocation addTapGestureRecognizerWithTarget:self action:@selector(pushLookMyLocation)];
    self.confirmView.payStyle.text=self.model.payWayDescription;
    self.confirmView.couponsLabel.text=[self.model.isUseCoupon isEqualToString:@"yes"]?@"已使用":@"未使用";
    self.confirmView.payPrcies.text=servModel.servicesPrices;
    self.confirmView.payPrcies.text=[UtilityMethod addRMB:self.model.payPrcies];
    self.confirmView.couponsPrices.text=[UtilityMethod addSubRMB:self.model.couponsPrices];
    self.confirmView.realityPrices.text=[UtilityMethod addRMB:self.model.realityPrices];
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
    //判断 是否有技师提供服务
    if (self.isTrue) {
        self.bulletinLabel.text=@"附近有技师为您提供服务";
    }else{
        if (self.backView==nil&&self.warinView==nil) {
            [self noPersonForServicesLayout];
        }
    }
    [self initData];
    
    _po([UtilityMethod getObjectData:self.model]);
}

-(void)noPersonForServicesLayout{
    UIView* back=[[UIView alloc]initWithFrame:[UIScreen mainScreen].bounds];
    back.backgroundColor=[UIColor blackColor];
    back.alpha=0.3;
    [self.view addSubview:back];
    self.backView=back;
    
    UIView* warinView=[[UIView alloc]initWithFrame:[UIScreen mainScreen].bounds];
    warinView.backgroundColor=[UIColor clearColor];
    [self.view addSubview:warinView];
    self.warinView=warinView;
    
    UIImageView* infoView=[[UIImageView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-80, SCREEN_WIDTH-80)];
    infoView.center = warinView.center;
    infoView.image=[UIImage imageNamed:@"ordingFault.png"];
    [self.warinView addSubview:infoView];
    //刷新  修改 按钮
    CGFloat height=35;
    CGFloat y=13;
    UIButton* comebackBtn=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH/2.0, SCREEN_HEIGHT-height-y, 65,height)];
    [comebackBtn setTitle:@"返回" forState:0];
    [comebackBtn setTitleColor:[UIColor blackColor] forState:0];
    [self.warinView addSubview:comebackBtn];
    [comebackBtn.layer setMasksToBounds:YES];
    [comebackBtn.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [comebackBtn.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef colorref=COLOR(221, 221, 221, 1).CGColor;
    [comebackBtn.layer setBorderColor:colorref];
    comebackBtn.titleLabel.font=FONT14;
    [comebackBtn addTarget:self action:@selector(combaceClick) forControlEvents:UIControlEventTouchUpInside];
    comebackBtn.backgroundColor=[UIColor whiteColor];
    
    UIButton* submitBTN=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH-20-65,SCREEN_HEIGHT-height-y, 65,height)];
    submitBTN.titleLabel.font=FONT14;
    [submitBTN setTitle:@"刷新" forState:0];
    submitBTN.backgroundColor=[UIColor whiteColor];
    [submitBTN setTitleColor:[UIColor blackColor] forState:0];
    [submitBTN.layer setMasksToBounds:YES];
    [submitBTN.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [submitBTN.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef scolorref=colorref;
    [submitBTN.layer setBorderColor:scolorref];
    [self.warinView addSubview:submitBTN];
    [submitBTN addTarget:self action:@selector(updateTap) forControlEvents:UIControlEventTouchUpInside];
    CGFloat imageW=SCREEN_WIDTH/3.0*2;
    CGFloat imageH=imageW/1.6;
    UIImageView* image=[[UIImageView alloc]initWithFrame:CGRM(SCREEN_WIDTH-imageW-20,submitBTN.frame.origin.y-imageH+10, imageW, imageH)];
    _po(NSStringFromCGRect(image.frame));
    [self.warinView addSubview:image];
    image.image=[UIImage imageNamed:@"dianjiashuaxin"];
}

-(void)layoutUI{
    self.baseScrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64-50)];
    self.baseScrollView.backgroundColor=HDfillColor;
    self.baseScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 600);
    [self.view addSubview:self.baseScrollView];
    self.bulletinLabel=[[UILabel alloc]initWithFrame:CGRM(0, 0, SCREEN_WIDTH, 30)];
    self.bulletinLabel.font=FONT14;
    self.bulletinLabel.textAlignment=1;
    self.bulletinLabel.textColor=[UIColor redColor];
    self.bulletinLabel.backgroundColor=[UIColor whiteColor];
    [self.baseScrollView addSubview:self.bulletinLabel];
    self.importantView=[[[NSBundle mainBundle]loadNibNamed:@"JVorderImportantView" owner:nil options:nil]lastObject];
    self.importantView.frame=CGRM(0, CGRectGetMaxY(self.bulletinLabel.frame), SCREEN_WIDTH, 130);
    [self.baseScrollView addSubview:self.importantView];
    self.servicesInfoView=[[[NSBundle mainBundle]loadNibNamed:@"servicesOrderInfoView" owner:nil options:nil]lastObject];
    self.servicesInfoView.frame=CGRM(0, CGRectGetMaxY(self.importantView.frame), SCREEN_WIDTH, 130);
    [self.baseScrollView addSubview:self.servicesInfoView];
    self.confirmView=[[[NSBundle mainBundle]loadNibNamed:@"JVconfirmWashCarView" owner:nil options:nil]lastObject];
    self.confirmView.frame=CGRM(0, CGRectGetMaxY(self.servicesInfoView.frame), SCREEN_WIDTH, 285);
    [self.baseScrollView addSubview:self.confirmView];
    //    提交订单view
    [self submitViewLayout];
}

#pragma -mark submitView Layout
-(void)submitViewLayout{
    self.submitView=[[UIView alloc]initWithFrame:CGRectMake(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 50)];
    self.submitView.backgroundColor=[UIColor whiteColor];
    [self.view addSubview:self.submitView];
    UIButton* comebackBtn=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH/2.0, 10, 65,30)];
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
    UIButton* submitBTN=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH-20-65, 10, 65,30)];
    submitBTN.titleLabel.font=FONT14;
    [submitBTN setTitle:@"支付" forState:0];
    submitBTN.backgroundColor=SUBMITCOLOR;
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

#pragma -mark 刷新
-(void)updateTap{
    
    [HTTPconnect sendPOSTHttpWithUrl:getTechnicianAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            if ([[responseObject objectForKey:@"canserver"] intValue]==1) {
                self.isTrue=YES;
                [self.backView setHidden:YES];
                [self.warinView setHidden:YES];
            }else{
                self.isTrue=NO;
                [self.warinView setHidden:YES];
                [self.view addSubview:self.HUD];
                self.HUD.labelText =@"刷新中...";
                self.HUD.mode=MBProgressHUDModeText;
                [self.HUD showAnimated:YES whileExecutingBlock:^{
                    sleep(2);
                } completionBlock:^{
                    [self.HUD removeFromSuperview];
                    [self.warinView setHidden:NO];
                }];
            }
        }else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        warn(@"请检查网络");
    }];
}

#pragma -mark 提交订单
-(void)submitClick:(UIButton*)btn{
    _po([UtilityMethod getObjectData:self.model]);
    btn.userInteractionEnabled=NO;
    NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    unsigned int propsCount;
    objc_property_t *props = class_copyPropertyList([self.model class], &propsCount);
    for(int i = 0;i < propsCount; i++)
    {
        objc_property_t prop = props[i];
        NSString *propName = [NSString stringWithUTF8String:property_getName(prop)];
            id value = [self.model valueForKey:propName];
            if(value != nil)
            {
                [dic setObject:value forKey:propName];
            }
    }
    [dict addEntriesFromDictionary:dic];
    
//    _po([UtilityMethod JVDebugUrlWithdict:dict nsurl:submitWashCarAPI]);
    
    
    
    
    [HTTPconnect sendGETWithUrl:submitWashCarAPI parameters:dict success:^(id responseObject) {
        
        btn.userInteractionEnabled=YES;
        if (![responseObject isKindOfClass:[NSString class]]) {
            
            NSInteger s=[responseObject[@"payAction"] integerValue];
            switch (s) {
                case 1:
                {
                    //支付宝
                    if ([self.model.payWay isEqualToString:@"2"]) {
                        [self payFromZhifubao:responseObject[@"onum"]];
                        return;
                    }
                    //微信支付
                    if ([self.model.payWay isEqualToString:@"4"]) {
                        AppDelegate * appdelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
                        appdelegate.playViewController=self;
                         [JVweiXinPay payFromWeXin:responseObject[@"onum"]];
                        return;
                    }
                    //银联支付
                    if ([self.model.payWay isEqualToString:@"3"]) {
                        [UPPayPlugin startPay:responseObject[@"onum"] mode:@"00" viewController:self delegate:self];
                        return;
                    }

                }
                    break;
                case 2:
                {
                    [self successPay];
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
#pragma -mark 微信支付
-(void)payFromWeXin:(NSString*)onum{

    BOOL state=[WXApi isWXAppInstalled];
//    BOOL state=[[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"weixin://"]];
    if (state==NO) {
        warn(@"请安装微信！！");return;
    }
//    //创建支付签名对象
//    payRequsestHandler *requse = [[payRequsestHandler alloc] init];
//    //初始化支付签名对象
//    [requse init:APP_ID mch_id:MCH_ID];
//    //设置密钥
//    [requse setKey:PARTNER_ID];
//    //获取到实际调起微信支付的参数后，在app端调起支付
//    NSMutableDictionary *dict = [requse sendPay_demo:onum];
//    NSMutableString *stamp  = [dict objectForKey:@"timestamp"];
//        //调起微信支付
//        PayReq* req             = [[PayReq alloc] init];
//        req.openID              = [dict objectForKey:@"appid"];
//        req.partnerId           = [dict objectForKey:@"partnerid"];
//        req.prepayId            = onum;
//        req.nonceStr            = [dict objectForKey:@"noncestr"];
//        req.timeStamp           = stamp.intValue;
//        req.package             = [dict objectForKey:@"package"];
//        req.sign                = [dict objectForKey:@"sign"];
//        [WXApi sendReq:req];
}






#pragma -mark支付宝支付
-(void)payFromZhifubao:(NSString*)onum{

    JVcommonProduct* product=[JVcommonProduct new];
    product.orderID=onum;
    signValueObject* signV=[signValueObject shareSignValue];
    globalServicesModel* servModel=[signV getSelectGlobalServiceModel];
    product.productName=servModel.scname;
    product.productDescription=servModel.scremark;
    product.orderPrices=[self.model.realityPrices doubleValue];
    product.resultURL=zhiFuBaoOrderURL;
    WEAKSELF;
    [JVzhifubao JVzfbWith:product success:^{
        [weakSelf successPay];
    } failure:^{
        warn(@"支付失败，请重新支付");
    }];

}


#pragma -mark 支付成功
-(void)successPay{
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

#pragma mark 银联回调
-(void)UPPayPluginResult:(NSString*)result{
    _po(result);
    if ([result isEqualToString:@"cancel"]) {
        warn(@"支付失败");
    } else {
        [self successPay];
    }
}

#pragma -mark 微信支付成功
-(void)onResp:(BaseResp*)resp
{
//    NSString *strMsg = [NSString stringWithFormat:@"errcode:%d", resp.errCode];
    NSString *strTitle;
    if([resp isKindOfClass:[SendMessageToWXResp class]])
    {
        strTitle = [NSString stringWithFormat:@"发送媒体消息结果"];
    }
    if([resp isKindOfClass:[PayResp class]]){
        //支付返回结果，实际支付结果需要去微信服务器端查询
        strTitle = [NSString stringWithFormat:@"支付结果"];
        
        switch (resp.errCode) {
            case WXSuccess:
                [self successPay];

                break;
                
            default:
                warn(@"支付失败!");
                //                NSLog(@"错误，retcode = %d, retstr = %@", resp.errCode,resp.errStr);
                break;
        }
    }

}




@end
