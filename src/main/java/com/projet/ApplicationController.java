package com.projet;

import com.projet.Team.Team;
import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import com.projet.Tournament.TournamentService;
import com.projet.Users.CustomUserDetails;
import com.projet.Users.CustomUserDetailsService;
import com.projet.Users.User;
import com.projet.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class ApplicationController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("")
    public String viewHomePage(Model model, @RequestParam(defaultValue = "1") int p) {

        Page<Tournament> page = tournamentService.getTournaments(p);
        List<Tournament> listTournoi = page.getContent();
        model.addAttribute("currentPage", p);
        model.addAttribute("previousPage", p-1);
        model.addAttribute("nextPage", p +1);
        model.addAttribute("totalPage", page.getTotalPages());
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
    public String viewAccount(Model model, Principal principal) {
        if(principal != null){
            User user = userRepo.findByEmail(principal.getName());
            model.addAttribute("u", user);
        }

        return "account";
    }

    @GetMapping("/account/editAccount")
    public String editAccount(Model model, User user){
        return "editAccount";
    }

    @PostMapping("/account")
    public String confirmEdit(User user, Model model){
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        customUserDetailsService.updateUser(user.getEmail(),user.getFirstName(),user.getLastName());
        model.addAttribute("account", true);
        return "edition_success";
    }



    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login_form";
    }

    @GetMapping("/register")
    public String showRegistrationForm(User user) {

        return "signup_form";
    }
    @GetMapping("/search")
    public String showSearch(Model model, @RequestParam(defaultValue = "") String search){
        if(search != ""){
            List<Tournament> tournamentsSearch = tournamentService.findbyWord(search);
            model.addAttribute("tournaments", tournamentsSearch);
            model.addAttribute("recherche", search);
        }
        return "Search";
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

    @GetMapping("/yourtournament")
    public String yourtournament(Model model, @RequestParam(defaultValue = "1") int p, Principal principal){
        if(principal == null){
            return "index";
        }
        CustomUserDetails user1 = customUserDetailsService.loadUserByUsername(principal.getName());
        Page<Tournament> page = tournamentService.getUserTournaments(p, user1);
        model.addAttribute("listTournoi", page);
        List<Tournament> listTournoi = page.getContent();
        model.addAttribute("currentPage", p);
        model.addAttribute("previousPage", p-1);
        model.addAttribute("nextPage", p +1);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listTournoi", listTournoi);
        return "your_tournament";
    }


}
