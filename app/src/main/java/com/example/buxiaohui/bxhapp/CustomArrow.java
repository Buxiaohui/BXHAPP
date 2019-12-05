package com.example.buxiaohui.bxhapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomArrow extends View {
    private Paint mPainter;
    private int mDefaultFillColor = Color.parseColor("#ffffff");

    public CustomArrow(Context context) {
        super(context);
    }

    public CustomArrow(Context context,
                       @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomArrow(Context context,
                       @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPainter == null) {
            mPainter = new Paint();
            mPainter.setColor(mDefaultFillColor);
        }
        LinearGradient linearGradient = new LinearGradient(0, 0, 400, 0, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        mPainter.setShader(linearGradient);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Log.e("CustomArrow", "width:" + width + ",height:" + height);
        canvas.drawRoundRect(10, 10, width-10, height-10, 50, 50, mPainter);
    }
}
