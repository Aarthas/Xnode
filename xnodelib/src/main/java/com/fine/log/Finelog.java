package com.fine.log;

import com.apkfuns.logutils.LogUtils;

public class Finelog {

    public static void d(Object object) {
        LogUtils.d(object);
    }
    public static void d(String tag,Object object) {

        LogUtils.d(tag+":"+object);
    }

    public static void e(Object object) {
        LogUtils.e(object);
    }
    public static void init(boolean showLog) {
        LogUtils.getLogConfig()
                .configAllowLog(showLog)
                .configTagPrefix("Finelog").configShowBorders(false)
                .configMethodOffset(1);
    }
}
