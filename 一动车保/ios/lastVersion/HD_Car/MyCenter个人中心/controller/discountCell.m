//
//  discountCell.m
//  
//
//  Created by hydom on 8/13/15.
//
//

#import "discountCell.h"

@implementation discountCell

- (void)awakeFromNib {
    // Initialization code
}

-(instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        _cellImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 5, SCREEN_WIDTH, 0.33*SCREEN_WIDTH)];
        [self.contentView addSubview:_cellImageView];
        _cellPastImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 80, 80)];
        _cellPastImageView.center = CGPointMake(120, _cellImageView.centerY);
        [_cellImageView addSubview:_cellPastImageView];
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
