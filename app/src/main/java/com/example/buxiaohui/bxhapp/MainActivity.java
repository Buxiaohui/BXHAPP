package com.example.buxiaohui.bxhapp;

import java.net.URLEncoder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String ssss =
            "a&b=c&d=WF-SjQ2aWpFN1lWM3NxVGY2WGFaRUVzUzI3UEdxaFAAAAAAAAA()AAAAAAAAAMT9R1vE~Udba+ ";

    public static String testD() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.encodedAuthority("test");
        builder.appendQueryParameter("qt", "ta_point_sug");
        builder.appendQueryParameter("cityid", "cityid(1,0)");

        String url = builder.build().toString() + "&cc=sss%20ss()" + "&f=%28%29";

        // 重新生成Uri.Builder为方便进行参数检验
        builder = Uri.parse(url).buildUpon();

        Log.d("hahaha", "builder.build().getEncodedQuery():" + builder.build().getEncodedQuery());
        // 生成最终url
        url = builder.build().toString();
        return url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        testDrawableBitmap();
        //        testDrawableBitmapV2();
        //        testHtmlText();
        //        testDestEndName();
        //        try {
        //
        //            throw new Throwable("hahaha");
        //        } catch (Throwable e) {
        //            Log.d("hahaha", "e:" + e.toString());
        //        }
        //        Assert.assertNotNull("hahaha断言测试",null);
        //        TextView textView = (TextView)findViewById(R.id.tv);
        //        ObjectAnimator animator = ObjectAnimator.ofFloat(textView,"alpha",0f,1f);
        //        animator.setDuration(1000 * 8);
        //        animator.start();

        //       testA();
        //       testB();
        //       testC();
        //       testD();
        //        AnimTest.doGrowAnimation((TextView)findViewById(R.id.eta_tag_tx));
        String string = "北京市朝阳区S12(机场高速) > ";
        String string1 = "北京市朝阳区S12(机场高速) > 北京首都国际机场";
        Log.e("buxiaohui", "+string.length():" + string.length());
        TextView textView = (TextView) findViewById(R.id.tv_test);
        TextView textView1 = (TextView) findViewById(R.id.tv_test1);
        textView1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(Integer.MAX_VALUE) });
        textView.setText(string);
        textView1.setText(string1);
        testMargin();
    }
    private void testMargin(){
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.parent_container);
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(Color.BLACK);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT,ViewGroup.MarginLayoutParams.MATCH_PARENT);
//        marginLayoutParams.bottomMargin = 30;
//        marginLayoutParams.rightMargin = 10;
//        marginLayoutParams.topMargin = 50;
//        marginLayoutParams.leftMargin = 10;
        marginLayoutParams.setMargins(10,20,30,40);
        frameLayout.addView(imageView,marginLayoutParams);
    }
    private void testHtmlText() {
        String s1 = "<font color=\"#3385ff\" size =\"10\" >T3航站楼</font><font color=\"#00ff00\" >(无法停车)</font>";
        // String s2 = "<font color=\"#000000\">" + "(" + "适合接送" + ")" + "</font>";
        // int startIndex = s1.indexOf("\">");
        // int endIndex = s1.indexOf("</font>");
        // String s = s1.substring(0,s1.length()-1);
        TextView textView = ((TextView) findViewById(R.id.tv));
        String oldStr = textView.getText().toString();
        Log.d("buxiaohui", "testHtmlText,oldStr: " + oldStr);
        textView.setText(Html.fromHtml(s1));
        //textView.setText(Html.fromHtml(s1+s2).toString());
    }

    private void testC() {
        Uri.Builder builder1 = new Uri.Builder();
        builder1.encodedAuthority("PPP");
        builder1.scheme("https");
        builder1.appendQueryParameter("bbbb", "NNNNBBBB(xxx)");
        builder1.appendQueryParameter("cccc", "NNNNBBBB ");
        builder1.appendQueryParameter("dddd", "NNNN~BBBB");
        String url = builder1.build().toString();
        builder1 = Uri.parse(url).buildUpon();
        String xxxx = builder1.build().getEncodedQuery();
        String xxxxx = builder1.build().getEncodedFragment();
        //        Log.d("hahaha","Uri.encode:"+builder1.build());
        //        Log.d("hahaha","Uri.encode:"+xxxxx);
        Log.d("hahaha", "builder1.build().getEncodedQuery():" + xxxx);
        //        Log.d("hahaha","Uri.encode:"+url);
        //        Log.d("hahaha","Uri.encode:"+Uri.parse(url));
        Log.d("hahaha", "Uri.encode:" + Uri.encode(url));
        //        Log.d("hahaha","Uri.encode:"+builder1.build().toString());
    }

    private void testB() {
        String s2 = new String(ssss);
        Log.d("hahaha", "testB s2:" + s2);
        String xxx = URLEncoder.encode(s2);
        Log.d("hahaha", "testB URLEncoder.encode:" + xxx);
    }

    private void testA() {
        Uri.Builder builder = new Uri.Builder();
        String s1 = new String(ssss);
        Log.d("hahaha", "testA s1:" + s1);
        builder.appendQueryParameter("bbbb", s1);
        String xx = builder.build().getEncodedQuery();
        Log.d("hahaha", "testA builder.build().getEncodedQuery():" + xx);
    }

    public void testDrawableBitmapV2() {
        RouteCarNearbySearchPopup routeNearbySearchPopup = new RouteCarNearbySearchPopup(this);
        routeNearbySearchPopup.setPoiName("那笔");
        routeNearbySearchPopup.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        routeNearbySearchPopup
                .layout(0, 0, routeNearbySearchPopup.getMeasuredWidth(), routeNearbySearchPopup.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(routeNearbySearchPopup.getWidth(), routeNearbySearchPopup.getHeight(),
                Bitmap.Config.ARGB_8888);
        bitmap.setDensity(getResources().getDisplayMetrics().densityDpi);
        Canvas canvas = new Canvas(bitmap);
        //把view中的内容绘制在画布上
        routeNearbySearchPopup.draw(canvas);
        canvas.setBitmap(null);

        Drawable drawable = new BitmapDrawable(bitmap);
        findViewById(R.id.container_pop_l).setBackgroundDrawable(drawable);
        ((ImageView) findViewById(R.id.container_image)).setImageBitmap(bitmap);

    }

    public void testDrawableBitmap() {
        RouteCarNearbySearchPopup routeNearbySearchPopup = new RouteCarNearbySearchPopup(this);
        routeNearbySearchPopup.setPoiName("西北");
        routeNearbySearchPopup.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        routeNearbySearchPopup
                .layout(0, 0, routeNearbySearchPopup.getMeasuredWidth(), routeNearbySearchPopup.getMeasuredHeight());
        routeNearbySearchPopup.setDrawingCacheEnabled(true);
        routeNearbySearchPopup.buildDrawingCache();
        Bitmap bitmap = routeNearbySearchPopup.getDrawingCache();
        if (bitmap != null) {
            Drawable drawable = new BitmapDrawable(bitmap);
            findViewById(R.id.layout).setBackgroundDrawable(drawable);
        }

        ((RelativeLayout) findViewById(R.id.container_pop)).addView(routeNearbySearchPopup);
    }

    public void testDestEndName() {
        TextView tv0 = (TextView) findViewById(R.id.test_end_name);
        String name = "终点(特来电充电站(深圳中科纳能大厦))";
        int start = name.lastIndexOf("(");
        int end = name.lastIndexOf(")");
        String finalStr = name.substring(start + 1, end);
        tv0.setText(name + "\n" + finalStr);
    }

    public void testDest() {
        TextView tv0 = (TextView) findViewById(R.id.tv01);
        TextView tv1 = (TextView) findViewById(R.id.tv02);
        TextView tv2 = (TextView) findViewById(R.id.tv03);
        tv0.setText("西进站口" + "\n" + "(可以停车)");
        tv1.setText("南进站口" + "\n" + "(可以休息)");
        tv2.setText("北进站口" + "\n" + "(可以购物)");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackPressedV1();
    }
    public boolean onBackPressedV1() {
       return m1()||m2()||m3();
    }

    private boolean m1(){
        Log.d("hahaha", "m1" );
        return true;
    }
    private boolean m2(){
        Log.d("hahaha", "m2" );
        return false;
    }
    private boolean m3(){
        Log.d("hahaha", "m3" );
        return false;
    }


}
