//
//  HomeViewController.m
//  HD_InsuranceCar
//
//  Created by cc on 15/6/13.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "HomeViewController.h"
#import "loginViewController.h"
#import "HDNavigationView.h"
#import "CYTabbarContainer.h"
#import "CYScollViewContainer.h"
#import "HomeServicesView.h"
#import "characteristicMarketView.h"
#import "selectCarController.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "UIImage+MJ.h"
#import "carOderViewController.h"
#import "userDefaultManager.h"
#import "AFNetworking.h"
#import "MBProgressHUD.h"
#import "carModel.h"
#import "UIImageView+WebCache.h"
#import "signValueObject.h"
#import "globalServicesModel.h"
#import "ChooseServerViewController.h"
#import "YC_HotSaleViewController.h"
#import "YC_HotGoodsViewController.h"
#import "YC_SelectServerViewController.h"
#import "JVshowScrollImageViewController.h"
#import <MJRefresh.h>
@interface HomeViewController (){
    CGFloat mainScrollViewDisplayH;
    CGFloat weatherViewHeight;
}
@property(nonatomic,strong)UIScrollView* homeScrollView;

@property(nonatomic,strong)MBProgressHUD *HUD;
/**父容器*/
@property(nonatomic,weak)CYTabbarContainer* parentContainer;
/**weatherView*/
@property(nonatomic,strong)UIView* weatherInfoView;
/**天气背景*/
@property(nonatomic,weak)UIImageView* weatherBackgroundView;
@property(nonatomic,strong)UILabel* carInfoforWeather;
//天气图标
@property(nonatomic,strong)UIImageView* weatherIcon;

@property(nonatomic,strong)UILabel* weatherInfoLabel;

/**天气以下模块*/

@property(nonatomic,strong)UIView* weatherBottomMainView;

/**滚动视图模块*/
@property(nonatomic,weak)UIView* scollImageView;

/**滚动vc*/
@property(nonatomic,weak)CYScollViewContainer* scollImageVC;

//服务new  View
@property(nonatomic,strong)HomeServicesView* servicesView;

//特色市场以及热门分类view
@property(nonatomic,strong)characteristicMarketView* marketView;

/**servicesDataArray*/
@property(nonatomic,strong)NSMutableArray* homeServiceArray;


/**adlistDataArray*/
@property(nonatomic,strong)NSMutableArray* homeAdlistArray;

/**redHotArray*/
@property(nonatomic,strong)NSMutableArray* redHotArray;

/**blistArray*/
@property(nonatomic,strong)NSMutableArray* blistArray;

@end

@implementation HomeViewController
#pragma -mark 懒加载

-(NSMutableArray *)redHotArray{
    if (!_redHotArray) {
        _redHotArray=[NSMutableArray array];
    }
    return _redHotArray;
}

-(NSMutableArray *)blistArray{
    if (!_blistArray) {
        _blistArray=[NSMutableArray array];
    }
    return _blistArray;
}

-(NSMutableArray *)homeAdlistArray{
    if (!_homeAdlistArray) {
        _homeAdlistArray=[NSMutableArray array];
    }
    return _homeAdlistArray;
}

-(NSMutableArray *)homeServiceArray{
    if (!_homeServiceArray) {
        _homeServiceArray=[NSMutableArray array];
    }
    return _homeServiceArray;
}

-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view];
    }
    return _HUD;
}

-(UIView *)weatherBottomMainView{
    
    if (!_weatherBottomMainView) {
        _weatherBottomMainView=[[UIView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(self.weatherInfoView.frame), SCREEN_WIDTH, mainScrollViewDisplayH-weatherViewHeight)];
    }
    return _weatherBottomMainView;
}

-(CYTabbarContainer *)parentContainer{
    if (!_parentContainer) {
        _parentContainer=(CYTabbarContainer*)self.parentViewController.parentViewController;
    }
    return _parentContainer;
}

-(UIScrollView *)homeScrollView{
    if (!_homeScrollView) {
        _homeScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64-49)];
        _homeScrollView.backgroundColor=HDfillColor;
        mainScrollViewDisplayH=0.6*SCREEN_WIDTH+320+365+60+30;
        _homeScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, mainScrollViewDisplayH);
        WEAKSELF;
        _homeScrollView.header = [MJRefreshNormalHeader headerWithRefreshingBlock:^{
            //             进入刷新状态后会自动调用这个block
            [weakSelf sendHTTP];
        }];
        
    }
    return _homeScrollView;
}


