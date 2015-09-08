//
//  YC_SelectServerViewController.m
//  HD_Car
//
//  Created by hydom on 7/30/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_SelectServerViewController.h"
#import "HDNavigationView.h"
#import "YC_SelectServerCell.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "UIImageView+WebCache.h"
#import "JVCommonSelectCarView.h"
#import "YC_AddGoodsViewController.h"
#import "carListViewController.h"
#import "JVservicesOrderViewController.h"
#import "JVcommodityInfoViewController.h"

@interface YC_SelectServerViewController ()<UITableViewDelegate,UITableViewDataSource>


@property(nonatomic, weak)NSMutableArray * dataSource;
//商品 和服务
@property(nonatomic, strong)NSMutableArray * dataSourceOne;
//服务
@property(nonatomic, strong)NSMutableArray * dataSourceStyleTwo;

@property(nonatomic, strong)UITableView * tableView;
@property(nonatomic, strong)UIButton * selectButton;
@property(nonatomic, strong)UIScrollView * selectView;  //自备配件页
@property(nonatomic, strong)YC_SelectServerCell * deleteCell; //获取删除cell
@property(nonatomic, strong)UIView * showView; //删除弹框


@property(nonatomic,strong)JVCommonSelectCarView* topView;

@end

@implementation YC_SelectServerViewController



-(NSMutableArray *)dataSource{
    if (!_dataSource) {
        _dataSource=[NSMutableArray array];
    }
    return _dataSource;
}

-(NSMutableArray *)dataSourceOne{
    if (!_dataSourceOne) {
        _dataSourceOne=[NSMutableArray array];
    }
    return _dataSourceOne;
}

-(NSMutableArray *)dataSourceStyleTwo{
    if (!_dataSourceStyleTwo) {
        _dataSourceStyleTwo=[NSMutableArray array];
    }
    return _dataSourceStyleTwo;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.view.backgroundColor = COLOR(236, 236, 236, 1);
    for (NSDictionary * dic in self.selectArray) {
        NSMutableDictionary * newDic = [NSMutableDictionary dictionaryWithCapacity:1];
        [newDic setObject:dic[@"scname"] forKey:@"scname"];
        [newDic setObject:dic[@"scprice"] forKey:@"scprice"];
        [newDic setObject:dic[@"scimage"] forKey:@"scimage"];
        [newDic setObject:dic[@"scid"] forKey:@"scid"];
        NSMutableArray * array = [[NSMutableArray alloc] initWithCapacity:1];
        [newDic setObject:array forKey:@"array"];
        NSMutableDictionary* dictcopy=[newDic mutableCopy];
        [dictcopy removeObjectForKey:@"array"];
        [self.dataSourceOne addObject:newDic];
        [self.dataSourceStyleTwo addObject:dictcopy];
    }
    [self initNavViewWithtitle:@"选择服务"];
    [self LayoutUI];
  
    [self initData];
}


#pragma -mark 初始化数据
-(void)initData{
    NSMutableDictionary* requestDict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    NSMutableString * scid;
    if (_selectArray.count == 1) {
        scid = _selectArray[0][@"scid"];
    } else if (_selectArray.count > 1) {
        for (NSDictionary * dic in _selectArray) {
            if ([dic isEqualToDictionary:_selectArray[0]]) {
                scid = dic[@"scid"];
            } else {
                scid = [NSMutableString stringWithFormat:@"%@#%@", scid,dic[@"scid"]];
            }
        }
    }
    NSString * cmid;
    cmid = self.carModel.cmid;
    [requestDict setObject:scid forKey:@"scid"];
    [requestDict setObject:cmid forKey:@"cmid"];
    [HTTPconnect sendGETWithUrl:getSelectOrderAPI parameters:requestDict success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            self.dataSource=self.dataSourceOne;
            for (NSDictionary * dic in [responseObject objectForKey:@"list"]){
                NSString* scid= [dic objectForKey:@"scid"];
                for (int i=0; i< self.dataSource.count; i++) {
                    NSString* current=[self.dataSource[i] objectForKey:@"scid"];
                    if ([scid isEqualToString:current]) {
                        YC_GoodsModel* model=[YC_GoodsModel goodsModelWithDictionary:dic];
                        [[self.dataSource[i] objectForKey:@"array"] addObject:model];
                    }
                }
            }
            [self.tableView reloadData];
        }else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        warn(@"网络错误");
    }];
}


