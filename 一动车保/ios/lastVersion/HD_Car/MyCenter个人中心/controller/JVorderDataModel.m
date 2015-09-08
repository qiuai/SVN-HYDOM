//
//  JVorderDataModel.m
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVorderDataModel.h"

@implementation JVorderDataModel


-(void)setCellStyle:(NSInteger)cellStyle{
    _cellStyle=cellStyle;
    CGFloat height;
    switch (_cellStyle) {
        case 0:
            height=140;
            break;
        case 1:
            height=100;
            break;
        case 2:
            height=100;
            break;
        default:
            height=44;
            break;
    }
    self.cellHeight=height;

}
@end
