package com.projet.Tournament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TournamentRepository
        extends JpaRepository<Tournament, Long> {

    @Query
    Optional<Tournament> findTournamentByName(String name);

}
