//
//  UIImage+MJ.m
//  传智微博
//
//  Created by teacher on 14-6-6.
//  Copyright (c) 2014年 itcast. All rights reserved.
//

#import "UIImage+MJ.h"

@implementation UIImage (MJ)

+ (UIImage *)imageWithName:(NSString *)name
{
    // 0.需要返回的图片
    UIImage *image = nil;
    
    
    
  
        image = [self imageNamed:name];
    
    return image;
}

+ (UIImage *)resizableImage:(NSString *)name
{
    return [self resizableImage:name leftRatio:0.5 topRatio:0.5];
}

+ (UIImage *)resizableImage:(NSString *)name leftRatio:(CGFloat)leftRatio topRatio:(CGFloat)topRatio
{
    UIImage *image = [self imageWithName:name];
    CGFloat left = image.size.width * leftRatio;
    CGFloat top = image.size.height * topRatio;
    return [image stretchableImageWithLeftCapWidth:left topCapHeight:top];
}


/**
 创建UIimage by UIcolor
 */
+ (UIImage*) createImageWithColor: (UIColor*) color{
    CGRect rect=CGRectMake(0.0f, 0.0f, 1.0f, 1.0f);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetFillColorWithColor(context, [color CGColor]);
    CGContextFillRect(context, rect);
    UIImage *theImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return theImage;
}
@end
