package com.os.operando.asynclayoutinflater.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Set;

import xjsonview.process.base.BseViewComponent;
import xjsonview.process.base.FrameLayoutProcess;
import xjsonview.process.base.TextViewProcess;

public class ViewFactoryGlobal {
    public static HashMap<String, BseViewComponent> componentMap = new HashMap();
    public static HashMap<String, Class<? extends BseViewComponent>> componentClass = new HashMap();

    public void registerComponent(String tag, Class<? extends BseViewComponent> bseViewComponentClazz) {
        componentClass.put(tag, bseViewComponentClazz);
    }

    public View createView(Node node, ViewGroup parent, Context context) {
        Finelog.d("createView node = " + node.tag);
        Finelog.d(node);
        String tag = node.tag;
        HashMap<String, String> attr = node.attr;
        BseViewComponent bseViewComponent = componentMap.get(tag);
        if (bseViewComponent == null) {
            Class<? extends BseViewComponent> bseViewComponentClass = componentClass.get(tag);
            if (bseViewComponentClass ==null)
            {
//                throw new IllegalArgumentException("no this tag in factory "+tag);
                return null;
            }
            try {
                bseViewComponent = bseViewComponentClass.newInstance();
                componentMap.put(tag, bseViewComponent);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        View view = bseViewComponent.initView(context, parent, attr);
        if (view != null) {
            Set<String> keys = attr.keySet();
            for (String key : keys) {
                String value = attr.get(key);
                bseViewComponent.applyProperty(view, key, value);
            }
            if (node.child != null) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (Node child : node.child) {
                    View view1 = XNode.getInstance().createView(child, viewGroup, context);
                    viewGroup.addView(view1);
                }
            }

            return view;

        }
        return null;

    }

}
