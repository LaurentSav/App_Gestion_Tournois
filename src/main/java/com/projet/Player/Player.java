package com.projet.Player;

import com.projet.Team.Team;
import com.projet.Users.User;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity(name = "Player")
@Table(name = "player")
public class Player {


    @Id
    @SequenceGenerator(
            name = "player_sequence",
            sequenceName = "player_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_sequence"

    )
    @Column(name = "id", updatable = false)
    private Long  id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;


    public Player() {
    }

    public Player(String name, User user, Team team) {
        this.name = name;
        this.user = user;
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player(Long id, String name, Team team) {
        this.id = id;
        this.name = name;
        this.team = team;
    }

    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }




    public Player(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
