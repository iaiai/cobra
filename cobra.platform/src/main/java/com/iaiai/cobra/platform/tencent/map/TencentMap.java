package com.iaiai.cobra.platform.tencent.map;

import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.util.web.HttpsUtil;
import com.iaiai.cobra.platform.tencent.map.vo.TencentMapVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.platform.tencent.map
 * Author: iaiai
 * Create Time: 2020/6/9 4:41 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
@Component
public class TencentMap {

    @Value("${cobra.keys.tencent-map}")
    public String tencentMapKey; //腾讯地图key

    /**
     * 经纬度反查地址
     * 参考地址：https://lbs.qq.com/service/webService/webServiceGuide/webServiceGcoder
     * @param lat
     * @param lng
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public TencentMapVo locationToAddress(Double lat, Double lng) throws IOException, URISyntaxException {
        String getAddressUrl = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + lat + "," + lng + "&key="+tencentMapKey;
        String json = new HttpsUtil.Builder().build().get(getAddressUrl);
        TencentMapVo mapVo = JsonUtil.getInstance().deserialize(json, TencentMapVo.class);
        return mapVo;
    }

    /**
     * 查询附近的地址
     * 参考地址：https://lbs.qq.com/service/webService/webServiceGuide/webServiceSearch
     * @param lat
     * @param lng
     * @param radius
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public TencentMapVo nearby(Double lat,Double lng,Integer radius,String keyword) throws IOException, URISyntaxException {
        String getAddressUrl = "https://apis.map.qq.com/ws/place/v1/search?boundary=nearby("+lat+","+lng+","+radius+")&keyword="+keyword+"&page_size=20&page_index=1&orderby=_distance&key="+tencentMapKey;
        String json = new HttpsUtil.Builder().build().get(getAddressUrl);
        TencentMapVo vo = JsonUtil.getInstance().deserialize(json,TencentMapVo.class);
        return vo;
    }

    /**
     * 查询附近的地址
     * 参考地址：https://lbs.qq.com/service/webService/webServiceGuide/webServiceSearch
     * @param city
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public TencentMapVo nearby(String city,String keyword) throws IOException, URISyntaxException {
        String getAddressUrl = "https://apis.map.qq.com/ws/place/v1/search?boundary=region("+city+",0)&keyword="+keyword+"&page_size=20&page_index=1&orderby=_distance&key="+tencentMapKey;
        String json = new HttpsUtil.Builder().build().get(getAddressUrl);
        TencentMapVo vo = JsonUtil.getInstance().deserialize(json,TencentMapVo.class);
        return vo;
    }


    /**
     * 根据地址反查经纬度
     * @param address
     * @return
     */
    public String addressToLocation(String address) throws IOException, URISyntaxException {
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?" + "address=" + address + "&key=" + tencentMapKey;
        String json=new HttpsUtil.Builder().build().get(url);
        return  json;
    }

    /**
     * 关键词输入提示
     * @param address
     * @return
     */
    public String suggestion(String address) throws IOException, URISyntaxException {
        String url = "https://apis.map.qq.com/ws/place/v1/suggestion/?" + "keyword=" + address + "&key=" + tencentMapKey;
        String json=new HttpsUtil.Builder().build().get(url);
        return  json;
    }

}
