package com.fine.xnode.base;

import android.view.View;
import android.view.ViewGroup;

import com.fine.xnode.XNode;
import com.fine.xnode.XNodeAdapter;

public abstract class BaseFactory {
    public abstract View createView(XNode XNode, ViewGroup parent, XNodeAdapter xNodeAdapter);
}
