//
//  storesDetailController.m
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/18.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "storesDetailController.h"
#import "HDNavigationView.h"
#import "qqMusicContainer.h"
@interface storesDetailController ()<UIScrollViewDelegate>


#pragma -mark 控件
@property(nonatomic,strong)HDNavigationView* titleView;

@end

@implementation storesDetailController
#pragma -mark 懒加载
-(HDNavigationView *)titleView{

    if (!_titleView) {
        _titleView=[HDNavigationView navigationViewWithTitle:@"门店"];
        [_titleView tapLeftViewWithImageName:@"comeBack" tapBlock:^{
            [self.navigationController popViewControllerAnimated:YES];
        }];
    }
    return _titleView;
}


#pragma -mark 初始化布局
- (void)viewDidLoad {
    [super viewDidLoad];
    [self.view addSubview:self.titleView];
    qqMusicContainer* qqContainer=[[qqMusicContainer alloc]init];
    qqContainer.view.frame=CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64);
    [self addChildViewController:qqContainer];
    [self.view addSubview:qqContainer.view];
}








@end
