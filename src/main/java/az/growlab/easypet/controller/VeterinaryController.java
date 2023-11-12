package az.growlab.easypet.controller;

import az.growlab.easypet.domain.Veterinary;
import az.growlab.easypet.dto.JobInfoRequestDto;
import az.growlab.easypet.service.VeterinarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/veterinary")
public class VeterinaryController {
    private final VeterinarService veterinarService;

    @GetMapping
    public List<Veterinary> getAll(){
        return veterinarService.findAll();
    }
    @GetMapping("/location")
    public List<Veterinary> getAllByLocation(@RequestParam String location){
        return veterinarService.findByLocation(location);
    }
    @GetMapping("/name-surname")
    public List<Veterinary> getAllByLocation(@RequestParam String name,@RequestParam String surname){
        return veterinarService.findByNameAndSurname(name,surname);
    }
    @PostMapping("/job-info")
    public void addJobInfo(@RequestBody JobInfoRequestDto jobInfoRequestDto, Principal principal){
        Veterinary veterinary =  veterinarService.findByEmail(principal.getName());
        veterinarService.addJobInfo(veterinary,jobInfoRequestDto);
    }
}
