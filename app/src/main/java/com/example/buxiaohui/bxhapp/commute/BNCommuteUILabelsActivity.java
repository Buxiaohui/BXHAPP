package com.example.buxiaohui.bxhapp.commute;

import java.util.Random;

import com.example.buxiaohui.bxhapp.R;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BNCommuteUILabelsActivity extends Activity {
    private static final String TAG = "BNCommuteUILabelsActivity";
    LinearLayout container;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public static int measureTextWidth(TextView view, String text) {
        Paint paint = view.getPaint();
        if (paint == null) {
            return -1;
        }

        return (int) paint.measureText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commute_ui_test);
        container = (LinearLayout) findViewById(R.id.container);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addView();
            }
        }, 500);
    }

    private void addView() {
        for (int i = 0; i < 7; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_commute_ui_test_label, null);
            TextView textView = view.findViewById(R.id.text);
            int m1 = new Random().nextInt(7) + 10; // 10-17
            String s = "";
            for (int m = 0; m < m1; m++) {
                s += m;
            }
            textView.setText(s);
            if (canAdd(textView)) {
                container.addView(view);
            }
        }
    }

    private int getNeedWidth(TextView textView) {
        int w = 0;
        w += measureTextWidth(textView, textView.getText().toString());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        w += layoutParams.leftMargin;
        w += layoutParams.rightMargin;
        return w;
    }

    private boolean canAdd(TextView textView) {
        int oldChildrenCount = container.getChildCount();
        int oldChildrenWidth = 0;
        int newChildWidth = getNeedWidth(textView);
        for (int i = 0; i < oldChildrenCount; i++) {
            TextView tv = (TextView) ((ViewGroup) (container.getChildAt(i))).getChildAt(0);
            oldChildrenWidth += getNeedWidth(tv);
        }

        return (oldChildrenWidth + newChildWidth) < container.getWidth();
    }
}
