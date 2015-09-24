//
//  noGoodsListTableViewCell.m
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "noGoodsListTableViewCell.h"
@interface noGoodsListTableViewCell()
@property (weak, nonatomic) IBOutlet UIButton* btn;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *priceWithRIghtLayout;
@end
@implementation noGoodsListTableViewCell

- (void)awakeFromNib {
    _btn.titleLabel.font=FONT14;
    [_btn setTitle:@"评价" forState:0];
    _btn.backgroundColor=[UIColor whiteColor];
    [_btn setTitleColor:[UIColor redColor] forState:0];
    [_btn.layer setMasksToBounds:YES];
    [_btn.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [_btn.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef scolorref=[UIColor redColor].CGColor;
    [_btn.layer setBorderColor:scolorref];
    [_btn addTarget:self action:@selector(submitClick:) forControlEvents:UIControlEventTouchUpInside];
    
}


//是否展示评价
-(void)showEvaluate:(BOOL)b{
    if (b==NO) {
//        self.priceWithRIghtLayout.constant=15;
//        [_btn setHidden:YES];
        self.priceWithRIghtLayout.constant=100;
        _btn.userInteractionEnabled = NO;
        [_btn setTitle:@"已评价" forState:UIControlStateNormal];
    }else{
        [_btn setTitle:@"评价" forState:UIControlStateNormal];
        _btn.userInteractionEnabled = YES;
        self.priceWithRIghtLayout.constant=100;
        [_btn setHidden:NO];
    }

    
}

-(void)hideEvaluate{
    self.priceWithRIghtLayout.constant=15;
    [_btn setHidden:YES];
}

-(void)submitClick:(UIButton*)bt{
    noGoodsListTableViewCell* cell=[UtilityMethod getTableViewCell:bt class:[noGoodsListTableViewCell class]];
    [self.viewController performSelector:self.subClick withObject:cell];
}
@end
