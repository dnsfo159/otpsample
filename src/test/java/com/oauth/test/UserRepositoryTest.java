package com.oauth.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    public void create(){
        tb_Google user = new tb_Google();
        user.setName("user1");
        user.setEmail("user1@gmail.com");
        user.setPhoneNumber("010-1111-2222");
        user.setRegDt(LocalDateTime.now());
        user.setRegUser("shlee0882");

        tb_Google newUser =userRepository.save(user);
        System.out.println("테스트입니다. : " + newUser);



    }

}