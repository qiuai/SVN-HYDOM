//
//  CarDetailInforView.h
//  HD_Car
//
//  Created by hydom on 15-7-20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ParameterDetailsView.h"
#import "JVcommodityInfoViewController.h"
@interface CarDetailInforView : UIView

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *inforViewHeight;
@property (weak, nonatomic) IBOutlet UILabel *VersionLabel;
@property (weak, nonatomic) IBOutlet UILabel *moneyLabel;
@property (weak, nonatomic) IBOutlet UIView *inforView;

@property (weak, nonatomic) IBOutlet UILabel *evaluateNumLabel;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *ImageTextLabelConstraint;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *imageTextButtonConstraint;

@property (nonatomic, weak) JVcommodityInfoViewController * vc;
@property (nonatomic,strong)ParameterDetailsView * parameterView;
@property (nonatomic,assign)NSInteger buyNum;

@property (weak, nonatomic) IBOutlet UIView *line1View;
@property (weak, nonatomic) IBOutlet UIView *line2View;
@property (weak, nonatomic) IBOutlet UIView *line3View;
@property (weak, nonatomic) IBOutlet UIView *line4View;
@property (weak, nonatomic) IBOutlet UIView *line5View;
@property (weak, nonatomic) IBOutlet UIView *line6View;
@property (weak, nonatomic) IBOutlet UIView *line7View;


/**商品名称*/
@property(strong,nonatomic)NSString* goodsTitle;
/**商品价格*/
@property(strong,nonatomic)NSNumber* goodsPrices;
/**支持服务*/
@property(strong,nonatomic)NSArray* servicesArray;
/**评价数*/
@property(strong,nonatomic)NSNumber* pingjia;
/**详细参数*/
@property(strong,nonatomic)NSArray* parameter;
/**图文URL*/
@property(strong,nonatomic)NSString* purl;


- (void)setUpUserInterface;

@end
