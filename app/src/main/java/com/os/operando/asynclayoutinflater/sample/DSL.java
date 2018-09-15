package com.os.operando.asynclayoutinflater.sample;

import com.fine.xnode.XNode;

public class DSL {
    public static XNode Xnode(String tag) {
        return new XNode(tag);
    }
    public static XNode verticlelayout() {
        return new XNode("verticlelayout");
    }
}
