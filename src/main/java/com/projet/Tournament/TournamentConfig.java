package com.projet.Tournament;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.rmi.StubNotFoundException;
import java.util.List;

@Configuration
public class TournamentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            TournamentRepository repository){
        return args -> {
             /*Tournament LeagueOfLegends = new Tournament(
                     "League of Legends",
                     false
             );
             Tournament RocketLeague = new Tournament(
                    "Rocket League",
                     false
             );
             *//*repository.saveAll(List.of(LeagueOfLegends, RocketLeague));*/

        };
    }

}
