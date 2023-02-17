package com.tool.wxtoolproject.api.user.controller;


import com.tool.wxtoolproject.api.common.vo.Result;
import com.tool.wxtoolproject.api.user.service.WordOfTheDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wordOfTheDay")
@Controller
public class WordOfTheDayController {

    @Autowired
    private WordOfTheDayService wordOfTheDayService;

    @GetMapping("/getWordOfTheDay")
    public Result getWordOfTheDay(){
        return wordOfTheDayService.getWordOfTheDay();
    }
}
