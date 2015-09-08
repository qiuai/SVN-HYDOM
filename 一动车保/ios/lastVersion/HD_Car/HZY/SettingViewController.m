//
//  SettingViewController.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "SettingViewController.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
#import "userDefaultManager.h"
#import "SettingModel.h"
#import "MJExtension.h"
#import <MBProgressHUD.h>
#import "HSettingView.h"
#import "ChangeInforViewController.h"
#import "JVmyCancelOrderViewController.h"

@interface SettingViewController ()<ChangeInforDelegate>

@property (nonatomic,strong)SettingModel * model;
@property (nonatomic,strong)HSettingView * settingView;

@end

@implementation SettingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self initNavViewWithtitle:@"设置"];
    
    [self setUpUserInterface];
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [self initData];
}

- (void)initData{
    MBProgressHUD *  hud =  [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    [HTTPconnect sendPOSTHttpWithUrl:GetUserInforAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        
        if (![responseObject isKindOfClass:[NSString class]]) {
            _model=[SettingModel objectWithKeyValues:responseObject];
            [_settingView setUpUserInterfaceWith:_model];
        }else{
            warn(responseObject);
        
        }
        [hud hide:YES afterDelay:0.5];
    } failure:^(NSError *error) {
        _po(error);
        [hud hide:YES afterDelay:0.5];
    }];

}
- (void)setUpUserInterface{
    
    _settingView = [[[NSBundle mainBundle]loadNibNamed:@"HSettingView" owner:self options:nil]lastObject];
    _settingView.frame = CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64);
    [self.view addSubview:_settingView];
    [_settingView.changeImageButton addTarget:self action:@selector(clickChangeImageButton) forControlEvents:UIControlEventTouchUpInside];
    [_settingView.changeNickNameButton addTarget:self action:@selector(clickNicknameButton) forControlEvents:UIControlEventTouchUpInside];
    _settingView.viewController=self;
    _settingView.Method=@selector(signout);
    

}
-(void)clickChangeImageButton{

    ChangeInforViewController * changeVC = [[ChangeInforViewController alloc] init];
    changeVC.type = ChangeImage;
    changeVC.changeValue = _model.photo;
    changeVC.delegate = self;
    [self.navigationController pushViewController:changeVC animated:NO];
    

}
- (void)clickNicknameButton{
    
    ChangeInforViewController * changeVC = [[ChangeInforViewController alloc] init];
    changeVC.type = ChangeUserName;
    changeVC.changeValue = _model.nickname;
    changeVC.delegate = self;
    [self.navigationController pushViewController:changeVC animated:NO];

}
-(void)ChangeWith:(ChangeInforType)type value:(id)value{
    if (type == ChangeImage) {
        [self.settingView.headImageView setImage:value];
    }else{
        self.settingView.userNameLabel.text = value;
    }

}

#pragma -mark 退出登陆

-(void)signout{
//注销登陆
    [HTTPconnect sendPOSTHttpWithUrl:signoutAPI parameters:[userDefaultManager getUserWithToken] success:^(id responseObject) {
        if (![responseObject isKindOfClass:[NSString class]]) {
            
        }else{
        }
    } failure:^(NSError *error) {
    }];
    [self.view addSubview:self.HUD];
    self.HUD.labelText =@"退出成功";
    self.HUD.mode=MBProgressHUDModeText;
    [self.HUD showAnimated:YES whileExecutingBlock:^{
        sleep(2);
    } completionBlock:^{
        [self.HUD removeFromSuperview];
        [self.navigationController popViewControllerAnimated:YES];
        [userDefaultManager logout];
        [userDefaultManager logoutPersonNumber];
        [userDefaultManager saveUserModel:nil];
    }];
    
    
    
}
@end
