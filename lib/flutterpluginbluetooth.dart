import 'dart:async';

import 'package:flutter/services.dart';

class Flutterpluginbluetooth {
  static const MethodChannel _channel =
      const MethodChannel('flutterpluginbluetooth');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get messageText async {
    final String message = await _channel.invokeMethod('getMessage');
    return message;
  }
}
