package az.growlab.easypet.service;

import az.growlab.easypet.domain.PetWalker;
import az.growlab.easypet.dto.PetWalkerSignUpRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface PetWalkerService {
    void signUp(PetWalkerSignUpRequest signUpRequest);
    List<PetWalker> findAll();
    List<PetWalker> findByLocation(String location);
    List<PetWalker> findByNameAndSurname(String name, String surname);
    void createPetWalkerAnnouncement(PetWalker petWalker, String suitableDate) throws ParseException;

    PetWalker findByEmail(String email);
}
