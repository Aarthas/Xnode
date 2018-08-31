package com.fine.xnode.viewprocess;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import com.fine.xnode.livedata.MyLiveData;
import com.fine.xnode.livedata.Observer;

import java.util.HashMap;

/**
 * Created by zhangyn on 17/4/5.
 */

public class EditTextProcess extends TextViewProcess {


    public View initView(Context context, ViewGroup parent, HashMap<String, String> map) {
        AppCompatEditText hostview = new AppCompatEditText(context);
        ViewGroup.LayoutParams params = getLayoutParams(parent, map);
        hostview.setLayoutParams(params);



        return hostview;
    }


    @Override
    public void applyProperty(View hostview, String key, String value) {
        super.applyProperty(hostview, key, value);
    }

    @Override
    public void applyValue(final View view, final MyLiveData<String> livedata) {
        livedata.observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                AppCompatEditText hostview = (AppCompatEditText) view;
                hostview.setText(s);
            }
        });
    }

    @Override
    public void emitValue(View view, final MyLiveData<String> livedata) {
        AppCompatEditText hostview = (AppCompatEditText) view;
        hostview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                livedata.justSetValue(s.toString());
            }
        });
    }
}
