//
//  loginViewController.m
//  HD_Car
//
//  Created by xingso on 15/7/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "loginViewController.h"
#import "HDNavigationView.h"
#import "MBProgressHUD.h"
#import "userDefaultManager.h"
#import "HTTPconnect.h"
#import "HDApiUrl.h"
@interface loginViewController ()<UITextFieldDelegate,MBProgressHUDDelegate>{
    float scrollYforKeyBoard;
    NSInteger recordingTime;
}
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *phoneText;
@property (weak, nonatomic) IBOutlet JVshowkeyboardTextFiled *CaptchaText;
@property (weak, nonatomic) IBOutlet UIButton *getCaptchaButton;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;
@property(nonatomic,strong)HDNavigationView* titleView;
@property (weak, nonatomic) IBOutlet UIView *loginBackView;
@property(weak,nonatomic)UITextField* touchTextField;
@property(nonatomic,strong)MBProgressHUD *HUD;
@property (nonatomic, strong) NSTimer *captchaTimer;
@end
@implementation loginViewController

#pragma -mark 懒加载


-(HDNavigationView *)titleView{
    if (!_titleView) {
        _titleView=[HDNavigationView navigationViewWithTitle:@"登陆"];
        [_titleView tapLeftViewWithImageName:nil tapBlock:^{
            [self dismissViewControllerAnimated:YES completion:nil];
        }];
    }
    return _titleView;
}

#pragma -initBlock
-(void)setSuccessLogin:(loginBlock)successLogin{
    _successLogin=successLogin;
}

#pragma -mark 视图Load
- (void)viewDidLoad {
    [super viewDidLoad];
    self.HUD = [[MBProgressHUD alloc] initWithView:self.view];
    //加载titleView
    [self.view addSubview:self.titleView];
    self.phoneText.placeholder=@"请输入手机号";
    self.CaptchaText.placeholder=@"请输入验证码";
    self.phoneText.wordsMaxLenght=11;
    self.phoneText.keyboardType=UIKeyboardTypeNumberPad;
    self.CaptchaText.wordsMaxLenght=4;
    self.CaptchaText.keyboardType=UIKeyboardTypeNumberPad;
    self.CaptchaText.moveView=self.view;
    self.phoneText.moveView=self.view;
    
    [self.getCaptchaButton addTarget:self action:@selector(sendCaptcha) forControlEvents:UIControlEventTouchUpInside];
    [self.loginButton addTarget:self action:@selector(loginClick) forControlEvents:UIControlEventTouchUpInside];
}
#pragma -mark 发送验证码
-(void)sendCaptcha{
    if (self.phoneText.text.length==11) {
        //            创建定时器
        [self startPainting];
        NSDictionary* cDictionary=[NSDictionary dictionaryWithObjects:@[self.phoneText.text,@"1"] forKeys:@[@"phone",@"type"]];
        [HTTPconnect sendPOSTHttpWithUrl:sendCaptchaAPI parameters:cDictionary success:^(id responseObject) {
            
            [self.view addSubview:self.HUD];
            self.HUD.labelText = @"发送成功,请注意查收";
            self.HUD.mode=MBProgressHUDModeText;
            [self.HUD showAnimated:YES whileExecutingBlock:^{
                sleep(2); // 睡眠2秒
            } completionBlock:^{
                [self.HUD removeFromSuperview];
                [self.getCaptchaButton setUserInteractionEnabled:NO];
            }
             ];
        } failure:^(NSError *error) {
            [self.view addSubview:self.HUD];
            self.HUD.labelText = @"网络异常，请检查网络!!";
            self.HUD.mode=MBProgressHUDModeText;
            [self.HUD showAnimated:YES whileExecutingBlock:^{
                sleep(2); // 睡眠3秒
            } completionBlock:^{
                [self.HUD removeFromSuperview];
            }
             ];
        }];
    }
else{
    [self.view addSubview:self.HUD];
    self.HUD.labelText = @"手机号格式错误！！";
    self.HUD.mode=MBProgressHUDModeText;
    [self.HUD showAnimated:YES whileExecutingBlock:^{
        sleep(2); // 睡眠3秒
    } completionBlock:^{
        [self.HUD removeFromSuperview];
    }
     ];
}


}



