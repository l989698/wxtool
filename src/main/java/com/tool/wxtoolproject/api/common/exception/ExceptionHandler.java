package com.tool.wxtoolproject.api.common.exception;


import com.tool.wxtoolproject.api.common.error.code.BaseEnumError;
import com.tool.wxtoolproject.api.common.message.JsonResult;
import com.tool.wxtoolproject.api.common.message.JsonView;
import com.tool.wxtoolproject.api.common.message.ShopCmsLog;
import com.tool.wxtoolproject.api.common.util.json.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


/**
 * 统一异常处理，用于将 Exception 转换为共客户端使用的 errorcode
 */
@Component
@Slf4j
public class ExceptionHandler implements HandlerExceptionResolver {
	@Resource
	JsonView jsonView;

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
		/**
		 * 目前支持两种返回形式
		 * <1>AppException移动端调用，返回BaseRspParam
		 * <2>CashLoanException其他系统调用返回 JsonResult
		 */
		String jsonData;
		String respCode = "999999";
		response.setStatus(200);
		ex.printStackTrace();
		JsonResult jsonResult = new JsonResult();
		jsonResult.setBody(new HashMap<>(0));
	    if(ex instanceof AppException){
			BaseEnumError baseEnumError = ((BaseException) ex).getRespCodeMessage();
			AppException ce = (AppException)ex;
			if(baseEnumError != null){
				respCode = baseEnumError.getCode();
				jsonResult.setError(baseEnumError);
			}else if(StringUtils.isNotBlank(ce.getMessage())){
				if(StringUtil.isBlank(ce.getCode())){
					ce.setCode("400000");
				}
				respCode = ce.getCode();
				jsonResult.setError(ce.getCode(),ce.getMessage());
			} else{
				response.setStatus(500);
				//未知错误
				jsonResult.setError(BaseEnumError.SYS_ERR);
			}
			jsonData = FastJsonUtil.objToJson(jsonResult);
			bigLogException(request,jsonData,ex,respCode);
			return new ModelAndView(jsonView).addObject("error",jsonData);
	    }else{
			jsonResult.setError(BaseEnumError.SYS_ERR_404);
			return new ModelAndView(jsonView).addObject("error",FastJsonUtil.objToJson(jsonResult));
		}

	}


	/**
	 * 记录log错误响应报文 ResponseData/ResponseEtData
	 */
	private void bigLogException(HttpServletRequest request, String jsonData, Exception ex, String code){
		ShopCmsLog shopCmsLog = (ShopCmsLog)request.getAttribute("shopCmsLog");
		shopCmsLog.setResponseData(jsonData);
		if(shopCmsLog.getException()==null){
			shopCmsLog.setException(ex);
		}
		shopCmsLog.setResponseCode(code);
		log.error(shopCmsLog.getRequestId(),ex);
	}
}