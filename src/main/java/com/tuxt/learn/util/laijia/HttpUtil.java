package com.tuxt.learn.util.laijia;

import com.tuxt.learn.core.entity.MapBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * http工具类
 * Created by wbq on 2016/10/14.
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    protected static String addParamsToUrl(String url, MapBean params) {
        if (url.indexOf("?") < 0)
            url += "?";
        List<NameValuePair> names = new LinkedList<>();
        if (params != null && params.size() > 0) {
            names.addAll(params.keySet().stream().map(name -> new BasicNameValuePair(name, params.getString(name))).collect(Collectors.toList()));
        }
        String paramString = URLEncodedUtils.format(names, "utf-8");
        url += paramString;
        return url;
    }

    public static String common(HttpUriRequest request) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringBuffer log = new StringBuffer();
        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        String responseBody;
        try {
            responseBody = httpclient.execute(request, responseHandler);
            log.append("result: " + responseBody);
            logger.debug(log.toString() + "\r\n");
            logger.debug("----------------------------------------\r\n");
            return responseBody;
        } catch (IOException e) {
            logger.error("request url error", e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String get(String url, MapBean params) {
        StringBuffer log = new StringBuffer();
        HttpGet get = new HttpGet(addParamsToUrl(url, params));
        log.append("request: " + get.getRequestLine() + "\r\n");
        return common(get);
    }

    public static String delete(String url, MapBean params) {
        HttpDelete httpDelete = new HttpDelete(addParamsToUrl(url, params));
        return common(httpDelete);
    }


    public static String put(String url, MapBean params, String body) {
        HttpPut put = new HttpPut(addParamsToUrl(url, params));
        put.setEntity(new StringEntity(body, "UTF-8"));
        put.setHeader("Content-type", "application/json; charset=utf-8");
        return common(put);
    }


    public static String getWithLog(String url, MapBean params, StringBuffer log) {
        HttpGet get = new HttpGet(addParamsToUrl(url, params));
        if (log != null) {
            log.append("request:" + get.getRequestLine() + "\r\n");
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(50000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(50000).build();
        get.setConfig(requestConfig);

        logger.debug("Executing request " + get.getRequestLine());
        return common(get);
    }

    public static <T> T get(String url, MapBean params, Class<T> clazz) {
        String v = get(url, params);
        if (StringUtils.isNotBlank(v)) {
            return JsonUtil.toObject(v, clazz);
        }
        return null;
    }

    public static <T> T getWithLog(String url, MapBean params, Class<T> clazz, StringBuffer log) {
        String v = getWithLog(url, params, log);
        if (StringUtils.isNotBlank(v)) {
            return JsonUtil.toObject(v, clazz);
        }
        return null;
    }


    public static String postWithJson(String url, String body) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(body, "UTF-8"));
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        return common(httpPost);
    }

    public static String post(String url, byte[] bytes, String contentType) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new ByteArrayEntity(bytes));
        if (contentType != null) {
            httpPost.setHeader("Content-type", contentType);
        }
        return common(httpPost);
    }

    public static String postWithJson(String url, String body, String token) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(body, "UTF-8"));
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Authorization", "Bearer " + token);
        return common(httpPost);
    }

    public static String post(String url, byte[] bytes) throws IOException {
        return post(url, bytes, null);
    }

    public static String get(String url, MapBean params,
                             MapBean headers, String contentType) throws IOException {
        String allUrl = url;
        if (params != null && params.size() > 0) {
            if (allUrl.indexOf("?") < 0) {
                allUrl += "?";
            }
            for (String s : params.keySet()) {
                allUrl += "&" + s + "=" + params.getString(s);
            }
        }
        HttpGet httpPost = new HttpGet(allUrl);
        if (headers != null && headers.size() > 0) {
            for (String s : headers.keySet()) {
                httpPost.addHeader(s, headers.getString(s));
            }
        }
        if (contentType != null) {
            httpPost.setHeader("Content-type", contentType);
        }
        return common(httpPost);
    }

    public static String post(String url, MapBean params,
                              MapBean headers, String contentType) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && headers.size() > 0) {
            for (String s : headers.keySet()) {
                httpPost.addHeader(s, headers.getString(s));
            }
        }
        if (params != null && params.size() > 0) {
            List<NameValuePair> pairList = params.keySet().stream().map(s -> new BasicNameValuePair(s, params.getString(s))).collect(Collectors.toCollection(LinkedList::new));
            httpPost.setEntity(new UrlEncodedFormEntity(pairList));
        }
        if (contentType != null) {
            httpPost.setHeader("Content-type", contentType);
        }
        return common(httpPost);
    }
}
