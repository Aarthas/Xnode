package com.os.operando.asynclayoutinflater.sample;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fine.log.Finelog;
import com.fine.xnode.XNode;
import com.fine.xnode.XNodeService;
import com.fine.xnode.XmlService;


public class MainActivity extends AppCompatActivity {


    private ModelData modelData = new ModelData();
    ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Finelog.init(true);
        context = this;
        super.onCreate(savedInstanceState);
//
//        Node node =  XmlHeler.loadResourceXml(context,R.xml.main);
//        Node node =  XmlHeler.loadAsseartXml(context,"main.xml");
//        Node node = XmlHeler.loadResourceXml(context, R.xml.main);

        modelData.text1.setValue("aaa");
        modelData.edittext.setValue("asdasdasda");
//        XNode.getInstance().registerGlobalComponent("mytitlebar", MyTitleBar.class);
        XNodeService.getInstance().registerGlobalComponent("mytitlebar", MyTitleBar.class);
        XNode xNode = XmlService.loadAsseartXml(context, "main.xml");
        View contentView = XNodeService.getInstance().createView(xNode, null, context, modelData);
        setContentView(contentView);

//        View simple = contentView.findViewWithTag("simple");
//        simple.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Finelog.d("adsadasd");
//                modelData.text1.setValue("adsadasd");
//                Finelog.d(modelData.edittext);
//            }
//        });
    }


}



