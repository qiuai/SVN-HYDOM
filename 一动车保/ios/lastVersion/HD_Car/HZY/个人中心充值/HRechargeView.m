//
//  HRechargeView.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "HRechargeView.h"
#import "HRechargeTableViewCell.h"

@implementation HRechargeView
-(void)awakeFromNib{
    
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.dataSource = [NSMutableArray array];
    self.buttonTitleArray = @[@"全部",@"充值",@"消费"];
    UITapGestureRecognizer * tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapView)];
    [self.ButtonView addGestureRecognizer:tap];
    self.ButtonView.hidden = YES;
    _typeArray = [NSMutableArray arrayWithArray:@[@"0",@"1",@"2"]];

}
- (void)tapView{
    self.ButtonView.hidden = YES;

}
- (IBAction)typeButton:(UIButton *)sender {
    
    if (sender.tag - 50 == 0) {
        self.ButtonView.hidden = !self.ButtonView.hidden;
    }else{
        self.ButtonView.hidden = YES;
        NSString * string = _typeArray[sender.tag -50];
        [_typeArray removeObjectAtIndex:sender.tag-50];
        [_typeArray insertObject:string atIndex:0];
        self.type = [string integerValue];
        if ([self.delegate respondsToSelector:@selector(ChangeNetWorkingWith:)]) {
            [self.delegate ChangeNetWorkingWith:self.type];
        }
    }
    NSMutableArray * array = [NSMutableArray arrayWithArray:self.buttonTitleArray];
    [array removeObject:_buttonTitleArray[self.type]];
    for (NSInteger tag = 50 ; tag < 53; tag ++) {
        UIButton * button = (UIButton *)[self viewWithTag:tag];
        NSString * string = _typeArray[tag -50];
        
        [button setTitle:self.buttonTitleArray[[string integerValue]] forState:0];
        
    }
    
    
}
#pragma mark----tableView
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{

    return 45;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{

    return _dataSource.count;
}
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    
    return 1;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{

    static NSString *CellIdentifier = @"HRechargeTableViewCell";
        HRechargeTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        if (cell == nil) {
            //通过xib的名称加载自定义的cell
            cell = [[[NSBundle mainBundle] loadNibNamed:@"HRechargeTableViewCell" owner:self options:nil] lastObject];
        }
    [cell setUpUserInterface:_dataSource[indexPath.row]];
        return cell;

}

@end
