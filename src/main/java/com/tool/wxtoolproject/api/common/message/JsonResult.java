package com.tool.wxtoolproject.api.common.message;




import com.tool.wxtoolproject.api.common.error.code.BaseEnumError;

import java.io.Serializable;


public class JsonResult implements Serializable {

    private static final long serialVersionUID = 3016236218647866097L;
    /**
     * 状态码
     */
    private String code;
    /**
     * 信息描述
     */
    private String msg;
    /**
     * 返回报文
     */
    private Object body = null;
    /**
     * 错误码枚举，不需要序列化
     */
    private transient BaseEnumError error;

    public JsonResult() {
    }

    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static JsonResult newJsonResult(BaseEnumError error, Object ob) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setError(error);
        jsonResult.setBody(ob);
        return jsonResult;
    }
	public static JsonResult newJsonResult(Object code, Object msg,Object map) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setError(code.toString(),msg.toString());
		jsonResult.setBody(map);
		return jsonResult;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setError(BaseEnumError error) {
        this.code = error.getCode();
        this.msg = error.getMsg();
        this.error = error;
    }

    public void setError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
