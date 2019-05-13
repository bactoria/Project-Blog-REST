package com.bactoria.toy1.index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexRestController {

    @GetMapping("/")
    public String index() {
        return "bactoria Blog's RESTful API";
    }

}

