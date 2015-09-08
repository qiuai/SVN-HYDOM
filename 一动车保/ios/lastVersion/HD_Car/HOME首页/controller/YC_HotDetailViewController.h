//
//  YC_HotDetailViewController.h
//  HD_Car
//
//  Created by hydom on 8/11/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface YC_HotDetailViewController : UIViewController

@property(nonatomic,strong)NSString* productID;

//隐藏产品详情适配车型栏
@property (nonatomic, assign)BOOL isFixCar;
//更改价格
-(void)changePayLabel:(NSString *)price number:(NSInteger)number;

@end
