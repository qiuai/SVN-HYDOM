//
//  selectCarModelViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "selectCarModelViewController.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
#import <SDWebImage/UIImageView+WebCache.h>
#import "MBProgressHUD.h"
#import "carModel.h"
#import "carOderViewController.h"
#import "EdtingCarInforViewController.h"
#import "carListViewController.h"

@interface selectCarModelViewController ()<UITableViewDataSource,UITableViewDelegate>
@property(nonatomic,strong)HDNavigationView* titleView;
@property(nonatomic,strong)UIView* carSignView;
@property(nonatomic,strong)UITableView* tableView;
//数据
@property(nonatomic,strong)NSMutableArray* dataArray;
@property(nonatomic,strong)MBProgressHUD *HUD;

//车型描述
@property(nonatomic,weak)UILabel* carLabel;

//车型Image
@property(nonatomic,weak)UIImageView* carimageView;

@end

@implementation selectCarModelViewController
#pragma -mark lan
-(HDNavigationView *)titleView{
    if (!_titleView) {
        _titleView=[HDNavigationView navigationViewWithTitle:@"选择车型"];
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
        _tableView=[[UITableView alloc]initWithFrame:CGRectMake(0, maxY+20, SCREEN_WIDTH, SCREEN_HEIGHT-maxY-20) style:UITableViewStylePlain];
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
    self.carimageView=carimage;
    [self.carSignView addSubview:carimage];
    UILabel* carLabel=[[UILabel alloc]initWithFrame:CGRectMake(0, 60, SCREEN_WIDTH, 20)];
    carLabel.textAlignment=NSTextAlignmentCenter;
    carLabel.text=[NSString stringWithFormat:@"%@  %@",self.selctCar.cbname,self.selctCar.csname];
    self.carLabel=carLabel;
    [self.carSignView addSubview:carLabel];
}

#pragma -mark 网络请求

-(void)sendHTTP{
    NSDictionary* requestDict=[userDefaultManager getUserWithToken];
    NSDictionary *muDIT=[NSMutableDictionary dictionaryWithDictionary:requestDict];
    [muDIT setValue:self.selctCar.csid forKey:@"csid"];
    [HTTPconnect sendPOSTHttpWithUrl:getCarModelAPI parameters:muDIT success:^(id responseObject) {
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
    
    return  self.dataArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* idf=@"CarCell";
    UITableViewCell* cell=[tableView dequeueReusableCellWithIdentifier:idf];
    if (!cell) {
        cell=[[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:idf];
    }
    cell.textLabel.text=[self.dataArray[indexPath.row] objectForKey:@"cmname"];
    return cell;
}




//点击
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell* cell=[tableView cellForRowAtIndexPath:indexPath];
    cell.selectionStyle=UITableViewCellSelectionStyleNone;
    NSString* cmid=[self.dataArray[indexPath.row] objectForKey:@"cmid"];
    NSString* cmname=[self.dataArray[indexPath.row] objectForKey:@"cmname"];
     carModel* cmodel=self.selctCar;
    cmodel.cmid=cmid;
    cmodel.cmname=cmname;

//        取得当前控制器的index  跳转页面判断
    NSInteger index=[[self.navigationController viewControllers]indexOfObject:self];
    UIViewController* popVC=[self.navigationController viewControllers][index-3];
        if ([popVC isKindOfClass:[EdtingCarInforViewController class]] ) {
            EdtingCarInforViewController* orVC=(EdtingCarInforViewController*)popVC;
            orVC.model=cmodel;
            orVC.pushVcStyle=pushStyleForUpdate;
            [self.navigationController popToViewController:orVC animated:YES];
        }else{
            EdtingCarInforViewController* vc=[[EdtingCarInforViewController alloc]init];
            vc.model=cmodel;
            [self.navigationController pushViewController:vc animated:NO];
        }
    
    
}



@end
