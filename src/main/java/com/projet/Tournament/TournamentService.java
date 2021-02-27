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
        List<Tournament> tournaments = tournamentRepository.findAllByIsPrivateIsFalse();
       /* for (Tournament t : tournaments
             ) {
            // updating nb of participants
            t.setNumberOfParticipants(t.getTeams().size());
        }*/
        return tournaments;
    }


    public void addNewTournament(Tournament tournament){
        Optional<Tournament> tournamentOptional = tournamentRepository.findTournamentByName(tournament.getName());
        if(tournamentOptional.isPresent()){
            throw
                    new IllegalStateException("Name taken");
        }
        int x = tournament.getNumberOfParticipants();
        while (x % 2 == 0){
            x /= 2;
        }
        if(x != 1){
            throw  new IllegalStateException("Nb of participants must be k = 2^n");
        }
        tournamentRepository.save(tournament);
    }



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
            Optional<Tournament> nameOptional = tournamentRepository.findTournamentByName(name);
            if(nameOptional.isPresent() && nameOptional.get().getId() != tournamentId){
                throw
                        new IllegalStateException("Name taken");
            }
            tournamentOptional.get().setName(name);
        }

        if(is_private != null && !Objects.equals(tournamentOptional.get().isPrivate(), is_private)){
            tournamentOptional.get().setPrivate(is_private);
        }

        if(nb_participants != null && !Objects.equals(tournamentOptional.get().getNumberOfParticipants(), nb_participants)){
            int x = nb_participants;
            while (x % 2 == 0){
                x /= 2;
            }
            if(x != 1){
                throw  new IllegalStateException("Nb of participants must be k = 2^n");
            }
            tournamentOptional.get().setNumberOfParticipants(nb_participants);
        }
    }

    public void startTournament(Long tournamentId) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        if(!tournamentOptional.isPresent()){
            throw new IllegalStateException("Tournament " + tournamentId + " doesnt exist");
        }
        if(tournamentOptional.get().getTeams().size() < tournamentOptional.get().getNumberOfParticipants()){
            throw new IllegalStateException("Tournament is not full");

        }
        tournamentOptional.get().setStarted(true);

    }
}
