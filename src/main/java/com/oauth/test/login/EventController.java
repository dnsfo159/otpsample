package com.oauth.test.login;


import com.oauth.test.UserRepository;
import com.oauth.test.tb_Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    UserRepository userRepository;



    @RequestMapping("/loginFind")
    @ResponseBody
    public List<tb_Login> login(@RequestParam String id  , String pw) {

        return userRepository.findByIdAndPw(id,pw);

    }

    @RequestMapping("/otplastcheck")
    @ResponseBody
    public boolean otpCheck(@RequestParam String outnumbered , String id) {


        GoogleOTP otp = new GoogleOTP();

        String key = "";

        tb_Login user = userRepository.findById(id);

        System.out.println("데이터 확인 " + user.getSecretkey());
        key = user.getSecretkey();    //???;



        boolean check = false;
        if(otp.checkCode(outnumbered,key)){
            jpatest2U(user,true);
            check = true;

        }

        return check;
    }

    @GetMapping("/otpSetting")
    @ResponseBody
    public HashMap<String,String> otpSetting(@RequestParam String id){

        GoogleOTP otp = new GoogleOTP();

        System.out.println("들어오는것 확인 : "  + id);

        HashMap<String,String> map = otp.generate("testotp",id);

        System.out.println("map 확인 : " + map);

        String otpkey = map.get("encodedKey");

//        Optional<tb_Login> user = userRepository.findById(id);
        tb_Login user = userRepository.findById(id);

        jpatest1U(user,otpkey);

        return map;
    }

    public void jpatest1U(tb_Login user , String key) {
        user.setSecretkey(key);
        user.setOtpYN(true);
        userRepository.save(user);
    }

    public void jpatest2U(tb_Login user , boolean key) {
        user.setOtpYN(key);
        userRepository.save(user);
    }

}
