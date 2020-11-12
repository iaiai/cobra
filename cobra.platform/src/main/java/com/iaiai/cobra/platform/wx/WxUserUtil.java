package com.iaiai.cobra.platform.wx;

import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.util.web.HttpsUtil;
import com.iaiai.cobra.platform.wx.bean.WxCode2Session;
import com.iaiai.cobra.platform.wx.bean.WxEncrypted;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.wx
 * Author: iaiai
 * Create Time: 2020/6/4 5:13 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
public class WxUserUtil {

    /**
     * 获取微信用户的openId和unionid
     * 参数地址:https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
     */
    public static String code2Session(String appId,String secret,String code) throws IOException, URISyntaxException {
        Map<String,String> headers = new HashMap<>();
        headers.put("content-type","application/json");

        Map<String,String> map = new HashMap<>();
        map.put("appid",appId);
        map.put("secret",secret);
        map.put("js_code",code);
        map.put("grant_type","authorization_code");

        String content = new HttpsUtil.Builder().build().get("https://api.weixin.qq.com/sns/jscode2session",map,headers);

        return content;
    }

    /**
     * 把上面那个返回的数据转成对接，里面有sessionKey
     * @param content
     */
    public static WxCode2Session code2SessionToBean(String content) throws IOException {
        Map<String,Object> map = JsonUtil.getInstance().deserialize(content,Map.class);

        WxCode2Session session = new WxCode2Session();
        session.setOpenid(map.get("openid")!=null?map.get("openid").toString():null);
        session.setSessionKey(map.get("session_key")!=null?map.get("session_key").toString():null);
        if(map.get("unionid")!=null) {
            session.setUnionid(map.get("unionid").toString());
        }
        if(map.get("unionId")!=null) {
            session.setUnionid(map.get("unionId").toString());
        }
        session.setErrcode(map.get("errcode")!=null?Integer.parseInt(map.get("errcode").toString()):null);
        session.setErrmsg(map.get("errmsg")!=null?map.get("errmsg").toString():null);

        return session;
    }

    /**
     * 解密数据
     * @param encryptedData
     * @param iv
     * @param sessionKey 参考上面code2Session此接口返回数据中有
     * @return
     * @throws Exception
     *
     * 参考:https://developers.weixin.qq.com/community/develop/doc/00008e73a3c5a0ac4d7ae341c5ac00
     */
    public static String encryptedDataDecrypt(String encryptedData, String iv, String sessionKey) throws Exception {
        // 被加密的数据
        byte[] dataByte = new Base64().decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = new Base64().decode(sessionKey);
        // 偏移量
        byte[] ivByte = new Base64().decode(iv);

        // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            return new String(resultByte, "UTF-8");
        }

        return null;
    }

    /**
     * 把数据转为对象
     * @param json
     * @return
     * @throws IOException
     */
    public static WxEncrypted encryptedDataToBean(String json) throws IOException {
        return JsonUtil.getInstance().deserialize(json, WxEncrypted.class);
    }

}