#pragma --mark 加载视图  布局。。
- (void)viewDidLoad {
    [super viewDidLoad];
    [self LayoutUI];
    [self sendHTTP];
    
}
#pragma -mark 请求数据

-(void)sendHTTP{
    //    1.天气
    NSMutableDictionary* dictionary=[NSMutableDictionary dictionary];
    [dictionary setObject:@"贵阳" forKey:@"location"];
    [dictionary setObject:@"json" forKey:@"output"];
    [dictionary setObject:weatherKEY forKey:@"ak"];
    [HTTPconnect getHttpWithUrl:weatherAPI parameters:dictionary success:^(id responseObject) {
        NSDictionary* data=(NSDictionary*)responseObject;
        NSInteger status=[[data objectForKey:@"error"] integerValue];
        if (status==0){
            NSArray* da=[data objectForKey:@"results"];
            NSArray* index=[da[0] objectForKey:@"index"];
            id weatherData=[da[0] objectForKey:@"weather_data"][0];
            NSString* carStr=[index[1] objectForKey:@"des"];
            NSString* d=[weatherData objectForKey:@"date"];
            NSString* nowDate=[d componentsSeparatedByString:@" "][0];
            NSString* weather=[NSString stringWithFormat:@"%@    %@|%@ ",[weatherData objectForKey:@"weather"],[weatherData objectForKey:@"temperature"],nowDate];
            NSString* image=[weatherData objectForKey:@"dayPictureUrl"];
            //处理天气图标
            NSRange range = [image rangeOfString:@"(\\w)+(.png){1}" options:NSRegularExpressionSearch];
            if (range.location != NSNotFound) {
                //天气图标
                self.weatherIcon.image=[UIImage imageNamed:[image substringWithRange:range]];
            }
            NSArray* array=[carStr componentsSeparatedByString:@"，"];
            //        取前两个逗号
            self.carInfoforWeather.text=[NSString stringWithFormat:@"%@，%@",array[0],array[1]];
            self.weatherInfoLabel.text=weather;
        }
    } failure:^(NSError *error) {
        _po(@"失败！！");
    }];
    
    
    
    
    
    [HTTPconnect sendPOSTHttpWithUrl:homeGetServicesAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        [_homeScrollView.header endRefreshing];
        if (![responseObject isKindOfClass:[NSString class]]) {
            self.homeServiceArray=[responseObject objectForKey:@"sclist"];
//            self.homeServiceArray=[responseObject objectForKey:@"list"];
            // 单例 保存 服务model
            signValueObject* signValue=[signValueObject shareSignValue];
            [signValue.servicesModelArray removeAllObjects];
            for (NSDictionary* dic in self.homeServiceArray) {
                globalServicesModel* model=[globalServicesModel globalServicesModelWithDictionary:dic];
                [signValue.servicesModelArray addObject:model];
            }if (self.homeServiceArray.count>0) {
                NSURL* url1=[self imageNameStringToURL:0];
                [self.servicesView.fisrtServicesIcon sd_setImageWithURL:url1 placeholderImage:self.servicesView.fisrtServicesIcon.image];
                [self.servicesView.fisrtServicesTitle setText:[self.homeServiceArray[0]objectForKey:@"scname"] ];
                self.servicesView.fisrtServiceInfoLabel.text=[self.homeServiceArray[0] objectForKey:@"scremark"];
                NSURL* url2= [self imageNameStringToURL:1];
                [self.servicesView.secondServiceIcon sd_setImageWithURL:url2 placeholderImage:self.servicesView.secondServiceIcon.image];
                [self.servicesView.secondServiceTitle setText:[self.homeServiceArray[1]objectForKey:@"scname"] ];
                self.servicesView.secondServiceInfoLabel.text=[self.homeServiceArray[1] objectForKey:@"scremark"];
                NSURL* url3= [self imageNameStringToURL:2];
                [self.servicesView.thirdServiceIcon sd_setImageWithURL:url3 placeholderImage:self.servicesView.thirdServiceIcon.image];
                [self.servicesView.thirdServiceTitle setText:[self.homeServiceArray[2]objectForKey:@"scname"] ];
                NSURL* url4= [self imageNameStringToURL:3];
                [self.servicesView.fourthServiceIcon sd_setImageWithURL:url4 placeholderImage:self.servicesView.fourthServiceIcon.image];
                [self.servicesView.fourthServiceTitle setText:[self.homeServiceArray[3]objectForKey:@"scname"] ];
                NSURL* url5= [self imageNameStringToURL:4];
                [self.servicesView.fifthServiceIcon sd_setImageWithURL:url5 placeholderImage:self.servicesView.fifthServiceIcon.image];
                [self.servicesView.fifthServiceTItle setText:[self.homeServiceArray[4]objectForKey:@"scname"] ];
                NSURL* url6=[self imageNameStringToURL:5];
                [self.servicesView.sixthServiceIcon sd_setImageWithURL:url6 placeholderImage:self.servicesView.sixthServiceIcon.image];
                [self.servicesView.sixthServiceTitle setText:[self.homeServiceArray[5]objectForKey:@"scname"] ];
                NSURL* url7=[self imageNameStringToURL:6];
                [self.servicesView.seventhServiceIcon sd_setImageWithURL:url7 placeholderImage:self.servicesView.seventhServiceIcon.image];
                [self.servicesView.seventhServiceTitle setText:[self.homeServiceArray[6]objectForKey:@"scname"] ];
                NSURL* url8= [self imageNameStringToURL:7];
                [self.servicesView.eighthServiceIcon sd_setImageWithURL:url8 placeholderImage:self.servicesView.eighthServiceIcon.image];
                [self.servicesView.eighthServiceTitle setText:[self.homeServiceArray[7]objectForKey:@"scname"] ];
                NSURL* url9= [self imageNameStringToURL:8];
                [self.servicesView.ninthServiceIcon sd_setImageWithURL:url9 placeholderImage:self.servicesView.ninthServiceIcon.image];
                [self.servicesView.ninthServiceTitle setText:[self.homeServiceArray[8]objectForKey:@"scname"] ];
                NSURL* url10=[self imageNameStringToURL:9];
                [self.servicesView.tenthServiceIcon sd_setImageWithURL:url10 placeholderImage:self.servicesView.tenthServiceIcon.image];
                [self.servicesView.tenthServiceTitle setText:[self.homeServiceArray[9]objectForKey:@"scname"] ];
            }
            //广告相关
            self.homeAdlistArray=responseObject[@"adlist"];
            if (self.homeAdlistArray.count>0) {
                NSMutableArray* mArray=[NSMutableArray array];
                for (NSDictionary* dic in self.homeAdlistArray) {
                    [mArray addObject:imageURLWithPath(dic[@"adimg"])];
                }
                [self.scollImageVC setImageArray:mArray];
            }
            //品牌推荐
            self.blistArray =responseObject[@"blist"];
            self.marketView.blistArray = self.blistArray;
            
            //热门分类
            self.redHotArray=responseObject[@"pclist"];
            self.marketView.redHotArray = self.redHotArray;
            
            [self.marketView layoutUI];
        }
        else{
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
        [_homeScrollView.header endRefreshing];
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"请检查网络";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠3秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
        }
         ];
    }];
    
    
}

