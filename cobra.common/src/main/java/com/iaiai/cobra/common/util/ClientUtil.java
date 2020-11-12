package com.iaiai.cobra.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.framework.util
 * Author: iaiai
 * Create Time: 16/7/11 下午5:09
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public final class ClientUtil {

    /**
     * 获取客户端ip
     * @param request
     * @return
     */
    public final static String getClientIp(HttpServletRequest request){
        StringBuilder ipAddress = new StringBuilder();
        if(request.getHeader("x-forwarded-for")!=null||request.getHeader("X-Forwarded-For")!=null){
            if(request.getHeader("x-forwarded-for")!=null){
                ipAddress.append(request.getHeader("x-forwarded-for")).append(":");
            }else if(request.getHeader("X-Forwarded-For")!=null){
                ipAddress.append(request.getHeader("X-Forwarded-For")).append(":");
            }
        }else{
            ipAddress.append(":");
        }
        if(request.getHeader("proxy-client-ip")!=null||request.getHeader("Proxy-Client-IP")!=null){
            if(request.getHeader("proxy-client-ip")!=null){
                ipAddress.append(request.getHeader("proxy-client-ip")).append(":");
            }else if(request.getHeader("Proxy-Client-IP")!=null){
                ipAddress.append(request.getHeader("Proxy-Client-IP")).append(":");
            }
        }else{
            ipAddress.append(":");
        }
        if(request.getHeader("wl-proxy-client-ip")!=null||request.getHeader("WL-Proxy-Client-IP")!=null){
            if(request.getHeader("wl-proxy-client-ip")!=null){
                ipAddress.append(request.getHeader("wl-proxy-client-ip")).append(":");
            }else if(request.getHeader("WL-Proxy-Client-IP")!=null){
                ipAddress.append(request.getHeader("WL-Proxy-Client-IP")).append(":");
            }
        }else{
            ipAddress.append(":");
        }
        if(request.getRemoteAddr()!=null) {
            ipAddress.append(request.getRemoteAddr());
        }
        return ipAddress.toString();
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 参考文章： http://developer.51cto.com/art/201111/305181.htm
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            String ipAddress = request.getHeader("Proxy-Client-IP");
            ip = StringUtil.isEmpty(ipAddress)?ip:ipAddress;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            String ipAddress = request.getHeader("WL-Proxy-Client-IP");
            ip = StringUtil.isEmpty(ipAddress)?ip:ipAddress;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            String ipAddress = request.getHeader("HTTP_CLIENT_IP");
            ip = StringUtil.isEmpty(ipAddress)?ip:ipAddress;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            String ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            ip = StringUtil.isEmpty(ipAddress)?ip:ipAddress;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            String ipAddress = request.getRemoteAddr();
            ip = StringUtil.isEmpty(ipAddress)?ip:ipAddress;
        }

        if (ip != null && ip.indexOf(",") >= 0) {
            String[] ips = ip.split(",");
            for (String i : ips) {
                if (StringUtil.isNotEmpty(i) && !"unknown".equals(ip.toLowerCase())) {
                    return i.trim();
                }
            }
            return null;
        } else {
            return ip;
        }
    }

    public final static String getSelfHost(){
        StringBuilder str = new StringBuilder();
        try {
            Enumeration<NetworkInterface> interfs = NetworkInterface.getNetworkInterfaces();
            while (interfs.hasMoreElements()) {
                NetworkInterface interf = interfs.nextElement();
                Enumeration<InetAddress> addres = interf.getInetAddresses();
                while (addres.hasMoreElements()) {
                    InetAddress in = addres.nextElement();
                    if (in instanceof Inet4Address) {
                        if(str.length()>0)str.append("\r\n");
                        str.append("v4:" + in.getHostAddress());
                    } else if (in instanceof Inet6Address) {
                        if(str.length()>0)str.append("\r\n");
                        str.append("v6:" + in.getHostAddress());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return str.length()<=0?null:str.toString();
    }

    /**
     * 获取所有的头信息
     * @param request
     * @return
     */
    public final static Map<String, Object> getHeader(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> keys = request.getHeaderNames();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            map.put(key,request.getHeader(key));
        }
        return map;
    }

    /**
     * 获取所有的信息
     * @param request
     * @return
     */
    public final static Map<String, String> getParameter(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            map.put(key,request.getParameter(key));
        }
        return map;
    }

    /**
     * 获取所有的头信息
     * @param request
     * @return
     */
    public final static String getHeaderReturnString(HttpServletRequest request){
        StringBuilder header = new StringBuilder();
        Enumeration keys = request.getHeaderNames();

        if(keys!=null) {
            while (keys.hasMoreElements()) {
                try {
                    Object key = keys.nextElement();
                    if (key == null) continue;

                    Enumeration<String> values = request.getHeaders(key.toString());
                    while (values.hasMoreElements()) {
                        Object val = values.nextElement();
                        if (val == null) continue;

                        header.append(String.format("%s:%s", new Object[]{key, val})).append("\r\n");
                    }
                }catch (Exception e){

                }
            }
        }

        return header.toString();
    }

    /**
     * 获取所有的头信息
     * @param response
     * @return
     */
    public final static String getHeaderReturnString(HttpServletResponse response){
        StringBuilder header = new StringBuilder();
        Iterator<String> keys = response.getHeaderNames().iterator();

        if(keys!=null) {
            while (keys.hasNext()) {
                try {
                    String key = keys.next();
                    if (key == null) continue;

                    Iterator<String> values = response.getHeaders(key).iterator();
                    while (values.hasNext()) {
                        String val = values.next();
                        if (val == null) continue;

                        header.append(String.format("%s:%s", new Object[]{key, val})).append("\r\n");
                    }
                }catch (Exception e){

                }
            }
        }

        return header.toString();
    }

    public static String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }

    /**
     * 获取所有的头信息
     * @param request
     * @return
     */
    public final static String getBodyReturnString(HttpServletRequest request){
        try {
//            request.setCharacterEncoding("UTF-8");
//            int size = request.getContentLength();
//
//            InputStream is = request.getInputStream();
//            byte[] reqBodyBytes = readBytes(is, size);
//
//            return new String(reqBodyBytes);

//            return getBodyString(request.getReader());

            StringBuilder params = new StringBuilder();

            if(request.getParameterNames()!=null){
                Enumeration<String> keys = request.getParameterNames();
                while (keys.hasMoreElements()){
                    String key = keys.nextElement();
                    String[] values = request.getParameterValues(key);
                    if(values!=null){
                        if(values.length>1){
                            params.append(String.format("%s=[%s]", key, values));
                        }else {
                            params.append(String.format("%s=%s", key, values[0]));
                        }
                        params.append("\r\n");
                    }
                }
            }

            return params.toString();
        }catch (Exception e){
            return null;
        }
    }

    public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];

            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {

            }
        }
        return new byte[] {};
    }

}
