package com.fine.xnode;

import com.fine.xnode.livedata.MyLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class XNode {
    public String tag;
    public String text;
    public ArrayList<XNode> child;
    public HashMap<String, Object> attr;

    public XNode() {

    }


    public XNode(String tag) {
        this.tag = tag;
    }

    public XNode child(XNode... xNode) {
        if (child == null)
            child = new ArrayList<>();
        List<XNode> xNodes = Arrays.asList(xNode);
        child.addAll(xNodes);
        return this;
    }

    public XNode bind(String key, String value) {
        checkNull();
        attr.put("bind-" + key, value);
        return this;
    }

    public XNode data(String data) {
        checkNull();
        attr.put("data", data);
        return this;
    }

    private void checkNull() {
        if (attr == null)
            attr = new HashMap<>();
    }

    public XNode bind(String key, MyLiveData<String> data) {
        checkNull();
        attr.put("bind-" + key, data);
        return this;
    }

    public XNode data(MyLiveData<String> data) {
        checkNull();
        attr.put("data", data);
        return this;
    }

    public XNode matchWidth() {
        checkNull();
        attr.put("width", "match");
        return this;
    }

    public XNode width(String width) {
        checkNull();
        attr.put("width", width);
        return this;
    }
}
