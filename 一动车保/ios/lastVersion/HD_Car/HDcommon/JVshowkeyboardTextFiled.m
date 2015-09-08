//
//  JVshowkeyboardTextFiled.m
//  UIViewConvertRect
//
//  Created by xingso on 15/7/17.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVshowkeyboardTextFiled.h"
@interface JVshowkeyboardTextFiled()
@property(strong,nonatomic)UIView* keyboardToolbar;

//@property(assign,nonatomic)CGFloat inWindowY;
@end
@implementation JVshowkeyboardTextFiled

-(UIView *)keyboardToolbar{
    if (!_keyboardToolbar) {
        _keyboardToolbar=[[UIView alloc]initWithFrame:CGRM(0, SCREEN_HEIGHT-200, SCREEN_WIDTH, 40)];
        _keyboardToolbar.hidden=YES;
        _keyboardToolbar.backgroundColor=COLOR(208, 213, 218, 1.0);
        UIButton* complateBtn=[[UIButton alloc]initWithFrame:CGRM(SCREEN_WIDTH-35-10, 0, 35, 40)];
        [complateBtn setTitle:@"完成" forState:0];
        [complateBtn setTitleColor:[UIColor blueColor] forState:0];
        complateBtn.titleLabel.font=FONT14;
        [complateBtn addTarget:self action:@selector(closeTheKeyboard)forControlEvents:UIControlEventTouchUpInside];
        [_keyboardToolbar addSubview:complateBtn];
        [[[UIApplication sharedApplication].windows lastObject] addSubview:_keyboardToolbar];
    }
    return _keyboardToolbar;
}


//-(id)initWithCoder:(NSCoder *)aDecoder{
//   self=[super initWithCoder:aDecoder];
//    [self InitializeNotification];
//    return self;
//}

-(instancetype)initWithFrame:(CGRect)frame{
    self=[super initWithFrame:frame];
    [self InitializeNotification];
    return self;
}
-(instancetype)init{
    self=[super init];
    [self InitializeNotification];
    return self;
}

-(void)awakeFromNib{
    [self InitializeNotification];
}



#pragma -mark 限制字符
-(void)setWordsMaxLenght:(NSInteger)wordsMaxLenght{
    _wordsMaxLenght=wordsMaxLenght;
    [self addTarget:self action:@selector(changeText) forControlEvents:UIControlEventEditingChanged];
}

-(void)changeText{
    if (self.text.length > _wordsMaxLenght) {
        self.text = [self.text substringToIndex:_wordsMaxLenght];
    }
}

#pragma -mark 添加通知
-(void)InitializeNotification{
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:nil];
}
#pragma -mark 键盘显示时
- (void)keyboardWillShow:(NSNotification *)notif {
    CGSize kbSize = [[notif.userInfo objectForKey:UIKeyboardFrameEndUserInfoKey] CGRectValue].size;//得到鍵盤的高度
    CGFloat kbHeight = kbSize.height;
    self.keyboardToolbar.y=SCREEN_HEIGHT-kbHeight-40;
    self.keyboardToolbar.hidden=NO;
    CGFloat viewY=[self relativeFrameForScreenWithView:self].origin.y;
    CGFloat viewMaxY=viewY+self.frame.size.height;
    CGFloat moveY=viewMaxY+kbHeight-[UIScreen mainScreen].bounds.size.height+40;
    if (moveY>0) {
        self.moveView.bounds=CGRectMake(0, moveY, self.moveView.frame.size.width, self.moveView.frame.size.height);
    }
}
#pragma -mark 键盘隐藏
- (void)keyboardWillHide:(NSNotification *)notif {
    self.keyboardToolbar.hidden=YES;
    [UIView animateWithDuration:0.25 animations:^{
            self.moveView.bounds=CGRectMake(0, 0, self.moveView.frame.size.width, self.moveView.frame.size.height);
    }];
}
#pragma -mark 点击关闭键盘
-(void)closeTheKeyboard{
    [[[UIApplication sharedApplication] keyWindow] endEditing:YES];
}


#pragma -mark 移除通知
-(void)removeFromSuperview{
    [self.keyboardToolbar removeFromSuperview];
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}


#pragma -mark 计算view 在 root window中的 rect
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
        if ([view isKindOfClass:[UIScrollView class]]) {
            x -= ((UIScrollView *) view).contentOffset.x;
            y -= ((UIScrollView *) view).contentOffset.y;
        }
    }
    return CGRectMake(x, y, v.frame.size.width, v.frame.size.height);
}



@end
