package com.os.operando.asynclayoutinflater.sample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.a.MutableLiveData;
import com.a.Observer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

import xjsonview.process.base.BseViewComponent;
import xjsonview.process.base.FrameLayoutProcess;
import xjsonview.process.base.TextViewProcess;
import xjsonview.process.base.VerticleProcess;

public class ViewFactory {
    public static HashMap<String, BseViewComponent> componentMap = new HashMap();
    public static HashMap<String, Class<? extends BseViewComponent>> componentClass = new HashMap();

    public void registerComponent(String tag, Class<? extends BseViewComponent> bseViewComponentClazz) {
        componentClass.put(tag, bseViewComponentClazz);
    }

    static {
        componentClass.put("layout",FrameLayoutProcess.class);
        componentClass.put("text",TextViewProcess.class);
        componentClass.put("verticlelayout",VerticleProcess.class);
    }
    public View createView(Node node, ViewGroup parent, Context context, ModelData modelData) {
        Finelog.d("createView node = " + node.tag);
        Finelog.d(node);
        String tag = node.tag;
        HashMap<String, String> attr = node.attr;
        BseViewComponent bseViewComponent = componentMap.get(tag);
        if (bseViewComponent == null) {
            Class<? extends BseViewComponent> bseViewComponentClass = componentClass.get(tag);
            if (bseViewComponentClass == null) {
//                throw new IllegalArgumentException("no this tag in factory "+tag);
                return null;
            }
            try {
                bseViewComponent = bseViewComponentClass.newInstance();
                componentMap.put(tag, bseViewComponent);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        final View view = bseViewComponent.initView(context, parent, attr);
        if (view != null) {
            Set<String> keys = attr.keySet();
            for (final String key : keys) {
                String value = attr.get(key);
                if (value.startsWith("model.")) {
                    Finelog.d("start get  model = " + value);
                    String fieldName = value.substring(6, value.length());
                    Finelog.d("start get fieldName= " + fieldName);
                    try {
                        Field declaredField = modelData.getClass().getDeclaredField(fieldName);
                        declaredField.setAccessible(true);
                        MutableLiveData<String> o = (MutableLiveData<String>) declaredField.get(modelData);
                        value = (String) o.getValue();

                        final BseViewComponent finalbseViewComponent = bseViewComponent;
                        final String finalValue = value;
                        o.observeForever(new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String s) {
                                Finelog.d("onChanged key=" + key + ", value=" + s);
                                finalbseViewComponent.applyProperty(view, key, s);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    bseViewComponent.applyProperty(view, key, value);
                }


            }
            if (node.child != null) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (Node child : node.child) {
                    View view1 = XNode.getInstance().createView(child, viewGroup, context, modelData);
                    viewGroup.addView(view1);
                }
            }

            return view;

        }
        return null;

    }

}
