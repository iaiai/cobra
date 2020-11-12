package com.iaiai.cobra.admin.intercepter.req;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.intercepter.wrapper
 * Author: iaiai
 * Create Time: 2019/12/24 5:27 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description: 包装HttpServletRequest，目的是让其输入流可重复读
 */
@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * 请求体
     */
    private final byte[] mBody;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        // 将body数据存储起来
        mBody = getBody(request);
    }

    /**
     * 获取请求体
     * @param request 请求
     * @return 请求体
     */
    private byte[] getBody(HttpServletRequest request) {
        try {
//            return IOUtils.toString(request.getInputStream(),"utf-8");
            BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求体
     * @return 请求体
     */
    public String getBody() throws UnsupportedEncodingException {
        return new String(mBody,"utf-8");
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 创建字节数组输入流
        final ByteArrayInputStream bais = new ByteArrayInputStream(mBody);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

}
