package com.os.operando.asynclayoutinflater.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import xjsonview.process.base.BseViewComponent;

public class XNode {
    static XNode xNode = new XNode();

    public static XNode getInstance() {
        return xNode;
    }

    ViewFactoryGlobal viewFactoryGlobal = new ViewFactoryGlobal();
    ViewFactory viewFactory = new ViewFactory();

    public void registerGlobalComponent(String tag, Class<? extends BseViewComponent> bseViewComponentClazz) {
        viewFactoryGlobal.registerComponent("mytitlebar", bseViewComponentClazz);
    }

    public View createView(Node node, ViewGroup parent, Context context, ModelData modelData) {
        View view = viewFactory.createView(node, parent, context,modelData);
        if (view == null) {
            view = viewFactoryGlobal.createView(node, parent, context,modelData);
        }
        return view;
    }
}
