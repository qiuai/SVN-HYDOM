//
//  CarsManagerViewController.m
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import "CarsManagerViewController.h"
#import "HDNavigationView.h"
#import "EdtingCarInforViewController.h"

@interface CarsManagerViewController ()
@property (nonatomic,strong)CarManagerView * carView;
@property (nonatomic,strong)CarManagerCellModel * model;
@property (nonatomic,strong)HDNavigationView * nav;

@end

@implementation CarsManagerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = COLOR(220, 220, 220, 1);
    _nav = [HDNavigationView navigationViewWithTitle:@"车管家"];
    [self.view addSubview:_nav];
    [_nav tapLeftViewWithImageName:@"comeBack.png" tapBlock:^{
        [self.navigationController popViewControllerAnimated:YES];
    }];
    [self setUpUserInterface];
    // Do any additional setup after loading the view.
}
- (void)initData{
    

}
- (void)setUpUserInterface{
    
    UIButton * button = [[UIButton alloc] initWithFrame:CGRM(10, 200, SCREEN_WIDTH-20, 40)];
    if (!_model) {
        _carView = [[[NSBundle mainBundle]loadNibNamed:@"CarManagerView" owner:self options:nil]lastObject];
        _carView.frame = CGRM(0, 74, SCREEN_WIDTH, 100);
//        [_carView setUpUserInterfaceWith:_model];
        [self.view addSubview:_carView];
        
        [button setTitle:@"编  辑" forState:0];
    }else{
        [button setTitle:@"添  加" forState:0];
    }
    [button setTitleColor:[UIColor whiteColor] forState:0];
    button.backgroundColor = ShareBackColor;
    button.titleLabel.font = [UIFont boldSystemFontOfSize:15];
    [button addTarget:self action:@selector(pressButton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    
}
-(void)pressButton:(UIButton *)sender{
    EdtingCarInforViewController * edtingVC = [[EdtingCarInforViewController alloc] init];
    if (!self.model) {
        _model = [[CarManagerCellModel alloc] init];
    }
    edtingVC.model = _model;
    [self.navigationController pushViewController:edtingVC animated:YES];
}
-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    if (self.model) {
        [_carView setUpUserInterfaceWith:_model];
    }
}


@end
