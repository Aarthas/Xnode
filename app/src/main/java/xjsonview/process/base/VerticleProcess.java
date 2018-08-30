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

public class VerticleProcess extends ViewProcess {


    @Override
    public View initView(Context context, ViewGroup parent, HashMap<String, String> map) {
        LinearLayout hostview = new LinearLayout(context);
        hostview.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams params = getLayoutParams(parent, map);
        hostview.setLayoutParams(params);

        return hostview;
    }



    @Override
    public void applyProperty(View hostview, String key, String value) {
        super.applyProperty(hostview, key, value);

    }
}