package com.example.jwtsessionwithredis.controller;

import com.example.jwtsessionwithredis.dto.User;
import com.example.jwtsessionwithredis.service.RedisService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private User user;


    @GetMapping("/home")
    public String home(HttpSession httpSession){
        increment(httpSession,"count");
        return "Home call";
    }

    @GetMapping("/getCount")
    public String getCount(HttpSession httpSession){
        return "Value of count:" + httpSession.getAttribute("count");
    }

    @DeleteMapping("/deleteSession")
    public String deleteSession(HttpSession httpSession) {
        httpSession.invalidate();
        return "Session deleted";
    }

    @GetMapping("/insertDataIntoRedis")
    public String insertDataIntoRedis(HttpSession httpSession){
        redisService.insertData("count - " + httpSession.getId(),httpSession.getAttribute("count"));
        return "Count Data was inserted into redis";
    }

    @GetMapping("/insertUserData")
    public String insertUserData(@RequestParam(name="username") String username, HttpSession httpSession) {
        user.setUsername(username);
        user.setSessionId(httpSession.getId());
        return "Data is inserted";
    }

    @GetMapping("/getUserData")
    public String retrieveUserData(){
        String name = user.getUsername();
        return "User name : " + user.getUsername();
    }

    private void increment(HttpSession httpSession, String count) {
        Integer value = httpSession.getAttribute(count) == null ? 0 : (Integer) httpSession.getAttribute(count);
        httpSession.setAttribute(count,value+1);
    }
}
