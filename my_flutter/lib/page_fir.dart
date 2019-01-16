import 'dart:ui';
import 'package:flutter/material.dart';

class FlutterFirst extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _MyFlutterFlutterFirstState();
}

class _MyFlutterFlutterFirstState extends State<FlutterFirst> {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
        title: "flutter---bxh",
        home: new Scaffold(
            appBar: AppBar(
              title: Text("我是Appbar"),
            ),
            body: Container(
                width: double.infinity, // 这样设置会强制填充满父布局
                height: double.infinity, // 这样设置会强制填充满父布局
                child: Column(
                  children: <Widget>[
                    Text(
                      "我是 flutter view - 1 - !!!",
                      softWrap: true,
                      style: TextStyle(color: Colors.red),
                    ),
                    Text("asasfasfdfas!"),
                    Text("sdddddddddddddddddd!"),
                    Text("hahahhahahah!"),
                  ],
                ),
                color: Colors.lightBlue)));
  }
}

void main() => runApp(FlutterFirst());
