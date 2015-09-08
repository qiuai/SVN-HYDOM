//
//  scorePayCell.m
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "scorePayCell.h"

@implementation scorePayCell

- (void)awakeFromNib {
    // Initialization code
}

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        _cellImageView = [[UIImageView alloc] initWithFrame:CGRectMake(10, 5, SCREEN_WIDTH-20, (SCREEN_WIDTH-20)*0.33)];
        [self.contentView addSubview:_cellImageView];
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
