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
import com.lzy.okgo.OkGo;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import rx.functions.Action1;
import xjsonview.XmlHeler;
import xjsonview.process.base.TextViewProcess;

public class MainActivity extends AppCompatActivity {


    private ModelData modelData;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Finelog.init(true);
        context = this;
        super.onCreate(savedInstanceState);
//
//        Node node =  XmlHeler.loadResourceXml(context,R.xml.main);
//        Node node =  XmlHeler.loadAsseartXml(context,"main.xml");
         XmlHeler.loadNetworkXml(context,"http://192.168.67.81:9999/neiwrok.xml").subscribe(new Action1<Node>() {
             @Override
             public void call(Node node) {
                 modelData = new ModelData();
                 modelData.text1.setValue("aaa");
                 modelData.edittext.setValue("asdasdasda");
                 XNode.getInstance().registerGlobalComponent("mytitlebar", MyTitleBar.class);
                 XNode.getInstance().registerGlobalComponent("aa", MyTitleBar.class);
                 View contentView =   XNode.getInstance().createView(node, null, context, modelData);
                 setContentView(contentView);

                 View simple = contentView.findViewWithTag("simple");
                 simple.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Finelog.d("adsadasd");
                         modelData.text1.setValue("adsadasd");
                         Finelog.d(modelData.edittext);
                     }
                 });
             }
         });



    }



}
