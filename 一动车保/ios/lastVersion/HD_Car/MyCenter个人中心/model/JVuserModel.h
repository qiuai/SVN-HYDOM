//
//  JVuserModel.h
//  HD_Car
//
//  Created by xingso on 15/8/13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface JVuserModel : NSObject


@property(nonatomic,strong)NSString* nickname;

@property(nonatomic,strong)NSString* userImageURL;

@property(nonatomic,strong)UIImage* userImage;

@property(nonatomic,assign)BOOL update;
@end
