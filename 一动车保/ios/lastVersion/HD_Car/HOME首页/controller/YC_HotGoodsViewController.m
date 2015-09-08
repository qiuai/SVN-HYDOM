//
//  YC_HotGoodsViewController.m
//  HD_Car
//
//  Created by hydom on 8/11/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_HotGoodsViewController.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "JVCommonSelectCarView.h"
#import "HChooseCommodityTableViewCell.h"
#import "UIImageView+WebCache.h"
#import "CYTabbarContainer.h"
#import "YC_HotDetailViewController.h"
#import "carListViewController.h"
#import "carModel.h"
#import "Global.pch"
@interface YC_HotGoodsViewController ()<UITableViewDataSource,UITableViewDelegate>

@property(nonatomic, assign)NSInteger maxPage;
@property(nonatomic, strong)NSMutableArray * dataArray;
@property(nonatomic, strong)UITableView * tableView;
/**父容器*/
@property(nonatomic, weak)CYTabbarContainer* parentContainer;
@property(nonatomic, strong)YC_MarketModel * marketModel;
@end

@implementation YC_HotGoodsViewController
-(CYTabbarContainer *)parentContainer{
    if (!_parentContainer) {
        _parentContainer=(CYTabbarContainer*)self.parentViewController.parentViewController;
    }
    return _parentContainer;
}
- (NSMutableArray *)dataArray
{
    if (!_dataArray) {
        _dataArray = [NSMutableArray array];
    }
    return _dataArray;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor whiteColor];
    [self.parentContainer hiddenTabbar];
    HDNavigationView * navc = [HDNavigationView navigationViewWithTitle:self.pname];
    WEAKSELF;
    [navc tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:navc];
    
    NSMutableDictionary * parameters = [NSMutableDictionary new];
    [parameters setObject:@1 forKey:@"page"];
    [parameters setObject:@10 forKey:@"maxresult"];
    [parameters setObject:self.pcid forKey:@"pcid"];
    [HTTPconnect sendGETWithUrl:getHotGoodsAPI parameters:parameters success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            for( NSDictionary* dict in responseObject[@"list"]){
                YC_MarketModel * marketModel = [YC_MarketModel getMarketModelFromDic:dict];
                [self.dataArray addObject:marketModel];
            }
            [_tableView reloadData];

        }
    } failure:^(NSError *error) {
        
    }];
    
    [self initInterface];

}

-(void)initInterface
{
    //适配车型
    JVCommonSelectCarView * topView = [[NSBundle mainBundle]loadNibNamed:@"JVCommonSelectCarView" owner:nil options:nil].lastObject;
    topView.frame = CGRectMake(0, 64, SCREEN_WIDTH, 36);
    topView.carNameLabel.text=[NSString stringWithFormat:@"%@",_car.csname];
    UITapGestureRecognizer * topViewTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(topViewTapGR:)];
    [topView addGestureRecognizer:topViewTapGR];
    [self.view addSubview:topView];
    UIView * topViewLine = [[UIView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(topView.frame)-1, SCREEN_WIDTH, 1)];
    topViewLine.backgroundColor = COLOR(220, 220, 220, 1);
    [topView addSubview:topViewLine];
    
    //商品服务列表
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100) style:UITableViewStylePlain];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.showsVerticalScrollIndicator = NO;
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.rowHeight = 90;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    [self.view addSubview:_tableView];
 
}

#pragma -mark tableView代理
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArray.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    HChooseCommodityTableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:@"HChooseCommodityTableViewCell"];
    if (!cell) {
        cell = [[NSBundle mainBundle] loadNibNamed:@"HChooseCommodityTableViewCell" owner:nil options:nil].lastObject;
    }
    YC_MarketModel * marketModel = _dataArray[indexPath.row];
    cell.markeModel = marketModel;
    [cell.leftImageView sd_setImageWithURL:imageURLWithPath(marketModel.pimage)];
    cell.priceLabel.text = [NSString stringWithFormat:@"￥%@", globalPrices(marketModel.price)];
    cell.buyNumLabel.text = [NSString stringWithFormat:@"%@人购买", marketModel.pbuynum];
    cell.commentNumLabel.text = [NSString stringWithFormat:@"%@评价", marketModel.pcomts];
    cell.titleLabel.text = marketModel.pname;
    cell.backgroundColor = [UIColor clearColor];
    return cell;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    HChooseCommodityTableViewCell * cell = (HChooseCommodityTableViewCell *)[tableView cellForRowAtIndexPath:indexPath];
    YC_HotDetailViewController * vc = [[YC_HotDetailViewController alloc] init];
    vc.productID = cell.markeModel.pid;
    vc.isFixCar = YES;
    [self.navigationController pushViewController:vc animated:NO];
    
}


#pragma -mark 顶部选车手势
-(void)topViewTapGR:(UIGestureRecognizer *)sender
{
    carListViewController * vc = [[carListViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
    vc.defaultCarModel = ^(carModel * model){
        NSLog(@"回调模型");
    };
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
