//
//  selectCarController.m
//  HD_Car
//
//  Created by xingso on 15/6/29.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "selectCarController.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "MBProgressHUD.h"
#import "carModel.h"
#import "userDefaultManager.h"
#import "HDApiUrl.h"
#import "selectCarSeriesViewController.h"
#import "UIImageView+WebCache.h"

@interface selectCarController ()<UITableViewDataSource,UITableViewDelegate,UISearchBarDelegate>
@property(nonatomic,strong)UITableView* brandTableView;
@property(nonatomic,strong)MBProgressHUD *HUD;
@property(nonatomic,strong)NSMutableArray* carArray;
/**车品牌索引*/
@property(nonatomic,strong)NSMutableArray* carIndexArray;
/**汽车数据*/
@property(nonatomic,strong)NSMutableArray* cardataByOpeation;


/**搜索汽车数据*/
@property(nonatomic,strong)NSMutableArray* searchCardataByOpeation;



@property(nonatomic,strong)UISearchBar* searchBar;


@property(nonatomic,assign)BOOL displayStyle;//yes 搜索之前  no 搜索之后

@end

@implementation selectCarController
#pragma -mark 懒加载
-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view];
    }
    return _HUD;
}
-(NSMutableArray *)carArray{
    if (!_carArray) {
        _carArray=[NSMutableArray array];
    }
    return _carArray;
}




-(NSMutableArray *)searchCardataByOpeation{
    if (!_searchCardataByOpeation) {
        _searchCardataByOpeation=[NSMutableArray array];
    }
    return _searchCardataByOpeation;
}


-(NSMutableArray *)cardataByOpeation{
    if (!_cardataByOpeation) {
        _cardataByOpeation=[NSMutableArray array];
    }
    return _cardataByOpeation;
}
-(NSMutableArray *)carIndexArray{
    if (!_carIndexArray) {
        _carIndexArray=[NSMutableArray array];
    }
    return _carIndexArray;
}


#pragma -mark Load
- (void)viewDidLoad {    
    [super viewDidLoad];
    UISearchBar *mySearchBar = [[UISearchBar alloc]
                                initWithFrame:CGRectMake(0,64+20,SCREEN_WIDTH, 45)];
    mySearchBar.delegate = self;
    mySearchBar.showsCancelButton = NO;
    mySearchBar.barStyle=UIBarStyleDefault;
    mySearchBar.placeholder=@"请输入汽车品牌";
    
    [self.view addSubview:mySearchBar];
    self.searchBar=mySearchBar;
    [self initTitleView];
    [self brandCarViewLayout];
    [self sendHttp];
    self.view.backgroundColor=HDfillColor;
}



-(void)initTitleView{
    HDNavigationView* titleView=[HDNavigationView navigationViewWithTitle:@"车型选择"];
    WEAKSELF;
    [titleView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:NO];
    }];
    [self.view addSubview:titleView];
}



//汽车品牌view
-(void)brandCarViewLayout{
    
    self.brandTableView=[[UITableView alloc]initWithFrame:CGRectMake(0,CGRectGetMaxY(self.searchBar.frame)+20, SCREEN_WIDTH, SCREEN_HEIGHT-CGRectGetMaxY(self.searchBar.frame)-20) style:UITableViewStyleGrouped];
    self.brandTableView.delegate=self;
    self.brandTableView.dataSource=self;
    [self.view addSubview:self.brandTableView];
}


#pragma -mark http
-(void)sendHttp{
    NSDictionary* requestDict=[userDefaultManager getUserWithToken];
    [HTTPconnect sendPOSTHttpWithUrl:getCarBrandAPI parameters:requestDict success:^(id responseObject) {
             if (![responseObject isKindOfClass:[NSString class]]) {
                 [self opeationCarData:responseObject];
                 self.displayStyle=YES;
                 [self.brandTableView reloadData];
             }else{
                 [self.view addSubview:self.HUD];
                 self.HUD.labelText = responseObject;
                 self.HUD.mode=MBProgressHUDModeText;
                 [self.HUD showAnimated:YES whileExecutingBlock:^{
                     sleep(2); // 睡眠3秒
                 } completionBlock:^{
                     [self.HUD removeFromSuperview];
                 }
                  ];
             }
    } failure:^(NSError *error) {
                [self.view addSubview:self.HUD];
                self.HUD.labelText = @"网络异常，请检查网络!!";
                self.HUD.mode=MBProgressHUDModeText;
                [self.HUD showAnimated:YES whileExecutingBlock:^{
                    sleep(2); // 睡眠3秒
                } completionBlock:^{
                    [self.HUD removeFromSuperview];
                }
        ];
    }];
}