#pragma -mark 初始化界面
-(void)LayoutUI
{
    //适配车型
    JVCommonSelectCarView * topView = [[NSBundle mainBundle]loadNibNamed:@"JVCommonSelectCarView" owner:nil options:nil].lastObject;
    topView.frame = CGRectMake(0, 64, SCREEN_WIDTH, 36);
    
    topView.carNameLabel.text=[NSString stringWithFormat:@"%@",_carModel.csname];
    UITapGestureRecognizer * topViewTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(topViewTapGR:)];
    [topView addGestureRecognizer:topViewTapGR];
    [self.view addSubview:topView];
    UIView * topViewLine = [[UIView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(topView.frame)-1, SCREEN_WIDTH, 1)];
    topViewLine.backgroundColor = COLOR(220, 220, 220, 1);
    [topView addSubview:topViewLine];
    
    
    
    //商品服务列表
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 100, SCREEN_WIDTH, SCREEN_HEIGHT-100-90) style:UITableViewStylePlain];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.showsVerticalScrollIndicator = NO;
    _tableView.backgroundColor = [UIColor clearColor];
    [self.view addSubview:_tableView];
    
    //底部视图
    UIView * footView = [[UIView alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT - 50, SCREEN_WIDTH, 50)];
    footView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:footView];
    
    UIView * topLine = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 1)];
    topLine.backgroundColor = COLOR(220, 220, 220, 1);
    [footView addSubview:topLine];
    
    UIButton * cancelButton = [UIButton buttonWithType:UIButtonTypeCustom];
    cancelButton.frame = CGRectMake(SCREEN_WIDTH - 180, 10, 70, 30);
    cancelButton.layer.cornerRadius = 5;
    cancelButton.layer.borderWidth = 1;
    cancelButton.layer.borderColor = [UIColor lightGrayColor].CGColor;
    [cancelButton setTitle:@"取消" forState:UIControlStateNormal];
    [cancelButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [cancelButton addTarget:self action:@selector(footButton:) forControlEvents:UIControlEventTouchUpInside];
    [footView addSubview:cancelButton];
    UIButton * submitButton = [UIButton buttonWithType:UIButtonTypeCustom];
    submitButton.frame = CGRectMake(SCREEN_WIDTH - 90, 10, 70, 30);
    submitButton.layer.cornerRadius = 5;
    submitButton.layer.borderWidth = 1;
    submitButton.layer.borderColor = [UIColor lightGrayColor].CGColor;
    submitButton.backgroundColor = COLOR(190, 0, 19, 1);
    [submitButton setTitle:@"确定" forState:UIControlStateNormal];
    [submitButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [submitButton addTarget:self action:@selector(footButton:) forControlEvents:UIControlEventTouchUpInside];
    [footView addSubview:submitButton];
    
    //自备配件栏
    UIView * selectView = [[UIView alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT - 85, SCREEN_WIDTH, 30)];
    selectView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:selectView];
    
    UIView * SelectTopLine = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 1)];
    SelectTopLine.backgroundColor = COLOR(220, 220, 220, 1);
    [selectView addSubview:SelectTopLine];
    
    UIView * SelectBottomLine = [[UIView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(selectView.frame)-1, SCREEN_WIDTH, 1)];
    SelectBottomLine.backgroundColor = COLOR(220, 220, 220, 1);
    [selectView addSubview:SelectBottomLine];
    
    UILabel * selectLabel = [[UILabel alloc] initWithFrame:CGRectMake(15, 0, 120, 30)];
    selectLabel.text = @"自备配件";
    selectLabel.font = [UIFont systemFontOfSize:17];
    [selectView addSubview:selectLabel];
    
    UITapGestureRecognizer * tapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(selectViewTAP:)];
    [selectView addGestureRecognizer:tapGR];
    
    _selectButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _selectButton.frame = CGRectMake(SCREEN_WIDTH - 45, 4, 22, 22);
    [_selectButton setImage:[UIImage imageNamed:@"redSingle2"] forState:UIControlStateNormal];
    [_selectButton setImage:[UIImage imageNamed:@"redSingle1"] forState:UIControlStateSelected];
    [_selectButton addTarget:self action:@selector(selectButton:) forControlEvents:UIControlEventTouchUpInside];
    [selectView addSubview:_selectButton];
    [self.view addSubview:selectView];
    [self InitUseUserPast];
    
}


