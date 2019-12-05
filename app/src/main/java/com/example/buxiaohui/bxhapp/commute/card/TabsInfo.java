package com.example.buxiaohui.bxhapp.commute.card;

public class TabsInfo {
    private int flag;
    private String tag;

    public TabsInfo(int flag, String tag) {
        this.flag = flag;
        this.tag = tag;
    }

    public int getFlag() {
        return flag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TabsInfo{");
        sb.append("flag=").append(flag);
        sb.append(", tag='").append(tag).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
