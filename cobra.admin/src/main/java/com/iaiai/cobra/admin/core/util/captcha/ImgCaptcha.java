package com.iaiai.cobra.admin.core.util.captcha;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import static com.iaiai.cobra.admin.core.util.captcha.Randoms.alpha;
import static com.iaiai.cobra.admin.core.util.captcha.Randoms.num;

/**
 * Created with IntelliJ IDEA.
 * Package:  com.iaiai.cobra.admin.core.util.captcha
 * Author: iaiai
 * Create Time: 2020/7/24 10:51 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
public abstract class ImgCaptcha {

    protected Font font = new Font("Verdana", Font.ITALIC|Font.BOLD, 28);   // 字体
    protected int len = 5;  // 验证码随机字符长度
    protected int width = 150;  // 验证码显示跨度
    protected int height = 40;  // 验证码显示高度
    private String chars = null;  // 随机字符串

    /**
     * 生成随机字符数组
     * @return 字符数组
     */
    public char[] alphas() {
        char[] cs = new char[len];
        for(int i = 0;i<len;i++) {
            cs[i] = alpha();
        }
        chars = new String(cs);
        return cs;
    }
    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 给定范围获得随机颜色
     * @return Color 随机颜色
     */
    protected Color color(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + num(bc - fc);
        int g = fc + num(bc - fc);
        int b = fc + num(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 验证码输出,抽象方法，由子类实现
     * @param os 输出流
     */
    public abstract void out(OutputStream os);

    /**
     * 获取随机字符串
     * @return string
     */
    public String text() {
        return chars;
    }

    public static void main(String[] args) throws FileNotFoundException {
        GifCaptcha captcha = new GifCaptcha(150,40,9);//   gif格式动画验证码
        captcha.out(new FileOutputStream("/Users/iaiai/Downloads/1.gif"));

        String str = captcha.text();
        String code = "";
        String postion = "";

        //4-9的随机数
        int num = new Random().nextInt(3)+4;
        for(int i=0;i<num;i++) {
            //获取字符串位置，可以重复
            int random = (int) (Math.random() * 9 + 1);
            postion += random+"";
            code += str.substring(random-1,random);
        }
        log.debug("....str: {}",str);
        log.debug("....postion: {}",postion);
        log.debug("....code: {}",code);
    }

}
