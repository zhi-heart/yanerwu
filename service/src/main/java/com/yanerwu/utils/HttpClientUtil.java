package com.yanerwu.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/*
 * 利用HttpClient进行post请求的工具类
 */
public abstract class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String sendPost(String url, String params) {
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
		return sendPost(url, pas);
	}

	public static String sendPost(String url, List<NameValuePair> params) {
		String result = "";
		try {
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();

			HttpPost httppost = new HttpPost(url);
			if (params != null) {
				httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			}

			CloseableHttpResponse response = httpclient.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				HttpEntity httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity); // 取出应答字符串
			}
			httppost.releaseConnection();
		} catch (UnsupportedEncodingException e) {
			logger.error("",e);
		} catch (ClientProtocolException e) {
			logger.error("",e);
		} catch (ParseException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
		return result;
	}

    public static String doGet(String url){
        return doGet(url,"utf-8");
    }

	public static String doGet(String url,String charset){
		return doGet(url,null,null,charset);
	}

	public static String doGet(String url,String host,Integer port,String charset) {
		String result="";

		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = createSSLClientDefault();
		RequestBuilder requestBuilder= RequestBuilder.get();

		requestBuilder.setUri(url);
		requestBuilder.addHeader("Accept-Charset", charset);
		requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");
		requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0");
		requestBuilder.addHeader("Cache-Control", "no-cache");


		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		//设置代理
		if(StringUtils.isNotBlank(host)){
//			requestConfigBuilder.setProxy(new HttpHost(host, port));
		}
		//超时时间
		requestConfigBuilder.setConnectionRequestTimeout(1000*60);
		requestConfigBuilder.setSocketTimeout(1000*60);
		requestConfigBuilder.setConnectTimeout(1000*60);
		requestBuilder.setConfig(requestConfigBuilder.build());

		// 用get方法发送http请求
		CloseableHttpResponse httpResponse = null;
		try {
			// 发送get请求
			HttpUriRequest httpUriRequest = requestBuilder.build();

			logger.info(String.format("downloading page %s", url));

			httpResponse=httpClient.execute(httpUriRequest);
			// response实体
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				result= EntityUtils.toString(entity,charset);
			}

			if(HttpStatus.SC_OK!=httpResponse.getStatusLine().getStatusCode()){
				logger.error(String.format("download page code:[%s] %s error ", httpResponse.getStatusLine().getStatusCode(),url));
			}
		} catch (Exception e) {
			logger.error(String.format("download page %s error", url),e);
		} finally {
			try {
				httpResponse.close();
				httpClient.close();
			} catch (IOException e) {
				logger.error("",e);
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
			logger.error("",e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("",e);
		} catch (KeyStoreException e) {
			logger.error("",e);
		}
		return HttpClients.createDefault();
	}

	public static String doGetWdzj(String url) throws Exception {
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36";
		httpURLConnection.setRequestProperty("User-Agent", userAgent);
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}
		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
				resultBuffer.append("\r\n");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}
}
