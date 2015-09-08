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
    NSMutableArray* pushArray=[NSMutableArray array];
    for (NSDictionary* dic in self.dataArray) {
        JVcommonOrderSericesModel *m=[JVcommonOrderSericesModel new];
        m.scimg=dic[@"scimage"];
        m.scname=dic[@"scname"];
        m.scprice=dic[@"scprice"];
        NSMutableArray* productArray=[NSMutableArray array];
        for (YC_GoodsModel* mPro in dic[@"array"]) {
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
    childVC.dataSoure=pushArray;
    UIScrollView* scrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 65, SCREEN_WIDTH, SCREEN_HEIGHT-65)];
    [self.view addSubview:scrollView];
    scrollView.contentSize=CGSizeMake(SCREEN_WIDTH, SCREEN_HEIGHT);
    [scrollView addSubview:childVC.view];
    CGFloat height=childVC.ViewHeight;
    childVC.view.frame=CGRM(0,0, SCREEN_WIDTH, height);
    scrollView.contentSize=CGSizeMake(SCREEN_WIDTH, height+30);
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
