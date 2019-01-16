import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:my_flutter/page_fir.dart';
import 'package:my_flutter/page_forth.dart';
import 'package:my_flutter/page_sec.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

Widget _widgetForRoute(String route) {
  switch (route) {
    case 'route1':
      return new FlutterFirst();
    case 'route2':
      return new FlutterSecond();
    case 'route4':
      return new FlutterForth();
    default:
      return Center(
        child: Text('Unknown route: $route', textDirection: TextDirection.ltr),
      );
  }
}
