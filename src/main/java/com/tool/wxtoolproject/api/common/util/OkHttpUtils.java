package com.tool.wxtoolproject.api.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Slf4j
@Component
public class OkHttpUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    @Autowired
    private OkHttpClient okHttpClient;
    /**
     * get 请求
     * @param url       请求url地址
     * @return string
     * */
    public String doGet(String url) {
        return doGet(url, null, null);
    }


    /**
     * get 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     * */
    public String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

    /**
     * get 请求
     * @param <T>
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     * */
    public <T> T doGetToObject(String url, Map<String, String> params, Class<T> clazz) {
        String result = doGet(url, params, null);
        return JSONObject.parseObject(result, clazz);
    }

    /**
     * get 请求
     * @param url       请求url地址
     * @param headers   请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public String doGet(String url, String[] headers) {
        return doGet(url, null, headers);
    }


    /**
     * get 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @param headers   请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public String doGet(String url, Map<String, String> params, String[] headers) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }

        Request.Builder builder = new Request.Builder().addHeader("Connection", "close");
        if (headers != null && headers.length > 0) {
            if (headers.length % 2 == 0) {
                for (int i = 0; i < headers.length; i = i + 2) {
                    builder.addHeader(headers[i], headers[i + 1]);
                }
            } else {
                log.warn("headers's length[{}] is error.", headers.length);
            }

        }

        Request request = builder.url(sb.toString()).build();
        log.info("do get request and url[{}]", sb.toString());
        return execute(request);
    }

    /**
     * post 请求
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     */
    public String doPost(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder().addHeader("Connection", "close").url(url).post(builder.build()).build();
        log.info("do post request and url[{}]", url);

        return execute(request);
    }

    /**
     * post 请求
     * @param <T>
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     */
    public <T> T doPost(String url, Map<String, String> params, Map<String, String> header, Class<T> clazz) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Builder requestBuilder = new Request.Builder().addHeader("Connection", "close").url(url);
        if(MapUtils.isNotEmpty(header)) {
            header.forEach((k, v) -> requestBuilder.addHeader(k, v));
        }
        Request request = requestBuilder.post(builder.build()).build();
        log.info("do post request and url[{}]", url);
        String result = execute(request);
        return JSONObject.parseObject(result, clazz);
    }


    /**
     * post 请求
     * @param <T>
     * @param url       请求url地址
     * @param params    请求参数 map
     * @return string
     */
    public <T> T doPost(String url, Map<String, String> params, Class<T> clazz) {
        return doPost(url, params, null, clazz);
    }



    /**
     * post 请求, 请求数据为 json 的字符串
     * @param url       请求url地址
     * @param json      请求数据, json 字符串
     * @return string
     */
    public String doPostJson(String url, String json) {
        log.info("do post request and url[{}]", url);
        return exectePost(url, json, JSON);
    }
    public <T> T doPostJsonHeader(String url, String json,Map<String,String> header, Class<T> clazz) {
        log.info("do post request and url[{}]", url);
        return exectePostHeader(url, json,header,clazz, JSON);
    }
    /**
     * post 请求, 请求数据为 xml 的字符串
     * @param url       请求url地址
     * @param xml       请求数据, xml 字符串
     * @return string
     */
    public String doPostXml(String url, String xml) {
        log.info("do post request and url[{}]", url);
        return exectePost(url, xml, XML);
    }

    //@SuppressWarnings("deprecation")
    private <T> T exectePostHeader(String url, String data,Map<String,String> header, Class<T> clazz, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request.Builder builder = new Request.Builder();
        builder.addHeader("Connection", "close");
        for(String key:header.keySet()){
            builder.addHeader(key,header.get(key));
        }
        Request request = builder.url(url).post(requestBody).build();
        String result = execute(request);
        return JSONObject.parseObject(result, clazz);
    }

    @SuppressWarnings("deprecation")
    private String exectePost(String url, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request request = new Request.Builder().addHeader("Connection", "close").url(url).post(requestBody).build();

        return execute(request);
    }

    private String execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }
}