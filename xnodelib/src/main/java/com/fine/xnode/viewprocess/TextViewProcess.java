package com.fine.xnode.viewprocess;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.fine.xnode.XMeatureUtil;

import java.util.HashMap;


/**
 * Created by zhangyn on 17/4/5.
 */

public  class TextViewProcess extends ViewProcess {


    public View initView(Context context, ViewGroup parent, HashMap<String, String> map) {
        TextView hostview = new TextView(context);
        ViewGroup.LayoutParams params = getLayoutParams(parent, map);
        hostview.setLayoutParams(params);


        return hostview;
    }


    @Override
    public void applyProperty(View hostview, String key, String value) {
        super.applyProperty(hostview, key, value);
        TextView textView = (TextView) hostview;
        switch (key) {
            case "text":
                textView.setText(value);
                break;
            case "color":
                textView.setTextColor(XMeatureUtil.parseColor(value));
                break;
            case "textSize":
                float textSize = Float.parseFloat(value);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                break;
            case "hint":
                textView.setHint(value);
                break;
        }
    }


}
