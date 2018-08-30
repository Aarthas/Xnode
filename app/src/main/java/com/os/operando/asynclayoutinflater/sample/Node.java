package com.os.operando.asynclayoutinflater.sample;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    public String tag;
    public String text;
    public ArrayList<Node> child;
    //    public View viewCurrent;
//    public ViewGroup viewParent;
    public HashMap<String, String> attr;
}
