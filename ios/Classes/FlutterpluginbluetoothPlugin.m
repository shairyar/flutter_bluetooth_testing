#import "FlutterpluginbluetoothPlugin.h"
#if __has_include(<flutterpluginbluetooth/flutterpluginbluetooth-Swift.h>)
#import <flutterpluginbluetooth/flutterpluginbluetooth-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "flutterpluginbluetooth-Swift.h"
#endif

@implementation FlutterpluginbluetoothPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterpluginbluetoothPlugin registerWithRegistrar:registrar];
}
@end
