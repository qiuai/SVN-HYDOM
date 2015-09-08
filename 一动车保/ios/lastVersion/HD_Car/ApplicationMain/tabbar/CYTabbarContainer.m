//
//  CYTabbarContainer.m
//  tabbarContainer
//
//  Created by cc on 15/5/9.
//  Copyright (c) 2015年 cc. All rights reserved.
//

#import "CYTabbarContainer.h"
#import "CYtabbar.h"
#import "HomeViewController.h"
#import "DiscoverController.h"
#import "JVmyCenterViewController.h"
#import "StoresViewController.h"
#import "loginViewController.h"
#import "userDefaultManager.h"


@interface CYTabbarContainer ()
@property(nonatomic,weak)UIView* displayingView;
@property(nonatomic,strong)CYtabbar* tabbar;
@end

@implementation CYTabbarContainer

- (void)viewDidLoad {
    [super viewDidLoad];
    [self addChildControllers];
    [self addTabber];
}
#pragma -mark 添加底部tabber
-(void)addTabber{
    __weak typeof(self) thisVC=self;
    self.tabbar=[CYtabbar tabbarInitWithClickBlock:^(NSInteger btnTag) {
        [thisVC changeControllerWithBtnTag:btnTag];
    }];
    [self.view addSubview:self.tabbar];
}

#pragma -mark 点击切换事件
-(void)changeControllerWithBtnTag:(NSInteger )tag{
    [self.tabbar changeTabbarByTag:tag];
    if (tag==2) {
//        判断登陆
        if (![userDefaultManager getUserWithToken]) {
            //切换上次选中
            [self.tabbar changeLastSelectTabbar];
            loginViewController* loginVC=[[loginViewController alloc]init];
            loginVC.successLogin=^(){
                [self.tabbar changeTabbarByTag:2];
                [self.displayingView removeFromSuperview];
                UINavigationController* navVC =self.childViewControllers[2];
                self.displayingView=navVC.view;
                [self.view insertSubview:self.displayingView atIndex:0];
            };
            [self presentViewController:loginVC animated:YES completion:nil];
            return;
        }else{
            [self.displayingView removeFromSuperview];
            UINavigationController* navVC =self.childViewControllers[tag];
            self.displayingView=navVC.view;
            [self.view insertSubview:self.displayingView atIndex:0];
            return;
        }
    }
    [self.displayingView removeFromSuperview];
   UINavigationController* navVC =self.childViewControllers[tag];
    self.displayingView=navVC.view;
    [self.view insertSubview:self.displayingView atIndex:0];
}




#pragma -mark 添加子控制器
-(void)addChildControllers{
    HomeViewController* vc1=[[HomeViewController alloc]init];
    [self creatNavigationControllerByVC:vc1 TitleName:@"首页"];
    DiscoverController* vc2=[[DiscoverController alloc]init];
    [self creatNavigationControllerByVC:vc2 TitleName:@"发现"];
    JVmyCenterViewController* vc3=[[JVmyCenterViewController alloc]init];
    [self creatNavigationControllerByVC:vc3 TitleName:@"我的"];
}

#pragma -mark 包装NavigationController
-(void)creatNavigationControllerByVC:(UIViewController*)vc TitleName:(NSString*)title{
    vc.title=title;
    vc.view.frame=[UIScreen mainScreen].bounds;
    UINavigationController* nav=[[UINavigationController alloc]initWithRootViewController:vc];
    nav.navigationBarHidden=YES;
    [self addChildViewController:nav];
}



#pragma -mark 隐藏 显示tabbar
-(void)showTabbar{
    [self.tabbar setHidden:NO];
}
-(void)hiddenTabbar{
    [self.tabbar setHidden:YES];
}
@end
