package com.clicktechlabs.flutterpluginbluetooth;

import androidx.annotation.NonNull;

import io.flutter.app.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/// imports required for bluetooth
import java.util.*;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;

//import android.bluetooth.BluetoothManager;
//import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
//import android.bluetooth.le.ScanFilter;

//import android.bluetooth.le.ScanSettings;


/**
 * FlutterpluginbluetoothPlugin
 */
public class FlutterpluginbluetoothPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;
//  private static int REQUEST_ENABLE_BT = 1;


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutterpluginbluetooth");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutterpluginbluetooth");
    channel.setMethodCallHandler(new FlutterpluginbluetoothPlugin());

  }


  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("getMessage")) {
      BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

      // Device doesn't support Bluetooth
      if (bluetoothAdapter == null) {

        System.out.println("DEBUG - Device doesn't support Bluetooth");
      }

      // Bluetooth is disabled
      if (!bluetoothAdapter.isEnabled()) {
        System.out.println("DEBUG - Device Bluetooth is not enabled");
        // Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        // activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
      }

      Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

      if (pairedDevices.size() > 0) {
        // There are paired devices. Get the name and address of each paired device.
        System.out.println("DEBUG - Paid devices found" + pairedDevices.size());
        for (BluetoothDevice device : pairedDevices) {
          String deviceName = device.getName();
          String deviceHardwareAddress = device.getAddress(); // MAC address
        }
      } else {
        System.out.println("DEBUG - No paired devices found");
      }

      result.success("Shairyar says hello");

      System.out.println("DEBUG - Starting discovery");
      bluetoothAdapter.startDiscovery();


      final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
//          System.out.println("DEBUG - inside onReceive");
          String action = intent.getAction();
//          System.out.println("DEBUG - Action is: " + action);
          //Finding devices
          if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Get the BluetoothDevice object from the Intent
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // Add the name and address to an array adapter to show in a ListView
//            mArrayAdapter.add(device.getName() + "\n" + device.getAddress());

            System.out.println("================================");
            System.out.println(device.toString());
            System.out.println("DEBUG - Device Name: " + device.getName());
            System.out.println("DEBUG - Device Address: " + device.getAddress());
            System.out.println("DEBUG - Device UUID: " + device.getUuids());
            System.out.println("DEBUG - Device Type: " + device.getType());
            System.out.println("DEBUG - Device Bond State: " + device.getBondState());

          } else {
            System.out.println("DEBUG: Action did not match" + BluetoothDevice.ACTION_FOUND);
          }
        }

      };


      IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
      System.out.println("DEBUG - About to registerReceiver");
      context.registerReceiver(mReceiver, filter);


    } else {
      result.notImplemented();
    }

  }


  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

//  protected void onDestroy() {
//    super.onDestroy();
//    // Don't forget to unregister the ACTION_FOUND receiver.
//    unregisterReceiver(receiver);
//  }
}
