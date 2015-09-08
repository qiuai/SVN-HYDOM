//
//  YC_DiscountViewController.m
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_DiscountViewController.h"
#import "YC_ScoreViewController.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "UIImageView+WebCache.h"
#import "userDefaultManager.h"
#import "discountCell.h"
#import "discountModel.h"
@interface YC_DiscountViewController ()<UITableViewDataSource,UITableViewDelegate>
{
    NSInteger index;
}
@property(nonatomic, strong)NSDictionary * dataDic;  //请求数据
@property(nonatomic, strong)NSMutableArray * dataArray;
@property(nonatomic, strong)NSMutableArray * currentDataArray;
@property(nonatomic, strong)NSMutableArray * pastDataArray;
@property(nonatomic, strong)UIButton * selectButton;
@property(nonatomic, strong)UITableView * tableView;
@property(nonatomic, strong)discountModel * model;
@property(nonatomic, strong)UIButton * currentButton; //优惠券
@property(nonatomic, strong)UIButton * pastButton;    //过期优惠券

@end

@implementation YC_DiscountViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = HDfillColor;
    index = 1;
    
    [self initNavViewWithtitle:@"优惠券"];
    WEAKSELF;
    [self.navView tapRightViewWithImageName:@"我的积分" tapBlock:^{
        YC_ScoreViewController * vc = [[YC_ScoreViewController alloc] init];
        [weakSelf.navigationController pushViewController:vc animated:YES];
    }];
    [self initInterface];
}


-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self initData];
}


-(void)initData{
    NSMutableDictionary * parameters = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [parameters setObject:@"1" forKey:@"type"];
    [parameters setObject:[NSNumber numberWithInteger:index] forKey:@"page"];
    [parameters setObject:@50 forKey:@"maxresult"];
    [HTTPconnect sendGETWithUrl:getDiscountListAPI parameters:parameters success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            _currentDataArray = [NSMutableArray new];
            for (NSDictionary * dic in responseObject[@"list"]) {
                discountModel * model = [discountModel new];
                [model setValuesForKeysWithDictionary:dic];
                [_currentDataArray addObject:model];
            }
            _dataArray = _currentDataArray;
            [_tableView reloadData];
        }
    } failure:^(NSError *error){
        warn(@"网络错误");
    }];
    NSMutableDictionary * pastParameters = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [pastParameters setObject:@"0" forKey:@"type"];
    [pastParameters setObject:@1 forKey:@"page"];
    [pastParameters setObject:@50 forKey:@"maxresult"];
    
    
    _po([UtilityMethod JVDebugUrlWithdict:pastParameters nsurl:getDiscountListAPI]);
    [HTTPconnect sendGETWithUrl:getDiscountListAPI parameters:pastParameters success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            _pastDataArray = [NSMutableArray new];
            for (NSDictionary * dic in responseObject[@"list"]) {
                discountModel * model = [discountModel new];
                [model setValuesForKeysWithDictionary:dic];
                [_pastDataArray addObject:model];
            }
        }
    } failure:^(NSError *error) {
         warn(@"网络错误");
    }];

}
-(void)initInterface{
    _currentButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _currentButton.frame = CGRectMake(0, 64, SCREEN_WIDTH/2, 40);
    [_currentButton setTitle:@"我的优惠券" forState:UIControlStateNormal];
    [_currentButton setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    [_currentButton setTitleColor:[UIColor redColor] forState:UIControlStateSelected];
    _currentButton.titleLabel.font = [UIFont systemFontOfSize:14];
    _currentButton.backgroundColor = [UIColor whiteColor];
    [_currentButton addTarget:self action:@selector(pressSelectButton:) forControlEvents:UIControlEventTouchUpInside];
    //默认选择
    [self pressSelectButton:_currentButton];
    
    [self.view addSubview:_currentButton];
    _pastButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _pastButton.frame = CGRectMake(CGRectGetMaxX(_currentButton.frame), 64, SCREEN_WIDTH-CGRectGetMaxX(_currentButton.frame), 40);
    [_pastButton setTitle:@"过期优惠券" forState:UIControlStateNormal];
    [_pastButton setTitleColor:[UIColor lightGrayColor] forState:UIControlStateNormal];
    [_pastButton setTitleColor:[UIColor redColor] forState:UIControlStateSelected];
    _pastButton.titleLabel.font = [UIFont systemFontOfSize:14];
    _pastButton.backgroundColor = [UIColor whiteColor];
    [_pastButton addTarget:self action:@selector(pressSelectButton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_pastButton];
    
    UIView * midline = [[UIView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2, 69, 1, 30)];
    midline.backgroundColor = HDfillColor;
    [self.view addSubview:midline];
    
    UIView * bottomline = [[UIView alloc] initWithFrame:CGRectMake(0, 103, SCREEN_WIDTH, 1)];
    bottomline.backgroundColor = HDfillColor;
    [self.view addSubview:bottomline];
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 104, SCREEN_WIDTH, SCREEN_HEIGHT-104) style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.rowHeight = SCREEN_WIDTH*0.33+10;
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    _tableView.showsVerticalScrollIndicator = NO;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:_tableView];
    
}

