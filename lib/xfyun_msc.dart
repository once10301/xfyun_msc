import 'dart:async';

import 'package:flutter/services.dart';

enum SpeechType { cloud, local }
enum Speaker { xiaoyan, xiaofeng }

class XfyunMsc {
  Stream<bool> stream;

  static const MethodChannel methodChannel = const MethodChannel('xfyun_msc');

  static const EventChannel eventChannel = EventChannel('xfyun_msc_speaking');

  static Future<bool> init(String appId) async {
    return await methodChannel.invokeMethod('init', {"appId": appId});
  }

  static Future speak(String content, {SpeechType type: SpeechType.local, Speaker speaker: Speaker.xiaoyan, int speed: 50, int volume: 100, int pitch: 10}) async {
    Map<String, dynamic> params = {
      'content': content,
      'type': type == SpeechType.local ? 'local' : 'cloud',
      'speaker': speaker == Speaker.xiaoyan ? 'xiaoyan' : 'xiaofeng',
      'speed': speed,
      'volume': volume,
      'pitch': pitch,
    };
    return await methodChannel.invokeMethod('speak', params);
  }

  Stream<bool> get onSpeakingChanged {
    if (stream == null) {
      stream = eventChannel.receiveBroadcastStream().map((dynamic event) => event as bool);
    }
    return stream;
  }
}
