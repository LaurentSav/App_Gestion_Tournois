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
                     "Starcraft",
                     false, 16

             );
             Tournament RocketLeague = new Tournament(
                    "Fifa",
                     false, 15
             );
             repository.saveAll(List.of(LeagueOfLegends, RocketLeague));*/

        };
    }

}
