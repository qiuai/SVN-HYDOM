//
//  CarDetailInforView.m
//  HD_Car
//
//  Created by hydom on 15-7-20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "CarDetailInforView.h"
@interface CarDetailInforView()
@property (weak, nonatomic) IBOutlet UILabel *productTItle;
@property (weak, nonatomic) IBOutlet UILabel *productPrice;
@property (weak, nonatomic) IBOutlet UILabel *productPinlun;

@end
@implementation CarDetailInforView
-(void)awakeFromNib{
    
    self.buyNum = 1;
    for (NSInteger tag = 40; tag < 47; tag ++) {
        UIView * lineView = (UIView *)[self viewWithTag:tag];
        [self addline:lineView];
    }
    
}
/**添加虚线*/
-(void)addline:(UIView*)view{
    UIImage* backgroundImage=[UIImage imageNamed:@"HDxuxian"];
    UIColor *backgroundColor = [UIColor colorWithPatternImage:backgroundImage];
    view.backgroundColor=backgroundColor;
}


- (void)buildViewWith:(NSString *)string x:(CGFloat)x y:(CGFloat)y{
    
    UIView * view = [[UIView alloc] initWithFrame:CGRectMake(x, y, 80, 25)];
    UIImageView * imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 25, 25)];
    [imageView setImage:[UIImage imageNamed:@"gougou"]];
    [view addSubview:imageView];
    UILabel * label = [[UILabel alloc] initWithFrame:CGRectMake(25, 0, 55, 25)];
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
            x = 10 +kj * (SCREEN_WIDTH - 260)/2 + kj * 80;
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
    
    self.productPinlun.text=[NSString stringWithFormat:@"%@",self.pingjia];
}

//点击评论按钮触发
- (IBAction)pressEvaluateButton:(id)sender {
    
    
}
- (IBAction)pressParameterButton:(id)sender {
    
    if (!self.parameterView) {
        
        self.parameterView = [[ParameterDetailsView alloc] initWith:self.parameter];
        self.parameterView.frame = CGRectMake(0, CGRectGetMaxY(self.line6View.frame), SCREEN_WIDTH, self.parameterView.ParameterHeigth);
        [self addSubview:self.parameterView];
        self.ImageTextLabelConstraint.constant += self.parameterView.ParameterHeigth;
        self.imageTextButtonConstraint.constant += self.parameterView.ParameterHeigth;
        
    }else{
        
        self.parameterView.hidden = !self.parameterView.hidden;
        if (self.parameterView.hidden == NO) {
            self.ImageTextLabelConstraint.constant += self.parameterView.ParameterHeigth;
            self.imageTextButtonConstraint.constant += self.parameterView.ParameterHeigth;
        }else{
            self.ImageTextLabelConstraint.constant -= self.parameterView.ParameterHeigth;
            self.imageTextButtonConstraint.constant -= self.parameterView.ParameterHeigth;
        }
    }
}
//点击图文详情按钮触发
- (IBAction)pressImageTextButton:(id)sender {
    
    
}


@end
