//
//  YC_MarketSaleCell.m
//  HD_Car
//
//  Created by hydom on 8/10/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_MarketSaleCell.h"

@implementation YC_MarketSaleCell

- (void)awakeFromNib {
    // Initialization code 
    
}

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self)
    {
        // 初始化时加载collectionCell.xib文件
        NSArray *arrayOfViews = [[NSBundle mainBundle] loadNibNamed:@"YC_MarketSaleCell" owner:self options:nil];
        
        // 如果路径不存在，return nil
        if (arrayOfViews.count < 1)
        {
            return nil;
        }
        // 如果xib中view不属于UICollectionViewCell类，return nil
        if (![[arrayOfViews objectAtIndex:0] isKindOfClass:[UICollectionViewCell class]])
        {
            return nil;
        }
        // 加载nib
        self = [arrayOfViews objectAtIndex:0];
    }
    return self;
}

@end
