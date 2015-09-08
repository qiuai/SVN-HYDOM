//
//  JVcancelOrderStateView.h
//  HD_Car
//
//  Created by xingso on 15/8/11.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface JVcancelOrderStateView : UIView

@property (weak, nonatomic) IBOutlet UILabel *label1;
@property (weak, nonatomic) IBOutlet UILabel *label2;
@property (weak, nonatomic) IBOutlet UILabel *label3;
@property (weak, nonatomic) IBOutlet UILabel *label4;


//选择默认
@property (nonatomic,weak) id  viewController;
//事件
@property (nonatomic,assign) SEL tapMethod;

@property(assign,nonatomic)NSInteger state;


@property(strong,nonatomic)NSArray* array;
@end
