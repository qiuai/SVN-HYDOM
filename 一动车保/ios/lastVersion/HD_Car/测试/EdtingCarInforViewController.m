//
//  EdtingCarInforViewController.m
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import "EdtingCarInforViewController.h"
#import "EdtingCarsInforView.h"
#import "HDNavigationView.h"

@interface EdtingCarInforViewController ()<UITextFieldDelegate>
{
    CGFloat _keyBordHeigth;
}
@property (nonatomic,strong)EdtingCarsInforView *edtingView;
@property (nonatomic,strong)HDNavigationView * nav;
@property (nonatomic,strong)UIScrollView *scrollView;
@property (nonatomic,weak)UITextField * currentTextField;
@end

@implementation EdtingCarInforViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = COLOR(220, 220, 220, 1);
    _nav = [HDNavigationView navigationViewWithTitle:@"车管家"];
    [self.view addSubview:_nav];
    [_nav tapLeftViewWithImageName:@"comeBack.png" tapBlock:^{
        [self.navigationController popViewControllerAnimated:YES];
    }];
    [self setUpUserInterface];
    // Do any additional setup after loading the view.
}
- (void)setUpUserInterface{
    _scrollView = [[UIScrollView alloc] initWithFrame:CGRM(0, 64, SCREEN_WIDTH, SCREEN_HEIGTHT-64)];
    _scrollView.backgroundColor = self.view.backgroundColor;
    _scrollView.showsHorizontalScrollIndicator = NO;
    _scrollView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:_scrollView];
    
    _edtingView = [[[NSBundle mainBundle]loadNibNamed:@"EdtingCarsInforView" owner:self options:nil]lastObject];
    _edtingView.pushVC = self;
    [_edtingView setUpUserInterfaceWith:_model];
    _edtingView.carRunTextField.delegate = self;
    _edtingView.carOutPutTextField.delegate = self;
    _edtingView.carOilTextField.delegate = self;
    _edtingView.startTimeTextField.delegate = self;
    _edtingView.frame = CGRectMake(0, 0,SCREEN_WIDTH , 305);
    _edtingView.saveButton.backgroundColor = ShareBackColor;
    [_scrollView addSubview:_edtingView];
}
#pragma ViewWillAppear
- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWasShown:)
                                                 name:UIKeyboardWillShowNotification object:nil];
    
    //使用NSNotificationCenter 鍵盤隐藏時
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillBeHidden:)
                                                 name:UIKeyboardWillHideNotification object:nil];
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}
-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField{
    _currentTextField = textField;
    return YES;
}
-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    [self.view endEditing:YES];
    return YES;
}
-(void)textFieldDidEndEditing:(UITextField *)textField{
    _currentTextField = nil;
    _scrollView.contentOffset = CGPointMake(0, 0);
}
- (void)keyboardWasShown:(NSNotification *)notification{
    NSDictionary *userInfo = [notification userInfo];
    NSValue *aValue = [userInfo objectForKey:UIKeyboardFrameEndUserInfoKey];
    CGRect keyboardRect = [aValue CGRectValue];
    _keyBordHeigth = keyboardRect.size.height;
    CGFloat temp  = CGRectGetMaxY(_currentTextField.superview.frame)+_keyBordHeigth;
    temp = temp > (SCREEN_HEIGTHT- 64 ) ?(temp  + 64- SCREEN_HEIGTHT):0;
    _scrollView.contentOffset = CGPointMake(0, temp);

}
- (void)keyboardWillBeHidden:(NSNotification *)notification{
    
    
}



@end
