package com.iaiai.cobra.platform.tencent.map;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.tencent.map
 * Author: iaiai
 * Create Time: 2020/6/9 4:43 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class Location {

    private Double lat;

    private Double lng;

    private Integer radius; //半径

}
