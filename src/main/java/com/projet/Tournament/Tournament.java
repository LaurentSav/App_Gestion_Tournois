package com.projet.Tournament;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projet.Team.Team;
import com.projet.Users.User;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Tournament")
@Table(
        name = "tournament",
        uniqueConstraints = {
                @UniqueConstraint(name = "tournament_name_unique", columnNames = "name")
        }
)
@JsonIgnoreProperties("teams")
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
    @Column(name = "id", updatable = false)
    private Long  id;
    @Column(
            name = "name",
            nullable = false
    )
    private String name;
    @Column(name = "private", nullable = false)
    private Boolean isPrivate;
    @Column(name = "nb_participants")
    private Integer NumberOfParticipants;
    @OneToMany(targetEntity = Team.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private List<Team> teams;

    public Tournament(String name, Boolean isPrivate, Integer numberOfParticipants, User owner) {
        this.name = name;
        this.isPrivate = isPrivate;
        NumberOfParticipants = numberOfParticipants;
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

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

    public Tournament(String name, Boolean isPrivate, Integer numberOfParticipants) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.NumberOfParticipants = numberOfParticipants;
    }

    public Tournament (Long id, String name,
                       Boolean isPrivate, Integer numberOfParticipants) {
        this.name = name;

        this.id = id;
        this.isPrivate = isPrivate;
        this.NumberOfParticipants = numberOfParticipants;

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

    @Override
    public String toString() {
        return "Tournament{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
