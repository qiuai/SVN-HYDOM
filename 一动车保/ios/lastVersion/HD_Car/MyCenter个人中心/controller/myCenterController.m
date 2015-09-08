//
//  myCenterController.m
//  HDCarProject
//
//  Created by xingso on 15/6/15.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "myCenterController.h"
#import "circleView.h"

@interface myCenterController ()
@property (weak, nonatomic) IBOutlet circleView *myIconView;
@end

@implementation myCenterController

- (void)viewDidLoad {
    [super viewDidLoad];
  self.myIconView.imageName=@"a123.jpg";
   
    
    
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
