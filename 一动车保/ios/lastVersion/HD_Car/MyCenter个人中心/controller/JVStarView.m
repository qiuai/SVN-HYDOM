//
//  JVStarView.m
//  HD_Car
//
//  Created by xingso on 15/8/12.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVStarView.h"
@interface JVStarView()

@property(nonatomic,strong)NSMutableArray* array;
@end


@implementation JVStarView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(instancetype)initWithFrame:(CGRect)frame{
    self=[super initWithFrame:frame];
    
    
    self.array=[NSMutableArray array];
    
    CGFloat width=frame.size.width/5.0;
    
    
    CGFloat height=frame.size.height;
    
    for (NSInteger i=0; i<10; i++) {
        UIButton* bt=[[UIButton alloc]init];
        bt.frame=CGRM(i*width/2.0, 0, width/2.0, height);
        bt.tag=111+i;
//        bt.backgroundColor=randomColor;
        [bt addTarget:self action:@selector(click:) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:bt];
    }
    
    for (NSInteger i=0; i<5; i++) {
      UIImageView* grayImage=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"grayStar"]];
        grayImage.frame=CGRM(0, 0, 30, 30);
        grayImage.center=CGPointMake(i*width+width/2.0, height/2.0);
        [self addSubview:grayImage];
        [self.array addObject:grayImage];
    }
    return self;

}

-(void)click:(UIButton *)btn{
    
    NSInteger starCount=btn.tag-110;
    self.starCount=starCount;
    NSInteger count=starCount/2;
    NSInteger f=starCount%2;
    for (NSInteger j=0; j<5; j++) {
        UIImageView* image=self.array[j];
        image.image=[UIImage imageNamed:@"grayStar"];
    }
    
    for (NSInteger i=0; i<count; i++) {
        UIImageView* image=self.array[i];
        image.image=[UIImage imageNamed:@"redStar"];
    }
    if (f!=0) {
        UIImageView* image=self.array[count];
        image.image=[UIImage imageNamed:@"halfRedStar"];
    }
    

}

@end
