//
//  StoresViewController.m
//  HD_InsuranceCar
//
//  Created by cc on 15/6/13.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "StoresViewController.h"
#import "HDNavigationView.h"
#import "storesCell.h"
#import "storesDetailController.h"
#import "CYTabbarContainer.h"
#import "sotresSelectRankView.h"

@interface StoresViewController ()<UITableViewDataSource,UITableViewDelegate>
@property(nonatomic,weak)CYTabbarContainer* parentContainer; //父容器
/**页面title*/
@property(nonatomic,strong)HDNavigationView* navViewCOM;
@property(nonatomic,strong)UITableView* storesTableView;
@property(nonatomic,strong)UIView* categoryView;


/**RANKView*/
@property(nonatomic,strong)UIView* rankView;
@end

@implementation StoresViewController
#pragma -mark 懒加载

-(CYTabbarContainer *)parentContainer{
    if (!_parentContainer) {
        _parentContainer=(CYTabbarContainer*)self.parentViewController.parentViewController;
    }
    return _parentContainer;
}

-(HDNavigationView *)navViewCOM{
    if(!_navViewCOM){
        _navViewCOM=[HDNavigationView navigationViewWithTitle:@"门店"];
    }
    return _navViewCOM;
}

-(UIView *)categoryView{
    if (!_categoryView) {
        _categoryView=[[UIView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(self.navViewCOM.frame), SCREEN_WIDTH, 35)];
        _categoryView.backgroundColor=WHITECOLOR;
    }
    return _categoryView;
}

-(UITableView *)storesTableView{
    if (!_storesTableView) {
        _storesTableView=[[UITableView alloc]initWithFrame:CGRectMake(0, 65+40, SCREEN_WIDTH, SCREEN_HEIGHT-64-49-40) style:UITableViewStylePlain];
        _storesTableView.dataSource=self;
        _storesTableView.delegate=self;
    }return _storesTableView;
}
#pragma -mark 搜索视图Layout
-(void)LayoutCategoryView{

    CGFloat h=CGRectGetHeight(self.categoryView.frame);
    CGFloat width_3=SCREEN_WIDTH/3.0;
    sotresSelectRankView* categoryView1=[[[NSBundle mainBundle]loadNibNamed:@"sotresSelectRankView" owner:nil options:nil]lastObject];
    categoryView1.frame=CGRectMake(0, 0, width_3, h);
    sotresSelectRankView* categoryView2=[[[NSBundle mainBundle]loadNibNamed:@"sotresSelectRankView" owner:nil options:nil]lastObject];
    categoryView2.frame=CGRectMake(width_3, 0, width_3, h);
    [categoryView2 changeTtile:@"默认排序"];
    sotresSelectRankView* categoryView3=[[[NSBundle mainBundle]loadNibNamed:@"sotresSelectRankView" owner:nil options:nil]lastObject];
    categoryView3.frame=CGRectMake(width_3*2, 0, width_3, h);
    [categoryView3 changeTtile:@"服务类型"];
    categoryView1.tag=23;
    categoryView2.tag=24;
    categoryView3.tag=25;
    [categoryView1 addTapGestureRecognizerWithTarget:self action:@selector(categoryFun:)];
    [categoryView2 addTapGestureRecognizerWithTarget:self action:@selector(categoryFun:)];
    [categoryView3 addTapGestureRecognizerWithTarget:self action:@selector(categoryFun:)];
    [self.categoryView addSubview:categoryView1];
    [self.categoryView addSubview:categoryView2];
    [self.categoryView addSubview:categoryView3];
    
    CGFloat maxY=CGRectGetMaxY(self.categoryView.frame);
    CGFloat rankViewH=SCREEN_HEIGHT-49-maxY;
    self.rankView=[[UIView alloc]initWithFrame:CGRectMake(0, maxY-rankViewH, SCREEN_WIDTH, rankViewH)];
    self.rankView.backgroundColor=randomColor;
    
}

-(void)categoryFun:(UITapGestureRecognizer*)sender{
    switch (sender.view.tag) {
        case 23:
            [self displayRankView];
            break;
        case 24:
            _po(@"排序点击");
            break;
        case 25:
            _po(@"服务类型点击");
            break;
    }

}

-(void)displayRankView{
    static BOOL rankViewState=NO;
    CGFloat maxY=CGRectGetMaxY(self.categoryView.frame);
    
    CGFloat rankViewH=SCREEN_HEIGHT-49-maxY;
    if (rankViewState) {
        [UIView animateWithDuration:0.5 animations:^{
            self.rankView.y=maxY-rankViewH;
        } completion:^(BOOL finished) {
            [self.rankView removeFromSuperview];
        }];
    }else{
        [self.view addSubview:self.rankView];
        [UIView animateWithDuration:0.5 animations:^{
            self.rankView.y=maxY;
        }];
    }
    rankViewState=!rankViewState;
}

#pragma -mark 页面初始化
- (void)viewDidLoad {
    [super viewDidLoad];
    [self.view setBackgroundColor:HDfillColor];
    [self.view addSubview:self.navViewCOM];
    [self.view addSubview:self.storesTableView];
    [self.view addSubview:self.categoryView];
    [self LayoutCategoryView];
}

#pragma -mark 显示tabbar
-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self.parentContainer showTabbar];

}

#pragma -mark tableView协议
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 40;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    storesCell* cell=[storesCell cellWithTableView:tableView];
    return cell;
}


//高度
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 85.0;
}
//点击cell
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell* cell=[tableView cellForRowAtIndexPath:indexPath];
    cell.selectionStyle=UITableViewCellSelectionStyleNone;
    storesDetailController *vc=[[storesDetailController alloc]init];
    [self.parentContainer hiddenTabbar];
    [self.navigationController pushViewController:vc animated:YES];
    
}



#pragma -mark 点击搜索 doing
-(void)clickSearchDone{
    
    
}





@end
