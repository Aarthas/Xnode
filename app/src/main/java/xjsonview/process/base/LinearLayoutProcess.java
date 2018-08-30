package xjsonview.process.base;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Set;

import xjsonview.Utils;

/**
 * Created by zhangyn on 17/4/5.
 */

public class LinearLayoutProcess extends ViewProcess {


    @Override
    public View initView(Context context, ViewGroup parent, HashMap<String, String> map) {
        LinearLayout hostview = new LinearLayout(context);
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
        super.applyProperty(hostview, key, value);

//        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) params;
//        if (yiew.layout_gravity != null)
//            params1.gravity = (int) Utils.getValueInt(Gravity.class, yiew.layout_gravity.toUpperCase());
//        if (yiew.weight != 0)
//            params1.weight = yiew.weight;
    }
}
