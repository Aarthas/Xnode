package xjsonview.process.base;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.taobao.weex.ui.component.WXComponentProp;

import java.util.HashMap;
import java.util.Set;

import xjsonview.Utils;

/**
 * Created by zhangyn on 17/4/5.
 */

public class TextViewProcess extends ViewProcess {


    public View initView(Context context, ViewGroup parent, HashMap<String, String> map) {
        TextView hostview = new TextView(context);
        ViewGroup.LayoutParams params = getLayoutParams(parent, map);
        hostview.setLayoutParams(params);


        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = map.get(key);
            applyProperty(hostview, key, value);
        }




        return hostview;
    }

    public void applyProperty(TextView hostview, String key, String value) {
        super.applyProperty(hostview, key, value);
        switch (key) {
            case "text":
                hostview.setText(value);
                break;
            case "color":
                hostview.setTextColor(Utils.parseColor(value));
                break;
            case "textSize":
                float textSize = Float.parseFloat(value);
                hostview.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                break;
            case "hint":
                hostview.setHint(value);
                break;
        }
//            if (yiew.maxLine != 0) {
//                view.setMaxLines(yiew.maxLine);
//            }
//            if (yiew.hintColor != null) {
//                view.setHintTextColor(Utils.parseColor(yiew.hintColor));
//            }
//            if (yiew.lineSpace != null) {
//                view.setLineSpacing(Utils.meature(yiew.lineSpace), 1);
//            }
//
//            if (yiew.gravity != null) {
//                view.setGravity((Integer) Utils.getValueInt(Gravity.class, yiew.gravity.toUpperCase()));
//            }
    }

//
//        ViewProcess.applyProperty(hostview, yiew);
//        LayoutProcess.applyaLayout(hostview, params, yiew);
//
//        applyTextView(hostview, yiew);


//    public static View refresh(Yiew yiew) {
////        View view = yiew.getCurrentView();
////        ViewGroup.LayoutParams params = view.getLayoutParams();
////
////        ViewProcess.applyProperty(view, yiew);
////        LayoutProcess.applyaLayout(view, params, yiew);
////
////        applyTextView(view, yiew);
//
//
//        return view;
//    }

//    public static void applyTextView(TextView view, XViewBody yiew) {
//
//        if (yiew.text != null) {
//
//            if (yiew.text.startsWith("&")) {
//
//                String a = Utils.getValueStringIfDataExist(yiew, yiew.text, yiew.text);
//                view.setText(a);
//            } else {
//                view.setText(yiew.text);
//            }
//        }
//
//
//        if (yiew.textColor != null) {
//            if (yiew.textColor.startsWith("&")) {
//                String textColor = Utils.getValueStringIfDataExist(yiew, yiew.textColor, null);
//                if (textColor != null) {
//                    view.setTextColor(Utils.parseColor(textColor));
//                }
//            } else {
//                view.setTextColor(Utils.parseColor(yiew.textColor));
//            }
//
//
//        }
//        if (yiew.textSize != null) {
//
//            if (yiew.textSize.startsWith("&")) {
//                String a = Utils.getValueStringIfDataExist(yiew, yiew.textSize, null);
//                if (a != null) {
//                    int textSize = Integer.parseInt(a.trim());
//                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
//                }
//            } else {
//                int textSize = Integer.parseInt(yiew.textSize.trim());
//
//                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
//            }
//
//
//        }
//        if (yiew.hint != null) {
//            view.setHint(yiew.hint);
//        }
//        if (yiew.maxLine != 0) {
//            view.setMaxLines(yiew.maxLine);
//        }
//        if (yiew.hintColor != null) {
//            view.setHintTextColor(Utils.parseColor(yiew.hintColor));
//        }
//        if (yiew.lineSpace != null) {
//            view.setLineSpacing(Utils.meature(yiew.lineSpace), 1);
//        }
//
//        if (yiew.gravity != null) {
//            view.setGravity((Integer) Utils.getValueInt(Gravity.class, yiew.gravity.toUpperCase()));
//        }
//
//
//    }
}
