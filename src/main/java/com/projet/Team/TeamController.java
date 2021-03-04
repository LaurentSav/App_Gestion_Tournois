package com.projet.Team;

import com.projet.Tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
