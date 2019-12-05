package com.example.buxiaohui.bxhapp.commute;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import android.support.annotation.StringDef;

public class DataDispatcher {
    private HashMap<String, CopyOnWriteArraySet<IDataReceiver>> dataReceiverMap =
            new HashMap<String, CopyOnWriteArraySet<IDataReceiver>>();

    public synchronized void register(@BNDataType String careDataType, IDataReceiver dataReceiver) {
        CopyOnWriteArraySet<IDataReceiver> list = dataReceiverMap.get(careDataType);
        if (list == null) {
            list = new CopyOnWriteArraySet<IDataReceiver>();
            dataReceiverMap.put(careDataType, list);
        }
        list.add(dataReceiver);
    }

    public synchronized void unregister(@BNDataType String careDataType) {
        dataReceiverMap.remove(careDataType);
    }

    public void release() {
        if (dataReceiverMap != null) {
            dataReceiverMap.clear();
        }
    }

    public synchronized void dispatch(@BNDataType String careDataType, Object data) {
        CopyOnWriteArraySet<IDataReceiver> list = dataReceiverMap.get(careDataType);
        if (list != null && list.size() > 0) {
            for (IDataReceiver receiver : list) {
                if (receiver != null) {
                    receiver.onReceive(data);
                }
            }
        }
    }

    @StringDef({BNDataType.ROUTE_TABS,
            BNDataType.GUIDE_TABS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BNDataType {
        String ROUTE_TABS = "commute_route_tabs";
        String GUIDE_TABS = "commute_guide_tabs";
    }

}
