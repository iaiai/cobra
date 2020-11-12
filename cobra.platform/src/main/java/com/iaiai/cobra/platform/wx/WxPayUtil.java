package com.iaiai.cobra.platform.wx;

import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.util.web.HttpsUtil;
import com.iaiai.cobra.common.util.xml.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.wx
 * Author: iaiai
 * Create Time: 2020/6/16 5:33 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
public class WxPayUtil {

    public enum SignType {
        MD5, HMACSHA256
    }

    /**
     * 统一下单
     * 参考:https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     *
     * @param appid
     * @param mchId 商户号id
     * @param nonceStr 随机字符串
     * @param body 商品描述
     * @param outTradeNo 商户订单号
     * @param totalFee 订单总金额(分)
     * @param spbillCreateIp 终端IP
     * @param timeExpire 交易结束时间
     * @param notifyUrl 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     * @param tradeType 交易类型,JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付，不同trade_type决定了调起支付的方式，请根据支付产品正确上传
     * @param openid 用户的openid
     * @param attach 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     * @throws IOException
     * @throws URISyntaxException
     */
    public static Map<String,String> unifiedorder(String appid,
                                                  String mchId,
                                                  String nonceStr,
                                                  String body,
                                                  String outTradeNo,
                                                  long totalFee,
                                                  String spbillCreateIp,
                                                  String timeExpire,
                                                  String notifyUrl,
                                                  String tradeType,
                                                  String openid,
                                                  String attach,
                                                  String key) throws Exception {
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        HashMap<String, String> data = new HashMap<>();
        data.put("appid",appid);
        data.put("attach",attach);
        data.put("nonce_str",nonceStr);
        data.put("mch_id",mchId);
        data.put("body",body);
        data.put("sign_type","MD5");
        data.put("out_trade_no",outTradeNo);
        data.put("total_fee",String.valueOf(totalFee));
        data.put("spbill_create_ip",spbillCreateIp);
        data.put("time_expire",timeExpire);
        data.put("openid",openid);
        data.put("notify_url",notifyUrl);
        data.put("trade_type",tradeType);
        String sign = WxPayUtil.generateSignature(data,key);
        data.put("sign",sign);   //签名

        StringBuilder xml = new StringBuilder("<xml>");
        xml.append("<appid>").append(data.get("appid")).append("</appid>");
        xml.append("<attach>").append(data.get("attach")).append("</attach>");
        xml.append("<nonce_str>").append(data.get("nonce_str")).append("</nonce_str>");
        xml.append("<mch_id>").append(data.get("mch_id")).append("</mch_id>");
        xml.append("<body>").append(data.get("body")).append("</body>");
        xml.append("<sign>").append(data.get("sign")).append("</sign>");
        xml.append("<sign_type>").append(data.get("sign_type")).append("</sign_type>");
        xml.append("<out_trade_no>").append(data.get("out_trade_no")).append("</out_trade_no>");
        xml.append("<total_fee>").append(data.get("total_fee")).append("</total_fee>");
        xml.append("<spbill_create_ip>").append(data.get("spbill_create_ip")).append("</spbill_create_ip>");
        xml.append("<time_expire>").append(data.get("time_expire")).append("</time_expire>");
        xml.append("<openid>").append(data.get("openid")).append("</openid>");
        xml.append("<notify_url>").append(data.get("notify_url")).append("</notify_url>");
        xml.append("<trade_type>").append(data.get("trade_type")).append("</trade_type>");
        xml.append("</xml>");

//        <xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[mch_id参数格式错误]]></return_msg></xml>
        String content = new HttpsUtil.Builder().build().post(url,xml.toString());
        return XmlUtil.xmlToMap(content);
    }

    /**
     * 生成签名
     *
     * @param data     待签名数据
     * @param key      API密钥
     * @return 签名
     */
    private static String generateSignature(final Map<String, String> data, String key) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            if (data.get(k)!=null && data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        return MD5(sb.toString()).toUpperCase();
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    private static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成签名
     *
     * @param data     待签名数据
     * @return 签名
     */
    public static String generateSignaturePaySign(final Map<String, String> data, String key) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        return MD5(sb.toString()).toUpperCase();
    }

    /**
     * 查询订单状态
     * 参考:https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_2
     * @param appid
     * @param mchId
     * @param outTradeNo
     * @param key API密钥
     * @return
     * @throws Exception
     */
    public static Map<String,String> queryOutTradeNo(String appid,
                                                     String mchId,
                                                     String outTradeNo,
                                                     String key) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("appid",appid);
        map.put("mch_id",mchId);
        map.put("out_trade_no",outTradeNo);
        map.put("nonce_str",StringUtil.get32UUID());

