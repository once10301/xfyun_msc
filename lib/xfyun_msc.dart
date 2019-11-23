import 'dart:async';

import 'package:flutter/services.dart';

enum SpeechType { cloud, local }
enum Speaker { xiaoyan, xiaofeng }

class XfyunMsc {
  static const MethodChannel _channel = const MethodChannel('xfyun_msc');

  static Future<bool> init(String appId) async {
    return await _channel.invokeMethod('init', {"appId": appId});
  }

  static Future speak(String content, {SpeechType type: SpeechType.local, Speaker speaker: Speaker.xiaoyan, int speed: 50, int volume: 100}) async {
    Map<String, dynamic> params = {
      'content': content,
      'type': type == SpeechType.local ? 'local' : 'cloud',
      'speaker': speaker == Speaker.xiaoyan ? 'xiaoyan' : 'xiaofeng',
      'speed': speed,
      'volume': volume,
    };
    return await _channel.invokeMethod('speak', params);
  }
}
