package com.projet;

import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import com.projet.Users.User;
import com.projet.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class ApplicationController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TournamentRepository trepo;

    @GetMapping("")
    public String viewHomePage(Model model) {
        List<Tournament> listTournoi = trepo.findAll();
        model.addAttribute("listTournoi", listTournoi);
        return "index";
    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/account")
    public String viewAccount(Model model) {
        return "account";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login_form";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }
    @GetMapping("/createtournament")
    public String createTournament(Model model) {
        model.addAttribute("tournament", new Tournament());
        return "createtournament";
    }

    

}
