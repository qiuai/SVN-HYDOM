//
//  EvaluteCell.m
//  HD_Car
//
//  Created by hydom on 8/13/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_EvaluteCell.h"
#import "UIImageView+WebCache.h"
#import "HDApiUrl.h"
@interface YC_EvaluteCell()
@property (weak, nonatomic) IBOutlet UIImageView *imageView1;
@property (weak, nonatomic) IBOutlet UIImageView *imageView2;
@property (weak, nonatomic) IBOutlet UIImageView *imageView3;

@end


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
    self.cellLayoutConstraint.constant = labelsize.size.height+5;
    self.commentLabel.text = string;
    return labelsize.size.height;
}

+(CGFloat)getCellHight:(NSString *)string{
    YC_EvaluteCell * cell = [[NSBundle mainBundle] loadNibNamed:@"YC_EvaluteCell" owner:nil options:nil].lastObject;
    CGSize size = CGSizeMake(CGRectGetWidth(cell.commentLabel.frame),500);
    CGRect labelsize = [string boundingRectWithSize:size
                                            options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesFontLeading  |NSStringDrawingUsesLineFragmentOrigin//采用换行模式
                                         attributes:@{NSFontAttributeName: cell.commentLabel.font}//传人的字体字典
                                            context:nil];
    return labelsize.size.height;
}

-(void)drawNoStarWithView
{
    UIImage * image = [self imageWithTintColor:[UIColor lightGrayColor] image:[UIImage imageNamed:@"storesSelectStar"]];
    for (int i = 0; i < 5; i ++) {
        UIImageView* imageV=[[UIImageView alloc]initWithImage:image];
        imageV.frame=CGRectMake(15*i, 0, 15, 15);
        [self.starView addSubview:imageV];
    }
}

- (UIImage *)imageWithTintColor:(UIColor *)tintColor image:(UIImage *)image
{
    //We want to keep alpha, set opaque to NO; Use 0.0f for scale to use the scale factor of the device’s main screen.
    UIGraphicsBeginImageContextWithOptions(image.size, NO, 0.0f);
    [tintColor setFill];
    CGRect bounds = CGRectMake(0, 0, image.size.width, image.size.height);
    UIRectFill(bounds);
    
    //Draw the tinted image in context
    [image drawInRect:bounds blendMode:kCGBlendModeDestinationIn alpha:1.0f];
    
    UIImage *tintedImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return tintedImage;
}


-(void)setImageListWith:(NSArray *)imglist;
{
    for (int i = 0; i < [imglist count]; i ++) {
        NSString* str=[NSString stringWithFormat:@"imageView%d",i+1];
        UIImageView * imageView =[self valueForKey:str];
        [imageView sd_setImageWithURL:imageURLWithPath(imglist[i][@"img"]) placeholderImage:[UIImage imageNamed:FillImage]];
    }
}

@end
