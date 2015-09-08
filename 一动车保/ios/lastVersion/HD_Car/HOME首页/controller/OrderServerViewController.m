//
//  OrderServerViewController.m
//  HD_Car
//
//  Created by hydom on 7/20/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "OrderServerViewController.h"
#import "HDNavigationView.h"
#import "OrderServerCell.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
@interface OrderServerViewController ()<UITableViewDataSource,UITableViewDelegate>

@property(nonatomic, strong)UILabel * headLabel;
@property(nonatomic, strong)UITableView * tableView;

@end

@implementation OrderServerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.view.backgroundColor = [UIColor whiteColor];
    HDNavigationView * naveView = [HDNavigationView navigationViewWithTitle:@"服务订单"];
    WEAKSELF;
    [naveView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:naveView];
    [self initInterface];
}

-(void)initInterface
{
    [self yuanchuan];
    UIView * headView = [[UIView alloc] init];
    headView.frame = CGRectMake(0, 64, SCREEN_WIDTH, 26);
    headView.backgroundColor = COLOR(82, 180, 234, 1);
    [self.view addSubview:headView];
    
    _headLabel = [[UILabel alloc] init];
    _headLabel.frame = CGRectMake(0, 5, SCREEN_WIDTH, 16);
    _headLabel.textAlignment = NSTextAlignmentCenter;
    _headLabel.text = @"适配车型：XXXXXX";
    _headLabel.font = [UIFont systemFontOfSize:14];
    [headView addSubview:_headLabel];
    
    UITapGestureRecognizer * headTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(headViewTapRG)];
    [headView addGestureRecognizer:headTapGR];
    
    UIView * buttonView = [[UIView alloc] init];
    buttonView.frame = CGRectMake(0, 90, SCREEN_WIDTH, 30);
    [self.view addSubview:buttonView];
    
    UIButton * brandButton = [UIButton buttonWithType:UIButtonTypeCustom];
    brandButton.frame = CGRectMake(0, 0, SCREEN_WIDTH/2, CGRectGetHeight(buttonView.frame));
    [brandButton setTitle:@"全部品牌" forState:UIControlStateNormal];
    [brandButton setTitleColor:COLOR(137, 137, 137, 1) forState:UIControlStateNormal];
    brandButton.tag = 100;
    brandButton.titleLabel.font = [UIFont systemFontOfSize:18];
    [brandButton addTarget:self action:@selector(selectButton:) forControlEvents:UIControlEventTouchUpInside];
    [buttonView addSubview:brandButton];
    
    UIButton * typeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    typeButton.frame = CGRectMake(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, CGRectGetHeight(buttonView.frame));
    [typeButton setTitle:@"全部类型" forState:UIControlStateNormal];
    [typeButton setTitleColor:COLOR(137, 137, 137, 1) forState:UIControlStateNormal];
    typeButton.tag = 101;
    typeButton.titleLabel.font = [UIFont systemFontOfSize:18];
    [typeButton addTarget:self action:@selector(selectButton:) forControlEvents:UIControlEventTouchUpInside];
    [buttonView addSubview:typeButton];
    
    UIView * midLine = [[UIView alloc] init];
    midLine.frame = CGRectMake(SCREEN_WIDTH/2, 4, 1, 22);
    midLine.backgroundColor = COLOR(215, 215, 215, 1);
    [buttonView addSubview:midLine];
    
    UIView * bottomLine = [[UIView alloc] init];
    bottomLine.frame = CGRectMake(0, 29, SCREEN_WIDTH, 1);
    bottomLine.backgroundColor = midLine.backgroundColor;
    [buttonView addSubview:bottomLine];
    
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 120, SCREEN_WIDTH, SCREEN_HEIGHT - 120) style:UITableViewStylePlain];
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    _tableView.backgroundColor = COLOR(244, 244, 244, 1);
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:_tableView];
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    
    UIView * topLine = [[UIView alloc] init];
    topLine.frame = CGRectMake(0, 0, SCREEN_WIDTH, 1);
    topLine.backgroundColor = midLine.backgroundColor;
    _tableView.tableHeaderView = topLine;
}

#pragma self method
-(void)headViewTapRG
{
    NSLog(@"适配车型：XXXX");
}

-(void)selectButton:(UIButton *)sender
{
    if (sender.tag == 100) {
        NSLog(@"选择品牌");
    } else if (sender.tag == 101) {
        NSLog(@"选择类型");
    }
}



-(void)yuanchuan
{
 
    NSMutableDictionary* requestDict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    
    [HTTPconnect sendPOSTHttpWithUrl:getbrandAPI parameters:requestDict success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSLog(@"%@", responseObject);
//            success
        }else{
//        responseObject nssring
            NSLog(@"11111");
        }
    } failure:^(NSError *error) {
        
    }];
}


#pragma protocol
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 5;
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    OrderServerCell * cell = [tableView dequeueReusableCellWithIdentifier:@"myCell"];
    if (!cell)
    {
        [tableView registerNib:[UINib nibWithNibName:@"OrderServerCell" bundle:nil] forCellReuseIdentifier:@"myCell"];
        cell = [tableView dequeueReusableCellWithIdentifier:@"myCell"];
    }
    cell.backgroundColor = [UIColor clearColor];
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    return 120;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSLog(@"点击cell");
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
