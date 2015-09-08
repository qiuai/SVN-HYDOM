//
//  UIButton+extension.m
//  tabbarContainer
//
//  Created by cc on 15/5/9.
//  Copyright (c) 2015年 cc. All rights reserved.
//

#import "UIButton+extension.h"

@implementation UIButton (extension)

-(void)setNomalImage:(UIImage *)imageN highlightedImage:(UIImage *)imageh{
    [self setImage:imageN forState:UIControlStateNormal];    
    [self setImage:imageh forState:UIControlStateHighlighted];
    [self setImage:imageh forState:UIControlStateSelected];
}

-(void)setNomalTitle:(NSString *)titleN highlightedTitle:(NSString *)titleh{
    
    [self setTitle:titleN forState:UIControlStateNormal];
    [self setTitle:titleh forState:UIControlStateHighlighted];
    [self setTitle:titleh forState:UIControlStateSelected];

}


-(void)setNomalColor:(UIColor *)color1 highlightedColor:(UIColor *)color2{

    [self setTitleColor:color1 forState:UIControlStateNormal];
    [self setTitleColor:color2 forState:UIControlStateHighlighted];
    [self setTitleColor:color2 forState:UIControlStateSelected];


}
@end
