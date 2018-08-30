//package com.os.operando.asynclayoutinflater.sample;
//
//import android.content.Context;
//import android.content.res.XmlResourceParser;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.a.MutableLiveData;
//import com.a.Observer;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Set;
//
//import xjsonview.XmlHeler;
//import xjsonview.process.base.BseViewComponent;
//import xjsonview.process.base.EditTextProcess;
//import xjsonview.process.base.FrameLayoutProcess;
//import xjsonview.process.base.TextViewProcess;
//import xjsonview.process.base.VerticleProcess;
//
//public class ViewImportFactory extends BaseFactory{
//
//
//
//    @Override
//    public View createView(Node node, ViewGroup parent, Context context, ModelData modelData) {
//        XmlResourceParser xmlParser = context.getResources().getXml(R.xml.textview);
//        Node node = XmlHeler.logXmlData(xmlParser);
//        XNode.getInstance().createView(node,parent,map,modelData)
//
//    }
//
//}
