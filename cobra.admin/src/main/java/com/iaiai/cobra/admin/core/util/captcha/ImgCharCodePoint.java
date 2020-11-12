package com.iaiai.cobra.admin.core.util.captcha;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Package: com.xproject.xcommons.captcha
 * Author: iaiai
 * Create Time: 2018/12/19 9:36 AM
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public class ImgCharCodePoint implements Serializable {

    private int x, y, fontSize;
    private Boolean status; //状态，是否验证

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

}
