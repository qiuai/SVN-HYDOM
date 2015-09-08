//
//  OrderCarWashModel.h
//  HD_Car
//
//  Created by hydom on 15-8-4.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface OrderCarWashModel : NSObject

@property (nonatomic,strong)NSString * orderNum;
/**
 *  1.派单中 2.路途中 3.服务中 4.完结
 */
@property (nonatomic,assign)NSInteger  status;
@property (nonatomic,strong)NSString * imageUrl;
@property (nonatomic,strong)NSString * serversName;
@property (nonatomic,strong)NSString * Details;
@property (nonatomic,strong)NSString * pay;
@property (nonatomic,strong)NSString * technicianName;
@property (nonatomic,strong)NSString * phone;


@end
