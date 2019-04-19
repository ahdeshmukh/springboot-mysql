package com.deshmukhamit.springbootmysql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController extends BaseController {
    @GetMapping("/public")
    public String getPublicData() {
        return "This is a publicly accessible route";
    }
}
