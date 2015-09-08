//
//  qqMusicContainer.m
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/19.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "qqMusicContainer.h"
#import "storeHome.h"
#import "storeSubscribeView.h"
#import "storeCommentCell.h"

#define qqImageName @"c123.jpg"
@interface qqMusicContainer ()<UIScrollViewDelegate,UITableViewDataSource,UITableViewDelegate>{
    CGFloat tailStrongImageMaxY;
    
    CGFloat oldContentMainViewY;
    
    CGFloat moveHeight;
    
    CGFloat monitorHeight;
}
//主要的ScrollView
@property(nonatomic,strong)UIScrollView* qqMainScrollView;
//图片父控件
@property(nonatomic,strong)UIView* imageContainerView;
//大图
@property(nonatomic,strong)UIImageView* bigImageView;
//底部视图
@property(nonatomic,strong)UIView* contentMainView;
//label
@property(nonatomic,strong)UIView* tabBarView;
@property(nonatomic,strong)UIView* onePxView;
@property(nonatomic,strong)UIScrollView* changeScrollView;
//tableView
@property(nonatomic,strong)UITableView* tableRootView;


//contentScrollView rootView
@property(nonatomic,strong)UIScrollView* contentParentChangeScrollView;

//门店详情view
@property(nonatomic,strong)UIView* storeHomeView;
//服务项目View
@property(nonatomic,strong)UIView* servicesView;

//数据条数
@property(nonatomic,assign)NSInteger dataCount;

//预定
@property(nonatomic,assign)storeSubscribeView* subscribeVW;
//指针
//指针 指向点击的UIlabel
@property(nonatomic,weak)UILabel* titleLabelPointer;

@end
@implementation qqMusicContainer
#pragma -mark 懒加载
-(storeSubscribeView *)subscribeVW{
    if (!_subscribeVW) {
        _subscribeVW=[[[NSBundle mainBundle]loadNibNamed:@"storeSubscribeView" owner:nil options:nil]lastObject];
        _subscribeVW.frame=CGRectMake(0, self.imageContainerView.height-40, SCREEN_WIDTH, 40);
#warning 测试门店预定
        _subscribeVW.imageStr=@"a123.jpg";
        _subscribeVW.starCount=9;
        _subscribeVW.storeName=@"武侯去个头一轮胎精品店";
    }
    return _subscribeVW;
}


-(UIView *)storeHomeView{

    if (!_storeHomeView) {
        //    //门店详情view
        _storeHomeView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, self.contentParentChangeScrollView.frame.size.height)];
        _storeHomeView.backgroundColor=[UIColor clearColor];
        storeHome* homeStore=[[[NSBundle mainBundle]loadNibNamed:@"storeHome" owner:nil options:nil]lastObject];
        homeStore.frame=CGRectMake(0, 0, SCREEN_WIDTH, 0.4*SCREEN_WIDTH);
//       __weak typeof(self) thisVC=self;
        homeStore.locatonServiceBlock=^(){
            
//            kcMapViewController* mapVC=[[kcMapViewController alloc]init];
//            [thisVC.parentViewController.navigationController pushViewController:mapVC animated:YES];
        };
        [_storeHomeView addSubview:homeStore];
    }
    return _storeHomeView;
}

-(UIScrollView *)contentParentChangeScrollView{
    if (!_contentParentChangeScrollView) {
        
        _contentParentChangeScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(self.tabBarView.frame)+20, SCREEN_WIDTH, self.view.height-SCREEN_WIDTH/2.0-60+moveHeight)];;
        _contentParentChangeScrollView.contentSize=CGSizeMake(SCREEN_WIDTH*3, _contentParentChangeScrollView.frame.size.height);
        _contentParentChangeScrollView.pagingEnabled=YES;
        _contentParentChangeScrollView.showsHorizontalScrollIndicator=NO;
        _contentParentChangeScrollView.delegate=self;
        _contentParentChangeScrollView.tag=89;
        _contentParentChangeScrollView.scrollEnabled=NO;
    }
    return _contentParentChangeScrollView;
    
}

