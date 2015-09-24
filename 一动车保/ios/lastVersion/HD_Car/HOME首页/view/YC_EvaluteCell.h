//
//  EvaluteCell.h
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface YC_EvaluteCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIView *imagesView;

@property (weak, nonatomic) IBOutlet UIImageView *headImageView;
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
@property (weak, nonatomic) IBOutlet UILabel *carLabel;
@property (weak, nonatomic) IBOutlet UILabel *commentLabel;
@property (weak, nonatomic) IBOutlet UIView *starView;
@property (weak, nonatomic) IBOutlet UILabel *timeLable;
@property (nonatomic, assign) CGFloat cellLabelHight;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *cellLayoutConstraint;
-(void)drawStarWithInt:(NSInteger)index;
-(void)drawNoStarWithView;
-(CGFloat)setHightWithString:(NSString *)string;
-(void)setImageListWith:(NSArray *)imglist;
+(CGFloat)getCellHight:(NSString *)string;
@end
