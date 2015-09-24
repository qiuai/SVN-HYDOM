//
//  JVservicesOrderTableViewCell.m
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVservicesOrderTableViewCell.h"

@interface JVservicesOrderTableViewCell ()
@property (weak, nonatomic) IBOutlet UIButton *evalution;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *rightLayout;
@end

@implementation JVservicesOrderTableViewCell

- (void)awakeFromNib {
    // Initialization code
    _evalution.titleLabel.font=FONT14;
    [_evalution setTitle:@"评价" forState:0];
    _evalution.backgroundColor=[UIColor whiteColor];
    [_evalution setTitleColor:[UIColor redColor] forState:0];
    [_evalution.layer setMasksToBounds:YES];
    [_evalution.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [_evalution.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef scolorref=[UIColor redColor].CGColor;
    [_evalution.layer setBorderColor:scolorref];
    [_evalution addTarget:self action:@selector(productSubmitClick:) forControlEvents:UIControlEventTouchUpInside];
}
//是否展示评价
-(void)showEvaluate:(BOOL)b{
    if (b==NO) {
//        self.rightLayout.constant=30;
//        [_evalution setHidden:YES];
        self.rightLayout.constant=100;
        [_evalution setTitle:@"已评价" forState:UIControlStateNormal];
        _evalution.userInteractionEnabled = NO;
    }else{
        [_evalution setTitle:@"评价" forState:UIControlStateNormal];
        self.rightLayout.constant=100;
        _evalution.userInteractionEnabled = YES;
        [_evalution setHidden:NO];
    }
    
}

-(void)hideEvaluate{
    self.rightLayout.constant=30;
    [_evalution setHidden:YES];
}

#pragma -mark 商品评价
-(void)productSubmitClick:(UIButton*)bt{
    JVservicesOrderTableViewCell* cell=[UtilityMethod getTableViewCell:bt class:[JVservicesOrderTableViewCell class]];
    [self.viewController performSelector:self.productSubClick withObject:cell];
}

@end
