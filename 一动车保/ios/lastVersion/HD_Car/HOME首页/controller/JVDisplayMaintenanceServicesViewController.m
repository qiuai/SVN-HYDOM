//
//  JVDisplayMaintenanceServicesViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/14.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVDisplayMaintenanceServicesViewController.h"
#import "YC_GoodsModel.h"
@interface JVDisplayMaintenanceServicesViewController ()

@end

@implementation JVDisplayMaintenanceServicesViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = HDfillColor;
    NSLog(@"%@", self.dataArray);
    BOOL pureServer=YES;
    NSMutableArray* pushArray=[NSMutableArray array];
    for (NSDictionary* dic in self.dataArray) {
        JVcommonOrderSericesModel *m=[JVcommonOrderSericesModel new];
        m.scimg=dic[@"scimage"];
        m.scname=dic[@"scname"];
        m.scprice=dic[@"scprice"];
        NSMutableArray* productArray=[NSMutableArray array];
        for (YC_GoodsModel* mPro in dic[@"array"]) {
            pureServer=NO;
            JVcommonOrderSericesProductMOdel *pM=[JVcommonOrderSericesProductMOdel new];
            pM.pimg=mPro.pimage;
            pM.pname=mPro.pname;
            pM.pprice=[NSString stringWithFormat:@"%@",mPro.price];
            pM.pnum=[NSString stringWithFormat:@"%ld",mPro.myCount];
            [productArray addObject:pM];
        }
        m.productArray=productArray;
        [pushArray addObject:m];
    }
    [self initNavViewWithtitle:@"详情"];
    JVDisplayServicesViewController* childVC=[[JVDisplayServicesViewController alloc]init];
    childVC.pureServer=pureServer;
    childVC.dataSoure=pushArray;
    UIScrollView* scrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 65, SCREEN_WIDTH, SCREEN_HEIGHT-65)];
    [self.view addSubview:scrollView];
    scrollView.contentSize=CGSizeMake(SCREEN_WIDTH, SCREEN_HEIGHT);
    [scrollView addSubview:childVC.view];
    CGFloat height=childVC.ViewHeight;
    childVC.view.frame=CGRM(0,0, SCREEN_WIDTH, height);
    scrollView.contentSize=CGSizeMake(SCREEN_WIDTH, height+30);
    
    if (pureServer == YES) {
        [self addPayView];
    }
}

-(void)addPayView
{
    UIView * priceView = [[UIView alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT-40, SCREEN_WIDTH, 40)];
    priceView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:priceView];

    UIView * priceTopLine = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 1)];
    priceTopLine.backgroundColor = COLOR(220, 220, 220, 1);
    [priceView addSubview:priceTopLine];

    UILabel * priceViewLabel = [[UILabel alloc] initWithFrame:CGRectMake(15, 0, 120, 40)];
    priceViewLabel.text = @"服务费";
    priceViewLabel.font = [UIFont systemFontOfSize:17];
    [priceView addSubview:priceViewLabel];

    UILabel * priceLabel = [[UILabel alloc] initWithFrame:CGRectMake(SCREEN_WIDTH - 100, 0, 80, 40)];
    priceLabel.textColor = [UIColor redColor];
    priceLabel.textAlignment = NSTextAlignmentRight;
    [priceView addSubview:priceLabel];
    priceLabel.text = self.price;
    
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
