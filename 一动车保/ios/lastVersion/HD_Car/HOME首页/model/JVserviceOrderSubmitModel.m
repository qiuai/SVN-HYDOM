//
//  JVserviceOrderSubmitModel.m
//  HD_Car
//
//  Created by xingso on 15/8/8.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVserviceOrderSubmitModel.h"

@implementation JVserviceOrderSubmitModel

-(void)setTimeDescriptionStr:(NSString *)timeDescriptionStr{
    _timeDescriptionStr=timeDescriptionStr;
    NSArray *array = [timeDescriptionStr componentsSeparatedByString:@" "];
    NSArray* tArray=[array[1] componentsSeparatedByString:@"-"];
    self.stime=[NSString stringWithFormat:@"%@ %@",array[0],tArray[0]];
    self.etime=[NSString stringWithFormat:@"%@ %@",array[0],tArray[1]];
}


-(void)setHttpDataDict:(NSMutableDictionary *)httpDataDict{
    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:httpDataDict options:NSJSONWritingPrettyPrinted error:&error];
    NSString *json =[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    _httpDataDict=httpDataDict;
   NSString* s1=[json stringByReplacingOccurrencesOfString:@" " withString:@""];
    NSString* s2=[s1 stringByReplacingOccurrencesOfString:@"\n" withString:@""];
    _httpData=s2;

}
@end
