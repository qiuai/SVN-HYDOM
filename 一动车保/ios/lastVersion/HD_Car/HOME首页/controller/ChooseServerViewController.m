//
//  ChooseServerViewController.m
//  HD_Car
//
//  Created by hydom on 15-7-30.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//
#define Button_tag 30

#import "ChooseServerViewController.h"
#import "HDNavigationView.h"
#import <UIImageView+WebCache.h>
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "YC_SelectServerViewController.h"
#import "MBProgressHUD.h"
#import "JVCommonSelectCarView.h"
#import "carListViewController.h"
@interface ChooseServerViewController ()

@property (nonatomic,strong)NSMutableArray * dataSource;
@property (nonatomic,strong)NSMutableArray * selectArray;
@property(nonatomic,weak)UIView* navView;


@property(nonatomic,weak)JVCommonSelectCarView* topView;

@end

@implementation ChooseServerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    _selectArray = [[NSMutableArray alloc] init];
    HDNavigationView * navView = [HDNavigationView navigationViewWithTitle:@"选择服务"];
    WEAKSELF;
    [navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self.view addSubview:navView];
    self.navView=navView;
    [HTTPconnect sendPOSTHttpWithUrl:getAllServicesAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        //        _po(responseObject);
        if (![responseObject isKindOfClass:[NSString class]]) {
            self.dataSource=[NSMutableArray arrayWithArray:[responseObject objectForKey:@"list"]];
            [self setUpUserInterface];
        }
        
        
    } failure:^(NSError *error) {
        
    }];
    
    [self setUpUserInterface];
}
- (void)setUpUserInterface{
    
    //适配车型
    JVCommonSelectCarView * topView = [[NSBundle mainBundle]loadNibNamed:@"JVCommonSelectCarView" owner:nil options:nil].lastObject;
    topView.frame = CGRectMake(0, 64, SCREEN_WIDTH, 36);
    self.topView=topView;
    self.topView.carNameLabel.text=[NSString stringWithFormat:@"%@",_carModel.csname];
    [topView.tapChangeCarView addTapGestureRecognizerWithTarget:self action:@selector(topViewTapGR:)];
    [self.view addSubview:topView];
    UIView * topViewLine = [[UIView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(topView.frame)-1, SCREEN_WIDTH, 1)];
    topViewLine.backgroundColor = COLOR(220, 220, 220, 1);
    [topView addSubview:topViewLine];
    
    
    UIButton * button = [[UIButton alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-66, 20, 66, 44)];
    [button setTitle:@"确认" forState:0];
    [button setTitleColor:[UIColor whiteColor] forState:0];
    [button addTarget:self action:@selector(pressSureButton:) forControlEvents:UIControlEventTouchUpInside];
    button.titleLabel.font = [UIFont systemFontOfSize:15];
    [self.navView addSubview:button];
    
    CGFloat heigth = 104;
    NSInteger tag = Button_tag;
#warning 根据数据修改
    
    for (NSDictionary * dic in _dataSource) {
        heigth = [self buildViewWith:[dic objectForKey:@"scimage"] title:[dic objectForKey:@"scname"] minY:heigth buttontag:tag];
        tag ++;
    }
    
}

/**
 *  创建View
 *
 *  @param imageUrl 图片地址
 *  @param title    标题
 *  @param minY     view位置
 *
 *  @return view的maxY
 */
- (CGFloat)buildViewWith:(NSString *)imageUrl title:(NSString *)title minY:(CGFloat)minY buttontag:(NSInteger)buttontag{
    
    UIView * view                 = [[UIView alloc] initWithFrame:CGRectMake(0, minY, SCREEN_WIDTH, 41)];
    UIImageView * imageView       = [[UIImageView alloc] initWithFrame:CGRectMake(10, 5, 30, 30)];
    imageView.layer.cornerRadius  = 15;
    imageView.layer.masksToBounds = YES;
    [imageView sd_setImageWithURL:imageURLWithPath(imageUrl) placeholderImage:nil];
    [view addSubview:imageView];
    
    UILabel * label = [[UILabel alloc] initWithFrame:CGRectMake(45, 5, 150, 30)];
    label.text      = title;
    label.font      = [UIFont systemFontOfSize:13];
    [view addSubview:label];
    
    UIButton * button = [[UIButton alloc] initWithFrame:CGRectMake(SCREEN_WIDTH - 50, 0, 40, 40)];
    [button setImage:[UIImage imageNamed:@"redSingle2"] forState:0];
    [button setImage:[UIImage imageNamed:@"redSingle1"] forState:UIControlStateSelected];
    [button addTarget:self action:@selector(pressSelectButtons:) forControlEvents:UIControlEventTouchUpInside];
    button.tag = buttontag;
    [view addSubview:button];
    
    UILabel * linlabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 40, SCREEN_WIDTH, 1)];
    linlabel.backgroundColor = [UIColor lightGrayColor];
    [view addSubview:linlabel];
    [self.view addSubview:view];
    
    return CGRectGetMaxY(view.frame);
}

-(void)pressSelectButtons:(UIButton *)sender{
    sender.selected = !sender.selected;
    
    
}
-(void)pressSureButton:(UIButton *)sender{
    
    [_selectArray removeAllObjects];
    
    for (int i = 30 ; i < 30 +_dataSource.count; i ++) {
        UIButton * button = (UIButton *)[self.view viewWithTag:i];
        if (button.selected == YES) {
            [_selectArray addObject:_dataSource[button.tag - 30]];
        }
    }
    if (_selectArray.count==0) {
        
        
        
    }else{
        YC_SelectServerViewController* vc=[[YC_SelectServerViewController alloc]init];
        vc.carModel = self.carModel;
        vc.selectArray = _selectArray;
        [self.navigationController pushViewController:vc animated:NO];
    }
    
    
}

-(void)topViewTapGR:(UIGestureRecognizer *)sender
{
    
    carListViewController* vc=[[carListViewController alloc]init];
    vc.defaultCarModel=^(carModel* m){
        self.topView.carNameLabel.text=[NSString stringWithFormat:@"%@ %@",m.cbname,m.cmname];
    };
    [self.navigationController pushViewController:vc animated:NO];
}



@end
