//
//  discoverModel.h
//  HD_Car
//
//  Created by hydom on 8/15/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface discoverModel : NSObject

/**
 *发现ID
 */
@property (nonatomic,strong)NSString * nwid;
/**
 *发现标题
 */
@property (nonatomic,strong)NSString * nwtitle;
/**
 *展示图
 */
@property (nonatomic,strong)NSString * nwimage;

/**
 *发布日期
 */
@property (nonatomic,strong)NSString * nwdate;

/**
 *赞数
 */
@property (nonatomic,strong)NSNumber * nwstar;

/**
 *html地址
 */
@property (nonatomic,strong)NSString * nwurl;


@end
