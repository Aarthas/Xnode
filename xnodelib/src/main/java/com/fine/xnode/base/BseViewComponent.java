package com.fine.xnode.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.fine.xnode.XMeatureUtil;
import com.fine.xnode.livedata.MyLiveData;

import java.util.HashMap;


public abstract class BseViewComponent {
    public abstract View initView(Context context, ViewGroup parent, HashMap<String, String> attr);

    public abstract void applyProperty(View hostview, String key, String value);

    protected ViewGroup.LayoutParams getLayoutParams(ViewGroup parent, HashMap<String, String> attr) {
        if (parent != null) {
            String width = attr.get("width");
            String height = attr.get("height");
            attr.remove("width");
            attr.remove("height");
            ViewGroup.LayoutParams layoutParams = XMeatureUtil.createLayoutParams(parent, width, height);
            return layoutParams;
        } else {
            ViewGroup.LayoutParams layoutParams = XMeatureUtil.createLayoutParams(parent, "match", "match");
            return layoutParams;
        }

    }

    public LayoutInflater getLayoutInflater(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater;
    }

    public void emitValue(View view, MyLiveData<String> livedata) {

    }

    public void applyValue(View view, MyLiveData<String> livedata) {

    }
}
