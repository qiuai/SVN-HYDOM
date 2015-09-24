//
//  JVcommonDisplayServicesViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVDisplayServicesViewController.h"
#import "JVcommonDisplayServicesView.h"
#import "commonDisplayProductVIew.h"
#import "UIImageView+WebCache.h"
#import "HDApiUrl.h"

@interface JVDisplayServicesViewController ()
@property(nonatomic, strong)UIScrollView * baseScrollView;
@property(nonatomic,assign)CGFloat endY;
@end

@implementation JVDisplayServicesViewController

-(void)setDataSoure:(NSArray *)dataSoure{
    _dataSoure=dataSoure;
    self.ViewHeight=0.0;
    NSInteger count=_dataSoure.count;
    for (NSInteger i=0;i<count;i++) {
        JVcommonOrderSericesModel *orderModel=_dataSoure[i];
        self.ViewHeight+=44;
        NSInteger productCount=orderModel.productArray.count;
        for (NSInteger j=0; j<productCount; j++) {
            self.ViewHeight+=100;
        }
    }
    
}


- (void)viewDidLoad {
    [super viewDidLoad];
    NSInteger count=self.dataSoure.count;
    self.endY=0;
    for (NSInteger i=0;i<count;i++) {
        JVcommonOrderSericesModel *orderModel=self.dataSoure[i];
        JVcommonDisplayServicesView* serView=[[[NSBundle mainBundle]loadNibNamed:@"JVcommonDisplayServicesView" owner:nil options:nil]lastObject];
        serView.frame=CGRM(0, _endY, SCREEN_WIDTH, 44);
        [serView.icon sd_setImageWithURL:imageURLWithPath(orderModel.scimg) placeholderImage:[UIImage imageNamed:FillImage]];
        serView.name.text=orderModel.scname;
        serView.prices.text=[UtilityMethod addRMB:orderModel.scprice];
        [self.view addSubview:serView];
        _endY+=44;
        NSInteger productCount=orderModel.productArray.count;
        //判断纯服务
        if(self.pureServer==YES) {
            serView.prices.hidden = YES;
            serView.serverPay.hidden = YES;
        }
        
        for (NSInteger j=0; j<productCount; j++) {
            JVcommonOrderSericesProductMOdel* productModel=orderModel.productArray[j];
            commonDisplayProductVIew* productView=[[[NSBundle mainBundle]loadNibNamed:@"commonDisplayProductVIew" owner:nil options:nil]lastObject];
            productView.frame=CGRM(0, _endY, SCREEN_WIDTH, 99);
            [productView.image sd_setImageWithURL:imageURLWithPath(productModel.pimg) placeholderImage:[UIImage imageNamed:FillImage]];
            productView.prices.text=[UtilityMethod addRMB:productModel.pprice];
            productView.text.text=productModel.pname;
            productView.count.text=[NSString stringWithFormat:@"X %@",productModel.pnum];
            [self.view addSubview:productView];
            _endY+=100;
        }
    }
}





@end
