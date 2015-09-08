//
//  servicesOrderViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "servicesOrderViewController.h"
#import "HDNavigationView.h"
#import "HDSelectUserLocationViewController.h"
#import "shareMapView.h"
#import "servicesOrderInfoView.h"
#import "storesOrderPriceView.h"
#import "UIImage+GIF.h"
#import "servicesOrderBystoresView.h"
#import "servicesOrderGoHomeView.h"
@interface servicesOrderViewController ()
@property(nonatomic,strong) HDNavigationView* titleView;

@property(nonatomic,strong)servicesOrderInfoView* servicesInfoView;

/**门店服务VIEW*/
@property(nonatomic,strong)servicesOrderBystoresView* byStoresView;

/**上门服务VIEW*/
@property(nonatomic,strong)servicesOrderGoHomeView* goHomeView;

@property(nonatomic,strong)storesOrderPriceView* pricesView;

@property(nonatomic,strong)UIView* submitView;
/**金额*/
@property(nonatomic,strong)UILabel* pricesLabel;
/**提交订单*/
@property(nonatomic,strong)UIButton* submitBtn;
@property(nonatomic,strong)UIScrollView* osScrollView;

@property(nonatomic,weak)UIImageView* gif;

@property(nonatomic,strong)UIView* backView;
@end

@implementation servicesOrderViewController

#pragma -mark once load
-(servicesOrderGoHomeView *)goHomeView{
    if (!_goHomeView) {
        _goHomeView=[[[NSBundle mainBundle]loadNibNamed:@"servicesOrderGoHomeView" owner:nil options:nil]lastObject];
        _goHomeView.frame=CGRM(0, CGRectGetMaxY(self.servicesInfoView.frame), SCREEN_WIDTH, 315);
        [_goHomeView.selectServicesLabel addTapGestureRecognizerWithTarget:self action:@selector(changService)];
        [self.osScrollView addSubview:_goHomeView];
    }
    return _goHomeView;

}

-(UIView *)backView{
    if (!_backView) {
        _backView=[[UIView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT)];
        _backView.backgroundColor=[UIColor whiteColor];
//        _blackView.alpha=0.3;
        [self.view addSubview:_backView];
    }
    return _backView;
}

-(UIView *)submitView{
    if (!_submitView) {
        _submitView=[[UIView alloc]initWithFrame:CGRectMake(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 50)];
        _submitView.backgroundColor=[UIColor blackColor];
    }
    return _submitView;
}

-(UIScrollView *)osScrollView{
    if (!_osScrollView) {
      CGFloat  titleViewY=CGRectGetMaxY(self.titleView.frame);
        _osScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, titleViewY, SCREEN_WIDTH, SCREEN_HEIGHT-50-titleViewY)];
        _osScrollView.contentSize=CGSizeMake(SCREEN_WIDTH, 560);
        _osScrollView.backgroundColor=HDfillColor;
    }
    return _osScrollView;
}

#pragma -VIEW LOAD
- (void)viewDidLoad {
    [super viewDidLoad];
    self.titleView=[HDNavigationView navigationViewWithTitle:@"服务订单"];
    WEAKSELF;
    [self.titleView tapLeftViewWithImageName:nil tapBlock:^{
//        [self.view endEditing:YES];
        [weakSelf.navigationController popToRootViewControllerAnimated:YES];
    }];
    [self layoutServicesOrderView];
    //    提交订单view
    [self submitViewLayout];
    
}

