//
//  ChooseAreaViewController.h
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "JVbaseViewController.h"
@protocol AddressDelegate <NSObject>

- (void)sendAddressWith:(NSString *)address Id:(NSString *)Id detailsString:(NSString *)detailString;

@end
@interface ChooseAreaViewController : JVbaseViewController
@property (nonatomic,weak)id <AddressDelegate> delegate;

@end

