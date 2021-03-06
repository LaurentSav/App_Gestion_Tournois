package com.projet.Tournament;


import com.projet.Games.Game;
import com.projet.Team.Team;
import com.projet.Users.User;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Tournament")
@Table(name = "tournament")
public class Tournament {
    @Id
    @SequenceGenerator(
            name = "tournament_sequence",
            sequenceName = "tournament_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "tournament_sequence"
    )
    /*---ATTRIBUTS---*/
    @Column(name = "id", updatable = false)
    private Long  id;

    @Column(name = "name",nullable = false)
    private String name;


    @Column(name = "private", nullable = false)
    private Boolean isPrivate = false;

    @Column(name = "game", nullable = false)
    private String game;

    @Column(name = "nb_participants")
    private Integer NumberOfParticipants; // nombre de team qui participe
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "start_date", nullable = true)
    private Date startdate;
    @Column(name = "started")
    private Boolean started = false;
    @Column(name = "team_size")
    private Integer teamSize = 1;

    @OneToMany(targetEntity = Team.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private List<Team> teams;
    @OneToMany(targetEntity = Game.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private List<Game> Games ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;



    public Tournament(String name, Boolean isPrivate, Integer numberOfParticipants, String description, User owner) {
        this.name = name;
        this.isPrivate = isPrivate;
        NumberOfParticipants = numberOfParticipants;
        this.description = description;
        this.owner = owner;
    }

    public Tournament(String name, Boolean isPrivate, Integer numberOfParticipants, String description, User owner ,Integer teamsize) {
        this.name = name;
        this.isPrivate = isPrivate;
        NumberOfParticipants = numberOfParticipants;
        this.description = description;
        this.teamSize = teamsize;
        this.owner = owner;
    }

    public Tournament(String name, Boolean isPrivate, Integer numberOfParticipants, String description, Integer teamsize) {
        this.name = name;
        this.isPrivate = isPrivate;
        NumberOfParticipants = numberOfParticipants;
        this.description = description;
        this.teamSize = teamsize;
    }

    public Tournament(String name, Boolean isPrivate, Integer numberOfParticipants) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.NumberOfParticipants = numberOfParticipants;
        this.started = false;
    }

    public Tournament (Long id, String name,
                       Boolean isPrivate, Integer numberOfParticipants) {
        this.name = name;

        this.id = id;
        this.isPrivate = isPrivate;
        this.NumberOfParticipants = numberOfParticipants;
        this.started = false;
    }


    // main constructor we're using
    public Tournament(String name, Boolean isPrivate, Integer numberOfParticipants, User owner) {
        this.name = name;
        this.isPrivate = isPrivate;
        NumberOfParticipants = numberOfParticipants;
        this.owner = owner;
        this.started = false;
    }

    public Integer getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }


    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public Integer getNumberOfParticipants() {
        return NumberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        NumberOfParticipants = numberOfParticipants;
    }

    public Tournament() {
    }

    public Tournament(String name, String game, Boolean isPrivate, Integer numberOfParticipants) {
        this.name = name;
        this.game = game;
        this.isPrivate = isPrivate;
        this.NumberOfParticipants = numberOfParticipants;
        this.description = "";
    }

    public Tournament(String name, String game, Boolean isPrivate, Integer numberOfParticipants, String description) {
        this.name = name;
        this.game = game;
        this.isPrivate = isPrivate;
        this.NumberOfParticipants = numberOfParticipants;
        this.description = description;
    }

    public Tournament(String name, String game, Boolean isPrivate, Integer numberOfParticipants, String description, User user) {
        this.name = name;
        this.game = game;
        this.isPrivate = isPrivate;
        this.NumberOfParticipants = numberOfParticipants;
        this.description = description;
        this.owner = user;
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

    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public List<Game> getGames() {
        return Games;
    }

    public void setGames(List<Game> games) {
        Games = games;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", isPrivate=" + isPrivate +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
