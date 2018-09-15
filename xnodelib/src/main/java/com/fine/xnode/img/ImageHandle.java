package com.fine.xnode.img;

import android.widget.ImageView;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface ImageHandle {
    void display(ImageView view, String imageUrl);

    void display(ImageView view, Integer resourceId);
}
