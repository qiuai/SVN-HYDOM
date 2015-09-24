//
//  YC_MarketViewController.m
//  HD_Car
//
//  Created by hydom on 8/10/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_MarketViewController.h"
#import "HDNavigationView.h"
#import "HChooseCommodityTableViewCell.h"
#import "CYTabbarContainer.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "YC_MarketModel.h"
#import "YC_LimitGoodsCell.h"
#import "UIImageView+WebCache.h"
#import "YC_MarketSaleCell.h"
#import "JVshowGoodsForOrderViewController.h"
#import "YC_HotDetailViewController.h"
#import <MJRefresh.h>
@interface YC_MarketViewController ()<UITableViewDataSource,UITableViewDelegate,UICollectionViewDataSource,UICollectionViewDelegate>
{
    
    NSString * title;
    NSString * api;
    NSInteger index;
}

/**父容器*/
@property(nonatomic,weak)CYTabbarContainer* parentContainer;

@property(nonatomic,assign)NSInteger currentPage; //数据请求页数
@property(nonatomic,assign)NSInteger totalPage;  //服务器返回总页数

@property(nonatomic,strong)NSMutableArray * dataArray;

//数据view  指针
@property(nonatomic,weak)id dataView;

@end

@implementation YC_MarketViewController


-(NSMutableArray *)dataArray
{
    if (!_dataArray) {
        _dataArray = [NSMutableArray array];
    }
    return _dataArray;
}

-(CYTabbarContainer *)parentContainer{
    if (!_parentContainer) {
        _parentContainer=(CYTabbarContainer*)self.parentViewController.parentViewController;
    }
    return _parentContainer;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.automaticallyAdjustsScrollViewInsets = NO;
    index = 1;
    self.view.backgroundColor = [UIColor whiteColor];
    [self.parentContainer hiddenTabbar];
    [self initInterface];
    switch (self.marketType) {
        case sugeest:
            title = @"品牌推荐";
            api = getMarketSuggestAPI;
            break;
        case limit:
            title = @"限量精品";
            api = getMarketLimitAPI;
            break;
        case sale:
            title = @"天天特价";
            api = getMarketSaleAPI;
            break;
        case life:
            title = @"绿色出行";
            api = getMarketLifeAPI;
            break;
        default:
            break;
    }
    HDNavigationView * navc = [HDNavigationView navigationViewWithTitle:title];
    WEAKSELF;
    [navc tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:navc];
    
    [self initData];

}
#pragma -mark发送请求
-(void)initData{
    
    NSMutableDictionary * parameters = [[NSMutableDictionary alloc]init];
    [parameters setObject:@1 forKey:@"page"];
    //请求一页数据的count
    [parameters setObject:@10 forKey:@"maxresult"];
    
    [HTTPconnect sendGETWithUrl:api parameters:parameters success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            _totalPage = [responseObject[@"page"] integerValue];
            for( NSDictionary* dict in responseObject[@"list"]){
                YC_MarketModel * marketModel = [YC_MarketModel getMarketModelFromDic:dict];
                [self.dataArray addObject:marketModel];
            }
            //刷新数据
            [self.dataView performSelector:@selector(reloadData) withObject:nil];
        }
    } failure:^(NSError *error) {
        
    }];

}
#pragma -mark页面布局
-(void)initInterface
{
    //天天特价页面
    if (self.marketType == sale) {
        CGFloat itemWidth = (SCREEN_WIDTH-40)/2.0;
        CGFloat itemHeight = itemWidth * (1.3);
        
        UICollectionViewFlowLayout *layout = [[UICollectionViewFlowLayout alloc] init];
        layout.minimumLineSpacing = 10;
        layout.minimumInteritemSpacing = 10;
        layout.itemSize = CGSizeMake(itemWidth, itemHeight);
        layout.scrollDirection = UICollectionViewScrollDirectionVertical;
        layout.sectionInset = UIEdgeInsetsMake(15, 13, 0, 13);
        layout.headerReferenceSize = CGSizeZero;
        
        UICollectionView * collectionView = [[UICollectionView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64) collectionViewLayout:layout];
        collectionView.delegate = self;
        collectionView.dataSource = self;
        collectionView.backgroundColor = HDfillColor;
        [self.view addSubview:collectionView];
        WEAKSELF;
        collectionView.footer = [MJRefreshAutoNormalFooter footerWithRefreshingBlock:^{
            [weakSelf getDataFromServer];
        }];
        [collectionView registerClass:[YC_MarketSaleCell class] forCellWithReuseIdentifier:@"YC_MarketSaleCell"];
        self.dataView=collectionView;

    } else {
        UITableView * tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64)];
        tableView.dataSource = self;
        tableView.delegate = self;
        tableView.showsVerticalScrollIndicator = NO;
        tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        WEAKSELF;
        tableView.footer = [MJRefreshAutoNormalFooter footerWithRefreshingBlock:^{
            [weakSelf getDataFromServer];
        }];
        if (self.marketType == sugeest || self.marketType == life) {
            tableView.rowHeight = 90;
        } else {
            tableView.rowHeight = 100;
        }
        tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
        [self.view addSubview:tableView];
        self.dataView=tableView;
    }
    
}

