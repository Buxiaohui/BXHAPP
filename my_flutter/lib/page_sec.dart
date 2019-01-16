import 'package:flutter/material.dart';

class FlutterSecond extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _MyFlutterFlutterSecondState();
}

class _MyFlutterFlutterSecondState extends State<FlutterSecond> {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        body:
            new Column(children: <Widget>[Text("我是 flutter view - 2 -  !!!")]),
      ),
    );
  }
}

void main() => runApp(FlutterSecond());
