//
//  HomeServicesView.h
//  HD_Car
//
//  Created by xingso on 15/7/8.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HomeServicesView : UIView
@property (weak, nonatomic) IBOutlet UIView *washCarView;
@property (weak, nonatomic) IBOutlet UIView *goHomeServicesView;

/*第一个到第十个服务 IBOutlet*/
@property (weak, nonatomic) IBOutlet UIImageView *fisrtServicesIcon;
@property (weak, nonatomic) IBOutlet UILabel *fisrtServicesTitle;
@property (weak, nonatomic) IBOutlet UILabel *fisrtServiceInfoLabel;
@property (weak, nonatomic) IBOutlet UIImageView *secondServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *secondServiceTitle;
@property (weak, nonatomic) IBOutlet UILabel *secondServiceInfoLabel;
@property (weak, nonatomic) IBOutlet UIImageView *thirdServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *thirdServiceTitle;
@property (weak, nonatomic) IBOutlet UIImageView *fourthServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *fourthServiceTitle;
@property (weak, nonatomic) IBOutlet UIImageView *fifthServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *fifthServiceTItle;
@property (weak, nonatomic) IBOutlet UIImageView *sixthServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *sixthServiceTitle;
@property (weak, nonatomic) IBOutlet UIImageView *seventhServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *seventhServiceTitle;
@property (weak, nonatomic) IBOutlet UIImageView *eighthServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *eighthServiceTitle;
@property (weak, nonatomic) IBOutlet UIImageView *ninthServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *ninthServiceTitle;
@property (weak, nonatomic) IBOutlet UIImageView *tenthServiceIcon;
@property (weak, nonatomic) IBOutlet UILabel *tenthServiceTitle;


/**服务点击view 3-10*/


@property (weak, nonatomic) IBOutlet UIView *tapServicesView1;
@property (weak, nonatomic) IBOutlet UIView *tapServicesView2;
@property (weak, nonatomic) IBOutlet UIView *tapServicesView3;
@property (weak, nonatomic) IBOutlet UIView *tapServicesView4;
@property (weak, nonatomic) IBOutlet UIView *tapServicesView5;
@property (weak, nonatomic) IBOutlet UIView *tapServicesView6;
@property (weak, nonatomic) IBOutlet UIView *tapServicesView7;
@property (weak, nonatomic) IBOutlet UIView *tapServicesView8;


//提供 vc 给予cell 事件处理
@property(weak,nonatomic)id viewController;

//增加
@property (nonatomic,assign) SEL  tapServicesViewWithtag;

@end
