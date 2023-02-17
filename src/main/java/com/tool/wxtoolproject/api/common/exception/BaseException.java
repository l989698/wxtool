package com.tool.wxtoolproject.api.common.exception;


import com.tool.wxtoolproject.api.common.error.code.BaseEnumError;

/**
 * @author zhaolei
 * @date 2017-12-8 16:40:57
 * 异常基类
 */
public class BaseException extends Exception  {

	private static final long serialVersionUID = -5008720177368489411L;

	private String code;

	private BaseEnumError baseEnumError;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause,
                         boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(BaseEnumError baseEnumError) {
		super(baseEnumError.getMsg());
		this.code = baseEnumError.getCode();
		this.baseEnumError = baseEnumError;
	}
	public BaseEnumError getRespCodeMessage(){
		return baseEnumError;
	}

}
