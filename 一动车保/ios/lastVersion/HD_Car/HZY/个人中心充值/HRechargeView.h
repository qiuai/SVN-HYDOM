//
//  HRechargeView.h
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TypeButton.h"
#import "FreeModel.h"
@protocol ChangeNetWorkingTypeDelegate <NSObject>

- (void)ChangeNetWorkingWith:(FreeNetWorkingType)type;

@end
@interface HRechargeView : UIView<UITableViewDelegate,UITableViewDataSource>
@property (weak, nonatomic) IBOutlet TypeButton *typeButton;
@property (weak, nonatomic) IBOutlet UILabel *moneylABEL;
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UIView *ButtonView;
@property (nonatomic,strong)NSMutableArray * typeArray;
@property (nonatomic,strong)NSArray * buttonTitleArray;
@property (nonatomic,strong)NSMutableArray * dataSource;
@property (nonatomic,assign)FreeNetWorkingType type;
@property (nonatomic,weak)id <ChangeNetWorkingTypeDelegate> delegate;

@end
