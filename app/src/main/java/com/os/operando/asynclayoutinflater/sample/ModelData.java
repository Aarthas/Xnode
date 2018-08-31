package com.os.operando.asynclayoutinflater.sample;

import android.arch.lifecycle.MutableLiveData;

import com.fine.xnode.base.BaseModelData;
import com.fine.xnode.livedata.MyLiveData;

public class ModelData extends BaseModelData {
    MyLiveData<String> text1 = new MyLiveData<>();
    MyLiveData<String> edittext = new MyLiveData<>();
}
