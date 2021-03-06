package com.projet.Games;

import com.projet.Team.Team;
import com.projet.Team.TeamRepository;
import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameService {



    private GameRepository gameRepository;

    private TournamentRepository tournamentRepository;

    @Autowired
    private TeamRepository teamRepository;

    public GameService(GameRepository gameRepository, TournamentRepository tournamentRepository) {
        this.gameRepository = gameRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Game getGame(Long id){
        return gameRepository.findbyGameId(id);
    }

    public void createGame(Long tournament_id, Game g){
        Optional<Tournament> t = tournamentRepository.findById(tournament_id);
        Team tb = teamRepository.findTeam(g.getBlueteam().getId());
        Team tr = teamRepository.findTeam(g.getRedteam().getId());
        if(!t.isPresent()){
            throw new IllegalStateException("Bracket creation failed because Tournament does not exist");
        }
        g.setBlueteam(tb);
        g.setRedteam(tr);
        g.setTournament(t.get());
        gameRepository.save(g);
    }

    public void deleteGame(Long gameid){
        Optional<Game> g = gameRepository.findById(gameid);
        boolean exists = g.isPresent();
        if (!exists){
            throw
                    new IllegalStateException("Match" + g +" does not exist");
        }
        gameRepository.delete(g.get());
    }

    public List<Game> getGames(Long tournament_id){
        return gameRepository.findAllByTournament(tournament_id);
    }




    public void createBracket(Long tournament_id) {
        Optional<Tournament> t = tournamentRepository.findById(tournament_id);
        if(!t.isPresent()){
            throw new IllegalStateException("Bracket creation failed because Tournament does not exist");
        }
        Tournament tournament = t.get();
        List<Game> bracket = MatchMaker.classicBracket(tournament);
        gameRepository.saveAll(bracket);

    }

    @Transactional
    public void updateGame(Long game_id, Long rteam_id, Long bteam_id, Date date) {
        Optional<Game> g = gameRepository.findById(game_id);
        if(!g.isPresent()){
            throw new IllegalStateException("Game " + game_id + " does not exist");
        }

        if(rteam_id!= null &&
                !Objects.equals(g.get().getRedteam().getId(), rteam_id) &&
                !Objects.equals(g.get().getBlueteam().getId(), rteam_id)){
            Optional<Team> t = teamRepository.findById(rteam_id);
            if (!t.isPresent()){
                throw new IllegalStateException("red team " + rteam_id +" doesnt exist" );
            }
            if(!Objects.equals(g.get().getTournament(), t.get().getTournament())){
                throw new IllegalStateException("red team " + rteam_id +" doesnt isnt in this tournament" );
            }
            System.err.println(t.get().getName());
            g.get().setRedteam(t.get());
        }

        if(bteam_id!= null &&
                !Objects.equals(g.get().getBlueteam().getId(), bteam_id) &&
                !Objects.equals(g.get().getRedteam().getId(), bteam_id)){
            Optional<Team> t = teamRepository.findById(bteam_id);
            if (!t.isPresent()){
                throw new IllegalStateException("blue team " + bteam_id +" doesnt exist" );
            }
            if(!Objects.equals(g.get().getTournament(), t.get().getTournament())){
                throw new IllegalStateException("blue team " + rteam_id +" doesnt isnt in this tournament" );
            }
            System.err.println(t.get().getName());
            g.get().setBlueteam(t.get());
        }

       /*if(winner_id!= null){
            if(!Objects.equals(g.get().getBlueteam().getId(), winner_id) &&
                            !Objects.equals(g.get().getRedteam().getId(), winner_id)){
                throw new IllegalStateException("winner team " + winner_id +" is not participating in this game" );
            }
            Optional<Team> t = teamRepository.findById(winner_id);
            g.get().setWinner(t.get());
        }*/

        if(date!=null && !Objects.equals(date, g.get().getDate())){
            Tournament t = g.get().getTournament();
            if(t.getStartdate() == null|| t.getStartdate().after(date)){
                t.setStartdate(date);
            }
            g.get().setDate(date);

        }


    }


    @Transactional
    public void setWinnerBlue(Long game_id) {
        Optional<Game> g = gameRepository.findById(game_id);
        if(!g.isPresent()){
            throw new IllegalStateException("Game " + game_id + " does not exist");
        }
        g.get().setWinner(g.get().getBlueteam());
    }

    @Transactional
    public void setWinnerRed(Long game_id) {
        Optional<Game> g = gameRepository.findById(game_id);
        if(!g.isPresent()){
            throw new IllegalStateException("Game " + game_id + " does not exist");
        }
        g.get().setWinner(g.get().getRedteam());
    }
}
