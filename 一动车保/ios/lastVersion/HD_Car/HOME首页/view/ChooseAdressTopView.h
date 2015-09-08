//
//  ChooseAdressTopView.h
//  dizhi
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//


#import <UIKit/UIKit.h>

typedef NS_ENUM(NSUInteger, ShowType) {
    Area = 1,   //市
    Street = 2,//街道
};
@protocol pressButtonDelegate <NSObject>

- (void)pressButtonWith:(ShowType)type;

@end
@interface ChooseAdressTopView : UIView
@property (weak, nonatomic) IBOutlet UIButton *areaButton;
@property (nonatomic,assign)id<pressButtonDelegate> delegate;

@end

