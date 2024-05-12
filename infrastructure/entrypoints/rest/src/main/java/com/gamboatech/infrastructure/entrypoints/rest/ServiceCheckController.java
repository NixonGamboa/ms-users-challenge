package com.gamboatech.infrastructure.entrypoints.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class ServiceCheckController {

    @GetMapping("/service-check")
    public String serviceCheck() {
        return "I'm alive!";
    }
}
