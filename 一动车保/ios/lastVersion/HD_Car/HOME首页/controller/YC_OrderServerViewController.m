//
//  YC_OrderServerViewController.m
//  HD_Car
//
//  Created by hydom on 8/11/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_OrderServerViewController.h"
#import "HDNavigationView.h"
#import "YC_orderView.h"
#import "userDefaultManager.h"
#import "loginViewController.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "signValueObject.h"
#import "globalServicesModel.h"
#import "YC_SelectDiscountViewController.h"
#import "JVzhifubao.h"
#import <AlipaySDK/AlipaySDK.h>

@interface YC_OrderServerViewController (){
    BOOL selectDiscount;
}

/**底部视图*/
@property(nonatomic,strong)UIView* submitView;
/**金额*/
@property(nonatomic,strong)UILabel* pricesLabel;
/**提交订单*/
@property(nonatomic,strong)UIButton* submitBtn;

/*订单页面*/
@property(nonatomic,strong)YC_orderView * orderView;

//弹出层
@property(nonatomic,strong)UIView * showView;

@property(nonatomic,strong)sumPricesModel * pricesModel;



/**金额相关*/
@property(nonatomic,strong)NSString* CT_allPrices;

@property(nonatomic,strong)NSString*  CT_realyPrices;

@property(nonatomic,strong)NSString*  CT_privilegePrices;
@end

@implementation YC_OrderServerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    selectDiscount=NO;
    self.view.backgroundColor = [UIColor whiteColor];
    [self initNavViewWithtitle:@"服务订单"];
    [self submitViewLayout];
    [self initOrderView];
    [self goodsHttpPrices];
}

