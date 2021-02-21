package com.projet.Team;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projet.Player.Player;
import com.projet.Tournament.Tournament;

import javax.persistence.*;


import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Team")
@Table(
        name = "team"
)
@JsonIgnoreProperties("players")
public class Team {


    @Id
    @SequenceGenerator(
            name = "team_sequence",
            sequenceName = "team_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "team_sequence"
    )
    @Column(name = "id", updatable = false)
    Long id;

    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "nb_members", nullable = false)
    Integer nbMembers;
    @ManyToOne
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    public List<Player> getPlayers() {
        return Players;
    }

    public void setPlayers(List<Player> players) {
        Players = players;
    }

    @OneToMany(targetEntity = Player.class ,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private List<Player> Players;
    public Team() {
    }
    public Team(Long id, String name, Integer nbMembers) {
        this.id = id;
        this.name = name;
        this.nbMembers = nbMembers;
    }


    public Team(String name, Integer nbMembers) {
        this.name = name;
        this.nbMembers = nbMembers;
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

    public Integer getNbMembers() {
        return nbMembers;
    }

    public void setNbMembers(Integer nbMembers) {
        this.nbMembers = nbMembers;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nbMembers=" + nbMembers +
                ", tournament=" + tournament +
                '}';
    }
}
