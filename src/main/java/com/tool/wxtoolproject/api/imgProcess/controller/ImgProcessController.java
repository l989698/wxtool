package com.tool.wxtoolproject.api.imgProcess.controller;

import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.imgProcess.params.ImgProcessParam;
import com.tool.wxtoolproject.api.imgProcess.service.ImgProcesssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
@RequestMapping("/images")
public class ImgProcessController {
    @Autowired
    private ImgProcesssService imgProcesssService;

    /**
     * 根据文字转换图片
     *
     * @param request
     * @param file
     * @param param
     * @return com.tool.wxtoolproject.api.common.vo.Result
     * @author liuhy
     * @date 2021/6/13 21:17
     */
    @RequestMapping(value = "/getTextImage", method = RequestMethod.POST)
    public Result getTextImage(HttpServletRequest request, MultipartFile file, ImgProcessParam param) throws Exception {
        String imgBase64Str = imgProcesssService.textPicture(file, param);
        return Result.ok(imgBase64Str);
    }
}
