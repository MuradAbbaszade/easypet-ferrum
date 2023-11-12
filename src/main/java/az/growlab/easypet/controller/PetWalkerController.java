package az.growlab.easypet.controller;

import az.growlab.easypet.domain.PetWalker;
import az.growlab.easypet.service.PetWalkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pet-walker")
public class PetWalkerController {
    private final PetWalkerService petWalkerService;

    @GetMapping
    public List<PetWalker> getAll(){
        return petWalkerService.findAll();
    }
    @GetMapping("/location")
    public List<PetWalker> getAllByLocation(@RequestParam String location){
        return petWalkerService.findByLocation(location);
    }
    @GetMapping("/name-surname")
    public List<PetWalker> getAllByLocation(@RequestParam String name,@RequestParam String surname){
        return petWalkerService.findByNameAndSurname(name,surname);
    }
    @PostMapping("/announcement")
    public void createAnnouncement(@RequestParam String suitableDate, Principal principal) throws ParseException {
        PetWalker petWalker = petWalkerService.findByEmail(principal.getName());
        petWalkerService.createPetWalkerAnnouncement(petWalker, suitableDate);
    }
}
