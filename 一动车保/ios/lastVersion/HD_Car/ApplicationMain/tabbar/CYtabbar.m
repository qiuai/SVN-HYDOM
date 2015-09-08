//
//  CYtabber.m
//  tabbarContainer
//
//  Created by cc on 15/5/9.
//  Copyright (c) 2015年 cc. All rights reserved.
//

#import "CYtabbar.h"
#import "CYtabberButton.h"
@interface CYtabbar()
#define AllbarCount 3.0
@property(nonatomic,copy)clickMethod didclick;
@property (nonatomic,weak)CYtabberButton* selectBtn;
/**上次选中*/
@property (nonatomic,weak)CYtabberButton* lastSelectBtn;
@end
@implementation CYtabbar

- (instancetype)init
{
    self = [super init];
    if (self) {
        [self addBarWithTitle:@"首页" nomalImage:@"tabbarHome_2" highlightedImage:@"tabbarHome_1" index:0];
        
        [self addBarWithTitle:@"发现" nomalImage:@"tabbarDiscover2" highlightedImage:@"tabbarDiscover1" index:1];
        [self addBarWithTitle:@"我的" nomalImage:@"tabbarCenter2" highlightedImage:@"tabbarCenter1" index:2];
        self.frame=CGRectMake(0, SCREEN_HEIGHT-49, SCREEN_WIDTH, 49);
        self.backgroundColor=ShareBackColor;
    }
    return self;
}

-(instancetype)initWithBlock:(clickMethod)clickM{
    self=[self init];
    self.didclick=clickM;
    [self clickBtn:[self.subviews firstObject]];
    return  self;
}



#pragma -mark 添加导航button
-(void)addBarWithTitle:(NSString*)title nomalImage:(NSString*)nomalImage highlightedImage:(NSString*)highlighImage index:(int)index{
    CYtabberButton *btn=[[CYtabberButton alloc]init];
    CGFloat btWidth=SCREEN_WIDTH/AllbarCount;
    NSInteger btW=round(btWidth);
    btn.frame=CGRectMake(index*btW, 0,btW , 49);
    [btn setNomalTitle:title highlightedTitle:title];
    [btn setNomalImage:[UIImage imageNamed:nomalImage] highlightedImage:[UIImage imageNamed:highlighImage]];
    [btn setNomalColor:[UIColor whiteColor] highlightedColor:ShareBackColor];
    btn.tag=index;
    [btn addTarget:self action:@selector(clickBtn:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:btn];
}

//点击事件
-(void)clickBtn:(CYtabberButton*)btn{
    //上次选中
    self.lastSelectBtn=self.selectBtn;
    
    [self.selectBtn changeState];
    self.selectBtn=btn;
    [btn  changeState];
    NSInteger i=btn.tag;
    self.didclick(i);
}
#pragma -mark  外部block调用
-(void)tabbarDidClickBlock:(clickMethod)clickB{
    self.didclick=clickB;
}

//静态方法
+(instancetype)tabbarInitWithClickBlock:(clickMethod)clickm{
    CYtabbar* tb=[[CYtabbar alloc]initWithBlock:clickm];
    return tb;
}

/**切换到上次选中*/
-(void)changeLastSelectTabbar{
    [self.lastSelectBtn  changeState];
    [self.selectBtn changeState];
    self.selectBtn=self.lastSelectBtn;
}



/**切换选中for tag*/
-(void)changeTabbarByTag:(NSInteger)tag{
    [self.selectBtn changeState];
    for (UIView* noView in self.subviews) {
        if (noView.tag==tag) {
            CYtabberButton* btn=(CYtabberButton*)noView;
            self.selectBtn=btn;
            [btn  changeState];
        }
    }
    
    
}
@end
