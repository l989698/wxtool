package com.tool.wxtoolproject.api.imgProcess.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.tool.wxtoolproject.api.common.util.BaiduHttpUtil;
import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.imgProcess.params.BaiduConfig;
import com.tool.wxtoolproject.api.imgProcess.service.OcrRecognitionService;
import lombok.extern.slf4j.Slf4j;


import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OcrRecognitionServiceImpl implements OcrRecognitionService {


    @Autowired
    private BaiduConfig baiduConfig;


    /**
     * 根据模板识别图片信息
     *
     * @param file 图片文件
     * @param templateSign 模板编码
     * @return java.util.Map
     * @author liuhy
     * @date 2021/12/22 16:34
     */
    @Override
    public Map templateInterfaceRecognition(MultipartFile file, String templateSign) throws Exception {
        try {
            // iocr识别apiUrl
            String recogniseUrl = baiduConfig.getGetOcrApiUrl();
            //请求百度api获取识别信息
            JSONObject jsonObject = requestBaiduApi(recogniseUrl, file, templateSign);
            //查询正常 验证状态=0 则解析
            if (jsonObject.getString("error_code").equals("0")) {
                //处理返回结果
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray ret = data.getJSONArray("ret");
                List<Map> list = ret.toJavaList(Map.class);
                Map<String, String> resultMap = new HashMap<>();
                for (Map map : list) {
                    resultMap.put(map.get("word_name").toString(), map.get("word").toString());
                }
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap();
    }

    @Override
    public Result recognitionOfAccountingResults(MultipartFile file) throws Exception {
        // iocr识别apiUrl
        String recogniseUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        //请求百度api获取识别信息
        JSONObject jsonObject = requestBaiduApi(recogniseUrl, file, "");
        JSONArray words_result = jsonObject.getJSONArray("words_result");
        Map<String, String> resultMap = new HashMap<>();
        for (int i = 0; i < words_result.size(); i++) {
            String words = words_result.getJSONObject(i).getString("words");
            if (null != words && words.contains("姓名")){
                i +=1;
                resultMap.put("name",words_result.getJSONObject(i).getString("words"));
            }
        }
        return Result.ok(resultMap);
    }

    /**
     * 请求百度api ocr 通用
     *
     * @author liuhy
     * @date 2021/12/23 16:13
     * @param recogniseUrl 接口地址
     * @param file 图片文件
     * @param templateSign 模板编号(可空)
     * @return com.alibaba.fastjson.JSONObject
     */
    public JSONObject requestBaiduApi(String recogniseUrl, MultipartFile file, String templateSign) throws Exception {
        try {
            //转成base64编码
            String image = multipartFileToBASE64(file);
            // 请求模板参数
            StringBuffer recogniseParams = new StringBuffer();
            recogniseParams.append("image=");
            recogniseParams.append(image);
            // todo 迁移完代码优化成 StringUtils.isNotEmpty()
            if (null != templateSign && !"".equals(templateSign)) {
                recogniseParams.append("&templateSign=");
                recogniseParams.append(templateSign);
            }
            //获取access_token
            String accessToken = getAccessToken();
            //请求获取信息
            String result = BaiduHttpUtil.post(recogniseUrl, accessToken, recogniseParams.toString());
            //解析返回值
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MultipartFile 转 base64
     *
     * @param mFile
     * @return java.lang.String
     * @author liuhy
     * @date 2021/12/22 16:36
     */
    public String multipartFileToBASE64(MultipartFile mFile) throws Exception {
        BASE64Encoder bEncoder = new BASE64Encoder();
        String base64EncoderImg = bEncoder.encode(mFile.getBytes()).replaceAll("[\\s*\t\n\r]", "");
        return URLEncoder.encode(base64EncoderImg, "UTF-8");
    }


    /**
     * 获取API访问token 有效期30天
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public String getAccessToken() {
        // 获取token地址
        String authHost = baiduConfig.getGetTokenApiUrl();
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + baiduConfig.getAk()
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + baiduConfig.getSk();
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
}
