package com.projet.Team;

import com.projet.Player.Player;
import com.projet.Player.PlayerService;
import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import com.projet.Tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
    private TournamentService tournamentService;

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

    public Team getTeam(Long team_id){
        return teamRepository.findTeam(team_id);
    }

    public List<Player> getTeamParticipant(@RequestParam Long team_id){
        return playerService.getTeamPlayers(team_id);
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);
        boolean exists = team.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Team" + teamId +" does not exist");
        }
        List<Player> players = playerService.getTeamPlayers(teamId);
        for(Player p : players){
            playerService.deletePlayer(p.getId(), teamId);
        }
        teamRepository.delete(team.get());
    }

    public void addNewTeam(Team team, Long tournamentId) {
        Optional<Team> optionalTeam =
                teamRepository.findTeamByNameAndTournament(team.getName(), team.getTournament());
        boolean exists = optionalTeam.isPresent();
        if (exists){
            if(team.getName() == optionalTeam.get().getName() && team.getTournament() == optionalTeam.get().getTournament())
            throw
                    new IllegalStateException("Team " + team.getName() +" already exists");
        }
        Optional<Tournament> optionalTournament =
                tournamentRepository.findById(tournamentId);
        if(!optionalTournament.isPresent()){
            throw
                    new IllegalStateException("Team Tournament does not exist");
        }
        if(optionalTournament.get().getTeams().size() >= optionalTournament.get().getNumberOfParticipants()){
            throw
                    new IllegalStateException("Tournament full");

        }
        team.setTournament(tournamentRepository.findById(tournamentId).get());
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Long teamId, String name) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        boolean exists = teamOptional.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Team " + teamId +" does not exist");
        }

        if(name != null && name.length() > 0 && !Objects.equals(teamOptional.get().getName(), name)){
            teamOptional.get().setName(name);
        }

    }
}



