package com.projet.Tournament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository
        extends JpaRepository<Tournament, Long> {

    @Query("SELECT t FROM  Tournament t WHERE t.name = :name")
    Optional<Tournament> findTournamentByName(String name);

    @Query("SELECT t FROM Tournament t WHERE t.name LIKE CONCAT('%',:name,'%')")
    List<Tournament> findTournamentByWord(@Param("name") String word);

    @Query("SELECT t FROM Tournament t WHERE t.owner.id = :id")
    Page<Tournament> findTournamentbyUserId(@Param("id") Long id, Pageable pageable);

    List<Tournament> findAllByIsPrivateIsFalse();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Tournament t SET t.name = :name WHERE t.id = :id")
    public void updateName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Tournament t SET t.game = :game WHERE t.id = :id")
    public void updateGame(@Param("id") Long id, @Param("game") String game);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Tournament t SET t.isPrivate = :isPrivate WHERE t.id = :id")
    public void updateprivate(@Param("id") Long id, @Param("isPrivate") Boolean isPrivate);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Tournament t SET t.NumberOfParticipants = :NumberOfParticipants WHERE t.id = :id")
    public void updatenbParti(@Param("id") Long id, @Param("NumberOfParticipants") Integer NumberOfParticipants);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Tournament t SET t.description = :description WHERE t.id = :id")
    public void updateDescription(@Param("id") Long id, @Param("description") String description);


}
