//
//  HDNavigationView.m
//  HD_InsuranceCar
//
//  Created by cc on 15/6/13.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "HDNavigationView.h"
typedef void(^clickBlock)(void);

@interface HDNavigationView()

@property(nonatomic,copy) clickBlock taPblock;
@property(nonatomic,copy) clickBlock leftBlock;

//标题
@property(nonatomic,weak)UILabel* titleLabel;

@end
@implementation HDNavigationView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
/**初始化*/
-(instancetype)initWithTitle:(NSString*)title{
    self=[super init];
    self.frame=CGRectMake(0, 0, SCREEN_WIDTH, 64);
    self.backgroundColor=ShareBackColor;
    UILabel* titleLabel=[[UILabel alloc]initWithFrame:CGRectMake((SCREEN_WIDTH-80)/2.0, 27, 80, 30)];
    titleLabel.text=title;
    titleLabel.textColor=[UIColor whiteColor];
    titleLabel.backgroundColor=[UIColor clearColor];
    titleLabel.font=[UIFont systemFontOfSize:18.0];
    titleLabel.textAlignment=NSTextAlignmentCenter;
    self.titleLabel=titleLabel;
    [self addSubview:titleLabel];
    
    return self;
}


#pragma -mark 添加搜索View
-(void)addSearchView{
    //添加搜索view
    UIView* serchTapView=[[UIView alloc]initWithFrame:CGRectMake(SCREEN_WIDTH-44, 20, 44, 44)];
    UIImageView* serchIMGV=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"homeSearch"]];
    serchIMGV.frame=CGRectMake(17, 12, 20, 20);
    [serchTapView setUserInteractionEnabled:YES];
    UITapGestureRecognizer *serchTapGRZ=[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(tapClick:)];
    [serchTapView addGestureRecognizer:serchTapGRZ];
    [self addSubview:serchTapView];
    [serchTapView addSubview:serchIMGV];

}

#pragma --mark 点击事件
-(void)tapClick:(UIGestureRecognizer*)GRZ{
    if (self.taPblock!=nil) {
        self.taPblock();
    }
}

-(void)leftClick{
    if (self.leftBlock!=nil) {
        self.leftBlock();
    }

}

#pragma --mark 回调
-(void)tapSearchViewWithBlock:(void (^)(void))block{
    self.taPblock=block;
}

-(void)tapLeftViewWithImageName:(NSString*)imageN tapBlock:(void(^)(void))block{
    self.leftBlock=block;
    UIImage* leftImage;
    //leftVIew
    if (imageN==nil) {
        leftImage=[UIImage imageNamed:@"comeBack"];
    }
    else{
        leftImage=[UIImage imageNamed:imageN];
    }
        UIView* leftTapView=[[UIView alloc]initWithFrame:CGRectMake(0, 20, 44, 44)];
        UIImageView* leftView=[[UIImageView alloc]initWithImage:leftImage];
        leftView.frame=CGRectMake(7, 12, 20, 20);
        [leftTapView setUserInteractionEnabled:YES];
//        [leftTapView addTapGestureRecognizerWithTarget:self action:@selector(leftClick)];
        [self addSubview:leftTapView];
        [leftTapView addSubview:leftView];
    
    
}
#pragma -mark 静态方法
+(instancetype)navigationViewWithTitle:(NSString *)title{
    HDNavigationView* hdView=[[HDNavigationView alloc]initWithTitle:title];
    return hdView;
}

@end
