package com.fine.xnode.viewprocess;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import java.util.HashMap;

/**
 * Created by zhangyn on 17/4/5.
 */

public class FrameLayoutProcess extends ViewProcess {


    @Override
    public View initView(Context context, ViewGroup parent, HashMap<String, Object> map) {
        FrameLayout hostview = new FrameLayout(context);


        return hostview;
    }



    @Override
    public void applyProperty(View hostview, String key, String value) {
        super.applyProperty(hostview,key,value);
    }
}
