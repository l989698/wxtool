package com.tool.wxtoolproject.api.imgProcess.service;

import com.tool.wxtoolproject.api.common.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface OcrRecognitionService {
    /**
     * 模板识别图片信息
     *
     * @author liuhy
     * @date 2021/12/22 16:34
     * @param file
     * @param templateSign
     * @return java.util.Map
     */
    Map templateInterfaceRecognition(MultipartFile file, String templateSign) throws Exception;

    Result recognitionOfAccountingResults(MultipartFile file) throws Exception;
}
