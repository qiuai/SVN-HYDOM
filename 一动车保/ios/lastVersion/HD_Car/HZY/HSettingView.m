//
//  HSettingView.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "HSettingView.h"
#import <UIImageView+WebCache.h>
#import "HDApiUrl.h"
@implementation HSettingView
-(void)awakeFromNib{
    
    self.logoutButton.layer.cornerRadius = 5;
    self.logoutButton.layer.masksToBounds = YES;
    self.headImageView.layer.cornerRadius = 20;
    self.headImageView.layer.masksToBounds = YES;

}
-(void)setUpUserInterfaceWith:(SettingModel *)model{

    [self.headImageView sd_setImageWithURL:imageURLWithPath(model.photo) placeholderImage:[UIImage imageNamed:FillImage]];
    self.userNameLabel.text = model.nickname;

}

- (IBAction)clickLogoutButton:(UIButton *)sender {
    [self.viewController performSelector:self.Method withObject:nil];
}

@end
