//
//  ChangeImageView.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "ChangeImageView.h"
#import <UIImageView+WebCache.h>
#import "HDApiUrl.h"
@implementation ChangeImageView

-(void)awakeFromNib{
    self.headImageView.layer.cornerRadius = 50;
    self.headImageView.layer.masksToBounds = YES;
    self.changeHeadImageButton.layer.cornerRadius = 5;
    self.changeHeadImageButton.layer.masksToBounds = YES;
}
- (void)setUpUserInterfaceWith:(NSString *)picUrl{
    [self.headImageView sd_setImageWithURL:imageURLWithPath(picUrl) placeholderImage:[UIImage imageNamed:FillImage]];
}
@end
