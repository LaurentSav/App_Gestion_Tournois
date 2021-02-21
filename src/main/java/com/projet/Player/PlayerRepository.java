package com.projet.Player;

import com.projet.Team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {



    List<Player> findAll();

    List<Player> findPlayersByTeam(Team team );



    Optional<Player> findPlayerById(Long player_id);

    List<Player> findPlayersByTeam(Long team_id);

    List<Player> findPlayersByName(String name);
}