-(UITableView *)tableRootView{
    if (!_tableRootView) {
        _tableRootView=[[UITableView alloc]initWithFrame:CGRectMake(2*SCREEN_WIDTH, 0, SCREEN_WIDTH, self.contentParentChangeScrollView.height)];
        _tableRootView.backgroundColor=randomColor;
        _tableRootView.scrollEnabled=NO;
        _tableRootView.dataSource=self;
        _tableRootView.delegate=self;
    }
    return _tableRootView;

}

-(UIView *)contentMainView{
    if (!_contentMainView) {
        oldContentMainViewY=CGRectGetMaxY(self.imageContainerView.frame);
        _contentMainView=[[UIView alloc]initWithFrame:CGRectMake(0,oldContentMainViewY , SCREEN_WIDTH, self.view.height-oldContentMainViewY+moveHeight)];
        _contentMainView.clipsToBounds=YES;
        _contentMainView.backgroundColor=HDfillColor;
    }return _contentMainView;

}
-(UIScrollView *)qqMainScrollView{
    if (!_qqMainScrollView) {
        CGFloat sizeHeight=SCREEN_WIDTH/2.0+125+135.0*self.dataCount;
        _qqMainScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, self.view.height)];
        _qqMainScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, sizeHeight);
        _qqMainScrollView.delegate=self;
        _qqMainScrollView.tag=88;
        _qqMainScrollView.showsVerticalScrollIndicator=NO;
    }return _qqMainScrollView;
    
}
-(UIView *)imageContainerView{
    if (!_imageContainerView) {
        _imageContainerView=[[UIView alloc]init];
        _imageContainerView.frame=CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_WIDTH/2.0);
    }
    return _imageContainerView;
}
-(UIView *)tabBarView{

    if (!_tabBarView) {
        _tabBarView=[[UIView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 40)];
        for (NSInteger i=0; i<3; i++) {
            CGFloat lbWidth=SCREEN_WIDTH/3.0;
            UILabel* titleLabe=[[UILabel alloc]initWithFrame:CGRectMake(i*lbWidth, 0, lbWidth, 40)];
            titleLabe.backgroundColor=[UIColor whiteColor];
            titleLabe.tag=i+5;
            titleLabe.textAlignment=NSTextAlignmentCenter;
            titleLabe.font=FONT14;
            switch (i) {
                case 0:
                    titleLabe.text=@"门店详情";
                    titleLabe.textColor=ShareBackColor;
                    self.titleLabelPointer=titleLabe;
                    break;
                case 1:
                    titleLabe.text=@"服务项目";
                    titleLabe.textColor=BLACKCOLOR;
                    break;
                default:
                    titleLabe.text=@"客户评价";
                    titleLabe.textColor=BLACKCOLOR;
                    break;
            }
            [titleLabe addTapGestureRecognizerWithTarget:self action:@selector(tabBarChangeEvent:)];
            [_tabBarView addSubview:titleLabe];
        }
        self.onePxView=[[UIView alloc]initWithFrame:CGRectMake(2, 40-2, SCREEN_WIDTH/3.0-4, 1)];
        self.onePxView.backgroundColor=ShareBackColor;
        [_tabBarView addSubview:self.onePxView];
    }return _tabBarView;
}

