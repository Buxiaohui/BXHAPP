import 'package:dio/dio.dart';
import 'dart:io';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';

class DownloadHelper {
  static void download(String url, String path, Function callback,
      {OnDownloadProgress onDownloadProgress}) async {
    Dio dio = new Dio();
    Response response =
        await dio.download(url, path, onProgress: onDownloadProgress);
    if (response != null) {
      print(response.data.hashCode);
    } else {
      print("response is null");
    }
    if (callback != null) {
      callback(url, path, response);
    }
  }
}
