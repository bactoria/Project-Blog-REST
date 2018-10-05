package com.bactoria.toy1.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@AllArgsConstructor // 이게 @Autowired 대신 사용가능
public class WebRestController {

    private Environment env;

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("Not Exist");
    }

    @GetMapping("/")
    public String index() {
        return "bactoris's API Server";
    }

}

