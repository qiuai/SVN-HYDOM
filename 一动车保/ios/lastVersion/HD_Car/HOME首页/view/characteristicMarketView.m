//
//  characteristicMarketView.m
//  HD_Car
//
//  Created by xingso on 15/7/9.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "characteristicMarketView.h"
#import "YC_MarketViewController.h"
#import "YC_HotSaleViewController.h"
#import "YC_HotGoodsViewController.h"
#import "UIImageView+WebCache.h"
#import "HDApiUrl.h"
@implementation characteristicMarketView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(void)awakeFromNib
{
    //特色市场
    UITapGestureRecognizer * marketSuggustTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(markertTapGR:)];
    UITapGestureRecognizer * marketLimitTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(markertTapGR:)];
    UITapGestureRecognizer * marketSaleTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(markertTapGR:)];
    UITapGestureRecognizer * marketLifeTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(markertTapGR:)];

        [self.marketSuggustImageView addGestureRecognizer:marketSuggustTapGR];
        [self.marketLimitImageView addGestureRecognizer:marketLimitTapGR];
        [self.marketSaleImageView addGestureRecognizer:marketSaleTapGR];
        [self.marketLifeImageView addGestureRecognizer:marketLifeTapGR];

    //热门分类
    UITapGestureRecognizer * hotMoreTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hotTapGR:)];
    UITapGestureRecognizer * hotTopLeftTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hotTapGR:)];
    UITapGestureRecognizer * hotTopRightTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hotTapGR:)];
    UITapGestureRecognizer * hotBottomLeftTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hotTapGR:)];
    UITapGestureRecognizer * hotBottomRightTapGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hotTapGR:)];
    
    [self.hotMoreView addGestureRecognizer:hotMoreTapGR];
    [self.hotTopLeftView addGestureRecognizer:hotTopLeftTapGR];
    [self.hotTopRightView addGestureRecognizer:hotTopRightTapGR];
    [self.hotBottomLeftView addGestureRecognizer:hotBottomLeftTapGR];
    [self.hotBottomRightView addGestureRecognizer:hotBottomRightTapGR];

}


-(void)markertTapGR:(UIGestureRecognizer *)sender
{
    YC_MarketViewController * vc = [[YC_MarketViewController alloc] init];
    if (sender.view == self.marketSuggustImageView) {
        vc.marketType = sugeest;
    } else if (sender.view == self.marketLimitImageView) {
        vc.marketType = limit;
    } else if (sender.view == self.marketSaleImageView) {
        vc.marketType = sale;
    } else if (sender.view == self.marketLifeImageView) {
        vc.marketType = life;
    }
     [self.vc.navigationController pushViewController:vc animated:YES];

}

-(void)hotTapGR:(UIGestureRecognizer *)sender
{
    if (sender.view == self.hotMoreView) {
        [self.vc performSelector:self.method withObject:@"1"];
    }
    if (sender.view == self.hotTopLeftView) {
        [self.vc performSelector:self.method withObject:@"2"];
    }
    if (sender.view == self.hotTopRightView) {
        [self.vc performSelector:self.method withObject:@"3"];
    }
    if (sender.view == self.hotBottomLeftView) {
        [self.vc performSelector:self.method withObject:@"4"];
    }
    if (sender.view == self.hotBottomRightView) {
        [self.vc performSelector:self.method withObject:@"5"];
    }
    
    

}

-(void)layoutUI
{
    if (self.blistArray.count != 0) {
        for (int i = 0; i < _blistArray.count; i ++) {
            switch (i) {
                case 0: {
                    [_marketSuggustImageView sd_setImageWithURL:imageURLWithPath(self.blistArray[0][@"bimage"])placeholderImage:[UIImage imageNamed:@"homeTest21332.png"]];
                }
                    break;
                case 1: {
                    [_marketLimitImageView sd_setImageWithURL:imageURLWithPath(self.blistArray[1][@"bimage"]) placeholderImage:[UIImage imageNamed:@"homeTest213321.png"]];
                }
                    break;
                case 2: {
                    [_marketSaleImageView sd_setImageWithURL:imageURLWithPath(self.blistArray[2][@"bimage"])placeholderImage:[UIImage imageNamed:@"homeTest213cc.png"]];
                }
                    break;
                case 3: {
                    [_marketLifeImageView sd_setImageWithURL:imageURLWithPath(self.blistArray[3][@"bimage"])placeholderImage:[UIImage imageNamed:@"homeTest2122.png"]];
                }
                    break;
                default:
                    break;
            }
        }
    }
    
    if (self.redHotArray.count != 0) {
        NSLog(@"%@", _redHotArray);
        for (int i = 0; i < _redHotArray.count; i ++) {
            switch (i) {
                case 0: {
                    [[_hotTopLeftView.subviews[1] subviews][0]  sd_setImageWithURL:imageURLWithPath(self.redHotArray[0][@"pcimage"])];
                    [_hotTopLeftView.subviews[0] setText:_redHotArray[0][@"pcname"]];
                }
                    break;
                case 1: {
                    [[_hotTopRightView.subviews[1] subviews][0]  sd_setImageWithURL:imageURLWithPath(self.redHotArray[1][@"pcimage"])];
                    [_hotTopRightView.subviews[0] setText:_redHotArray[1][@"pcname"]];
                }
                    break;
                case 2: {
                    [[_hotBottomLeftView.subviews[1] subviews][0]  sd_setImageWithURL:imageURLWithPath(self.redHotArray[2][@"pcimage"])];
                    [_hotBottomLeftView.subviews[0] setText:_redHotArray[2][@"pcname"]];
                }
                    break;
                case 3: {
                    [[_hotBottomRightView.subviews[1] subviews][0]  sd_setImageWithURL:imageURLWithPath(self.redHotArray[3][@"pcimage"])];
                    [_hotBottomRightView.subviews[0] setText:_redHotArray[3][@"pcname"]];
                }
                    break;
                default:
                    break;
            }
        }
    }

}

@end
