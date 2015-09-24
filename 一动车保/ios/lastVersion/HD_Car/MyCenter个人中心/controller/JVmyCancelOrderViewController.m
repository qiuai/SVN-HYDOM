//
//  JVmyCancelOrderViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/11.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVmyCancelOrderViewController.h"
#import "orderListModel.h"
#import "JVorderDataModel.h"
#import "UIImageView+WebCache.h"
#import "orderTitleView.h"
#import "firstServicesTableViewCell.h"
#import "JVorderFooterView.h"
#import "JvOrderFooterStyleTwoView.h"
#import "JVwashCarOrderTableViewCell.h"
#import "JVservicesOrderTableViewCell.h"
#import "noGoodsListTableViewCell.h"
#import "JVcancelOrderStateView.h"
#import <MJRefresh.h>
#import "JVcommonOrderInfoViewController.h"
#import "JVwashCarOrderDisplayViewController.h"
#import "JVshowOnceProductViewController.h"

@interface JVmyCancelOrderViewController ()<UITableViewDataSource,UITableViewDelegate,UIAlertViewDelegate>{
    NSInteger footerTag;
    NSInteger cancelFooterIndex;
    NSInteger cancelFooterStyleClick;
    NSInteger httpDataStyle;
    NSInteger intPage;
    BOOL goods;
}

@property(nonatomic,strong)UITableView* tableView;

//加载中数据
@property(nonatomic,strong)NSMutableArray* dataSource;

@property(nonatomic,strong)UIAlertView* alert;

@property(nonatomic,assign)BOOL sign;

@property(nonatomic,strong)UIView* stateView;

@end

@implementation JVmyCancelOrderViewController


-(void)viewDidLoad{
    [super viewDidLoad];
    self.dataSource=[NSMutableArray array];
    self.tableView=[[UITableView alloc]initWithFrame:CGRM(0, 85, SCREEN_WIDTH, SCREEN_HEIGHT-85) style:UITableViewStyleGrouped];
    self.tableView.delegate=self;
    self.tableView.dataSource=self;
    self.tableView.backgroundColor=COLOR(236, 236, 236, 1.0);
    self.tableView.clipsToBounds=YES;
    [self.view addSubview:self.tableView];
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;

    typeof(self) __weak _self = self;
    self.tableView.header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{
        // 进入刷新状态后会自动调用这个block
        [_self initData:YES];
    }];

    self.tableView.footer = [MJRefreshAutoNormalFooter footerWithRefreshingBlock:^{
        [_self initData:NO];
    }];
    
    
    [self initNavViewWithtitle:@"取消的订单"];
    [self layoutStateUI];
    
    //    self.view.backgroundColor=randomColor;
}


#pragma -mark 订单状态  view
-(void)layoutStateUI{
    JVcancelOrderStateView* stateView=[[[NSBundle mainBundle]loadNibNamed:@"JVcancelOrderStateView" owner:nil options:nil]lastObject];
    [self.view addSubview:stateView];
    stateView.frame=CGRM(0, 64, SCREEN_WIDTH, 40);
    stateView.array=@[@"待审核",@"审核中",@"退款中",@"已完结"];
    self.stateView=stateView;
    stateView.viewController=self;
    stateView.tapMethod=@selector(changeData:);
    [self changeData:@1];
}


#pragma -mark 切换数据
-(void)changeData:(NSNumber*)index{
    httpDataStyle=[index integerValue];
    intPage=1;
    [self initData:YES];
}

