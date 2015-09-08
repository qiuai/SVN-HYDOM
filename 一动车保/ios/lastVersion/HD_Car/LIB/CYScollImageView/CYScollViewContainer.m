//
//  CYScollViewContainer.m
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/17.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "CYScollViewContainer.h"

#import "UIImageView+WebCache.h"

#define containerW self.view.size.width
#define containerH self.view.size.height
#define IMAGEVIEW_COUNT 3
@interface CYScollViewContainer ()<UIScrollViewDelegate>{
    UIScrollView *_scrollView;
    UIImageView *_leftImageView;
    UIImageView *_centerImageView;
    UIImageView *_rightImageView;
    UIPageControl *_pageControl;
    NSInteger _currentImageIndex;//当前图片索引
    NSInteger _imageCount;//图片总数
    
    NSInteger _leftClik;
    
    NSInteger _centerClik;
    
    NSInteger _rightClik;
}

@property(nonatomic,copy)NSArray* imageNameArray;
@property(nonatomic,assign)CGRect scollViewFrame;
@end

@implementation CYScollViewContainer

- (void)viewDidLoad {
    [super viewDidLoad];
    
}


//初始化方法
-(void)setImageArray:(NSArray*)array {
    
    self.scollViewFrame=self.view.frame;
    self.imageNameArray=array;
    
    if (self.imageNameArray.count<2) {
        [self imageOneMethod];
        return;
    }
    //图片总数
    _imageCount=_imageNameArray.count;
    //添加滚动控件
    [self addScrollView];
    //添加图片控件
    [self addImageViews];
    //添加分页控件
    [self addPageControl];
    //加载默认图片
    [self setDefaultImage];
}


#pragma -mark  图片小于2张的 时候
-(void)imageOneMethod{
    UIImageView* image=[[UIImageView alloc]initWithFrame:CGRM(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
    if (self.imageNameArray.count<1) {
        image.image=[UIImage imageNamed:FillImage];
        return;
    }
    
    if ([self.imageNameArray[0] isKindOfClass:[NSURL class]]){
        [image sd_setImageWithURL:self.imageNameArray[0] placeholderImage:[UIImage imageNamed:FillImage]];
    }else{
        image.image=[UIImage imageNamed:self.imageNameArray[0]];
    }
    image.tag=99;
    [image addTapGestureRecognizerWithTarget:self action:@selector(tapWithIndex:)];
    [self.view addSubview:image];
    
}

#pragma -mark 点击事件
-(void)tapWithIndex:(UIGestureRecognizer*)gez{
    if (gez.view.tag==99) {
        if (self.clickBlock!=nil) {
            self.clickBlock(0);
        }
        return;
    }
    NSInteger it=gez.view.tag-80;
    NSInteger clickIndex;
    switch (it) {
        case 0:
            clickIndex=_leftClik;
            break;
        case 1:
            clickIndex=_centerClik;
            break;
        default:
            clickIndex=_rightClik;
            break;
    }
    if (self.clickBlock!=nil) {
        self.clickBlock(clickIndex);
    }
    return;
    
    
    
}

#pragma mark 添加控件
-(void)addScrollView{
    _scrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, containerW, containerH)];
    [self.view addSubview:_scrollView];
    //设置代理
    _scrollView.delegate=self;
    //设置contentSize
    _scrollView.contentSize=CGSizeMake(IMAGEVIEW_COUNT*containerW, containerH) ;
    //设置当前显示的位置为中间图片
    [_scrollView setContentOffset:CGPointMake(containerW, 0) animated:NO];
    //设置分页
    _scrollView.pagingEnabled=YES;
    //去掉滚动条
    _scrollView.showsHorizontalScrollIndicator=NO;
}

