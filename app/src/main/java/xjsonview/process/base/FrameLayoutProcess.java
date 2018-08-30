package xjsonview.process.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import java.util.HashMap;
import java.util.Set;

/**
 * Created by zhangyn on 17/4/5.
 */

public class FrameLayoutProcess extends ViewProcess {


    @Override
    public View initView(Context context, ViewGroup parent, HashMap<String, String> map) {
        FrameLayout hostview = new FrameLayout(context);
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
        super.applyProperty(hostview,key,value);
    }
}
