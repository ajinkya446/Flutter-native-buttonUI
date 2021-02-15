package com.ajinkya.native_button_view

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView


class FlutterButtonView internal constructor(context: Context?, messenger: BinaryMessenger?, id: Int) : PlatformView, MethodChannel.MethodCallHandler {
    private val button: Button
    private val methodChannel: MethodChannel
    override fun getView(): View {
        return button
    }

    override fun onMethodCall(methodCall: MethodCall, result: MethodChannel.Result) {
        when (methodCall.method) {
            "setText" -> setText(methodCall, result)
            else -> result.notImplemented()
        }
    }

    private fun setText(methodCall: MethodCall, result: MethodChannel.Result) {
        val text = methodCall.arguments as String
        button.text = text
        val params: LinearLayout.LayoutParams = button.layoutParams as LinearLayout.LayoutParams
        params.width = 100
        params.height=40
        button.layoutParams = params
        result.success(null)
    }

    override fun dispose() {}

    init {
        button = Button(context)
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        button.setTextColor(Color.WHITE)
        button.setBackgroundColor(Color.BLUE)
        methodChannel = MethodChannel(messenger, "plugins.ajinkya/buttonView$id")
        methodChannel.setMethodCallHandler(this)
    }
}