//拼接imageURL
-(NSURL*)imageNameStringToURL:(NSInteger)index{
    NSString* str1=[self.homeServiceArray[index] objectForKey:@"scimage"];
    NSURL* url=imageURLWithPath(str1);
    return url;
}

#pragma -mark  Layout UI
-(void)LayoutUI{
    
    HDNavigationView* navView=[HDNavigationView navigationViewWithTitle:@"一动车保"];
    //添加导航
    [self.view addSubview:navView];
    //    主要的ScrollView
    [self.view addSubview:self.homeScrollView];
    //天气模块
    [self LayoutWeatherView];
    //天气之后的模块
    [self.homeScrollView addSubview:self.weatherBottomMainView];
    //轮播模块
    [self scollDispalyImage];
    //服务模块
    [self servicesViewLayout];
    //    商品模块
    [self commodityViewLayout];
}


#pragma -mark 重新展示tabbar
-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self.parentContainer showTabbar];
}

#pragma -mark 天气模块
-(void)LayoutWeatherView{
    //天气背景
    weatherViewHeight=50;
    self.weatherInfoView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, weatherViewHeight)];
    [self.homeScrollView addSubview:self.weatherInfoView];
    self.weatherInfoView.backgroundColor=COLOR(0, 182, 233, 1);
    //天气右边图标
    UIImageView* weatherRightView=[[UIImageView alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-42, (weatherViewHeight-32)/2.0, 32, 32)];
    weatherRightView.image=[UIImage imageNamed:@"quxiao_cha"];
    [weatherRightView addTapGestureRecognizerWithTarget:self action:@selector(closeWeather)];
    [self.weatherInfoView addSubview:weatherRightView];
    //天气文字
    self.weatherInfoLabel=[[UILabel alloc]initWithFrame:CGRectMake(70, 5, 170, 15)];
    self.weatherInfoLabel.text=@"error";
    self.weatherInfoLabel.textColor=WHITECOLOR;
    self.weatherInfoLabel.font=FONT12;
    [self.weatherInfoView addSubview:self.weatherInfoLabel];
    //给爱车洗澡
    self.carInfoforWeather=[[UILabel alloc]initWithFrame:CGRectMake(65,CGRectGetMaxY(self.weatherInfoLabel.frame)+5, 180, 20)];
    self.carInfoforWeather.text=@"error";
    self.carInfoforWeather.textColor=WHITECOLOR;
    self.carInfoforWeather.font=FONT12;
    [self.weatherInfoView addSubview:self.carInfoforWeather];
    //图片
    self.weatherIcon=[[UIImageView alloc]initWithFrame:CGRectMake(15, 5, 40, 40)];
    [self.weatherInfoView addSubview:self.weatherIcon];
}
//关闭天气
-(void)closeWeather{
    
    [self.weatherInfoView removeFromSuperview];
    CGRect wframe=self.weatherBottomMainView.frame;
    [UIView animateWithDuration:0.25 animations:^{
        [self.weatherBottomMainView setFrame:CGRectMake(0, 0, wframe.size.width, wframe.size.height)];
    }];
    self.homeScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, self.homeScrollView.contentSize.height-weatherViewHeight);
}
#pragma -mark 滚动视图模块
-(void)scollDispalyImage{
    CYScollViewContainer* cyVC=[[CYScollViewContainer alloc]init];
    cyVC.view.frame=CGRectMake(0, 0, SCREEN_WIDTH, 0.45*SCREEN_WIDTH);
    [cyVC setImageArray:@[@"HomeTESTyouhuijuan"]];
    [self addChildViewController:cyVC];
    [self.weatherBottomMainView addSubview:cyVC.view];
    self.scollImageView=cyVC.view;
    WEAKSELF;
    cyVC.clickBlock=^(NSInteger i){
        //点击事件
        [weakSelf tapScrollImageView:i];
    };
    self.scollImageVC=cyVC;
}



