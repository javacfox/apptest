package com.game.itstar.controller;

import com.game.itstar.entity.Test;
import com.game.itstar.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public Test getTest(@RequestParam Integer id){
        return testService.getTestById(id);
    }

    @PostMapping
    public String saveTest(@RequestBody Test test){
        testService.saveTest(test);
        return "保存成功";
    }
}
