package com.tool.wxtoolproject.api.imgProcess.controller;


import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.imgProcess.params.BaiduConfig;
import com.tool.wxtoolproject.api.imgProcess.service.OcrRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 百度ocr识别相关接口
 * @author liuhy
 * @date 2021/12/22 16:07
 * @return null
 */
@RestController
@RequestMapping("/ocr")
public class OcrRecognitionController {

    @Autowired
    private OcrRecognitionService ocrRecognitionService;
    @Autowired
    private BaiduConfig baiduConfig;

    /**
     *
     * @author liuhy
     * @date 2021/12/22 16:22
     * @param file 文件流
     * @param request
     * @param type  模板类型 1,健康码  2,疫苗接种记录
     * @return com.chinaunicom.prod.model.Result
     */
    @RequestMapping(value = "/templateInterfaceRecognition", method = RequestMethod.POST)
    public Result templateInterfaceRecognition(@RequestParam("file") MultipartFile file, HttpServletRequest request,@RequestParam("type") String type) throws Exception {
        String templateSign="";
        if ("1".equals(type)){
            templateSign = baiduConfig.getHealthCode();
        }else if("2".equals(type)){
            templateSign = baiduConfig.getVaccine();
        }
        Map<String,Object> resuleMap = ocrRecognitionService.templateInterfaceRecognition(file,templateSign);
        return Result.ok(resuleMap);
    }

    @PostMapping("/recognitionOfAccountingResults")
    public Result recognitionOfAccountingResults(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
        Result result = ocrRecognitionService.recognitionOfAccountingResults(file);
        return result;
    }
}
