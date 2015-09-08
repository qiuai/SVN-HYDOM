//
//  JVsendEvaluationViewController.h
//  HD_Car
//
//  Created by xingso on 15/8/12.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVbaseViewController.h"
#import "JVorderDataModel.h"
@interface JVsendEvaluationViewController : JVbaseViewController


@property(strong,nonatomic)JVorderDataModel* dataModel;

@property(strong,nonatomic)NSString* orderID;

@property(assign,nonatomic)BOOL style;


@property(copy,nonatomic)void(^evaluationBlock)(void);

@end
