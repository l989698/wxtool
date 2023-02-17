package com.tool.wxtoolproject.api.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tool.wxtoolproject.api.common.config.WeChatConfig;
import com.tool.wxtoolproject.api.common.user.WeChatLoginModel;
import com.tool.wxtoolproject.api.common.util.HttpClientUtils;
import com.tool.wxtoolproject.api.user.dao.UserMapper;
import com.tool.wxtoolproject.api.user.entity.WxUser;
import com.tool.wxtoolproject.api.user.service.UserService;
import com.tool.wxtoolproject.api.user.vo.MyUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatConfig weChatConfig;

    @Override
    public List<WxUser> getUserDetail() {
        List<WxUser> wxUser = userMapper.query();
        return wxUser;
    }

    @Override
    public MyUserInfoVo getUserById(String userId) {
        MyUserInfoVo wxUser = userMapper.getUserById(userId);
        if (null != wxUser) {
            return wxUser;
        }
        return new MyUserInfoVo();
    }

    /**
     * 小程序登录
     *
     * @param model
     * @return
     */
    @Override
    public WxUser weChatLogin(WeChatLoginModel model) {
//        WeChatLoginResult<UserAccount> result = null;
        try {
            //根据code获取 openid等
            JSONObject object = getSessionKeyOrOpenId(model.getCode());
            if (!object.isEmpty()) {
                //获取会话密钥（session_key）
                String session_key = object.get("session_key").toString();
                //用户的唯一标识（openid）
                String openid = (String) object.get("openid");

                // 去数据库 检查 openId 是否存在 不存在就新建用户
                WxUser user = userMapper.wechatOpenIdIsExists(openid);
                if (null == user || null == user.getId()) {
                    // 不存在，就是第一次登录：新建用户信息
                    user = new WxUser();
                    user.setId(UUID.randomUUID().toString());
                    user.setOpenId(openid);
                    userMapper.insertUser(user);
                } else {
                    //如果存在，就不是第一次登录，更新最后登录时间
                    user.setCtime(new Date().toString());
                    userMapper.updateByUserOpenId(user);
                }
                user.setSessionKey(session_key);
                return user;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new WxUser();
    }


    @Override
    public int insertUserInfo(WxUser user) {
        int result = userMapper.updateByUserId(user);
        return result;
    }

    /**
     * 请求微信接口获取openid
     *
     * @param code
     * @return
     * @throws Exception
     */
    private JSONObject getSessionKeyOrOpenId(String code) throws Exception {
        Map<String, String> requestUrlParam = new HashMap<>();
        // 小程序appId，自己补充
        requestUrlParam.put("appid", weChatConfig.getAppId());
        // 小程序secret，自己补充
        requestUrlParam.put("secret", weChatConfig.getSecret());
        // 小程序端返回的code
        requestUrlParam.put("js_code", code);
        // 默认参数
        requestUrlParam.put("grant_type", weChatConfig.getGrantType());

        // 发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpClientUtils.doPost(weChatConfig.getSessionHost(), requestUrlParam);
        return JSON.parseObject(result);
    }


}
