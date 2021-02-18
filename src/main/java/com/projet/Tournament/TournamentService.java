package com.projet.Tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TournamentService {

    @Autowired
    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getTournaments() {
        return tournamentRepository.findAllByIsPrivateIsFalse();
    }


    public void addNewTournament(Tournament tournament){
        Optional<Tournament> tournamentOptional = tournamentRepository.findTournamentByName(tournament.getName());
        if(tournamentOptional.isPresent()){
            throw
                    new IllegalStateException("Name taken");
        }
        tournamentRepository.save(tournament);
    }


  /*  public void deleteTournament(String tounrnamentName) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findTournamentByName(tounrnamentName);
        boolean exists = tournamentOptional.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Tournament " + tounrnamentName +" does not exist");
        }
        tournamentRepository.delete(tournamentOptional.get());
    }*/

    public void deleteTournament(long tournamentId) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        boolean exists = tournamentOptional.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Tournament " + tournamentId +" does not exist");
        }
        tournamentRepository.delete(tournamentOptional.get());
    }

    @Transactional
    public void updateTournament(Long tournamentId, String name, Boolean is_private, Integer nb_participants) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        boolean exists = tournamentOptional.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Tournament " + tournamentId +" does not exist");
        }

        if(name != null && name.length() > 0 && !Objects.equals(tournamentOptional.get().getName(), name)){
            tournamentOptional.get().setName(name);
        }

        if(is_private != null && !Objects.equals(tournamentOptional.get().isPrivate(), is_private)){
            tournamentOptional.get().setPrivate(is_private);
        }

        if(nb_participants != null && !Objects.equals(tournamentOptional.get().getNumberOfParticipants(), nb_participants)){
            tournamentOptional.get().setNumberOfParticipants(nb_participants);
        }
    }
}
