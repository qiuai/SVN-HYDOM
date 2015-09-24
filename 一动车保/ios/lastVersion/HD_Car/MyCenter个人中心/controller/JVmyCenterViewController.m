//
//  JVmyCenterViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/8.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVmyCenterViewController.h"
#import "SettingViewController.h"
#import "circleView.h"
#import "YC_CenterCell.h"
#import "carListViewController.h"
#import "JVmyOrderViewController.h"
#import "JVsuggestViewController.h"
#import "YC_DiscountViewController.h"
#import "YC_payToCountViewController.h"
#import "ChooseAreaViewController.h"
#import "JVcontactUsViewController.h"
#import "YC_VIPViewController.h"

@interface JVmyCenterViewController ()<UITableViewDataSource,UITableViewDelegate>

@property(nonatomic, strong)circleView * myIconView;  //头像view

@property(nonatomic, strong)UILabel * myNameLabel;

/**父容器*/
@property(nonatomic,weak)CYTabbarContainer* parentContainer;

@property(nonatomic,strong)JVuserModel* userModel;
@end

@implementation JVmyCenterViewController

-(CYTabbarContainer *)parentContainer{
    if (!_parentContainer) {
        _parentContainer=(CYTabbarContainer*)self.parentViewController.parentViewController;
    }
    return _parentContainer;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.automaticallyAdjustsScrollViewInsets = NO;
    self.view.backgroundColor = COLOR(220, 220, 220, 1);
    [self initInterface];
    
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    //已经注销
    if ([userDefaultManager getUserWithToken]==nil) {
        [self.parentContainer changeControllerWithBtnTag:0];
        return;
    }
    
     self.userModel=[userDefaultManager getUserModel];
    if (self.userModel==nil) {
        [HTTPconnect sendPOSTHttpWithUrl:GetUserInforAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
            if (![responseObject isKindOfClass:[NSString class]]) {
                JVuserModel* m=[[JVuserModel alloc]init];
                m.nickname=responseObject[@"nickname"];
                m.userImageURL=responseObject[@"photo"];
                m.update=NO;
                [userDefaultManager saveUserModel:m];
                self.userModel=m;
                self.myNameLabel.text=_userModel.nickname;
                [self.myIconView setURL:imageURLWithPath(_userModel.userImageURL)];
            }else{
                warn(responseObject);
            }
        } failure:^(NSError *error) {
            warn(@"请检查网络");
        }];
    }else{
        if (self.userModel.update==YES) {
            self.myNameLabel.text=_userModel.nickname;
            [self.myIconView setImage:_userModel.userImage];
        }else{
        self.myNameLabel.text=_userModel.nickname;
        [self.myIconView setURL:imageURLWithPath(_userModel.userImageURL)];
        }
    }
    [self.parentContainer showTabbar];
}

-(void)initInterface
{
    
    //整体scrollView
    UIScrollView * scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT-50)];
    scrollView.contentSize = CGSizeMake(SCREEN_WIDTH, SCREEN_HEIGHT+60);
    scrollView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:scrollView];
    
    //头部视图
    UIView * headView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH,SCREEN_WIDTH/2.0+30)];
    headView.backgroundColor = ShareBackColor;
    [scrollView addSubview:headView];
    CGFloat y=(headView.frame.size.height-86) /2.0-20;
    CGFloat x=(headView.frame.size.width-86)/2.0;
    _myIconView = [[circleView alloc] initWithFrame:CGRectMake(x, y, 86, 86)];
    _myIconView.backgroundColor = [UIColor clearColor];
    [headView addSubview:_myIconView];
    
    UILabel * nameLabel = [[UILabel alloc] initWithFrame:CGRectMake((headView.frame.size.width-200) /2.0, y+86+10, 200, 40)];
    nameLabel.text = @"你的名称";
    nameLabel.textAlignment = NSTextAlignmentCenter;
    nameLabel.font = [UIFont boldSystemFontOfSize:17];
    nameLabel.textColor = [UIColor whiteColor];
    [headView addSubview:nameLabel];
    self.myNameLabel=nameLabel;
    
    
    UIButton * rightTopButton = [UIButton buttonWithType:UIButtonTypeCustom];
    rightTopButton.frame = CGRectMake(SCREEN_WIDTH-45, 25, 35, 35);
    rightTopButton.contentEdgeInsets = UIEdgeInsetsMake(5, 5, 5, 5);
    [rightTopButton setImage:[UIImage imageNamed:@"我的_03"] forState:UIControlStateNormal];
    [rightTopButton addTarget:self action:@selector(topButton:) forControlEvents:UIControlEventTouchUpInside];
    [headView addSubview:rightTopButton];
    
    
    //列表
    UITableView * tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, CGRectGetMaxY(headView.frame), SCREEN_WIDTH, SCREEN_HEIGHT*(2.6/3.0))];
    tableView.dataSource = self;
    tableView.delegate = self;
    tableView.backgroundColor = [UIColor clearColor];
    tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    tableView.rowHeight = CGRectGetHeight(tableView.frame)/10.0;
    tableView.scrollEnabled = NO;
    [scrollView addSubview:tableView];
    
    
}


