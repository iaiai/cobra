package com.iaiai.cobra.common.vo;

import com.iaiai.cobra.common.vo.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Package: com.xproject.xcommons.vo
 * Author: iaiai
 * Create Time: 2017/11/6 下午7:53
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class ResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    //状态码
    private Integer code = ResultCode.SUCCESS.getValue();

    //出错内容
    private String msg;

}
