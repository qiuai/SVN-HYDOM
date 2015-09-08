//
//  selectCarSeriesViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "selectCarSeriesViewController.h"
#import "HDNavigationView.h"
#import "carModel.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
#import <SDWebImage/UIImageView+WebCache.h>
#import "MBProgressHUD.h"
#import "selectCarModelViewController.h"

@interface selectCarSeriesViewController ()<UITableViewDataSource,UITableViewDelegate>
@property(nonatomic,strong)HDNavigationView* titleView;
@property(nonatomic,strong)UIView* carSignView;
@property(nonatomic,strong)UITableView* tableView;
//数据
@property(nonatomic,strong)NSMutableArray* dataArray;
@property(nonatomic,strong)MBProgressHUD *HUD;
@end

@implementation selectCarSeriesViewController

#pragma -mark lan
-(HDNavigationView *)titleView{
    if (!_titleView) {
        _titleView=[HDNavigationView navigationViewWithTitle:@"选择车系"];
        WEAKSELF;
        [_titleView tapLeftViewWithImageName:nil tapBlock:^{
            [weakSelf.navigationController popViewControllerAnimated:YES];
        }];
    }
    return _titleView;
}
-(UITableView *)tableView{
    if (!_tableView) {
    CGFloat maxY=CGRectGetMaxY(self.carSignView.frame);
        _tableView=[[UITableView alloc]initWithFrame:CGRectMake(0, maxY+20, SCREEN_WIDTH, SCREEN_HEIGHT-maxY-20) style:UITableViewStyleGrouped];
        _tableView.delegate=self;
        _tableView.dataSource=self;
    }
    return _tableView;
}
-(NSMutableArray *)dataArray{
    if (!_dataArray) {
        _dataArray=[NSMutableArray array];
    }
    return _dataArray;
}
-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view];
    }
    return _HUD;
}

#pragma -mark LOAD
- (void)viewDidLoad {
    [super viewDidLoad];
    [self.view addSubview:self.titleView];
    [self carSignLayout];
    [self.view addSubview:self.tableView];
    [self sendHTTP];
}

-(void)carSignLayout{
    self.carSignView=[[UIView alloc]initWithFrame:CGRectMake(0, 69, SCREEN_WIDTH, 80)];
//    self.carSignView.backgroundColor=randomColor;
    [self.view addSubview:self.carSignView];
    UIImageView* carimage=[[UIImageView alloc]init];
    NSURL* url=imageURLWithPath(self.selctCar.cbimageURL);
    [carimage sd_setImageWithURL:url placeholderImage:[UIImage imageNamed:FillImage]];
    carimage.frame=CGRectMake((SCREEN_WIDTH-50)/2.0, 10, 50, 50);
    [self.carSignView addSubview:carimage];
    UILabel* carLabel=[[UILabel alloc]initWithFrame:CGRectMake(0, 60, SCREEN_WIDTH, 20)];
    carLabel.textAlignment=NSTextAlignmentCenter;
    carLabel.text=self.selctCar.cbname;
    [self.carSignView addSubview:carLabel];
}

#pragma -mark 网络请求

-(void)sendHTTP{
   NSDictionary* requestDict=[userDefaultManager getUserWithToken];
    NSDictionary *muDIT=[NSMutableDictionary dictionaryWithDictionary:requestDict];
    [muDIT setValue:self.selctCar.cbid forKey:@"cbid"];
    [HTTPconnect sendPOSTHttpWithUrl:getCarseriesAPI parameters:muDIT success:^(id responseObject) {
        self.dataArray=[responseObject objectForKey:@"list"];
        [self.tableView reloadData];
   } failure:^(NSError *error) {
       [self.view addSubview:self.HUD];
       self.HUD.labelText = @"网络异常，请检查网络!!";
       self.HUD.mode=MBProgressHUDModeText;
       [self.HUD showAnimated:YES whileExecutingBlock:^{
           sleep(2); // 睡眠3秒
       } completionBlock:^{
           [self.HUD removeFromSuperview];
       }
        ];

}];
    

}
#pragma tableViewDelegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
  
    NSDictionary* d=self.dataArray[section];
    NSArray* array=[d objectForKey:@"cslist"];
    return  array.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* idf=@"CarCell";
    UITableViewCell* cell=[tableView dequeueReusableCellWithIdentifier:idf];
    if (!cell) {
        cell=[[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:idf];
    }
    NSDictionary* d=self.dataArray[indexPath.section];
    NSArray* array=[d objectForKey:@"cslist"];
    NSString* csname=[array[indexPath.row] objectForKey:@"csname"];
    cell.textLabel.text=csname;
//    cell.imageView.image=[UIImage imageNamed:@"a123.jpg"];
    return cell;
}

//多少组
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    
    return  self.dataArray.count;
}
//分组标题
- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
    NSDictionary* d=self.dataArray[section];
    NSString* str=[d objectForKey:@"topname"];
    return str;
}

//分组 标题高度
- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 30.0;
}
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 1.0;
}



//点击
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell* cell=[tableView cellForRowAtIndexPath:indexPath];
    cell.selectionStyle=UITableViewCellSelectionStyleNone;
    NSDictionary* d=self.dataArray[indexPath.section];
    NSArray* array=[d objectForKey:@"cslist"];
    NSString* csid=[array[indexPath.row] objectForKey:@"csid"];
    NSString* csname=[array[indexPath.row] objectForKey:@"csname"];
    selectCarModelViewController* vc=[[selectCarModelViewController alloc]init];
    carModel* valueModel=self.selctCar;
    valueModel.csid=csid;
    valueModel.csname=csname;
    vc.selctCar=valueModel;
    [self.navigationController pushViewController:vc animated:NO];
}





@end
