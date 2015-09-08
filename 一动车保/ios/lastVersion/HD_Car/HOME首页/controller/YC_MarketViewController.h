//
//  YC_MarketViewController.h
//  HD_Car
//
//  Created by hydom on 8/10/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef enum {
    sugeest,
    limit,
    sale,
    life
}marketType;

@interface YC_MarketViewController : UIViewController

@property (nonatomic, assign)marketType marketType;

@end
