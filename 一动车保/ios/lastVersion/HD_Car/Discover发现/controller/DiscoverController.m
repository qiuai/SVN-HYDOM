//
//  DiscoverController.m
//  HD_InsuranceCar
//
//  Created by cc on 15/6/13.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "DiscoverController.h"
#import "HDNavigationView.h"
#import "HDApiUrl.h"
#import "HTTPconnect.h"
#import "DiscoverCell.h"
#import "discoverModel.h"
#import "UIImageView+WebCache.h"
#import <MJRefresh.h>
#import "CYTabbarContainer.h"
#import "JVshowScrollImageViewController.h"

@interface DiscoverController ()<UITableViewDataSource,UITableViewDelegate>{
    NSInteger index;
}
@property(nonatomic,strong)UITableView* discoverTableView;
@property(nonatomic, strong)NSMutableArray * dataArray;

/**父容器*/
@property(nonatomic,weak)CYTabbarContainer* parentContainer;
@end

@implementation DiscoverController



#pragma -mark 懒加载

-(CYTabbarContainer *)parentContainer{
    if (!_parentContainer) {
        _parentContainer=(CYTabbarContainer*)self.parentViewController.parentViewController;
    }
    return _parentContainer;
}

-(UITableView *)discoverTableView{
    if (!_discoverTableView) {
        _discoverTableView=[[UITableView alloc]initWithFrame:CGRectMake(0, 65, SCREEN_WIDTH, SCREEN_HEIGHT-64-49) style:UITableViewStylePlain];
        _discoverTableView.dataSource=self;
        _discoverTableView.delegate=self;
        _discoverTableView.separatorStyle=UITableViewCellSeparatorStyleNone;
        WEAKSELF;
        _discoverTableView.header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{
            //             进入刷新状态后会自动调用这个block
            [weakSelf getDataFromServer:NO];
        }];
        
        _discoverTableView.footer = [MJRefreshAutoNormalFooter footerWithRefreshingBlock:^{
            [weakSelf getDataFromServer:YES];
        }];

    }return _discoverTableView;
}

#pragma -mark 初始化视图布局
- (void)viewDidLoad {
    [super viewDidLoad];
    index=1;
    _dataArray = [NSMutableArray new];
    
    HDNavigationView* navView=[HDNavigationView navigationViewWithTitle:@"发现"];
    //添加导航
    [self.view addSubview:navView];
    
    [self.view addSubview:self.discoverTableView];
    //发送请求
    [self getDataFromServer:NO];
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self.parentContainer showTabbar];

}

#pragma -mark 请求数据
-(void)getDataFromServer:(BOOL)bl{
    if (bl==YES) {
        index++;
    }else{
        [_dataArray removeAllObjects];
        index = 1;
    }
    NSMutableDictionary * parameters = [NSMutableDictionary new];
    [parameters setObject:[NSNumber numberWithInteger:index] forKey:@"page"];
    [parameters setObject:@10 forKey:@"maxresult"];
    [HTTPconnect sendGETWithUrl:getExtraNewsAPI parameters:parameters success:^(id responseObject) {
        [_discoverTableView.header endRefreshing];
        [_discoverTableView.footer endRefreshing];
        if (![responseObject isKindOfClass:[NSString class]]) {
            for (NSDictionary * dic in responseObject[@"list"]) {
                discoverModel * model = [discoverModel new];
                [model setValuesForKeysWithDictionary:dic];
                [_dataArray addObject:model];
            }
            [_discoverTableView reloadData];
        }else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        [_discoverTableView.header endRefreshing];
        [_discoverTableView.footer endRefreshing];
        warn(@"网络错误");
    }];
    
}


#pragma -mark tableView协议
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [_dataArray count]<1?0:_dataArray.count;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    static NSString *identiFier=@"DiscoverCell";
    
    DiscoverCell* cell=(DiscoverCell*)[tableView dequeueReusableCellWithIdentifier:identiFier];
    if (cell==nil) {
        cell=[[[NSBundle mainBundle]loadNibNamed:@"DiscoverCell" owner:nil options:nil]lastObject];
    }
    
    if (_dataArray.count != 0) {
        discoverModel * model = _dataArray[indexPath.row];
        [cell.topImageVIew sd_setImageWithURL:imageURLWithPath(model.nwimage) placeholderImage:[UIImage imageNamed:FillImage]];
        cell.numberLabel.text =[NSString stringWithFormat:@"%@",model.nwstar];
        cell.timeLabel.text = model.nwdate;
        cell.titleLabel.text = model.nwtitle;
    }
    return cell;
}


//高度
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return SCREEN_WIDTH * 0.625;
}
//点击cell

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell* cell=[tableView cellForRowAtIndexPath:indexPath];
    cell.selectionStyle=UITableViewCellSelectionStyleNone;
    JVshowScrollImageViewController* vc=[[JVshowScrollImageViewController alloc]init];
    discoverModel * model = _dataArray[indexPath.row];
    vc.url=htmlWithPath(model.nwurl);
    [self.parentContainer hiddenTabbar];
    [self.navigationController pushViewController:vc animated:NO];
}




#pragma -mark 点击搜索 doing
-(void)clickSearchDone{


}

#pragma -mark 内存警告
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
