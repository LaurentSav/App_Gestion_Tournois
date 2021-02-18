package com.projet.Tournament;


import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Table

public class Tournament {
    @Id
    @SequenceGenerator(
            name = "tournament_sequence",
            sequenceName = "tournament_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tournament_sequence"

    )
    private Long  id;
    private String name;

    public Integer getNumberOfParticipants() {
        return NumberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        NumberOfParticipants = numberOfParticipants;
    }

    private Boolean isPrivate;
    private Integer NumberOfParticipants;

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
