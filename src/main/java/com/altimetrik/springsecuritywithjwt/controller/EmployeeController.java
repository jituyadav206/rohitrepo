package com.altimetrik.springsecuritywithjwt.controller;

import com.altimetrik.springsecuritywithjwt.JwtUtils;
import com.altimetrik.springsecuritywithjwt.model.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping(value = "/employee/{name}")
    public String getEmployee(@PathVariable(value ="name") String name){
        return "Hello " + name + " welcome to organization";
    }

    @PostMapping(value = "/login")
    public UserToken login(@RequestParam("name") String userName, @RequestParam("password") String password){

        String token = jwtUtils.generateToken(userName);
        UserToken user = new UserToken();
        user.setName(userName);
        user.setToken(token);
        return user;
    }


}
