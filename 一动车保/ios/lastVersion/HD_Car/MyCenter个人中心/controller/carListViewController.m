//
//  carListViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/30.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "carListViewController.h"
#import "carModel.h"
#import "HDNavigationView.h"
#import "carManagerCell.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
#import "MBProgressHUD.h"
#import "EdtingCarInforViewController.h"
#import "selectCarController.h"
#import "signValueObject.h"
@interface carListViewController ()<UITableViewDataSource,UITableViewDelegate>
@property(nonatomic,strong)HDNavigationView* nav;
@property(nonatomic,strong)UITableView* tableView;
@property(nonatomic,strong)NSMutableArray* dataArray;

@property(nonatomic,weak)carManagerCell* defaultCell;


@property(nonatomic,strong)MBProgressHUD *HUD;
@end

@implementation carListViewController
-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view.window];
    }
    return _HUD;
}
-(NSMutableArray *)dataArray{
    if (!_dataArray) {
        _dataArray=[NSMutableArray array];
    }
    return _dataArray;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    _nav=[HDNavigationView navigationViewWithTitle:@"车管家"];
    WEAKSELF;
    [_nav tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:_nav];
    self.tableView=[[UITableView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64) style:0];
    [self.view addSubview:self.tableView];
    self.tableView.delegate=self;
    self.tableView.dataSource=self;
    UIButton* bt=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH-80, 20, 80, 44)];
    [bt setTitle:@"添加" forState:0];
    [bt setTitleColor:[UIColor whiteColor] forState:0];
    bt.titleLabel.font=FONT14;
    [self.view addSubview:bt];
    [bt addTarget:self action:@selector(inssertCar) forControlEvents:UIControlEventTouchUpInside];
}


-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self initData];
}

-(void)initData{
    [self.dataArray removeAllObjects];
//    _po([UtilityMethod JVDebugUrlWithdict:[userDefaultManager getUserWithToken] nsurl:userCarListAPI]);
    [HTTPconnect sendPOSTHttpWithUrl:userCarListAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            for (NSDictionary* dict in [responseObject objectForKey:@"list"]) {
                carModel* model=[carModel carModelWithDictionary:dict];
                [self.dataArray addObject:model];
            }
            [self.tableView reloadData];
        }
        else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        warn(@"网络异常");
    }];
}

#pragma -mark tableViewDelegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
  return  self.dataArray.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString*idfier=@"carManagerCell";
    carManagerCell* cell=[tableView dequeueReusableCellWithIdentifier:idfier];
    if (!cell) {
        cell=[[[NSBundle mainBundle]loadNibNamed:@"carManagerCell" owner:nil options:nil]firstObject];
        cell.viewController=self;
        [cell setChangeDefaultMethod:@selector(setDefault:)];
        [cell setDeleteMethod:@selector(deleteCarModel:)];
        [cell setUpdateMethod:@selector(updateCarModel:)];
    }
    carModel* m=self.dataArray[indexPath.row];
    if ([m.defaultCar intValue]==1) {
        self.defaultCell=cell;
        if (self.defaultCarModel!=nil) {
            self.defaultCarModel(m);
        }
    }
    [cell initWithModel:m];
    return cell;
}


-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 200.0;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell* cell=[tableView cellForRowAtIndexPath:indexPath];
    cell.selectionStyle=UITableViewCellSelectionStyleNone;
}

#pragma -mark 设置默认
-(void)setDefault:(carManagerCell*)cell{
    
    [self.view addSubview:self.HUD];
    self.HUD.labelText =@"请稍后";
    self.HUD.mode = MBProgressHUDModeIndeterminate;
    [self.HUD show:YES];
    NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    NSIndexPath *indexPath=[self.tableView indexPathForCell:cell];
    carModel* m=self.dataArray[indexPath.row];
    [dict setObject:m.ucid forKey:@"ucid"];
    [dict setObject:[NSNumber numberWithInt:1] forKey:@"defaultCar"];
    [dict setObject:m.cmid forKey:@"cmid"];
    [HTTPconnect sendPOSTHttpWithUrl:carEditAPI parameters:dict success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            [cell changeDefault:YES];
            if (self.defaultCell!=nil) {
                [self.defaultCell changeDefault:NO];
            }
            self.defaultCell=cell;
            m.defaultCar=[NSNumber numberWithInteger:1];
            sleep(1);
            warn(@"设置默认成功");
            if (self.defaultCarModel!=nil) {
                self.defaultCarModel(m);
            }
        }else{
            sleep(1);
            warn(responseObject);
        }
    } failure:^(NSError *error) {
         sleep(1);
        warn(@"请检查网络");
    }];
}

#pragma -mark 删除
-(void)deleteCarModel:(carManagerCell*)cell{
    NSIndexPath * indexPath=[self.tableView indexPathForCell:cell];
    [self.view addSubview:self.HUD];
    self.HUD.labelText =@"请稍后";
    self.HUD.mode = MBProgressHUDModeIndeterminate;
    [self.HUD show:YES];
    NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    carModel* m=self.dataArray[indexPath.row];
    
    if ([cell isEqual:self.defaultCell]) {
        warn(@"不能删除默认车型");
        return;
    }
    [dict setObject:m.ucid forKey:@"ucid"];
    [HTTPconnect sendPOSTHttpWithUrl:careDeleteAPI parameters:dict success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            sleep(1);
            warn(@"删除成功");
            [self.dataArray removeObjectAtIndex:indexPath.row];
            [self.tableView reloadData];
        }else{
            sleep(1);
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        sleep(1);
        warn(@"请检查网络");
    }];
}
#pragma -mark 编辑
-(void)updateCarModel:(carManagerCell*)cell{
    EdtingCarInforViewController* vc=[[EdtingCarInforViewController alloc]init];
    NSIndexPath * indexPath=[self.tableView indexPathForCell:cell];
    carModel* m=(carModel*)self.dataArray[indexPath.row];
    vc.model=m;
    vc.pushVcStyle=pushStyleForUpdate;
    signValueObject* sigh=[signValueObject shareSignValue];
    [sigh SetDefaultCar:[m.defaultCar integerValue]==1?YES:NO];
    [self.navigationController pushViewController:vc animated:NO];
}

#pragma -mark 新增车型
-(void)inssertCar{
    selectCarController* vc=[[selectCarController alloc]init];
    if (self.dataArray.count<1) {
        signValueObject *sign=[signValueObject shareSignValue];
        [sign SetDefaultCar:YES];
    }
    [self.navigationController pushViewController:vc animated:NO];
}

@end
