//
//  GetRoundnessImage.h
//  IMKanKe
//
//  Created by kanke on 15/5/23.
//  Copyright (c) 2015年 kanke. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FSMediaPicker.h"
@protocol CircleImageDelegate <NSObject>
@optional
- (void)image:(UIImage *)image imageData:(NSData *)imagedata;

@end


@interface GetRoundnessImage : NSObject<FSMediaPickerDelegate>
@property (nonatomic,strong)NSData * imageData;
@property (nonatomic,strong)UIImage * image;
@property (nonatomic)FSEditMode editMode;
@property (nonatomic,weak)id <CircleImageDelegate> delegate;
- (void)presentImagePickerController;

@end