-(void)layoutServicesOrderView{
    [self.view addSubview:self.titleView];
    [self.view addSubview:self.osScrollView];
    
    self.servicesInfoView=[[[NSBundle mainBundle]loadNibNamed:@"servicesOrderInfoView" owner:nil options:nil]lastObject];
    self.servicesInfoView.frame=CGRM(0, 0, SCREEN_WIDTH, 110);
    self.byStoresView=[[[NSBundle mainBundle]loadNibNamed:@"servicesOrderBystoresView" owner:nil options:nil]lastObject];
    self.byStoresView.frame=CGRM(0, CGRectGetMaxY(self.servicesInfoView.frame), SCREEN_WIDTH, 347);
    self.byStoresView.personText.moveView=self.view;
    self.byStoresView.phoneText.moveView=self.view;
        [self.byStoresView.selectStoresLabel addTapGestureRecognizerWithTarget:self action:@selector(selectStoresTap)];
    self.pricesView=[[[NSBundle mainBundle]loadNibNamed:@"storesOrderPriceView" owner:nil options:nil]lastObject];
    self.pricesView.frame=CGRM(0, CGRectGetMaxY(self.byStoresView.frame)+20, SCREEN_WIDTH, 50);
    [self.osScrollView addSubview:self.servicesInfoView];
    [self.osScrollView addSubview:self.byStoresView];
    [self.osScrollView addSubview:self.pricesView];
    [self.byStoresView.selectServiceLabel addTapGestureRecognizerWithTarget:self action:@selector(changService)];
    
}

-(void)submitViewLayout{
    [self.view addSubview:self.submitView];
    float submitWith=0.25*SCREEN_WIDTH;
    UILabel* lb1=[[UILabel alloc]initWithFrame:CGRectMake(20, 0, 80,self.submitView.frame.size.height)];
    lb1.text=@"实付款:";
    lb1.textColor=WHITECOLOR;
    [self.submitView addSubview:lb1];
    float pricesX=CGRectGetMaxX(lb1.frame)+10;
    float pricesW=SCREEN_WIDTH-submitWith-pricesX;
    self.pricesLabel=[[UILabel alloc]initWithFrame:CGRectMake(pricesX, 0, pricesW, self.submitView.frame.size.height)];
    self.pricesLabel.text=@"￥3685";
    self.pricesLabel.textColor=WHITECOLOR;
    [self.submitView addSubview:self.pricesLabel];
    self.submitBtn=[[UIButton alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-submitWith, 0, submitWith, self.submitView.frame.size.height)];
    self.submitBtn.backgroundColor=[UIColor redColor];
    self.submitBtn.titleLabel.textColor=[UIColor whiteColor];
    [self.submitBtn setTitle:@"提交" forState:UIControlStateNormal];
    [self.submitBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.submitView addSubview:self.submitBtn];
    [self.submitBtn addTarget:self action:@selector(submitOrder) forControlEvents:UIControlEventTouchUpInside];
}


#pragma -mark  切换服务
-(void)changService{
    static NSInteger changeIndex=0;
    
    [self.backView setHidden:NO];
    UIImageView* gifImageView=[[UIImageView alloc]initWithImage:[UIImage sd_animatedGIFNamed:@"bufferGIF"]];
    gifImageView.frame=CGRM((SCREEN_WIDTH-210)/2.0,(SCREEN_HEIGHT-70)/2.0, 210, 70);
        [self.view addSubview:gifImageView];
        self.gif=gifImageView;
    
    self.gif.hidden=NO;
    if (changeIndex==0) {
        self.byStoresView.hidden=YES;
        self.goHomeView.hidden=NO;
        changeIndex=1;
    }else{
        self.byStoresView.hidden=NO;
        self.goHomeView.hidden=YES;
        changeIndex=0;
    }
    NSTimer* timer = [NSTimer timerWithTimeInterval:2.5
                                                target:self
                                              selector:@selector(changeServiceView)
                                              userInfo:nil
                                               repeats:NO];
    [[NSRunLoop currentRunLoop] addTimer:timer forMode:NSRunLoopCommonModes];
    
}

#pragma -mark 切换serviceView显示
-(void)changeServiceView{
    self.gif.hidden=YES;
    [self.backView setHidden:YES];
    self.gif=nil;
}


#pragma -mark   选择位置 、 门店
-(void)selectStoresTap{
    
    HDSelectUserLocationViewController* userLocationVC=[[HDSelectUserLocationViewController alloc]init];
    userLocationVC.title   = @"单击选择你的位置";
    userLocationVC.mapView = [shareMapView share].mapView;
    userLocationVC.search  = [shareMapView share].search;
//    userLocationVC.storesModelBlock=^(storeModel* mo){
//            
//    };
    
    [self.navigationController pushViewController:userLocationVC animated:NO];
}







#pragma -mark 提交订单
-(void)submitOrder{


}


@end
