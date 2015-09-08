//
//  YC_orderView.h
//  HD_Car
//
//  Created by hydom on 8/12/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "YC_OrderServerViewController.h"
#import "YC_GoodsOrderModerl.h"
#import "JVshowkeyboardTextFiled.h"
@interface YC_orderView : UIView

@property (nonatomic, weak) YC_OrderServerViewController * vc;
//数据模型
@property (nonatomic, strong) YC_GoodsOrderModerl * model;
@property (weak, nonatomic) IBOutlet UIView *payTapView1;
@property (weak, nonatomic) IBOutlet UIView *payTapView2;
@property (weak, nonatomic) IBOutlet UIView *payTapView3;

@property (weak, nonatomic) IBOutlet UIButton *discountButton;

@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *userTextField;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *phoneTextField;

@property (weak, nonatomic) IBOutlet UILabel *address1;
@property (weak, nonatomic) IBOutlet UILabel *address2;

/**支付方式*/
@property(strong,nonatomic)NSString* payStyle;
/**支付方式描述*/
@property(strong,nonatomic)NSString* payStyleDescription;

@property (weak, nonatomic) IBOutlet UILabel *payLabel;
@property (weak, nonatomic) IBOutlet UILabel *saleLabel;



@property(nonatomic,strong)NSDictionary* addressDict;

@end
