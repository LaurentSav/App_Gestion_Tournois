package com.projet.Games;

import com.projet.Team.TeamService;
import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(path = "games")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    //Using put method to add games automatically when tournament has started, might not work
    @PutMapping("bracket={tournament_id}")
    public void createTournamentBracket(@PathVariable ("tournament_id") Long tournament_id){
        gameService.createBracket(tournament_id);
    }

    @PutMapping("{game_id}")
    public void updateGame(@PathVariable("game_id") Long game_id,
                           @RequestParam(required = false) Long rteam_id,
                           @RequestParam(required = false) Long bteam_id,
                           @RequestParam("date") Date date){
        gameService.updateGame(game_id, rteam_id, bteam_id, date);
    }

    @PutMapping("/setWinnerRed/{game_id}")
    public void setWinnerRed(@PathVariable("game_id") Long game_id){
        gameService.setWinnerRed(game_id);
    }

    @PutMapping("/setWinnerBlue/{game_id}")
    public void setWinnerBlue(@PathVariable("game_id") Long game_id){
        gameService.setWinnerBlue(game_id);
    }






}
