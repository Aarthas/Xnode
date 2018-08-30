package xjsonview.process.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a.MutableLiveData;

import java.util.HashMap;

import xjsonview.Utils;

public abstract class BseViewComponent {
    public abstract View initView(Context context, ViewGroup parent, HashMap<String, String> attr);

    public abstract void applyProperty(View hostview, String key, String value);

    protected ViewGroup.LayoutParams getLayoutParams(ViewGroup parent, HashMap<String, String> attr) {
        if (parent != null) {
            String width = attr.get("width");
            String height = attr.get("height");
            attr.remove("width");
            attr.remove("height");
            ViewGroup.LayoutParams layoutParams = Utils.createLayoutParams(parent, width, height);
            return layoutParams;
        } else {
            ViewGroup.LayoutParams layoutParams = Utils.createLayoutParams(parent, "match", "match");
            return layoutParams;
        }

    }

    public LayoutInflater getLayoutInflater(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater;
    }

    public void emitValue(View view, MutableLiveData<String> livedata) {

    }

    public void applyValue(View view, MutableLiveData<String> livedata) {

    }
}
