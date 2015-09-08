//
//  JVzhifubao.m
//  HD_Car
//
//  Created by xingso on 15/8/15.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVzhifubao.h"
#import "Order.h"
#import "DataSigner.h"
#import <AlipaySDK/AlipaySDK.h>
#import "APAuthV2Info.h"

@implementation JVzhifubao

+(void)JVzfbWith:(JVcommonProduct*)product success:(void (^)(void))success failure:(void(^)(void))failure{
    NSString *partner = @"2088021002450046";
    NSString *seller = @"ydcbab@163.com"; 
    NSString *privateKey = @"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL3SFw1xk7cdziiwT5BKlbkHJ833kSKfpCikth4dJKwpwQH5Xnl1c4aEz9vACaHgjMFlUHCYBGUMB/DjvuCswVDbdKHnIqUd8mUsQh9mvI83e562SxNL2YuL9++l+hSm8bmwhNuoku0u12uT2tnsQ1TbT12W4Zcs+iHCHom+6B9ZAgMBAAECgYEApcZb8Za2TZ7PFAPeiIJKvdu87IkADH/lKsWmcyg6hcy1TdcNpf9oFBvbN+w/vUrRQnkLxjlM5T6blMohQjA9ZrDwbP7logCHMToshSOAHnDcQSrjgs1FEi77LRpWpJiLwMnaVwdjjMQYP3MPujORql5ra6HEe103uXgt/Iz78DECQQDur3KJA7hKt12zUqqhGkWIDNWW+ftaZ2aad2dQqLgpvsJ97p+jmzedmSwM09donypAAlP/iMfo3DZ/mJJfIRMlAkEAy5cxD7gOT+nDou+cfYJTV5WHXyiG16krxeifdbKNAASCftIKZkZl93yOXi5cOBizYlT8QclUufl0KK1wms1/JQJARsZddwVUW0teDHNhxx2MKphrqTX880Sf5wOq7f7phO9cqozcZ136MtAdgLw8LnirxYkrMSV06baKrnEmTfD3xQJBAJjH3CR8rhjgR1UV1W0GiT6X0t/hTNe4d0XsnQW5OUDDIZ7ERtObjtebnEcnKUNbnfpz5l4EFVX+0mHYTkGNZxUCQQC6rSJNnAUUl2k0/H3kK07rSM5Vu9E0b3lB8SC3j1hbZEd3jsAWUJ+Iw8pdPPfpQF6bvAHFOcbet2d6IDrCoF6W";
    /*
     *生成订单信息及签名
     */
    //将商品信息赋予AlixPayOrder的成员变量
    Order *order = [[Order alloc] init];
    order.partner = partner;
    order.seller = seller;
    order.tradeNO = product.orderID; //订单ID（由商家自行制定）
    order.productName =product.productName; //商品标题
    order.productDescription = product.description; //商品描述
    order.amount = [NSString stringWithFormat:@"%.2f",product.orderPrices]; //商品价格
    order.notifyURL =product.resultURL; //回调URL
    order.service = @"mobile.securitypay.pay";
    order.paymentType = @"1";
    order.inputCharset = @"utf-8";
    order.itBPay = @"30m";
    order.showUrl = @"m.alipay.com";
    //应用注册scheme,在AlixPayDemo-Info.plist定义URL types
    NSString *appScheme = @"hydomCarAlisdk";
    //将商品信息拼接成字符串
    NSString *orderSpec = [order description];
    NSLog(@"orderSpec = %@",orderSpec);
    //获取私钥并将商户信息签名,外部商户可以根据情况存放私钥和签名,只需要遵循RSA签名规范,并将签名字符串base64编码和UrlEncode
    id<DataSigner> signer = CreateRSADataSigner(privateKey);
    NSString *signedString = [signer signString:orderSpec];
    //将签名成功字符串格式化为订单字符串,请严格按照该格式
    NSString *orderString = nil;
    if (signedString != nil) {
        orderString = [NSString stringWithFormat:@"%@&sign=\"%@\"&sign_type=\"%@\"",
                       orderSpec, signedString, @"RSA"];
//        [[AlipaySDK defaultService] payOrder:orderString fromScheme:appScheme callback:success];
        
        
        [[AlipaySDK defaultService]payOrder:orderString fromScheme:appScheme callback:^(NSDictionary *resultDic) {
            NSString* Status=resultDic[@"resultStatus"];
            if ([Status isEqualToString:@"9000"]) {
                success();
            }else{
                failure();
            }
        }];
        
    }
    
    
    
}
@end
