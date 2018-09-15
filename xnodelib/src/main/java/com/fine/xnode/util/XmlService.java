package com.fine.xnode.util;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;

import com.fine.log.Finelog;
import com.fine.xnode.XNode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class XmlService {
    public static XNode logXmlData(XmlPullParser xmlParser) {
        XNode root = new XNode();
        try {
            int event = xmlParser.getEventType();   //先获取当前解析器光标在哪
            while (event != XmlPullParser.END_DOCUMENT) {    //如果还没到文档的结束标志，那么就继续往下处理
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        Finelog.d("xml解析开始");
                        break;
                    case XmlPullParser.START_TAG:
                        //一般都是获取标签的属性值，所以在这里数据你需要的数据
                        String name = xmlParser.getName();
                        Finelog.d("START_TAG  当前标签是：" + name);
                        //两种方法获取属性值

                        root.tag = name;
                        int attributeCount = xmlParser.getAttributeCount();
                        HashMap map = new HashMap();
                        for (int i = 0; i < attributeCount; i++) {
                            String attributeName = xmlParser.getAttributeName(i);
                            String attributeValue = xmlParser.getAttributeValue(i);
                            map.put(attributeName, attributeValue);
                        }
                        root.attr = map;
                        Finelog.d("map：" + map);
//                        xmlParser.getatt
                        final AttributeSet attrs = Xml.asAttributeSet(xmlParser);
                        rInflateChildren(xmlParser, root, attrs, true);
                        Finelog.d(root);
                        Finelog.d("node：" + root);

                        return root;

                    case XmlPullParser.TEXT:
                        Finelog.d("Text:" + xmlParser.getText());
                        break;
                    case XmlPullParser.END_TAG:
                        Finelog.d("END_TAG:" + xmlParser.getName());
                        break;
                    default:
                        break;
                }
                event = xmlParser.next();   //将当前解析器光标往下一步移
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void rInflate(XmlPullParser parser, XNode parent, Context context,
                         AttributeSet attrs, boolean finishInflate) throws XmlPullParserException, IOException {

        final int depth = parser.getDepth();
        Finelog.d("depth" + depth);
        int type;

        while (((type = parser.next()) != XmlPullParser.END_TAG ||
                parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            final String name = parser.getName();

            Finelog.d(name);
            XNode XNode = new XNode();
            XNode.tag = name;
            int attributeCount = parser.getAttributeCount();
            HashMap map = new HashMap();
            for (int i = 0; i < attributeCount; i++) {
                String attributeName = parser.getAttributeName(i);
                String attributeValue = parser.getAttributeValue(i);
                map.put(attributeName, attributeValue);
            }
            XNode.attr = map;


            if (parent.child == null)
                parent.child = new ArrayList<>();
            parent.child.add(XNode);
            rInflateChildren(parser, XNode, attrs, true);
        }

    }

    public static final void rInflateChildren(XmlPullParser parser, XNode parent, AttributeSet attrs,
                                              boolean finishInflate) throws XmlPullParserException, IOException {
        rInflate(parser, parent, null, attrs, finishInflate);
    }

    public static XNode loadResourceXml(Context context, int main) {
        XmlResourceParser xmlParser = context.getResources().getXml(main);
        return logXmlData(xmlParser);
    }

    public static XNode loadAsseartXml(Context context, String main) {
        XmlPullParser xmlParser = null;
        try {
            InputStream open = context.getResources().getAssets().open(main);
            xmlParser = Xml.newPullParser();
            xmlParser.setInput(open, "UTF-8");
            return logXmlData(xmlParser);
        } catch (Exception e) {
            e.printStackTrace();
            Finelog.d(e);
        }
        return null;

    }

    public static Observable<XNode> loadNetworkXml(final Context context, final String urlstr) {
        Observable<XNode> get = Observable.create(new Observable.OnSubscribe<XNode>() {
            @Override
            public void call(Subscriber<? super XNode> subscriber) {
                try {
                    URL url = new URL(urlstr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置请求方式‘
                    connection.setRequestMethod("GET");
                    //设置请求连接超时的时间（优化）
                    connection.setConnectTimeout(5000);

                    //获取结果码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        //获取服务器返回过来的结果
                        InputStream is = connection.getInputStream();
                        XmlPullParser xmlParser = Xml.newPullParser();
                        xmlParser.setInput(is, "UTF-8");
                        XNode XNode = logXmlData(xmlParser);
                        subscriber.onNext(XNode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return get;
    }
}
