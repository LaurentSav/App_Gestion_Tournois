package com.projet.Tournament;

import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping(path = "tournament")
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public List<Tournament> getTournaments(){
        return tournamentService.getTournaments();
    }

    @PostMapping
    public void registerNewTournament(@RequestBody Tournament tournament){
        tournamentService.addNewTournament(tournament);
    }

    @DeleteMapping("/{tournamentId}")
    public void deleteTournamentById(@PathVariable("tournamentId") Long tournamentId){
        tournamentService.deleteTournament(tournamentId);
    }

    @PutMapping("/{tournamentId}")
    public void updateTournament(
            @PathVariable("tournamentId") Long tournamentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean is_private,
            @RequestParam(required = false) Integer nb_participants){
        tournamentService.updateTournament(tournamentId, name, is_private, nb_participants);
    }

}