#pragma -mark 自备配件view
-(void)InitUseUserPast
{
    
    _selectView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 115, SCREEN_WIDTH, SCREEN_HEIGHT - 165)];
    _selectView.backgroundColor = COLOR(236, 236, 236, 1);
    _selectView.contentSize=CGSizeMake(SCREEN_WIDTH, SCREEN_HEIGHT);
    _selectView.hidden = YES;
    [self.view addSubview:_selectView];
    
    for (int i = 0; i < _dataSourceStyleTwo.count; i ++) {
        UIView * view = [[UIView alloc] initWithFrame:CGRectMake(0, i*45, SCREEN_WIDTH, 45)];
        view.backgroundColor = [UIColor whiteColor];
        [_selectView addSubview:view];
        
        UIImageView * imageView = [[UIImageView alloc] initWithFrame:CGRectMake(20, 10, 25, 25)];
        [imageView sd_setImageWithURL:imageURLWithPath(_dataSourceStyleTwo[i][@"scimage"])];
        [view addSubview:imageView];
        
        UILabel * titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(55, 10, 100, 25)];
        titleLabel.text = _dataSourceStyleTwo[i][@"scname"];
        titleLabel.font = [UIFont systemFontOfSize:17];
        titleLabel.textColor = COLOR(110, 110, 110, 1);
        [view addSubview:titleLabel];
        
        UILabel * rightLabel = [[UILabel alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-120, 10, 60, 25)];
        rightLabel.text = @"服务费:";
        rightLabel.font = [UIFont systemFontOfSize:15];
        rightLabel.textAlignment = NSTextAlignmentRight;
        [view addSubview:rightLabel];
        
        UILabel * priceLabel = [[UILabel alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-60, 10, 60, 25)];
        priceLabel.textColor = [UIColor redColor];

        priceLabel.text = [NSString stringWithFormat:@"￥%@",globalPrices(_dataSourceStyleTwo[i][@"scprice"])];

        priceLabel.font = [UIFont systemFontOfSize:17];
        [view addSubview:priceLabel];
        
        UIView * line = [[UIView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(view.frame)-1, SCREEN_WIDTH, 1)];
        line.backgroundColor = COLOR(220, 220, 220, 1);
        [view addSubview:line];
    }
    
    UIView * selectNewView = [[UIView alloc] initWithFrame:CGRectMake(0, _dataSourceStyleTwo.count*45+20 , SCREEN_WIDTH, 40)];
    selectNewView.backgroundColor = [UIColor whiteColor];
    [_selectView addSubview:selectNewView];
    _selectView.contentSize = CGSizeMake(SCREEN_WIDTH, CGRectGetMaxY(selectNewView.frame));
    
    UIView * selectNewViewTopLine = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 1)];
    selectNewViewTopLine.backgroundColor = COLOR(220, 220, 220, 1);
    [selectNewView addSubview:selectNewViewTopLine];
    
    UIView * selectNewViewBottomLine = [[UIView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(selectNewView.frame)-1, SCREEN_WIDTH, 1)];
    selectNewViewBottomLine.backgroundColor = COLOR(220, 220, 220, 1);
    [selectNewView addSubview:selectNewViewBottomLine];
    
    UILabel * selectNewLabel = [[UILabel alloc] initWithFrame:CGRectMake(15, 0, 120, 40)];
    selectNewLabel.text = @"自备配件";
    selectNewLabel.font = [UIFont systemFontOfSize:17];
    [selectNewView addSubview:selectNewLabel];
    
    UITapGestureRecognizer * NewTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(selectViewTAP:)];
    [selectNewView addGestureRecognizer:NewTapGR];
    
    UIButton * selectNewButton = [UIButton buttonWithType:UIButtonTypeCustom];
    selectNewButton.frame = CGRectMake(SCREEN_WIDTH - 50, 8, 24, 24);
    [selectNewButton setImage:[UIImage imageNamed:@"redSingle1"] forState:UIControlStateNormal];
    selectNewButton.userInteractionEnabled = NO;
    [selectNewView addSubview:selectNewButton];
}

