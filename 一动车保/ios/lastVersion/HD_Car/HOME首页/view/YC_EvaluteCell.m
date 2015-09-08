//
//  EvaluteCell.m
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_EvaluteCell.h"

@implementation YC_EvaluteCell

- (void)awakeFromNib {
    
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}


-(void)drawStarWithInt:(NSInteger)index{
    NSInteger count=index/2;
    NSInteger f=index%2;
    for (int i=0; i<count; i++) {
        UIImageView* imageV=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"storesSelectStar"]];
        imageV.frame=CGRectMake(15*i, 0, 15, 15);
        [self.starView addSubview:imageV];
    }
    if (f>0) {
        UIImageView* imageV=[[UIImageView alloc]initWithImage:[UIImage imageNamed:@"storesStar_2"]];
        imageV.frame=CGRectMake(15*count, 0, 15, 15);
        [self.starView addSubview:imageV];
    }
}

-(CGFloat)setHightWithString:(NSString *)string{
    CGSize size = CGSizeMake(CGRectGetWidth(self.commentLabel.frame),500);
    CGRect labelsize = [string boundingRectWithSize:size
            options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesFontLeading  |NSStringDrawingUsesLineFragmentOrigin//采用换行模式
            attributes:@{NSFontAttributeName: self.commentLabel.font}//传人的字体字典
                                       context:nil];
    self.cellLayoutConstraint.constant = labelsize.size.height;
    self.commentLabel.text = string;
    return labelsize.size.height;
}

@end
