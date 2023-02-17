package com.tool.wxtoolproject.api.tiktok.controller;


import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.tiktok.params.TikTokParams;
import com.tool.wxtoolproject.api.tiktok.service.TikTokService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 抖音相关工具接口
 *
 * @author liuhy
 * @date 2021/5/29 18:17
 */

@RestController
@RequestMapping("/tikTool")
public class TikTokController {

    @Autowired
    private TikTokService tikTokService;

    /**
     * 抓取获取抖音无水印视频地址
     *
     * @param tikTokParams
     * @return com.liuhy.tool.wxtoolproject.api.common.vo.Result
     * @author liuhy
     * @date 2021/5/29 19:20
     */
    @RequestMapping(value = "/getTikTokVideoWithoutWatermark", method = RequestMethod.POST)
    public Result getTikTokVideoWithoutWatermark(@RequestBody TikTokParams tikTokParams) throws Exception {
        return tikTokService.getTikTokVideoWithoutWatermark(tikTokParams);
    }

    /**
     * 抓取获取抖音无水印图片
     *
     * @param tikTokParams
     * @return com.liuhy.tool.wxtoolproject.api.common.vo.Result
     * @author liuhy
     * @date 2021/5/29 19:20
     */
    @RequestMapping(value = "/getTikTokImageWithoutWatermark", method = RequestMethod.POST)
    public Result getTikTokImageWithoutWatermark(@RequestBody TikTokParams tikTokParams) throws Exception {
        List<Map<String, Object>> finalVideoAddress = tikTokService.getTikTokImageWithoutWatermark(tikTokParams);
        return Result.ok(finalVideoAddress);
    }
}