#pragma -mark 新版 服务模块
-(void)servicesViewLayout{
    
    self.servicesView=[[[NSBundle mainBundle]loadNibNamed:@"HomeServicesView" owner:nil options:nil] lastObject];
    self.servicesView.frame=CGRectMake(0, CGRectGetMaxY(self.scollImageView.frame), SCREEN_WIDTH, 320.0);
    [self.weatherBottomMainView addSubview:self.servicesView];
    self.servicesView.tapServicesViewWithtag=@selector(tapServicesViewWithtag:);
    self.servicesView.viewController=self;
    [self.servicesView.washCarView addTapGestureRecognizerWithTarget:self action:@selector(washCarServTap)];
    [self.servicesView.goHomeServicesView addTapGestureRecognizerWithTarget:self action:@selector(gohomeTap)];
}
#pragma -mark 点击其他服务  单服务
-(void)tapServicesViewWithtag:(NSNumber*)number{
    //判断登陆
    if ([userDefaultManager getUserWithToken]==nil) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"请登陆";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
            loginViewController* loginVC=[[loginViewController alloc]init];
            [self presentViewController:loginVC animated:YES completion:nil];
        }
         ];
        return;
    }
    
    if(self.homeServiceArray.count<1) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"下拉刷新或检查网络";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
        }
         ];
        return;
    }
    
    //用户选择的服务类型
    signValueObject* signValue=[signValueObject shareSignValue];
    
    NSInteger seInt=[number integerValue]+1;
    [signValue setSelectGlobalServicesModel:signValue.servicesModelArray[seInt]];
    //获取用户车型
    
    [HTTPconnect sendPOSTHttpWithUrl:userCarModelAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSMutableDictionary* redict=[(NSDictionary*)responseObject mutableCopy];
            [redict removeObjectForKey:@"result"];
            //            _po(redict);
            carModel* responseModel=[carModel carModelWithDictionary:redict];
            globalServicesModel* servicesMOdel=[signValue getSelectGlobalServiceModel];
            YC_SelectServerViewController* serviceVC=[[YC_SelectServerViewController alloc]init];
            serviceVC.carModel = responseModel;
            serviceVC.selectArray = [NSArray arrayWithObject:[UtilityMethod getObjectData:servicesMOdel]];
            [self.navigationController pushViewController:serviceVC animated:NO];
            [self.parentContainer hiddenTabbar];
        }
        else{
            //    选车页面
            selectCarController* selectCarVC=[[selectCarController alloc]init];
            //设置默认车型
            signValueObject* sign=[signValueObject shareSignValue];
            [sign SetDefaultCar:YES];
            [self.parentContainer hiddenTabbar];
            [self.navigationController pushViewController:selectCarVC animated:NO];
        }
    } failure:^(NSError *error) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"网络异常";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];}
         ];
    }];
    
    
}
#pragma -mark 轮播 图 跳转
-(void)tapScrollImageView:(NSInteger)index{
    if (self.homeAdlistArray.count>index) {
        JVshowScrollImageViewController* vc=[[JVshowScrollImageViewController alloc]init];
        vc.url=htmlWithPath(self.homeAdlistArray[index][@"adurl"]);
        [_parentContainer hiddenTabbar];
        [self.navigationController pushViewController:vc animated:NO];
    }
}
#pragma -mark 热门分类跳转
-(void)goHotVC:(NSString*)str{
    
    
    
    //判断登陆
    if ([userDefaultManager getUserWithToken]==nil) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"请登陆";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
            loginViewController* loginVC=[[loginViewController alloc]init];
            [self presentViewController:loginVC animated:YES completion:nil];
        }
         ];
        return;
    }
    
    if(self.redHotArray.count<1) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"下拉刷新或检查网络";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
        }
         ];
        return;
    }
    
    //获取用户车型
    
    [HTTPconnect sendPOSTHttpWithUrl:userCarModelAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSMutableDictionary* redict=[(NSDictionary*)responseObject mutableCopy];
            [redict removeObjectForKey:@"result"];
            NSInteger c=[str integerValue];
           
            carModel* responseModel=[carModel carModelWithDictionary:redict];
            if (c==1) {
                YC_HotSaleViewController * vc = [[YC_HotSaleViewController alloc] init];
                vc.car=responseModel;
                [self.navigationController pushViewController:vc animated:NO];
            }else{
                YC_HotGoodsViewController * vc =[[YC_HotGoodsViewController alloc] init];
                vc.car=responseModel;
                switch (c) {
                    case 2:{
                        if (_redHotArray.count<1) {
                            warn(@"请后台录入信息");
                            return ;
                        }
                        vc.pcid = _redHotArray[0][@"pcid"];
                        vc.pname = _redHotArray[0][@"pcname"];
                    }
                        break;
                    case 3:{
                        if (_redHotArray.count<2) {
                            warn(@"请后台录入信息");
                            return ;
                        }
                        vc.pcid = _redHotArray[1][@"pcid"];
                        vc.pname = _redHotArray[1][@"pcname"];
                    }
                        break;
                    case 4:{
                        if (_redHotArray.count<3) {
                            warn(@"请后台录入信息");
                            return ;
                        }
                        vc.pcid = _redHotArray[2][@"pcid"];
                        vc.pname = _redHotArray[2][@"pcname"];
                    }
                        break;
                    default:{
                        if (_redHotArray.count<4) {
                            warn(@"请后台录入信息");
                            return ;
                        }
                        vc.pcid = _redHotArray[3][@"pcid"];
                        vc.pname = _redHotArray[3][@"pcname"];
                    }
                        break;
                }
                [self.navigationController pushViewController:vc animated:NO];
            }
        }
        else{
            // 单例保存 从热门分类跳转
            signValueObject* sign=[signValueObject shareSignValue];
            [sign.saveDictionary setValue:str forKey:@"pushHotVC"];
            [sign.saveDictionary setValue:_redHotArray forKey:@"pushHotVCData"];
            //    选车页面
            selectCarController* selectCarVC=[[selectCarController alloc]init];
            //设置默认车型
            [sign SetDefaultCar:YES];
            [self.parentContainer hiddenTabbar];
            [self.navigationController pushViewController:selectCarVC animated:NO];
        }
    } failure:^(NSError *error) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"网络异常";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];}
         ];
    }];
}

