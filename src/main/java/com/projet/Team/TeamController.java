package com.projet.Team;


import com.projet.Player.Player;
import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "teams")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/tournament={t_id}")
    public List<Team> getTournamentTeams( @PathVariable Long t_id){
        return teamService.getTeams(t_id);
    }

    @PostMapping("/{tournament_id}")
    public String registerNewTeam(Team team, @PathVariable Long tournament_id){
        System.err.println("team tounament id = " + tournament_id);
        teamService.addNewTeam(team, tournament_id);
        return "redirect:/tournament/" + tournament_id.toString() + "/participant";
    }


    @GetMapping( "/{team_id}")
    public String findParticipant(Model model, @PathVariable Long team_id, Principal principal){
        /*
        List<Player> listplayer = teamService.getTeamParticipant(team_id);
        model.addAttribute("players", listplayer);*/
        return "view_player";
    }

    @GetMapping("/{team_id}/createplayer")
    public String createPlayer(Model model, @PathVariable Long team_id, Principal principal){

        model.addAttribute("player", new Player());
        return "create_player";
    }


    @DeleteMapping("/{team_Id}")
    public void deleteTeam(@PathVariable("team_Id") Long teamId){
        teamService.deleteTeam(teamId);
    }

    @PutMapping("/{team_Id}")
    public void updateTeam(
            @PathVariable("team_Id") Long team_Id,
            @RequestParam(required = false) String name){
        teamService.updateTeam(team_Id, name);
    }

}
