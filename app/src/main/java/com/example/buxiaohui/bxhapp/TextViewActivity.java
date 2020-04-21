package com.example.buxiaohui.bxhapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import bnav.baidu.com.sublog.LogUtil;

public class TextViewActivity extends AppCompatActivity {
    private static final String TAG = "FickerActivity";
    private String prefixStr;
    private String tailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);
        TextView delTv = findViewById(R.id.del);
        TextView textview = findViewById(R.id.commute_route_tab_concern_road_tip_tv);
        TextView addTv = findViewById(R.id.add);
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefixStr = "啊" + prefixStr;
                textview.setText(getSpannableString(textview));
            }
        });
        delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefixStr = prefixStr.substring(1, prefixStr.length());
                textview.setText(getSpannableString(textview));
            }
        });
        textview.setText(getSpannableString(textview));
    }

    private SpannableStringBuilder getSpannableString(TextView tv) {
        if (TextUtils.isEmpty(prefixStr)) {
            prefixStr = "设置关注路线及时了解路线解拥堵变化";
        }
        if (TextUtils.isEmpty(tailStr)) {
            tailStr = "去设置";
        }
        int maxWidth =
                ScreenUtils.getScreenWidth(this)
                        - (9 + 9)  // .9图左右阴影
                        - ScreenUtils.dip2px(this, 21)  // 左边icon宽度
                        - ScreenUtils.dip2px(this, 9 + 8)  // 左边icon leftMargin+rightMargin
                        - ScreenUtils.dip2px(this, 10)  // 关闭icon宽度
                        - ScreenUtils.dip2px(this, 5 + 12)  // 关闭按钮 leftMargin+rightMargin
                        - ScreenUtils.dip2px(this, 9 + 9); // 黄条container leftMargin+rightMargin
        String tempFullStr = prefixStr + "  " + tailStr;
        int maxNum = UIUtils.getFeedLinePosition(tv, maxWidth, tempFullStr, ScreenUtils.dip2px(this, 14));
        SpannableString prefixSpan = new SpannableString(prefixStr);
        String sTail;
        LogUtil.e("getSpannableString",
                "getSpannableString,maxNum:" + maxNum
                        + "，len:" + tempFullStr.length()
                        + ",maxNum+5:" + (maxNum + 5));
        if (maxNum <= -1) { // 一行放得下
            sTail = "  " + tailStr;
        } else { // 一行放不下
            if (tempFullStr.length() > maxNum && tempFullStr.length() < maxNum + 5) { // 两行放得下
                sTail = "\n" + tailStr;
            } else { // 这种几乎不可能了
                sTail = "  " + tailStr;
            }

        }
        setTextSize(prefixSpan, 14, 0, prefixStr.length());
        setTextColor(prefixSpan, Color.parseColor("#333333"), 0, prefixStr.length());

        SpannableString tailSpan = new SpannableString(sTail);
        setTextSize(tailSpan, 14, 0, sTail.length());
        setClickListener(tv, tailSpan, 0, sTail.length());

        SpannableStringBuilder builder = new SpannableStringBuilder();
        return builder.append(prefixSpan).append(tailSpan);
    }

    private void setTextSize(SpannableString spanStr, int numSize, int hourNumStart, int hourNumEnd) {
        if (hourNumStart != -1 && hourNumEnd != -1 && hourNumEnd >= hourNumStart) {
            spanStr.setSpan(new AbsoluteSizeSpan(numSize, true), hourNumStart, hourNumEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void setTextColor(SpannableString spanStr, int color, int hourNumStart, int hourNumEnd) {
        if (hourNumStart != -1 && hourNumEnd != -1 && hourNumEnd >= hourNumStart) {
            spanStr.setSpan(new ForegroundColorSpan(color), hourNumStart, hourNumEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void setClickListener(TextView textView, SpannableString spanStr, int hourNumStart, int hourNumEnd) {
        if (hourNumStart != -1 && hourNumEnd != -1 && hourNumEnd >= hourNumStart) {
            spanStr.setSpan(new ClickableSpan() {
                                @Override
                                public void updateDrawState(@NonNull TextPaint ds) {
                                    ds.setColor(Color.parseColor("#3385ff"));
                                    ds.setUnderlineText(false); // 去掉下划线
                                }

                                @Override
                                public void onClick(@NonNull View widget) {
                                }
                            }, hourNumStart, hourNumEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

}
