//
//  selectStoresForServiceOrderViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/21.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "selectStoresForServiceOrderViewController.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
#import "appointmentStoresCell.h"
#import "storeModel.h"
@interface selectStoresForServiceOrderViewController()<UITableViewDataSource,UITableViewDelegate>{
    NSInteger pageMax;
}
@property(nonatomic,strong)UITableView* tableView;
@property(nonatomic,strong)NSMutableDictionary* sendDictionary;
@property(nonatomic,strong)NSMutableArray* dataArray;
@end

@implementation selectStoresForServiceOrderViewController
#pragma -mark 懒加载

-(NSMutableArray *)dataArray{
    if (!_dataArray) {
        _dataArray=[NSMutableArray array];
    }
    return _dataArray;
}

-(UITableView *)tableView{
    if (!_tableView) {
        _tableView=[[UITableView alloc]initWithFrame:CGRM(0, 69, SCREEN_WIDTH, SCREEN_HEIGHT-69) style:0];
        _tableView.delegate=self;
        _tableView.dataSource=self;
    }
    return _tableView;
}
-(NSMutableDictionary *)sendDictionary{
    if (!_sendDictionary) {
        _sendDictionary=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
        [_sendDictionary setObject:@"scid" forKey:@"scid"];
        [_sendDictionary setObject:@"15" forKey:@"maxresult"];
        [_sendDictionary setObject:@"1" forKey:@"page"];
        [_sendDictionary setObject:[NSString stringWithFormat:@"%f",self.coordinate.longitude] forKey:@"lng"];
        [_sendDictionary setObject:[NSString stringWithFormat:@"%f",self.coordinate.latitude] forKey:@"lat"];
    }
    return _sendDictionary;
}
#pragma -mark layout
-(void)layoutView{
    HDNavigationView* titilView=[HDNavigationView navigationViewWithTitle:@"选择门店"];
    WEAKSELF;
    [titilView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:titilView];
    [self.view addSubview:self.tableView];
}
#pragma -mark 请求数据
-(void)initData{
    _po(self.sendDictionary);
[HTTPconnect sendPOSTHttpWithUrl:nil parameters:self.sendDictionary success:^(id responseObject) {
    if ([responseObject isKindOfClass:[NSString class]]) {
        _po(@"错误");
    }else{
        pageMax=[[responseObject objectForKey:@"pages"] integerValue];
        for (NSDictionary* dic in [responseObject objectForKey:@"list"]) {
            storeModel* model=[storeModel storeModelWithDict:dic];
            [self.dataArray addObject:model];
        }
        [self.tableView reloadData];
    }
} failure:^(NSError *error) {
    _po(@"EX");
}];
    
}

#pragma -mark tableView Delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    appointmentStoresCell* cell=[appointmentStoresCell cellWithTableView:tableView];
    cell.model=self.dataArray[indexPath.row];
    return cell;
}
//高度
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 85.0;
}

#pragma -mark 点击预定
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    //        取得当前控制器的index  跳转页面判断
    if (self.storesModelBlock!=nil) {
        self.storesModelBlock(self.dataArray[indexPath.row]);
    }
    NSInteger index=[[self.navigationController viewControllers]indexOfObject:self];
    UIViewController* popVC=[self.navigationController viewControllers][index-2];
    [self.navigationController popToViewController:popVC animated:YES];
}

#pragma -mark didLoad
- (void)viewDidLoad {
    [super viewDidLoad];
    [self layoutView];
    [self initData];
}



@end
