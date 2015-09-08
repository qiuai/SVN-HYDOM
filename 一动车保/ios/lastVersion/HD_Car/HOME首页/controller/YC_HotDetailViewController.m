//
//  YC_HotDetailViewController.m
//  HD_Car
//
//  Created by hydom on 8/11/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_HotDetailViewController.h"
#import "JVshowGoodsForOrderViewController.h"
#import "HDNavigationView.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "CYScollViewContainer.h"
#import "YC_CarDetailInforView.h"
#import "YC_OrderServerViewController.h"
#import "YC_GoodsOrderModerl.h"
#import <MBProgressHUD.h>
#import "loginViewController.h"
#import "Global.pch"
@interface YC_HotDetailViewController ()
@property(nonatomic,strong)HDNavigationView* navView;
@property(nonatomic,weak)CYScollViewContainer* imagePageVC;
@property(strong,nonatomic)YC_CarDetailInforView* detailInfoView;
@property(strong,nonatomic)UIScrollView* scrollView;
@property(strong, nonatomic)YC_GoodsOrderModerl * model;

@property(nonatomic,strong)UIView* submitView;
/**金额*/
@property(nonatomic,strong)UILabel* pricesLabel;
/**提交订单*/
@property(nonatomic,strong)UIButton* submitBtn;
@property(nonatomic,strong)MBProgressHUD *HUD;
@end

@implementation YC_HotDetailViewController

-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view.window];
    }
    return _HUD;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    _navView=[HDNavigationView navigationViewWithTitle:@"产品详情"];
    WEAKSELF;
    [_navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:_navView];

    self.scrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64)];
    self.scrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 600);
    [self.view addSubview:self.scrollView];
    CYScollViewContainer* imagePageViewVC=[[CYScollViewContainer alloc]init];
    [self addChildViewController:imagePageViewVC];
    imagePageViewVC.view.frame=CGRM(0, 0, SCREEN_WIDTH, SCREEN_WIDTH*0.5);
    [self.scrollView addSubview:imagePageViewVC.view];
    self.imagePageVC=imagePageViewVC;
    self.detailInfoView=[[[NSBundle mainBundle]loadNibNamed:@"YC_CarDetailInforView" owner:nil options:nil]lastObject];
    self.detailInfoView.contentSizeBlock = ^(CGFloat height){
        weakSelf.scrollView.contentSize = CGSizeMake(weakSelf.scrollView.contentSize.width, weakSelf.scrollView.contentSize.height + height);
    };
    [self.detailInfoView hideFitCar:self.isFixCar];
    self.detailInfoView.vc = self;
    self.detailInfoView.frame=CGRM(0, 0.5*SCREEN_WIDTH, SCREEN_WIDTH, 400);
    [self.scrollView addSubview:self.detailInfoView];
    [self submitViewLayout];
    [self getDate];
    
}

-(UIView *)submitView{
    if (!_submitView) {
        _submitView=[[UIView alloc]initWithFrame:CGRectMake(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 50)];
        _submitView.backgroundColor=[UIColor blackColor];
    }
    return _submitView;
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
    self.pricesLabel.text=@"￥00.00";
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

-(void)getDate{
    NSMutableDictionary* dic=[NSMutableDictionary new];
    [dic setObject:self.productID forKey:@"pid"];
    [HTTPconnect sendPOSTHttpWithUrl:getProductInfoAPI parameters:dic success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSArray* imageArray= responseObject[@"imglist"];
            NSMutableArray* imageURL=[NSMutableArray array];
            for (NSDictionary* dic in imageArray) {
                [imageURL addObject: imageURLWithPath(dic[@"pimg"])];
            }
            [self.imagePageVC setImageArray:imageURL];
            self.detailInfoView.goodsTitle=responseObject[@"pname"];
            self.detailInfoView.goodsPrices=responseObject[@"price"];
            self.detailInfoView.pingjia=responseObject[@"pcomts"];
            self.detailInfoView.purl=htmlWithPath(responseObject[@"purl"]);
            self.detailInfoView.servicesArray=responseObject[@"suplist"];
            self.detailInfoView.parameter=responseObject[@"paramlist"];
            self.detailInfoView.fitCarArray = responseObject[@"cmlist"];
            [self dataToModel:responseObject];
            [self.detailInfoView setUpUserInterface];
            [self changePayLabel:[NSString stringWithFormat:@"%@", self.detailInfoView.goodsPrices] number:1];
        }else{
            
        }
    } failure:^(NSError *error) {
        warn(@"网络错误");
    }];
    
    
}

#pragma -mark 数据类型转换
-(void)dataToModel:(NSDictionary *)responseObject
{
    YC_GoodsOrderModerl * model = [YC_GoodsOrderModerl new];
    self.model = model;
    self.model.pID = responseObject[@"pid"];
    /**商品名字*/
    self.model.pName = responseObject[@"pname"];
    /**价格*/
    self.model.pPrice = [NSString stringWithFormat:@"%@",responseObject[@"price"]] ;
    /**图片地址*/
    self.model.pImage = responseObject[@"pimage"];;
}

#pragma -mark 改变提交金额
-(void)changePayLabel:(NSString *)price number:(NSInteger)number
{
    self.model.pNumber = [NSString stringWithFormat:@"%ld", (long)number];
    self.pricesLabel.text = [NSString stringWithFormat:@"￥%@",globalPrices(price)];
}

#pragma -mark 提交订单
-(void)submitOrder{
    //判断登陆
    if ([userDefaultManager getUserWithToken]==nil) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"请登陆";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
            loginViewController* loginVC=[[loginViewController alloc]init];
            [self presentViewController:loginVC animated:YES completion:nil];
            loginVC.successLogin = ^{
                //登录成功
                [self gotoOrder];
            };
        }
         ];
    } else {
        [self gotoOrder];
    }
    
}

-(void)gotoOrder
{
    YC_OrderServerViewController * vc = [[YC_OrderServerViewController alloc] init];
    self.model.pPay = self.pricesLabel.text;
    vc.model = self.model;
    [self.navigationController pushViewController:vc animated:NO];
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
