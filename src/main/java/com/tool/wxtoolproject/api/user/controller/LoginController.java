package com.tool.wxtoolproject.api.user.controller;

import com.tool.wxtoolproject.api.common.config.BaseConfig;
import com.tool.wxtoolproject.api.common.error.code.BaseEnumError;
import com.tool.wxtoolproject.api.common.user.WeChatLoginModel;
import com.tool.wxtoolproject.api.common.util.RedisUtil;
import com.tool.wxtoolproject.api.common.vo.ApiResp;
import com.tool.wxtoolproject.api.sys.annotation.Authorize;
import com.tool.wxtoolproject.api.sys.annotation.PassToken;
import com.tool.wxtoolproject.api.user.entity.User;
import com.tool.wxtoolproject.api.user.entity.WxUser;
import com.tool.wxtoolproject.api.user.service.UserService;
import com.tool.wxtoolproject.api.user.vo.MyUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Controller
public class LoginController extends BaseConfig {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;


    @Authorize
    @ResponseBody
    @PassToken
    @GetMapping("/login")
    public ApiResp userLogin(WeChatLoginModel userId) {
        List<WxUser> wxUser = userService.getUserDetail();
        return ApiResp.retOK(wxUser);
    }

    /**
     * 微信小程序登录
     * <p>
     * 登录成功后，将用户身份信息及session_key存入token
     *
     * @param model
     * @return
     */
    @PassToken
    @ResponseBody
    @PostMapping("/weChatLogin")
    public ApiResp weChatLogin(@RequestBody WeChatLoginModel model) throws UnsupportedEncodingException {
        //调用 userService.weChatLogin后台检查openid是否存在，返回openid对应的用户
        WxUser wxUser = userService.weChatLogin(model);
        if (wxUser == null) {
            return ApiResp.retFail(BaseEnumError.login_failure);
        } else {
            User u = new User();
            u.setId(wxUser.getId());
            u.setPassword(wxUser.getOpenId());
            u.setSessionKey(wxUser.getSessionKey());
            String token = BaseController.getToken(u);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("token", token);
            resultMap.put("userId", wxUser.getId());
            return ApiResp.retOK(resultMap);
        }
    }

    /**
     * 授权后补充用户信息
     *
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     */
    @PassToken
    @ResponseBody
    @PostMapping("/insertUserInfo")
    public ApiResp insertUserInfo(@RequestBody WxUser user) throws UnsupportedEncodingException {
        //调用 userService.weChatLogin后台检查openid是否存在，返回openid对应的用户
        MyUserInfoVo wxUser = userService.getUserById(user.getUserId());
        if (wxUser == null) {
            return ApiResp.retFail(BaseEnumError.USER_NOT);
        } else {
            int result = userService.insertUserInfo(user);
            if (result > 0) {
                return ApiResp.retOK();
            }
            return ApiResp.retFail(BaseEnumError.SYS_ERR);
        }
    }


    /**
     * 查询用户我的基本信息
     *
     * @param userId
     * @return
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @GetMapping("/getMyUserInfo")
    public ApiResp getMyUserInfo(@RequestParam String userId) throws UnsupportedEncodingException {
        //调用 userService.weChatLogin后台检查openid是否存在，返回openid对应的用户
        MyUserInfoVo myUserInfoVo = userService.getUserById(userId);
        if (myUserInfoVo == null) {
            return ApiResp.retFail(BaseEnumError.USER_NOT);
        } else {
            myUserInfoVo.setOpenId("");
            return ApiResp.retOK(myUserInfoVo);
        }
    }

}
