//
//  YC_webViewViewController.m
//  HD_Car
//
//  Created by hydom on 8/12/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_webViewViewController.h"
#import "HDNavigationView.h"
@interface YC_webViewViewController ()

@property(nonatomic, strong)UIWebView * webView;

@end

@implementation YC_webViewViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    HDNavigationView * navc = [HDNavigationView navigationViewWithTitle:@"图文详情"];
    WEAKSELF;
    [navc tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:navc];
    
    [self initInterface];
    
}

-(void)initInterface{
    self.webView = [[UIWebView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64)];
    [self.view addSubview:_webView];
    
    NSLog(@"%@", self.urlString);
    NSURL *url =[NSURL URLWithString:self.urlString];
    NSURLRequest *request =[NSURLRequest requestWithURL:url];
    [_webView loadRequest:request];
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
