//
//  storeHome.h
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/18.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^navigationBlock)(void);

@interface storeHome : UIView
@property (weak, nonatomic) IBOutlet UILabel *servicesTimesLavel;
@property (weak, nonatomic) IBOutlet UILabel *tellNumberLabel;
@property (weak, nonatomic) IBOutlet UILabel *addressLabel;
@property (weak, nonatomic) IBOutlet UIImageView *callPhoneNumber;
@property (weak, nonatomic) IBOutlet UIImageView *locationServices;
@property(nonatomic,copy)navigationBlock locatonServiceBlock;
@end
