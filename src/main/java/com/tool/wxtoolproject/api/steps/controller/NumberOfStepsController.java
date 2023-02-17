package com.tool.wxtoolproject.api.steps.controller;

import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.steps.params.ModifyWeChatStepsParam;
import com.tool.wxtoolproject.api.steps.service.NumberOfStepsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/numberOfSteps")
public class NumberOfStepsController {
    @Autowired
    private NumberOfStepsService numberOfStepsService;


    /**
     * 同过小米修改运动步数
     *
     * @param param
     * @return
     */
    @PostMapping("/modifyWeChatSteps")
    public Result modifyWeChatSteps(@RequestBody  ModifyWeChatStepsParam param) {
        return numberOfStepsService.modifyWeChatSteps(param);
    }

}
