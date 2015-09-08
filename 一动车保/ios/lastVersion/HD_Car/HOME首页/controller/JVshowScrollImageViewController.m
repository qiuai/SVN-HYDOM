//
//  JVshowScrollImageViewController.m
//  HD_Car
//
//  Created by cc on 15/8/16.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVshowScrollImageViewController.h"
#import "userDefaultManager.h"
@interface JVshowScrollImageViewController ()
@property(nonatomic,strong)UIWebView* webView;
@end

@implementation JVshowScrollImageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initNavViewWithtitle:@"详情"];
   self.webView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 65, SCREEN_WIDTH , SCREEN_HEIGHT-65)];
    NSDictionary * dic = [userDefaultManager getUserWithToken];
    NSString * url = [NSString stringWithFormat:@"%@?uid=%@&&token=%@",self.url, dic[@"uid"],dic[@"token"]];
    NSURLRequest *request =[NSURLRequest requestWithURL:[NSURL URLWithString:url]];
    [self.view addSubview: self.webView];
    [self.webView loadRequest:request];
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
