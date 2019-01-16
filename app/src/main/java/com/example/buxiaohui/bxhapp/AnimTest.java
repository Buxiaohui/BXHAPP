package com.example.buxiaohui.bxhapp;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnimTest {
    public static void doGrowAnimation(TextView textView) {
        if (textView == null) {
            return;
        }
        Context context = textView.getContext();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) textView.getLayoutParams();
            lp.height = 200;
            textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.nsdk_drawable_rr_eta_tangle_shape));
        textView.setLayoutParams(lp);
        textView.setPivotX(textView.getWidth() / 2);
        textView.setPivotY((float) 260);
        Log.e("doAnimation", "doGrowAnimation data is " + textView.getY() + "," + textView.getHeight());
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 0f, 1.0f);
        scaleY.setDuration(5000);
        scaleY.setRepeatCount(0);
        scaleY.setInterpolator(new DecelerateInterpolator());
        scaleY.start();
    }
}
