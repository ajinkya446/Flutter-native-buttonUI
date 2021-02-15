package com.winjit.flutter_plugin

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraMetadata
import android.os.Build
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


/** FlutterPlugin */
class FlutterPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_plugin")
        channel.setMethodCallHandler(this)
        flutterPluginBinding.platformViewRegistry.registerViewFactory("plugins.ajinkya/textview", TextViewFactory(flutterPluginBinding.binaryMessenger))
  //      flutterPluginBinding.platformViewRegistry.registerViewFactory("opengl_texture", CameraViewFactory(flutterPluginBinding.binaryMessenger))
        flutterPluginBinding.platformViewRegistry.registerViewFactory("plugins.ajinkya/buttonView", ButtonViewFactory(flutterPluginBinding.binaryMessenger))
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else {
            result.notImplemented()
        }
    }

    private fun registerWith(registrar: Registrar) {
        registrar
                .platformViewRegistry()
                .registerViewFactory(
                        "plugins.ajinkya/textview", TextViewFactory(registrar.messenger()))
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}

