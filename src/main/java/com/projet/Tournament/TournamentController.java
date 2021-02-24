package com.projet.Tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "tournament")
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Tournament> getTournaments(){
        return tournamentService.getTournaments();
    }

    @PostMapping
    public void registerNewTournament(@RequestBody Tournament tournament){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        } else {
            String username = principal.toString();
        }
        tournamentService.addNewTournament(tournament);
    }

    @DeleteMapping( "/{tournamentId}")
    public void deleteTournamentById(@PathVariable("tournamentId") Long tournamentId){
        tournamentService.deleteTournament(tournamentId);
    }

    @PutMapping({"onlyForAuthenticated", "/{tournamentId}"} )
    public void updateTournament(
            @PathVariable("tournamentId") Long tournamentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean is_private,
            @RequestParam(required = false) Integer nb_participants){
        tournamentService.updateTournament(tournamentId, name, is_private, nb_participants);
    }

}
