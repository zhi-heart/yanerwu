package com.yanerwu.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * 利用HttpClient进行post请求的工具类
 */
public abstract class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String sendPost(String url, String params, Map<String, String> headers) {
        List<NameValuePair> pas = new ArrayList<NameValuePair>();
        try {
            String[] paramsArrays = params.split("&");
            for (String str : paramsArrays) {
                String[] s = str.split("=");
                pas.add(new BasicNameValuePair(s[0], s[1]));
            }
        } catch (Exception e) {
            logger.error("post参数解析失败", e);
            throw new RuntimeException("post参数解析失败", e);
        }
        return sendPost(url, pas, headers);
    }

    public static String sendPost(String url, List<NameValuePair> params, Map<String, String> headers) {
        String result = "";
        try {
            CloseableHttpClient httpclient = HttpClientBuilder.create().build();

            HttpPost httppost = new HttpPost(url);
            if (params != null) {
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            }

            if (null != headers) {
                for (String key : headers.keySet()) {
                    httppost.addHeader(key, headers.get(key));
                }
            }

            CloseableHttpResponse response = httpclient.execute(httppost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity, "utf-8"); // 取出应答字符串
            }
            httppost.releaseConnection();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("", e);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            logger.error("", e);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("", e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("", e);
        }
        return result;
    }

    public static String sendPostEntity(String url, String params, Map<String, String> headers, String charset) {
        return sendPostEntity(url, params, headers, charset, null);
    }

    public static String sendPostEntity(String url, String params, Map<String, String> headers, String charset, HttpHost proxy) {
        String result = "";
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            if (null != proxy) {
                httpClientBuilder.setProxy(proxy);
            }

            CloseableHttpClient httpclient = httpClientBuilder.build();

            HttpPost httppost = new HttpPost(url);

            if (params != null) {
                //当做整体post过去
				httppost.setEntity(new StringEntity(params, charset));
            }

            if (null != headers && headers.size() > 0) {
                for (String key : headers.keySet()) {
                    httppost.addHeader(key, headers.get(key));
                }
            }

            CloseableHttpResponse response = httpclient.execute(httppost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity); // 取出应答字符串
            }
            httppost.releaseConnection();
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (ParseException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
        return result;
    }

    public static String doGet(String url) {
        return doGet(url, "utf-8");
    }

    public static String doGet(String url, String charset) {
        return doGet(url, null, null, charset);
    }

    public static String doGet(String url, String host, Integer port, String charset) {
        String result = "";

        // 创建默认的httpClient实例
        CloseableHttpClient httpClient = createSSLClientDefault();
        RequestBuilder requestBuilder = RequestBuilder.get();

        requestBuilder.setUri(url);
        requestBuilder.addHeader("Accept-Charset", charset);
        requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");
        requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0");
        requestBuilder.addHeader("Cache-Control", "no-cache");


        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        //设置代理
        if (StringUtils.isNotBlank(host)) {
//			requestConfigBuilder.setProxy(new HttpHost(host, port));
        }
        //超时时间
        requestConfigBuilder.setConnectionRequestTimeout(1000 * 60);
        requestConfigBuilder.setSocketTimeout(1000 * 60);
        requestConfigBuilder.setConnectTimeout(1000 * 60);
        requestBuilder.setConfig(requestConfigBuilder.build());

        // 用get方法发送http请求
        CloseableHttpResponse httpResponse = null;
        try {
            // 发送get请求
            HttpUriRequest httpUriRequest = requestBuilder.build();

            logger.info(String.format("downloading page %s", url));

            httpResponse = httpClient.execute(httpUriRequest);
            // response实体
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, charset);
            }

            if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {
                logger.error(String.format("download page code:[%s] %s error ", httpResponse.getStatusLine().getStatusCode(), url));
            }
        } catch (Exception e) {
            logger.error(String.format("download page %s error", url), e);
        } finally {
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        return result;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    //https不信任问题
//					.setSSLHostnameVerifier(new CustomizedHostnameVerifier())
                    .build();
        } catch (KeyManagementException e) {
            logger.error("", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        } catch (KeyStoreException e) {
            logger.error("", e);
        }
        return HttpClients.createDefault();
    }

}
