//
//  carModel.h
//  HD_Car
//
//  Created by xingso on 15/7/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface carModel : NSObject
/**车辆品牌id*/
@property(nonatomic,copy)NSString* cbid;
/**品牌名称*/
@property(nonatomic,copy)NSString* cbname;  
/**字母拼音*/
@property(nonatomic,copy)NSString* fletter;
/**图片URL*/
@property(nonatomic,copy)NSString* cbimageURL;
/**顶级车系名称*/
@property(nonatomic,copy)NSString* topname;
/**车系ID*/
@property(nonatomic,copy)NSString* csid;
/**车系名称*/
@property(nonatomic,copy)NSString* csname;
/**车型ID*/
@property(nonatomic,copy)NSString* cmid;
/**车型名称*/
@property(nonatomic,copy)NSString* cmname;
/**油耗*/
@property (nonatomic,strong)NSNumber * fuel;
/**行驶里程*/
@property (nonatomic,strong)NSNumber* drange;
/**排量*/
@property (nonatomic,strong)NSNumber * engines;
/**上路*/
@property (nonatomic,strong)NSString * date;
/**车牌*/
@property(nonatomic,strong)NSString* plateNumber;
/**颜色*/
@property(nonatomic,strong)NSString* color;
/**是否是默认车型*/
@property(nonatomic,assign)NSNumber* defaultCar;
/**用户车辆ID*/
@property(nonatomic,strong)NSString* ucid;

/**车型描述*/
-(NSString*)getCarDescription;

/**字典转模型*/
+(instancetype)carModelWithDictionary:(NSDictionary*)dictionary;
@end
