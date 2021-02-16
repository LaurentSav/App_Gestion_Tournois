package com.projet.Tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {

    @Autowired
    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getTournaments() {
        return tournamentRepository.findAll();
    }

    public void addNewTournament(Tournament tournament){
        Optional<Tournament> tournamentOptional = tournamentRepository.findTournamentByName(tournament.getName());
        if(tournamentOptional.isPresent()){
            throw
                    new IllegalStateException("Name taken");
        }

        tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long tournamentId) {
        boolean exists = tournamentRepository.existsById(tournamentId);
        if (!exists){
            throw
                    new IllegalStateException("Tournament does not exist");
        }
        tournamentRepository.deleteById(tournamentId);
    }
}
