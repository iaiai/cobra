package com.iaiai.cobra.repository.pojo;

import com.iaiai.cobra.repository.beans.Menu;
import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.repository.pojo
 * Author: iaiai
 * Create Time: 2019/12/11 9:13 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Data
public class MenuPojo extends Menu {

    private List<MenuPojo> children;

}
