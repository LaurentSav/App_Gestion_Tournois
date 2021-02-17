package com.projet;

import com.projet.Tournament.Tournament;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Controller
@SpringBootApplication
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
