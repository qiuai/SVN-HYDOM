//
//  JVbaseViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/11.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVbaseViewController.h"

@interface JVbaseViewController ()

@end

@implementation JVbaseViewController
-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view.window];
    }
    return _HUD;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    
}


-(void)initNavViewWithtitle:(NSString*)title{
    self.navView=[HDNavigationView navigationViewWithTitle:title];
    WEAKSELF;
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:self.navView];
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