#pragma -mark cell  加  减事件
-(void)sumMethod:(UITableViewCell*)cell{
    
    NSIndexPath* indexPath=[self.tableView indexPathForCell:cell];
    YC_GoodsModel* model=(YC_GoodsModel*)[_dataSource[indexPath.section] objectForKey:@"array"][indexPath.row];
    NSInteger it=model.myCount+1;
    model.myCount=it;
}
-(void)subtractMethod:(UITableViewCell*)cell{
    //    NSIndexPath* indexPath=[self.tableView indexPathForCell:cell];
    //    NSMutableDictionary* dict=[_dataSource[indexPath.section] objectForKey:@"array"][indexPath.row];
    //    NSNumber* number=dict[@"pbuynum"];
    //    NSInteger i=[number integerValue]-1;
    //    [dict setObject:[NSNumber numberWithInteger:i] forKey:@"pbuynum"];
    NSIndexPath* indexPath=[self.tableView indexPathForCell:cell];
    YC_GoodsModel* model=(YC_GoodsModel*)[_dataSource[indexPath.section] objectForKey:@"array"][indexPath.row];
    NSInteger it=model.myCount-1;
    model.myCount=it;
    
}

#pragma mark tableView
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[_dataSource[section] objectForKey:@"array"] count];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    YC_SelectServerCell * cell = [tableView dequeueReusableCellWithIdentifier:@"YC_SelectServerCell"];
    if (!cell)
    {
        cell = [[[NSBundle mainBundle]loadNibNamed:@"YC_SelectServerCell" owner:nil options:nil]lastObject];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.backgroundColor = COLOR(239, 245, 259, 1);
        cell.viewController=self;
        cell.subtractMethod=@selector(subtractMethod:);
        cell.sumMethod=@selector(sumMethod:);
    }
    cell.dataSource = [_dataSource[indexPath.section] objectForKey:@"array"][indexPath.row];
    [cell.deleteButton addTarget:self action:@selector(cellDeleteButton:) forControlEvents:UIControlEventTouchUpInside];
    [cell setCellDataSource];
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    YC_GoodsModel* goods = [_dataSource[indexPath.section] objectForKey:@"array"][indexPath.row];
    JVcommodityInfoViewController* infoVC=[[JVcommodityInfoViewController alloc]init];
    infoVC.productID=goods.pid;
    [self.navigationController pushViewController:infoVC animated:NO];
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 90;
}


-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return _dataSource.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 50;
}

