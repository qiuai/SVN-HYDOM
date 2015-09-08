//
//  OS4myCenterController.m
//  HDCarProject
//
//  Created by xingso on 15/6/15.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "OS4myCenterController.h"
#import "myCenterController.h"
@interface OS4myCenterController ()

@end

@implementation OS4myCenterController

- (void)viewDidLoad {
    [super viewDidLoad];
    myCenterController* mycenterVC=[[myCenterController alloc]initWithNibName:@"myCenterController" bundle:nil];
    UIScrollView* scollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, 320, 480)];
    [self.view addSubview:scollView];
    scollView.contentSize=CGSizeMake(320, 568);
    mycenterVC.view.frame=CGRectMake(0, 0, 320, 568);
    [scollView addSubview:mycenterVC.view];
    scollView.showsVerticalScrollIndicator=NO;
    [self addChildViewController:mycenterVC];
    
    self.automaticallyAdjustsScrollViewInsets=NO;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
