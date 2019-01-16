import 'dart:async';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';

class FlutterForth extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _MyFlutterForthState();
}

class _MyFlutterForthState extends State<FlutterForth> {
  //获取到插件与原生的交互通道
  static const methodPlugin =
      const MethodChannel('buxiaohui.flutter.m1/plugin');
  static const eventPlugin = const EventChannel('buxiaohui.flutter.e1/plugin');

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: AppBar(
          title: Text("AppBar"),
        ),
        body: new Column(children: <Widget>[
          Text("我是 flutter view - 2 -  !!!"),
          GestureDetector(
            child: Padding(
              padding: EdgeInsets.all(10),
              child: Container(
                child: Text("跳转到 NA-First-Activity"),
                color: Color(0x11223344),
              ),
            ),
            onTap: () {
              _jumpToNative();
            },
          ),
          GestureDetector(
            child: Container(
                color: Color(0xff3385ff),
                child: Padding(
                  padding: EdgeInsets.all(10),
                  child: Text("跳转到 NA-Second-Activity"),
                )),
            onTap: () {
              _jumpToNativeWithValue();
            },
          ),
          GestureDetector(
            child: Container(
                color: Colors.deepPurpleAccent,
                child: Padding(
                  padding: EdgeInsets.all(10),
                  child: Text("flutter 调用NA toast"),
                )),
            onTap: () {
              callNAToast("你瞅啥！！");
            },
          ),
          GestureDetector(
            child: Container(
                color: Colors.cyan,
                child: Padding(
                  padding: EdgeInsets.all(10),
                  child: Text("flutter 调用flutter toast"),
                )),
            onTap: () {
              Fluttertoast.showToast(
                  msg: "瞅你咋地？！",
                  toastLength: Toast.LENGTH_SHORT,
                  backgroundColor: Colors.red);
            },
          ),
          GestureDetector(
            child: Container(
                color: Colors.cyan,
                child: Padding(
                  padding: EdgeInsets.all(10),
                  child: Text("flutter跳转资源管理器"),
                )),
            onTap: () {
              callNaResController();
            },

            //
          ),
        ]),
      ),
    );
  }

  void callNaResController() async {
    var result = await methodPlugin.invokeMethod("open");
    print("callNaResController $result");
  }

  void callNAToast(String msg) async {
    var result = await methodPlugin.invokeMethod("toast", {"msg": msg});
    print("callNAToast $result");
  }

  StreamSubscription _subscription;

  var _counter;

  @override
  void initState() {
    super.initState();
    //开启监听
    if (_subscription == null) {
      _subscription = eventPlugin
          .receiveBroadcastStream()
          .listen(_onEvent, onError: _onError);
    }
  }

  void _onEvent(Object event) {
    setState(() {
      _counter = event;
      print("_onEvent: $event");
    });
  }

  void _onError(Object error) {
    setState(() {
      _counter = error;
      print("_onError $error");
    });
  }

  @override
  void dispose() {
    super.dispose();
  }

  Future<Null> _jumpToNative() async {
    String result = await methodPlugin.invokeMethod('oneAct');

    print(result);
  }

  Future<Null> _jumpToNativeWithValue() async {
    Map<String, String> map = {"flutter": "这是一条来自flutter的参数"};

    String result = await methodPlugin.invokeMethod('twoAct', map);

    print(result);
  }
}

void main() => runApp(FlutterForth());
