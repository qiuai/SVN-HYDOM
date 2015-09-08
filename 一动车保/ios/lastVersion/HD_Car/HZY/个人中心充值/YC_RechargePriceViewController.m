//
//  YC_RechargePriceViewController.m
//  HD_Car
//
//  Created by hydom on 8/18/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_RechargePriceViewController.h"
#import "JVzhifubao.h"
#import "userDefaultManager.h"
@interface YC_RechargePriceViewController ()<UITextFieldDelegate>

@property(nonatomic, strong)UIButton * submitButton;

@property(nonatomic,weak)UITextField* payPrices;
@end

@implementation YC_RechargePriceViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initNavViewWithtitle:@"充值"];
    self.view.backgroundColor = HDfillColor;
    [self LayoutUI];
}

-(void)LayoutUI{
    UIView * priceView = [[UIView alloc] initWithFrame:CGRectMake(0, 80, SCREEN_WIDTH, 50)];
    priceView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:priceView];
    
    UILabel * priceLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 60, 50)];
    priceLabel.text = @"金额";
    priceLabel.font = [UIFont systemFontOfSize:18];
    priceLabel.textAlignment = NSTextAlignmentRight;
    [priceView addSubview:priceLabel];
    
    UITextField * payTextField = [[UITextField alloc] initWithFrame:CGRectMake(70, 3, 120, 45)];
    payTextField.placeholder = @"请输入充值金额";
    payTextField.delegate = self;
    [priceView addSubview:payTextField];
    self.payPrices = payTextField;
    
    _submitButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _submitButton.frame = CGRectMake(10, 160, SCREEN_WIDTH-20, 50);
    [_submitButton setImage:[UIImage imageNamed:@"余额_充值01"] forState:UIControlStateNormal];
    [_submitButton setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    _submitButton.userInteractionEnabled = NO;
    [_submitButton addTarget:self action:@selector(pressSubmitButton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_submitButton];
}

#pragma -mark 确认支付
-(void)pressSubmitButton:(UIButton*)bt{
    bt.userInteractionEnabled=NO;
    NSMutableDictionary * parameters = [[userDefaultManager getUserWithToken] mutableCopy];
    [parameters setObject:self.payPrices.text forKey:@"money"];
    [HTTPconnect sendGETWithUrl:getRechargeNumberAPI parameters:parameters success:^(id responseObject) {
        bt.userInteractionEnabled=YES;
        if (![responseObject isKindOfClass:[NSString class]]) {
            JVcommonProduct* product=[JVcommonProduct new];
            product.orderID= responseObject[@"num"];
            product.productName=@"亿动车包充值";
            product.productDescription=@"亿动车包充值";
            product.resultURL = zhiFuBaoPayURL;
            product.orderPrices=[globalPrices(self.payPrices.text) doubleValue];
            [JVzhifubao JVzfbWith:product success:^{
                warn(@"充值成功!!");
                NSInteger index=[[self.navigationController viewControllers]indexOfObject:self];
                UIViewController* popVC1=[self.navigationController viewControllers][index-2];
                [self.navigationController popToViewController:popVC1 animated:YES];
            } failure:^{
                warn(@"充值失败!!");
            }];
        }
    } failure:^(NSError *error) {
        bt.userInteractionEnabled=YES;
        warn(@"充值失败!!");
    }];
    
}


#pragma -mark TextField
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}

-(void)textFieldDidEndEditing:(UITextField *)textField
{
    if ([textField.text floatValue]){
        _submitButton.userInteractionEnabled = YES;
        self.payPrices=textField;
        [_submitButton setImage:[UIImage imageNamed:@"余额_充值02"] forState:UIControlStateNormal];
    } else {
        warn(@"您输入的金额有误");
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
