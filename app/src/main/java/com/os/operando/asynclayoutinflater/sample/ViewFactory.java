package com.os.operando.asynclayoutinflater.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;

import xjsonview.XViewComponent;
import xjsonview.process.base.BseViewComponent;
import xjsonview.process.base.FrameLayoutProcess;
import xjsonview.process.base.TextViewProcess;

public class ViewFactory {
    private static HashMap<String, BseViewComponent> componentMap = new HashMap();

    public View createView(Node node, ViewGroup parent, Context context) {
        Finelog.d("createView node = "+node.tag);
        Finelog.d(node);
        if (node.tag.equals("layout")) {
            FrameLayoutProcess frameLayoutProcess = new FrameLayoutProcess();
            View view = frameLayoutProcess.initView(context, parent, node.attr);
//            node.viewCurrent = view;

            if (node.child != null) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (Node child : node.child) {
                    View view1 = XNode.getInstance().createView(child, viewGroup, context);
                    viewGroup.addView(view1);
                }
            }
            return view;
        } else if (node.tag.equals("TextView")) {
            TextViewProcess frameLayoutProcess = new TextViewProcess();
            View view = frameLayoutProcess.initView(context, parent, node.attr);
            return view;
        }
        return null;
    }
}
