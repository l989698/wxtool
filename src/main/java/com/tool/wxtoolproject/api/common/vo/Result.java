package com.tool.wxtoolproject.api.common.vo;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 接口返回标准
 *
 * @author liuhy
 * @date 2021/5/29 16:17
 */
@Data
public class Result implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4912312620800645580L;
    /**
     * 返回代码
     */
    private int status;
    /**
     * 代码说明
     */
    private String message;
    /**
     * 返回数据
     */
    private String data;

//	private Object data;

    /**
     * 以状态方式初始化
     *
     * @param status http状态
     */
    public Result(HttpStatus status, Object data) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
        Object temp = data;
        if (null != data && Objects.nonNull(data)) {
            if (data instanceof CharSequence) {
                this.data = (String) data;
                return;
            } else if (data instanceof Page<?>) {
                temp = new Pager<>((Page<?>) data);
            }
            this.data = JSONObject.toJSONString(temp);
//			this.data = temp;
        }
    }

    public Result(int code, String message, String data) {
        super();
        this.status = code;
        this.message = message;
        this.data = data;
    }

    public Result() {
        super();
    }

    /**
     * 将返回结果详细信息转换成对象
     *
     * @param <T>
     * @param clazz 返回对象类型
     * @return 返回数据
     */
    @SuppressWarnings("unchecked")
    public <T> T getData(Class<T> clazz) {
        if (this.data == null) {
            return null;
        }
        if (clazz == CharSequence.class) {
            return (T) String.valueOf(data);
        } else if (clazz == Integer.class) {
            return (T) Integer.valueOf(data);
        } else if (clazz == Long.class) {
            return (T) Long.valueOf(data);
        } else if (clazz == Double.class) {
            return (T) Double.valueOf(data);
        } else if (clazz == Boolean.class) {
            return (T) Boolean.valueOf(data);
        } else if (clazz == BigDecimal.class) {
            return (T) new BigDecimal(data);
        }
        return JSONObject.parseObject(data, clazz);
    }

    @SuppressWarnings("unchecked")
    public <E> Pager<E> getPageData(Class<E> clazz) {
        if (this.data == null) {
            return null;
        }
        Pager<E> pager = JSONObject.parseObject(data, Pager.class);
        List<?> result = pager.getList();
        if (result.size() > 0) {
            List<E> list = result.stream().map((o) -> ((JSONObject) o).toJavaObject(clazz)).collect(Collectors.toList());
            pager.setList((List<E>) list);
        }

        return pager;
    }

    public <E> List<E> getListData(Class<E> clazz) {
        if (this.data == null) {
            return null;
        }
        return JSONObject.parseArray(data, clazz);
    }

    /**
     * 正常返回
     *
     * @param data 数据
     * @return
     */
    public static Result ok(Object data) {
        return new Result(HttpStatus.OK, data);
    }

    /**
     * 正常返回
     *
     * @return
     */
    public static Result ok() {
        return new Result(HttpStatus.OK, null);
    }

    /**
     * 返回失败
     *
     * @param data 数据
     * @return
     */
    public static Result fail(Object data) {
        return new Result(HttpStatus.BAD_REQUEST, data);
    }

    /**
     * 返回失败
     *
     * @return
     */
    public static Result fail() {
        return new Result(HttpStatus.BAD_REQUEST, null);
    }

    /**
     * 返回错误
     *
     * @param data 数据
     * @return
     */
    public static Result error(Object data) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    /**
     * 返回错误
     *
     * @return
     */
    public static Result error() {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    /**
     * 请求网关出错
     *
     * @return
     */
    public static Result badGateway() {
        return new Result(HttpStatus.BAD_GATEWAY, null);
    }
//	HttpStatus

    /**
     * 请求网关出错
     *
     * @return
     */
    public static Result badGateway(Object data) {
        return new Result(HttpStatus.BAD_GATEWAY, data);
    }

    /**
     * 自定义返回
     *
     * @param status 状态
     * @return
     */
    public static Result other(HttpStatus status) {
        return new Result(status, null);
    }

    /**
     * 自定义返回
     *
     * @param status 状态
     * @param data   数据
     * @return
     */
    public static Result other(HttpStatus status, Object data) {
        return new Result(status, data);
    }

    /**
     * 接口状态
     *
     * @author 张帅
     * @since 2020年11月10日
     * @version 1.0
     */
//	public static enum HttpStatus {
//
//		ok(200, "success")
//		// 错误请求
//		, fail(400, "fail")
//		// 无权访问
//		, unaut(401, "Unauthorized")
//		// 验证错误
//		, paymetRequired(402, "Payment Required")
//		// 服务器拒绝请求
//		, forbidden(403, "Forbidden")
//		// 服务器内部错误
//		, error(500, "error")
//		// 网关错误，服务器作为网关或代理，从上游服务器收到无效响应
//		, badGateway(502, "Bad Gateway")
//		// 请求超时
//		, timeout(504, "GatewayTimeout");
//
//		/**
//		 * 状态码
//		 */
//		private int code;
//
//		/**
//		 * 说明信息
//		 */
//		private String msg;
//
//		private HttpStatus(int code, String msg) {
//			this.code = code;
//			this.msg = msg;
//		}
//
//		public int getCode() {
//			return code;
//		}
//
//		public String getMsg() {
//			return msg;
//		}
//	}
}
