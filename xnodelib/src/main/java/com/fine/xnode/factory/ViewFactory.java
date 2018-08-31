package com.fine.xnode.factory;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;


import com.fine.log.Finelog;
import com.fine.xnode.XNode;
import com.fine.xnode.XNodeService;
import com.fine.xnode.base.BaseFactory;
import com.fine.xnode.base.BaseModelData;
import com.fine.xnode.base.BseViewComponent;
import com.fine.xnode.livedata.MyLiveData;
import com.fine.xnode.livedata.Observer;
import com.fine.xnode.viewprocess.EditTextProcess;
import com.fine.xnode.viewprocess.FrameLayoutProcess;
import com.fine.xnode.viewprocess.TextViewProcess;
import com.fine.xnode.viewprocess.VerticleProcess;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;


public class ViewFactory extends BaseFactory {
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
    public View createView(XNode XNode, ViewGroup parent, Context context, BaseModelData modelData) {
        Finelog.d("createView node = " + XNode.tag);
        Finelog.d(XNode);
        String tag = XNode.tag;
        HashMap<String, String> attr = XNode.attr;
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
                        MyLiveData<String> livedata = (MyLiveData<String>) declaredField.get(modelData);

                        final BseViewComponent finalBseViewComponent = bseViewComponent;
                        livedata.observeForever(new Observer<String>() {
                            @Override
                            public void onChanged(@Nullable String realValue) {
                                finalBseViewComponent.applyProperty(view, realKey, realValue);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (key.startsWith("prop-")) {
                    Finelog.d("start get  bind- = " + key);
                    final String realKey = key.substring(5, key.length());
                    Finelog.d("start get fieldName= " + realKey);
                    try {
                        String realValue = modelData.propMap.get(value);
                        bseViewComponent.applyProperty(view,realKey,realValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (key.startsWith("data")) {
                    Finelog.d("start get  data = " + key);
                    try {
                        Field declaredField = modelData.getClass().getDeclaredField(value);
                        declaredField.setAccessible(true);
                        MyLiveData<String> livedata = (MyLiveData<String>) declaredField.get(modelData);

                        bseViewComponent.applyValue(view, livedata);
                        bseViewComponent.emitValue(view, livedata);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    bseViewComponent.applyProperty(view, key, value);
                }


            }
            if (XNode.child != null) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (XNode child : XNode.child) {
                    View view1 = XNodeService.getInstance().createView(child, viewGroup, context, modelData);

                    if (view1 != null)
                    {
                        viewGroup.addView(view1);
                    }else
                    {
                        Finelog.e(child.tag+"  为空");
                    }

                }
            }

            return view;

        }
        return null;

    }

}
