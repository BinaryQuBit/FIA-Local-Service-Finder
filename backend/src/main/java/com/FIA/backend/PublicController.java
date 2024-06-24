package com.FIA.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the restricted area!";
    }
}
