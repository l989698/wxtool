package com.tool.wxtoolproject.api.user.params;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class LoginParams {

   private String userId;
   private JSONObject rawData;
}
