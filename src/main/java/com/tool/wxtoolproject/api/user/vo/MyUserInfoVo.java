package com.tool.wxtoolproject.api.user.vo;

import lombok.Data;

@Data
public class MyUserInfoVo {

    private String openId;
    //用户昵称
    private String nickName;
    //用户头像
    private String avatarUrl;
    //用户性别 0 男 1女
    private int gender;
    //所在国家
    private String country;
    //省份
    private String province;
    //城市
    private String city;
    //语种
    private String language;
    //创建时间
    private String ctime;
    //手机号
    private String mobile;

}
