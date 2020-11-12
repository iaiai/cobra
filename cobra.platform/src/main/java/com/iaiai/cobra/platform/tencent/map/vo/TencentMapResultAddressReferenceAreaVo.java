package com.iaiai.cobra.platform.tencent.map.vo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.tencent.map.vo
 * Author: iaiai
 * Create Time: 2020/6/9 3:32 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class TencentMapResultAddressReferenceAreaVo {

    private String id;

    private String title;

    private TencentMapResultLocationVo location;

    private Integer _distance;

    private String _dir_desc;

}
