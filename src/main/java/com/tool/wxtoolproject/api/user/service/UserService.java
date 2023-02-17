package com.tool.wxtoolproject.api.user.service;

import com.tool.wxtoolproject.api.common.user.WeChatLoginModel;
import com.tool.wxtoolproject.api.user.entity.WxUser;
import com.tool.wxtoolproject.api.user.vo.MyUserInfoVo;

import java.util.List;

public interface UserService {
    List<WxUser> getUserDetail();

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    MyUserInfoVo getUserById(String userId);

    /**
     * 小程序登录
     *
     * @param model
     * @return
     */
    WxUser weChatLogin(WeChatLoginModel model);

    /**
     * 授权后添加用户信息
     * @param user
     * @return
     */
    int insertUserInfo(WxUser user);
}