        String sign = generateSignaturePaySign(map,key);
        map.put("sign",sign);

        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        StringBuilder xml = new StringBuilder("<xml>");
        xml.append("<appid>").append(map.get("appid")).append("</appid>");
        xml.append("<mch_id>").append(map.get("mch_id")).append("</mch_id>");
        xml.append("<nonce_str>").append(map.get("nonce_str")).append("</nonce_str>");
        xml.append("<out_trade_no>").append(map.get("out_trade_no")).append("</out_trade_no>");
        xml.append("<sign>").append(map.get("sign")).append("</sign>");
        xml.append("</xml>");

        log.debug("xml:{}",xml.toString());
        log.debug("sign:{}",sign);
        String content = new HttpsUtil.Builder().build().post(url,xml.toString());
        log.debug(content);

        Map<String,String> resultMap = XmlUtil.xmlToMap(content);
        resultMap.put("_body",content); //把返回的数据记录下来，如果要更新订单记录此数据
        return resultMap;
    }

    /**
     * 微信退款
     * 参考：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_4
     * @param appid 小程序ID
     * @param mchId 商户号
     * @param transactionId 微信订单号
     * @param outRefundNo 退款单号
     * @param totalFee 订单金额(分)
     * @param refundFee 退款金额(分)
     * @param key API密钥
     */
    public static Map<String,String> refund(String appid,
                                            String mchId,
                                            String transactionId,
                                            String outRefundNo,
                                            int totalFee,
                                            int refundFee,
                                            String key) throws Exception {

        Map<String,String> map = new HashMap<>();
        map.put("appid",appid);
        map.put("mch_id",mchId);
        map.put("nonce_str", StringUtil.get32UUID());
        map.put("sign_type","MD5");
        map.put("transaction_id",transactionId);
        map.put("out_refund_no",outRefundNo);
        map.put("total_fee",String.valueOf(totalFee));
        map.put("refund_fee",String.valueOf(refundFee));

        String sign = generateSignature(map,key);
        map.put("sign",sign);

        StringBuilder xml = new StringBuilder("<xml>");
        xml.append("<appid>").append(map.get("appid")).append("</appid>");
        xml.append("<mch_id>").append(map.get("mch_id")).append("</mch_id>");
        xml.append("<nonce_str>").append(map.get("nonce_str")).append("</nonce_str>");
        xml.append("<sign>").append(map.get("sign")).append("</sign>");
        xml.append("<sign_type>").append(map.get("sign_type")).append("</sign_type>");
        xml.append("<transaction_id>").append(map.get("transaction_id")).append("</transaction_id>");
        xml.append("<out_refund_no>").append(map.get("out_refund_no")).append("</out_refund_no>");
        xml.append("<total_fee>").append(map.get("total_fee")).append("</total_fee>");
        xml.append("<refund_fee>").append(map.get("refund_fee")).append("</refund_fee>");
        xml.append("</xml>");

        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        Resource resource = new ClassPathResource("cert/apiclient_cert.p12");
        File file = resource.getFile();

        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(file);
        try {
            keyStore.load(instream, mchId.toCharArray());//这里写密码..默认是你的MCHID
        } finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        try {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(xml.toString(), "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);

                Map<String,String> returnMap = XmlUtil.xmlToMap(jsonStr);
                returnMap.put("___result",jsonStr);   //把原始返回的数据也返回出去
                return returnMap;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    public static void getSandboxSignKey(String mchId,String key) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("mch_id",mchId);
        map.put("nonce_str",StringUtil.get32UUID());

        String sign = generateSignature(map,key);
        map.put("sign",sign);

        StringBuilder xml = new StringBuilder("<xml>");
        xml.append("<mch_id>").append(map.get("mch_id")).append("</mch_id>");
        xml.append("<nonce_str>").append(map.get("nonce_str")).append("</nonce_str>");
        xml.append("<sign>").append(map.get("sign")).append("</sign>");
        xml.append("</xml>");

        Resource resource = new ClassPathResource("cert/apiclient_cert.p12");
        File file = resource.getFile();

        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(file);
        try {
            keyStore.load(instream, mchId.toCharArray());//这里写密码..默认是你的MCHID
        } finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        try {
            HttpPost httpost = new HttpPost("https://api.mch.weixin.qq.com/pay/getsignkey"); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(xml.toString(), "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);

                Map<String,String> returnMap = XmlUtil.xmlToMap(jsonStr);
                returnMap.put("___result",jsonStr);   //把原始返回的数据也返回出去
                log.debug("...........{}",jsonStr);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }


    public static void main(String[] args) throws Exception {
//        WxPayUtil.unifiedorder("","","","","",0,"","","","JSAPI","","","");
//        WxPayUtil.getSandboxSignKey("1600285861","f559651b11aa4f21af5ae510cfa803fd");
    }

}
