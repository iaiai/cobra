package com.iaiai.cobra.common.util.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package: com.xzly.common.util
 * Author: iaiai
 * Create Time: 2020/6/17 9:15 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public class XmlUtil {

    public static Map<String, String> xmlToMap(String xml) throws UnsupportedEncodingException, DocumentException {
        return XmlUtil.xmlToMap(xml,"utf-8");
    }

    /**
     *
     * @param xml 要转换的xml字符串
     * @param charset 字符编码
     * @return  转换成map后返回结果
     * @throws UnsupportedEncodingException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(String xml, String charset) throws UnsupportedEncodingException, DocumentException {
        Map<String, String> respMap = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(xml.getBytes(charset)));
        Element root = doc.getRootElement();
        xmlToMap(root, respMap);
        return respMap;
    }

    public static Map<String, String> xmlToMap(Element tmpElement, Map<String, String> respMap){
        ArrayList<String> strings = new ArrayList<String>();
        for (String s:strings) {

        }
        if (tmpElement.isTextOnly()) {
            respMap.put(tmpElement.getName(), tmpElement.getText());
            return respMap;
        }

        @SuppressWarnings("unchecked")
        Iterator<Element> eItor = tmpElement.elementIterator();
        while (eItor.hasNext()) {
            Element element = eItor.next();
            xmlToMap(element, respMap);
        }
        return respMap;
    }

}
