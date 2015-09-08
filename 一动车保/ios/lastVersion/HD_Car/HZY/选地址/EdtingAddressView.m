//
//  EdtingAddressView.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "EdtingAddressView.h"

@implementation EdtingAddressView

- (void)awakeFromNib{
    self.addressTextField.delegate =self;
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    
    [self endEditing:YES];
    return YES;
}
@end