#pragma -mark 请求数据
-(void)initData:(BOOL)removeObject{
    if(removeObject){
        intPage=1;
        [self.dataSource removeAllObjects];
    }else{
    intPage++;
    }
    NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [dict setValue:[NSNumber numberWithInteger:intPage] forKey:@"page"];
    [dict setValue:@"10" forKey:@"maxresult"];
    [dict setValue:[NSNumber numberWithInteger:httpDataStyle] forKey:@"type"];
    
//    _po([UtilityMethod JVDebugUrlWithdict:dict nsurl:getDidCancelOrderAPI]);
    
    [HTTPconnect sendPOSTHttpWithUrl:getDidCancelOrderAPI parameters:dict success:^(id responseObject) {
        
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSArray* dictArray=(NSArray*)responseObject[@"list"];
            if (dictArray.count==0&&removeObject==NO) {
                [self.tableView.footer noticeNoMoreData];
            }else{
                [self doingOptionData:dictArray];
                [self.tableView reloadData];
            }
            [self.tableView.header endRefreshing];
            [self.tableView.footer endRefreshing];
        }else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        warn(@"获取网络数据失败");
    }];
    
}
#pragma -mark 进行中数据处理
-(void)doingOptionData:(NSArray *)dictArray{
    for (NSDictionary * d in dictArray)
    {
        orderListModel* listModel=[[orderListModel alloc]init];
        listModel.oid=d[@"oid"];
        listModel.onum=d[@"onum"];
        listModel.otype=d[@"otype"];
        listModel.ostatus=d[@"ostatus"];
        listModel.oprice=d[@"oprice"];
        listModel.ocanop=d[@"ocanop"];
        listModel.ocontact=d[@"ocontact"];
        listModel.ophone=d[@"ophone"];
        if ([listModel.ocontact isEqualToString:@""]||[listModel.ocontact isEqualToString:@""]) {
            listModel.showContact=NO;
        }else{
            listModel.showContact=YES;
        }
        goods = NO;
        //处理cell 数据 array
        NSMutableArray* array=[NSMutableArray array];
        NSArray* valueArray=d[@"sclist"];
        for (NSDictionary* dict in valueArray) {
            NSString* scid=dict[@"scid"];
            NSString* scname=dict[@"scname"];
            NSNumber* scprice=dict[@"scprice"];
            NSArray* dArray=dict[@"plist"];
            NSString* scimg=dict[@"scimg"];
            if (dArray.count<1) {
                JVorderDataModel* model=[[JVorderDataModel alloc]init];
                model.scid=scid;
                model.scname=scname;
                model.scprice=scprice;
                model.scimg=scimg;
                model.cellStyle=3;//cell 类型 为没有商品cell
                [array addObject:model];
            }
            for (NSInteger i=0;i<dArray.count;i++) {
                goods = YES;
                JVorderDataModel* model=[[JVorderDataModel alloc]init];
                model.scid=scid;
                model.scname=scname;
                model.scprice=scprice;
                model.scimg=scimg;
                model.pid=dArray[i][@"pid"];
                model.pimg=dArray[i][@"pimg"];
                model.pname=dArray[i][@"pname"];
                model.premark=dArray[i][@"premark"];
                model.pprice=dArray[i][@"pprice"];
                model.pnum=dArray[i][@"pnum"];
                //判断cell 类型
                if (i==0&&[listModel.otype integerValue]==2) {
                    model.cellStyle=0;
                }else if(i!=0&&[listModel.otype integerValue]==2){
                    model.cellStyle=2;
                }if([listModel.otype integerValue]==3){
                    model.cellStyle=2;
                }else if([listModel.otype integerValue]==1){
                    model.cellStyle=1;
                }
                [array addObject:model];
            }
        }
        if (goods == YES) {
            listModel.goods = YES;
        }
        listModel.orderDataArray=array;
        [self.dataSource addObject:listModel];
    }
}
#pragma -mark 取出对应的 model
-(JVorderDataModel*)getModelWithIndexpath:(NSIndexPath*)indexpath{
    if (self.dataSource.count==0) {
        return nil;
    }
    orderListModel* listModel=self.dataSource[indexpath.section];
    JVorderDataModel* dataModel=listModel.orderDataArray[indexpath.row];
    dataModel.goods = listModel.goods;
    return dataModel;
}