-(void)getDataFromServer
{
    if (index < _totalPage) {
        index ++;
        NSMutableDictionary * parameters = [[NSMutableDictionary alloc]init];
        [parameters setObject:@(index) forKey:@"page"];
        //请求一页数据的count
        [parameters setObject:@10 forKey:@"maxresult"];
        
        [HTTPconnect sendGETWithUrl:api parameters:parameters success:^(id responseObject) {
            [[self.dataView footer] endRefreshing];
            if (![responseObject isKindOfClass:[NSString class]]) {
                
                for( NSDictionary* dict in responseObject[@"list"]){
                    YC_MarketModel * marketModel = [YC_MarketModel getMarketModelFromDic:dict];
                    [self.dataArray addObject:marketModel];
                }
                //刷新数据
                [self.dataView performSelector:@selector(reloadData) withObject:nil];
            }
        } failure:^(NSError *error) {
              [[self.dataView footer] endRefreshing];
        }];

    }
    [[self.dataView footer] endRefreshing];
}


#pragma -mark tableView协议
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArray.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (self.marketType == sugeest || self.marketType == life) {
        HChooseCommodityTableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:@"HChooseCommodityTableViewCell"];
        if (!cell) {
            cell = [[NSBundle mainBundle] loadNibNamed:@"HChooseCommodityTableViewCell" owner:nil options:nil].lastObject;
            YC_MarketModel * marketModel = _dataArray[indexPath.row];
            [cell.leftImageView sd_setImageWithURL:imageURLWithPath(marketModel.pimage)];
            cell.priceLabel.text = [NSString stringWithFormat:@"￥%@", globalPrices(marketModel.price)];
            cell.buyNumLabel.text = [NSString stringWithFormat:@"%@人购买", marketModel.pbuynum];
            cell.commentNumLabel.text = [NSString stringWithFormat:@"%@评价", marketModel.pcomts];
            cell.titleLabel.text = marketModel.pname;
        }
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        return cell;
    } else {
        YC_LimitGoodsCell * cell = [tableView dequeueReusableCellWithIdentifier:@"LimitCell"];
        if (!cell) {
            cell = [[NSBundle mainBundle] loadNibNamed:@"YC_LimitGoodsCell" owner:nil options:nil].lastObject;
            YC_MarketModel * marketModel =_dataArray[indexPath.row];
            [cell.leftImageView sd_setImageWithURL:imageURLWithPath(marketModel.pimage)];
            cell.titleLabel.text = marketModel.pname;
            cell.priceLabel.text = [NSString stringWithFormat:@"￥%@", globalPrices(marketModel.price)];
            cell.buyNumLabel.text = [NSString stringWithFormat:@"已抢%@份", marketModel.salenum];
            cell.totalLabel.text = [NSString stringWithFormat:@"共%@份", marketModel.ptotalnum];
            if ([marketModel.salenum isEqualToNumber:marketModel.ptotalnum]) {
                cell.buyLabel.text = @"已抢完";
                cell.buyLabel.backgroundColor = [UIColor lightGrayColor];
            }
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return cell;
    }
    return nil;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([[tableView cellForRowAtIndexPath:indexPath] isKindOfClass:[YC_LimitGoodsCell class]]) {
        YC_LimitGoodsCell * cell = (YC_LimitGoodsCell *)[tableView cellForRowAtIndexPath:indexPath];
        if (cell.buyLabel.backgroundColor == [UIColor lightGrayColor]) {
        } else {
            [self pushVCWithRow:indexPath.row];
        }
    } else {
            [self pushVCWithRow:indexPath.row];
    }

}

#pragma -mark collectionView协议
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return _dataArray.count;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    YC_MarketSaleCell * cell = [collectionView dequeueReusableCellWithReuseIdentifier:@"YC_MarketSaleCell"  forIndexPath:indexPath];
    cell.backgroundColor = [UIColor whiteColor];
    YC_MarketModel * marketModel = _dataArray[indexPath.row];
    [cell.topImageView sd_setImageWithURL:imageURLWithPath(marketModel.pimage)];
    cell.titleLabel.text = [NSString stringWithFormat:@"【天天特价】%@",marketModel.pname];
    cell.pdiscountLabel.text = [NSString stringWithFormat:@"%@折", marketModel.pdiscount];
    cell.poripriceLabel.text = [NSString stringWithFormat:@"￥%@", globalPrices(marketModel.poriprice)];
    cell.pdispriceLabel.text = [NSString stringWithFormat:@"￥%@", marketModel.pdisprice];
    return cell;
}


-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    [self pushVCWithRow:indexPath.row];
}



#pragma -mark 跳转商品详情
-(void)pushVCWithRow:(NSInteger)row{
    YC_HotDetailViewController* goodsVC=[[YC_HotDetailViewController alloc]init];
    YC_MarketModel * marketModel=self.dataArray[row];
        goodsVC.productID=marketModel.pid;
    [self.navigationController pushViewController:goodsVC animated:NO];
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
