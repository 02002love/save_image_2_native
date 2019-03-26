import 'dart:async';

import 'package:http/http.dart' as http;

import 'package:flutter/services.dart';

class SaveImage2Native {
  static const MethodChannel _channel =
      const MethodChannel('save_image_2_native');

  static Future<int> saveImage2NativeMethod({String imgUrl}) async {
    final res = await http.get(imgUrl);
    Map params = {'imageData':res.bodyBytes};

    var saveStatus = await _channel.invokeMethod('saveImage2NativeMethod',params);
    return saveStatus;
  }
}
