//
//  JVcancelOrderStateView.h
//  HD_Car
//
//  Created by xingso on 15/8/11.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVcancelOrderStateView.h"
@interface JVcancelOrderStateView(){

    CGFloat labeWith;
}
@property(nonatomic,strong)UIView* line;


@property(nonatomic,weak)UILabel* labelPainter;
@end
@implementation JVcancelOrderStateView

-(void)awakeFromNib{
     labeWith=SCREEN_WIDTH/4.0;
    CGFloat y=CGRectGetMaxY(self.label1.frame);
    self.line=[[UIView alloc]init];
   
    self.line.frame=CGRM(20, y, labeWith-40, 1);
    self.line.backgroundColor=ShareBackColor;
    [self addSubview:self.line];
    self.label1.tag=200+1;
    self.label2.tag=200+2;
    self.label3.tag=200+3;
    self.label4.tag=200+4;
    [self.label1 addTapGestureRecognizerWithTarget:self action:@selector(tapLabel:)];
    [self.label2 addTapGestureRecognizerWithTarget:self action:@selector(tapLabel:)];
    [self.label3 addTapGestureRecognizerWithTarget:self action:@selector(tapLabel:)];
    [self.label4 addTapGestureRecognizerWithTarget:self action:@selector(tapLabel:)];
    self.state=1;
    _label1.textColor=ShareBackColor;
    self.labelPainter=self.label1;
}

-(void)tapLabel:(UIGestureRecognizer*)gez{
    self.state=gez.view.tag-200;
    if (self.labelPainter!=nil) {
        self.labelPainter.textColor=BLACKCOLOR;
    }
    UILabel* lb=(UILabel*)gez.view;
    lb.textColor=ShareBackColor;
    self.labelPainter=lb;
    [UIView animateWithDuration:0.5 animations:^{
      CGFloat  x=(self.state-1)*labeWith+20;
        self.line.frame=CGRM(x, CGRectGetMaxY(self.label1.frame), labeWith-40, 1);
    }];
    
    [self.viewController performSelectorInBackground:self.tapMethod withObject:[NSNumber numberWithInteger:self.state]];
}



-(void)setArray:(NSArray *)array{
    _array=array;
    self.label1.text=array[0];
    self.label2.text=array[1];
    self.label3.text=array[2];
    self.label4.text=array[3];

}
@end
