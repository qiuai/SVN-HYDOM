//
//  JVcommonProduct.h
//  HD_Car
//
//  Created by xingso on 15/8/15.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface JVcommonProduct : NSObject
/**订单ID（由商家自行制定）*/
@property(nonatomic,strong)NSString* orderID;
/**商品标题*/
@property(nonatomic,strong)NSString* productName;
/**商品描述*/
@property(nonatomic,strong)NSString* productDescription;
/**商品价格*/
@property(nonatomic,assign)double orderPrices;

/**回调地址*/
@property(nonatomic,strong)NSString* resultURL;
@end
