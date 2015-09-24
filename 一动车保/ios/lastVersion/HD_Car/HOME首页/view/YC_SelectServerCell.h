//
//  YC_SelectServerCell.h
//  HD_Car
//
//  Created by hydom on 7/30/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
@class YC_GoodsModel;
@interface YC_SelectServerCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIButton *deleteButton;
@property (nonatomic, strong) YC_GoodsModel * dataSource;
-(void)setCellDataSource;

//提供 vc 给予cell 事件处理
@property(weak,nonatomic)UIViewController* viewController;

//增加
@property (nonatomic,assign) SEL  sumMethod;
//减少
@property (nonatomic,assign) SEL subtractMethod;

@end
