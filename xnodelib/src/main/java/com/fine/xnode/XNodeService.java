package com.fine.xnode;

import android.view.View;
import android.view.ViewGroup;

import com.fine.xnode.factory.ViewFactory;


public class XNodeService {

    private final XNodeAdapter xNodeAdapter;

    public XNodeService(XNodeAdapter xNodeAdapter) {
        this.xNodeAdapter = xNodeAdapter;
    }


    public View createView(XNode XNode, ViewGroup parent) {
        View view = ViewFactory.get().createView(XNode, parent, xNodeAdapter);
        return view;
    }
}
