package xjsonview;

import android.widget.ImageView;

import xjsonview.bean.XViewBase;


/**
 * Created by zhangyn on 17/3/30.
 */

public interface XViewImageAdapter {
    void display(ImageView view, String src, XViewBase yiew);
}
