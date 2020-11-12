package com.iaiai.cobra.common.util.web;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1.
 */
public class HttpUtil {
    String proxy_host;
    int proxy_port;
    String proxy_username;
    String proxy_password;

    public String resultEncode = "utf-8";

    protected HttpUtil() {
        this(null, -1, null, null);
    }

    protected HttpUtil(String proxy_host, int proxy_port, String proxy_username, String proxy_password) {
        this.proxy_host = proxy_host;
        this.proxy_port = proxy_port;
        this.proxy_username = proxy_username;
        this.proxy_password = proxy_password;
    }

    public HttpUtil setResultEncode(String resultEncode){
        this.resultEncode = resultEncode;
        return this;
    }

    protected CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpclient = null;
        if (this.proxy_host != null) {
            HttpHost proxy = new HttpHost(this.proxy_host, this.proxy_port);
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            httpclient = HttpClients.custom().setRoutePlanner(routePlanner).build();
        } else {
            httpclient = HttpClients.createDefault();
        }
        return httpclient;
    }

    /**
     * @param uri
     * @param params
     * @param headers
     * @return
     * @throws IOException
     */
    public String post(String uri, Map<String, String> params,
                       Map<String, String> headers) throws IOException {
        CloseableHttpResponse response = post(uri, params, headers, "UTF-8");
        try {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,resultEncode).trim();
        } finally {
            response.close();
        }
    }

    /**
     * @param uri
     * @param params
     * @param headers
     * @param encode
     * @return
     * @throws IOException
     */
    public CloseableHttpResponse post(String uri, Map<String, String> params,
                                      Map<String, String> headers, String encode) throws IOException {
        CloseableHttpClient httpclient = getHttpClient();
        HttpPost httpPost = new HttpPost(uri);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null) {
            for (String key : params.keySet()) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
        }

        if (headers != null) {
            for (String h : headers.keySet()) {
                httpPost.setHeader(h, headers.get(h));
            }
        }

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encode));
        return httpclient.execute(httpPost);
    }

    /**
     * @param uri
     * @param requestBody
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String post(String uri, String requestBody) throws IOException,
            URISyntaxException {
        return post(uri, requestBody, null, null);
    }

    /**
     * @param uri
     * @param body
     * @param params
     * @param headers
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String post(String uri, String body, Map<String, String> params,
                       Map<String, String> headers) throws IOException, URISyntaxException {
        CloseableHttpResponse response = post(uri, body, params, headers,
                "UTF-8");
        try {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,resultEncode).trim();
        } finally {
            response.close();
        }

    }

    /**
     * @param uri
     * @param requestBody
     * @param encode
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String post(String uri, String requestBody, String encode)
            throws IOException, URISyntaxException {
        CloseableHttpResponse response = post(uri, requestBody, null, null,
                encode);
        try {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,resultEncode).trim();
        } finally {
            response.close();
        }
    }

    /**
     * @param uri
     * @param body
     * @param params
     * @param headers
     * @param encode
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public CloseableHttpResponse post(String uri, String body,
                                      Map<String, String> params, Map<String, String> headers,
                                      String encode) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = getHttpClient();

        URIBuilder ub = new URIBuilder(uri);
        if (params != null) {
            for (String k : params.keySet()) {
                ub.setParameter(k, params.get(k));
            }
        }

        HttpPost httpPost = new HttpPost(ub.build());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);

        if (headers != null) {
            for (String h : headers.keySet()) {
                httpPost.setHeader(h, headers.get(h));
            }
        }

        HttpEntity reqEntity = new StringEntity(body, encode);

        httpPost.setEntity(reqEntity);
        return httpclient.execute(httpPost);
    }

    public CloseableHttpResponse postMultipart(String uri,
                                               String fileName,
                                               File[] files,
                                               Map<String, String> params,
                                               Map<String, String> headers,
                                               String encode) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = getHttpClient();

        HttpPost httpPost = new HttpPost(uri);
        if (headers != null) {
            for (String h : headers.keySet()) {
                httpPost.setHeader(h, headers.get(h));
            }
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if(files!=null) {
            for (File file:files) {
                builder.addPart(fileName, new FileBody(file));
            }
        }
        if (params != null) {
            for (String k : params.keySet()) {
                builder.addPart(k, new StringBody(params.get(k), ContentType.create("text/plain", encode)));
            }
        }

        httpPost.setEntity(builder.build());
        return httpclient.execute(httpPost);
    }

    /**
     * @param uri
     * @param requestBody
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String put(String uri, String requestBody) throws IOException,
            URISyntaxException {
        return put(uri, requestBody, null);
    }

    /**
     * @param uri
     * @param requestBody
     * @param headers
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String put(String uri, String requestBody,
                      Map<String, String> headers) throws IOException, URISyntaxException {
        return put(uri, requestBody, null, headers);
    }

    /**
     * @param uri
     * @param requestBody
     * @param params
     * @param headers
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String put(String uri, String requestBody,
                      Map<String, String> params, Map<String, String> headers)
            throws IOException, URISyntaxException {
        CloseableHttpResponse response = put(uri, requestBody, params, headers,
                "UTF-8");

        try {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,resultEncode).trim();
        } finally {
            response.close();
        }
    }

    /**
     * @param uri
     * @param requestBody
     * @param params
     * @param headers
     * @param encode
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public CloseableHttpResponse put(String uri, String requestBody,
                                     Map<String, String> params, Map<String, String> headers,
                                     String encode) throws IOException, URISyntaxException {

        CloseableHttpClient httpclient = getHttpClient();

        URIBuilder ub = new URIBuilder(uri);
        if (params != null) {
            for (String k : params.keySet()) {
                ub.setParameter(k, params.get(k));
            }
        }

        HttpPut httpPut = new HttpPut(ub.build());
        if (headers != null) {
            for (String h : headers.keySet()) {
                httpPut.setHeader(h, headers.get(h));
            }
        }

        HttpEntity reqEntity = new StringEntity(requestBody, encode);
        httpPut.setEntity(reqEntity);
        return httpclient.execute(httpPut);
    }

    /**
     * @param uri
     * @param inputStream
     * @param params
     * @param headers
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public CloseableHttpResponse put(String uri, InputStream inputStream,
                                     Map<String, String> params, Map<String, String> headers)
            throws IOException, URISyntaxException {

        CloseableHttpClient httpclient = getHttpClient();

        URIBuilder ub = new URIBuilder(uri);
        if (params != null) {
            for (String k : params.keySet()) {
                ub.setParameter(k, params.get(k));
            }
        }

        HttpPut httpPut = new HttpPut(ub.build());

        if (headers != null) {
            for (String h : headers.keySet()) {
                httpPut.setHeader(h, headers.get(h));
            }
        }

        HttpEntity reqEntity = new InputStreamEntity(inputStream);
        httpPut.setEntity(reqEntity);
        return httpclient.execute(httpPut);
    }

    /**
     * @param uri
     * @param params
     * @param headers
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public CloseableHttpResponse put(String uri, Map<String, String> params,
                                     Map<String, String> headers) throws IOException, URISyntaxException {

        CloseableHttpClient httpclient = getHttpClient();

        URIBuilder ub = new URIBuilder(uri);
        if (params != null) {
            for (String k : params.keySet()) {
                ub.setParameter(k, params.get(k));
            }
        }

        HttpPut httpPut = new HttpPut(ub.build());

        if (headers != null) {
            for (String h : headers.keySet()) {
                httpPut.setHeader(h, headers.get(h));
            }
        }
        return httpclient.execute(httpPut);
    }

    /**
     * @param uri
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String get(String uri) throws IOException, URISyntaxException {
        return get(uri, null, null, "UTF-8");
    }

    /**
     * @param uri
     * @param encode
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String get(String uri, String encode) throws IOException,
            URISyntaxException {
        return get(uri, null, null, encode);
    }

    /**
     * @param uri
     * @param params
     * @param headers
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String get(String uri, Map<String, String> params,
                      Map<String, String> headers) throws IOException, URISyntaxException {
        return get(uri, params, headers, "UTF-8");
    }

    /**
     * @param uri
     * @param params
     * @param headers
     * @param encode
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String get(String uri,
                      Map<String, String> params,
                      Map<String, String> headers,
                      String encode) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = getHttpClient();

        URIBuilder ub = new URIBuilder(uri);
        if (params != null) {
            for (String k : params.keySet()) {
                ub.setParameter(k, params.get(k));
            }
        }

        HttpGet httpget = new HttpGet(ub.build());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();//设置请求和传输超时时间
        httpget.setConfig(requestConfig);

        if (headers != null) {
            for (String h : headers.keySet()) {
                httpget.setHeader(h, headers.get(h));
            }
        }

        CloseableHttpResponse response = httpclient.execute(httpget);

        try {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,resultEncode).trim();
        } finally {
            response.close();
        }

    }

    public InputStream download(String uri) throws IOException,
            URISyntaxException {
        HttpEntity entity = download(uri, null, null);
        if (entity == null || entity.getContentLength() == 0) {
            return null;
        }
        return entity.getContent();
    }

    public HttpEntity download(String uri, Map<String, String> params,
                               Map<String, String> headers) throws IOException,
            URISyntaxException {

        CloseableHttpClient httpclient = getHttpClient();

        URIBuilder ub = new URIBuilder(uri);
        if (params != null) {
            for (String k : params.keySet()) {
                ub.setParameter(k, params.get(k));
            }
        }

        HttpGet httpget = new HttpGet(ub.build());

        if (headers != null) {
            for (String h : headers.keySet()) {
                httpget.setHeader(h, headers.get(h));
            }
        }

        CloseableHttpResponse response = httpclient.execute(httpget);

        HttpEntity entity = response.getEntity();
        return entity;
    }

    public String getFileName(String urlStr) throws MalformedURLException {
        URL url = new URL(urlStr);
        String path = url.getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static final class Builder {
        private String proxy_host;
        private int proxy_port = 80;
        private String proxy_username;
        private String proxy_password;

        public HttpUtil build() {
            return new HttpUtil(proxy_host, proxy_port, proxy_username,
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
