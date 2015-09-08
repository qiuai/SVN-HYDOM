//
//  JVshowOnceProductViewController.m
//  HD_Car
//
//  Created by cc on 15/8/16.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVshowOnceProductViewController.h"
#import "JVshowOrderProductView.h"
@interface JVshowOnceProductViewController ()
@property(nonatomic,strong)JVshowOrderProductView* productOrderView;
@end

@implementation JVshowOnceProductViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initNavViewWithtitle:@"订单详情"];
    
    UIScrollView* scollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 65, SCREEN_WIDTH, SCREEN_HEIGHT-65)];
    scollView.contentSize=CGSizeMake(SCREEN_WIDTH, 550);
    scollView.backgroundColor=COLOR(240, 240, 240, 1.0);
    [self.view addSubview:scollView];
    self.productOrderView=[[[NSBundle mainBundle]loadNibNamed:@"JVshowOrderProductView" owner:nil options:nil]lastObject];
    self.productOrderView.frame=CGRM(0, 0, SCREEN_WIDTH, 550);
    [scollView addSubview:self.productOrderView];
    
    
    [self initData];
    
    
    
    
    
}

- (void)initData {
    NSMutableDictionary* parameters=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [parameters setObject:self.orderID forKey:@"oid"];
    [HTTPconnect sendPOSTHttpWithUrl:getOrderProductDetailAPI parameters:parameters success:^(id responseObject) {
      if (![responseObject isKindOfClass:[NSString class]]) {
          self.productOrderView.orderNumber.text=responseObject[@"onum"];
          self.productOrderView.state.text=responseObject[@"ostatus"];
          self.productOrderView.contact.text=responseObject[@"ocontact"];
          self.productOrderView.phone.text=responseObject[@"ophone"];
          [self.productOrderView.image sd_setImageWithURL:imageURLWithPath(responseObject[@"opimgpath"]) placeholderImage:[UIImage imageNamed:FillImage]];
          self.productOrderView.productName.text=responseObject[@"opname"];
          self.productOrderView.oncePrices.text=[NSString stringWithFormat:@"￥%@",responseObject[@"oprice"]];
          self.productOrderView.count.text=[NSString stringWithFormat:@"X %@",responseObject[@"opnum"]];
//          responseObject[@"opnum"];
          self.productOrderView.address.text=responseObject[@"address"];
          self.productOrderView.payStyle.text= [self getPayTpyeFromNumber: responseObject[@"payway"]];
          self.productOrderView.youhuijuan.text=responseObject[@"usecoup"];
          self.productOrderView.sumPrices.text=[NSString stringWithFormat:@"￥%@",responseObject[@"orimoney"]];
          self.productOrderView.youhuiPrices.text=[NSString stringWithFormat:@"-￥%@",responseObject[@"cpmoney"]];
          self.productOrderView.realyPrices.text=[NSString stringWithFormat:@"￥%@",responseObject[@"paymoney"]];
      }else{
          warn(responseObject);
      }
      
  } failure:^(NSError *error) {
      warn(@"服务器错误");
  }];
    
    

}

-(NSString *)getPayTpyeFromNumber:(NSString *)string
{
    switch ([string integerValue]) {
        case 1:
            string = @"会员卡支付";
            break;
        case 2:
            string = @"支付宝";
            break;
        case 3:
            string = @"银联";
            break;
        case 4:
            string = @"微信";
            break;
        default:
            break;
    }
    return string;
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
