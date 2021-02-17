package com.projet.Tournament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TournamentRepository
        extends JpaRepository<Tournament, Long> {

    @Query("SELECT t FROM  Tournament t WHERE t.name = :name")
    Optional<Tournament> findTournamentByName(String name);

   /* @Query("SELECT r FROM Tournament r WHERE r.id = :id ")
    Tournament getTournamentById(@Param("id") long id);*/




}
