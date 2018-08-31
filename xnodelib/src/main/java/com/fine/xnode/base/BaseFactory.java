package com.fine.xnode.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fine.xnode.XNode;

public abstract class BaseFactory {
    public abstract View createView(XNode xNode, ViewGroup parent, Context context, BaseModelData modelData);
}
