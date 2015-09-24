//
//  YC_SelectTimeViewController.m
//  HD_Car
//
//  Created by hydom on 9/16/15.
//  Copyright (c) 2015 HD_CyYihan. All rights reserved.
//

#import "YC_SelectTimeViewController.h"
#import "HTTPconnect.h"

@interface YC_SelectTimeViewController ()<UITableViewDataSource,UITableViewDelegate>

@property(nonatomic, strong) UITableView * tableView;
@property(nonatomic, strong) NSString * fromServiceTime;
@property(nonatomic, strong) NSMutableArray * dataArray;
@property(nonatomic, assign) NSInteger year;
@property(nonatomic, assign) NSInteger mouth;
@property(nonatomic, assign) NSInteger day;


@end

@implementation YC_SelectTimeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initUI];
    [self initData];
}

-(void)initUI
{
    [self initNavViewWithtitle:@"选择预约时间"];
    WEAKSELF;
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:NO];
    }];
    
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64) style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.backgroundColor = HDfillColor;
    _tableView.rowHeight = 50;
    _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    _tableView.showsVerticalScrollIndicator = NO;
    [self.view addSubview:_tableView];
    
}

-(void)initData
{
    [HTTPconnect sendPOSTHttpWithUrl:getServerDataAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            
            _fromServiceTime=responseObject[@"date"];
            _dataArray = [self getArrayFromDate:_fromServiceTime];
            [_tableView reloadData];
//            if (self.selectTimeView==nil) {
//                self.selectTimeView=[[[NSBundle mainBundle]loadNibNamed:@"JV_selectTimeView" owner:nil options:nil]lastObject];
//                [self.view addSubview:self.selectTimeView];
//                self.selectTimeView.frame=CGRM(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
//            }
//            self.selectTimeView.timeDescription.text=self.fromServiceTime;
//            WEAKSELF;
//            self.selectTimeView.selectTimeBlock=^(NSString* str){
//                weakSelf.periodTime=str;
//                weakSelf.productOrderInfoView.selectTime.text=[NSString stringWithFormat:@"%@ %@",weakSelf.fromServiceTime,weakSelf.periodTime];
//                weakSelf.productOrderInfoView.selectTime.textColor = [UIColor blackColor];
//            };
//            [self.selectTimeView setHidden:NO];
        }else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        warn(@"网络错误");
    }];
}

#pragma mark tableView协议
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _dataArray.count?_dataArray.count:0;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell * cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if (_dataArray) {
        UILabel * label = [[UILabel alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-75, 0, 150, 35)];
        label.textAlignment = NSTextAlignmentCenter;
        label.text = _dataArray[indexPath.row];
        label.centerY = cell.contentView.centerY;
        [cell.contentView addSubview:label];
    }
    return cell;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (self.selectTimeView==nil) {
        self.selectTimeView=[[[NSBundle mainBundle]loadNibNamed:@"JV_selectTimeView" owner:nil options:nil]lastObject];
        [self.view addSubview:self.selectTimeView];
        self.selectTimeView.frame=CGRM(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
    WEAKSELF;
    self.selectTimeView.timeDescription.text=_dataArray[indexPath.row];
    self.selectTimeView.selectTimeBlock = ^(NSString * str){
        NSString * time = [NSString stringWithFormat:@"%@ %@",weakSelf.selectTimeView.timeDescription.text, str];
        if (weakSelf.selectViewController) {
            weakSelf.selectViewController(time);
            [weakSelf.navigationController popViewControllerAnimated:NO];
        }
    };
    [self.selectTimeView setHidden:NO];

}


#pragma mark 对象方法
-(NSMutableArray *)getArrayFromDate:(NSString *)string
{
    NSMutableArray * array = [NSMutableArray new];
    [array addObject:string];
    NSString * yearS = [string substringToIndex:4];
    NSString * mouthS = [string substringWithRange:NSMakeRange(5, 2)];
    NSString * dayS = [string substringFromIndex:8];
    _day = [dayS integerValue];
    _mouth = [mouthS integerValue];
    _year = [yearS integerValue];
    for (int i = 0; i < 6; i ++) {
        _day = [self getNextDayFromDay:_day];
        NSString * nextDay = [NSString stringWithFormat:@"%ld-%.2ld-%ld", (long)_year,(long)_mouth,(long)_day];
        [array addObject:nextDay];
    }
    return array;
}

-(NSInteger)getNextDayFromDay:(NSInteger)day
{
    day = day + 1;
    switch (_mouth) {
        case 1:
            if (day > 31) {
                day = 1;
                _mouth++;
            }
            break;
        case 2:
            if((_year%4==0&&_year%100!=0)||(_year%400==0)) {
                if (day > 29) {
                    day = 1;
                    _mouth++;
                }
            } else {
                if (day > 30) {
                    day = 1;
                    _mouth++;
                }
            }
            break;
        case 3:
            if (day > 31) {
                day = 1;
                _mouth++;
            }
            break;
        case 4:
            if (day > 30) {
                day = 1;
                _mouth++;
            }
            break;
        case 5:
            if (day > 31) {
                day = 1;
                _mouth++;
            }
            break;
        case 6:
            if (day > 30) {
                day = 1;
                _mouth++;
            }
            break;
        case 7:
            if (day > 31) {
                day = 1;
                _mouth++;
            }
            break;
        case 8:
            if (day > 31) {
                day = 1;
                _mouth++;
            }
            break;
        case 9:
            if (day > 30) {
                day = 1;
                _mouth++;
            }
            break;
        case 10:
            if (day > 31) {
                day = 1;
                _mouth++;
            }
            break;
        case 11:
            if (day > 30) {
                day = 1;
                _mouth++;
            }
            break;
        case 12:
            if (day > 31) {
                day = 1;
                _mouth = 1;
                _year++;
            }
            break;
        default:
            break;
    }
    return day;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
