package com.tool.wxtoolproject.api.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.tool.wxtoolproject.api.common.constant.RedisConstant;
import com.tool.wxtoolproject.api.common.util.OkHttpUtils;
import com.tool.wxtoolproject.api.common.util.RedisUtil;
import com.tool.wxtoolproject.api.common.util.date.DateTools;
import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.user.service.WordOfTheDayService;
import com.tool.wxtoolproject.api.user.vo.WordOfTheDayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordOfTheDayServiceImpl implements WordOfTheDayService {
    @Autowired
    private OkHttpUtils okHttpUtils;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public Result getWordOfTheDay() {
        JSONObject resultObject = redisUtil.get(RedisConstant.WORD_OF_THE_DAY);
        //验证内存不为空
        if (ObjectUtil.isNotNull(resultObject)) {
            String dateStr = DateTools.getDateStr("yyyy-MM-dd");
            //不是当天信息则重新请求获取
            if (!resultObject.getString("dateline").equals(dateStr)){
                resultObject = getJsonStr();
            }
        }else {
            resultObject = getJsonStr();
        }
        List<WordOfTheDayVO> resultList = new ArrayList<>();
        WordOfTheDayVO vo = new WordOfTheDayVO();
        vo.setImage(resultObject.getString("picture2"));
        vo.setTitle(resultObject.getString("note"));
        vo.setEnglishtitle(resultObject.getString("content"));
        resultList.add(vo);
        return Result.ok(resultList);
    }

    //获取每日一言JSON信息
    private JSONObject getJsonStr(){
        final String url = "http://open.iciba.com/dsapi/";
        String jsonStr = okHttpUtils.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        redisUtil.set(RedisConstant.WORD_OF_THE_DAY,jsonObject);
        return jsonObject;
    }
}
