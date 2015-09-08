//
//  firstServicesTableViewCell.h
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface firstServicesTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *icon;
@property (weak, nonatomic) IBOutlet UILabel *serviceName;
@property (weak, nonatomic) IBOutlet UILabel *servicePrices;
@property (weak, nonatomic) IBOutlet UIImageView *goodsImage;
@property (weak, nonatomic) IBOutlet UILabel *goodsName;
@property (weak, nonatomic) IBOutlet UILabel *goodsPrices;
@property (weak, nonatomic) IBOutlet UILabel *goodsCount;


//是否展示评价服务
-(void)showEvaluate:(BOOL)b;



//是否展示商品评价服务
-(void)showProductEvaluate:(BOOL)b;


//提供 vc 给予view 事件处理
@property(weak,nonatomic)id viewController;

//服务评价
@property (nonatomic,assign) SEL  subClick;

//商品评价
@property (nonatomic,assign) SEL  productSubClick;

@end
