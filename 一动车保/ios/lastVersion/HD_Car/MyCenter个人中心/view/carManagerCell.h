//
//  carManagerCell.h
//  HD_Car
//
//  Created by xingso on 15/7/28.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
@class carModel;
@interface carManagerCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIImageView *defaultImageView;

@property (weak, nonatomic) IBOutlet UIImageView *carImageView;
@property (weak, nonatomic) IBOutlet UIView *defaultCarView;
@property (weak, nonatomic) IBOutlet UILabel *carName;
@property (weak, nonatomic) IBOutlet UILabel *carModelText;
@property (weak, nonatomic) IBOutlet UILabel *carNumberText;
@property (weak, nonatomic) IBOutlet UILabel *carKMtext;
@property (weak, nonatomic) IBOutlet UILabel *carRunwayText;
@property (weak, nonatomic) IBOutlet UILabel *carColorText;
@property (weak, nonatomic) IBOutlet UILabel *carOutPutText;
@property (weak, nonatomic) IBOutlet UILabel *carWearText;
@property (weak, nonatomic) IBOutlet UIView *deleteView;
@property (weak, nonatomic) IBOutlet UIView *updateView;

//数据 model
-(void)initWithModel:(carModel* )carmodel;
//提供 vc 给予cell 事件处理
@property(weak,nonatomic)id viewController;

//选择默认
@property (nonatomic,assign) SEL  changeDefaultMethod;
//删除
@property (nonatomic,assign) SEL deleteMethod;
//编辑
@property (nonatomic,assign) SEL updateMethod;


@property(nonatomic,assign)BOOL isDefault;

//改变默认 图标
-(void)changeDefault:(BOOL)bl;

@end
