package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/hello")
public class HelloController {

    @GetMapping("/")
    public String hello(HttpServletRequest request){
        return "SessionID: " + request.getSession().getId();
    }

}
