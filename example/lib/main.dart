import 'package:flutter/material.dart';
import 'package:xfyun_msc/xfyun_msc.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  TextEditingController controller = TextEditingController()..text = '请张三到外科诊室';

  @override
  void initState() {
    super.initState();
    XfyunMsc.init('5dd0055d');
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              TextField(
                controller: controller,
              ),
              RaisedButton(
                child: Text('Speak'),
                onPressed: () => XfyunMsc.speak(controller.text),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
