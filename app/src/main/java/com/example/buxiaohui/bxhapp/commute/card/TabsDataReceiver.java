package com.example.buxiaohui.bxhapp.commute.card;

import com.example.buxiaohui.bxhapp.commute.IDataReceiver;

import bnav.baidu.com.sublog.LogUtil;

public class TabsDataReceiver implements IDataReceiver<TabsInfo> {
    @Override
    public void onReceive(TabsInfo tabsInfo) {
        LogUtil.e("TabsDataReceiver", "onReceive,tabsInfo:" + tabsInfo);
        LogUtil.e("TabsDataReceiver", tabsInfo.getFlag()+" & " + tabsInfo.getTag());
    }
}
