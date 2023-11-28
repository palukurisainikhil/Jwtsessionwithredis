package com.example.jwtsessionwithredis.controller;

import com.example.jwtsessionwithredis.service.RedisService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;

    @GetMapping("/home")
    public String home(HttpSession httpSession){
        increment(httpSession,"count");
        redisService.insertData("count - " + httpSession.getId(),httpSession.getAttribute("count"));
        return "Home call";
    }

    @GetMapping("/getCount")
    public String getCount(HttpSession httpSession){
        Object count = redisService.getData("count - ");
        return "Value of count:" + httpSession.getAttribute("count");
    }

    @DeleteMapping("/deleteSession")
    public String deleteSession(HttpSession httpSession) {
        httpSession.invalidate();
        return "Session deleted";
    }

    private void increment(HttpSession httpSession, String count) {
        Integer value = httpSession.getAttribute(count) == null ? 0 : (Integer) httpSession.getAttribute(count);
        httpSession.setAttribute(count,value+1);
    }
}
