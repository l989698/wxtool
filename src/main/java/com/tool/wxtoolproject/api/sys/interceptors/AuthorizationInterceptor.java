package com.tool.wxtoolproject.api.sys.interceptors;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tool.wxtoolproject.api.common.error.code.BaseEnumError;
import com.tool.wxtoolproject.api.common.exception.AppException;
import com.tool.wxtoolproject.api.common.util.json.FastJsonUtil;
import com.tool.wxtoolproject.api.sys.annotation.AnnotationDealUtil;
import com.tool.wxtoolproject.api.sys.annotation.Authorize;
import com.tool.wxtoolproject.api.sys.annotation.PassToken;
import com.tool.wxtoolproject.api.user.service.UserService;
import com.tool.wxtoolproject.api.user.vo.MyUserInfoVo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author liuhy
 * @date 2021/6/20 15:19
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    //拦截器：请求之前preHandle
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        //检查是否有passtoken注释，有则跳过认证，注意:其中这个注解多余了
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        //如果有注解Authorize，就需要验证token
        if (method.isAnnotationPresent(Authorize.class)) {
            Authorize userLoginToken = method.getAnnotation(Authorize.class);
            if (userLoginToken.required()) {
                try {
                    String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token

                    // 执行认证
                    if (token == null) {
                        throw new AppException(BaseEnumError.TOKEN_IS_NULL);
                    }

                    //先把传入的参数序列化
                    String httpData = IOUtils.toString(httpServletRequest.getInputStream(), "UTF-8");
                    JSONObject bodyJson = JSONObject.parseObject(httpData);
                    httpServletRequest.setAttribute("body", bodyJson);
                    //定义一个map为入参把body里的值放入
                    Map<String, Object> reqMap = (Map<String, Object>) httpServletRequest.getAttribute("body");
                    if (reqMap == null) {
                        reqMap = new HashMap<String, Object>();
                    }

                    // 获取 token 中的 user id
                    String userId;
                    try {
                        // 获取 userid
                        userId = JWT.decode(token).getKeyId();
                        // 添加request参数，用于传递userid
                        httpServletRequest.setAttribute("userId", userId);
                        reqMap.put("userId",userId);
                        // 根据userId 查询用户信息
                        MyUserInfoVo user = userService.getUserById(userId);
                        if (user == null) {
                            throw new AppException(BaseEnumError.USER_NOT);
                        }

                        try {
                            String session_key = JWT.decode(token).getClaim("session_key").as(String.class);
                            // 添加request参数，用于传递userid
                            httpServletRequest.setAttribute("sessionKey", session_key);
                        } catch (Exception e) {
                        }

                        // 验证 密码
                        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getOpenId())).build();
                        try {
                            jwtVerifier.verify(token);
                        } catch (JWTVerificationException e) {
                            throw new AppException(BaseEnumError.SYS_401);
                        }


                    } catch (JWTDecodeException j) {
                        throw new AppException(BaseEnumError.SYS_401);
                    }
                    //组装参数传入controller
                    Object[] params = initParams(handlerMethod, reqMap, httpServletResponse);
                    Object data = method.invoke(handlerMethod.getBean(), params);
                    ObjectMapper mapper = new ObjectMapper();
                    httpServletResponse.setContentType("application/json; charset=utf-8");
                    mapper.writeValue(httpServletResponse.getOutputStream(), data);
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                    Exception e = (Exception) ex.getTargetException();
                    //固定抛出CashLoanException，用于统一异常处理判断，ExceptionHandler
                    if (!AppException.class.isAssignableFrom(e.getClass())) {
                        throw new AppException(BaseEnumError.SYS_ERR);
                    }
                    throw e;
                }
                return false;
            }
        }
        return true;
    }

    private Object[] initParams(HandlerMethod handlerMethod, Map<String, Object> reqMap, HttpServletResponse httpServletResponse) throws Exception {
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        Object[] params = new Object[methodParameters.length];
        //循环组装参数
        for (int i = 0; i < methodParameters.length; i++) {
            MethodParameter methodParameter = methodParameters[i];
            //获取参数类型
            Class<?> clazz = methodParameter.getParameterType();
            if (clazz == String.class || clazz == int.class || clazz == Integer.class) {
                //初始化参数名称
                methodParameter.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());
                if (clazz == int.class || clazz == Integer.class) {
                    params[i] = Double.valueOf(reqMap.get(methodParameter.getParameterName()).toString()).intValue();
                } else {
                    params[i] = reqMap.get(methodParameter.getParameterName());
                }
            } else if (clazz == HttpServletResponse.class) {
                params[i] = httpServletResponse;
                // 数据判断
            } else if (clazz == String[].class || clazz == Integer[].class) {
                List list = (ArrayList) reqMap.get(methodParameter.getParameterName());
                if (clazz == String[].class) {
                    params[i] = list.toArray(new String[list.size()]);
                } else {
                    params[i] = list.toArray(new Integer[list.size()]);
                }
            } else {
                Object param = FastJsonUtil.jsonToObj(FastJsonUtil.objToJson(reqMap), clazz);
                //验证属性上的注解
                verifyParameterAnnotation(param);
                params[i] = param;
            }
        }
        return params;
    }


    private void verifyParameterAnnotation(Object obj) throws Exception {
        Map<String, Object> map = AnnotationDealUtil.validate(obj);
        if (!(boolean) map.get(AnnotationDealUtil.RESULT)) {
            throw new AppException((String) map.get(AnnotationDealUtil.MESSAGE));
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    //拦截器：请求之后：afterCompletion
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