#pragma mark 添加图片三个控件
-(void)addImageViews{
    _leftImageView=[[UIImageView alloc]initWithFrame:CGRectMake(0, 0, containerW, containerH)];
    _leftImageView.tag=80;
    [_leftImageView addTapGestureRecognizerWithTarget:self action:@selector(tapWithIndex:)];
    //    _leftImageView.contentMode=UIViewContentModeScaleAspectFit;
    [_scrollView addSubview:_leftImageView];
    _centerImageView=[[UIImageView alloc]initWithFrame:CGRectMake(containerW, 0, containerW, containerH)];
    _centerImageView.tag=81;
    [_centerImageView addTapGestureRecognizerWithTarget:self action:@selector(tapWithIndex:)];
    //    _centerImageView.contentMode=UIViewContentModeScaleAspectFit;
    [_scrollView addSubview:_centerImageView];
    _rightImageView=[[UIImageView alloc]initWithFrame:CGRectMake(2*containerW, 0, containerW, containerH)];
    _rightImageView.tag=82;
    [_rightImageView addTapGestureRecognizerWithTarget:self action:@selector(tapWithIndex:)];
    //    _rightImageView.contentMode=UIViewContentModeScaleAspectFit;
    [_scrollView addSubview:_rightImageView];
    
}
#pragma mark 设置默认显示图片
-(void)setDefaultImage{
    //加载默认图片
    NSInteger lastImage=_imageCount-1;
    _rightClik=1;
    _centerClik=0;
    _leftClik=lastImage;
    if ([self.imageNameArray[lastImage] isKindOfClass:[NSURL class]]) {
        [_leftImageView sd_setImageWithURL:self.imageNameArray[lastImage] placeholderImage:[UIImage imageNamed:FillImage ]];
        [_centerImageView sd_setImageWithURL:self.imageNameArray[0] placeholderImage:[UIImage imageNamed:FillImage ]];
        [_rightImageView sd_setImageWithURL:self.imageNameArray[1] placeholderImage:[UIImage imageNamed:FillImage ]];
    }else{
        _leftImageView.image=[UIImage imageNamed:self.imageNameArray[lastImage]];
        _centerImageView.image=[UIImage imageNamed:self.imageNameArray[0]];
        _rightImageView.image=[UIImage imageNamed:self.imageNameArray[1]];
        _currentImageIndex=0;
        //设置当前页
        _pageControl.currentPage=_currentImageIndex;
    }
}

#pragma mark 添加分页控件
-(void)addPageControl{
    _pageControl=[[UIPageControl alloc]init];
    //注意此方法可以根据页数返回UIPageControl合适的大小
    CGSize size= [_pageControl sizeForNumberOfPages:_imageCount];
    _pageControl.bounds=CGRectMake(0, 0, size.width, size.height);
    _pageControl.center=CGPointMake(containerW/2, containerH-10);
    //设置颜色
    _pageControl.pageIndicatorTintColor=[UIColor colorWithRed:193/255.0 green:219/255.0 blue:249/255.0 alpha:1];
    //设置当前页颜色
    _pageControl.currentPageIndicatorTintColor=[UIColor colorWithRed:0 green:150/255.0 blue:1 alpha:1];
    //设置总页数
    _pageControl.numberOfPages=_imageCount;
    
    [self.view addSubview:_pageControl];
}
#pragma mark 滚动停止事件
-(void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView{
    //重新加载图片
    [self reloadImage];
    //移动到中间
    [_scrollView setContentOffset:CGPointMake(containerW, 0) animated:NO];
    //设置分页
    _pageControl.currentPage=_currentImageIndex;
}

#pragma mark 重新加载图片
-(void)reloadImage{
    NSInteger leftImageIndex,rightImageIndex;
    CGPoint offset=[_scrollView contentOffset];
    if (offset.x>containerW) { //向右滑动
        _currentImageIndex=(_currentImageIndex+1)%_imageCount;
    }else if(offset.x<containerW){ //向左滑动
        _currentImageIndex=(_currentImageIndex+_imageCount-1)%_imageCount;
    }
    _centerClik=_currentImageIndex;
    _leftClik=leftImageIndex;
    _rightClik=rightImageIndex;
    if ([self.imageNameArray[0] isKindOfClass:[NSURL class]]) {
        [_centerImageView sd_setImageWithURL:self.imageNameArray[_currentImageIndex] placeholderImage:[UIImage imageNamed:FillImage ]];
        //重新设置左右图片
        leftImageIndex=(_currentImageIndex+_imageCount-1)%_imageCount;
        rightImageIndex=(_currentImageIndex+1)%_imageCount;
        [_leftImageView sd_setImageWithURL:self.imageNameArray[leftImageIndex] placeholderImage:[UIImage imageNamed:FillImage ]];
        [_rightImageView sd_setImageWithURL:self.imageNameArray[rightImageIndex] placeholderImage:[UIImage imageNamed:FillImage ]];
    }else{
        
        
        _centerImageView.image=[UIImage imageNamed:self.imageNameArray[_currentImageIndex]];
        //重新设置左右图片
        leftImageIndex=(_currentImageIndex+_imageCount-1)%_imageCount;
        rightImageIndex=(_currentImageIndex+1)%_imageCount;
        _leftImageView.image=[UIImage imageNamed:self.imageNameArray[leftImageIndex]];
        _rightImageView.image=[UIImage imageNamed:self.imageNameArray[rightImageIndex]];
        
        
    }
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}



@end