#pragma -mark登陆
-(void)loginClick{
    if (self.phoneText.text.length==11&&self.CaptchaText.text.length==4){
        NSDictionary* cDictionary=[NSDictionary dictionaryWithObjects:@[self.phoneText.text,self.CaptchaText.text] forKeys:@[@"phone",@"code"]];
        
        _po([UtilityMethod JVDebugUrlWithdict:cDictionary nsurl:loginAPI]);
        [HTTPconnect sendPOSTHttpWithUrl:loginAPI parameters:cDictionary success:^(id responseObject) {
            if (![responseObject isKindOfClass:[NSString class]]) {
                [self stopPainting];
                NSString* uid=[responseObject objectForKey:@"uid"];
                NSString* token=[responseObject objectForKey:@"token"];
                [userDefaultManager setUser:uid Token:token];
                [self.view addSubview:self.HUD];
                self.HUD.labelText = @"登陆成功";
                self.HUD.mode=MBProgressHUDModeText;
                [self.HUD showAnimated:YES whileExecutingBlock:^{
                    sleep(2); // 睡眠3秒
                } completionBlock:^{
                    if (self.successLogin) {
                        self.successLogin();
                    }
                    [self dismissViewControllerAnimated:NO completion:nil];
                }
                 ];
            }else{
                [self.view addSubview:self.HUD];
                self.HUD.labelText = responseObject;
                self.HUD.mode=MBProgressHUDModeText;
                [self.HUD showAnimated:YES whileExecutingBlock:^{
                    sleep(2); // 睡眠3秒
                } completionBlock:^{
                }
                 ];
            }
        } failure:^(NSError *error) {
            [self.view addSubview:self.HUD];
            self.HUD.labelText = @"网络异常，请检查网络";
            self.HUD.mode=MBProgressHUDModeText;
            [self.HUD showAnimated:YES whileExecutingBlock:^{
                sleep(2); // 睡眠3秒
            } completionBlock:^{
                [self.HUD removeFromSuperview];
            }
             ];
        }];
    }else{
        [self.view addSubview:self.HUD];
        self.HUD.labelText = @"请输入手机号或验证码";
        self.HUD.mode=MBProgressHUDModeText;
        [self.HUD showAnimated:YES whileExecutingBlock:^{
            sleep(2); // 睡眠3秒
        } completionBlock:^{
            [self.HUD removeFromSuperview];
        }
         ];
    }
}
    
    


#pragma -mark 定时器相关
// 定时器执行的方法
- (void)paint:(NSTimer *)paramTimer{
    if (recordingTime==0) {
        [self stopPainting];
        return;
    }
    [self.getCaptchaButton setTitle:[NSString stringWithFormat:@"%ld",recordingTime] forState:UIControlStateNormal];
    recordingTime--;
}

// 开始定时器
- (void) startPainting{
    recordingTime=60;
    self.captchaTimer = [NSTimer timerWithTimeInterval:1.0
                                           target:self
                                         selector:@selector(paint:)
                                         userInfo:nil
                                          repeats:YES];
    [[NSRunLoop currentRunLoop] addTimer:self.captchaTimer forMode:NSRunLoopCommonModes];
}

// 停止定时器
- (void) stopPainting{
    [self.getCaptchaButton setTitle:@"获取验证码" forState:UIControlStateNormal];
    [self.getCaptchaButton setUserInteractionEnabled:YES];
    if (self.captchaTimer != nil){
        // 定时器调用invalidate后，就会自动执行release方法。不需要在显示的调用release方法
        [self.captchaTimer invalidate];
    }
}



#pragma -mark 关闭键盘
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
    if ([self.phoneText isFirstResponder]) {
         [self.phoneText resignFirstResponder];
    }
    if ([self.CaptchaText isFirstResponder]) {
        [self.CaptchaText resignFirstResponder];
    }
}


@end
