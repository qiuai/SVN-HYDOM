//
//  UIImage+MJ.h
//  传智微博
//
//  Created by teacher on 14-6-6.
//  Copyright (c) 2014年 itcast. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (MJ)
/**
 *  返回一张图片
 *
 *  @param name 图片名
 */
+ (instancetype)imageWithName:(NSString *)name;

/**
 *  返回一张能自由拉伸的图片
 *
 *  @param name 图片名
 */
+ (instancetype)resizableImage:(NSString *)name;

/**
 *  返回一张能自由拉伸的图片
 *
 *  @param name      图片名
 *  @param leftRatio 左边有多少比例不需要拉伸(0~1)
 *  @param topRatio  顶部有多少比例不需要拉伸(0~1)
 */
+ (instancetype)resizableImage:(NSString *)name leftRatio:(CGFloat)leftRatio topRatio:(CGFloat)topRatio;

/**
 创建UIimage by UIcolor
 */
+ (UIImage*) createImageWithColor: (UIColor*) color;
@end
