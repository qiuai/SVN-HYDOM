//
//  characteristicMarketView.h
//  HD_Car
//
//  Created by xingso on 15/7/9.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HomeViewController.h"
@interface characteristicMarketView : UIView

//特色市场
@property (weak, nonatomic) IBOutlet UIImageView *marketSuggustImageView;
@property (weak, nonatomic) IBOutlet UIImageView *marketLimitImageView;
@property (weak, nonatomic) IBOutlet UIImageView *marketSaleImageView;
@property (weak, nonatomic) IBOutlet UIImageView *marketLifeImageView;

//热门分类
@property (weak, nonatomic) IBOutlet UIView *hotMoreView;

@property (weak, nonatomic) IBOutlet UIView *hotTopLeftView;
@property (weak, nonatomic) IBOutlet UIView *hotTopRightView;
@property (weak, nonatomic) IBOutlet UIView *hotBottomLeftView;
@property (weak, nonatomic) IBOutlet UIView *hotBottomRightView;

@property (nonatomic, weak) HomeViewController * vc;
@property (nonatomic, strong) NSArray *redHotArray;
@property (nonatomic, strong) NSArray *blistArray;

@property(nonatomic,strong)NSDictionary* servicesDict;

-(void)layoutUI;
@property(nonatomic,assign)SEL  method;
@end
