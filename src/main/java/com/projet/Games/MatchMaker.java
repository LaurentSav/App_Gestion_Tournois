package com.projet.Games;


import com.projet.Team.Team;
import com.projet.Tournament.Tournament;

import java.util.List;

public class MatchMaker {


    public static List<Game> classicBracket(Tournament t){
        List<Team> tournamentTeams = t.getTeams();
        List<Game> tournamentGames = List.of();
        int nbTeams = tournamentTeams.size();
        for (int i = 0; i < nbTeams/2; i++) {
            tournamentGames.add(new Game(tournamentTeams.get(i),
                    tournamentTeams.get(i + nbTeams / 2)));
        }

        return tournamentGames;
    }




}
