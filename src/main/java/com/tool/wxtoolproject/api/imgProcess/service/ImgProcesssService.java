package com.tool.wxtoolproject.api.imgProcess.service;

import com.tool.wxtoolproject.api.imgProcess.params.ImgProcessParam;
import org.springframework.web.multipart.MultipartFile;

public interface ImgProcesssService {

     String textPicture(MultipartFile file, ImgProcessParam param);

}
