//
//  JVshowkeyboardTextFiled.m
//  UIViewConvertRect
//
//  Created by xingso on 15/7/17.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVshowkeyboardTextFiled.h"
#import "JVshowKeyboardManger.h"
@interface JVshowkeyboardTextFiled()
@property(strong,nonatomic)UIView* keyboardToolbar;

@end
@implementation JVshowkeyboardTextFiled


-(instancetype)initWithFrame:(CGRect)frame{
    self=[super initWithFrame:frame];
    [self Initialize];
    return self;
}
-(instancetype)init{
    self=[super init];
    [self Initialize];
    return self;
}

-(void)awakeFromNib{
    [self Initialize];
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

#pragma -mark 初始化
-(void)Initialize{
    [self addTarget:self action:@selector(addKeyboardNotification) forControlEvents:UIControlEventEditingDidBegin];
    [self addTarget:self action:@selector(removeKeyboardNotification) forControlEvents:UIControlEventEditingDidEnd];
}

#pragma -mark添加通知
-(void)addKeyboardNotification{
  [JVshowKeyboardManger addNotificationWithTextFiled:self];
}

#pragma -mark 移除通知
-(void)removeKeyboardNotification{
    [JVshowKeyboardManger removeNotificationWithTextFiled:self];
}

@end
