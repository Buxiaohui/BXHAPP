package com.example.buxiaohui.bxhapp.commute;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import bnav.baidu.com.sublibrary.statemachine.IState;
import bnav.baidu.com.sublibrary.statemachine.State;
import bnav.baidu.com.sublibrary.statemachine.StateMachine;
import bnav.baidu.com.sublog.LogUtil;

public class StateMachineImpl extends StateMachine implements IStateMachine {
    private final static String TAG = "StateMachineImpl";
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private HashMap<Class, State> mStateMap = new HashMap<>();
    private HashMap<Class, HashMap<Integer, Class>> mStateActionMap = new HashMap();
    private HashMap<Integer, Class> mRouteOperateStateCareActionMap = new HashMap();
    private HashMap<Integer, Class> mRouteBrowserStateCareActionMap = new HashMap();
    private HashMap<Integer, Class> mRouteBaseStateCareActionMap = new HashMap();

    {
        mRouteBaseStateCareActionMap.put(BNStateMsg.MSG_GUIDE_ENTER_BROWSER_STATE, CommuteGuideBrowserState.class);
        mRouteBrowserStateCareActionMap.putAll(mRouteBaseStateCareActionMap);
        mRouteOperateStateCareActionMap.putAll(mRouteBaseStateCareActionMap);
    }

    {
        /**
         * 路线页浏览态配置
         */
        mRouteBrowserStateCareActionMap.put(BNStateMsg.MSG_ROUTE_OPERATE_MAP, CommuteRouteOperateState.class);
    }

    {
        /**
         * 路线页操作态配置
         */
        mRouteOperateStateCareActionMap.put(BNStateMsg.MSG_ROUTE_NO_OPERATE_TIME_OUT, CommuteRouteBrowserState.class);
    }

    {
        mStateActionMap.put(CommuteRouteOperateState.class, mRouteOperateStateCareActionMap);
        mStateActionMap.put(CommuteRouteBrowserState.class, mRouteBrowserStateCareActionMap);
    }

    public StateMachineImpl(String name) {
        super(name);
    }

    public StateMachineImpl() {
        super(TAG, Looper.getMainLooper());
        init(CommuteRouteBrowserState.class);

    }

    public void init(Class initState) {
        initState(initState);
        start(); // 状态机进入初始状态等候外界的命令
    }

    private void initState(Class initState) {
        if (initState == null) {
            throw new IllegalArgumentException("必须设置初始状态");
        }
        // step 1 create instance
        State defaultState = new CommuteDefaultState(this);

        State commuteRouteBaseState = new CommuteRouteBaseState(this);
        State commuteRouteOperateState = new CommuteRouteOperateState(this);
        State commuteRouteBrowserState = new CommuteRouteBrowserState(this);

        State commuteGuideBaseState = new CommuteGuideBaseState(this);
        State commuteGuideOperateState = new CommuteGuideOperateState(this);
        State commuteGuideBrowserState = new CommuteGuideBrowserState(this);

        State commuteGuideFailState = new CommuteGuideFailState(this);
        State commuteGuideSuccessState = new CommuteGuideSuccessState(this);

        State commuteRouteFailState = new CommuteRouteFailState(this);
        State commuteRouteSuccessState = new CommuteRouteSuccessState(this);

        // step 2 add to stack
        addState(defaultState, null);  // 加入默认状态作为父状态，子状态处理不了的命令可以交给它来报错
        addState(commuteRouteBaseState, defaultState);
        addState(commuteRouteOperateState, commuteRouteBaseState);
        addState(commuteRouteBrowserState, commuteRouteBaseState);
        addState(commuteGuideBaseState, defaultState);
        addState(commuteGuideOperateState, commuteGuideBaseState);
        addState(commuteGuideBrowserState, commuteGuideBaseState);

        addState(commuteGuideFailState, commuteGuideBrowserState);
        addState(commuteGuideSuccessState, commuteGuideBrowserState);


        addState(commuteGuideFailState, commuteGuideBrowserState);
        addState(commuteGuideSuccessState, commuteGuideBrowserState);

        // step 3 add to map
        mStateMap.put(CommuteDefaultState.class, defaultState);
        mStateMap.put(CommuteRouteOperateState.class, commuteRouteOperateState);
        mStateMap.put(CommuteRouteBrowserState.class, commuteRouteBrowserState);
        mStateMap.put(CommuteGuideOperateState.class, commuteGuideOperateState);
        mStateMap.put(CommuteGuideBrowserState.class, commuteGuideOperateState);

        if (mStateMap.get(initState) == null) {
            throw new IllegalArgumentException("必须设置合法的初始状态");
        }
        // step 4 set init state
        setInitialState(mStateMap.get(initState)); // 初始状态
    }

