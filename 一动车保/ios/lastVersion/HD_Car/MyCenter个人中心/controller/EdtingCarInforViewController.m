//
//  EdtingCarInforViewController.m
//  CarManager
//
//  Created by hydom on 15-7-14.
//  Copyright (c) 2015年 hydom03. All rights reserved.
//

#import "EdtingCarInforViewController.h"
#import "userDefaultManager.h"
#import "globalServicesModel.h"
#import "EdtingCarsInforView.h"
#import "HDNavigationView.h"
#import "selectCarController.h"
#import "HDApiUrl.h"
#import "UIImageView+WebCache.h"
#import "carOderViewController.h"
#import "signValueObject.h"
#import "MBProgressHUD.h"
#import "ChooseServerViewController.h"
#import "HDApiUrl.h"
#import "carListViewController.h"
#import "HTTPconnect.h"
#import "YC_SelectServerViewController.h"
#import "YC_HotSaleViewController.h"
#import "YC_HotSaleViewController.h"
#import "YC_HotGoodsViewController.h"

@interface EdtingCarInforViewController ()<UITextFieldDelegate,ZHPickViewDelegate>
{
    CGFloat _keyBordHeigth;
}
@property (nonatomic,strong)EdtingCarsInforView *edtingView;
@property (nonatomic,strong)HDNavigationView * nav;
@property (nonatomic,strong)UIScrollView *scrollView;
@property (nonatomic,weak)UITextField * currentTextField;
@property(nonatomic,strong)MBProgressHUD*  HUD;

@end

@implementation EdtingCarInforViewController
-(MBProgressHUD *)HUD{
    if (!_HUD) {
        _HUD = [[MBProgressHUD alloc] initWithView:self.view.window];
    }
    return _HUD;
}


#pragma -mark init
- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = COLOR(220, 220, 220, 1);
    _nav = [HDNavigationView navigationViewWithTitle:@"车型信息"];
    [self.view addSubview:_nav];
    WEAKSELF;
    [_nav tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    [self setUpUserInterface];
    [self datePickViewSelector];
    [_pickView remove];
    [self initData];
}

//日期选择器
- (void)datePickViewSelector
{
    [self.view endEditing:YES];
    [_pickView remove];
    NSDate * date = [NSDate date];
    _pickView = [[ZHPickView alloc]initDatePickWithDate:date
                                         datePickerMode:UIDatePickerModeDate
                                     isHaveNavControler:NO];
    _pickView.delegate = self;
    [_pickView show];
}

#pragma mark ZhpickViewDelegate methods
- (void)toobarDonBtnHaveClick:(ZHPickView *)pickView resultString:(NSString *)resultString
{
    self.edtingView.startTimeTextField.text = resultString;
}

