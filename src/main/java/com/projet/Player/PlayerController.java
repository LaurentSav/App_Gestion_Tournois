package com.projet.Player;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "players")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/team={t_id}")
    public List<Player> getTeamPlayers(@PathVariable Long t_id){
        return playerService.getTeamPlayers(t_id);
    }

    @GetMapping
    public List<Player> getPlayers(){
        return playerService.getPlayers();
    }
    @PostMapping
    public void registerNewTeam(@RequestBody Player player){
        playerService.addNewPlayer(player);
    }

    @DeleteMapping(value = "/{playerId}")
    public void deletePlayer(@PathVariable("playerId") Long playerId){
        playerService.deletePlayer(playerId);
    }

    @PutMapping("/{player_Id}")
    public void updateTeam(
            @PathVariable("player_Id") Long player_Id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long team_id){
         playerService.updatePlayer(player_Id, name, team_id);
    }





}
