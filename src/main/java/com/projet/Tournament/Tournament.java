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
    private int  id;
    private String name;
    private boolean isPrivate;

    public Tournament() {
    }

    public Tournament (int id, String name,
                      boolean isPrivate) {
        this.name = name;
        this.id = id;
        this.isPrivate = isPrivate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Tournament(String name, boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                ", id=" + id +
                "name='" + name + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
