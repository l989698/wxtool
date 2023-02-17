package com.tool.wxtoolproject.api.tiktok.service;

import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.tiktok.params.TikTokParams;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TikTokService {

    /**
     * 获取抖音无水印视频
     *
     * @param tikTokParams
     * @return java.lang.String
     * @author liuhy
     * @date 2021/5/29 20:04
     */
    Result getTikTokVideoWithoutWatermark(TikTokParams tikTokParams) throws Exception;

    /**
     * 获取抖音无水印图片
     *
     * @param tikTokParams
     * @return
     * @throws IOException
     */
    List<Map<String, Object>> getTikTokImageWithoutWatermark(TikTokParams tikTokParams) throws IOException;
}
