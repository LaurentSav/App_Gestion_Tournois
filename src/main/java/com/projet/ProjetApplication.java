package com.projet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@Controller
public class ProjetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetApplication.class, args);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account")
    public String account() {
        return "account.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public String search() {
        return "search.html";
    }


}
