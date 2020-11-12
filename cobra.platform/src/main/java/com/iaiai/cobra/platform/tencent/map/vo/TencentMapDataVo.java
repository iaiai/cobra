package com.iaiai.cobra.platform.tencent.map.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.tencent.map.vo
 * Author: iaiai
 * Create Time: 2020/6/9 5:29 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class TencentMapDataVo {

    private String id;

    private String title;

    private String address;

    private String tel;

    private String category;

    private Integer type;

    private TencentMapResultLocationVo location;

    private Double _distance;

    private TencentMapDataAdInfo ad_info;

}
