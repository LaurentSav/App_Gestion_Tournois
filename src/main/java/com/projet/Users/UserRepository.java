package com.projet.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.email = :email, u.firstName = :firstName, u.lastName = :lastName WHERE u.id = :id")
    public void updateUser(@Param("id") long id, @Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName);

}
