//
//  gitViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/3.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "gitViewController.h"
#import "UIImage+GIF.h"
@interface gitViewController ()
@property (weak, nonatomic) IBOutlet UIImageView *gifImageView;

@end

@implementation gitViewController

- (void)viewDidLoad {
    [super viewDidLoad];

 
    
}

-(void)displayingGIF{
    
        CGRect gifframe=self.gifImageView.frame;
        UIImageView* imageView=[[UIImageView alloc]initWithImage:[UIImage sd_animatedGIFNamed:@"abc"]];
        [self.view addSubview:imageView];
        imageView.frame=gifframe;
}


@end
