package com.fine.xnode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fine.xnode.base.BaseModelData;
import com.fine.xnode.base.BseViewComponent;
import com.fine.xnode.factory.ViewFactory;
import com.fine.xnode.factory.ViewFactoryGlobal;
import com.fine.xnode.factory.ViewImportFactory;


public class XNodeService {
    static XNodeService xNodeService = new XNodeService();

    public static XNodeService getInstance() {
        return xNodeService;
    }

    ViewFactoryGlobal viewFactoryGlobal = new ViewFactoryGlobal();
    ViewFactory viewFactory = new ViewFactory();
    ViewImportFactory viewImportFactory = new ViewImportFactory();
    public void registerGlobalComponent(String tag, Class<? extends BseViewComponent> bseViewComponentClazz) {
        viewFactoryGlobal.registerComponent(tag, bseViewComponentClazz);
    }

    public View createView(XNode XNode, ViewGroup parent, Context context, BaseModelData modelData) {
        View view = viewFactory.createView(XNode, parent, context,modelData);
        if (view == null) {
            view = viewFactoryGlobal.createView(XNode, parent, context,modelData);
        }
        if (view == null) {
            view = viewImportFactory.createView(XNode, parent, context,modelData);
        }
        return view;
    }
}
