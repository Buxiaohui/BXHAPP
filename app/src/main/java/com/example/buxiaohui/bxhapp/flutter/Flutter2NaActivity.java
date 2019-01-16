package com.example.buxiaohui.bxhapp.flutter;

import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;

public class Flutter2NaActivity extends io.flutter.app.FlutterActivity {
    private static final String TAG = "Flutter2NaActivity";
    FlutterNativeView nativeView;
    FlutterView flutterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "--onCreate");
        GeneratedPluginRegistrant.registerWith(this);
        init();
    }

    @Override
    public FlutterView getFlutterView() {
        Log.i(TAG, "--getFlutterView");
        return super.getFlutterView();
    }

    @Override
    public FlutterView createFlutterView(Context context) {
        // return super.createFlutterView(context);
        Log.i(TAG, "--createFlutterView");
        WindowManager.LayoutParams matchParent = new WindowManager.LayoutParams(-1, -1);
        nativeView = this.createFlutterNativeView();
        flutterView = new FlutterView(this, (AttributeSet) null, nativeView);
        flutterView.setInitialRoute("route4");
        flutterView.setLayoutParams(matchParent);
        this.setContentView(flutterView);
        return flutterView;
    }

    private void init() {
        Log.i(TAG, "--init");

        Registrar registrar = registrarFor("buxiaohui.flutter.m1/plugin");
        registrar.addRequestPermissionsResultListener(new RequestPermissionsResultListener() {
            @Override
            public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                Log.i(TAG, "--onRequestPermissionsResult,requestCode:" + requestCode
                        + ",permissions:" + permissions
                        + ",grantResults:" + Arrays.toString(grantResults));
                return false;
            }
        });
        registrar.addActivityResultListener(new ActivityResultListener() {
            @Override
            public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                Log.i(TAG, "--onActivityResult,requestCode:" + requestCode
                        + ",resultCode:" + resultCode
                        + ",data:" + data);
                if (requestCode == 10010) {
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            // 拿到音频文件uri,开始播放。
                            Toast.makeText(Flutter2NaActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Flutter2NaActivity.this, "invalid media file", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
        registrar.addViewDestroyListener(new ViewDestroyListener() {
            @Override
            public boolean onViewDestroy(FlutterNativeView view) {
                Log.i(TAG, "--onViewDestroy,view:" + view);
                return false;
            }
        });
        MethodChannel methodCall =
                new MethodChannel(registrar.messenger(),
                        "buxiaohui.flutter.m1/plugin",
                        StandardMethodCodec.INSTANCE);
        methodCall.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                Log.i(TAG, "--onMethodCall,methodCall:" + methodCall);
                if (methodCall.method.equals("toast")) {
                    Toast.makeText(Flutter2NaActivity.this, (String) (methodCall.argument("msg")), Toast.LENGTH_SHORT)
                            .show();
                    result.success("成功了");
                }
                //通过MethodCall可以获取参数和方法名，然后再寻找对应的平台业务，本案例做了2个跳转的业务
                //接收来自flutter的指令oneAct
                else if (methodCall.method.equals("oneAct")) {

                    //跳转到指定Activity
                    Intent intent = new Intent(Flutter2NaActivity.this, FlutterFirstActivity.class);
                    Flutter2NaActivity.this.startActivity(intent);

                    //返回给flutter的参数
                    result.success("success");
                }
                //接收来自flutter的指令twoAct
                else if (methodCall.method.equals("twoAct")) {

                    //解析参数
                    String text = methodCall.argument("flutter");

                    //带参数跳转到指定Activity
                    Intent intent = new Intent(Flutter2NaActivity.this, FlutterSecondActivity.class);
                    intent.putExtra("data", text);
                    Flutter2NaActivity.this.startActivity(intent);

                    //返回给flutter的参数
                    result.success("success");
                } else if (methodCall.method.equals("open")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("audio/*");
                    Flutter2NaActivity.this.startActivityForResult(intent, 10010);

                } else {
                    result.notImplemented();
                }
            }

        });
        final EventChannel eventChannel =
                new EventChannel(flutterView, "buxiaohui.flutter.e1/plugin", StandardMethodCodec.INSTANCE);
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                Log.i(TAG, "--onListen");
                events.success("some data from native");
            }

            @Override
            public void onCancel(Object arguments) {
                Log.i(TAG, "--onCancel");
            }
        });
    }

    @Override
    public FlutterNativeView createFlutterNativeView() {
        Log.i(TAG, "createFlutterNativeView");
        return super.createFlutterNativeView();
    }

    @Override
    public boolean retainFlutterNativeView() {
        return super.retainFlutterNativeView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
