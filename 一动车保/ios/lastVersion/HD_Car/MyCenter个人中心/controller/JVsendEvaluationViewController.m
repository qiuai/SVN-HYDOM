//
//  JVsendEvaluationViewController.m
//  HD_Car
//
//  Created by xingso on 15/8/12.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVsendEvaluationViewController.h"
#import "GetRoundnessImage.h"
#import "JVStarView.h"


#define textDescription @"您对商品的评价"
@interface JVsendEvaluationViewController ()<CircleImageDelegate,UITextViewDelegate>
{
    CGFloat uploadImageX;
}
//@property(nonatomic,strong)NSMutableArray *imageDataArray;
//
@property(nonatomic,strong)NSMutableArray *imageArray;

@property(nonatomic,strong)GetRoundnessImage *selectImage;


@property(nonatomic,strong)UIImageView *iconView;



@property(nonatomic,weak)UITextView* textview;


@property(nonatomic,strong)UIView* showStarView;

@property(nonatomic,strong)UIButton *addButton;


@property(nonatomic,strong)JVStarView* starView;
@end

@implementation JVsendEvaluationViewController



- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor=HDfillColor;
    [self initNavViewWithtitle:@"评价"];
    self.imageArray=[NSMutableArray array];
    self.selectImage = [[GetRoundnessImage alloc] init];
    self.selectImage.delegate = self;
    self.selectImage.editMode = 2;
        [self layoutServicesUI];
    
    UIButton* saveBtn=[UIButton buttonWithType:UIButtonTypeCustom];
    saveBtn.frame=CGRM(0, SCREEN_HEIGHT-50, SCREEN_WIDTH, 50);
    [saveBtn setTitle:@"提交" forState:0];
    saveBtn.backgroundColor=ShareBackColor;
    [saveBtn addTarget:self action:@selector(submitText) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:saveBtn];
}


-(void)layoutServicesUI{
    UIView* seView=[[UIView alloc]initWithFrame:CGRM(0, 64, SCREEN_WIDTH, 100)];
    seView.backgroundColor=[UIColor whiteColor];
    [self.view addSubview:seView];
    self.iconView=[[UIImageView alloc]initWithFrame:CGRM(20, 15, 80, 80)];
    [self.iconView sd_setImageWithURL:imageURLWithPath(self.style==YES?self.dataModel.scimg:self.dataModel.pimg) placeholderImage:[UIImage imageNamed:FillImage]];
    [seView addSubview:self.iconView];
    UILabel* label=[[UILabel alloc]initWithFrame:CGRM(120, 20, SCREEN_WIDTH-135, 60)];
    label.font=FONT14;
    label.textColor=BLACKCOLOR;
    label.numberOfLines=0;
    label.text=self.style==YES?self.dataModel.scname:self.dataModel.pname;
    [seView addSubview:label];
    
    UIView* starView=[[UIView alloc]initWithFrame:CGRM(0, CGRectGetMaxY(seView.frame)+18, SCREEN_WIDTH, 300)];
    self.showStarView=starView;
    starView.backgroundColor=[UIColor whiteColor];
    
    [self.view addSubview:starView];
    
    UILabel* pj=[[UILabel alloc]initWithFrame:CGRM(15, 0, 80, 35)];
    pj.text=@"评价:";
    [starView addSubview:pj];
    pj.font=FONT14;
   
    [self drawStarView];
    
    UIView* line=[[UIView alloc]initWithFrame:CGRM(15, CGRectGetMaxY(pj.frame), SCREEN_WIDTH-30, 1)];
    line.backgroundColor=[UIColor grayColor];
    [starView addSubview:line];
    line.alpha=0.2;
    
    
    UITextView *textview = [[UITextView alloc] initWithFrame:CGRectMake(15, CGRectGetMaxY(line.frame), SCREEN_WIDTH-30, 200-CGRectGetMaxY(line.frame))];
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
    textview.text = textDescription;//设置显示的文本内容
    [self.view addSubview:textview];
    //    textview.layer.backgroundColor = [[UIColor whiteColor] CGColor];
    self.textview=textview;
    [starView addSubview:textview];
   
    
    
    UIButton* addButton=[[UIButton alloc]initWithFrame:CGRM(20, CGRectGetMaxY(textview.frame), 50, 50)];
//    [addButton setTitle:@"添加" forState:0];
    [addButton setTitleColor:[UIColor blackColor] forState:0];
    [addButton setBackgroundImage:[UIImage imageNamed:@"JVcamera"] forState:0];
    [addButton addTarget:self action:@selector(addImage) forControlEvents:UIControlEventTouchUpInside];
    [starView addSubview:addButton];
    
    [addButton.layer setMasksToBounds:YES];
    [addButton.layer setCornerRadius:5.0]; //设置矩形四个圆角半径
    [addButton.layer setBorderWidth:1.0]; //边框宽度
    CGColorRef scolorref=[UIColor grayColor].CGColor;
    [addButton.layer setBorderColor:scolorref];
    uploadImageX=20;
    self.addButton=addButton;
}


