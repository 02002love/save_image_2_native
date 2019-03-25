import 'dart:async';

import 'package:http/http.dart' as http;

import 'package:flutter/services.dart';

class SaveImage2Native {
  static const MethodChannel _channel =
      const MethodChannel('save_image_2_native');

  static Future<int> get saveImage2NativeMethod async {
    final url = 'https://upload.jianshu.io/admin_banners/web_images/4592/22f5cfa984d47eaf3def6a48510cc87c157bf293.png?imageMogr2/auto-orient/strip|imageView2/1/w/1250/h/540';
    final res = await http.get(url);
    Map params = {'imageData':res.bodyBytes};

    var saveStatus = await _channel.invokeMethod('saveImage2NativeMethod',params);
    return saveStatus;
  }
}
