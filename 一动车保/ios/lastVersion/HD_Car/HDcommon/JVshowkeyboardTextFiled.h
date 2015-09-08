//
//  JVshowkeyboardTextFiled.h
//  UIViewConvertRect
//
//  Created by xingso on 15/7/17.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface JVshowkeyboardTextFiled : UITextField
/**移动View层*/
@property(nonatomic,weak)UIView* moveView;

/**字数限制*/
@property(nonatomic,assign)NSInteger wordsMaxLenght;

@end
