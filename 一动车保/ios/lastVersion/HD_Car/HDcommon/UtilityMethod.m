//
//  UtilityMethod.m
//  ydcbjs
//
//  Created by hydom on 15-7-30.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import "UtilityMethod.h"
#import <objc/runtime.h>
@implementation UtilityMethod
+(NSString *) compareCurrentTime:(NSString*) string
{
    NSInteger timeInt = [string integerValue];
    NSTimeInterval time = timeInt;
    NSDate *compareDate = [NSDate dateWithTimeIntervalSince1970:time];
    NSTimeInterval  timeZoneOffset=[[NSTimeZone systemTimeZone] secondsFromGMT];
    NSTimeInterval  timeInterval = [compareDate timeIntervalSinceNow];
    timeInterval = -timeInterval;
    timeInterval += timeZoneOffset;
    long temp = 0;
    NSString *result;
    if (timeInterval < 60) {
        result = [NSString stringWithFormat:@"刚刚"];
    }
    else if((temp = timeInterval/60) <60){
        result = [NSString stringWithFormat:@"%ld分前",temp];
    }
    
    else if((temp = temp/60) <24){
        result = [NSString stringWithFormat:@"%ld小时前",temp];
    }
    
    else if((temp = temp/24) <30){
        result = [NSString stringWithFormat:@"%ld天前",temp];
    }
    
    else if((temp = temp/30) <12){
        result = [NSString stringWithFormat:@"%ld月前",temp];
    }
    else{
        temp = temp/12;
        result = [NSString stringWithFormat:@"%ld年前",temp];
    }
    
    return  result;
}

+(CGFloat)getHeightToLongLabel:(NSString *)textstring width:(CGFloat)width {
    CGSize containSize = CGSizeMake(width, 2000);
    NSDictionary * descriptionDic = @{NSFontAttributeName:[UIFont systemFontOfSize:13]};
    CGRect labelRect = [textstring boundingRectWithSize:containSize options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:descriptionDic context:nil];
    return labelRect.size.height;
}

+(CGFloat)getHeightToLongLabel:(NSString *)textstring width:(CGFloat)width font:(NSInteger)font {
    CGSize containSize = CGSizeMake(width, 2000);
    NSDictionary * descriptionDic = @{NSFontAttributeName:[UIFont systemFontOfSize:font]};
    CGRect labelRect = [textstring boundingRectWithSize:containSize options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:descriptionDic context:nil];
    return labelRect.size.height;
}

+(CGFloat)getWidthToLongLabel:(NSString *)textstring heigth:(CGFloat)heigth font:(NSInteger)font{
    CGSize containSize = CGSizeMake(2000, heigth);
    NSDictionary * descriptionDic = @{NSFontAttributeName:[UIFont systemFontOfSize:font]};
    CGRect labelRect = [textstring boundingRectWithSize:containSize options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:descriptionDic context:nil];
    return labelRect.size.width;
}

+(NSString *)makeTimeStampToTimeFormatterWith:(NSString *)timeStamp{
    NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"yyyy年MM月dd日  HH:mm"];
    NSTimeInterval  timeZoneOffset=[[NSTimeZone systemTimeZone] secondsFromGMT];
    NSTimeInterval newTime = [timeStamp integerValue] - timeZoneOffset;
    NSDate *date = [NSDate dateWithTimeIntervalSince1970:newTime];
    NSString * timeString = [formatter stringFromDate:date];
    return timeString;
}

+(NSString *)makeTimeStampToTimeStringWith:(NSString *)timeStamp{
    NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
    [formatter setDateStyle:NSDateFormatterMediumStyle];
    [formatter setTimeStyle:NSDateFormatterShortStyle];
    [formatter setDateFormat:@"yyyy-MM-dd"];
    NSTimeInterval  timeZoneOffset=[[NSTimeZone systemTimeZone] secondsFromGMT];
    NSTimeInterval newTime = [timeStamp integerValue] - timeZoneOffset;
    NSDate *date = [NSDate dateWithTimeIntervalSince1970:newTime];
    NSString * timeString = [formatter stringFromDate:date];
    return timeString;
}

+ (BOOL)validateEmail:(NSString *)email
{
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    return [emailTest evaluateWithObject:email];
}

+ (void)uploadImageWithUrl:(NSString *)url
                     image:(UIImage *)image
                   success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
                   failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure
                                     {
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager POST:url parameters:nil constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        NSData *imageData = UIImageJPEGRepresentation(image, 0.5);
        
        NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
        formatter.dateFormat = @"yyyyMMddHHmmss";
        NSString *str = [formatter stringFromDate:[NSDate date]];
        NSString *fileName = [NSString stringWithFormat:@"%@.jpg", str];
        
        // 上传图片，以文件流的格式
        [formData appendPartWithFileData:imageData name:@"myfiles" fileName:fileName mimeType:@"image/jpeg"];
        
    } success:^(AFHTTPRequestOperation *operation, id responseObject) {
        success(operation,responseObject);
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        failure(operation,error);
    }];
    
}


/**模型转字典*/
+ (NSDictionary*)getObjectData:(id)obj{
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    unsigned int propsCount;
    objc_property_t *props = class_copyPropertyList([obj class], &propsCount);
    for(int i = 0;i < propsCount; i++)
    {
        objc_property_t prop = props[i];
        
        NSString *propName = [NSString stringWithUTF8String:property_getName(prop)];
        if (![propName isEqualToString:@"httpDataDict"]&&![propName isEqualToString:@"cpid"]) {
            id value = [obj valueForKey:propName];
            if(value != nil)
            {
                [dic setObject:value forKey:propName];
            }
        }
    }
    free(props);
    return dic;
}

/**加上 ￥ 人名币符合*/
+(NSString*)addRMB:(NSString*)str{
    return [NSString stringWithFormat:@"￥ %@",str];
}


/**优惠劵 加 -￥*/
+(NSString *)addSubRMB:(NSString*)str{
    return  [NSString stringWithFormat:@"-￥ %@",str];
}



//遍历 拼接url
+(NSString*)JVDebugUrlWithdict:(NSDictionary*)dic nsurl:(NSString* )url{
    NSMutableString* str=[NSMutableString stringWithString:url];
    NSArray* keyArray=[dic allKeys];
    NSArray* valueArray=[dic allValues];
    NSInteger outCount=keyArray.count;
    for (NSInteger i = 0; i<outCount; i++)
    {
        if (i==0) {
            [str insertString:[NSString stringWithFormat:@"?%@=%@",keyArray[i],valueArray[i]] atIndex:str.length];
        }else{
            [str insertString:[NSString stringWithFormat:@"&%@=%@",keyArray[i],valueArray[i]] atIndex:str.length];
        }
    }
    return str;
}


//获取tableViewCell
+(id)getTableViewCell:(UIView*)view class:(Class)cellClass{
    id cell;
    while (view.superview) {
        if ([view.superview isKindOfClass:cellClass]){
           cell=view.superview;
            break;
        }else{
            view=view.superview;
        }
    }
    return cell;
}

@end
