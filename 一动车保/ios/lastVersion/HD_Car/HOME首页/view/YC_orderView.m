//
//  YC_orderView.m
//  HD_Car
//
//  Created by hydom on 8/12/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_orderView.h"
#import "UIImageView+WebCache.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
#import "YC_OrderServerViewController.h"
#import "YC_SelectDiscountViewController.h"
#import "ChooseAreaViewController.h"


@interface YC_orderView ()<UITextFieldDelegate,AddressDelegate>

@property (weak, nonatomic) IBOutlet UIView *line1;
@property (weak, nonatomic) IBOutlet UIView *line2;

@property(weak,nonatomic)UIImageView* payPointer;

@property (weak, nonatomic) IBOutlet UIImageView *topImageView;
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;
@property (weak, nonatomic) IBOutlet UILabel *priceLabel;
@property (weak, nonatomic) IBOutlet UILabel *numberLabel;


@end

@implementation YC_orderView


-(void)awakeFromNib{
    
    // 初始化代码
    [self addline:self.line1];
    [self addline:self.line2];
    self.payStyle=@"2";
    self.payStyleDescription=@"支付宝";
    self.vc.model.payWay = self.payStyle;
    self.payPointer=(UIImageView*)self.payTapView1.subviews[0];
    [self addTapforChange];
    self.userTextField.moveView=self;
    self.phoneTextField.moveView=self;
    self.userTextField.text =[[userDefaultManager getPersonNumber] objectForKey:@"myPerson"];
    self.phoneTextField.text =[[userDefaultManager getPersonNumber] objectForKey:@"myPhone"];
    self.discountButton.contentEdgeInsets = UIEdgeInsetsMake(0, 0, 0, 3);
}


-(void)setModel:(YC_GoodsOrderModerl *)model{
    _model=model;
    [self.topImageView sd_setImageWithURL:imageURLWithPath(self.model.pImage)];
    self.titleLabel.text = _model.pName;
    self.priceLabel.text = [NSString stringWithFormat:@"￥%@", globalPrices(_model.pPrice)];
    self.numberLabel.text = [NSString stringWithFormat:@"X%@", _model.pNumber];
    self.userTextField.text=[[userDefaultManager getPersonNumber] objectForKey:@"myPerson"];
    self.phoneTextField.text=[[userDefaultManager getPersonNumber] objectForKey:@"myPhone"];
    self.payLabel.text = [NSString stringWithFormat:@"￥%@", globalPrices(_model.pPay)];
    self.saleLabel.text = @"￥0.00";
}

/**添加虚线*/
-(void)addline:(UIView*)view{
    UIImage* backgroundImage=[UIImage imageNamed:@"HDxuxian"];
    UIColor *backgroundColor = [UIColor colorWithPatternImage:backgroundImage];
    view.backgroundColor=backgroundColor;
}


/**添加切换手势*/
-(void)addTapforChange{
    [self.payTapView1 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView1.tag=21;
    [self.payTapView2 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView2.tag=22;
    [self.payTapView3 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView3.tag=23;
}




-(void)changePay:(UIGestureRecognizer* )gez{
    UIView* view=gez.view;
    UIImageView* imageV=(UIImageView*)view.subviews[0];
    if (self.payPointer!=nil) {
        self.payPointer.image=[UIImage imageNamed:@"redSingle2"];
    }
    imageV.image=[UIImage imageNamed:@"redSingle1"];
    self.payPointer=imageV;
    switch (view.tag) {
        case 21:
            self.payStyle=@"2";
            self.payStyleDescription=@"支付宝";
            break;
        case 22:
            self.payStyle=@"4";
            self.payStyleDescription=@"微信支付";
            break;
        case 23:
            self.payStyle=@"1";
            self.payStyleDescription=@"会员卡支付";
            break;
        default:
            break;
    }
    self.vc.model.payWay = self.payStyle;
}



- (IBAction)selectAdd:(UIButton *)sender {
    ChooseAreaViewController* vcc=[ChooseAreaViewController new];
    _po(self.vc);
    [self.vc.navigationController pushViewController:vcc animated:NO];
    vcc.delegate=self;
}



- (void)sendAddressWith:(NSString *)address Id:(NSString *)Id detailsString:(NSString *)detailString{
    self.addressDict=@{@"id":Id,@"adddress":address,@"detail":detailString};
    self.address1.text=address;
    self.address2.text=detailString;
}

- (IBAction)saleButton:(UIButton *)sender {
//    YC_SelectDiscountViewController * vc = [[YC_SelectDiscountViewController alloc] init];
//    [vc initNavViewWithtitle:@"选择优惠券"];
//    vc.money = self.vc.model.pPrice;
//    [self.vc.navigationController pushViewController:vc animated:YES];
//    vc.selectblock = ^(discountModel * model){
//        self.vc.model.pCpid = model.cpid;
    
//        NSMutableDictionary * parameters = [NSMutableDictionary new];
//        [parameters setObject:self.vc.model.pPrice forKey:@"money"];
//        [parameters setObject:@"3" forKey:@"otype"];
//        [HTTPconnect sendGETWithUrl:getDiscountAbleListAPI parameters:parameters success:^(id responseObject) {
//            if (![responseObject isKindOfClass:[NSString class]]) {
//                NSLog(@"%@",responseObject);
//                _dataArray = [NSMutableArray new];
//                _model = [discountModel new];
//                for (NSDictionary * dic in responseObject[@"list"]) {
//                    [_model setValuesForKeysWithDictionary:dic];
//                    [_dataArray addObject:_model];
//                }
//                [_tableView reloadData];
//            }
//        } failure:^(NSError *error){
//            
//        }];
//    };
}

@end