#pragma -mark tableView delegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return self.dataSource.count;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    orderListModel* listModel=self.dataSource.count==0?nil:self.dataSource[section];
    _po(listModel.onum);
    return listModel.orderDataArray.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    JVorderDataModel* dataModel=[self getModelWithIndexpath:indexPath];
    NSInteger style=dataModel.cellStyle;
    switch (style) {
        case 0:
        {
            static NSString* str1=@"firstServicesTableViewCell";
            firstServicesTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:str1];
            if (cell==nil) {
                cell=[[[NSBundle mainBundle]loadNibNamed:@"firstServicesTableViewCell" owner:nil options:nil]lastObject];
                cell.selectionStyle=UITableViewCellSelectionStyleNone;
                
            }            
            [cell.icon sd_setImageWithURL:imageURLWithPath(dataModel.scimg) placeholderImage:[UIImage imageNamed:FillImage]];
            [cell.goodsImage sd_setImageWithURL:imageURLWithPath(dataModel.pimg) placeholderImage:[UIImage imageNamed:FillImage]];
            cell.serviceName.text=dataModel.scname;
            cell.servicePrices.text=[UtilityMethod addRMB:[NSString stringWithFormat:@"%@",globalPrices(dataModel.scprice)]];
            cell.goodsName.text=dataModel.pname;
            cell.goodsPrices.text=[UtilityMethod addRMB:[NSString stringWithFormat:@"%@",globalPrices(dataModel.pprice)]];
            cell.goodsCount.text=[NSString stringWithFormat:@"X %@",dataModel.pnum];
            [cell showEvaluate:NO];
            [cell hideEvaluate];
            return cell;
        }
            break;
        case 1:
        {
            static NSString* str1=@"JVwashCarOrderTableViewCell";
            JVwashCarOrderTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:str1];
            if (cell==nil) {
                cell=[[[NSBundle mainBundle]loadNibNamed:@"JVwashCarOrderTableViewCell" owner:nil options:nil]lastObject];
                
            }
            [cell.image sd_setImageWithURL:imageURLWithPath(dataModel.pimg) placeholderImage:[UIImage imageNamed:FillImage]];
            cell.washCar.text=dataModel.pname;
            cell.servicesDescription.text=dataModel.premark;
            [cell showEvaluate:NO];
            [cell hideEvaluate];
            return cell;
        }
            break;
        case 2:
        {
            static NSString* str1=@"JVservicesOrderTableViewCell";
            JVservicesOrderTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:str1];
            if (cell==nil) {
                cell=[[[NSBundle mainBundle]loadNibNamed:@"JVservicesOrderTableViewCell" owner:nil options:nil]lastObject];
                
            }
            [cell.image sd_setImageWithURL:imageURLWithPath(dataModel.pimg) placeholderImage:[UIImage imageNamed:FillImage]];
            cell.servicesTitle.text=dataModel.pname;
            cell.prices.text=[UtilityMethod addRMB:[NSString stringWithFormat:@"%@",dataModel.pprice]];
            cell.count.text= [NSString stringWithFormat:@"X %@",dataModel.pnum];
            [cell showEvaluate:NO];
            [cell hideEvaluate];
            return cell;
        }
            break;
        default:
        {
            static NSString* str1=@"noGoodsListTableViewCell";
            noGoodsListTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:str1];
            if (cell==nil) {
                cell=[[[NSBundle mainBundle]loadNibNamed:@"noGoodsListTableViewCell" owner:nil options:nil]lastObject];
            }
            if (dataModel.goods == NO) {
                cell.priceDescribe.hidden = YES;
                cell.prices.hidden = YES;
            }
            cell.selectionStyle = 0;
            [cell.icon sd_setImageWithURL:imageURLWithPath(dataModel.scimg) placeholderImage:[UIImage imageNamed:FillImage]];
            cell.servicesTitle.text=dataModel.scname;
            cell.prices.text=[UtilityMethod addRMB:globalPrices(dataModel.scprice)];
            [cell showEvaluate:NO];
            [cell hideEvaluate];
            return cell;
        }
            break;
            
    }
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    orderListModel* listModel=self.dataSource.count==0?nil:self.dataSource[indexPath.section];
    switch ([listModel.otype integerValue]) {
        case 1:
        {JVwashCarOrderDisplayViewController *vc=[[JVwashCarOrderDisplayViewController alloc]init];
            vc.orderID=listModel.oid;
            [self.navigationController pushViewController:vc animated:NO];
        }
            break;
        case 2:
        {JVcommonOrderInfoViewController *vc=[[JVcommonOrderInfoViewController alloc]init];
            vc.orderID=listModel.oid;
            vc.goods = listModel.goods;
            vc.price = [NSString stringWithFormat:@"￥ %@", globalPrices(listModel.oprice)];
            [self.navigationController pushViewController:vc animated:NO];
        }
            break;
        default:
        {JVshowOnceProductViewController *vc=[[JVshowOnceProductViewController alloc]init];
            vc.orderID=listModel.oid;
            [self.navigationController pushViewController:vc animated:NO];
        }
            break;
    }
    
    
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    JVorderDataModel* dataModel=[self getModelWithIndexpath:indexPath];
    return dataModel.cellHeight;
}



