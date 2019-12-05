package com.example.buxiaohui.bxhapp.histogram;

import java.util.ArrayList;

import bnav.baidu.com.sublog.LogUtil;

public class FuturetripLimit {

    private Limit mDefaultInitLimit;
    private Limit mDefaultSelectTimeLimit;
    private Limit mDefaultTimePickerLimit;
    private ArrayList<DistanceLimit> limitList;

    public FuturetripLimit() {
        mDefaultInitLimit =
                new Limit(FutureTripParams.OFFSET_HOUR_ON_INIT_START, FutureTripParams.OFFSET_HOUR_ON_INIT_END);
        mDefaultSelectTimeLimit =
                new Limit(FutureTripParams.OFFSET_HOUR_ON_SELECT_START, FutureTripParams.OFFSET_HOUR_ON_SELECT_END);
        mDefaultTimePickerLimit = new Limit(FutureTripParams.OFFSET_HOUR_ON_PICK_TIME_START,
                FutureTripParams.OFFSET_HOUR_ON_PICK_TIME_END);
    }

    /**
     * @param curRouteDisMeter
     * @param requestType
     *
     * @return
     */
    public Limit getLimit(int curRouteDisMeter, @FutureTripParams.MainPanelRequestType int requestType) {
        if (limitList == null || limitList.size() <= 0
                || requestType == FutureTripParams.MainPanelRequestType.INVALID) {
            return geDefaultLimit(requestType);
        }
        for (int i = 0; i < limitList.size(); i++) {
            if (limitList.get(i) != null) {
                if (curRouteDisMeter < limitList.get(i).distance) {
                    return limitList.get(i).getLimits().get(requestType - 1);
                }
            }
        }
        return geDefaultLimit(requestType);
    }

    private Limit geDefaultLimit(@FutureTripParams.MainPanelRequestType int requestType) {
        switch (requestType) {
            case FutureTripParams.MainPanelRequestType.PULL_ON_INIT:
                return mDefaultInitLimit;
            case FutureTripParams.MainPanelRequestType.PULL_ON_SELECT:
                return mDefaultSelectTimeLimit;
            case FutureTripParams.MainPanelRequestType.PULL_ON_FROM_TIME_PICKER:
                return mDefaultTimePickerLimit;
            default:
                return mDefaultSelectTimeLimit;
        }
    }

    public ArrayList<DistanceLimit> getLimitList() {
        return limitList;
    }

    public void setLimitList(
            ArrayList<DistanceLimit> limitList) {
        this.limitList = limitList;
    }

    public static class DistanceLimit {

        private int distance;
        // 0,1,2 => init,select time,time picker
        private ArrayList<Limit> limits;

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public ArrayList<Limit> getLimits() {
            return limits;
        }

        public void setLimits(ArrayList<Limit> limits) {
            this.limits = limits;
        }
    }

    public static class Limit {
        private int start;
        private int end;

        public Limit(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Limit{");
            sb.append("start=").append(start);
            sb.append(", end=").append(end);
            sb.append('}');
            return sb.toString();
        }

        public void print() {
            LogUtil.e("buxiaohui_limit", "buxiaohui_limit:" + this);
        }
    }
}
