//
//  ChooseAreaViewController.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "ChooseAreaViewController.h"
#import "HTTPconnect.h"
#import "AddressModel.h"
#import "MJExtension.h"
#import "LeftTitleButton.h"
#import "EdtingAddressView.h"

@interface ChooseAreaViewController ()<UITableViewDataSource,UITableViewDelegate>
@property (strong, nonatomic)  UITableView *tableView;
@property (nonatomic,strong)NSArray * dataSource;
@property (nonatomic,strong)NSMutableArray * selectDataSource;
@property (nonatomic,strong)EdtingAddressView * edtingView;
@end

@implementation ChooseAreaViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initNavViewWithtitle:@"设置"];
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        [self.navigationController popViewControllerAnimated:YES];
    }];
    
    [self initdataTop];
    [self setUpUserInterface];
    // Do any additional setup after loading the view.
}
- (void)setUpUserInterface{
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT -64)];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:_tableView];


}
- (void)initdataTop{
    _dataSource = [NSMutableArray array];
    _selectDataSource = [NSMutableArray array];
//    MBProgressHUD * hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    [HTTPconnect sendPOSTHttpWithUrl:TopAreaListAPI parameters:nil success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            _dataSource = [AddressModel objectArrayWithKeyValuesArray:responseObject[@"list"]];
            [_tableView reloadData];
            
        }
        NSLog(@"%@",responseObject);
//        [hud hide:YES];
    } failure:^(NSError *error) {
        NSLog(@"%@",error);
//        [hud hide:YES];
    }];
}
- (void)initDataWith:(AddressModel *)model{
    NSDictionary * dic = @{@"aid":model.aid};
    MBProgressHUD * hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    [HTTPconnect sendPOSTHttpWithUrl:ChildAreaListAPI parameters:dic success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            BOOL issame = NO;
            NSMutableArray * array = [NSMutableArray array];
            for (AddressModel * objc in _selectDataSource) {
                if (issame == NO) {
                    [array addObject:objc];
                }
                if (objc == model) {
                    issame = YES;
                }
                
            }
            if (issame == NO) {
                [_selectDataSource addObject:model];
            }else{
                _selectDataSource = array;
            }
            
            if ([[AddressModel objectArrayWithKeyValuesArray:responseObject[@"list"]] count] == 0) {
                [self loadEdtingView];
            }else{
            _dataSource = [AddressModel objectArrayWithKeyValuesArray:responseObject[@"list"]];
            [_tableView reloadData];
            }
        }
        [hud hide:YES];
    } failure:^(NSError *error) {
        [hud hide:YES];
    }];
}

#pragma mark----tableView
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    return 30;
}
-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UIView * view = [[UIView alloc] init];
    view.backgroundColor = COLOR(220, 220, 220, 1);
    CGFloat height = 0;
    NSInteger tag =0;
    for (AddressModel * model in _selectDataSource) {
        LeftTitleButton * title = [[LeftTitleButton alloc] initWithFrame:CGRectMake(8, height, SCREEN_WIDTH-8, 30)];
        title.backgroundColor =[UIColor clearColor];
        title.titleLabel.font = [UIFont systemFontOfSize:14];
        title.titleLabel.textAlignment = NSTextAlignmentLeft;
        [title setTitleColor:[UIColor blackColor] forState:0];
        [title setTitle:model.name forState:0];
        title.tag = 70 + tag;
        [title addTarget:self action:@selector(clickTitleButton:) forControlEvents:UIControlEventTouchUpInside];
        [view addSubview:title];
        height = CGRectGetMaxY(title.frame);
        
        UILabel * lineLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, height, SCREEN_WIDTH, 1)];
        lineLabel.backgroundColor = [UIColor darkGrayColor];
        [view addSubview:lineLabel];
        height = CGRectGetMaxY(lineLabel.frame);
        tag ++;
    }
    view.frame = CGRectMake(0, 0, SCREEN_WIDTH, height);
    return view;
}
-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{

    return _selectDataSource.count * 31;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    return _dataSource.count;
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    
    return 1;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    static NSString *CellIdentifier = @"cell";
    UITableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    AddressModel * model = _dataSource[indexPath.row];
    cell.textLabel.text = model.name;
    cell.textLabel.font =[UIFont systemFontOfSize:15];
    return cell;
    
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    [self initDataWith:_dataSource[indexPath.row]];

}
- (void)clickTitleButton:(UIButton *)sender{
    if (sender.tag == 70) {
        [self initdataTop];
    }else{
    NSMutableArray * array = [NSMutableArray array];
    for (NSInteger tag = 70;tag < sender.tag;tag ++ ) {
        [array addObject:_selectDataSource[tag - 70]];
        
    }
    [self initDataWith:[array lastObject]];
    }
}
- (void)loadEdtingView{

    _edtingView = [[[NSBundle mainBundle]loadNibNamed:@"EdtingAddressView" owner:self options:nil]lastObject];
    _edtingView.hidden = NO;
    _edtingView.frame = CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64);
    [self.view addSubview:_edtingView];
    UITapGestureRecognizer * tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(clickAdrressLabel)];
    _edtingView.addressLabel.userInteractionEnabled = YES;
    [_edtingView.addressLabel addGestureRecognizer:tap];
    [_edtingView.saveButton addTarget:self action:@selector(clickSaveButton) forControlEvents:UIControlEventTouchUpInside];
    NSString * string = @"";
    for (AddressModel * model in _selectDataSource) {
        string = [NSString stringWithFormat:@"%@%@",string,model.name];
    }
    _edtingView.addressLabel.text = string;

}
-(void)clickAdrressLabel{
    [self initdataTop];
    _edtingView.hidden = YES;
    [_edtingView removeFromSuperview];
    _edtingView = nil;
}
- (void)clickSaveButton{
    AddressModel * model = [_selectDataSource lastObject];
    if ([self.delegate respondsToSelector:@selector(sendAddressWith:Id:detailsString:)]) {
        [self.delegate sendAddressWith:_edtingView.addressLabel.text Id:model.aid detailsString:_edtingView.addressTextField.text];
    }
    [self.navigationController popViewControllerAnimated:YES];

}

-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}
@end
