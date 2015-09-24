//
//  YC_VIPViewController.m
//  HD_Car
//
//  Created by hydom on 9/10/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_VIPViewController.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "UIImageView+WebCache.h"
#import "scorePayCell.h"
#import "YC_VIPBuyViewController.h"
#import "YC_VipModel.h"
@interface YC_VIPViewController ()<UITableViewDataSource,UITableViewDelegate>
{
    NSString * money;
}
@property(nonatomic, strong)UITableView * tableView;
@property(nonatomic, strong)NSMutableArray * dataArray;

@end

@implementation YC_VIPViewController

-(NSMutableArray *)dataArray{
    if (!_dataArray) {
        _dataArray = [NSMutableArray new];
    }
    return _dataArray;
}

- (void)viewDidLoad {
    [super viewDidLoad];

    [self initUI];
    [self initData];
}

-(void)initUI{
    self.view.backgroundColor = [UIColor whiteColor];
    [self initNavViewWithtitle:@"会员卡"];
    WEAKSELF;
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64) style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.rowHeight = SCREEN_WIDTH*0.33+10;
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    _tableView.showsVerticalScrollIndicator = NO;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:_tableView];
}

-(void)initData{
    NSMutableDictionary * parameters = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [HTTPconnect sendGETWithUrl:getVipAPI parameters:parameters success:^(id responseObject) {
        _po(responseObject);
        if (![responseObject isKindOfClass:[NSString class]]) {
            money = responseObject[@"money"];
            for (NSDictionary * dic in responseObject[@"list"]) {
                YC_VipModel* model = [YC_VipModel new];
                [model setValuesForKeysWithDictionary:dic];
                [self.dataArray addObject:model];
            }
            [_tableView reloadData];
        }
    } failure:^(NSError *error){
        warn(@"网络错误");
    }];
    
}


#pragma -mark tableView协议
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.dataArray count];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString * cellID = @"baobao";
    scorePayCell * cell = [tableView dequeueReusableCellWithIdentifier:cellID];
    if (!cell) {
        cell = [[scorePayCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellID];
    }
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if (_dataArray.count>0) {
        YC_VipModel * model= _dataArray[indexPath.row];
        cell.vipModel = model;
        [cell.cellImageView sd_setImageWithURL:imageURLWithPath(model.cppimg)];
    }
    return cell;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    YC_VipModel * model = _dataArray[indexPath.row];
//        money = [NSString stringWithFormat:@"%.2f", ([money floatValue]-[model.cppprice floatValue])];
        YC_VIPBuyViewController * vc = [[YC_VIPBuyViewController alloc] init];
        vc.model = model;
        [self.navigationController pushViewController:vc animated:NO];
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
