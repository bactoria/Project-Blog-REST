package com.bactoria.toy1.index;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexRestController {

    @GetMapping("/")
    public ResponseEntity index() {
        String message = "bactoria Blog's RESTful API";
        return ResponseEntity.ok(message);
    }
}

