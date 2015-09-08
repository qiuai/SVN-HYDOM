//
//  JVcommodityInfoViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/5.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVcommodityInfoViewController.h"
#import "HDNavigationView.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "CYScollViewContainer.h"
#import "CarDetailInforView.h"

@interface JVcommodityInfoViewController ()
@property(nonatomic,strong)HDNavigationView* navView;
@property(nonatomic,weak)CYScollViewContainer* imagePageVC;
@property(strong,nonatomic)CarDetailInforView* detailInfoView;

@property(strong,nonatomic)UIScrollView* scrollView;
@end

@implementation JVcommodityInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    _navView=[HDNavigationView navigationViewWithTitle:@"商品详情"];
    WEAKSELF;
    [_navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:_navView];
    
    self.scrollView=[[UIScrollView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64)];
    self.scrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 1000);
    [self.view addSubview:self.scrollView];
    CYScollViewContainer* imagePageViewVC=[[CYScollViewContainer alloc]init];
    [self addChildViewController:imagePageViewVC];
    
    imagePageViewVC.view.frame=CGRM(0, 0, SCREEN_WIDTH, SCREEN_WIDTH*0.5);
    [self.scrollView addSubview:imagePageViewVC.view];
    self.imagePageVC=imagePageViewVC;
    self.detailInfoView=[[[NSBundle mainBundle]loadNibNamed:@"CarDetailInforView" owner:nil options:nil]lastObject];
    self.detailInfoView.frame=CGRM(0, 0.5*SCREEN_WIDTH, SCREEN_WIDTH, 400);
    [self.scrollView addSubview:self.detailInfoView];
    [self initData];
    
}



-(void)initData{
        NSMutableDictionary* dic=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
        [dic setObject:self.productID forKey:@"pid"];
    
        [HTTPconnect sendPOSTHttpWithUrl:getProductInfoAPI parameters:dic success:^(id responseObject) {
            if (![responseObject isKindOfClass:[NSString class]]) {
                NSArray* imageArray=responseObject[@"imglist "];
                NSMutableArray* imageURL=[NSMutableArray array];
                for (NSDictionary* dic in imageArray) {
                    [imageURL addObject:dic[@"pimg"]];
                }
                [self.imagePageVC setImageArray:imageURL];
                self.detailInfoView.goodsTitle=responseObject[@"pname"];
                self.detailInfoView.goodsPrices=responseObject[@"price"];
                self.detailInfoView.pingjia=responseObject[@"pcomts"];
                self.detailInfoView.purl=responseObject[@"purl"];
                 self.detailInfoView.servicesArray=responseObject[@"suplist"];
                self.detailInfoView.parameter=responseObject[@"paramlist"];
                [self.detailInfoView setUpUserInterface];
            }else{
                
            }
        } failure:^(NSError *error) {
        }];
    
    
}


@end
