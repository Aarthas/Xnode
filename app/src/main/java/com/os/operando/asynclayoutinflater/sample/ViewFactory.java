package com.os.operando.asynclayoutinflater.sample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;

import com.a.MutableLiveData;
import com.a.Observer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

import xjsonview.process.base.BseViewComponent;
import xjsonview.process.base.EditTextProcess;
import xjsonview.process.base.FrameLayoutProcess;
import xjsonview.process.base.TextViewProcess;
import xjsonview.process.base.VerticleProcess;

public class ViewFactory extends BaseFactory{
    public static HashMap<String, BseViewComponent> componentMap = new HashMap();
    public static HashMap<String, Class<? extends BseViewComponent>> componentClass = new HashMap();

    public void registerComponent(String tag, Class<? extends BseViewComponent> bseViewComponentClazz) {
        componentClass.put(tag, bseViewComponentClazz);
    }

    static {
        componentClass.put("layout", FrameLayoutProcess.class);
        componentClass.put("TextView", TextViewProcess.class);
        componentClass.put("verticlelayout", VerticleProcess.class);
        componentClass.put("EditText", EditTextProcess.class);
    }

    @Override
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
                final String value = attr.get(key);
                if (key.startsWith("bind-")) {
                    Finelog.d("start get  bind- = " + key);
                    final String realKey = key.substring(5, key.length());
                    Finelog.d("start get fieldName= " + realKey);
                    try {
                        Field declaredField = modelData.getClass().getDeclaredField(value);
                        declaredField.setAccessible(true);
                        MutableLiveData<String> livedata = (MutableLiveData<String>) declaredField.get(modelData);

                        final BseViewComponent finalBseViewComponent = bseViewComponent;
                        livedata.observeForever(new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String s) {
                                finalBseViewComponent.applyProperty(view,realKey,s);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (key.startsWith("data")) {
                    Finelog.d("start get  data = " + key);
                    try {
                        Field declaredField = modelData.getClass().getDeclaredField(value);
                        declaredField.setAccessible(true);
                        MutableLiveData<String> livedata = (MutableLiveData<String>) declaredField.get(modelData);

                        bseViewComponent.applyValue(view, livedata);
                        bseViewComponent.emitValue(view, livedata);


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
