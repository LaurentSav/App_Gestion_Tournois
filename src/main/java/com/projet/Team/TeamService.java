package com.projet.Team;

import java.util.List;

public class TeamService {
    public List<Team> getTeams() {
        return List.of(new Team("Test", 5));
    }
}
