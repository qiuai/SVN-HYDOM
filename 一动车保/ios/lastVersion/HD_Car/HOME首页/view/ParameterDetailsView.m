//
//  ParameterDetailsView.m
//  HD_Car
//
//  Created by hydom on 15-7-20.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "ParameterDetailsView.h"

@implementation ParameterDetailsView
- (instancetype)initWith:(NSArray *)array{
    self = [super init];
    if (self) {
        [self setUpUserInterfaceWith:array];
    }
    return self;
}

- (instancetype)initWithFitCar:(NSArray *)array{
    self = [super init];
    if (self) {
        [self fitCarArray:array];
    }
    return self;
}

- (void)fitCarArray:(NSArray *)array{
    if  (array.count == 0){
        return;
    }
    self.fitCarHeigth = 5;
    int tamp=1;
    for (NSDictionary* dic in array){
        if (tamp%2 == 1 && tamp > 1) {
            self.fitCarHeigth += 10;
        }
        [self fitCarWith:dic[@"cmname"] value:[dic objectForKey:@"cmname"]];
        tamp++;
    }
    self.fitCarHeigth += 5;
}


- (void)setUpUserInterfaceWith:(NSArray *)array{
    if  (array.count == 0){
        return;
    }
    self.ParameterHeigth = 5;
    int tamp=1;
    for (NSDictionary* dic in array){
        if (tamp%2 == 1 && tamp > 1) {
            self.ParameterHeigth += 10;
        }
        [self buildViewWith:dic[@"paramname"]  value:[dic objectForKey:@"paramvalue"]];
        tamp++;
    }
    
    self.ParameterHeigth += 5;
    
}

-(void)fitCarWith:(NSString *)key value:(NSString *)value{
//    UILabel * keyLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, self.fitCarHeigth, 65, 25)];
//    keyLabel.text = key;
//    keyLabel.font = [UIFont systemFontOfSize:13];
//    [self addSubview:keyLabel];
    
    UILabel * valueLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, self.fitCarHeigth, SCREEN_WIDTH - 35, 25)];
    valueLabel.text = value;
    valueLabel.font = [UIFont systemFontOfSize:13];
    valueLabel.textColor = [UIColor darkGrayColor];
    [self addSubview:valueLabel];
    _pf(CGRectGetMaxY(valueLabel.frame));
    self.fitCarHeigth = CGRectGetMaxY(valueLabel.frame);

}

- (void)buildViewWith:(NSString *)key value:(NSString *)value{
    
    UILabel * keyLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, self.ParameterHeigth, 65, 25)];
    keyLabel.text = key;
    keyLabel.font = [UIFont systemFontOfSize:13];
    [self addSubview:keyLabel];
    
    UILabel * valueLabel = [[UILabel alloc] initWithFrame:CGRectMake(CGRectGetMaxX(keyLabel.frame), self.ParameterHeigth, SCREEN_WIDTH - 85, 25)];
    valueLabel.text = value;
    valueLabel.font = [UIFont systemFontOfSize:13];
    valueLabel.textColor = [UIColor darkGrayColor];
    [self addSubview:valueLabel];
    _pf(CGRectGetMaxY(valueLabel.frame));
    self.ParameterHeigth = CGRectGetMaxY(valueLabel.frame);
}


@end
