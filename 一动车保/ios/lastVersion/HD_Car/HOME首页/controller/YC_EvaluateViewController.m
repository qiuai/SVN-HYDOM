//
//  YC_EvaluateViewController.m
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_EvaluateViewController.h"
#import "HDNavigationView.h"
#import "UIImageView+WebCache.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "YC_EvaluteCell.h"
#import <MJRefresh.h>

@interface YC_EvaluateViewController ()<UITableViewDataSource,UITableViewDelegate>
{
    NSInteger index;
}
@property(nonatomic, strong)UITableView * tableView;
@property(nonatomic, strong)NSMutableArray * dataArray;
@property (nonatomic, assign) CGFloat cellLabelHight;

@end

@implementation YC_EvaluateViewController

-(NSMutableArray *)dataArray{
    if (!_dataArray) {
        _dataArray = [NSMutableArray new];
    }
    return [_dataArray mutableCopy];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    index = 1;
    self.view.backgroundColor = [UIColor whiteColor];
    [self initNavViewWithtitle:@"评价"];
    WEAKSELF;
    [weakSelf.navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self initInterface];

    [self getDataFromServer:NO];
    

}

-(void)initInterface{

    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64) style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    _tableView.showsVerticalScrollIndicator = NO;
    WEAKSELF;
    _tableView.header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{
        //             进入刷新状态后会自动调用这个block
        [weakSelf getDataFromServer:NO];
    }];
    
    _tableView.footer = [MJRefreshAutoNormalFooter footerWithRefreshingBlock:^{
        [weakSelf getDataFromServer:YES];
    }];
    
    [self.view addSubview:_tableView];
    
}


#pragma -mark 请求数据
-(void)getDataFromServer:(BOOL)bl{
    if (bl==YES) {
        index++;
    }else{
        [self.dataArray removeAllObjects];
        index = 1;
    }
    NSMutableDictionary * parameters = [NSMutableDictionary new];
    [parameters setObject:self.pID forKey:@"pid"];
    [parameters setObject:[NSNumber numberWithInteger:index] forKey:@"page"];
    [parameters setObject:@10 forKey:@"maxresult"];
    
    [HTTPconnect sendGETWithUrl:getCommentListAPI parameters:parameters success:^(id responseObject) {
        [_tableView.header endRefreshing];
        [_tableView.footer endRefreshing];
        if (![responseObject isKindOfClass:[NSString class]]) {
            self.dataArray = responseObject[@"list"];
        } else {
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        [_tableView.header endRefreshing];
        [_tableView.footer endRefreshing];
        warn(@"网络错误");
    }];
    
}

#pragma mark -tableView协议
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_dataArray count];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YC_EvaluteCell * cell = [tableView dequeueReusableCellWithIdentifier:@"EvaluteCell"];
    if (!cell) {
        cell = [[NSBundle mainBundle] loadNibNamed:@"YC_EvaluteCell" owner:nil options:nil].lastObject;
    }

    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if (_dataArray.count!=0) {
        [cell.headImageView sd_setImageWithURL:imageURLWithPath(_dataArray[indexPath.row][@"cphoto"])];
        cell.timeLable.text = _dataArray[indexPath.row][@"ctime"];
        cell.nameLabel.text = _dataArray[indexPath.row][@"cperson"];
        cell.carLabel.text = _dataArray[indexPath.row][@"cmname"];
        _cellLabelHight = [cell setHightWithString:_dataArray[indexPath.row][@"content"]];
        [cell drawStarWithInt:[_dataArray[indexPath.row][@"star"] integerValue]];
    }
    return cell;
}


-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_cellLabelHight) {
        return 100-20+_cellLabelHight;
    }
    return 100;
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
