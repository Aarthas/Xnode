package com.os.operando.asynclayoutinflater.sample;


import android.content.Context;
import android.content.res.XmlResourceParser;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.a.MutableLiveData;
import com.a.Observer;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import xjsonview.process.base.TextViewProcess;

public class MainActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Finelog.init(true);
        super.onCreate(savedInstanceState);
        XmlResourceParser xmlParser = getResources().getXml(R.xml.textview_model);
        Finelog.d(" start logXmlData");
        Node node = logXmlData(xmlParser);
        Finelog.d("logXmlData result");
        Finelog.d(node);
        Context context = this;




        ModelData modelData = new ModelData();
        modelData.ti.setValue("aaa");
        XNode.getInstance().registerGlobalComponent("mytitlebar", MyTitleBar.class);
        View view1 =   XNode.getInstance().createView(node, null, context,modelData);
        setContentView(view1);
        modelData.ti.setValue("vvv");
//        Finelog.d("mytext");
//        TextView mytext = (TextView) view1.findViewWithTag("mytext");
//        mytext.setText("asf");
//        Finelog.d(mytext);

//        setContentView(view);


    }

    public ObservableBoolean name = new ObservableBoolean();


    public Node logXmlData(XmlResourceParser xmlParser) {
        Node root = new Node();
        try {
            int event = xmlParser.getEventType();   //先获取当前解析器光标在哪
            while (event != XmlPullParser.END_DOCUMENT) {    //如果还没到文档的结束标志，那么就继续往下处理
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        Finelog.d("xml解析开始");
                        break;
                    case XmlPullParser.START_TAG:
                        //一般都是获取标签的属性值，所以在这里数据你需要的数据
                        String name = xmlParser.getName();
                        Finelog.d("START_TAG  当前标签是：" + name);
                        //两种方法获取属性值

                        root.tag = name;
                        int attributeCount = xmlParser.getAttributeCount();
                        HashMap map = new HashMap();
                        for (int i = 0; i < attributeCount; i++) {
                            String attributeName = xmlParser.getAttributeName(i);
                            String attributeValue = xmlParser.getAttributeValue(i);
                            map.put(attributeName, attributeValue);
                        }
                        root.attr = map;
                        Finelog.d("map：" + map);
//                        xmlParser.getatt
                        final AttributeSet attrs = Xml.asAttributeSet(xmlParser);
                        rInflateChildren(xmlParser, root, attrs, true);
                        Finelog.d(root);
                        Finelog.d("node：" + root);

                        return root;

                    case XmlPullParser.TEXT:
                        Finelog.d("Text:" + xmlParser.getText());
                        break;
                    case XmlPullParser.END_TAG:
                        Finelog.d("END_TAG:" + xmlParser.getName());
                        break;
                    default:
                        break;
                }
                event = xmlParser.next();   //将当前解析器光标往下一步移
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void rInflate(XmlPullParser parser, Node parent, Context context,
                  AttributeSet attrs, boolean finishInflate) throws XmlPullParserException, IOException {

        final int depth = parser.getDepth();
        Finelog.d("depth" + depth);
        int type;

        while (((type = parser.next()) != XmlPullParser.END_TAG ||
                parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            final String name = parser.getName();

            Finelog.d(name);
            Node node = new Node();
            node.tag = name;
            int attributeCount = parser.getAttributeCount();
            HashMap map = new HashMap();
            for (int i = 0; i < attributeCount; i++) {
                String attributeName = parser.getAttributeName(i);
                String attributeValue = parser.getAttributeValue(i);
                map.put(attributeName, attributeValue);
            }
            node.attr = map;


            if (parent.child == null)
                parent.child = new ArrayList<>();
            parent.child.add(node);
            rInflateChildren(parser, node, attrs, true);
        }

    }

    final void rInflateChildren(XmlPullParser parser, Node parent, AttributeSet attrs,
                                boolean finishInflate) throws XmlPullParserException, IOException {
        rInflate(parser, parent, null, attrs, finishInflate);
    }
}
