//
//  storeHome.m
//  HD_InsuranceCar
//
//  Created by xingso on 15/6/18.
//  Copyright (c) 2015年 HD JARVAN. All rights reserved.
//

#import "storeHome.h"
@interface storeHome()

@end
@implementation storeHome

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/


-(void)awakeFromNib{
    
    [self.locationServices addTapGestureRecognizerWithTarget:self action:@selector(locationS)];

}



-(void)locationS{

    if (self.locatonServiceBlock!=nil) {
        self.locatonServiceBlock();
    }
}

@end
