//
//  firstServicesTableViewCell.m
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "firstServicesTableViewCell.h"
@interface firstServicesTableViewCell()
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *rightLayout;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *numberRightLayout;
@property (weak, nonatomic) IBOutlet UIView *bumVIew;
//评论btn
@property (weak, nonatomic) IBOutlet UIButton *evaluationBT;
//商品评价
@property (weak, nonatomic) IBOutlet UIButton *productEvaluation;

@end
@implementation firstServicesTableViewCell

- (void)awakeFromNib {
    
    _evaluationBT.titleLabel.font=FONT14;
    [_evaluationBT setTitle:@"评价" forState:0];
    _evaluationBT.backgroundColor=[UIColor whiteColor];
    [_evaluationBT setTitleColor:[UIColor redColor] forState:0];
    [_evaluationBT.layer setMasksToBounds:YES];
    [_evaluationBT.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [_evaluationBT.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef scolorref=[UIColor redColor].CGColor;
    [_evaluationBT.layer setBorderColor:scolorref];
    [_evaluationBT addTarget:self action:@selector(submitClick:) forControlEvents:UIControlEventTouchUpInside];
    
    _productEvaluation.titleLabel.font=FONT14;
    [_productEvaluation setTitle:@"评价" forState:0];
    _productEvaluation.backgroundColor=[UIColor whiteColor];
    [_productEvaluation setTitleColor:[UIColor redColor] forState:0];
    [_productEvaluation.layer setMasksToBounds:YES];
    [_productEvaluation.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [_productEvaluation.layer setBorderWidth:1.0]; //边框宽度
    [_productEvaluation.layer setBorderColor:scolorref];
    [_productEvaluation addTarget:self action:@selector(productSubmitClick:) forControlEvents:UIControlEventTouchUpInside];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}


-(void)hideEvaluate{
    self.numberRightLayout.constant=30;
    self.rightLayout.constant=15;
    [_evaluationBT setHidden:YES];
    [_productEvaluation setHidden:YES];

}


//是否展示评价
-(void)showEvaluate:(BOOL)b{
    if (b==NO) {
        [_evaluationBT setTitle:@"已评价" forState:UIControlStateNormal];
        _evaluationBT.userInteractionEnabled = NO;
        self.rightLayout.constant=100;
//        self.rightLayout.constant=15;
        [_evaluationBT setHidden:NO];
        [_productEvaluation setHidden:NO];
    }else{
        [_evaluationBT setTitle:@"评价" forState:UIControlStateNormal];
        _evaluationBT.userInteractionEnabled = YES;
        self.rightLayout.constant=100;
        [_evaluationBT setHidden:NO];
        [_productEvaluation setHidden:NO];
    }

}


//是否展示商品评价服务
-(void)showProductEvaluate:(BOOL)b{
    if (b==NO) {
        [_evaluationBT setTitle:@"已评价" forState:UIControlStateNormal];
        _evaluationBT.userInteractionEnabled = NO;
        self.numberRightLayout.constant=100;
//        self.numberRightLayout.constant=30;
        [_evaluationBT setHidden:NO];
        [_productEvaluation setHidden:NO];
    }else{
        [_evaluationBT setTitle:@"评价" forState:UIControlStateNormal];
        _evaluationBT.userInteractionEnabled = YES;
        self.numberRightLayout.constant=100;
        [_evaluationBT setHidden:NO];
        [_productEvaluation setHidden:NO];
    }

}


#pragma -mark 服务评价
-(void)submitClick:(UIButton*)bt{
    firstServicesTableViewCell* cell=[UtilityMethod getTableViewCell:bt class:[firstServicesTableViewCell class]];
    [self.viewController performSelector:self.subClick withObject:cell];
}

#pragma -mark 商品评价
-(void)productSubmitClick:(UIButton*)bt{
    firstServicesTableViewCell* cell=[UtilityMethod getTableViewCell:bt class:[firstServicesTableViewCell class]];
    [self.viewController performSelector:self.productSubClick withObject:cell];
}

@end
