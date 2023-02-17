package com.tool.wxtoolproject.api.user.dao;

import com.tool.wxtoolproject.api.user.entity.WxUser;
import com.tool.wxtoolproject.api.user.vo.MyUserInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<WxUser> query();

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    MyUserInfoVo getUserById(String userId);

    /**
     * 根据openId 获取user信息
     *
     * @param openId
     * @return
     */
    WxUser wechatOpenIdIsExists(String openId);

    int insertUser(WxUser wxUser);

    /**
     * 根据openId 更新数据
     *
     * @param wxUser
     * @return
     */
    int updateByUserOpenId(WxUser wxUser);

    int updateByUserId(WxUser wxUser);
}
