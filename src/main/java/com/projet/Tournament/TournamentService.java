package com.projet.Tournament;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    public List<Tournament> getTournaments() {
        return List.of( new Tournament(
                1 ,
                "LeagueOf",
                true));
    }
}
