package com.tool.wxtoolproject.api.common.user;

import lombok.Data;

@Data
public class WeChatLoginModel {
    private String code;
    private String userId;
}