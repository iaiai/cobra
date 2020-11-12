package com.iaiai.cobra.platform.tencent.map.vo;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.tencent.map.vo
 * Author: iaiai
 * Create Time: 2020/6/8 3:02 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class TencentMapVo {

    private Integer status;

    private String message;

    private Integer count;

    private String request_id;

    private String requestId;

    private TencentMapResultVo result;

    private List<TencentMapDataVo> data;

}
