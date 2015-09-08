//
//  YC_SelectServerCell.m
//  HD_Car
//
//  Created by hydom on 7/30/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_SelectServerCell.h"
#import "UIImageView+WebCache.h"
#import "HDApiUrl.h"
#import "YC_GoodsModel.h"
@interface YC_SelectServerCell()

@property (weak, nonatomic) IBOutlet UIButton *addButton;
@property (weak, nonatomic) IBOutlet UIButton *subButton;
@property (weak, nonatomic) IBOutlet UIImageView *leftImageView;
@property (weak, nonatomic) IBOutlet UILabel *number;
@property (weak, nonatomic) IBOutlet UILabel *price;
@property (weak, nonatomic) IBOutlet UILabel *title;

@end

@implementation YC_SelectServerCell

- (void)awakeFromNib {
    // Initialization code
    [self.addButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [self.subButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    self.addButton.titleLabel.font = [UIFont systemFontOfSize:17];
    self.subButton.titleLabel.font = [UIFont systemFontOfSize:20];
    self.addButton.layer.borderWidth = 1;
    self.subButton.layer.borderWidth = 1;
    self.addButton.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.subButton.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.addButton.layer.cornerRadius = 12.5;
    self.subButton.layer.cornerRadius = 12.5;
    [self.deleteButton setImage:[UIImage imageNamed:@"商品delete.png"] forState:UIControlStateNormal];
    [self.deleteButton setImageEdgeInsets:UIEdgeInsetsMake(10, 10, 10, 10)];
}

-(void)setCellDataSource{
    if (_dataSource) {
        self.number.text =[NSString stringWithFormat:@"%ld",_dataSource.myCount];
        self.title.text = _dataSource.pname;
        self.price.text = [NSString stringWithFormat:@"￥ %@",globalPrices(_dataSource.price)];
        [self.leftImageView sd_setImageWithURL:imageURLWithPath(_dataSource.pimage)];
    }
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (IBAction)addButton:(UIButton *)sender {
    NSInteger index = [self.number.text intValue];
    if (index < 999) {
        index ++;
        self.number.text = [NSString stringWithFormat:@"%ld", (long)index];
        //传递给 外部处理 加减数量
        UITableViewCell* cell=(UITableViewCell*)sender.superview.superview;
        [self.viewController performSelector:self.sumMethod withObject:cell];
    }
}

- (IBAction)subButton:(UIButton *)sender {
    NSInteger index = [self.number.text intValue];
    if (index > 1) {
        index --;
        self.number.text = [NSString stringWithFormat:@"%ld", (long)index];
        //传递给 外部处理 加减数量
        UITableViewCell* cell=(UITableViewCell*)sender.superview.superview;
        [self.viewController performSelector:self.subtractMethod withObject:cell];
    }
}


@end
