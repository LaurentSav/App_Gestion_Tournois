package com.projet.Player;

import com.projet.Team.Team;
import com.projet.Team.TeamRepository;
import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import com.projet.Users.User;
import com.projet.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class PlayerService {



    @Autowired
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private UserRepository userRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayer(Long id){return playerRepository.findPlayerById(id);}

    public List<Player> getTeamPlayers(Long t_id) {
        Optional<Team> team = teamRepository.findById(t_id);
        if(!team.isPresent()){
            throw new IllegalStateException("Team " + t_id + " does not exist");
        }
        return playerRepository.findPlayersByTeam(team.get());
    }

    public void deletePlayer(Long playerId, Long tid) {
        Optional<Player> player = playerRepository.findById(playerId);
        if(!player.isPresent()){
            throw new IllegalStateException("player "+ playerId +" does not exist");
        }
        player.get().setUser(null);
        Optional<Team> team = teamRepository.findById(tid);
        team.get().setCaptain(null);
        playerRepository.delete(player.get());
    }

    @Transactional
    public void updatePlayer(Long player_id, String name, Long team_id) {
        Optional<Player> playerOptional = playerRepository.findPlayerById(player_id);
        if(!playerOptional.isPresent()){
            throw new IllegalStateException("Player " + player_id + " does not exist");
        }

        if(name != null && name.length() > 0 && !Objects.equals(playerOptional.get().getName(), name)){
            playerOptional.get().setName(name);
        }

        if(team_id != null && !Objects.equals(playerOptional.get().getTeam().getId(), team_id )){
            playerOptional.get().setTeam(teamRepository.findById(team_id).get());
        }


        Long t_id = playerOptional.get().getTeam().getId();
        Optional<Team> pTeam = teamRepository.findById(t_id);
        if(!pTeam.isPresent()){
            deletePlayer(player_id,team_id);
            throw  new IllegalStateException("Player's team doesnt exist anymore");
        }

    }

    public void addNewPlayer(Player player, Long tid) {
        List<Player> optionalPlayer =
                playerRepository.findPlayersByName(player.getName());
        boolean exists = !optionalPlayer.isEmpty();
        Team team = teamRepository.findTeam(tid);
        if(team != null){
            player.setTeam(team);
        }
        if (exists){
            for (Player p : optionalPlayer
                 ) {

                if(Objects.equals(player.getTeam().getTournament(), p.getTeam().getTournament() )){
                    throw
                            new IllegalStateException("Team " + player.getName() +" already exists in this tournament!");
                }

            }
        }
        Optional<Team> t = teamRepository.findById(player.getTeam().getId());
        if(t.isPresent() && t.get().getCaptain() == null){
            t.get().setCaptain(player);
            t.get().addMember(player);
        }

        playerRepository.save(player);
    }

    public void addNewPlayer(Player player, Principal principal, Long tid) {
        List<Player> optionalPlayer =
                playerRepository.findPlayersByName(player.getName());
        Team team = teamRepository.findTeam(tid);

        if(team != null){
            player.setTeam(team);
        }

        boolean exists = !optionalPlayer.isEmpty();
        if (exists){
            for (Player p : optionalPlayer
            ) {

                if(Objects.equals(player.getTeam().getTournament(), p.getTeam().getTournament() )){
                    throw
                            new IllegalStateException("Team " + player.getName() +" already exists in this tournament!");
                }

            }
        }

        Optional<Team> t = teamRepository.findById(player.getTeam().getId());
        if(t.isPresent() && t.get().getCaptain() == null){
            t.get().setCaptain(player);
            t.get().addMember(player);
        }

        User user = userRepository.findByEmail(principal.getName());

        if(user != null){
            player.setUser(user);
        }

        playerRepository.save(player);
    }
}
