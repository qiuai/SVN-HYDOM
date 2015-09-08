//
//  webViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "webViewController.h"

@interface webViewController ()
{
    UIWebView *webView;
}
@end

@implementation webViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    webView = [[UIWebView alloc] initWithFrame:[UIScreen mainScreen].bounds];
    NSString* url=@"http://192.168.0.158:8080/xueeryou/pdf/web/viewer.html";
    
//    NSString* url=
    
    NSURLRequest *request =[NSURLRequest requestWithURL:[NSURL URLWithString:url]];
    [self.view addSubview: webView];
    [webView loadRequest:request];
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
