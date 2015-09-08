//
//  CarDetailInforView.m
//  HD_Car
//
//  Created by hydom on 15-7-20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "YC_CarDetailInforView.h"
@interface YC_CarDetailInforView()
@property (weak, nonatomic) IBOutlet UILabel *productTItle;
@property (weak, nonatomic) IBOutlet UILabel *productPrice;
@property (weak, nonatomic) IBOutlet UILabel *productPinlun;

@end
@implementation YC_CarDetailInforView
-(void)awakeFromNib{
    
    //更具传入参数判断  fixcarView的显示
    self.buyNum = 1;
    for (NSInteger tag = 40; tag < 49; tag ++) {
        UIView * lineView = (UIView *)[self viewWithTag:tag];
        [self addline:lineView];
    }
    
    self.addButton.layer.borderWidth = 1;
    self.addButton.layer.cornerRadius = 13;
    self.addButton.layer.borderColor = [UIColor lightGrayColor].CGColor;
    
    self.subButton.layer.borderWidth = 1;
    self.subButton.layer.cornerRadius = 13;
    self.subButton.layer.borderColor = [UIColor lightGrayColor].CGColor;
    
}
/**添加虚线*/
-(void)addline:(UIView*)view{
    UIImage* backgroundImage=[UIImage imageNamed:@"HDxuxian"];
    UIColor *backgroundColor = [UIColor colorWithPatternImage:backgroundImage];
    view.backgroundColor=backgroundColor;
}


//隐藏适配车型栏
-(void)hideFitCar:(BOOL)fitCar
{
    if (fitCar) {
        self.fixCarView.hidden = YES;
    }
}

- (void)buildViewWith:(NSString *)string x:(CGFloat)x y:(CGFloat)y{
    
    UIView * view = [[UIView alloc] initWithFrame:CGRectMake(x, y, 120, 25)];
    UIImageView * imageView = [[UIImageView alloc] initWithFrame:CGRectMake(5, 5, 15, 15)];
    [imageView setImage:[UIImage imageNamed:@"gougou"]];
    [view addSubview:imageView];
    UILabel * label = [[UILabel alloc] initWithFrame:CGRectMake(25, 0, 95, 25)];
    label.text = string;
    label.font = [UIFont systemFontOfSize:13];
    [view addSubview:label];
    [self.inforView addSubview:view];
}
- (void)setUpUserInterface{
    NSArray * array = self.servicesArray;
    NSInteger temp = 0;
    if (array.count < 4) {
        self.inforViewHeight.constant = 35;
    }
    for (NSDictionary * strDic in array) {
        CGFloat x = 0;
        CGFloat y = 0;
        if (temp == 0) {
            x = 10;
        }else{
            NSInteger kj=temp%3;
            x = 10 +kj * (SCREEN_WIDTH - 260)/2 + kj * 120;
        }
        if (temp < 3) {
            y = 5;
        }else{
            y = 35;
        }
        [self buildViewWith:strDic[@"suname"] x:x y:y];
        temp ++;
    }
    self.productTItle.text=self.goodsTitle;
    self.productPrice.text=[UtilityMethod addRMB:[NSString stringWithFormat:@"%@",self.goodsPrices]];
    self.productPinlun.text=[NSString stringWithFormat:@"（%@）",self.pingjia];
}

//点击评论按钮触发
- (IBAction)pressEvaluateButton:(id)sender {
    YC_EvaluateViewController * vc = [[YC_EvaluateViewController alloc] init];
    vc.pID = self.vc.productID;
    [self.vc.navigationController pushViewController:vc animated:YES];

}
//点击参数详情按钮
- (IBAction)pressParameterButton:(id)sender {
    
    if (!self.parameterView) {
        self.parameterView = [[ParameterDetailsView alloc] initWith:self.parameter];
        self.parameterView.frame = CGRectMake(0, CGRectGetMaxY(self.line6View.frame), SCREEN_WIDTH, self.parameterView.ParameterHeigth);
        [self addSubview:self.parameterView];
        self.ImageTextLabelConstraint.constant += self.parameterView.ParameterHeigth;
        self.imageTextButtonConstraint.constant += self.parameterView.ParameterHeigth;
        self.contentSizeBlock(self.parameterView.ParameterHeigth);
    }else{
        
        self.parameterView.hidden = !self.parameterView.hidden;
        if (self.parameterView.hidden == NO) {
            self.ImageTextLabelConstraint.constant += self.parameterView.ParameterHeigth;
            self.imageTextButtonConstraint.constant += self.parameterView.ParameterHeigth;
            self.contentSizeBlock(self.parameterView.ParameterHeigth);
        }else{
            self.ImageTextLabelConstraint.constant -= self.parameterView.ParameterHeigth;
            self.imageTextButtonConstraint.constant -= self.parameterView.ParameterHeigth;
            self.contentSizeBlock(-self.parameterView.ParameterHeigth);
        }
    }
}
//点击图文详情按钮触发
- (IBAction)pressImageTextButton:(id)sender {
    YC_webViewViewController * vc = [[YC_webViewViewController alloc] init];
    vc.urlString = self.purl;
    [self.vc.navigationController pushViewController:vc animated:YES];
}

//点击适配车型按钮
- (IBAction)pressFitCarButton:(UIButton *)sender {
    if (!self.fitCarView) {
        self.fitCarView = [[ParameterDetailsView alloc] initWithFitCar:self.fitCarArray];
        self.fitCarView.frame = CGRectMake(0, CGRectGetMaxY(self.line8View.frame), SCREEN_WIDTH, self.fitCarView.fitCarHeigth);
        [self.fixCarView addSubview:self.fitCarView];
        self.fitCarViewConstraint.constant += self.fitCarView.fitCarHeigth;
        self.contentSizeBlock(self.fitCarView.fitCarHeigth);
    }else{
        
        self.fitCarView.hidden = !self.fitCarView.hidden;
        if (self.fitCarView.hidden == NO) {
            self.fitCarViewConstraint.constant += self.fitCarView.fitCarHeigth;
            self.contentSizeBlock(self.fitCarView.fitCarHeigth);
        }else{
            self.fitCarViewConstraint.constant -= self.fitCarView.fitCarHeigth;
            self.contentSizeBlock(-self.fitCarView.fitCarHeigth);
        }
    }

}

//增加购买Button
- (IBAction)pressAddButton:(UIButton *)sender {
    if (self.buyNum < 1000) {
        self.buyNum ++;
        self.buyNubLabel.text = [NSString stringWithFormat:@"%ld", (long)self.buyNum];
        float price = [self.goodsPrices floatValue] * self.buyNum;
        [self.vc changePayLabel:[NSString stringWithFormat:@"%.2f", price] number:self.buyNum];
    }
}

//减少购买Button
- (IBAction)pressSubButton:(UIButton *)sender {
    if (self.buyNum > 1) {
        self.buyNum --;
        self.buyNubLabel.text = [NSString stringWithFormat:@"%ld", (long)self.buyNum];
        float price = [self.goodsPrices floatValue] * self.buyNum;
        [self.vc changePayLabel:[NSString stringWithFormat:@"%.2f", price] number:self.buyNum];
    }
}


@end
