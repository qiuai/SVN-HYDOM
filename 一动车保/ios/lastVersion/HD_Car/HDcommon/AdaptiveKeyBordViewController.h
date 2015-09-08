//
//  AdaptiveKeyBordViewController.h
//  adaptive
//
//  Created by hydom on 15-7-17.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AdaptiveKeyBordViewController : UIViewController<UITextFieldDelegate,UITextViewDelegate>


/**
 *需要初始化
 */
@property (nonatomic,strong)UIScrollView * scrollView;
/**
 *textView的各类属性
 */
@property (nonatomic,assign)CGFloat textViewMinHeigth;

@property (nonatomic,assign)CGFloat textViewMaxHeigth;

@property (nonatomic,assign)CGFloat textViewWidth;

@property (nonatomic,assign)NSInteger textViewMaxLenght;
/**
 *返回TextView的文本高度
 */
-(CGFloat)updateTextViewFrame;
/**
 *键盘弹出和收回方法
 */
- (void)keyboardWasShown:(NSNotification *)notification;

- (void)keyboardWillBeHidden:(NSNotification *)notification;
/**
 *
 */
-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField;
/**
 *在这设置textview的各类属性，如若属性一样可只赋值一次
 */
-(BOOL)textViewShouldBeginEditing:(UITextView *)textView;
/**
 *输入结束后的方法
 */
-(void)offsetOfScrollViewEndEdting;

@end
