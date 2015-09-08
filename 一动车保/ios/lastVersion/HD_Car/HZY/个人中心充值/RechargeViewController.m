//
//  RechargeViewController.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "RechargeViewController.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
#import "FreeModel.h"
#import "MJExtension.h"
#import "HRechargeView.h"
#import "ListModel.h"
#import "HRechargeView.h"
#import <MJRefresh.h>
#import "YC_RechargeViewController.h"
@interface RechargeViewController ()<ChangeNetWorkingTypeDelegate>
@property (nonatomic,strong)FreeModel * model;
@property (nonatomic,strong)NSMutableArray * listArray;
@property (nonatomic,assign)NSInteger page;
@property (nonatomic,assign)FreeNetWorkingType  workingType;
@property (nonatomic,strong)HRechargeView * rechargeView;

@end

@implementation RechargeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    _page = 1;
    self.workingType = All;
    [self initNavViewWithtitle:@"余额"];
    WEAKSELF;
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self initDtaSuccess:^(NSArray *array) {
        
    }];
    [self.navView tapRightViewWithImageName:@"充值" tapBlock:^{
        YC_RechargeViewController * vc = [[YC_RechargeViewController alloc] init];
        [weakSelf.navigationController pushViewController:vc animated:YES];
    }];
    [self setUpUserInterface];
    // Do any additional setup after loading the view.
}
- (void)initDtaSuccess:(void (^)(NSArray * array))success{
    NSMutableDictionary * paract = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [paract setObject:@(_page) forKey:@"page"];
    [paract setObject:@(_rechargeView.type) forKey:@"type"];
    [paract setObject:@"15" forKey:@"maxresult"];
    [HTTPconnect sendPOSTHttpWithUrl:UserFreeMoneyAPI parameters:paract success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            _model = [FreeModel objectWithKeyValues:responseObject];
            _rechargeView.moneylABEL.text = _model.balance;
            success ([ListModel objectArrayWithKeyValuesArray:_model.list]);
        }else{
            success(nil);
        }
    } failure:^(NSError *error) {
        success (nil);
    }];

}
- (void)setUpUserInterface{

    _rechargeView = [[[NSBundle mainBundle]loadNibNamed:@"HRechargeView" owner:self options:nil]lastObject];
    _rechargeView.frame = CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64);
    [self.view addSubview:_rechargeView];
    _rechargeView.delegate = self;
    
    _rechargeView.type = All;
    _rechargeView.tableView.header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{
        _page = 1;
        [self initDtaSuccess:^(NSArray *array) {
            _listArray = [NSMutableArray arrayWithArray:array];
            _rechargeView.dataSource = _listArray;
            [_rechargeView.tableView reloadData];
        }];
        [_rechargeView.tableView.header endRefreshing];
    }];
    
    _rechargeView.tableView.footer = [MJRefreshAutoNormalFooter footerWithRefreshingBlock:^{
        if (_page != _model.pages) {
            _page ++ ;
            [self initDtaSuccess:^(NSArray *array) {
                [_listArray addObjectsFromArray:array];
                _rechargeView.dataSource = _listArray;
                [_rechargeView.tableView reloadData];
            }];
        }else{
            warn(@"没有更多数据");
        }
        [_rechargeView.tableView.footer endRefreshing];
    }];
    [_rechargeView.tableView.header beginRefreshing];

}
-(void)ChangeNetWorkingWith:(FreeNetWorkingType)type{
    [self.rechargeView.tableView.header beginRefreshing];

}


@end
