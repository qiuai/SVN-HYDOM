//
//  YC_SelectDiscountViewController.m
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_SelectDiscountViewController.h"
#import "scorePayCell.h"
#import "signValueObject.h"
@interface YC_SelectDiscountViewController ()<UITableViewDataSource,UITableViewDelegate>

@property(nonatomic, strong)UITableView * tableView;
@property(nonatomic, strong)NSMutableArray * dataArray;
@property(nonatomic, strong)discountModel * model;


@end

@implementation YC_SelectDiscountViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initNavViewWithtitle:@"选择优惠券"];
    self.view.backgroundColor = HDfillColor;
    [self initInterface];
    [self getHttpData];
}

-(void)initInterface
{
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 94, SCREEN_WIDTH, SCREEN_HEIGHT-94) style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.rowHeight = SCREEN_WIDTH*0.25;
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    _tableView.showsVerticalScrollIndicator = NO;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:_tableView];
}

-(void)getHttpData
{
    
    
    _po([UtilityMethod JVDebugUrlWithdict:[UtilityMethod getObjectData:self.sendModel] nsurl:getDiscountAbleListAPI]);
    
    [HTTPconnect sendGETWithUrl:getDiscountAbleListAPI parameters:[UtilityMethod getObjectData:self.sendModel] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSLog(@"%@",responseObject);
            _dataArray = [NSMutableArray new];
            _model = [discountModel new];
            for (NSDictionary * dic in responseObject[@"list"]) {
                [_model setValuesForKeysWithDictionary:dic];
                [_dataArray addObject:_model];
            }
            [_tableView reloadData];
        }
    } failure:^(NSError *error){
        
    }];

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
    if (_dataArray.count != 0) {
        discountModel * model = _dataArray[indexPath.row];
        cell.model = model;
        [cell.cellImageView sd_setImageWithURL:imageURLWithPath(model.cpimg)];
    }
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    scorePayCell * cell = (scorePayCell *)[tableView cellForRowAtIndexPath:indexPath];
    
    //使用优惠卷
    self.sendModel.cpid=cell.model.cpid;
    NSMutableDictionary* parameters=[NSMutableDictionary dictionary];
    [parameters setObject:_sendModel.uid forKey:@"uid"];
    [parameters setObject:_sendModel.token forKey:@"token"];
    if (_sendModel.scid!=nil) {
        [parameters setObject:_sendModel.scid forKey:@"scid"];
    }
    if (_sendModel.pid!=nil) {
        NSString* str=_sendModel.pid;
        if ([_sendModel.otype integerValue]==3) {
            str=[NSString stringWithFormat:@"%@_%@",_sendModel.pid,_sendModel.productCount];
        }
        [parameters setObject:str forKey:@"pid"];
    }
    [parameters setObject:_sendModel.otype forKey:@"otype"];
    
    [parameters setObject:_sendModel.cpid forKey:@"cpid"];
    
    
    _po([UtilityMethod JVDebugUrlWithdict:parameters nsurl:sumPricesAPI]);
    
    MBProgressHUD * huddd = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    [HTTPconnect sendPOSTHttpWithUrl:sumPricesAPI parameters:parameters success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
//            weakSelf.pricesView.allPrices.text= globalPrices(responseObject[@"orimoney"]);
//            weakSelf.pricesLabel.text=globalPrices(responseObject[@"paymoney"]);
//            weakSelf.pricesView.privilegePrices.text=globalPrices(responseObject[@"cpmoney"]);
            [huddd hide:YES];
            if (self.pricesArrayblock!=nil) {
                NSArray* array=@[globalPrices(responseObject[@"ocmoney"]),globalPrices(responseObject[@"opmoney"]),globalPrices(responseObject[@"orimoney"]),globalPrices(responseObject[@"cpmoney"]),globalPrices(responseObject[@"paymoney"])];
                self.pricesArrayblock(array);
            }
            [self.navigationController popViewControllerAnimated:YES];
        }else{
           [huddd hide:YES];
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        [huddd hide:YES];
        warn(@"网络错误");
    }];
    
    
}



-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
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
