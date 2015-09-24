//
//  noGoodsListTableViewCell.h
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVorderFooterView.h"

@interface noGoodsListTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *icon;
@property (weak, nonatomic) IBOutlet UILabel *servicesTitle;
@property (weak, nonatomic) IBOutlet UILabel *prices;
@property (weak, nonatomic) IBOutlet UILabel *priceDescribe;

//是否展示评价
-(void)showEvaluate:(BOOL)b;

-(void)hideEvaluate;

//提供 vc 给予view 事件处理
@property(weak,nonatomic)id viewController;

//服务评价
@property (nonatomic,assign) SEL  subClick;
@end
