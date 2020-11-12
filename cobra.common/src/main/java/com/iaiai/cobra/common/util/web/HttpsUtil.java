package com.iaiai.cobra.common.util.web;

import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * Package: com.xiaozhi.commons.util
 * Author: iaiai
 * Create Time: 2017/3/22 下午3:42
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public class HttpsUtil extends HttpUtil {

    private HttpsUtil(String proxy_host, int proxy_port, String proxy_username, String proxy_password) {
        super(proxy_host, proxy_port, proxy_username, proxy_password);
    }

    protected CloseableHttpClient getHttpClient() {
        try {
            //采用绕过验证的方式处理https请求
//            SSLContext sslcontext = createIgnoreVerifySSL();

            SSLContext sslcontext = SSLContexts.custom()
                    // //忽略掉对服务器端证书的校验
                    .loadTrustMaterial(new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            return true;
                        }
                    }).build();

            // 设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext))
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            HttpClients.custom().setConnectionManager(connManager);

            CloseableHttpClient httpclient = null;
            if (this.proxy_host != null) {
                HttpHost proxy = new HttpHost(this.proxy_host, this.proxy_port);
                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(
                        proxy);
                httpclient = HttpClients.custom().setConnectionManager(connManager).setRoutePlanner(routePlanner)
                        .build();
            } else {
//                httpclient = HttpClients.createDefault();
                httpclient = HttpClients.custom().setConnectionManager(connManager).build();
            }

            return httpclient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
//        SSLContext sc = SSLContext.getInstance("TLS");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    public static final class Builder {
        private String proxy_host;
        private int proxy_port = 80;
        private String proxy_username;
        private String proxy_password;

        public HttpsUtil build() {
            return new HttpsUtil(proxy_host, proxy_port, proxy_username,
                    proxy_password);
        }

        public Builder setProxy(String host) {
            this.proxy_host = host;
            return this;
        }

        public Builder setProxy(String host, int port) {
            this.proxy_host = host;
            this.proxy_port = port;
            return this;
        }

        public Builder setCredentials(String username, String password) {
            this.proxy_username = username;
            this.proxy_password = password;
            return this;
        }
    }

}
