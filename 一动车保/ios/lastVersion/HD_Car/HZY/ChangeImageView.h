//
//  ChangeImageView.h
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ChangeImageView : UIView
@property (weak, nonatomic) IBOutlet UIImageView *headImageView;
@property (weak, nonatomic) IBOutlet UIButton *changeHeadImageButton;
- (void)setUpUserInterfaceWith:(NSString *)picUrl;
@end
