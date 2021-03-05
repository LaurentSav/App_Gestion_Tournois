package com.projet.Team;

import com.projet.Tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TeamRepository
        extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t WHERE t.tournament.id = :id")
    List<Team> findTeamsBytournament(@Param("id") Long id);

    @Query("SELECT t FROM Team t WHERE t.id = :id")
    Team findTeam(@Param("id") Long id);

    Optional<Team> findTeamByNameAndTournament(String name, Tournament tournament);

}
