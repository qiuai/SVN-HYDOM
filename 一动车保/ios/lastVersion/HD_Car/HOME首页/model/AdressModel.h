//
//  AdressModel.h
//  dizhi
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface AdressModel : NSObject
/**
 *地区ID
 */
@property (nonatomic,strong)NSString * aid;
/**
 *街道或者地区名称
 */
@property (nonatomic,strong)NSString * name;
/**
 *街道ID
 */
@property (nonatomic,strong)NSString * sid;


@end
