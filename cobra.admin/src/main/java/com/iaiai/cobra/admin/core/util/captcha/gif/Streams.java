package com.iaiai.cobra.admin.core.util.captcha.gif;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.core.util.captcha.gif
 * Author: iaiai
 * Create Time: 2020/7/24 11:10 上午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public class Streams {

    /**
     * 关闭输入流
     * @param in 输入流
     */
    public static void close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ioex) {
                // ignore
            }
        }
    }

    /**
     * 关闭输出流
     * @param out 输出流
     */
    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.flush();
            } catch (IOException ioex) {
                // ignore
            }
            try {
                out.close();
            } catch (IOException ioex) {
                // ignore
            }
        }
    }

}
