//
//  ChangeInforViewController.m
//  HD_Car
//
//  Created by hydom on 15-8-13.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "ChangeInforViewController.h"
#import "ChangeImageView.h"
#import "GetRoundnessImage.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"


@interface ChangeInforViewController ()<CircleImageDelegate>
@property (nonatomic,strong)UITextField * nameTextFild;
@property (nonatomic,strong)ChangeImageView * changeImageView;
@property (nonatomic,strong)GetRoundnessImage * imagePicker;

@end

@implementation ChangeInforViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    if (self.type == ChangeImage) {
        [self initNavViewWithtitle:@"修改头像"];
    }else{
        [self initNavViewWithtitle:@"修改名称"];
        [self.navView tapRightViewWithImageName:@"确定" tapBlock:^{
            if (self.type == ChangeUserName ) {
                [self CHngeNicknameNetworking];
            }else{
                [self.navigationController popViewControllerAnimated:YES];
            }
        }];
    }
    
    
    [self setUpUserInterface];
    // Do any additional setup after loading the view.
}
- (void)setUpUserInterface{
    if (self.type == ChangeUserName) {
        _nameTextFild = [[UITextField alloc] initWithFrame:CGRectMake(15, 64, SCREEN_WIDTH, 40)];
        _nameTextFild.font = [UIFont systemFontOfSize:13];
        _nameTextFild.placeholder = @"请输入";
        _nameTextFild.text = self.changeValue;
        [self.view addSubview:_nameTextFild];
        
        UILabel * lineLabel = [[UILabel alloc] initWithFrame:CGRectMake(15, 104, SCREEN_WIDTH - 30, 1)];
        lineLabel.backgroundColor = [UIColor lightGrayColor];
        [self.view addSubview:lineLabel];
    }else{
        _changeImageView = [[[NSBundle mainBundle]loadNibNamed:@"ChangeImageView" owner:self options:nil]lastObject];
        _changeImageView.frame = CGRectMake(0, 64, SCREEN_WIDTH, SCREEN_HEIGHT-64);
        [self.view addSubview:_changeImageView];
        [_changeImageView setUpUserInterfaceWith:self.changeValue];
        [_changeImageView.changeHeadImageButton addTarget:self action:@selector(clickChnageImageButton) forControlEvents:UIControlEventTouchUpInside];
    
    }

}
- (void)clickChnageImageButton{
    
    if (!_imagePicker) {
        _imagePicker = [[GetRoundnessImage alloc] init];
        _imagePicker.editMode = FSEditModeCircular;
        _imagePicker.delegate = self;
        
    }
    [_imagePicker presentImagePickerController];

}
- (void)CHngeNicknameNetworking{
    NSMutableDictionary * parameters = [NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
    [parameters setObject:_nameTextFild.text forKey:@"nickname"];
    MBProgressHUD * huddd = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    [manager POST:ChangeUserInforAPI parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {

    } success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        if (![responseObject isKindOfClass:[NSString class]]) {
            if ([self.delegate  respondsToSelector:@selector(ChangeWith:value:)]) {
                [self.delegate ChangeWith:ChangeUserName value:_nameTextFild.text];
            }
            JVuserModel* m=[userDefaultManager getUserModel];
            m.nickname = _nameTextFild.text;
            [userDefaultManager saveUserModel:m];
            [huddd hide:YES];
            [self.view addSubview:self.HUD];
            self.HUD.labelText =@"修改成功";
            self.HUD.mode=MBProgressHUDModeText;
            [self.HUD showAnimated:YES whileExecutingBlock:^{
                sleep(1);
            } completionBlock:^{
                [self.HUD removeFromSuperview];
                [self.navigationController popViewControllerAnimated:NO];
            }];
            
            
        }else{
            warn(@"修改失败");
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        warn(@"修改失败");
        [huddd hide:YES];
    }];
    
}






-(void)image:(UIImage *)image imageData:(NSData *)imagedata{
    
    if (image) {
        MBProgressHUD * hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        NSDictionary *parameters =[userDefaultManager getUserWithToken];
        [manager POST:ChangeUserInforAPI parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
            [formData appendPartWithFileData:imagedata name:@"photo" fileName:@"phote.jpeg" mimeType:@"image/jpeg"];
        } success:^(AFHTTPRequestOperation *operation, id responseObject) {
            if (![responseObject isKindOfClass:[NSString class]]) {
                [self.changeImageView.headImageView setImage:image];
                if ([self.delegate respondsToSelector:@selector(ChangeWith:value:)]) {
                    [self.delegate ChangeWith:ChangeImage value:image];
                }
                JVuserModel* m=[userDefaultManager getUserModel];
                m.update=YES;
                m.userImage=image;
                [userDefaultManager saveUserModel:m];
                warn(@"修改成功");
                [hud hide:YES];
                [self.HUD showAnimated:YES whileExecutingBlock:^{
                    sleep(1);
                } completionBlock:^{
                    [self.HUD removeFromSuperview];
                    [self.navigationController popViewControllerAnimated:NO];
                }];
            }else{
                warn(@"修改失败");
                [hud hide:YES];
            }
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            warn(@"修改失败");
            [hud hide:YES];
        }];

    }
    
}


@end