- (void)setUpUserInterface{
    self.edtingView = [[[NSBundle mainBundle]loadNibNamed:@"EdtingCarsInforView" owner:self options:nil]lastObject];
    self.edtingView.frame=CGRM(0, CGRectGetMaxY(self.nav.frame), SCREEN_WIDTH, 300);
//    self.edtingView.pushVC = self;
    self.edtingView.vc = self;
    self.edtingView.carColorTextField.delegate = self;
    self.edtingView.carNumberTextField.delegate = self;
    self.edtingView.carOilTextField.delegate = self;
    self.edtingView.carOutPutTextField.delegate = self;
    self.edtingView.carRunTextField.delegate = self;
    [self.edtingView setUpUserInterfaceWith:_model];
    [self.edtingView.comeBackCarView addTapGestureRecognizerWithTarget:self action:@selector(backSelectCarVC)];
    [self.view addSubview:_edtingView];
    [self.view bringSubviewToFront:_nav];
    UIButton* saveBtn=[UIButton buttonWithType:UIButtonTypeCustom];
    saveBtn.frame=CGRM(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 50);
    [saveBtn setTitle:@"保存" forState:0];
    saveBtn.backgroundColor=ShareBackColor;
    [saveBtn addTarget:self action:@selector(saveCar:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:saveBtn];
}

#pragma -mark 数据初始化
-(void)initData{
    
    signValueObject* sign=[signValueObject shareSignValue];
    [sign.saveDictionary setValue:self.model.ucid forKey:@"carModelUcid"];
    [self.edtingView.nameButton setTitle:self.model.cbname forState:0];
    NSURL* url=imageURLWithPath(self.model.cbimageURL);
    [self.edtingView.carImageView sd_setImageWithURL:url placeholderImage:[UIImage imageNamed:FillImage]];
}

#pragma -mark 返回选车
-(void)backSelectCarVC{
    NSInteger index=[[self.navigationController viewControllers]indexOfObject:self];
    UIViewController* popVC1=[self.navigationController viewControllers][index-1];
    if ([popVC1 isKindOfClass:[carListViewController class]]) {
        selectCarController* selectCar=[[selectCarController alloc]init];
        [self.navigationController pushViewController:selectCar animated:NO];
        return;
    }
    UIViewController* popVC=[self.navigationController viewControllers][index-3];
    if ([popVC isKindOfClass:[selectCarController class]] ) {
        selectCarController* orVC=(selectCarController*)popVC;
        [self.navigationController popToViewController:orVC animated:YES];
    }
}

#pragma -mark 保存 跳转
-(void)saveCar:(UIButton*)btn{
    [[self class] cancelPreviousPerformRequestsWithTarget:self selector:@selector(doSaveCar) object:btn];
    [self performSelector:@selector(doSaveCar) withObject:btn afterDelay:0.2f];
}

-(void)doSaveCar{
    //    逻辑判断
    //    if (self.edtingView.carOutPutTextField.text.length==0) {
    //        warn(@"请输入排量");
    //        return;
    //    }
    //    if (self.edtingView.carOilTextField.text.length==0) {
    //        warn(@"请输入油耗");
    //        return;
    //    }
    //    if (self.edtingView.carRunTextField.text.length==0) {
    //        warn(@"请输入行驶里程");
    //        return;
    //    }
    //
    NSString* number=@"^\\d{4}-\\d{1,2}-\\d{1,2}$";
    NSPredicate *numberPre = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",number];
    if(![numberPre evaluateWithObject:self.edtingView.startTimeTextField.text]){
        warn(@"上路日期格式错误");
        return;
    }
    if (self.edtingView.startTimeTextField.text.length==0) {
        warn(@"请输入上路时间");
        return;
    }
    if (self.edtingView.carNumberTextField.text.length==0) {
        warn(@"请输入车牌");
        return;
    }
    if (self.edtingView.carColorTextField.text.length==0) {
        warn(@"请输入颜色");
        return;
    }
    
    //提交
    self.model.engines=[NSNumber numberWithFloat:[self.edtingView.carOutPutTextField.text floatValue]] ;
    self.model.fuel=[NSNumber numberWithFloat:[self.edtingView.carOilTextField.text floatValue]];
    self.model.drange=[NSNumber numberWithFloat:[self.edtingView.carRunTextField.text floatValue]];
    self.model.date=self.edtingView.startTimeTextField.text;
    self.model.color=self.edtingView.carColorTextField.text;
    self.model.plateNumber=self.edtingView.carNumberTextField.text;
    
    NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    signValueObject *sign=[signValueObject shareSignValue];
    //判断保存默认车型
    [dict setObject:[sign getIsDefaultCar]==NO?[NSNumber numberWithInteger:0]:[NSNumber numberWithInteger:1] forKey:@"defaultCar"];
    [dict setObject:self.model.color forKey:@"color"];
    [dict setObject:self.model.plateNumber forKey:@"plateNumber"];
    [dict setObject:self.model.fuel forKey:@"fuel"];
    [dict setObject:self.model.drange forKey:@"drange"];
    [dict setObject:self.model.engines forKey:@"engines"];
    [dict setObject:self.model.date forKey:@"date"];
    [dict setObject:self.model.cmid forKey:@"cmid"];
    if (self.pushVcStyle==pushStyleForUpdate) {
        if (self.model.ucid==nil) {
            signValueObject* sign=[signValueObject shareSignValue];
            [dict setObject:sign.saveDictionary[@"carModelUcid"] forKey:@"ucid"];
        }else{
            [dict setObject:self.model.ucid forKey:@"ucid"];
        }
    }
    NSString* url=self.pushVcStyle==pushStyleForUpdate?carEditAPI:carsaveAPI;
    //    _po([UtilityMethod JVDebugUrlWithdict:dict nsurl:url]);
    [HTTPconnect sendPOSTHttpWithUrl:url parameters:dict success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            self.model.ucid=[responseObject objectForKey:@"ucid"];
            [sign SetDefaultCar:NO];
            [self pushVC];
        }else{
            warn(responseObject);
        }
    } failure:^(NSError *error) {
        warn(@"请检查网络");
    }];
    
}



