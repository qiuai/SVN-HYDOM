//
//  creatCarOrderView.h
//  HD_Car
//
//  Created by xingso on 15/7/14.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface creatCarOrderView : UIView
@property (weak, nonatomic) IBOutlet UITextField *phoneTextField;
@property (weak, nonatomic) IBOutlet UITextField *personTextField;

@property (weak, nonatomic) IBOutlet UILabel *appointmentTimeLabel;
@property (weak, nonatomic) IBOutlet UILabel *navSelectCarLabel;
@property (weak, nonatomic) IBOutlet UIView *boundaryView1;
@property (weak, nonatomic) IBOutlet UIView *boundaryView2;
@property (weak, nonatomic) IBOutlet UIView *boundaryView3;
@property (weak, nonatomic) IBOutlet UILabel *selectAddressLabel;
@property (weak, nonatomic) IBOutlet UIView *addressView;
@property (weak, nonatomic) IBOutlet UIView *washCarInfoView;
/**洗车介绍*/
@property(nonatomic,strong)NSString* washCarInfoText;
@end
