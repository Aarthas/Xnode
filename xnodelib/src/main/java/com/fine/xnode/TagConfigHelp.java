package com.fine.xnode;

import com.fine.xnode.base.BseViewComponent;
import com.fine.xnode.viewprocess.EditTextProcess;
import com.fine.xnode.viewprocess.FrameLayoutProcess;
import com.fine.xnode.viewprocess.TextViewProcess;
import com.fine.xnode.viewprocess.VerticleProcess;

import java.util.HashMap;

public class TagConfigHelp
{
    public static HashMap<String, BseViewComponent> componentMap = new HashMap();
    public static HashMap<String, Class<? extends BseViewComponent>> componentClass = new HashMap();

    public static void registerComponent(String tag, Class<? extends BseViewComponent> bseViewComponentClazz) {
        componentClass.put(tag, bseViewComponentClazz);
    }

    static {
        componentClass.put("layout", FrameLayoutProcess.class);
        componentClass.put("TextView", TextViewProcess.class);
        componentClass.put("verticlelayout", VerticleProcess.class);
        componentClass.put("EditText", EditTextProcess.class);
    }

    public static BseViewComponent get(String tag) {
        BseViewComponent bseViewComponent = componentMap.get(tag);
        if (bseViewComponent == null) {
            Class<? extends BseViewComponent> bseViewComponentClass = componentClass.get(tag);
            if (bseViewComponentClass == null) {
                return null;
            }
            try {
                bseViewComponent = bseViewComponentClass.newInstance();
                componentMap.put(tag, bseViewComponent);
                return bseViewComponent;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
