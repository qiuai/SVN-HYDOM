//
//  JVshowKeyboardManger.m
//  HD_Car
//
//  Created by xingso on 15/9/11.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVshowKeyboardManger.h"
static JVshowKeyboardManger *_sharedClass;

@interface JVshowKeyboardManger()
@property(strong,nonatomic)UIView* keyboardToolbar;
@property(assign,nonatomic) CGFloat viewY;


// 传递对象  for 遮挡键盘 处理
@property(nonatomic,weak)JVshowkeyboardTextFiled* textFiled;

@end
@implementation JVshowKeyboardManger

-(UIView *)keyboardToolbar{
    if (!_keyboardToolbar) {
        _keyboardToolbar=[[UIView alloc]initWithFrame:CGRM(0, SCREEN_HEIGHT, SCREEN_WIDTH, 40)];
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



+(instancetype)shareClass{
    static dispatch_once_t token;
    dispatch_once(&token,^{
        _sharedClass = [[JVshowKeyboardManger alloc] init];
    });
    return _sharedClass;
}

//添加通知
+(void)addNotificationWithTextFiled:(JVshowkeyboardTextFiled*)textFiled{
    JVshowKeyboardManger* thisObject=[JVshowKeyboardManger shareClass];
    thisObject.textFiled=textFiled;
    thisObject.viewY=[thisObject relativeFrameForScreenWithView:thisObject.textFiled].origin.y;
    [thisObject addKeyboardNotification];
}


//移除通知
+(void)removeNotificationWithTextFiled:(JVshowkeyboardTextFiled*)textFiled{
    JVshowKeyboardManger* thisObject=[JVshowKeyboardManger shareClass];
    [thisObject removeNotification];
}


#pragma -mark添加通知
-(void)addKeyboardNotification{
    
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:nil];
}

#pragma -mark 移除通知
-(void)removeNotification{
    
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

#pragma -mark 键盘显示时
- (void)keyboardWillShow:(NSNotification *)notif {
    if (self.textFiled==nil||self.textFiled.moveView==nil) return;
    self.keyboardToolbar.hidden=YES;
    CGSize kbSize = [[notif.userInfo objectForKey:UIKeyboardFrameEndUserInfoKey] CGRectValue].size;//得到鍵盤的高度
    CGFloat kbHeight = kbSize.height;
    self.keyboardToolbar.y=SCREEN_HEIGHT-kbHeight-40;
    self.keyboardToolbar.alpha=0;
    CGFloat viewY=self.viewY;
    CGFloat viewMaxY=viewY+self.textFiled.frame.size.height;
    CGFloat moveY=viewMaxY+kbHeight-[UIScreen mainScreen].bounds.size.height+40;
    if (moveY>0) {
        [UIView animateWithDuration:0.05 animations:^{
            self.textFiled.moveView.bounds=CGRectMake(0, moveY, self.textFiled.moveView.frame.size.width, self.textFiled.moveView.frame.size.height);
        } completion:^(BOOL finished) {
            self.keyboardToolbar.alpha=1;
            self.keyboardToolbar.hidden=NO;
        }];
    }else{
        [UIView animateWithDuration:0.25 animations:^{
            self.keyboardToolbar.alpha=1;
            self.keyboardToolbar.hidden=NO;
        }];
    }
}
#pragma -mark 键盘隐藏
- (void)keyboardWillHide:(NSNotification *)notif {
    if (self.textFiled==nil||self.textFiled.moveView==nil) return;
    self.keyboardToolbar.hidden=YES;
    [UIView animateWithDuration:0.25 animations:^{
         self.textFiled.moveView.bounds=CGRectMake(0, 0, self.textFiled.moveView.frame.size.width, self.textFiled.moveView.frame.size.height);
    }];
}



#pragma -mark 点击关闭键盘
-(void)closeTheKeyboard{
    [[[UIApplication sharedApplication] keyWindow] endEditing:YES];
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
