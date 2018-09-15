package com.os.operando.asynclayoutinflater.sample;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fine.xnode.TagConfigHelp;
import com.fine.log.Finelog;
import com.fine.xnode.XNode;
import com.fine.xnode.XNodeAdapter;
import com.fine.xnode.util.XmlService;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {


    private ModelData modelData = new ModelData();
    ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Finelog.init(true);
        context = this;
        TagConfigHelp.registerComponent("mytitlebar", MyTitleBar.class);
        super.onCreate(savedInstanceState);
//
//        Node node =  XmlHeler.loadResourceXml(context,R.xml.main);
//        Node node =  XmlHeler.loadAsseartXml(context,"main.xml");
//        Node node = XmlHeler.loadResourceXml(context, R.xml.main);

//        XNode xNode = XmlService.loadAsseartXml(context, "main.xml");

        XNode vet = DSL.verticlelayout()
                .child(DSL.Xnode("mytitlebar").bind("titleText", modelData.edittext)
                        .child(DSL.Xnode("EditText").matchWidth().data(modelData.edittext)));


        XNodeAdapter xNodeAdapter = new XNodeAdapter(context);
//        xNodeAdapter.setModelData(modelData);
        View process = xNodeAdapter.parse(vet);
        setContentView(process);
        modelData.edittext.setValue("ccc");
    }


}



