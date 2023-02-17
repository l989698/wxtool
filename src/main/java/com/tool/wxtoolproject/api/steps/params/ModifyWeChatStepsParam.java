package com.tool.wxtoolproject.api.steps.params;

import lombok.Data;

@Data
public class ModifyWeChatStepsParam {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 修改步数
     */
    private String steps;
}
