//
//  scollChangeViewController.m
//  HD_InsuranceCar
//
//  Created by cc on 15/6/17.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "scollChangeViewController.h"
#define labelColor [UIColor whiteColor]
@interface scollChangeViewController ()<UIScrollViewDelegate>
{
    CGFloat labelCount;
    
    CGFloat labelWidth;
    
    CGFloat labelHeight;
    
    CGFloat viewHeight;
    
}
@property(nonatomic,strong)UIScrollView* labelScrollView;

@property(nonatomic,strong)UIScrollView* viewScrollView;
@end

@implementation scollChangeViewController

#pragma -mark 懒加载
-(UIScrollView *)labelScrollView{
    if (!_labelScrollView) {
        _labelScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, labelHeight)];
        _labelScrollView.tag=100;
        _labelScrollView.contentSize=CGSizeMake(labelWidth*labelCount, labelHeight);
        _labelScrollView.delegate=self;
        for (NSInteger i=0; i<labelCount; i++) {
            UIButton* labelBT=[[UIButton alloc]initWithFrame:CGRectMake(i*labelWidth, 0, labelWidth, labelHeight)];
            [labelBT setNomalColor:[UIColor whiteColor] highlightedColor:[UIColor whiteColor]];
            [labelBT setTitle:self.titleArray[i] forState:UIControlStateNormal];
            labelBT.titleLabel.font=FONT12;
            [labelBT addTarget:self action:@selector(click:) forControlEvents:UIControlEventTouchUpInside];
            labelBT.tag=i;
            [_labelScrollView addSubview:labelBT];
        }
        
        _labelScrollView.backgroundColor=[UIColor whiteColor];
    }
    return _labelScrollView;
}
-(UIScrollView *)viewScrollView{
    if (!_viewScrollView) {
        _viewScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, labelHeight, SCREEN_WIDTH, viewHeight)];
        _viewScrollView.tag=200;
        _viewScrollView.contentSize=CGSizeMake(SCREEN_WIDTH*labelCount, viewHeight);
        _viewScrollView.delegate=self;
        _viewScrollView.backgroundColor=randomColor;
        for (NSInteger i=0; i<labelCount; i++) {
            UIView* view=self.viewArray[i];
            view.frame=CGRectMake(i*SCREEN_WIDTH, labelHeight, SCREEN_WIDTH, viewHeight);
            [_viewScrollView addSubview:view];
        }
    }
    return _viewScrollView;

}


#pragma -mark init
- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.frame=self.thisFrame;
    //初始化变量
    labelCount=self.titleArray.count;
    labelWidth=SCREEN_WIDTH/3.0;
    labelHeight=35.0;
    viewHeight=self.view.frame.size.height-labelHeight;
    
    
    [self.view addSubview:self.labelScrollView];
    [self.view addSubview:self.viewScrollView];
}


-(void)click:(UIButton*)btn{

    self.viewScrollView.contentOffset=CGPointMake(btn.tag*SCREEN_WIDTH, 0);

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
