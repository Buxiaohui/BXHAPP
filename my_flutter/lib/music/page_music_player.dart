import 'dart:ui';

import 'package:audioplayers/audioplayers.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:my_flutter/utils/LogUtils.dart';
import 'package:simple_permissions/simple_permissions.dart';

class FlutterFirst extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => new _MyFlutterFlutterFirstState();
}

class _MyFlutterFlutterFirstState extends State<FlutterFirst>
    with TickerProviderStateMixin {
  static final String TAG = "BXH_AUDIO_PLAYER";
  Animation<Color> _changeColor;
  var _progress_controller;
  var _change_controller;
  var _cover_controller;
  var _cover_curved;
  AudioPlayer _audioPlayer;
  int _audioPlayerIndex = -1;

  Animation _animation;
  Animation _cover_animation;
  String _percentStr = "";
  int _audio_duration = 0;
  String _audio_duration_str = "00:00:00";
  double _percent = 0;
  double _cur_percent = 0; // 0 - 1
  int _cur_position_seconds = 0; // 0 to  duration of audio media
  static String PLAYING_IMG_PATH = "assets/images/play_white_28.png";
  static String PAUSE_IMG_PATH = "assets/images/pause_white_28.png";

  static List<String> audioUrlList = [
    "http://other.web.nu01.sycdn.kuwo.cn/resource/n2/58/13/3132145810.mp3",
    "http://other.web.nf01.sycdn.kuwo.cn/resource/n1/61/86/1192645866.mp3",
    "http://other.web.rh01.sycdn.kuwo.cn/resource/n1/75/40/546856787.mp3",
    "http://other.web.rh01.sycdn.kuwo.cn/resource/n2/82/74/2600054377.mp3",
    "http://other.web.nt01.sycdn.kuwo.cn/resource/n2/86/69/4257489350.mp3"
  ];

  void init() {
    _audioPlayer = new AudioPlayer();
    _audioPlayer
      ..positionHandler = ((Duration duration) {
        _cur_position_seconds = duration.inSeconds;
        if (_audio_duration == 0) {
          _cur_percent = 0;
        } else {
          _cur_percent = _cur_position_seconds / _audio_duration;
        }
        LogUtils.log(TAG,
            "positionHandler,duration: $duration,_cur_percent:$_cur_percent,_audio_duration:$_audio_duration");
      })
      ..durationHandler = ((Duration duration) {
        // LogUtils.log(TAG, "durationHandler,duration: $duration");
        try {
          _audio_duration = duration.inSeconds;
          int hours = 0;
          int minutes = 0;
          int seconds = 0;
          int temp;
          if (_audio_duration >= 3600) {
            hours = _audio_duration ~/ 3600;
            temp = _audio_duration % 3600;
            if (temp != 0) {
              if (temp >= 60) {
                minutes = temp ~/ 60;
                seconds = temp % 60;
              } else {
                seconds = temp;
              }
            }
          } else {
            if (_audio_duration >= 60) {
              minutes = _audio_duration ~/ 60;
              seconds = _audio_duration % 60;
            } else {
              seconds = _audio_duration;
            }
          }
          String hoursStr = hours.toString();
          String minutesStr = minutes.toString();
          String secondsStr = seconds.toString();
          if (hours <= 9) {
            hoursStr = "0" + hours.toString();
          }
          if (minutes <= 9) {
            minutesStr = "0" + minutes.toString();
          }
          if (seconds <= 9) {
            secondsStr = "0" + seconds.toString();
          }
          _audio_duration_str = hoursStr + ":" + minutesStr + ":" + secondsStr;
        } catch (e) {
          LogUtils.log(TAG, "_audio_duration_str,e:$e");
        }
        // LogUtils.log(TAG, "_audio_duration_str $_audio_duration_str");
        if (_audio_duration_str == null || _audio_duration_str.isEmpty) {
          _audio_duration_str = "00:00:00";
        }
        setState(() {});
      })
      ..audioPlayerStateChangeHandler = ((AudioPlayerState state) {
        LogUtils.log(TAG, "state: $state");
      })
      ..errorHandler = ((String error) {
        LogUtils.log(TAG, "error: $error");
      })
      ..completionHandler = (() {
        LogUtils.log(TAG, "completionHandler");
        _next();
      });
    _audioPlayer.state = AudioPlayerState.STOPPED;
    _cover_controller = new AnimationController(
        vsync: this, duration: const Duration(seconds: 10));
    _progress_controller = new AnimationController(
        vsync: this, duration: const Duration(seconds: 10));
    _change_controller = new AnimationController(
        vsync: this, duration: const Duration(seconds: 3));
    _cover_curved = new CurvedAnimation(
        parent: _cover_controller, curve: Curves.linear); //模仿小球自由落体运动轨迹
    _changeColor = ColorTween(begin: Colors.white70, end: Colors.deepPurple)
        .animate(CurvedAnimation(
            parent: _change_controller,
            curve: Interval(0.83, 0.83, curve: Curves.linear))
          ..addListener(() {})
          ..addStatusListener((status) {}));
    _animation = new Tween(begin: 0.0, end: 1.0).animate(_progress_controller)
      ..addStatusListener((status) {
        if (status == AnimationStatus.completed) {
          _progress_controller.reset();
          _progress_controller.forward();
        }
        LogUtils.log(TAG, "status:$status");
      })
      ..addListener(() {
        setState(() {
          try {
            _percent = _animation.value;
            var temp = _percent * 100;
//             LogUtils.log(TAG,"addListener,temp:$temp");
            _percentStr = temp.toStringAsFixed(0) + "%";
          } catch (e) {
            LogUtils.log(TAG, "addListener,_percentStr,e:$e");
            _percentStr = "";
          } finally {
//             LogUtils.log(TAG,"addListener,_percentStr:$_percentStr");
          }
        });
      });
    _progress_controller.forward();
    _cover_controller.repeat();
  }

  void initState() {
    super.initState();
    init();
  }

  Future checkPermission() async {
    bool resCheck = await SimplePermissions.checkPermission(
        Permission.WriteExternalStorage);
    if (resCheck) {
      // TODO
    } else {
      PermissionStatus permissionStatus =
          await SimplePermissions.requestPermission(
              Permission.WriteExternalStorage);
      if (PermissionStatus.authorized == permissionStatus) {
        // TODO
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
        title: "flutter---bxh",
        home: new Scaffold(
          appBar: AppBar(
            leading: Builder(
              builder: (BuildContext context) {
                return IconButton(
                  icon: const Icon(Icons.arrow_back),
                  onPressed: () {
                    if (Navigator.of(context).canPop()) {
                      Navigator.of(context).pop(context);
                    } else {
                      SystemNavigator.pop();
                    }
                  },
                  tooltip: MaterialLocalizations.of(context).backButtonTooltip,
                );
              },
            ),
            title: Text("title"),
            centerTitle: true,
          ),
          body: Stack(
            children: <Widget>[
              // 显示歌曲封面背景图片
              new Container(
                decoration: new BoxDecoration(
                  image: new DecorationImage(
                    image: new AssetImage(
                        "assets/images/record_plate_bruno_mars.jpeg"),
                    fit: BoxFit.cover,
                    colorFilter: new ColorFilter.mode(
                      Colors.white30,
                      BlendMode.overlay,
                    ),
                  ),
                ),
              ),
              // 高斯模糊图层
              new Container(
                  child: new BackdropFilter(
                filter: ImageFilter.blur(sigmaX: 10.0, sigmaY: 10.0),
                child: Opacity(
                  opacity: 0.6,
                  child: new Container(
                    decoration: new BoxDecoration(
                      color: Colors.grey.shade900,
                    ),
                  ),
                ),
              )),
              Container(
                  width: double.infinity, // 这样设置会强制填充满父布局
                  height: double.infinity, // 这样设置会强制填充满父布局
                  child: Column(
                    children: <Widget>[
                      Container(
                        child: RotationTransition(
                            child: CircleAvatar(
                              backgroundImage: AssetImage(
                                'assets/images/record_plate_bruno_mars.jpeg',
                              ),
                            ),
                            turns: _cover_curved),
                        margin: EdgeInsets.fromLTRB(40, 20, 40, 20),
                        color: Colors.transparent,
                        width: 300,
                        height: 300,
                      ),
                      Text(
                        "I am flutter",
                        softWrap: true,
                        style: TextStyle(color: Colors.red),
                      ),
                      Text(
                        "jodan",
                        softWrap: true,
                        style: TextStyle(color: Colors.red),
                      ),
                      Container(
                        alignment: Alignment.center,
                        margin: EdgeInsets.fromLTRB(30, 30, 30, 30),
                        width: double.infinity,
                        height: 28,
                        child: new Row(
                          children: <Widget>[
                            getImageWidget(
                                'assets/images/download_white_28.png', 0),
                            getImageWidget(
                                'assets/images/favorite_white_28.png', 1),
                            getImageWidget(
                                'assets/images/comment_white_28.png', 2),
                            getImageWidget(
                                'assets/images/more_white_28.png', 3),
                          ],
                        ),
                      ),
                      Container(
                        alignment: Alignment.center,
                        margin: EdgeInsets.fromLTRB(20, 10, 20, 10),
                        child: Row(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: <Widget>[
                            Padding(
                              child: Text(
                                "00:00:00",
                                style: new TextStyle(color: Colors.white70),
                              ),
                              padding: EdgeInsets.fromLTRB(0, 0, 10, 0),
                            ),
                            Expanded(
                              child: Container(
                                child: Slider(
                                    label: _cur_percent == null
                                        ? "null"
                                        : _cur_percent.toString(),
                                    value: _cur_percent ?? 0.0,
                                    onChanged: (double value) {
                                      LogUtils.log(TAG,
                                          "value:$value，_audio_duration:$_audio_duration");
                                      double originPosition =
                                          (_audio_duration * value);
                                      int intPosition =
                                          (_audio_duration * value).toInt();
                                      if (intPosition > _audio_duration) {
                                        intPosition = intPosition;
                                      }
                                      LogUtils.log(TAG,
                                          "originPosition:$originPosition ,intPosition:$intPosition");
                                      Duration newDuration =
                                          Duration(seconds: intPosition);
                                      _cur_percent = value;
                                      Future<int> ret =
                                          _audioPlayer.seek(newDuration);
                                      LogUtils.log(TAG, "ret.then,ret:$ret");
                                      ret.then((int val) {
                                        LogUtils.log(TAG, "ret.then,val:$val");
                                      });
                                    }),
                                width: 200,
                                height: 5,
                              ),
                            ),
                            Padding(
                              child: Text(
                                _audio_duration_str,
                                style: new TextStyle(color: Colors.white70),
                              ),
                              padding: EdgeInsets.fromLTRB(10, 0, 0, 0),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        alignment: Alignment.center,
                        margin: EdgeInsets.fromLTRB(100, 30, 100, 30),
                        width: double.infinity,
                        height: 28,
                        child: new Row(
                          children: <Widget>[
                            getPlayImageWidget(
                                'assets/images/pre_white_28.png', 0),
                            getPlayImageWidget(
                                _audioPlayer.state == AudioPlayerState.PLAYING
                                    ? PAUSE_IMG_PATH
                                    : PLAYING_IMG_PATH,
                                1),
                            getPlayImageWidget(
                                'assets/images/next_white_28.png', 2),
                          ],
                        ),
                      ),
                    ],
                  ),
                  color: Colors.transparent)
            ],
          ),
        ));
  }

  Widget getImageWidget(String imagePath, int index) {
    return Expanded(
      child: Container(
        child: GestureDetector(
            child: Image.asset(
              imagePath,
              repeat: ImageRepeat.noRepeat,
              fit: BoxFit.contain,
              height: 28,
              width: 28,
            ),
            onTap: () {
              Fluttertoast.showToast(msg: "index $index");
              switch (index) {
                case 0:
                  break;
                case 1:
                  break;
                case 2:
                  break;
                case 3:
                  break;
                default:
                  break;
              }
            }),
      ),
    );
  }

  Widget getPlayImageWidget(String imagePath, int index) {
    return Expanded(
      child: Container(
        child: GestureDetector(
            child: Image.asset(
              imagePath,
              repeat: ImageRepeat.noRepeat,
              fit: BoxFit.contain,
              height: 28,
              width: 28,
            ),
            onTap: () {
              Fluttertoast.showToast(msg: "index $index");
              switch (index) {
                case 0:
                  _pre();
                  break;
                case 1:
                  _toggle();
                  break;
                case 2:
                  _next();
                  break;
                default:
                  break;
              }
            }),
      ),
    );
  }

  void _toggle() {
    AudioPlayerState state = _audioPlayer.state;
    LogUtils.log(TAG, "AudioPlayerState:$state");
    switch (_audioPlayer.state) {
      case AudioPlayerState.PLAYING:
        _audioPlayer.pause();
        break;
      case AudioPlayerState.PAUSED:
        _audioPlayer.resume();
        break;
      case AudioPlayerState.STOPPED:
        int index = 0;
        if (_audioPlayerIndex >= audioUrlList.length || _audioPlayerIndex < 0) {
          index = 0;
        } else {
          index = _audioPlayerIndex;
        }
        _audioPlayer.play(audioUrlList[index]);
        break;
      case AudioPlayerState.COMPLETED:
        break;
      default:
        break;
    }
  }

  void _pre() {
    _audioPlayerIndex--;
    if (_audioPlayerIndex < 0) {
      _audioPlayerIndex = audioUrlList.length - 1;
    }
    _audioPlayer.play(audioUrlList[_audioPlayerIndex]);
  }

  void _next() {
    _audioPlayerIndex++;
    if (_audioPlayerIndex >= audioUrlList.length) {
      _audioPlayerIndex = 0;
    }
    _audioPlayer.play(audioUrlList[_audioPlayerIndex]);
  }
}

void main() => runApp(FlutterFirst());
