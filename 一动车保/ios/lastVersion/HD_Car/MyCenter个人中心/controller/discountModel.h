//
//  discountModel.h
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface discountModel : NSObject

/**优惠券ID*/
@property(nonatomic,copy)NSString* cpid;

/**优惠券类型：1=满额打折、 2=满额减免、3=免额多少*/
@property(nonatomic,copy)NSString* cptype;

/**优惠券名称*/
@property(nonatomic,copy)NSString* cpname;

/**优惠券介绍*/
@property(nonatomic,copy)NSString* cpintroduction;

/**优惠券展示图*/
@property(nonatomic,copy)NSString* cpimg;

/**兑换需要多少积分*/
@property (nonatomic,copy)NSString* cpexscore;


@property(nonatomic,copy)NSString* cpminprice;
@property(nonatomic,copy)NSString* cprate;
@property(nonatomic,copy)NSString* cpmoney;
@end
