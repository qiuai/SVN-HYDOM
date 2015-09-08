//
//  creatCarOrderView.m
//  HD_Car
//
//  Created by xingso on 15/7/14.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "creatCarOrderView.h"
@interface creatCarOrderView()
@property(nonatomic,weak)UILabel* washInfoLabel;
@property(nonatomic,weak)UIView* backView;
@end


@implementation creatCarOrderView

/**添加虚线*/
-(void)addline:(UIView*)view{
    UIImage* backgroundImage=[UIImage imageNamed:@"HDxuxian"];
    UIColor *backgroundColor = [UIColor colorWithPatternImage:backgroundImage];
    view.backgroundColor=backgroundColor;
}


-(void)awakeFromNib{
    // 初始化代码
    [self addline:self.boundaryView1];
    [self addline:self.boundaryView2];
    [self addline:self.boundaryView3];
    CGRect rt=self.washCarInfoView.bounds;
    UILabel* washInfoLabel=[[UILabel alloc]initWithFrame:rt];
    washInfoLabel.textColor=[UIColor blackColor];
    washInfoLabel.font=FONT12;
    washInfoLabel.text=@"洗车介绍";
    washInfoLabel.numberOfLines=0;
    self.washInfoLabel=washInfoLabel;
    [self.washCarInfoView addSubview:washInfoLabel];
    self.phoneTextField.keyboardType=UIKeyboardTypePhonePad;
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:nil];
    self.phoneTextField.textAlignment=2;
    self.personTextField.textAlignment=2;
    self.phoneTextField.tag=10;
    self.personTextField.tag=11;
    [self.phoneTextField addTarget:self action:@selector(TextFieldDidChange:) forControlEvents:UIControlEventEditingChanged];
    [self.personTextField addTarget:self action:@selector(TextFieldDidChange:) forControlEvents:UIControlEventEditingChanged];
}

-(void)TextFieldDidChange:(UITextField*)textField{
    if (textField.tag==10) {
        if (textField.text.length > 11) {
            textField.text = [textField.text substringToIndex:11];
        }
    }else{
        if (textField.text.length > 6) {
            textField.text = [textField.text substringToIndex:6];
        }
    }
    

}

-(void)setWashCarInfoText:(NSString *)washCarInfoText{
    _washCarInfoText=washCarInfoText;
    self.washInfoLabel.text=_washCarInfoText;
}

-(void)keyboardWillShow:(NSNotification *)notif {
    //添加覆盖层
    if (self.backView==nil) {
        UIView* backview=[[UIView alloc]initWithFrame:self.bounds];
        [backview addTapGestureRecognizerWithTarget:self action:@selector(closeKeyboard)];
        [self.window addSubview:backview];
        backview.backgroundColor=[UIColor clearColor];
        self.backView=backview;
    }
    CGSize kbSize = [[notif.userInfo objectForKey:UIKeyboardFrameEndUserInfoKey] CGRectValue].size;//得到鍵盤的高度
//    NSLog(@"hight_hitht:%f",kbSize.height);
//    _pf(SCREEN_HEIGHT);
    CGFloat y = kbSize.height;
    if ((y+335)>SCREEN_HEIGHT) {
        float moveY=y+430-SCREEN_HEIGHT;
            CGRect bds=self.bounds;
            self.bounds=CGRectMake(0, moveY, SCREEN_WIDTH, bds.size.height);
    }
}


- (void)keyboardWillHide:(NSNotification *)notif {
    [self opeatingKeyboard];
    
}

-(void)opeatingKeyboard{
    [UIView animateWithDuration:0.25 animations:^{
        self.bounds=CGRectMake(0, 0, SCREEN_WIDTH, self.frame.size.height);
    }];
    [self.backView removeFromSuperview];
}

-(void)closeKeyboard{
    if (![self.phoneTextField isExclusiveTouch]) {
        [self.phoneTextField resignFirstResponder];
    }
    if (![self.personTextField isExclusiveTouch]) {
        [self.personTextField resignFirstResponder];
    }
[self opeatingKeyboard];
}


-(void)willRemoveSubview:(UIView *)subview{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}
@end
