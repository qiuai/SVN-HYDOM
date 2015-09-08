//
//  JvOrderFooterStyleTwoView.h
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface JvOrderFooterStyleTwoView : UIView
@property (weak, nonatomic) IBOutlet UILabel *prices;
@property (weak, nonatomic) IBOutlet UIView *addsubView;

//view 类型
@property(assign,nonatomic)NSInteger viewStyle;



//提供 vc 给予view 事件处理
@property(weak,nonatomic)id viewController;

//点击事件
@property (nonatomic,assign) SEL  subClick;
@end