#pragma mark -选择按钮
-(void)pressSelectButton:(UIButton *)sender
{
    if (_selectButton) {
        _selectButton.selected = NO;
        _selectButton.userInteractionEnabled = YES;
    }
    if (sender == _currentButton) {
        _dataArray = _currentDataArray;
    } else {
        _dataArray = _pastDataArray;
    }
    _selectButton = sender;
    _selectButton.selected = YES;
    _selectButton.userInteractionEnabled = NO;
    [_tableView reloadData];
}


#pragma mark -tableView协议
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_dataArray count];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString * cellID = @"baobao";
    discountCell * cell = [tableView dequeueReusableCellWithIdentifier:cellID];
    if (cell.cellPastImageView.image) {
        cell.cellPastImageView.image = nil;
    }
    if (!cell) {
        cell = [[discountCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellID];
    }
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if (_dataArray.count!= 0) {
        discountModel * model= _dataArray[indexPath.row];
        [cell.cellImageView sd_setImageWithURL:imageURLWithPath(model.cpimg)];
        if ([_pastButton isSelected]) {
            [cell.cellPastImageView setImage:[UIImage imageNamed:@"已过期"]];
        }
//        if ([_currentButton isSelected]) {
//            [cell.cellImageView sd_setImageWithURL:imageURLWithPath(model.cpimg)];
//        } else {
//            dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
//                NSData* data=[[NSData alloc]initWithContentsOfURL:imageURLWithPath(model.cpimg)];
//                UIImage *uslImage=[[UIImage alloc]initWithData:data];
//                UIImage* image=[self imageWithLogoImage:uslImage logo:[UIImage imageNamed:@"a123.jpg"]];
//                dispatch_async(dispatch_get_main_queue(), ^{
//                    [cell.cellImageView setImage:image];
//                });
//            });
//        }
    }
    return cell;
}


//#pragma mark - 加图片水印
//-(UIImage *)imageWithLogoImage:(UIImage *)img logo:(UIImage *)logo
//{
//    //get image width and height
//    int w = img.size.width;
//    int h = img.size.height;
//    int logoWidth = logo.size.width;
//    int logoHeight = logo.size.height;
//    CGColorSpaceRef colorSpace = CGColorSpaceCreateDeviceRGB();
//    //create a graphic context with CGBitmapContextCreate
//    CGContextRef context = CGBitmapContextCreate(NULL, w, h, 8, 4 * w, colorSpace, kCGImageAlphaPremultipliedFirst);
//    CGContextDrawImage(context, CGRectMake(0, 0, w, h), img.CGImage);
//    CGContextDrawImage(context, CGRectMake(0, h-5, 40, 40), [logo CGImage]);
//    CGImageRef imageMasked = CGBitmapContextCreateImage(context);
//    CGContextRelease(context);
//    CGColorSpaceRelease(colorSpace);
//    return [UIImage imageWithCGImage:imageMasked];
//    //  CGContextDrawImage(contextRef, CGRectMake(100, 50, 200, 80), [smallImg CGImage]);
//}

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