#pragma mark tableView代理
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 7;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 1;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YC_CenterCell * cell = [[NSBundle mainBundle] loadNibNamed:@"YC_CenterCell" owner:nil options:nil
                            ].lastObject;
    cell.selectionStyle = 0;
    switch (indexPath.section) {
        case 0:{
            [cell.leftImageView setImage:[UIImage imageNamed:@"我的_24"]];
            cell.titleLabel.text = @"我的订单";
        }
            break;
        case 1:{
            [cell.leftImageView setImage:[UIImage imageNamed:@"mycenterCar"]];
            cell.titleLabel.text = @"车管家";
        }
            break;
        case 2:{
            [cell.leftImageView setImage:[UIImage imageNamed:@"myCenterYUe"]];
            cell.titleLabel.text = @"余额";
        }
            break;
        case 3:{
            [cell.leftImageView setImage:[UIImage imageNamed:@"mycenter000"]];
            cell.titleLabel.text = @"积分与消费券";
        }
            break;
        case 4:{
            [cell.leftImageView setImage:[UIImage imageNamed:@"我的_06"]];
            cell.titleLabel.text = @"会员卡";
        }
            break;
        case 5:{
            [cell.leftImageView setImage:[UIImage imageNamed:@"我的_29"]];
            cell.titleLabel.text = @"反馈";
        }
            break;
        case 6:{
            [cell.leftImageView setImage:[UIImage imageNamed:@"我的_32"]];
            cell.titleLabel.text = @"关于";
        }
            break;
        default:
            break;
    }
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 7;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView * view = [[UIView alloc] init];;
    view.frame = CGRectMake(0, 0, SCREEN_WIDTH, 7);
    return view;
}

#pragma mark 点击cell
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    
    switch (indexPath.section) {
        case 0:{
            JVmyOrderViewController * orderVC=[[JVmyOrderViewController alloc]init];
            [self.navigationController pushViewController:orderVC animated:NO];
            [self.parentContainer hiddenTabbar];
        }
            break;
        case 1:{
            carListViewController * vc = [[carListViewController alloc] init];
            [self.navigationController pushViewController:vc animated:NO];
            [self.parentContainer hiddenTabbar];
        }
            break;
        case 2:{
            YC_payToCountViewController * vc = [[YC_payToCountViewController alloc] init];
            [self.navigationController pushViewController:vc animated:NO];
            [self.parentContainer hiddenTabbar];
        }
            break;
        case 3:{
            YC_DiscountViewController * vc = [[YC_DiscountViewController alloc] init];
            [self.navigationController pushViewController:vc animated:NO];
            [self.parentContainer hiddenTabbar];
        }
            break;
        case 4:{            
            YC_VIPViewController* vc=[[YC_VIPViewController alloc]init];
            [self.navigationController pushViewController:vc animated:NO];
            [self.parentContainer hiddenTabbar];
        }
            break;
        case 5:{
            JVsuggestViewController* vc=[[JVsuggestViewController alloc]init];
            [self.navigationController pushViewController:vc animated:NO];
            [self.parentContainer hiddenTabbar];
        }
            break;
        case 6:{
            JVcontactUsViewController* vc=[[JVcontactUsViewController alloc]init];
            [self.navigationController pushViewController:vc animated:NO];
            [self.parentContainer hiddenTabbar];
        }
            break;
        default:
            break;
    }
}

#pragma mark topButton方法
-(void)topButton:(UIButton *)sender
{
    SettingViewController* vc=[[SettingViewController alloc]init];
    [self.navigationController pushViewController:vc animated:NO];
    [self.parentContainer hiddenTabbar];

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
