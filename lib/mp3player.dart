import 'dart:async';

import 'package:flutter/services.dart';

class Mp3player {
  static const MethodChannel _channel =
      const MethodChannel('mp3player');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  static Future<String> get play async {
    final String version = await _channel.invokeMethod('play');
    return version;
  }
}
