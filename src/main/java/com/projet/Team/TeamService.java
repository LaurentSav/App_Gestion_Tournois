package com.projet.Team;

import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import com.projet.Tournament.TournamentService;
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
    private TournamentService tournamentService;

    public TeamService(TeamRepository teamRepository, TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public List<Team> getTeams(@RequestParam Long tournament_id) {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(tournament_id);
        if(!optionalTournament.isPresent()){
            throw new IllegalStateException("Tournament does not exist");
        }
        return teamRepository.findTeamsBytournament(optionalTournament.get());
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



