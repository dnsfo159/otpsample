package com.oauth.test.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class loginController {
    @GetMapping("/local/login")
    public String login(){

        return "login";
    }

    @GetMapping("/local/view")
    public String view(){

        return "view";
    }

    @GetMapping("/local/otp")
    public String otp(){

        return "otpcheck";
    }


    @GetMapping("/local/main")
    public String main(){

        return "main";
    }

}


