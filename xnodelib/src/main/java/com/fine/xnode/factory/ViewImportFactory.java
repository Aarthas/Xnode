package com.fine.xnode.factory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fine.log.Finelog;
import com.fine.xnode.XNode;
import com.fine.xnode.XNodeService;
import com.fine.xnode.XmlService;
import com.fine.xnode.base.BaseFactory;
import com.fine.xnode.base.BaseModelData;

import java.util.HashMap;


public class ViewImportFactory extends BaseFactory {


    @Override
    public View createView(XNode xNode, ViewGroup parent, Context context, BaseModelData modelData) {
        Finelog.d("" + xNode.tag);
        if (xNode.tag.equals("import")) {
            //./textview.xml
            String form = xNode.attr.get("form");

            XNode newnode = XmlService.loadAsseartXml(context, form);
            HashMap<String, String> attr = xNode.attr;
            modelData.propMap.putAll(attr);
            Finelog.d(modelData.propMap);
            return XNodeService.getInstance().createView(newnode, parent, context, modelData);
        }
        return null;

    }

}
