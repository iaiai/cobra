package com.iaiai.cobra.platform.tencent.map.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.tencent.map.vo
 * Author: iaiai
 * Create Time: 2020/6/8 3:08 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class TencentMapResultAdInfoVo {

    private String nation_code;

    private String adcode;

    private String city_code;

    private String name;

    private TencentMapResultLocationVo location;

    private String nation;

    private String province;

    private String city;

    private String district;

}
