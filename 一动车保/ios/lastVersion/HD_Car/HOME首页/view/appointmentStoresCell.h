//
//  appointmentStoresCell.h
//  HD_Car
//
//  Created by xingso on 15/7/21.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
@class storeModel;
@interface appointmentStoresCell : UITableViewCell

//预约btn
@property(nonatomic,strong)UIButton* appointmengtBtn;

//设置数据源
@property(nonatomic,strong)storeModel* model;

+(instancetype)cellWithTableView:(UITableView*)tableView;


@end
