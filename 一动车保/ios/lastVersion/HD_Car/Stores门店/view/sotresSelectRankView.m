//
//  sotresSelectRankView.m
//  HD_Car
//
//  Created by xingso on 15/6/30.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "sotresSelectRankView.h"
@interface sotresSelectRankView()
@property (weak, nonatomic) IBOutlet UIImageView *iconView;
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;

@end
@implementation sotresSelectRankView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
//图标改变
-(void)changeIcon{
    _po(@"图标改变");
}
//文字改变
-(void)changeTtile:(NSString*)text{
    self.titleLabel.text=text;
}
@end
