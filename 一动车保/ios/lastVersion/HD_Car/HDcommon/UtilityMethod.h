//
//  UtilityMethod.h
//  ydcbjs
//
//  Created by hydom on 15-7-30.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <AFNetworking.h>


@interface UtilityMethod : NSObject
/**
 *计算时间差
 */
+(NSString *) compareCurrentTime:(NSString*) string;
/**
 *计算文本高度，默认13号字体
 */
+(CGFloat)getHeightToLongLabel:(NSString *)textstring width:(CGFloat)width;
/**
 *计算文本高度
 */
+(CGFloat)getHeightToLongLabel:(NSString *)textstring width:(CGFloat)width font:(NSInteger)font;
/**
 *计算文本宽度
 */
+(CGFloat)getWidthToLongLabel:(NSString *)textstring heigth:(CGFloat)heigth font:(NSInteger)font;
/**
 *时间戳转时间yyyy年MM月dd日  HH:mm
 */
+(NSString *)makeTimeStampToTimeFormatterWith:(NSString *)timeStamp;
/**
 *时间戳转时间yyyy-MM-dd
 */
+(NSString *)makeTimeStampToTimeStringWith:(NSString *)timeStamp;
/**
 *邮箱正则判断
 */
+ (BOOL)validateEmail:(NSString *)email;

/**模型转字典*/
+ (NSDictionary*)getObjectData:(id)obj;

//遍历 拼接url 为了调试
+(NSString*)JVDebugUrlWithdict:(NSDictionary*)dic nsurl:(NSString* )url;

/**加上 ￥ 人名币符合*/
+(NSString*)addRMB:(NSString*)str;


/**优惠劵 加 -￥*/
+(NSString *)addSubRMB:(NSString*)str;

//获取tableViewCell
+(id)getTableViewCell:(UIView*)view class:(Class)cellClass;

@end
