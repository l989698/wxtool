package com.tool.wxtoolproject.api.common.message;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;


@Component
public class JsonView extends AbstractView {


    @Override
    protected void renderMergedOutputModel(Map<String, Object> arg0,
                                           HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
        arg2.setHeader("Access-Control-Allow-Origin", "*");
        arg2.setCharacterEncoding("UTF-8");
        arg2.setContentType("text/json; charset=UTF-8");
        PrintWriter out = arg2.getWriter();
        out.write((String) arg0.get("error"));
        out.flush();
        out.close();
    }

}
