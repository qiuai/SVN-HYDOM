//
//  YC_VIPBuyViewController.m
//  HD_Car
//
//  Created by hydom on 9/10/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_VIPBuyViewController.h"
#import "JVwashCarInfoView.h"
#import "YC_VipView.h"
#import "UIImageView+WebCache.h"
#import "JVweiXinPay.h"
#import "UPPayPlugin.h"
#import "UPPayPluginDelegate.h"
#import "JVzhifubao.h"
@interface YC_VIPBuyViewController ()<UPPayPluginDelegate>

@property(nonatomic, strong)UIView * submitView;
@property(nonatomic, strong)UILabel * pricesLabel;
@property(nonatomic, strong)UIButton * submitBtn;
@property(nonatomic, strong)JVwashCarInfoView * washCarInfoView;
@property(nonatomic, weak)YC_VipView * payWayView;

@end

@implementation YC_VIPBuyViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initUI];
}

-(void)initUI{
    self.view.backgroundColor = HDfillColor;
    [self initNavViewWithtitle:@"会员卡购买"];
    WEAKSELF;
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:NO];
    }];
 
    //会员卡购买信息
    UIView * vipCardView = [[UIView alloc] initWithFrame:CGRectMake(0, 70, SCREEN_WIDTH, SCREEN_WIDTH/2)];
//    vipCardView.backgroundColor = [UIColor orangeColor];
    [self.view addSubview:vipCardView];
    [self drawBorderInView:vipCardView];
    
    UIImageView * vipCarImageView = [[UIImageView alloc] initWithFrame:CGRectMake(15, 25, SCREEN_WIDTH-30, CGRectGetHeight(vipCardView.frame)-50)];
    [vipCardView addSubview:vipCarImageView];
    [vipCarImageView sd_setImageWithURL:imageURLWithPath(self.model.cppimg) placeholderImage:[UIImage imageNamed:FillImage]];
    
    //支付选择view
    YC_VipView * selectPayView = [[NSBundle mainBundle] loadNibNamed:@"YC_VipView" owner:nil options:nil].lastObject;
    selectPayView.frame=CGRM(0, SCREEN_HEIGHT-230, SCREEN_WIDTH, 150);
    [self.view addSubview:selectPayView];
    self.payWayView = selectPayView;
 
    
    //支付栏
    [self submitViewLayout];
 }



-(UIView *)submitView{
    if (!_submitView) {
        _submitView=[[UIView alloc]initWithFrame:CGRectMake(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 50)];
        _submitView.backgroundColor=SUBMITVIEWCOLOR;
    }
    return _submitView;
}
//提交栏
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
    self.pricesLabel.text= [UtilityMethod addRMB:globalPrices(self.model.cppprice)];
    self.pricesLabel.textColor=WHITECOLOR;
    [self.submitView addSubview:self.pricesLabel];
    self.submitBtn=[[UIButton alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-submitWith, 0, submitWith, self.submitView.frame.size.height)];
    self.submitBtn.backgroundColor=SUBMITCOLOR;
    self.submitBtn.titleLabel.textColor=[UIColor whiteColor];
    [self.submitBtn setTitle:@"支付" forState:UIControlStateNormal];
    [self.submitBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.submitView addSubview:self.submitBtn];
    [self.submitBtn addTarget:self action:@selector(submitOrder:) forControlEvents:UIControlEventTouchUpInside];
}


//支付按钮
-(void)submitOrder:(UIButton *)sender
{
    sender.userInteractionEnabled=NO;
    NSMutableDictionary * parameters = [[userDefaultManager getUserWithToken] mutableCopy];
    [parameters setObject:self.model.cppid forKey:@"cppid"];
    [parameters setObject:self.payWayView.payStyle forKey:@"payWay"];
    [HTTPconnect sendPOSTHttpWithUrl:getBuyVipAPI parameters:parameters success:^(id responseObject) {
        sender.userInteractionEnabled=YES;
        _po(responseObject);
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSInteger s=[responseObject[@"payAction"] integerValue];
            switch (s) {
                case 1:
                {
                    //支付宝
                    if ([self.payWayView.payStyle isEqualToString:@"2"]) {
                        JVcommonProduct* product=[JVcommonProduct new];
                        product.orderID= responseObject[@"num"];
                        product.productName=@"会员卡购买";
                        product.productDescription=@"会员卡购买";
                        product.resultURL = zhiFuBaoPayURL;
                        product.orderPrices=[globalPrices(self.model.cppprice) doubleValue];
                        [JVzhifubao JVzfbWith:product success:^{
                            warn(@"支付成功!!");
                            NSInteger index=[[self.navigationController viewControllers]indexOfObject:self];
                            UIViewController* popVC1=[self.navigationController viewControllers][index-2];
                            [self.navigationController popToViewController:popVC1 animated:YES];
                        } failure:^{
                            warn(@"支付失败!!");
                        }];
                        return;
                    }
                    //微信支付
                    if ([self.payWayView.payStyle isEqualToString:@"4"]) {
                        [JVweiXinPay payFromWeXin:responseObject[@"num"]];
                        return;
                    }
                    //银联支付
                    if ([self.payWayView.payStyle isEqualToString:@"3"]) {
                        [UPPayPlugin startPay:responseObject[@"num"] mode:@"00" viewController:self delegate:self];
                        return;
                    }
                    
                }
                    break;
                case 2:
                {
//                    [self successPay];
                    warn(@"支付成功");
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
        sender.userInteractionEnabled=NO;
        warn(@"支付失败");
    }];
}


//画彩色虚线
-(void)drawBorderInView:(UIView *)view{
    
    CGFloat padding=10;
    //有多少个
    NSInteger count=11;
    //颜色
    UIColor* color1=COLOR(93, 175, 218, 1);
    UIColor* color2=[UIColor redColor];
    
    //宽度
    CGFloat lineWidth=(SCREEN_WIDTH-12*padding)/11.0;
    for (NSInteger i=0; i<count; i++) {
        UIView* topView=[UIView new];
        UIView* bottomView=[UIView new];
        bottomView.frame=CGRM((i+1)*padding+i*lineWidth, CGRectGetMaxY(view.bounds)-13,lineWidth, 3);
        topView.frame=CGRM((i+1)*padding+i*lineWidth, CGRectGetMinY(view.bounds)+10,lineWidth, 3);
        NSInteger j=i%2;
        if (j==0) {
            topView.backgroundColor=color1;
            bottomView.backgroundColor=color1;
        }
        else{
            topView.backgroundColor=color2;
            bottomView.backgroundColor=color2;
        }
        [view addSubview:topView];
        [view addSubview:bottomView];
    }
    
    
    
}


-(void)UPPayPluginResult:(NSString*)result{
    _po(result);
    if ([result isEqualToString:@"cancel"]) {
        warn(@"支付失败");
    } else {
        warn(@"支付成功");
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
