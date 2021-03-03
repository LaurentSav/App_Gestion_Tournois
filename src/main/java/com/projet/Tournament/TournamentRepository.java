package com.projet.Tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository
        extends JpaRepository<Tournament, Long> {

    @Query("SELECT t FROM  Tournament t WHERE t.name = :name")
    Optional<Tournament> findTournamentByName(String name);

    @Query("SELECT t FROM Tournament t WHERE t.name LIKE CONCAT('%',:name,'%')")
    List<Tournament> findTournamentByWord(@Param("name") String word);

    @Query(value = "SELECT t FROM Tournament t WHERE t.owner.id = :id")
    Page<Tournament> findTournamentbyUserId(@Param("id") Long id, Pageable pageable);

    List<Tournament> findAllByIsPrivateIsFalse();



}
