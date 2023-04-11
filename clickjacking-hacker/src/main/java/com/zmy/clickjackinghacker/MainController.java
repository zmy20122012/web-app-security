package com.zmy.clickjackinghacker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/award")
    public String fakePage(){
        return "award";
    }
}