-(CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return 30;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView * headView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 50)];
    headView.backgroundColor = COLOR(236, 236, 236, 1);
    
    UIView * sectionView = [[UIView alloc] initWithFrame:CGRectMake(0, 15, SCREEN_WIDTH, 35)];
    sectionView.backgroundColor = [UIColor whiteColor];
    [headView addSubview:sectionView];
    
    UIImageView * logolView = [[UIImageView alloc] initWithFrame:CGRectMake(15, 5, 25, 25)];
    [logolView sd_setImageWithURL:imageURLWithPath(_dataSource[section][@"scimage"])];
    [sectionView addSubview:logolView];
    
    UILabel * titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(55, 5, 100, 25)];
    titleLabel.text = _dataSource[section][@"scname"];
    titleLabel.font = [UIFont systemFontOfSize:17];
    titleLabel.textColor = COLOR(110, 110, 110, 1);
    [sectionView addSubview:titleLabel];
    
    UIButton * addButton = [UIButton buttonWithType:UIButtonTypeCustom];
    addButton.frame = CGRectMake(SCREEN_WIDTH - 60, 2, 50, 30);
    addButton.titleLabel.font = [UIFont systemFontOfSize:17];
    [addButton setTitle:@"新增" forState:UIControlStateNormal];
    addButton.tag = section + 100;
    [addButton setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
    [addButton addTarget:self action:@selector(addButton:) forControlEvents:UIControlEventTouchUpInside];
    [sectionView addSubview:addButton];
    
    UIView * topLine = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 1)];
    topLine.backgroundColor = COLOR(220, 220, 220, 1);
    [sectionView addSubview:topLine];
    
    UIView * bottomLine = [[UIView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(sectionView.frame)-1, SCREEN_WIDTH, 1)];
    bottomLine.backgroundColor = topLine.backgroundColor;
    [sectionView addSubview:bottomLine];
    
    //    UITapGestureRecognizer * tapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(headViewTapGR:)];
    //    [sectionView addGestureRecognizer:tapGR];
    return headView;
}

-(UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView * footView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 30)];
    footView.backgroundColor = [UIColor whiteColor];
    
    UILabel * label = [[UILabel alloc] initWithFrame:CGRectMake(15, 0, 150, 30)];
    label.text = [NSString stringWithFormat:@"服务费：￥%@",globalPrices(_dataSource[section][@"scprice"])];
    label.font = [UIFont systemFontOfSize:15];
    [footView addSubview:label];
    
    UIView * topLine = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 1)];
    topLine.backgroundColor = COLOR(220, 220, 220, 1);
    [footView addSubview:topLine];
    
    UIView * bottomLine = [[UIView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(footView.frame)-1, SCREEN_WIDTH, 1)];
    bottomLine.backgroundColor = topLine.backgroundColor;
    [footView addSubview:bottomLine];
    
    return footView;
}

#pragma mark 新增
-(void)addButton:(UIButton *)sender
{
    YC_AddGoodsViewController * vc = [[YC_AddGoodsViewController alloc] init];
    vc.cmid = self.carModel.cmid;
    vc.scid = [self.dataSource[sender.tag - 100] objectForKey:@"scid"];
    NSLog(@"%@", self.dataSource[sender.tag -100]);
    NSArray * oldArray = self.dataSource[sender.tag-100][@"array"];
    NSMutableString * pids = [NSMutableString new];
    if (oldArray.count > 0) {
        if (oldArray.count == 1) {
            pids = [NSMutableString stringWithString:[oldArray[0] valueForKey:@"pid"]];
        } else {
            for (YC_GoodsModel * goodsModel in oldArray) {
                [pids appendFormat:@"%@#",goodsModel.pid];
            }
            [pids deleteCharactersInRange:NSMakeRange(pids.length-1, 1)];
    }
        vc.pids = pids;
    }
    [self.navigationController pushViewController:vc animated:YES];
    vc.addGooldsBlock=^(YC_GoodsModel* model){
        NSInteger index=sender.tag-100;
        NSMutableArray* array=self.dataSource[index][@"array"];
        [array addObject:model];
        [self.tableView reloadData];
    };
}

