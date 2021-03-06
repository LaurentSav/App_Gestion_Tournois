package com.projet.Tournament;

import com.projet.Games.Game;
import com.projet.Games.GameService;
import com.projet.Player.Player;
import com.projet.Player.PlayerService;
import com.projet.Team.Team;
import com.projet.Team.TeamService;
import com.projet.Users.CustomUserDetails;
import com.projet.Users.CustomUserDetailsService;
import com.projet.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TournamentService {

    @Autowired
    private final TournamentRepository tournamentRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private GameService gameService;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAll();
       /* for (Tournament t : tournaments
             ) {
            // updating nb of participants
            t.setNumberOfParticipants(t.getTeams().size());
        }*/
        return tournaments;
    }

    public Page<Tournament> getTournaments(int pageNum){
        int pagesize = 8;
        Pageable pageable = PageRequest.of(pageNum -1, pagesize);
        return tournamentRepository.findAll(pageable);
    }

    public Page<Tournament> getUserTournaments(int pageNum,CustomUserDetails user){
        int pagesize = 20;
        Pageable pageable = PageRequest.of(pageNum -1, pagesize);
        return tournamentRepository.findTournamentbyUserId(user.getId(), pageable);
    }



    public void addNewTournament(Tournament tournament, Principal principal){
        Optional<Tournament> tournamentOptional = tournamentRepository.findTournamentByName(tournament.getName());
        User user = customUserDetailsService.loadUser(principal.getName());
        tournament.setOwner(user);

        if(tournamentOptional.isPresent()){
            throw
                    new IllegalStateException("Name taken");
        }
        int x = tournament.getNumberOfParticipants();
        int y = tournament.getTeamSize();
        while (x % 2 == 0){
            x /= 2;
        }
        if(x != 1){
            throw  new IllegalStateException("Nb of participants must be k = 2^n");
        }
        tournamentRepository.save(tournament);
    }

    public Tournament getTournament(Long id){
        Tournament tournament = tournamentRepository.findById(id).get();
        return tournament;
    }

    public List<Tournament> findbyWord(String word){
        List<Tournament> listTournois = tournamentRepository.findTournamentByWord(word);
        return listTournois;
    }


    public void deleteTournament(long tournamentId) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        boolean exists = tournamentOptional.isPresent();
        if (!exists) {
            throw
                    new IllegalStateException("Tournament " + tournamentId + " does not exist");
        }
        List<Game> games = gameService.getGames(tournamentId);
        if(!games.isEmpty()){
            for(Game g : games){
                gameService.deleteGame(g.getId());
            }
        }
        List<Team> teams = teamService.getTeams(tournamentId);
        if (!teams.isEmpty()){
            for (Team t : teams) {
                List<Player> players = playerService.getTeamPlayers(t.getId());
                if(!players.isEmpty()){
                    for (Player p : players) {
                        playerService.deletePlayer(p.getId(), t.getId());
                    }
                }
                teamService.deleteTeam(t.getId());
            }
        }

        tournamentRepository.delete(tournamentOptional.get());
    }

    @Transactional
    public void updateTournament(Long tournamentId, String name, String game, Boolean is_private, Integer nb_participants, String description, Date date) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        boolean exists = tournamentOptional.isPresent();
        System.out.println(exists);
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
            tournamentRepository.updateName(tournamentId, name);
        }

        if(is_private != null && !Objects.equals(tournamentOptional.get().isPrivate(), is_private)){
            tournamentRepository.updateprivate(tournamentId,is_private);
        }

        if(nb_participants != null && !Objects.equals(tournamentOptional.get().getNumberOfParticipants(), nb_participants)){
            tournamentRepository.updatenbParti(tournamentId, nb_participants);
        }

        if(game != null && game.length() > 0 && !Objects.equals(tournamentOptional.get().getGame(), game)){
            tournamentRepository.updateGame(tournamentId, game);
        }

        if(description != null && !Objects.equals(tournamentOptional.get().getDescription(), description)){
            tournamentRepository.updateDescription(tournamentId,description);
        }


        tournamentRepository.updateStartDate(tournamentId, date);


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
        tournamentRepository.updateStarted(tournamentId, true);



    }
}