    /**
     * for test
     *
     * @param type
     *
     * @return
     */
    @Deprecated
    public State getState(@BNStateType String type) {
        switch (type) {
            case BNStateType.STATE_ROUTE_OPERATE:
                return getState(CommuteRouteOperateState.class);
            case BNStateType.STATE_ROUTE_BROWSER:
                return getState(CommuteRouteBrowserState.class);
            case BNStateType.STATE_GUIDE_OPERATE:
                return getState(CommuteGuideOperateState.class);
            case BNStateType.STATE_GUIDE_BROWSER:
                return getState(CommuteGuideBrowserState.class);
            case BNStateType.STATE_DEFAULT:
                return getState(CommuteDefaultState.class);
            default:
                return getState(CommuteDefaultState.class);
        }
    }

    public State getState(Class clz) {
        if (mStateMap != null && mStateMap.containsKey(clz)) {
            return mStateMap.get(clz);
        }
        return null;
    }

    private void printCurStateName(String tag) {
        IState curState = getCurrentState();
        if (curState != null) {
            LogUtil.e(TAG, tag + ":" + curState.getName());
        } else {
            LogUtil.e(TAG, tag + ": null");

        }
    }

    @Override
    public void to(IState state) {
        transitionTo(state);
    }

    public void to(Class clz) {
        transitionTo(getState(clz));
    }

    public IState getTargetState(int msg) {
        LogUtil.e(TAG, "getTargetState,msg:" + msg);
        HashMap<Integer, Class> stateCareActionMap = mStateActionMap.get(getCurrentState().getClass());
        if (stateCareActionMap != null) {
            Class targetStateClass = stateCareActionMap.get(msg);
            if (mStateMap != null) {
                IState targetState = mStateMap.get(targetStateClass);
                if (targetState != null) {
                    LogUtil.e(TAG, "getTargetState,state:" + targetState);
                    return targetState;
                } else {
                    LogUtil.e(TAG, "getTargetState,state is null");
                }
            } else {
                LogUtil.e(TAG, "getTargetState,mStateMap is null");
            }
        } else {
            LogUtil.e(TAG, "getTargetState,stateCareActionMap is null");
        }
        return null;
    }

    @Override
    public void sendMsg(int what) {
        sendMessage(what);
    }

    public void release() {
        if (handler != null) {
            handler.removeCallbacks(null);
        }
    }

    @IntDef({BNStateMsg.MSG_ROUTE_NO_OPERATE_TIME_OUT,
            BNStateMsg.MSG_ROUTE_ENTER_OPERATE_STATE,
            BNStateMsg.MSG_ROUTE_ENTER_BROWSER_STATE,
            BNStateMsg.MSG_ROUTE_OPERATE_MAP,

            BNStateMsg.MSG_GUIDE_ENTER_OPERATE_STATE,
            BNStateMsg.MSG_GUIDE_ENTER_BROWSER_STATE,
            BNStateMsg.MSG_GUIDE_NO_OPERATE_TIME_OUT,
            BNStateMsg.MSG_GUIDE_OPERATE_MAP
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BNStateMsg {
        /**
         * 路线页操作命令0x1XXX
         */
        int MSG_ROUTE_ENTER_OPERATE_STATE = 0x1000; // 进入行前操作态
        int MSG_ROUTE_ENTER_BROWSER_STATE = 0x1001; // 进入行前浏览态
        int MSG_ROUTE_NO_OPERATE_TIME_OUT = 0x1002; // 长期无操作
        int MSG_ROUTE_OPERATE_MAP = 0x1003; // 操作底图

        /**
         * 导航页操作命令0x2XXX
         */
        int MSG_GUIDE_ENTER_OPERATE_STATE = 0x2000; // 进入行前操作态
        int MSG_GUIDE_ENTER_BROWSER_STATE = 0x2001; // 进入行前浏览态
        int MSG_GUIDE_NO_OPERATE_TIME_OUT = 0x2002; // 长期无操作
        int MSG_GUIDE_OPERATE_MAP = 0x2003;  // 操作底图

    }

    @StringDef({BNStateType.STATE_ROUTE_OPERATE,
            BNStateType.STATE_ROUTE_BROWSER,
            BNStateType.STATE_GUIDE_OPERATE,
            BNStateType.STATE_GUIDE_BROWSER,
            BNStateType.STATE_DEFAULT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BNStateType {
        String STATE_ROUTE_OPERATE = "commute_route_operate_state";
        String STATE_ROUTE_BROWSER = "commute_route_browser_state";
        String STATE_GUIDE_OPERATE = "commute_guide_operate_state";
        String STATE_GUIDE_BROWSER = "commute_guide_browser_state";
        String STATE_DEFAULT = "commute_default_state";
    }
}
