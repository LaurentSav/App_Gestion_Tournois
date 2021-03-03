package com.projet.Tournament;

import com.projet.Player.PlayerService;
import com.projet.Users.CustomUserDetailsService;
import com.projet.Users.User;
import com.projet.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping(path = "tournament")
public class TournamentController {

    @Autowired
    private final TournamentService tournamentService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public List<Tournament> getTournaments(){
        return tournamentService.getTournaments();
    }

    @PostMapping("/register")
    public String registerNewTournament(Tournament tournament, Principal principal){
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        } else {
            String username = principal.toString();
        }*/

        tournamentService.addNewTournament(tournament, principal);
        return "create_success";
    }

    @GetMapping( "/{tournamentId}")
    public String findById(Model model, @PathVariable Long tournamentId, Principal principal){

        Tournament t = tournamentService.getTournament(tournamentId);
        if(principal != null){
            if(t.getOwner().getEmail().equals(principal.getName())){
                model.addAttribute("owner", true);
            }
        }else{
            model.addAttribute("owner", false);
        }
        model.addAttribute("tournament",t);
        return "view_tournament";
    }
    @GetMapping( "/{tournamentId}/participant")
    public String viewParticipant(Model model, @PathVariable Long tournamentId){
        model.addAttribute("player",playerService.getPlayers());
        return "tournament_participant";
    }

    @GetMapping( "/{tournamentId}/planning")
    public String viewPlanning(Model model, @PathVariable Long tournamentId, Principal principal){
        if(principal != null){
            return "tournament_planning";
        }
        return "redirect:/";
    }

    @GetMapping( "/{tournamentId}/setting")
    public String viewSetting(Model model, @PathVariable Long tournamentId, Principal principal){

        if(principal == null){
            return "error";
        }
        model.addAttribute("tournament",tournamentService.getTournament(tournamentId));
        return "tournament_setting";
    }

    @DeleteMapping( "/{tournamentId}")
    public String deleteTournamentById(@PathVariable("tournamentId") Long tournamentId){
        tournamentService.deleteTournament(tournamentId);
        return "redirect:/";
    }

    @PostMapping({ "/{tournamentId}/update"} )
    public String updateTournament(
            @PathVariable("tournamentId") Long tournamentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String game,
            @RequestParam(required = false) Boolean is_private,
            @RequestParam(required = false) Integer nb_participants,
            @RequestParam(required = false) String description,
            Model model){
        tournamentService.updateTournament(tournamentId, name,game, is_private, nb_participants, description);
        model.addAttribute("tid", tournamentId);
        model.addAttribute("tour", true);
        return "edition_success";
    }

    @PutMapping({"/start/{tournamentId}"} )
    public void startTournament(@PathVariable("tournamentId") Long tournamentId){
        tournamentService.startTournament(tournamentId);
    }




}
