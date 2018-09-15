package com.fine.xnode.viewprocess;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fine.xnode.img.ImageService;

import java.util.HashMap;


/**
 * Created by zhangyn on 17/4/5.
 */

public class ImageProcess extends ViewProcess {


    public View initView(Context context, ViewGroup parent, HashMap<String, Object> map) {
        ImageView hostview = new ImageView(context);


        return hostview;
    }



    @Override
    public void applyProperty(View hostview, String key, String value) {
        super.applyProperty(hostview, key, value);
        ImageView img = (ImageView) hostview;
        switch (key) {
            case "src":
                if (ImageService.imageHandle != null)
                    ImageService.imageHandle.display(img,value);
                break;
        }
    }


}
