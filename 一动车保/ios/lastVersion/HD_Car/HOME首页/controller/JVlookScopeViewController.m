//
//  JVlookScopeViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/19.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVlookScopeViewController.h"

@interface JVlookScopeViewController ()

@end

@implementation JVlookScopeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self initNavViewWithtitle:@"支持范围"];
    UIWebView* webView=[[UIWebView alloc]initWithFrame:CGRM(0, 65, SCREEN_WIDTH, SCREEN_HEIGHT-65)];
    [HTTPconnect sendPOSTHttpWithUrl:supportGareaAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            [webView loadHTMLString:responseObject[@"text"] baseURL:nil];
        }else{
            warn(@"失败");
        }
        
    } failure:^(NSError *error) {
        warn(@"请求失败");
    }];
    
    [self.view addSubview:webView];
    
    
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
