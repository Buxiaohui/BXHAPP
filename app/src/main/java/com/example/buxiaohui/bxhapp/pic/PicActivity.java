package com.example.buxiaohui.bxhapp.pic;

import java.io.IOException;
import java.io.InputStream;

import com.example.buxiaohui.bxhapp.R;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.rastermill.FrameSequenceDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class PicActivity extends AppCompatActivity {
    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        ImageView gif = findViewById(R.id.gif);
        ImageView apng = findViewById(R.id.apng);
        // setWebp();
        mDialog  = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_test,null);
        mDialog.setContentView(view);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mDialog.isShowing()) {
                        return true;
                    }
                }
                return false;
            }
        });
        mDialog.show();
    }

    private void setWebp() {
        ImageView webp = findViewById(R.id.webp);

        try {
            InputStream in = null;
            in = getResources().getAssets().open("elephant_webp.webp");
            final FrameSequenceDrawable drawable = new FrameSequenceDrawable(in);
            drawable.setLoopCount(1);
            drawable.setLoopBehavior(FrameSequenceDrawable.LOOP_FINITE);

            drawable.setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
                @Override
                public void onFinished(FrameSequenceDrawable frameSequenceDrawable) {

                }
            });
            webp.setImageDrawable(drawable);
        } catch (IOException e) {

        }
    }
}
