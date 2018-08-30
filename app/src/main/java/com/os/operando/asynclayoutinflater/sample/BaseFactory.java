package com.os.operando.asynclayoutinflater.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFactory {
    public abstract View createView(Node node, ViewGroup parent, Context context, ModelData modelData);
}
