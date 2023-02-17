package com.tool.wxtoolproject.api.imgProcess.params;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 百度相关配置
 *
 * @author liuhy
 * @date 2021/12/23 15:14
 */
@Data
@Component
public class BaiduConfig {

    /**
     * 获取配置值
     */
    //健康码模板id
    @Value("${baidu.ocr-api.template.healthCode}")
    private  String healthCode;
    //疫苗模板id
    @Value("${baidu.ocr-api.template.vaccine}")
    private  String vaccine;
    //获token接口
    @Value("${baidu.ocr-api.urls.getTokenApiUrl}")
    private  String getTokenApiUrl;
    //iocr识别接口
    @Value("${baidu.ocr-api.urls.getOcrApiUrl}")
    private  String getOcrApiUrl;

    private String getUniversalTextUrl;
    //ak - 百度云官网获取的 API Key
    @Value("${baidu.ocr-api.application.ak}")
    private  String ak;
    //sk - 百度云官网获取的 Secret Key
    @Value("${baidu.ocr-api.application.sk}")
    private  String sk;

}
