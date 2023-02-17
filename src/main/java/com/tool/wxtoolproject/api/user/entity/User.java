package com.tool.wxtoolproject.api.user.entity;

import lombok.Data;

@Data
public class User {
    private String id;
    private String sessionKey;
    private String password;
}
