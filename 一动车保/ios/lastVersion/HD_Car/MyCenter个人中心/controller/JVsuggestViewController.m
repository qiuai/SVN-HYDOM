//
//  JVsuggestViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/10.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVsuggestViewController.h"
#import "HDApiUrl.h"
#import "HDNavigationView.h"
#import "HTTPconnect.h"
#import "userDefaultManager.h"
#import "MBProgressHUD.h"
@interface JVsuggestViewController ()<UITextViewDelegate>
@property(nonatomic,weak)UITextView* textview;
@property(nonatomic,weak)UILabel * label;
@end

@implementation JVsuggestViewController



- (void)viewDidLoad {
    [super viewDidLoad];
    [self initNavViewWithtitle:@"反馈"];
    self.view.backgroundColor=COLOR(246, 246, 246, 1.0);
    WEAKSELF;
    [self.navView tapLeftViewWithImageName:nil tapBlock:^{
        [weakSelf.navigationController popViewControllerAnimated:YES];
    }];
    
    UITextView *textview = [[UITextView alloc] initWithFrame:CGRectMake(20, 84, SCREEN_WIDTH-40, 260)];
    textview.backgroundColor=[UIColor whiteColor]; //背景色
    textview.scrollEnabled = NO;    //当文字超过视图的边框时是否允许滑动，默认为“YES”
    textview.editable = YES;        //是否允许编辑内容，默认为“YES”
    textview.delegate = self;       //设置代理方法的实现类
    textview.font=FONT14; //设置字体名字和字体大小;
    textview.returnKeyType = UIReturnKeyDefault;//return键的类型
    textview.keyboardType = UIKeyboardTypeDefault;//键盘类型
    textview.textAlignment = NSTextAlignmentLeft; //文本显示的位置默认为居左
    textview.dataDetectorTypes = UIDataDetectorTypeAll; //显示数据类型的连接模式（如电话号码、网址、地址等）
    textview.textColor = [UIColor grayColor];
    textview.text = @"你的意见是我们前进的动力";//设置显示的文本内容
    [self.view addSubview:textview];
//    textview.layer.backgroundColor = [[UIColor whiteColor] CGColor];
    textview.layer.borderColor = [[UIColor grayColor]CGColor];
    textview.layer.borderWidth = 1.0;
    textview.layer.cornerRadius = 4.0f;
    [textview.layer setMasksToBounds:YES];
    UIButton* submit=[[UIButton alloc]initWithFrame:CGRM(0, SCREEN_HEIGHT-40, SCREEN_WIDTH, 40)];
    [submit setTintColor:[UIColor whiteColor]];
    [submit setTitle:@"提交" forState:0];
    [submit setBackgroundColor:ShareBackColor];
    [submit addTarget:self action:@selector(submit) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:submit];
    self.textview=textview;
    
    UILabel * label = [[UILabel alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-100, 314, 70, 30)];
    label.backgroundColor = [UIColor clearColor];
    label.font=FONT14;
    label.textColor = [UIColor grayColor];
    label.text = @"0/50";
    label.textAlignment = NSTextAlignmentRight;
    [self.view addSubview:label];
    self.label = label;
    
}


-(void)submit{
    if (![self.textview.text isEqualToString:@"你的意见是我们前进的动力"]) {
        NSLog(@"%@", self.textview.text);
        NSMutableDictionary* dict=[NSMutableDictionary dictionaryWithDictionary:[userDefaultManager getUserWithToken]];
        [dict setObject:self.textview.text forKey:@"content"];
        [HTTPconnect sendPOSTHttpWithUrl:userFeedbackAPI parameters:dict success:^(id responseObject) {
            [self.view addSubview:self.HUD];
            self.HUD.labelText =@"提交成功";
            self.HUD.mode=MBProgressHUDModeText;
            [self.HUD showAnimated:YES whileExecutingBlock:^{
                sleep(2);
            } completionBlock:^{
                [self.HUD removeFromSuperview];
                [self.navigationController popViewControllerAnimated:YES];
            }];
        } failure:^(NSError *error) {
            warn(@"提交失败");
        }];

    }
}

//将要开始编辑
- (BOOL)textViewShouldBeginEditing:(UITextView *)textView{
    if (textView.textColor==[UIColor grayColor]) {
        textView.text=@"";
        textView.textColor=[UIColor blackColor];
    }
    return YES;
}

//正在编辑
-(void)textViewDidChange:(UITextView *)textView
{
    if (textView.text.length <= 50) {
        _label.text = [NSString stringWithFormat:@"%d/50", _textview.text.length];

    } else {
        warn(@"超过输入范围");
        [_textview resignFirstResponder];
    }
}



//将要结束编辑
- (BOOL)textViewShouldEndEditing:(UITextView *)textView{
    if (textView.text.length<1) {
        textView.text = @"你的意见是我们前进的动力";
        textView.textColor = [UIColor grayColor];
    }
    return YES;
}


-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{

   [[[UIApplication sharedApplication] keyWindow] endEditing:YES];
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