-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 45;
}

-(CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    orderListModel* listModel=self.dataSource.count==0?nil:self.dataSource[section];
    if (listModel.showContact) {
        return 80;
    }else{
        return 43;
    }
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    orderTitleView * headView = [[[NSBundle mainBundle]loadNibNamed:@"orderTitleView" owner:nil options:nil]lastObject];
    orderListModel* listModel=self.dataSource.count==0?nil:self.dataSource[section];
    headView.orderNumber.text=listModel.onum;
    headView.orderStats.text=listModel.ostatus;
    return headView;
}

-(UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    orderListModel* listModel=self.dataSource.count==0?nil:self.dataSource[section];
    footerTag=section+50;
    if (listModel.showContact) {
        JVorderFooterView*   footView = [[[NSBundle mainBundle]loadNibNamed:@"JVorderFooterView" owner:nil options:nil]lastObject];
        footView.prices.text= [NSString stringWithFormat:@"￥%@", globalPrices(listModel.oprice)];
        footView.tag=footerTag;
        return footView;
    }else{
        JvOrderFooterStyleTwoView* footView = [[[NSBundle mainBundle]loadNibNamed:@"JvOrderFooterStyleTwoView" owner:nil options:nil]lastObject];
        footView.prices.text= [NSString stringWithFormat:@"￥%@", globalPrices(listModel.oprice)];
        footView.viewStyle=[listModel.ocanop integerValue];
        footView.tag=footerTag;
        footView.viewController=self;
        footView.subClick=@selector(clickFooterView:);
        return footView;
    }
}


#pragma -mark footerViewclick
-(void)clickFooterView:(NSDictionary*)dict{
    if (self.alert==nil) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@""
                                                        message:@"确认取消订单吗"
                                                       delegate:self
                                              cancelButtonTitle:@"取消"
                                              otherButtonTitles:@"确认",nil];
        self.alert=alert;
    }
    NSInteger s=[dict[@"style"] integerValue];
    cancelFooterStyleClick=s;
    if (s==1) {
        self.alert.message=@"取消订单";
    }else{
        self.alert.message=@"确认收货";
    }
    [self.alert show];
    cancelFooterIndex=[dict[@"tag"] integerValue]-50;
}

#pragma -mark 点击确认 取消订单
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    
    if (buttonIndex!=0&&cancelFooterStyleClick==1) {
        orderListModel* listModel=self.dataSource[cancelFooterIndex];
        NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
        [dict setObject:listModel.oid forKey:@"oid"];
        [self.view addSubview:self.HUD];
        self.HUD.labelText =@"请稍后";
        self.HUD.mode = MBProgressHUDModeIndeterminate;
        _po([UtilityMethod JVDebugUrlWithdict:dict nsurl:cancelOrderAPI]);
        [HTTPconnect sendPOSTHttpWithUrl:cancelOrderAPI parameters:dict success:^(id responseObject) {
            if (![responseObject isKindOfClass:[NSString class]]) {
                warn(@"取消订单成功");
                [self initData:YES];
            }else{
                sleep(1);
                warn(responseObject);
            }
        } failure:^(NSError *error) {
            sleep(1);
            warn(@"请检查网络");
        }];
    }
    if (buttonIndex!=0&&cancelFooterStyleClick==2) {
        orderListModel* listModel=self.dataSource[cancelFooterIndex];
        NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
        [dict setObject:listModel.oid forKey:@"oid"];
        [self.view addSubview:self.HUD];
        self.HUD.labelText =@"请稍后";
        self.HUD.mode = MBProgressHUDModeIndeterminate;
        [HTTPconnect sendPOSTHttpWithUrl:confirmOrderAPI parameters:dict success:^(id responseObject) {
            if (![responseObject isKindOfClass:[NSString class]]) {
                warn(@"确认收货");
                [self initData:YES];
            }else{
                sleep(1);
                warn(responseObject);
            }
        } failure:^(NSError *error) {
            sleep(1);
            warn(@"请检查网络");
        }];
    }
}

-(void)dealloc{
    _po(@"dealloc order");
}
@end
