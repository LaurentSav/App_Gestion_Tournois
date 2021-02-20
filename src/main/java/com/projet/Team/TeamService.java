package com.projet.Team;

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
    private TournamentRepository tournamentRepository;

    public TeamService(TeamRepository teamRepository, TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
    }


    public List<Team> getTeams(@RequestParam Long tournament_id) {
        return teamRepository.
                findTeamsBytournament(tournamentRepository.findById(tournament_id).get());
    }
    public List<Team> getTeams() {
        return teamRepository.findAll();
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
        if (!exists){
            if(team.getName() == optionalTeam.get().getName() && team.getTournament() == optionalTeam.get().getTournament())
            throw
                    new IllegalStateException("Team " + team.getName() +" already exists");
        }
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Long tournamentId, String name, Integer nb_members) {
        Optional<Team> teamOptional = teamRepository.findById(tournamentId);
        boolean exists = teamOptional.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Team " + name +" does not exist");
        }

        if(name != null && name.length() > 0 && !Objects.equals(teamOptional.get().getName(), name)){
            teamOptional.get().setName(name);
        }


        if(nb_members != null && !Objects.equals(teamOptional.get().getNbMembers(), nb_members)){
            teamOptional.get().setNbMembers(nb_members);
        }
    }
}



