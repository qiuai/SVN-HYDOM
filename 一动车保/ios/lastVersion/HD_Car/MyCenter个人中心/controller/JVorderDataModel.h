//
//  JVorderDataModel.h
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface JVorderDataModel : NSObject

@property(nonatomic,strong)NSString* scid;
@property(nonatomic,strong)NSString* scname;
@property(nonatomic,strong)NSNumber* scprice;
@property(nonatomic,strong)NSString* pid;
@property(nonatomic,strong)NSString* pimg;
@property(nonatomic,strong)NSString* pname;
@property(nonatomic,strong)NSString* premark;
@property(nonatomic,strong)NSNumber* pprice;
@property(nonatomic,strong)NSNumber* pnum;
@property(nonatomic,strong)NSString* scimg;


@property(nonatomic,strong)NSNumber* sccomt;


@property(nonatomic,strong)NSNumber* pcomt;



@property(nonatomic,assign)CGFloat cellHeight;

//哪种cell
@property(nonatomic,assign)NSInteger cellStyle;

@property(nonatomic,assign)BOOL goods;


@end
