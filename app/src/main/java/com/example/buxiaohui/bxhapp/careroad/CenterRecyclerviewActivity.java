package com.example.buxiaohui.bxhapp.careroad;

import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CenterRecyclerviewActivity extends AppCompatActivity {
    private static final String TAG = "FickerActivity";
    private CommuteConcernRoadPage commuteConcernRoadPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_recyclerview);
        commuteConcernRoadPage = new CommuteConcernRoadPage(null);
        FrameLayout container = findViewById(R.id.container);
        View contentView = commuteConcernRoadPage.onCreateView(this);
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(contentView, lp);
        commuteConcernRoadPage.onViewCreated();
    }

}
