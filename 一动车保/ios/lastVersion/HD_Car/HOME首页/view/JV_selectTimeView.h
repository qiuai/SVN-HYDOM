//
//  JV_selectTimeView.h
//  HD_Car
//
//  Created by xingso on 15/8/8.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface JV_selectTimeView : UIView

@property (weak, nonatomic) IBOutlet UILabel *timeDescription;
@property (weak, nonatomic) IBOutlet UIButton *trueButton;
@property (weak, nonatomic) IBOutlet UIButton *cancel;

@property(copy,nonatomic)void(^selectTimeBlock)(NSString* timeStr);
@end
