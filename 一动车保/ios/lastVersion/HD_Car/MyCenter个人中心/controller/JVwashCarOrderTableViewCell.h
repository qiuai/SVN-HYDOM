//
//  JVwashCarOrderTableViewCell.h
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface JVwashCarOrderTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *image;
@property (weak, nonatomic) IBOutlet UILabel *washCar;
@property (weak, nonatomic) IBOutlet UILabel *servicesDescription;
@property (weak, nonatomic) IBOutlet UIButton *eBtn;




//提供 vc 给予view 事件处理
@property(weak,nonatomic)id viewController;

//服务评价
@property (nonatomic,assign) SEL  subClick;

//是否展示评价
-(void)showEvaluate:(BOOL)b;

-(void)hideEvaluate;
@end
