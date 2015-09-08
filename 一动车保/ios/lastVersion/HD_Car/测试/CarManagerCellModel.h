//
//  CarManagerCellModel.h
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CarManagerCellModel : NSObject
/**
 *车名
 */
@property (nonatomic,strong)NSString * carName;
/**
 *图片
 */
@property (nonatomic,strong)NSString * carPic;
/**
 *型号
 */
@property (nonatomic,strong)NSString * carVersion;
/**
 *油耗
 */
@property (nonatomic,strong)NSString * carOil;
/**
 *行驶
 */
@property (nonatomic,strong)NSString * carRun;
/**
 *排量
 */
@property (nonatomic,strong)NSString * carOutPut;
/**
 *上路
 */
@property (nonatomic,strong)NSString * carStartTime;

@end
