//
//  YC_RechargeViewController.m
//  HD_Car
//
//  Created by hydom on 8/18/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_RechargeViewController.h"
#import "YCRechargeView.h"
#import "YC_RechargePriceViewController.h"
@interface YC_RechargeViewController ()

@end

@implementation YC_RechargeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initNavViewWithtitle:@"充值"];
    [self LayoutUI];
}

-(void)LayoutUI
{
    YCRechargeView * view = [[NSBundle mainBundle] loadNibNamed:@"YCRechargeView" owner:nil options:nil].lastObject;
    view.frame = CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64);
    view.backgroundColor = HDfillColor;
    [self.view addSubview:view];
    view.weixinView.tag = 101;
    view.bankView.tag = 102;
    view.payBaoView.tag = 103;
    [view.weixinView addTapGestureRecognizerWithTarget:self action:@selector(selectPayType:)];
    [view.bankView addTapGestureRecognizerWithTarget:self action:@selector(selectPayType:)];
    [view.payBaoView addTapGestureRecognizerWithTarget:self action:@selector(selectPayType:)];
}


//选择支付方式
-(void)selectPayType:(UIGestureRecognizer *)sender
{
    if(sender.view.tag!=103) return;
    NSString * type = @"3";
//    switch (sender.view.tag) {
//        case 101:{
//            //微信支付
//            type = @"1";
//        }
//            break;
//        case 102:{
//            //银联支付
//            type = @"2";
//        }
//            break;
//        case 103:{
//            //支付宝支付
//            type = @"3";
//        }
//            break;
//        default:
//            break;
//    }
    YC_RechargePriceViewController * vc = [[YC_RechargePriceViewController alloc] init];
    vc.type = type;
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