-(void)cellDeleteButton:(UIButton *)sender
{
    if (!_showView) {
        _showView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
        _showView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.6];
        _showView.userInteractionEnabled = YES;
        [self.view addSubview:_showView];
        
        UIView * alertView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 220, 160)];
        alertView.backgroundColor = [UIColor whiteColor];
        alertView.center = _showView.center;
        alertView.layer.cornerRadius = 5;
        alertView.userInteractionEnabled = YES;
        alertView.alpha = 1;
        [_showView addSubview:alertView];
        
        UILabel * label = [[UILabel alloc] initWithFrame:CGRectMake(0, 30, CGRectGetWidth(alertView.frame), 40)];
        label.text = @"确认删除该商品";
        label.textAlignment = NSTextAlignmentCenter;
        [alertView addSubview:label];
        
        UIView * line = [[UIView alloc] initWithFrame:CGRectMake(0, 100, CGRectGetWidth(alertView.frame), 1)];
        line.backgroundColor = [UIColor lightGrayColor];
        [alertView addSubview:line];
        
        UIButton * cancelButton = [UIButton buttonWithType:UIButtonTypeCustom];
        cancelButton.frame = CGRectMake(20, 115, 70, 30);
        [cancelButton setTitle:@"取消" forState:UIControlStateNormal];
        [cancelButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        cancelButton.layer.borderWidth = 1;
        cancelButton.layer.borderColor = [UIColor lightGrayColor].CGColor;
        cancelButton.layer.cornerRadius = 3;
        [cancelButton addTarget:self action:@selector(alertViewButton:) forControlEvents:UIControlEventTouchUpInside];
        [alertView addSubview:cancelButton];
        
        UIButton * confirmButton = [UIButton buttonWithType:UIButtonTypeCustom];
        confirmButton.frame = CGRectMake(130, 115, 70, 30);
        [confirmButton setTitle:@"确定" forState:UIControlStateNormal];
        [confirmButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [confirmButton setBackgroundColor:COLOR(190, 0, 19, 1)];
        confirmButton.layer.cornerRadius = 3;
        [confirmButton addTarget:self action:@selector(alertViewButton:) forControlEvents:UIControlEventTouchUpInside];
        [alertView addSubview:confirmButton];
        
        //        _deleteCell = (YC_SelectServerCell *)sender.superview.superview;
        _deleteCell=[UtilityMethod getTableViewCell:sender class:[YC_SelectServerCell class]];
    }
    [_showView setHidden:NO];
}

-(void)alertViewButton:(UIButton *)sender
{
    if ([sender.titleLabel.text isEqualToString:@"取消"]) {
        [_showView setHidden:YES];
    } else if ([sender.titleLabel.text isEqualToString:@"确定"]) {
        [_showView setHidden:YES];
        NSIndexPath * indexPath=[self.tableView indexPathForCell:_deleteCell];
        [_dataSource[indexPath.section][@"array"] removeObjectAtIndex:indexPath.row];
        [_tableView reloadData];
    }
}



-(void)selectButton:(UIButton *)sender
{
    sender.selected = !sender.selected;
    if (sender.isSelected) {
        _tableView.hidden = YES;
        self.dataSource=self.dataSourceStyleTwo;
        _selectView.hidden = NO;
    } else {
        _tableView.hidden = NO;
        self.dataSource=self.dataSourceOne;
        _selectView.hidden = YES;
    }
    
}

#pragma -mark 跳转提交订单
-(void)footButton:(UIButton *)sender
{
    if ([sender.titleLabel.text isEqualToString:@"取消"]) {
        [self.navigationController popViewControllerAnimated:YES];
    } else if ([sender.titleLabel.text isEqualToString:@"确定"]) {
        JVservicesOrderViewController *vc=[[JVservicesOrderViewController alloc]init];
        vc.dataArray=self.dataSource;
        _po(self.dataSource);
        vc.selectCarmodel=self.carModel;
        [self.navigationController pushViewController:vc animated:NO];
    }
}
//
//-(void)headViewTapGR:(UITapGestureRecognizer *)sender
//{
//    _po(@"点击sectionView");
//}
#pragma -mark 跳转车管家
-(void)topViewTapGR:(UITapGestureRecognizer *)sender
{
    carListViewController* carVC=[[carListViewController alloc]init];
    WEAKSELF;
    carVC.defaultCarModel=^(carModel* m){
        weakSelf.topView.carNameLabel.text=[NSString stringWithFormat:@"%@",_carModel.csname];
        self.carModel=m;
        [self initData];
    };
    [self.navigationController pushViewController:carVC animated:NO];
}

-(void)selectViewTAP:(UITapGestureRecognizer *)sender
{
    [self selectButton:_selectButton];
}

-(void)dealloc
{
    NSLog(@"dealloc");
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
