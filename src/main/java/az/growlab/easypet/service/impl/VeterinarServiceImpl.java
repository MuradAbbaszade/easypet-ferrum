package az.growlab.easypet.service.impl;
import az.growlab.easypet.domain.*;
import az.growlab.easypet.dto.JobInfoRequestDto;
import az.growlab.easypet.dto.VeterinarSignUpRequest;
import az.growlab.easypet.exception.CustomNotFoundException;
import az.growlab.easypet.repository.AuthorityRepository;
import az.growlab.easypet.repository.JobInfoRepository;
import az.growlab.easypet.repository.UserRepository;
import az.growlab.easypet.repository.VeterinaryRepository;
import az.growlab.easypet.service.VeterinarService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import az.growlab.easypet.enums.UserAuthority;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VeterinarServiceImpl implements VeterinarService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final VeterinaryRepository veterinaryRepository;
    private final JobInfoRepository jobInfoRepository;


    @Override
    @Transactional
    public void signUp(VeterinarSignUpRequest veterinarSignUpRequest) {
        User user = new User();
        userRepository.findByUsername(veterinarSignUpRequest.getEmail()).ifPresent(account -> {
            throw new IllegalArgumentException("Email used");
        });
        user.setUsername(veterinarSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(veterinarSignUpRequest.getPassword()));
        Authority authority = authorityRepository.findByAuthority(UserAuthority.VETERINARY.toString()).orElse(
                Authority.builder().authority(UserAuthority.VETERINARY.toString()).build()
        );
        authorityRepository.save(authority);
        user.setAuthorities(Set.of(authority));
        Veterinary veterinary = new Veterinary();
        veterinary.setName(veterinarSignUpRequest.getName());
        veterinary.setSurname(veterinarSignUpRequest.getSurname());
        veterinary.setUser(user);
        veterinary.setPhoneNumber(veterinarSignUpRequest.getPhoneNumber());
        veterinary.setLocation(veterinarSignUpRequest.getLocation());
        userRepository.save(user);
        veterinaryRepository.save(veterinary);
    }

    @Override
    public List<Veterinary> findAll() {
        return veterinaryRepository.findAll();
    }

    @Override
    public List<Veterinary> findByLocation(String location) {
        return veterinaryRepository.findAllByLocation(location);
    }

    @Override
    public List<Veterinary> findByNameAndSurname(String name, String surname) {
        return veterinaryRepository.findAllByNameAndSurname(name,surname);
    }

    @Override
    public Veterinary findByEmail(String email) {
        User user = userRepository.findByUsername(email).orElseThrow(
                ()->new CustomNotFoundException("User not found"));
        return veterinaryRepository.findByUser(user).orElseThrow(()->new CustomNotFoundException("Veterinary not found"));
    }

    @Override
    public void addJobInfo(Veterinary veterinary, JobInfoRequestDto jobInfoRequestDto) {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setVeterinary(veterinary);
        jobInfo.setCompany(jobInfoRequestDto.getCompany());
        jobInfo.setDuration(jobInfoRequestDto.getDuration());
        jobInfoRepository.save(jobInfo);
    }
}
