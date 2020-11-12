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
 * Description: 坐标相对位置参考
 */
@Data
public class TencentMapResultAddressReferenceVo {

    private TencentMapResultAddressReferenceAreaVo business_area;

    private TencentMapResultAddressReferenceAreaVo famous_area; //知名区域，如商圈或人们普遍认为有较高知名度的区域

    private TencentMapResultAddressReferenceAreaVo town;    //乡镇街道

    private TencentMapResultAddressReferenceAreaVo landmark_l1; //一级地标，可识别性较强、规模较大的地点、小区等【注】对象结构同 famous_area

    private TencentMapResultAddressReferenceAreaVo landmark_l2; //二级地标，较一级地标更为精确，规模更小【注】：对象结构同 famous_area

    private TencentMapResultAddressReferenceAreaVo street;   //街道 【注】：对象结构同 famous_area

    private TencentMapResultAddressReferenceAreaVo street_number;   //门牌 【注】：对象结构同 famous_area

    private TencentMapResultAddressReferenceAreaVo crossroad;   //交叉路口 【注】：对象结构同 famous_area

    private TencentMapResultAddressReferenceAreaVo water;   //水系 【注】：对象结构同 famous_area

}
