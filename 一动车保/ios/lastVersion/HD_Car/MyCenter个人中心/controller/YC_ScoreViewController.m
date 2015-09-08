//
//  YC_ScoreViewController.m
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_ScoreViewController.h"
#import "HDNavigationView.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "UIImageView+WebCache.h"
#import "discountModel.h"
#import "scorePayCell.h"
#import <MBProgressHUD.h>
@interface YC_ScoreViewController ()<UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate>

@property(nonatomic, strong)NSDictionary * dataDic;  //请求数据
@property(nonatomic, strong)NSMutableArray * dataArray;
@property(nonatomic, strong)UILabel * scoreLabel;
@property(nonatomic, strong)UITableView * tableView;
@property(nonatomic, strong)MBProgressHUD * HUD;
@property(nonatomic, weak)scorePayCell * selectCell;

@end

@implementation YC_ScoreViewController

-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view.window];
    }
    return _HUD;
}

-(NSMutableArray *)dataArray{
    if (!_dataArray) {
        _dataArray = [NSMutableArray new];
    }
    return _dataArray;
}

-(NSDictionary *)dataDic{
    if (!_dataDic) {
        _dataDic = [NSMutableDictionary new];
    }
    return _dataDic;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = HDfillColor;
    HDNavigationView * navc = [HDNavigationView navigationViewWithTitle:@"我的积分"];
    WEAKSELF;
    [navc tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:navc];
    [self initInterface];
    
    NSMutableDictionary * parameters = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [HTTPconnect sendGETWithUrl:getMyScoreAPI parameters:parameters success:^(id responseObject) {

        if (![responseObject isKindOfClass:[NSString class]]) {
            self.dataDic = responseObject;
            for (NSDictionary * dic in responseObject[@"list"]) {
                discountModel* _model = [discountModel new];
                [_model setValuesForKeysWithDictionary:dic];
                [self.dataArray addObject:_model];
            }
            [self reloadView];
        }
    } failure:nil];
    
}

-(void)initInterface{
    self.scoreLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, 30)];
    _scoreLabel.textAlignment = NSTextAlignmentCenter;
    _scoreLabel.textColor = [UIColor redColor];
    [self.view addSubview:_scoreLabel];
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 94, SCREEN_WIDTH, SCREEN_HEIGHT-94) style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.rowHeight = SCREEN_WIDTH*0.33+10;
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    _tableView.showsVerticalScrollIndicator = NO;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:_tableView];
    
}

-(void)reloadView{
    self.scoreLabel.text = [NSString stringWithFormat:@"%@分",self.dataDic[@"score"]];
    [self.tableView reloadData];
}


#pragma mark -tableView协议
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
        discountModel * model= _dataArray[indexPath.row];
        cell.model = model;
        [cell.cellImageView sd_setImageWithURL:imageURLWithPath(model.cpimg)];
    }
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    self.selectCell = (scorePayCell *)[tableView cellForRowAtIndexPath:indexPath];
    [self judgeScorePay:self.selectCell.model];
}



//积分兑换并且判断
-(void)judgeScorePay:(discountModel *)model
{
    NSInteger score = [_scoreLabel.text integerValue];
    NSInteger scorePay = [model.cpexscore integerValue];
    if (score > 0 && score >= scorePay) {
        UIAlertView * alertView = [[UIAlertView alloc] initWithTitle:@"积分兑换" message:[NSString stringWithFormat:@"兑换该优惠券需要%@积分", self.selectCell.model.cpexscore] delegate:self cancelButtonTitle:@"确认" otherButtonTitles:@"取消", nil];
        [alertView show];
    } else {
        warn(@"您的积分不足！");
    }
    
}


#pragma -mark UIAlertView代理
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) {
        NSInteger score = [_scoreLabel.text integerValue];
        NSInteger scorePay = [_selectCell.model.cpexscore integerValue];
        NSMutableDictionary * parameters = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
        [parameters setObject:_selectCell.model.cpid forKey:@"cpid"];
        [HTTPconnect sendPOSTHttpWithUrl:postScorePayAPI parameters:parameters success:^(id responseObject) {
            if (![responseObject isKindOfClass:[NSString class]]) {
                [self.view addSubview:self.HUD];
                _scoreLabel.text = [NSString stringWithFormat:@"%ld分",score-scorePay];
                self.HUD.labelText =@"兑换成功";
                self.HUD.mode=MBProgressHUDModeText;
                [self.HUD showAnimated:YES whileExecutingBlock:^{
                    sleep(2);
                } completionBlock:^{
                    [self.HUD removeFromSuperview];
                }];
            }else{
                warn(responseObject);
            }
        } failure:^(NSError *error) {
            warn(@"网络错误");
        }];
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