#pragma -mark 商品服务跳转
-(void)gohomeTap{
    //判断登陆
    if ([userDefaultManager getUserWithToken]==nil) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"请登陆";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
            loginViewController* loginVC=[[loginViewController alloc]init];
            [self presentViewController:loginVC animated:YES completion:nil];
            loginVC.successLogin=^(void){
                
                
            };
        }
         ];
        return;
    }
    
    if(self.homeServiceArray.count<1) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"下拉刷新或检查网络";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
        }
         ];
        return;
    }
    
    //用户选择的服务类型
    signValueObject* signValue=[signValueObject shareSignValue];
    [signValue setSelectGlobalServicesModel:signValue.servicesModelArray[1]];
    //获取用户车型
    [HTTPconnect sendPOSTHttpWithUrl:userCarModelAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSMutableDictionary* redict=[(NSDictionary*)responseObject mutableCopy];
            [redict removeObjectForKey:@"result"];
            //            _po(redict);
            carModel* responseModel=[carModel carModelWithDictionary:redict];
            ChooseServerViewController* vc=[[ChooseServerViewController alloc]init];
            vc.carModel=responseModel;
            [self.parentContainer hiddenTabbar];
            [self.navigationController pushViewController:vc animated:NO];
        }
        else{
            //    选车页面
            selectCarController* selectCarVC=[[selectCarController alloc]init];
            //设置默认车型
            signValueObject* sign=[signValueObject shareSignValue];
            [sign SetDefaultCar:YES];
            [self.parentContainer hiddenTabbar];
            [self.navigationController pushViewController:selectCarVC animated:NO];
        }
    } failure:^(NSError *error) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"网络异常";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];}
         ];
    }];
    
    
}