//处理 汽车数据
-(void)opeationCarData:(NSDictionary*)responseObject{
    NSArray* car=[responseObject objectForKey:@"list"];
    for (NSDictionary* dict in car) {
        carModel* model=[carModel carModelWithDictionary:dict];
        [self.carArray addObject:model];
        if(![self.carIndexArray containsObject:model.fletter]){
            [self.carIndexArray addObject:model.fletter];
        }
    }
   
    //创建字典
    for (NSString* str in self.carIndexArray){
        NSMutableDictionary* dictionary=[NSMutableDictionary dictionary];
        [dictionary setObject:str forKey:@"index"];
        NSMutableArray* list=[NSMutableArray array];
        [dictionary setObject:list forKey:@"list"];
        [self.cardataByOpeation addObject:dictionary];
    }
    //数据添加到字典里
    for(NSMutableDictionary* muDict in self.cardataByOpeation){
        for (carModel* ml in self.carArray) {
            if ([ml.fletter isEqualToString:[muDict objectForKey:@"index"]]) {
                NSMutableArray* array=[muDict objectForKey:@"list"];
                [array addObject:ml];
            }
        }
    }
    
}

#pragma  -mark 辅助方法 获取model
-(carModel*)getModelWithCarDataOPeation:(NSIndexPath*)indexPath{
    NSDictionary* d=self.cardataByOpeation[indexPath.section];
    NSArray* array=[d objectForKey:@"list"];
    return  array[indexPath.row];
}

#pragma -mark tableViewDelegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    if (_displayStyle==YES) {
    NSDictionary* d=self.cardataByOpeation[section];
    NSArray* array=[d objectForKey:@"list"];
    return  array.count;
    }
    else{
        return 1;
    }

}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* idf=@"CarBrandCell";
    UITableViewCell* cell=[tableView dequeueReusableCellWithIdentifier:idf];
    if (!cell) {
        cell=[[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:idf];
    }
    carModel* model=self.displayStyle==NO?self.searchCardataByOpeation[indexPath.row]:[self getModelWithCarDataOPeation:indexPath];
    cell.textLabel.text=model.cbname;
    NSURL* url=imageURLWithPath(model.cbimageURL);
    [cell.imageView sd_setImageWithURL:url placeholderImage:[UIImage imageNamed:FillImage]];
    return cell;
}

//多少组
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return _displayStyle==YES?self.cardataByOpeation.count:self.searchCardataByOpeation.count;
}
//索引数组
-(NSArray *)sectionIndexTitlesForTableView:(UITableView *)tableView{
    return  _displayStyle==YES?self.carIndexArray:nil;
}

//分组标题
- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
        return  _displayStyle==YES?self.carIndexArray[section]:nil;
}

//分组 标题高度
- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 30.0;
}
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 1.0;
}



//点击
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell* cell=[tableView cellForRowAtIndexPath:indexPath];
    cell.selectionStyle=UITableViewCellSelectionStyleNone;
    carModel* model=[self getModelWithCarDataOPeation:indexPath];
    selectCarSeriesViewController* vc=[[selectCarSeriesViewController alloc]init];
    vc.selctCar=model;
    [self.navigationController pushViewController:vc animated:NO];
}





#pragma -mark -searchbar
- (void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText;
{
    if (searchText.length>0) {
        [self.searchCardataByOpeation removeAllObjects];
        for (carModel* ml in self.carArray) {
            //找到合适的品牌名称时候
            
            if ([ml.cbname rangeOfString:searchText options:NSCaseInsensitiveSearch].location==NSNotFound) {
            }else {
                [self.searchCardataByOpeation addObject:ml];
            }
        }
        self.displayStyle=NO;
    }else{
        self.displayStyle=YES;
    }
    [self.brandTableView reloadData];
}

-(void) searchBarSearchButtonClicked:(UISearchBar *)searchBar {
    [self searchBar:self.searchBar textDidChange:nil];
    [_searchBar resignFirstResponder];
}

- (void)searchBarCancelButtonClicked:(UISearchBar *) searchBar
{
    [self searchBar:self.searchBar textDidChange:nil];
    [_searchBar resignFirstResponder];
}
@end
