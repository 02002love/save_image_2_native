package com.imsong.saveimage2native;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * SaveImage2NativePlugin
 */
public class SaveImage2NativePlugin implements MethodCallHandler {
    public static Context mContext;
    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        mContext = registrar.context();
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "save_image_2_native");
        channel.setMethodCallHandler(new SaveImage2NativePlugin());
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        boolean saveStatus;
        if (call.method.equals("saveImage2NativeMethod")) {
            if (call.arguments != null) {
                Map params = (Map) call.arguments;
                byte[] dataList = (byte[]) params.get("imageData");
                saveStatus = saveImageToGallery(BitmapFactory.decodeByteArray(dataList, 0, dataList.length),mContext);
                result.success(saveStatus ? 1 : 0);
            } else result.success(false);

        } else {
            result.notImplemented();
        }
    }

    //保存文件到指定路径
    public boolean saveImageToGallery(Bitmap bmp, Context context) {
        // 首先保存图片  dearxy是可以改的
        String storePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            if (isSuccess) {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