#pragma -mark  跳转洗车页面
-(void)washCarServTap{
    
    
    //判断登陆
    if ([userDefaultManager getUserWithToken]==nil) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"请登陆";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
            loginViewController* loginVC=[[loginViewController alloc]init];
            [self presentViewController:loginVC animated:YES completion:nil];
        }
         ];
        return;
    }
    
    if(self.homeServiceArray.count<1) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"下拉刷新或检查网络";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
        }
         ];
        return;
    }
    
    
    //用户选择的服务类型
    signValueObject* signValue=[signValueObject shareSignValue];
    
    [signValue setSelectGlobalServicesModel:signValue.servicesModelArray[0]];
    
    //获取用户车型
    
    [HTTPconnect sendPOSTHttpWithUrl:userCarModelAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            NSMutableDictionary* redict=[(NSDictionary*)responseObject mutableCopy];
            [redict removeObjectForKey:@"result"];
            carModel* responseModel=[carModel carModelWithDictionary:redict];
            carOderViewController* washVc=[[carOderViewController alloc]init];
            washVc.selectCarmodel=responseModel;
            [self.parentContainer hiddenTabbar];
            [self.navigationController pushViewController:washVc animated:NO];
        }
        else{
            //    选车页面
            selectCarController* selectCarVC=[[selectCarController alloc]init];
            //设置默认车型
            signValueObject* sign=[signValueObject shareSignValue];
            [sign SetDefaultCar:YES];
            [self.parentContainer hiddenTabbar];
            [self.navigationController pushViewController:selectCarVC animated:NO];
        }
    } failure:^(NSError *error) {
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"网络异常";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠2秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];}
         ];
    }];
}



#pragma -mark 商品模块
-(void)commodityViewLayout{
    characteristicMarketView* marketView=[[[NSBundle mainBundle]loadNibNamed:@"characteristicMarketView" owner:nil options:nil]lastObject];
    self.marketView = marketView;
    self.marketView.method=@selector(goHotVC:);
    marketView.frame=CGRectMake(0,CGRectGetMaxY(self.servicesView.frame)+15.0, SCREEN_WIDTH, 35+15+35+169+SCREEN_WIDTH/2);
    [self.weatherBottomMainView addSubview:marketView];
    marketView.vc = self;
}





#pragma -mark 内存警告
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

@end
