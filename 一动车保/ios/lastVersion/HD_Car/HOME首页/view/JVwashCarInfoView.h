//
//  JVwashCarInfoView.h
//  HD_Car
//
//  Created by xingso on 15/7/30.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVshowkeyboardTextFiled.h"
@interface JVwashCarInfoView : UIView
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *personTextField;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *phoneNumberTextField;
@property (weak, nonatomic) IBOutlet UILabel *carNumber;
@property (weak, nonatomic) IBOutlet UILabel *carColor;
@property (weak, nonatomic) IBOutlet UILabel *locationLabel;
@property (weak, nonatomic) IBOutlet UILabel *couponsLabel;

/**支付方式*/
@property(strong,nonatomic)NSString* payStyle;
/**支付方式描述*/
@property(strong,nonatomic)NSString* payStyleDescription;
/**洗车方式*/
@property(strong,nonatomic)NSString* washStyle;
/**洗车方式描述*/
@property(strong,nonatomic)NSString* washStyleDescription;

@end
