//
//  storeSubscribeView.m
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/18.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "storeSubscribeView.h"
@interface storeSubscribeView()
@property (weak, nonatomic) IBOutlet UIImageView *smallImageView;
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;
@property (weak, nonatomic) IBOutlet UILabel *numberStar;
@property (weak, nonatomic) IBOutlet UIView *starImage;
@property (weak, nonatomic) IBOutlet UIImageView *yuyueImage;
@end
@implementation storeSubscribeView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/





-(void)setImageStr:(NSString *)imageStr{
    _imageStr=imageStr;
    self.smallImageView.image=[UIImage imageNamed:self.imageStr];
}

-(void)setStoreName:(NSString *)storeName{
    _storeName=storeName;
  self.titleLabel.text=self.storeName;
}

-(void)setStarCount:(NSInteger)starCount{
    _starCount=starCount;
    NSInteger count=self.starCount/2;
    NSInteger f=self.starCount%2;
    for (int i=0; i<count; i++) {
        UIImageView* imageV=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"storesSelectStar"]];
        imageV.frame=CGRectMake(14*i, 0, 14, 14);
        [self.starImage addSubview:imageV];
    }
    if (f>0) {
        UIImageView* imageV=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"storesStar_2"]];
        imageV.frame=CGRectMake(14*count, 0, 14, 14);
        [self.starImage addSubview:imageV];
    }
    CGFloat number=self.starCount/2.0;
    self.numberStar.text=[NSString stringWithFormat:@"%.1lf分",number];
}

@end
