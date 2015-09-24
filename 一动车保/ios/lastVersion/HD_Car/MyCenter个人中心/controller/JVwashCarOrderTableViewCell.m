//
//  JVwashCarOrderTableViewCell.m
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVwashCarOrderTableViewCell.h"

@implementation JVwashCarOrderTableViewCell

- (void)awakeFromNib {
    
    _eBtn.titleLabel.font=FONT14;
    [_eBtn setTitle:@"评价" forState:0];
    _eBtn.backgroundColor=[UIColor whiteColor];
    [_eBtn setTitleColor:[UIColor redColor] forState:0];
    [_eBtn.layer setMasksToBounds:YES];
    [_eBtn.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [_eBtn.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef scolorref=[UIColor redColor].CGColor;
    [_eBtn.layer setBorderColor:scolorref];
    [_eBtn addTarget:self action:@selector(submitClick:) forControlEvents:UIControlEventTouchUpInside];
    
}

//是否展示评价
-(void)showEvaluate:(BOOL)b{
    if (b==NO) {
        [_eBtn setTitle:@"已评价" forState:UIControlStateNormal];
        _eBtn.userInteractionEnabled = NO;
//        [_eBtn setHidden:YES];
    }else{
        [_eBtn setTitle:@"评价" forState:UIControlStateNormal];
        _eBtn.userInteractionEnabled = YES;
        [_eBtn setHidden:NO];
    }
    
}

-(void)hideEvaluate{
    [_eBtn setHidden:YES];
}

#pragma -mark 服务评价
-(void)submitClick:(UIButton*)bt{
    JVwashCarOrderTableViewCell* cell=[UtilityMethod getTableViewCell:bt class:[JVwashCarOrderTableViewCell class]];
    [self.viewController performSelector:self.subClick withObject:cell];
}

@end
