//
//  AdaptiveKeyBordViewController.m
//  adaptive
//
//  Created by hydom on 15-7-17.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//
#import "AdaptiveKeyBordViewController.h"

@interface AdaptiveKeyBordViewController ()
{
    CGFloat _keyBordHeigth;
    CGPoint offset;
}
@property (nonatomic,weak)UITextField * currentTextField;
@property (nonatomic,weak)UITextView * currentTextView;
@property (nonatomic,assign)CGFloat superViewMinYInScrollView;


@end

@implementation AdaptiveKeyBordViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.superViewMinYInScrollView = 0;
    // Do any additional setup after loading the view.
}
- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWasShown:)
                                                 name:UIKeyboardWillShowNotification object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillBeHidden:)
                                                 name:UIKeyboardWillHideNotification object:nil];
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}
#pragma mark----textField

-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField{
    _currentTextField = nil;
    _currentTextField = textField;
    CGRect rect = [self relativeFrameForScreenWithView:_currentTextField];
    self.superViewMinYInScrollView = CGRectGetMaxY(rect);
    offset = self.scrollView.contentOffset;
    [self upDateframe];
    return YES;
}
-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    [self.view endEditing:YES];
    return YES;
}
-(void)textFieldDidBeginEditing:(UITextField *)textField{
    [self upDateframe];
}
-(void)textFieldDidEndEditing:(UITextField *)textField{
    [self offsetOfScrollViewEndEdting];
//    self.scrollView.contentOffset = offset;
}
- (void)keyboardWasShown:(NSNotification *)notification{
    NSDictionary *userInfo = [notification userInfo];
    NSValue *aValue = [userInfo objectForKey:UIKeyboardFrameEndUserInfoKey];
    CGRect keyboardRect = [aValue CGRectValue];
    _keyBordHeigth = keyboardRect.size.height;
    
    [self upDateframe];
    
    
}
- (void)upDateframe{
    if (_currentTextField) {
        
        CGFloat temp  = _keyBordHeigth +self.superViewMinYInScrollView;
        temp = temp > (SCREEN_HEIGHT ) ?(temp  - SCREEN_HEIGHT):0;
        _scrollView.contentOffset = CGPointMake(0, temp);
    }else if (_currentTextView){
        
        CGFloat temp  = _keyBordHeigth +self.superViewMinYInScrollView;
        temp = temp > (SCREEN_HEIGHT ) ?(temp  - SCREEN_HEIGHT):0;
        _scrollView.contentOffset = CGPointMake(0, temp);
    }

}
- (void)keyboardWillBeHidden:(NSNotification *)notification{
    
    
}
#pragma mark-----textView
-(BOOL)textViewShouldBeginEditing:(UITextView *)textView{
    _currentTextView = nil;
    _currentTextView = textView;
    offset = self.scrollView.contentOffset;
    CGRect rect = [self relativeFrameForScreenWithView:_currentTextView];
    self.superViewMinYInScrollView = CGRectGetMaxY(rect);
    
#warning 注意layer层级关系textView自适应高度时可能会被覆盖
//    [self.view bringSubviewToFront:textView];
    return YES;
}
-(BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text{
    
    if ([text isEqualToString:@"\n"]) {
        [self.view endEditing:YES];
        return NO;
    }
    if ([text isEqualToString:@""]) {
        CGRect rect = [self relativeFrameForScreenWithView:textView];
        self.superViewMinYInScrollView = CGRectGetMaxY(rect);
        [self textViewHeigth:textView];
        return YES;
    }
    if (textView.text.length > self.textViewMaxLenght) {
        return NO;
    }
    CGRect rect = [self relativeFrameForScreenWithView:textView];
    self.superViewMinYInScrollView = CGRectGetMaxY(rect);
    [self textViewHeigth:textView];
    return YES;
}
-(void)textViewDidEndEditing:(UITextView *)textView{
    
//    self.scrollView.contentOffset = offset;
    [self offsetOfScrollViewEndEdting];

}
-(void)offsetOfScrollViewEndEdting{

//    self.scrollView.contentOffset = offset;//回到输入之前offset
//    self.scrollView.contentOffset = CGPointMake(0, 0);//回到顶点

}
- (void)textViewHeigth:(UITextView *)textView{

    
    [self updateTextViewFrame];
    CGFloat temp  = _keyBordHeigth + self.superViewMinYInScrollView;
    temp = temp > (SCREEN_HEIGHT ) ?(temp  - SCREEN_HEIGHT):0;
    _scrollView.contentOffset = CGPointMake(0, temp);
    
}

-(CGFloat)updateTextViewFrame{
#warning heigth 为textView的高度 请在下方设置textView的frame或者constant
    CGFloat heigth = [self getHeightToLongLabel:_currentTextView.text width:self.textViewWidth];
    heigth = heigth > self.textViewMinHeigth ? heigth : self.textViewMinHeigth;
    heigth = heigth > self.textViewMaxHeigth ? self.textViewMaxHeigth:heigth;
    
    return heigth;
}
-(CGFloat)getHeightToLongLabel:(NSString *)textstring width:(CGFloat)width {
    CGSize containSize = CGSizeMake(width, 2000);
    NSDictionary * descriptionDic = @{NSFontAttributeName:[UIFont systemFontOfSize:13]};
    CGRect labelRect = [textstring boundingRectWithSize:containSize options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:descriptionDic context:nil];
    return labelRect.size.height;
}
-(CGRect)relativeFrameForScreenWithView:(UIView *)v
{
    UIView *view = v;
    CGFloat x = .0;
    CGFloat y = .0;
    // root  uiwindow  superView ==nil
    while ([view superview])
    {
        x += view.frame.origin.x;
        y += view.frame.origin.y;
        view = view.superview;
//        if ([view isKindOfClass:[UIScrollView class]]) {
//            x += ((UIScrollView *) view).contentOffset.x;
//            y += ((UIScrollView *) view).contentOffset.y;
//        }
    }
    return CGRectMake(x, y, v.frame.size.width, v.frame.size.height);
}




@end
