//
//  orderListModel.h
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "JVorderDataModel.h"

@interface orderListModel : NSObject

@property(nonatomic,strong)NSString* oid;


@property(nonatomic,strong)NSString* onum;

@property(nonatomic,strong)NSNumber* otype;

@property(nonatomic,strong)NSString* ostatus;

@property(nonatomic,strong)NSString* oprice;

@property(nonatomic,strong)NSNumber* ocanop;

@property(nonatomic,strong)NSString* ocontact;


@property(nonatomic,strong)NSString* ophone;


//是否显示联系人 电话
@property(nonatomic,assign)BOOL showContact;

/**JVorderDataModel array*/
@property(nonatomic,strong)NSMutableArray* orderDataArray;



@end
