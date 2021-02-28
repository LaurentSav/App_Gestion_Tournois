package com.projet.Games;

import com.projet.Tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {


    List<Game> findAllByTournament(Tournament tournament);
}