#pragma -mark 布局
- (void)viewDidLoad {
    [super viewDidLoad];
     moveHeight=SCREEN_WIDTH/3.0;
    self.dataCount=7;
    self.automaticallyAdjustsScrollViewInsets=NO;
    
    // 主scollView
    [self.view addSubview:self.qqMainScrollView];
//    图片
    [self.qqMainScrollView addSubview:self.imageContainerView];
    
    self.bigImageView=[[UIImageView alloc]initWithFrame:CGRectMake(0, 0, self.imageContainerView.width, self.imageContainerView.height)];
    self.bigImageView.image=[UIImage imageNamed:qqImageName];
    [self.imageContainerView addSubview:self.bigImageView];
    //预定
    
    [self.imageContainerView addSubview:self.subscribeVW];
    //底部
    [self.qqMainScrollView addSubview:self.contentMainView];
    //tab bar
    [self.contentMainView addSubview:self.tabBarView];
    //切换视图
    [self.contentMainView addSubview:self.contentParentChangeScrollView];
    //子视图
    [self.contentParentChangeScrollView addSubview:self.storeHomeView];
    //    //服务项目View
    self.servicesView=[[UIView alloc]initWithFrame:CGRectMake(SCREEN_WIDTH, 0, SCREEN_WIDTH, self.contentParentChangeScrollView.frame.size.height)];
    self.servicesView.backgroundColor=randomColor;
    [self.contentParentChangeScrollView addSubview:self.servicesView];
    
    [self.contentParentChangeScrollView addSubview:self.tableRootView];
    
}



#pragma -mark tabbar 切换视图 
-(void)tabBarChangeEvent:(UITapGestureRecognizer*)sender{
    
    //按钮切换
    self.titleLabelPointer.textColor=BLACKCOLOR;
    UILabel*lb=(UILabel*)sender.view;
    lb.textColor=ShareBackColor;
    self.titleLabelPointer=lb;
    [UIView animateWithDuration:0.35 animations:^{
        self.onePxView.x=(lb.tag-5)*(SCREEN_WIDTH/3.0)+2;
    }];
    //视图切换
    CGFloat contentX=(lb.tag-5)*SCREEN_WIDTH;
    self.contentParentChangeScrollView.contentOffset=CGPointMake(contentX, 0);
    
}


#pragma -mark scrollView代理协议
- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    if (scrollView.tag==88) {
    CGFloat sy=scrollView.contentOffset.y;
        
    //图片 放大效果
    if (sy<=0.0&&sy>-40.0) {
        CGRect newRect;
        CGFloat changeY=sy*1.2;
        CGFloat newH=SCREEN_WIDTH/2.0-changeY;
        CGFloat newW=newH*2.0;
        newRect=CGRectMake(changeY, sy, newW, newH);
        //图片改变
        self.imageContainerView.frame=newRect;
        self.bigImageView.frame=CGRectMake(0, 0, newW, newH);
        self.subscribeVW.frame=CGRectMake((newW-SCREEN_WIDTH)/2.0, newH-40, SCREEN_WIDTH, 40);
        // tabbar改变
        self.contentMainView.y=CGRectGetMaxY(self.imageContainerView.frame);
    }
    //底部效果
    if (sy>=0&&sy<moveHeight) {
        self.imageContainerView.y=sy;
    }else if(sy>=moveHeight){
        self.contentMainView.y=oldContentMainViewY+sy-moveHeight;
        self.imageContainerView.y=sy;
        monitorHeight=sy-moveHeight;
        //滚动tableView
        if (self.titleLabelPointer.tag==7) {
            [self.tableRootView setContentOffset:CGPointMake(0, sy-moveHeight) animated:NO];
        }else if(self.titleLabelPointer.tag!=7){
            //滚动tableView
            [self.tableRootView setContentOffset:CGPointMake(0, 0) animated:NO];
            self.contentMainView.y=self.contentMainView.y-monitorHeight;
            self.imageContainerView.y=self.imageContainerView.y-monitorHeight;
            [self.qqMainScrollView setContentOffset:CGPointMake(0, (self.qqMainScrollView.contentOffset.y)-monitorHeight) animated:NO];
        }
    }
        
        if (sy==0.0) {
            [self.tableRootView setContentOffset:CGPointMake(0, 0) animated:YES];
        }
        
        
    }
}

#pragma -mark  tabelView代理

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataCount;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString* identif=@"storeCommentCell";
    storeCommentCell* cell=[tableView dequeueReusableCellWithIdentifier:identif];
    if (cell==nil) {
        cell=[[[NSBundle mainBundle]loadNibNamed:@"storeCommentCell" owner:nil options:nil]lastObject];
    }
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 135.0;

}



@end
