//
//  JVdisplayProductView.m
//  HD_Car
//
//  Created by xingso on 15/8/7.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVdisplayProductView.h"
#import "HDApiUrl.h"
#import "YC_GoodsModel.h"
#import "UIImageView+WebCache.h"
#define viewWith 60
@interface JVdisplayProductView()
@property(nonatomic,strong)UIView* tapRightV;

@property(nonatomic,weak)UILabel* countLabel;
@end
@implementation JVdisplayProductView

-(id)initWithCoder:(NSCoder *)aDecoder{
   self=[super initWithCoder:aDecoder];
    [self initSubView];
    return self;
}

-(instancetype)init{
    self=[super init];
    [self initSubView];
    return self;
}


-(void)initSubView{
    self.backgroundColor=[UIColor whiteColor];
    
    self.tapRightV=[[UIView alloc]initWithFrame:CGRM(SCREEN_WIDTH-80, 0, 80, viewWith)];
    [self addSubview:self.tapRightV];
    
    UILabel* b=[[UILabel alloc]initWithFrame:CGRM(SCREEN_WIDTH-5-20, 0, 20, viewWith)];
    b.text=@">";
    b.font=[UIFont systemFontOfSize:16.0];
    b.textColor=COLOR(177, 177, 177, 1.0);
    [self addSubview:b];

    UILabel* abtn=[[UILabel alloc]initWithFrame:CGRM(b.frame.origin.x-40, 0,40, viewWith)];
    abtn.text=@"共3种";
    abtn.font=[UIFont systemFontOfSize:14.0];
    abtn.textColor=COLOR(177, 177, 177, 1.0);
    [self addSubview:abtn];
    self.countLabel=abtn;
    
    UIView* imageV=[[UIView alloc]initWithFrame:CGRM(abtn.frame.origin.x-60, 0, 60, viewWith)];
    [self addSubview:imageV];
    UIImageView* dbtn=[[UIImageView alloc]initWithFrame:CGRM(0, 25, 60, 10)];
    dbtn.image=[UIImage imageNamed:@"dian333"];
    [imageV addSubview:dbtn];
}

-(void)setProductArray:(NSArray *)productArray{
    _productArray=productArray;
    NSInteger count=productArray.count;
    NSInteger maxCout=count>3?3:count;
    self.countLabel.text=[NSString stringWithFormat:@"共%ld种",(long)count];
    CGFloat padding=15;
    CGFloat leftpadding=30;
    CGFloat w=viewWith-20;
    for (NSInteger i=0; i<maxCout; i++) {
        UIImageView* image=[[UIImageView alloc]initWithFrame:CGRM(leftpadding+padding*i+i*w, 10, w, w)];
        image.tag=20*i;
        [self addSubview:image];
        YC_GoodsModel* model=productArray[i];
        [image sd_setImageWithURL:imageURLWithPath(model.pimage) placeholderImage:[UIImage imageNamed:FillImage]];
//        image.backgroundColor=randomColor;
    }
    
}
@end
