package com.fine.xnode;

import java.util.ArrayList;
import java.util.HashMap;

public class XNode {
    public String tag;
    public String text;
    public ArrayList<XNode> child;
    //    public View viewCurrent;
//    public ViewGroup viewParent;
    public HashMap<String, String> attr;
}
