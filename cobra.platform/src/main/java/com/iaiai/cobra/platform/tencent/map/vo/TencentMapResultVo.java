package com.iaiai.cobra.platform.tencent.map.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.tencent.map.vo
 * Author: iaiai
 * Create Time: 2020/6/8 3:04 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class TencentMapResultVo {

    private TencentMapResultLocationVo location;

    private String address;

    private TencentMapResultFormattedAddressesVo formatted_addresses;

    private TencentMapResultAddressComponentVo address_component;

    private TencentMapResultAdInfoVo ad_info;

    private TencentMapResultAddressReferenceVo address_reference;

}
