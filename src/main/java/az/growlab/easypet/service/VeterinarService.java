package az.growlab.easypet.service;

import az.growlab.easypet.domain.PetWalker;
import az.growlab.easypet.domain.Veterinary;
import az.growlab.easypet.dto.JobInfoRequestDto;
import az.growlab.easypet.dto.VeterinarSignUpRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VeterinarService {
    void signUp(VeterinarSignUpRequest signUpRequest);
    List<Veterinary> findAll();
    List<Veterinary> findByLocation(String location);
    List<Veterinary> findByNameAndSurname(String name, String surname);
    Veterinary findByEmail(String email);
    void addJobInfo(Veterinary veterinary, JobInfoRequestDto jobInfoRequestDto);
}
