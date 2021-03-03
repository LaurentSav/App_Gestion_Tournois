package com.projet.Users;

import com.projet.Player.Player;
import com.projet.Team.Team;
import com.projet.Tournament.Tournament;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 210)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @OneToMany(targetEntity = Tournament.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Tournament> tournaments;


    @OneToMany(targetEntity =  Player.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private List<Player> playerEntities; //Representation of a user in a tournament

    public User() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }




}