#pragma -mark 跳转界面 判断
-(void)pushVC{
    NSInteger index=[[self.navigationController viewControllers]indexOfObject:self];
    //后面几次车管家修改车界面
    UIViewController* popVC1=[self.navigationController viewControllers][index-1];
    if ([popVC1 isKindOfClass:[carListViewController class]] ) {
        [self.navigationController popToViewController:popVC1 animated:YES];
        return;
    }
    //后面几次车管家新增车
    UIViewController* popVC2=[self.navigationController viewControllers][index-4];
    if ([popVC2 isKindOfClass:[carListViewController class]] ) {
        [self.navigationController popToViewController:popVC2 animated:YES];
        return;
    }
    //第一次 进入选车界面
    signValueObject* sign=[signValueObject shareSignValue];
    if (sign.saveDictionary[@"pushHotVC"]!=nil) {
        NSInteger c=[sign.saveDictionary[@"pushHotVC"] integerValue];
        if (c==1) {
            YC_HotSaleViewController * vc = [[YC_HotSaleViewController alloc] init];
            vc.car=self.model;
            [self.navigationController pushViewController:vc animated:NO];
        }else{
            YC_HotGoodsViewController * vc =[[YC_HotGoodsViewController alloc] init];
            vc.car=self.model;
            NSArray* _redHotArray=sign.saveDictionary[@"pushHotVCData"];
            switch (c) {
                case 2:
                    vc.pcid = _redHotArray[0][@"pcid"];
                    vc.pname = _redHotArray[0][@"pcname"];
                    break;
                case 3:
                    vc.pcid = _redHotArray[1][@"pcid"];
                    vc.pname = _redHotArray[1][@"pcname"];
                    break;
                case 4:
                    vc.pcid = _redHotArray[2][@"pcid"];
                    vc.pname = _redHotArray[2][@"pcname"];
                    break;
                default:
                    vc.pcid = _redHotArray[3][@"pcid"];
                    vc.pname = _redHotArray[3][@"pcname"];
                    break;
            }
            [self.navigationController pushViewController:vc animated:NO];
            
        }
        return;
    }
    
    
    
    
    globalServicesModel* servicesModel=[sign getSelectGlobalServiceModel];
    switch ([sign.servicesModelArray indexOfObject:servicesModel]) {
        case 0:
        {
            carOderViewController* vc=[[carOderViewController alloc]init];
            vc.selectCarmodel=self.model;
            [self.navigationController pushViewController:vc animated:NO];
        }
            break;
        case 1:
        {
            ChooseServerViewController* vc=[[ChooseServerViewController alloc]init];
            vc.carModel=self.model;
            [self.navigationController pushViewController:vc animated:NO];
        }
            break;
        default:
        {
            YC_SelectServerViewController* vc=[[YC_SelectServerViewController alloc]init];
            vc.carModel = self.model;
            vc.selectArray =[NSArray arrayWithObject:[UtilityMethod getObjectData:servicesModel]];
            [self.navigationController pushViewController:vc animated:NO];
        }
            break;
    }
}


#pragma -mark 关闭键盘
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
    if(_pickView)
    {
        [_pickView remove];
    }
    [self.view endEditing:YES];
}

- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField
{
    if(_pickView)
    {
        [_pickView remove];
    }
    return YES;
}

@end
