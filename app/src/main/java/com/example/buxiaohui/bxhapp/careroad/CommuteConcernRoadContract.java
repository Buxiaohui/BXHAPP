package com.example.buxiaohui.bxhapp.careroad;

import java.util.List;

public class CommuteConcernRoadContract {
    public interface Presenter<View> {
        void onDestroy();

        void onLocationBtnClick();

        String getTag();
    }

    public interface View<Presenter> {
        void updateScaleView();

        void updateScaleViewMode(int mode);

        void onRequestComplete();

        void onRequestSuccess(List<ConcernRoadMode> datas);

        void onRequestError(int errCode);

        String getTag();
    }

}
