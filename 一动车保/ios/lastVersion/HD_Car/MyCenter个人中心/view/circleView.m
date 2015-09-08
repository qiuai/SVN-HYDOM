//
//  circleView.m
//  HD_InsuranceCar
//
//  Created by cc on 15/6/18.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "circleView.h"
#import "UIImageView+WebCache.h"
@interface circleView()
@property(nonatomic,strong)UIImageView* img;
@end
@implementation circleView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.*/
- (void)drawRect:(CGRect)rect {
    
    CGFloat radius=rect.size.width/2.0;
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetRGBStrokeColor(context,1,1,1,1.0);//画笔线的颜色
    CGContextSetLineWidth(context, 2.0);//线的宽度
    CGContextAddArc(context, radius, radius, radius-3, 0, 2*M_PI, 0); //添加一个圆
    CGContextDrawPath(context, kCGPathStroke); //绘制路径
    
    UIImageView* img=[[UIImageView alloc]initWithFrame:CGRectMake(radius-35, radius-35, 70, 70)];
    
    //告诉layer将位于它之下的layer都遮盖住
    img.layer.masksToBounds = YES;
    
    //设置layer的圆角,刚好是自身宽度的一半，这样就成了圆形
    img.layer.cornerRadius = img.bounds.size.width * 0.5;
    
    //设置边框的宽度为
    img.layer.borderWidth = 1.0;
    
    //设置边框的颜色
    img.layer.borderColor = [UIColor whiteColor].CGColor;
   
    [self addSubview:img];
    
    self.img=img;
}

-(void)setURL:(NSURL*)url{
 [self.img sd_setImageWithURL:url placeholderImage:[UIImage imageNamed:FillImage]];
}



-(void)setImage:(UIImage*)image{
    self.img.image=image;
}


@end
