//
//  YC_AddGoodsViewController.m
//  HD_Car
//
//  Created by hydom on 8/7/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_AddGoodsViewController.h"
#import "HDNavigationView.h"
#import "HChooseCommodityTableViewCell.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
#import "UIImageView+WebCache.h"
#import "YC_GoodsModel.h"
#import "Global.pch"
@interface YC_AddGoodsViewController ()<UITableViewDelegate,UITableViewDataSource>

@property(nonatomic, strong)UITableView * tableView;
@property(nonatomic, assign)NSInteger maxPage;
@property(nonatomic, strong)NSMutableArray * dataSource;

@property(nonatomic,strong)YC_GoodsModel* selectGoods;

@end

@implementation YC_AddGoodsViewController
-(NSMutableArray *)dataSource{
    if (!_dataSource) {
        _dataSource=[NSMutableArray array];
    }
    return _dataSource;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = COLOR(236, 236, 236, 1);
    HDNavigationView * naveView = [HDNavigationView navigationViewWithTitle:@"新增"];
    WEAKSELF;
    [naveView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:naveView];
    
    UIButton * button = [[UIButton alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-66, 20, 66, 44)];
    [button setTitle:@"确认" forState:0];
    [button setTitleColor:[UIColor whiteColor] forState:0];
    [button addTarget:self action:@selector(pressSureButton:) forControlEvents:UIControlEventTouchUpInside];
    button.titleLabel.font = [UIFont systemFontOfSize:15];
    [naveView addSubview:button];
    
    NSMutableDictionary * requestDict=[NSMutableDictionary new];
    [requestDict setObject:self.scid forKey:@"scid"];
    [requestDict setObject:self.cmid forKey:@"cmid"];
    if (self.pids) {
        [requestDict setObject:self.pids forKey:@"pids"];
    }
    [requestDict setObject:@"1" forKey:@"page"];
    [requestDict setObject:@"20" forKey:@"maxresult"];
    
    
    [HTTPconnect sendGETWithUrl:getSelectOrderGoodsAPI parameters:requestDict success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            _dataSource = [NSMutableArray arrayWithArray:responseObject[@"list"]];
            NSLog(@"%@", responseObject);
            [self initInterface];
        }
    } failure:^(NSError *error) {

    } ];
    
    
}

-(void)initInterface
{
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64) style:UITableViewStylePlain];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.showsVerticalScrollIndicator = NO;
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    [self.view addSubview:_tableView];
    

}

#pragma mark tableView
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _dataSource.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    HChooseCommodityTableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:@"HChooseCommodityTableViewCell"];
    if (!cell) {
        cell = [[NSBundle mainBundle] loadNibNamed:@"HChooseCommodityTableViewCell" owner:nil options:nil].lastObject;
    }
    [cell.leftImageView sd_setImageWithURL:imageURLWithPath(_dataSource[indexPath.row][@"pimage"])];
    cell.priceLabel.text = [NSString stringWithFormat:@"￥%@", globalPrices(_dataSource[indexPath.row][@"price"])];
    cell.buyNumLabel.text = [NSString stringWithFormat:@"%@人购买", _dataSource[indexPath.row][@"pbuynum"]];
    cell.commentNumLabel.text = [NSString stringWithFormat:@"%@评价", _dataSource[indexPath.row][@"pcomts"]];
    cell.titleLabel.text = _dataSource[indexPath.row][@"pname"];
    UIView * view = [[UIView alloc] initWithFrame:CGRectMake(2, 0, SCREEN_WIDTH-4, 90)];
    view.backgroundColor = COLOR(232, 244, 249, 1);
    view.layer.borderWidth = 2;
    view.layer.borderColor = COLOR(132, 200, 238, 1).CGColor;
    view.alpha = 0.8;
    cell.selectedBackgroundView = view;
    cell.backgroundColor = [UIColor clearColor];
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 90;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary* dict=_dataSource[indexPath.row];
    self.selectGoods=[YC_GoodsModel goodsModelWithDictionary:dict];
}

#pragma mark method
-(void)pressSureButton:(UIButton *)sender
{
    if(self.selectGoods!=nil){
    if (self.addGooldsBlock!=nil) {
        self.addGooldsBlock(self.selectGoods);
    }
    [self.navigationController popViewControllerAnimated:YES];
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
