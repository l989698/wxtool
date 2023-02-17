package com.tool.wxtoolproject.api.common.config;

import com.tool.wxtoolproject.api.common.util.NetworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by Tony on 2017/5/20.<br/>
 * Api接入类的基类，请继承.<br/>
 * 1：刷新用户session时长45min .<br/>
 * 2：判断是否登录/权限拦截.<br/>
 * 3: 参数转换和拦截.
 */
@Slf4j
public class BaseConfig {

    @Autowired
    private MessageSource messageSource;

    /**
     * 在本controller所有方法执行前执行本方法.<br>
     * <p><blockquote><pre>
     *     1.session timeout refresh.
     *     2.print commont log.
     *     3.check login -- unify use header parameter "accessToken".
     *     </pre></blockquote><p>
     *
     * @param request HttpServletRequest  http客户端请求对象
     * @param uAT     user access token
     */
    @ModelAttribute
    public void firstMC(HttpServletRequest request, @RequestHeader(value = "uAT", required = false) String uAT) {
        request.setAttribute("uAT", uAT);  //此处可转换用户的id
        //打印请求日志
        printAccess(request);
    }

    private final void printAccess(HttpServletRequest request) {
        StringBuilder su = new StringBuilder();
        su.append("\nUser-Access-Args:").append("{");
        su.append("\"protocol\":\"").append(request.getProtocol() + "(" + request.getScheme()).append(")\",");
        su.append("\"ip\":\"").append(NetworkUtils.getIpAddr(request)).append("\",");
        su.append("\"port\":\"").append(NetworkUtils.getPort(request)).append("\",");
        su.append("\"method\":\"").append(request.getMethod()).append("\",");
        su.append("\"url\":\"").append(NetworkUtils.getCurrentURL(request)).append("\",");
        su.append("\"user-agent\":\"").append(NetworkUtils.getUserAgent(request)).append("\",");
        su.append("\"uAT\":\"").append(request.getAttribute("uAT")).append("\"}");
        log.info(su.toString());
    }

    /**
     * 获取国际化提示信息  ?lang=en_US / zh_CN
     */
    protected String getI18nMessage(HttpServletRequest request, String messageKey) {
        Locale locale = RequestContextUtils.getLocale(request);
        return messageSource.getMessage(messageKey, null, locale);
    }
}
