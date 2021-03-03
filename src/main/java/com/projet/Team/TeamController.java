package com.projet.Team;

import com.projet.Player.Player;
import com.projet.Tournament.Tournament;
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
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/tournament={t_id}")
    public List<Team> getTournamentTeams( @PathVariable Long t_id){
        return teamService.getTeams(t_id);
    }

    @PostMapping
    public void registerNewTeam(@RequestBody Team team){
        teamService.addNewTeam(team);
    }


    @GetMapping( "/{team_id}")
    public String findParticipant(Model model, @PathVariable Long team_id, Principal principal){

        List<Player> listplayer = teamService.getTeamParticipant(team_id);
        model.addAttribute("players", listplayer);
        return "view_player";
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
