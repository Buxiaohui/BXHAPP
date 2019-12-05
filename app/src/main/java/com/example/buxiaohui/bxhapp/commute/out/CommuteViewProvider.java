package com.example.buxiaohui.bxhapp.commute.out;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.support.annotation.StringDef;
import android.view.View;

public class CommuteViewProvider {

    public View getView(@BNCommuteViewType String viewType) {
        switch (viewType) {
            case BNCommuteViewType.ROUTE_HEAD:
                return null;
            case BNCommuteViewType.ROUTE_MAP:
                return null;
            case BNCommuteViewType.ROUTE_SCREEN:
                return null;
            case BNCommuteViewType.ROUTE_TABS:
                return null;
            default:
                return null;
        }
    }

    @StringDef({
            BNCommuteViewType.ROUTE_HEAD,
            BNCommuteViewType.ROUTE_MAP,
            BNCommuteViewType.ROUTE_SCREEN,
            BNCommuteViewType.ROUTE_TABS,

            BNCommuteViewType.GUIDE_HEAD,
            BNCommuteViewType.GUIDE_MAP,
            BNCommuteViewType.GUIDE_SCREEN,
            BNCommuteViewType.GUIDE_TABS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BNCommuteViewType {
        String ROUTE_TABS = "commute_route_tabs";
        String ROUTE_HEAD = "commute_route_head";
        String ROUTE_SCREEN = "commute_route_screen";
        String ROUTE_MAP = "commute_route_map";

        String GUIDE_TABS = "commute_guide_tabs";
        String GUIDE_HEAD = "commute_guide_head";
        String GUIDE_SCREEN = "commute_guide_screen";
        String GUIDE_MAP = "commute_guide_map";
    }
}
