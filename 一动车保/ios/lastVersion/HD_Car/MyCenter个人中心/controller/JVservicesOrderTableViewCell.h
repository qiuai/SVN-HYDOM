//
//  JVservicesOrderTableViewCell.h
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface JVservicesOrderTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *image;
@property (weak, nonatomic) IBOutlet UILabel *servicesTitle;
@property (weak, nonatomic) IBOutlet UILabel *prices;
@property (weak, nonatomic) IBOutlet UILabel *count;


//是否展示评价
-(void)showEvaluate:(BOOL)b;



//提供 vc 给予view 事件处理
@property(weak,nonatomic)id viewController;

//商品评价
@property (nonatomic,assign) SEL  productSubClick;
@end
