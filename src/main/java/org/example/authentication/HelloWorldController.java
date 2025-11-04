package org.example.authentication;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {
    @GetMapping("hello")
            public String home (){
        return "home";
    }
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}
