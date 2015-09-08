//
//  JVorderImportantView.m
//  HD_Car
//
//  Created by xingso on 15/7/31.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVorderImportantView.h"

@implementation JVorderImportantView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(void)awakeFromNib{
    [self drawBorder];
}

#pragma -mark 画边框线
-(void)drawBorder{

    CGFloat padding=10;
//    有多少个
    NSInteger count=11;
    //颜色
    UIColor* color1=COLOR(93, 175, 218, 1);
    UIColor* color2=[UIColor redColor];
    
    //宽度
    CGFloat lineWidth=(SCREEN_WIDTH-12*padding)/11.0;
    for (NSInteger i=0; i<count; i++) {
        UIView* topView=[UIView new];
        UIView* bottomView=[UIView new];
        bottomView.frame=CGRM((i+1)*padding+i*lineWidth, 130-8-3,lineWidth, 3);
        topView.frame=CGRM((i+1)*padding+i*lineWidth, 8,lineWidth, 3);
        NSInteger j=i%2;
        if (j==0) {
            topView.backgroundColor=color1;
            bottomView.backgroundColor=color1;
        }
        else{
            topView.backgroundColor=color2;
            bottomView.backgroundColor=color2;
        }
        [self addSubview:topView];
        [self addSubview:bottomView];
    }
    
    
    


}
@end
