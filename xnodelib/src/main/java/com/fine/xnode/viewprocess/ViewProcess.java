package com.fine.xnode.viewprocess;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.fine.log.Finelog;
import com.fine.xnode.XMeatureUtil;
import com.fine.xnode.base.BseViewComponent;

import java.util.HashMap;
import java.util.Set;


/**
 * Created by zhangyn on 17/4/1.
 */

public class ViewProcess extends BseViewComponent {


    @Override
    public View initView(Context context, ViewGroup parent, HashMap<String, String> map) {
        View hostview = new TextView(context);
        ViewGroup.LayoutParams params = getLayoutParams(parent, map);
        hostview.setLayoutParams(params);


        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = map.get(key);
            applyProperty(hostview, key, value);
        }

        return hostview;
    }

    @Override
    public void applyProperty(View hostview, String key, String value) {

        switch (key) {
            case "ref":
                hostview.setTag(value);
                break;
            case "id":
                hostview.setId(Integer.parseInt(value));
                break;
            case "tag":
                hostview.setTag(value);
                break;
            case "padding":
                int padding = (int) XMeatureUtil.meatureWithUnit(value);
                hostview.setPadding(padding, padding, padding, padding);
                break;
            case "paddingLeft":
                paddinga(hostview, value, 0);
                break;
            case "paddingTop":
                paddinga(hostview, value, 1);
                break;
            case "paddingRight":
                paddinga(hostview, value, 2);
                break;
            case "paddingBottom":
                paddinga(hostview, value, 3);
                break;

            case "background":
                hostview.setBackgroundColor(XMeatureUtil.parseColor(value));
                break;
            case "visible":

                boolean aTrue = value.equals("true");
                if (aTrue)
                    hostview.setVisibility(View.VISIBLE);
                else
                    hostview.setVisibility(View.GONE);
                break;
            case "margin":
                Finelog.d("margin");
                Finelog.d(value);
                while (value.contains("  "))
                {
                    Finelog.d("while  value = " + value);
                    value = value.replace("  "," ");
                }
                String[] split = value.split(" ");
                Finelog.d(split.length);
                int marginleft = 0, marginRight = 0, marginTop = 0, marginBottom = 0;
                if (split.length == 1) {
                    String s = split[0];
                    marginleft = marginRight = marginTop = marginBottom = (int) XMeatureUtil.meatureWithUnit(s);
                }
                else if (split.length == 4) {
                    marginleft=  (int) XMeatureUtil.meatureWithUnit(split[0])  ;
                    marginRight=  (int) XMeatureUtil.meatureWithUnit(split[2])  ;
                    marginTop=  (int) XMeatureUtil.meatureWithUnit(split[1])  ;
                    marginBottom=  (int) XMeatureUtil.meatureWithUnit(split[3])  ;
                }

                ViewGroup.LayoutParams layoutParams = hostview.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams p = ((ViewGroup.MarginLayoutParams) layoutParams);
                    p.setMargins(marginleft, marginTop, marginRight, marginBottom);
                }
                break;

            default: {
            }
        }


    }

    private void paddinga(View hostview, String value, int position) {


        int[] padding = new int[]{
                hostview.getPaddingLeft(),
                hostview.getPaddingTop(),
                hostview.getPaddingRight(),
                hostview.getPaddingBottom()
        };
        padding[position] = (int) XMeatureUtil.meatureWithUnit(value);
        hostview.setPadding(padding[0], padding[1], padding[2], padding[3]);
    }
}
