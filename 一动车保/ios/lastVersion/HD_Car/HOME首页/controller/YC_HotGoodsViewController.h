//
//  YC_HotGoodsViewController.h
//  HD_Car
//
//  Created by hydom on 8/11/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "carModel.h"
@interface YC_HotGoodsViewController : UIViewController

@property (nonatomic, strong) NSString * pname;
@property (nonatomic, strong) NSString * pcid;

@property(nonatomic,strong)carModel* car;
@end
