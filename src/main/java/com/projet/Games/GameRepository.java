package com.projet.Games;

import com.projet.Tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {


    @Query("SELECT g FROM Game g WHERE g.tournament.id = :id")
    List<Game> findAllByTournament(@Param("id") Long tournament_id);

    @Query("SELECT g FROM Game g WHERE g.id = :id")
    Game findbyGameId(@Param("id") Long id);

}
