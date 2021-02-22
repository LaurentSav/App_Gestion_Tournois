package com.projet.Team;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    //Columns
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
    @Column(name = "nb_members")
    Integer nbMembers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "captain_id", referencedColumnName = "id")
    private Player captain;

    @OneToMany(targetEntity = Player.class ,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @JsonIgnore
    private List<Player> Players;

    //Constructors
    public Team() {
    }
    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
        nbMembers = 0;
        if(getPlayers() != null){
            this.nbMembers = getPlayers().size();
        }


    }
    public Team(String name) {
        this.name = name;
        if(getPlayers() == null) {

            this.nbMembers = 0;
        }else{
            this.nbMembers = getPlayers().size();
        }

    }
    public Team(String name, Tournament tournament) {
        this.name = name;
        this.tournament = tournament;
        nbMembers = 0;
        if(getPlayers() != null){
            this.nbMembers = getPlayers().size();
        }

    }

    // Getters and setters

    public Player getCaptain() {
        return captain;
    }
    public void setCaptain(Player captain) {
        this.captain = captain;
    }


    public List<Player> getPlayers() {
        return Players;
    }

    public void setPlayers(List<Player> players) {
        Players = players;
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
        this.nbMembers = 0;
        if(Players != null){
            this.nbMembers = Players.size();

        }
        return nbMembers;
    }


    public void addMember(Player player){
        getNbMembers(); //updates nbMembers
        nbMembers += 1;
        Players.add(player);
    }

    public void removePlayer(Player player){
        this.nbMembers -= 1;
        Players.remove(player);
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
