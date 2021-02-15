import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_plugin/flutter_plugin.dart';

void main() => runApp(
    MaterialApp(debugShowCheckedModeBanner: false, home: TextViewExample()));

class TextViewExample extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: const Text('Flutter Native UI example')),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Center(
                  child: Container(
                      padding: EdgeInsets.symmetric(vertical: 30.0),
                      width: 130.0,
                      height: 100.0,
                      child: TextView(
                        onTextViewCreated: _onTextViewCreated,
                      ))),
              Center(
                child: Container(
                  height: 60,
                  width: 200,
                  child: ButtonView(
                    onButtonViewCreated: _onButtonViewCreated,
                  ),
                ),
              ),
              SizedBox(height: 20)
            ]));
  }

  void _onTextViewCreated(TextViewController controller) {
    controller.setText('Hello from Android!');
  }

  void _onButtonViewCreated(ButtonController controller) {
    controller.setText('Click!');
  }
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await FlutterPlugin.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