#pragma -mark 绘制 星星图
-(void)drawStarView{
    JVStarView *st=[[JVStarView alloc]initWithFrame:CGRM(100, 0, 200, 40)];
    [self.showStarView addSubview:st];
    self.starView=st;
}

#pragma -mark 选图
-(void)addImage{
    
    if (self.imageArray.count<4) {
        [self.selectImage presentImagePickerController];
    }else{
        warn(@"上传图片小于4张");
    }
}
-(void)image:(UIImage *)image imageData:(NSData *)imagedata{
    if (image) {
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        NSDictionary *parameters =[userDefaultManager getUserWithToken];
        [manager POST:uploadImageForEvaluationAPI parameters:parameters constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
            [formData appendPartWithFileData:imagedata name:@"imageFile" fileName:@"imageFile.png" mimeType:@"application/x-png"];
        } success:^(AFHTTPRequestOperation *operation, id responseObject) {
            if (![responseObject isKindOfClass:[NSString class]]) {
                NSString* url=responseObject[@"imgurl"];
                [self.imageArray addObject:url];
                [self addUploadImage:url];
                warn(@"上传成功");
            }else{
                warn(@"上传失败");
            }
        } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
            warn(@"上传失败");
        }];

    }
}


#pragma -mark 添加已经上传图片
-(void)addUploadImage:(NSString*)url{
    CGFloat y=self.addButton.frame.origin.y;
     UIImageView* addImageView=[[UIImageView alloc]initWithFrame:CGRM(uploadImageX, y, 50, 50)];
    [addImageView sd_setImageWithURL:imageURLWithPath(url) placeholderImage:[UIImage imageNamed:FillImage]];
    [self.showStarView addSubview:addImageView];
    uploadImageX+=(10+50);
    self.addButton.x=uploadImageX;
}

#pragma -mark textviewDelegate
//将要开始编辑
- (BOOL)textViewShouldBeginEditing:(UITextView *)textView{
    
    if (textView.textColor==[UIColor grayColor]) {
        textView.text=@"";
        textView.textColor=[UIColor blackColor];
    }
    return YES;
}

//将要结束编辑
- (BOOL)textViewShouldEndEditing:(UITextView *)textView{
    if (textView.text.length<1) {
        textView.text = textDescription;
        textView.textColor = [UIColor grayColor];
    }
    return YES;
}
#pragma -mark 提交
-(void)submitText{
    
    if (self.starView.starCount<1) {
        warn(@"请输入评价星级");
        return;
    }
    if ([self.textview.text isEqualToString:textDescription]||self.textview.text.length<1) {
        warn(@"请输入评价");
        return;
    }
    
    NSMutableDictionary* parameters=[[userDefaultManager getUserWithToken] mutableCopy];
    if (self.imageArray.count>0) {
        NSMutableString* imageURL=[NSMutableString string];
        for (NSString* s in self.imageArray) {
            [imageURL appendFormat:@"%@#",s];
        }
        [imageURL substringToIndex:imageURL.length-1];
        [parameters setObject:imageURL forKey:@"imgurl"];
    }
    [parameters setObject:[NSNumber numberWithInteger:self.starView.starCount] forKey:@"star"];
    [parameters setObject:self.textview.text forKey:@"content"];
    [parameters setObject:_orderID forKey:@"oid"];
    [parameters setObject:self.style==YES?self.dataModel.scid:self.dataModel.pid forKey:@"pid"];
    [parameters setObject:self.style==YES?@"1":@"2" forKey:@"type"];
    
    _po([UtilityMethod JVDebugUrlWithdict:parameters nsurl:sendEvaluationAPI]);
    
  [HTTPconnect sendPOSTHttpWithUrl:sendEvaluationAPI parameters:parameters success:^(id responseObject) {
      if (![responseObject isKindOfClass:[NSString class]]) {
          [self.view addSubview:self.HUD];
          self.HUD.labelText =@"评价成功";
          self.HUD.mode=MBProgressHUDModeText;
          [self.HUD showAnimated:YES whileExecutingBlock:^{
              sleep(2);
          } completionBlock:^{
              [self.HUD removeFromSuperview];
              if (self.evaluationBlock!=nil) {
                  self.evaluationBlock();
              }
              [self.navigationController popViewControllerAnimated:YES];
          }];
      }else{
          warn(responseObject);
      }
  } failure:^(NSError *error) {
      warn(@"提交失败");
  }];

    
}


#pragma -mark 关闭
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
    
    [[[UIApplication sharedApplication] keyWindow] endEditing:YES];
}
@end
