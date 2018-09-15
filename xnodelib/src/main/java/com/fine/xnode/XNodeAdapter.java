package com.fine.xnode;

import android.content.Context;
import android.view.View;

import com.fine.xnode.base.BaseModelData;

public class XNodeAdapter {
    private final Context context;
    public BaseModelData modelData;

    public void setModelData(BaseModelData modelData) {
        this.modelData = modelData;
    }

    public Context getContext() {
        return context;
    }

    public BaseModelData getModelData() {
        return modelData;
    }

    public XNodeAdapter(Context context) {
        this.context=context;
    }

    public View parse(XNode xNode) {
        XNodeService xNodeService = new XNodeService(this);
        View contentView = xNodeService.createView(xNode,null);
        return contentView;
    }
}
