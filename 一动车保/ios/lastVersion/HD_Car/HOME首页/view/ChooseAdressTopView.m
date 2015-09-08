//
//  ChooseAdressTopView.m
//  dizhi
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import "ChooseAdressTopView.h"

@implementation ChooseAdressTopView
- (IBAction)pressCityButton:(id)sender {
    [self.delegate pressButtonWith:Area];
}

- (IBAction)pressAreaButton:(id)sender {
    [self.delegate pressButtonWith:Street];
}


@end
