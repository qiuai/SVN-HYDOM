//
//  GetRoundnessImage.m
//  IMKanKe
//
//  Created by kanke on 15/5/23.
//  Copyright (c) 2015年 kanke. All rights reserved.
//

#import "GetRoundnessImage.h"

@implementation GetRoundnessImage
- (void)presentImagePickerController{
    FSMediaPicker *mediaPicker = [[FSMediaPicker alloc] init];
    mediaPicker.mediaType = FSMediaTypePhoto;
    mediaPicker.editMode = self.editMode;
    mediaPicker.delegate = self;
    [mediaPicker showFromView:nil];



}
- (void)mediaPicker:(FSMediaPicker *)mediaPicker didFinishWithMediaInfo:(NSDictionary *)mediaInfo
{
    if (self.editMode == 2) {
        _image = mediaInfo[@"UIImagePickerControllerOriginalImage"];
    }else{
        _image = mediaInfo[@"UIImagePickerControllerEditedImage"];
    }
    
    //    NSData * data = [NSData da]
    _imageData = UIImageJPEGRepresentation(_image, 0.5);
    NSLog(@"%@",mediaInfo);
    [self.delegate image:_image imageData:_imageData];
//    [self pressmenuButton];
}

- (void)mediaPickerDidCancel:(FSMediaPicker *)mediaPicker
{
    [self.delegate image:nil imageData:nil];
//    [self pressmenuButton];
    NSLog(@"%s",__FUNCTION__);
}


@end
