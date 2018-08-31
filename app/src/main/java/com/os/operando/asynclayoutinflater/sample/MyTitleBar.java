package com.os.operando.asynclayoutinflater.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fine.xnode.base.BseViewComponent;

import java.util.HashMap;


public class MyTitleBar extends BseViewComponent {
    @Override
    public View initView(Context context, ViewGroup parent, HashMap<String, String> map) {
        LayoutInflater layoutInflater = getLayoutInflater(context);
        View inflate = layoutInflater.inflate(R.layout.titlebar, parent, false);
        return inflate;
    }


    @Override
    public void applyProperty(View hostview, String key, String value) {
        if (key.equals("titleText")) {
            TextView viewById = (TextView) hostview.findViewById(R.id.tv);
            viewById.setText(value);
        } else if (key.equals("v_onclick")) {
            TextView viewById = (TextView) hostview.findViewById(R.id.tv);
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}
