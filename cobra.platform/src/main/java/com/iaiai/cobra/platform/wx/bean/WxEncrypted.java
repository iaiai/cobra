package com.iaiai.cobra.platform.wx.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.wx.bean
 * Author: iaiai
 * Create Time: 2020/6/16 4:40 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class WxEncrypted {

    private String phoneNumber;

    private String purePhoneNumber;

    private String countryCode;

    private WxEncryptedWatermark watermark;

    @Data
    public class WxEncryptedWatermark{

        private Long timestamp;

        private String appid;

    }

}
