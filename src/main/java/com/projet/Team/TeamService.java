package com.projet.Team;

import com.projet.Player.Player;
import com.projet.Player.PlayerService;
import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private PlayerService playerService;



    public TeamService(TeamRepository teamRepository, TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public List<Team> getTeams(@RequestParam Long tournament_id) {
        return teamRepository.findTeamsBytournament(tournament_id);
    }

    public List<Player> getTeamParticipant(@RequestParam Long team_id){
        return playerService.getTeamPlayers(team_id);
    }

    public void deleteTeam(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        boolean exists = team.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Team" + teamId +" does not exist");
        }
        teamRepository.delete(team.get());
    }

    public void addNewTeam(Team team) {
        Optional<Team> optionalTeam =
                teamRepository.findTeamByNameAndTournament(team.getName(), team.getTournament());
        boolean exists = optionalTeam.isPresent();
        if (exists){
            if(team.getName() == optionalTeam.get().getName() && team.getTournament() == optionalTeam.get().getTournament())
            throw
                    new IllegalStateException("Team " + team.getName() +" already exists");
        }
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Long tournamentId, String name) {
        Optional<Team> teamOptional = teamRepository.findById(tournamentId);
        boolean exists = teamOptional.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Team " + tournamentId +" does not exist");
        }

        if(name != null && name.length() > 0 && !Objects.equals(teamOptional.get().getName(), name)){
            teamOptional.get().setName(name);
        }

    }
}



