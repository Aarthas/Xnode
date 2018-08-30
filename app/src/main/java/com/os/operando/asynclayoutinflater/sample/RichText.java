package com.os.operando.asynclayoutinflater.sample;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

/**
 *  MutableLiveData liveData = new MutableLiveData() ;
 *         liveData.setValue("ccc");
 *         liveData.observeForever(new Observer() {
 *             @Override
 *             public void onChanged(@Nullable Object o) {
 *                 Finelog.d(o);
 *             }
 *         });
 *         liveData.setValue("aa");
 *         liveData.setValue("bb");
 *         liveData.justSetValue("bb");
 */
public class RichText extends com.taobao.weex.ui.component.WXComponent {
    public RichText(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, boolean isLazy) {
        super(instance, dom, parent, isLazy);
    }

    @Override
    protected void initView() {

    }
    //    public RichText(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
//        super(instance, dom, parent);
//    }
//
//    @Override
//    protected TextView initComponentHostView(@NonNull Context context) {
//        TextView textView = new TextView(context);
//        textView.setTextSize(22);
//        textView.setTextColor(Color.BLACK);
//        return textView;
//    }
//
//    @WXComponentProp(name = "tel")
//    public void setTel(String telNumber) {
//        getHostView().setText("tel: " + telNumber);
//    }
}