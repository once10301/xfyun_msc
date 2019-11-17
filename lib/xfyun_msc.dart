import 'dart:async';

import 'package:flutter/services.dart';

class XfyunMsc {
  static const MethodChannel _channel = const MethodChannel('xfyun_msc');

  static Future<bool> init(String appId) async {
    return await _channel.invokeMethod('init', {"appId": appId});
  }

  static Future speak(String content) async {
    return await _channel.invokeMethod('speak', {"content": content});
  }
}
