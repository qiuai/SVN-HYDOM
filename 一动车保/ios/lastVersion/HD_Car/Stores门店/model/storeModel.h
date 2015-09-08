//
//  storeModel.h
//  HD_Car
//
//  Created by xingso on 15/7/21.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface storeModel : NSObject
/**门店ID*/
@property(nonatomic,copy)NSString* soid;
/**门店名称*/
@property(nonatomic,copy)NSString* soname;
/**门店图片*/
@property(nonatomic,copy)NSString* soimage;
/**门店到当前距离*/
@property(nonatomic,assign)float sodistance;
/**门店评价数*/
@property(nonatomic,assign)NSInteger socomts;
/**门店星级*/
@property(nonatomic,assign)NSInteger sostar;
/**门店地址*/
@property(nonatomic,copy)NSString* soaddres;
/**门店支持的服务类型Array*/
@property(nonatomic,copy)NSArray* servicesArray;

/**dict to model */
+(instancetype)storeModelWithDict:(NSDictionary*)dict;
@end
