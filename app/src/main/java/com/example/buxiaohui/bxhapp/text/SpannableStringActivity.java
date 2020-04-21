package com.example.buxiaohui.bxhapp.text;

import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

public class SpannableStringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string);
        setText(R.id.tv_test_1, "1小时30分");
        setText(R.id.tv_test_4, "1小时3分");
        setText(R.id.tv_test_2, "54分");
        setText(R.id.tv_test_2, "4分");
        setText(R.id.tv_test_3, "11小时30分");
        setText(R.id.tv_test_5, "11小时3分");
    }

    private void setText(int viewId, String str) {
        TextView textView1 = findViewById(viewId);
        textView1.setText(getSpannableString(str));
    }

    private SpannableString getSpannableString(String str) {
        SpannableString spanStr = new SpannableString(str);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String hourKey = "小时";
        String minuteKey = "分";
        int numSizePx = 44;
        int unitSizePx = 24;
        int hourNumStart = -1;
        int hourNumEnd = -1;
        int hourUnitStart = -1;
        int hourUnitEnd = -1;
        int minuteNumStart = -1;
        int minuteNumEnd = -1;
        int minuteUnitStart = -1;
        int minuteUnitEnd = -1;

        if (str.contains(hourKey)) {
            hourNumStart = 0;
            hourNumEnd = str.indexOf(hourKey);
            hourUnitStart = str.indexOf(hourKey);
            hourUnitEnd = str.indexOf(hourKey) + hourKey.length();
            if (str.contains(minuteKey)) {
                minuteNumStart = str.indexOf(hourKey) + hourKey.length();
                minuteNumEnd = str.indexOf(minuteKey);
                minuteUnitStart = str.indexOf(minuteKey);
                minuteUnitEnd = str.length();
            }
        } else {
            if (str.contains(minuteKey)) {
                minuteNumStart = 0;
                minuteNumEnd = str.indexOf(minuteKey);
                minuteUnitStart = str.indexOf(minuteKey);
                minuteUnitEnd = str.length();
            }
        }

        setTextSize(spanStr, numSizePx, hourNumStart, hourNumEnd);
        setTextSize(spanStr, numSizePx, minuteNumStart, minuteNumEnd);

        setTextSize(spanStr, unitSizePx, hourUnitStart, hourUnitEnd);
        setTextSize(spanStr, unitSizePx, minuteUnitStart, minuteUnitEnd);
        return spanStr;
    }

    private void setTextSize(SpannableString spanStr, int numSizePx, int hourNumStart, int hourNumEnd) {
        if (hourNumStart != -1 && hourNumEnd != -1 && hourNumEnd >= hourNumStart) {
            spanStr.setSpan(new AbsoluteSizeSpan(numSizePx), hourNumStart, hourNumEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