//判断优惠券
#pragma -mark 从服务端计算价格
-(void)goodsHttpPrices{
    sumPricesModel* pricesModel=[sumPricesModel sumPrices];
    NSString* str=[NSString stringWithFormat:@"%@_%@",_model.pID,_model.pNumber];
    pricesModel.pid=str;
    pricesModel.productCount=_model.pNumber;
    pricesModel.otype=[NSNumber numberWithInteger:3];
    [HTTPconnect sendPOSTHttpWithUrl:sumPricesAPI parameters:[UtilityMethod getObjectData:pricesModel] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            
            if (selectDiscount==NO) {
                self.CT_allPrices=globalPrices(responseObject[@"orimoney"]);
                self.orderView.payLabel.text=[UtilityMethod addRMB:globalPrices(responseObject[@"orimoney"])];
            self.CT_realyPrices=globalPrices(responseObject[@"paymoney"]);
            self.pricesLabel.text=[UtilityMethod addRMB:globalPrices(responseObject[@"paymoney"])];
            self.CT_privilegePrices=globalPrices(responseObject[@"cpmoney"]);
            self.orderView.saleLabel.text=[UtilityMethod addRMB:globalPrices(responseObject[@"cpmoney"])];
            }
            //判断是否有可用优惠卷
            sumPricesModel* pricesModelCopy=[sumPricesModel sumPrices];
            NSString* strCopy=[NSString stringWithFormat:@"%@",_model.pID];
            pricesModelCopy.otype=[NSNumber numberWithInteger:3];
            pricesModelCopy.money=self.CT_allPrices;
            pricesModelCopy.pid=strCopy;
            pricesModelCopy.productCount=_model.pNumber;
            
            self.pricesModel=pricesModelCopy;
            [HTTPconnect sendPOSTHttpWithUrl:getDiscountAbleAPI parameters:[UtilityMethod getObjectData:pricesModelCopy] success:^(id responseObject) {
                if (![responseObject isKindOfClass:[NSString class]]) {
                    //优惠卷可用
                    NSNumber *array=responseObject[@"usable"];
                    if ([array integerValue]==1) {
                        selectDiscount=YES;
                        self.orderView.discountButton.titleLabel.text=@"可用";
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

-(UIView *)submitView{
    if (!_submitView) {
        _submitView=[[UIView alloc]initWithFrame:CGRectMake(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 50)];
        _submitView.backgroundColor=[UIColor blackColor];
    }
    return _submitView;
}


-(void)initOrderView
{
    UIScrollView * scrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64-50)];
    scrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 600);
    scrollView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:scrollView];
    
    self.orderView = [[[NSBundle mainBundle]loadNibNamed:@"YC_orderView" owner:nil options:nil]lastObject];
    [self.orderView.discountButton addTapGestureRecognizerWithTarget:self action:@selector(onceGoodsSelectCountVC)];
    self.orderView.model = self.model;
    self.orderView.frame = CGRectMake(0, 0, SCREEN_WIDTH, 574);
    self.orderView.vc=self;
    self.view.backgroundColor = self.orderView.backgroundColor;
    [scrollView addSubview:self.orderView];

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
    self.pricesLabel.text=self.model.pPay;
    self.pricesLabel.textColor=WHITECOLOR;
    [self.submitView addSubview:self.pricesLabel];
    self.submitBtn=[[UIButton alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-submitWith, 0, submitWith, self.submitView.frame.size.height)];
    self.submitBtn.backgroundColor=[UIColor redColor];
    self.submitBtn.titleLabel.textColor=[UIColor whiteColor];
    [self.submitBtn setTitle:@"支付" forState:UIControlStateNormal];
    [self.submitBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.submitView addSubview:self.submitBtn];
    [self.submitBtn addTarget:self action:@selector(submitOrder) forControlEvents:UIControlEventTouchUpInside];
}

-(void)submitOrder
{
    //判断用户登录
    NSMutableDictionary * pamerates = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    if (!pamerates) {
        loginViewController * vc = [[loginViewController alloc] init];
        [self presentViewController:vc animated:YES completion:nil];
    } else {
        //成功登录后判断地址
        if ([self.orderView.address2.text isEqual:@""]) {
            warn(@"请选择地址");
            return;
        }
        if (self.orderView.phoneTextField.text.length!=11) {
            warn(@"手机号11位");
            return;
        }if ([self.orderView.userTextField.text isEqual:@""]) {
            warn(@"请输入联系人");
            return;
        }
        [self payHttpPost];
        
    }
    
}

#pragma -mark 提交订单
-(void)payHttpPost{
    NSMutableDictionary * pamerates = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [pamerates  setObject:self.model.pID forKey:@"pid"];
    [pamerates  setObject:self.model.pNumber forKey:@"pnumber"];
    [pamerates  setObject:self.orderView.phoneTextField.text forKey:@"phone"];
    [pamerates  setObject:self.orderView.userTextField.text forKey:@"contact"];
    //地区ID
    [pamerates  setObject:self.orderView.addressDict[@"id"] forKey:@"aid"];
    [pamerates  setObject:self.orderView.addressDict[@"detail"] forKey:@"address"];
    if(self.model.pCpid) {
        [pamerates  setObject:self.model.pCpid forKey:@"cpid"];
    }
    [pamerates  setObject:self.orderView.payStyle forKey:@"payWay"];
    
    showHUD;
    [HTTPconnect sendPOSTHttpWithUrl:postOrderAPI parameters:pamerates success:^(id responseObject) {
        [hud hide:YES];
        NSInteger s=[responseObject[@"payAction"] integerValue];
        switch (s) {
            case 1:
            {
                //支付宝
                if ([self.orderView.payStyle isEqualToString:@"2"]) {
                    [self payFromZhifubao:responseObject[@"onum"]];
                    return;
                }
                //微信支付
                if ([self.orderView.payStyle isEqualToString:@"4"]) {
                    [self payFromZhifubao:responseObject[@"onum"]];
                    return;
                }
            }
                break;
            case 2:
            {
                [self showPopView];
            }
                break;
            default:
            {
                warn(@"对不起,您的余额不足!");
            }
                break;
        }
    } failure:^(NSError *error) {
        [hud hide:YES];
        warn(@"网络错误");
    }];
    
        
        
}


#pragma -mark支付宝支付
-(void)payFromZhifubao:(NSString*)onum{
    
    JVcommonProduct* product=[JVcommonProduct new];
    product.orderID=onum;
    signValueObject* signV=[signValueObject shareSignValue];
    globalServicesModel* servModel=[signV getSelectGlobalServiceModel];
    product.productName=servModel.scname;
    product.productDescription=servModel.scremark;
    product.orderPrices=[self.CT_realyPrices doubleValue];
    
    WEAKSELF;
    [JVzhifubao JVzfbWith:product success:^{
        [weakSelf showPopView];
    } failure:^{
        warn(@"支付失败，请重新支付");
    }];
}


#pragma -mark支付成功
-(void)showPopView
{
    [userDefaultManager setPerson:self.orderView.userTextField.text number:self.orderView.phoneTextField.text];
    if (!_showView) {
        _showView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
        _showView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.6];
        _showView.userInteractionEnabled = YES;
        [self.view addSubview:_showView];
    
        UIImageView * alertView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-80, SCREEN_WIDTH-80)];
        alertView.center = _showView.center;
        alertView.image = [UIImage imageNamed:@"goodsOrder.png"];
        [_showView addSubview:alertView];
        } else {
        _showView.hidden = NO;
    }
    [self performSelector:@selector(backToHome) withObject:nil afterDelay:3];
}

-(void)backToHome
{
    UIViewController * vc = self.navigationController.viewControllers[0];
    [self.navigationController popToViewController:vc animated:YES];
}

#pragma -mark 选择优惠卷
-(void)onceGoodsSelectCountVC{
    
    if (selectDiscount==YES) {
        YC_SelectDiscountViewController* vc=[[YC_SelectDiscountViewController alloc]init];
        vc.sendModel=self.pricesModel;
        WEAKSELF;
        vc.pricesArrayblock=^(NSArray* array){
            weakSelf.CT_allPrices=array[2];
            weakSelf.orderView.payLabel.text=[UtilityMethod addRMB:array[2]];
            weakSelf.CT_realyPrices=array[4];
            weakSelf.pricesLabel.text=[UtilityMethod addRMB:array[4]];
            weakSelf.CT_privilegePrices=array[3];
            weakSelf.orderView.saleLabel.text=[UtilityMethod addRMB:array[3]];
            [_orderView.discountButton setTitle:@"已选择" forState:UIControlStateNormal];
        };
        [self.navigationController pushViewController:vc animated:NO];
    }
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
