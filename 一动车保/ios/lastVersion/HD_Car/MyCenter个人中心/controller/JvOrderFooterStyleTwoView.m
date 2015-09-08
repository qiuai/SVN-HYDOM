//
//  JvOrderFooterStyleTwoView.m
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JvOrderFooterStyleTwoView.h"
@interface JvOrderFooterStyleTwoView()
@property(nonatomic,strong)UIButton* btn;
@end
@implementation JvOrderFooterStyleTwoView

-(UIButton *)btn{
    if (!_btn) {
        _btn=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH-20-65,7,65,20)];
        _btn.titleLabel.font=FONT14;
        [_btn setTitle:@"error" forState:0];
        _btn.backgroundColor=[UIColor whiteColor];
        [_btn setTitleColor:[UIColor redColor] forState:0];
        [_btn.layer setMasksToBounds:YES];
        [_btn.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
        [_btn.layer setBorderWidth:1.0]; //边框宽度
        CGColorRef scolorref=[UIColor redColor].CGColor;
        [_btn.layer setBorderColor:scolorref];
        [self.addsubView addSubview:_btn];
        [_btn addTarget:self action:@selector(submitClick:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _btn;
}

-(void)awakeFromNib{


}

-(void)setViewStyle:(NSInteger)viewStyle{
    _viewStyle=viewStyle;
    
    switch (viewStyle) {
        case 1:
            [self.btn setTitle:@"取消订单" forState:0];
            break;
        case 2:
            [self.btn setTitle:@"确认收货" forState:0];
            break;
        default:
            break;
    }
}

//点击事件
-(void)submitClick:(UIButton*)btn{
//发送给外部
    NSDictionary* dict=[[NSDictionary alloc]initWithObjects:@[[NSNumber numberWithInteger:self.tag],[NSNumber numberWithInteger:self.viewStyle]] forKeys:@[@"tag",@"style"]];
    [self.viewController performSelector:self.subClick withObject:dict];
}

@end
