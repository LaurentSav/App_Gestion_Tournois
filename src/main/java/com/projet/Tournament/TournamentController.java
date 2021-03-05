package com.projet.Tournament;

import com.projet.Player.Player;
import com.projet.Player.PlayerService;
import com.projet.Team.Team;

import com.projet.Users.CustomUserDetails;

import com.projet.Team.TeamController;
import com.projet.Team.TeamService;

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
    private TeamService teamService;

    @Autowired
    private CustomUserDetailsService userService;


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
    public String viewParticipant(Model model, @PathVariable("tournamentId") Long tournamentId){
        List<Team> t = teamService.getTeams(tournamentId);
        model.addAttribute("team", t);
        model.addAttribute("tournois", tournamentId);
        return "tournament_participant";
    }

    @GetMapping( "/{tournamentId}/participant/{teamid}")
    public String viewTeam(Model model, @PathVariable("tournamentId") Long tournamentId, @PathVariable("teamid") Long teamid, Principal principal){

        if(principal != null){
            Tournament t = tournamentService.getTournament(tournamentId);
            if(t.getOwner().getEmail().equals(principal.getName())){
                model.addAttribute("owner", true);
            }
        }
        List<Player> p = playerService.getTeamPlayers(teamid);
        model.addAttribute("teams", p);
        model.addAttribute("teamid", teamid);
        model.addAttribute("tid", tournamentId);
        return "view_player";
    }

    @GetMapping( "/{tournamentId}/participant/{teamid}/{playerid}/edit")
    public String editPlayer(Model model, @PathVariable("tournamentId") Long tournamentId, @PathVariable("teamid") Long teamid, @PathVariable("playerid") Long pid){
        return "create_player";
    }


    @GetMapping( "/{tournamentId}/participant/{teamid}/createplayer")
    public String viewPlayer(Model model, @PathVariable("tournamentId") Long tournamentId, @PathVariable("teamid") Long teamid){
        model.addAttribute("player", new Player());
        model.addAttribute("teamid", teamid);
        model.addAttribute("tid", tournamentId);
        return "create_player";
    }

    @PostMapping( "/{tournamentId}/participant/{teamid}/createplayer/register")
    public String createPlayer(Model model, @PathVariable("tournamentId") Long tournamentId, @PathVariable("teamid") Long teamid, Player player, Principal principal){
        model.addAttribute("teamid", teamid);
        model.addAttribute("tid", tournamentId);
        if(principal != null){
            playerService.addNewPlayer(player, principal, teamid);
        }else{
            playerService.addNewPlayer(player, teamid);
        }

        model.addAttribute("player", true);

        return "edition_success";


    }

    @DeleteMapping( "/{tournamentId}/participant/{teamid}/{playerid}")
    public String deletePlayerById(@PathVariable("tournamentId") Long tournamentId, @PathVariable("teamid") Long teamid,  @PathVariable("playerid") Long pid){
        playerService.deletePlayer(pid);
        return "redirect:/";

    @GetMapping( "/{tournamentId}/participant/create_team")
    public String addTeam(Model model, @PathVariable Long tournamentId){
        model.addAttribute("team",new Team());
        model.addAttribute("tournamentid",tournamentId);
        return "create_team";
    }

    @GetMapping( "/{tournamentId}/planning")
    public String viewPlanning(Model model, @PathVariable("tournamentId") Long tournamentId, Principal principal){
        if(principal != null){
            return "tournament_planning";
        }
        return "redirect:/";
    }

    @GetMapping( "/{tournamentId}/setting")
    public String viewSetting(Model model, @PathVariable("tournamentId") Long tournamentId, Principal principal){

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
