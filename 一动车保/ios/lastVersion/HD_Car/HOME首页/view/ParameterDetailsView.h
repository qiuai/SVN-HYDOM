//
//  ParameterDetailsView.h
//  HD_Car
//
//  Created by hydom on 15-7-20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ParameterDetailsView : UIView
@property (nonatomic,assign)CGFloat ParameterHeigth;
@property (nonatomic,assign)CGFloat fitCarHeigth;

- (instancetype)initWith:(NSArray *)array;
- (instancetype)initWithFitCar:(NSArray *)array;
@end
