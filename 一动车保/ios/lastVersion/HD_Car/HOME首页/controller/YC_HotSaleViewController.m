//
//  YC_HotSaleViewController.m
//  HD_Car
//
//  Created by hydom on 8/11/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_HotSaleViewController.h"
#import "HDNavigationView.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "CYTabbarContainer.h"
#import "YC_HotGoodsViewController.h"
@interface YC_HotSaleViewController ()<UITableViewDataSource,UITableViewDelegate>

/**父容器*/
@property(nonatomic,weak)CYTabbarContainer* parentContainer;

@property(nonatomic, strong)UITableView * homeTableView;
@property(nonatomic, strong)UITableView * subTableView;
@property(nonatomic, strong)NSMutableArray * dataArray;
@property(nonatomic, strong)NSMutableArray * subDataArray;

@property(nonatomic, strong)UITableViewCell * homeCell;


@end

@implementation YC_HotSaleViewController

-(CYTabbarContainer *)parentContainer{
    if (!_parentContainer) {
        _parentContainer=(CYTabbarContainer*)self.parentViewController.parentViewController;
    }
    return _parentContainer;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self.parentContainer hiddenTabbar];
    self.automaticallyAdjustsScrollViewInsets = NO;
    self.view.backgroundColor = [UIColor whiteColor];
    
    HDNavigationView * navc = [HDNavigationView navigationViewWithTitle:@"商品分类"];
    WEAKSELF;
    [navc tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:navc];

    _subDataArray = [NSMutableArray array];
    [HTTPconnect sendGETWithUrl:getHotMoreAPI parameters:nil success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {

            _dataArray = [NSMutableArray array];
            _dataArray = responseObject[@"list"];

            [_homeTableView reloadData];
        }
    } failure:^(NSError *error) {
    
    }];
    
    [self initInterface];
}

-(void)initInterface
{
    //左侧tableView
    _homeTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH/3.0, SCREEN_HEIGHT-64)];
    _homeTableView.backgroundColor = HDfillColor;
    _homeTableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    _homeTableView.layer.borderWidth = 1;
    _homeTableView.layer.borderColor = COLOR(210, 210, 210, 1).CGColor;
    _homeTableView.delegate = self;
    _homeTableView.dataSource = self;
    if ([_homeTableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [_homeTableView setSeparatorInset:UIEdgeInsetsZero];
    }
    if ([_homeTableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [_homeTableView setLayoutMargins:UIEdgeInsetsZero];
    }
    [self.view addSubview:_homeTableView];
    
    //右侧子tableView
    _subTableView = [[UITableView alloc] initWithFrame:CGRectMake(CGRectGetWidth(_homeTableView.frame), 64, SCREEN_WIDTH/3.0*2, SCREEN_HEIGHT-64)];
    _subTableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    _subTableView.delegate = self;
    _subTableView.dataSource = self;
    _subTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:_subTableView];
    
}


#pragma -mark tableView代理
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (tableView == _homeTableView) {
        if (_dataArray) {
           return _dataArray.count;
        }
    }
    if (tableView == _subTableView) {
        if (_subDataArray) {
            return  _subDataArray.count;

        }
    }
    return 0;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString * cellID = @"baobao";
    UITableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:cellID];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellID];
    }
    if (tableView == _homeTableView) {
        if ([cell respondsToSelector:@selector(setSeparatorInset:)]) {
            [cell setSeparatorInset:UIEdgeInsetsZero];
        }
        if ([cell respondsToSelector:@selector(setLayoutMargins:)]) {
            [cell setLayoutMargins:UIEdgeInsetsZero];
        }
        cell.backgroundColor = [UIColor clearColor];
        cell.textLabel.textAlignment = NSTextAlignmentRight;
        cell.textLabel.text = _dataArray[indexPath.row][@"topname"];
    } else  {
        cell.textLabel.textAlignment = NSTextAlignmentCenter;
        cell.textLabel.text = _subDataArray[indexPath.row][@"pcname"];
    }
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (tableView == _homeTableView) {
        if (self.homeCell) {
            self.homeCell.textLabel.textColor = [UIColor blackColor];
            self.homeCell.backgroundColor = [UIColor clearColor];
        }
        self.homeCell = [tableView cellForRowAtIndexPath:indexPath];
        self.homeCell.textLabel.textColor = ShareBackColor;
        self.homeCell.backgroundColor = [UIColor whiteColor];
        _subDataArray = _dataArray[indexPath.row][@"pclist"];
        NSLog(@"%@",_subDataArray);
        [_subTableView reloadData];
    }
    if (tableView == _subTableView) {
        YC_HotGoodsViewController * vc = [[YC_HotGoodsViewController alloc] init];
        vc.pname = _dataArray[indexPath.row][@"topname"];
        vc.pcid = _subDataArray[indexPath.row][@"pcid"];
        vc.car=_car;
        [self.navigationController pushViewController:vc animated:YES];
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
