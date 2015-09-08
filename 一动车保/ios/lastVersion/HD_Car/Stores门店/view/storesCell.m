//
//  storesCell.m
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/16.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "storesCell.h"
@interface storesCell()
//图片
@property(nonatomic,strong)UIImageView* iconView;

//标题
@property(nonatomic,strong)UILabel* titleLable;

//标签
@property(nonatomic,strong)UIView* featuresLableView;

//星级
@property(nonatomic,strong)UIView* starLevelView;

//评价
@property(nonatomic,strong)UILabel* commentLabel;

//地址
@property(nonatomic,strong)UILabel* addressesLabel;
//距离
@property(nonatomic,strong)UILabel* distanceLabel;
@end
@implementation storesCell

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier{
    self=[super initWithStyle:style reuseIdentifier:reuseIdentifier];
    //间距
    CGFloat padding=3.0;
//    标题和图片间距
    CGFloat titleImagePadding=SCREEN_WIDTH*0.05;
    
    //图片
    self.iconView=[[UIImageView alloc]initWithFrame:CGRectMake(5, 5, 75, 75)];
    [self.contentView addSubview:self.iconView];
    self.iconView.image=[UIImage imageNamed:@"baoshijie.jpg"];
    
    //标题
    self.titleLable=[[UILabel alloc]initWithFrame:CGRectMake(5+75+titleImagePadding, 5, SCREEN_WIDTH-160, 15)];
    self.titleLable.font=FONT14;
    [self.contentView addSubview:self.titleLable];
    self.titleLable.text=@"芳草街维修店";
    CGFloat titlLabelX=CGRectGetMinX(self.titleLable.frame);
    
    
    //标签
    self.featuresLableView=[[UIView alloc]initWithFrame:CGRectMake(titlLabelX, CGRectGetMaxY(self.titleLable.frame)+padding, SCREEN_WIDTH-160, 15)];
//    self.featuresLableView.backgroundColor=randomColor;
    [self.contentView addSubview:self.featuresLableView];
    
#warning 标签展示3个目前
        UIImageView* labelImageView1=[[UIImageView alloc]initWithFrame:CGRectMake(5, 2, 37, 11)];
        labelImageView1.image=[UIImage imageNamed:@"mendian_11"];
        [self.featuresLableView addSubview:labelImageView1];
    UIImageView* labelImageView2=[[UIImageView alloc]initWithFrame:CGRectMake(CGRectGetMaxX(labelImageView1.frame)+10, 2, 24, 11)];
    labelImageView2.image=[UIImage imageNamed:@"mendian_13"];
    [self.featuresLableView addSubview:labelImageView2];
    UIImageView* labelImageView3=[[UIImageView alloc]initWithFrame:CGRectMake(CGRectGetMaxX(labelImageView2.frame)+10, 2, 24, 11)];
    labelImageView3.image=[UIImage imageNamed:@"mendian_15"];
    [self.featuresLableView addSubview:labelImageView3];
    
    CGFloat starY=CGRectGetMaxY(self.featuresLableView.frame)+padding;
    
    //星级
    self.starLevelView=[[UIView alloc]initWithFrame:CGRectMake(titlLabelX, starY, 60, 15)];
    [self.contentView addSubview:self.starLevelView];
//    self.starLevelView.backgroundColor=randomColor;
    
#warning -星级展示5个
    for (int i=0; i<5; i++) {
        
        UIImageView* starIMG=[[UIImageView alloc]initWithFrame:CGRectMake(i*11+3, 2, 11, 11)];
        starIMG.image=[UIImage imageNamed:@"redStar"];
        [self.starLevelView addSubview:starIMG];
        
    }
    
    
    CGFloat starLevelMaxX=CGRectGetMaxX(self.starLevelView.frame);
    //评价
    self.commentLabel=[[UILabel alloc]initWithFrame:CGRectMake(starLevelMaxX+padding, starY, 90, 15)];
    [self.contentView addSubview:self.commentLabel];
    self.commentLabel.font=FONT12;
    self.commentLabel.text=@"234评价";
    
    CGFloat commentLabelMaxY=CGRectGetMaxY(self.commentLabel.frame);
    //地址
    self.addressesLabel=[[UILabel alloc]initWithFrame:CGRectMake(titlLabelX  , commentLabelMaxY , 100, 15)];
    self.addressesLabel.font=FONT12;
    [self.contentView addSubview: self.addressesLabel];
    self.addressesLabel.text=@"芳草街2号";
    
    //距离
    self.distanceLabel=[[UILabel alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-63, (85-15)/2, 50, 15)];
    [self.contentView addSubview:self.distanceLabel];
    self.distanceLabel.font=FONT12;
    self.distanceLabel.textAlignment=NSTextAlignmentRight;
//    self.distanceLabel.backgroundColor=randomColor;
    self.distanceLabel.text=@"500M";
    return self;
}



//静态方法

+(instancetype)cellWithTableView:(UITableView*)tableView{
    
    static NSString* idfier=@"storesCell";
    storesCell* cell=[tableView dequeueReusableCellWithIdentifier:idfier];
    if (!cell) {
        cell=[[storesCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:idfier];
    }
    return cell;

}
@end
