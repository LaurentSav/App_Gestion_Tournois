package com.projet.Games;

import com.projet.Team.Team;
import com.projet.Tournament.Tournament;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Game")
@Table(name = "game")
public class Game {

    @Id
    @SequenceGenerator(
            name = "game_sequence",
            sequenceName = "game_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "game_sequence"

    )
    @Column(name = "id" , updatable = false)
    private Long id;

    @Column(name = "date")
    private Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bteam_id", referencedColumnName = "id")
    private Team blueteam;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rteam_id", referencedColumnName = "id")
    private Team redteam;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private Team winner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;


    public Game() {
    }

    public Game(Team blueteam, Team redteam) {
        this.blueteam = blueteam;
        this.redteam = redteam;
    }

    public Game(Date date, Team blueteam, Team redteam) {
        this.date = date;
        this.blueteam = blueteam;
        this.redteam = redteam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Team getBlueteam() {
        return blueteam;
    }

    public void setBlueteam(Team blueteam) {
        this.blueteam = blueteam;
    }

    public Team getRedteam() {
        return redteam;
    }

    public void setRedteam(Team redteam) {
        this.redteam = redteam;
    }

    public Team getWinner() {
        return winner;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
