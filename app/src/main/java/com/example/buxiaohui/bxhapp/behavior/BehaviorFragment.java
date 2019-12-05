package com.example.buxiaohui.bxhapp.behavior;

import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BehaviorFragment extends Fragment implements View.OnClickListener {
    private View view_bottom;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.behavior_bottom_sheet, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view_bottom = view.findViewById(R.id.view_bottom);
        mBottomSheetBehavior = BottomSheetBehavior.from(view_bottom);
        view.findViewById(R.id.view1).setOnClickListener(this);
        view.findViewById(R.id.view2).setOnClickListener(this);
        view.findViewById(R.id.view3).setOnClickListener(this);
        view.findViewById(R.id.view4).setOnClickListener(this);
        view.findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switchState(v);
    }

    public void switchState(View view) {

        if (!(view instanceof Button) || mBottomSheetBehavior == null) {
            return;
        }

        Button button = (Button) view;
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            button.setText("展开显示");
            return;
        }

        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            button.setText("折叠收起");
            return;
        }
    }
}
