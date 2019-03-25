#import "SaveImage2NativePlugin.h"

@interface SaveImage2NativePlugin(){
    FlutterResult flutterResult;
}

@end

@implementation SaveImage2NativePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    FlutterMethodChannel* channel = [FlutterMethodChannel
                                     methodChannelWithName:@"save_image_2_native"
                                     binaryMessenger:[registrar messenger]];
    SaveImage2NativePlugin* instance = [[SaveImage2NativePlugin alloc] init];
    [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    flutterResult = result;
    UIImage * translatedImage;
    if ([@"saveImage2NativeMethod" isEqualToString:call.method]) {
        if (call.arguments) {
            NSDictionary * params = call.arguments;
            FlutterStandardTypedData *dataList = (FlutterStandardTypedData *)params[@"imageData"];
            if (dataList) {
                translatedImage = [UIImage imageWithData:dataList.data];
            }
        }else
            return;
        UIImageWriteToSavedPhotosAlbum(translatedImage, self, @selector(image:didFinishSavingWithError:contextInfo:), nil);
    } else {
        result(FlutterMethodNotImplemented);
    }
}

#pragma mark -- <保存到相册>
- (void)image:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo {
    flutterResult(@(error == NULL));
}

@end
