package az.growlab.easypet.service.impl;

import az.growlab.easypet.domain.*;
import az.growlab.easypet.dto.PetWalkerSignUpRequest;
import az.growlab.easypet.exception.CustomNotFoundException;
import az.growlab.easypet.mail.MailSenderService;
import az.growlab.easypet.repository.*;
import az.growlab.easypet.service.PetWalkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import az.growlab.easypet.enums.UserAuthority;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetWalkerServiceImpl implements PetWalkerService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final PetWalkerRepository petWalkerRepository;
    private final PetWalkingRequestRepository petWalkingRequestRepository;
    private final PetWalkerAnnouncementRepository petWalkerAnnouncementRepository;
    private final MailSenderService mailSenderService;

    @Override
    @Transactional
    public void signUp(PetWalkerSignUpRequest signUpRequest) {
        User user = new User();
        userRepository.findByUsername(signUpRequest.getEmail()).ifPresent(account -> {
            throw new IllegalArgumentException("Email used");
        });
        user.setUsername(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Authority authority = authorityRepository.findByAuthority(UserAuthority.PET_WALKER.toString()).orElse(
                Authority.builder().authority(UserAuthority.PET_WALKER.toString()).build()
        );
        authorityRepository.save(authority);
        user.setAuthorities(Set.of(authority));
        PetWalker petWalker = new PetWalker();
        petWalker.setName(signUpRequest.getName());
        petWalker.setSurname(signUpRequest.getSurname());
        petWalker.setLocation(signUpRequest.getLocation());
        petWalker.setPhoneNumber(signUpRequest.getPhoneNumber());
        petWalker.setUser(user);
        userRepository.save(user);
        petWalkerRepository.save(petWalker);
    }

    @Override
    public List<PetWalker> findAll() {
        return petWalkerRepository.findAll();
    }

    @Override
    public List<PetWalker> findByLocation(String location) {
        return petWalkerRepository.findAllByLocation(location);
    }

    @Override
    public List<PetWalker> findByNameAndSurname(String name,String surname) {
        return petWalkerRepository.findAllByNameAndSurname(name,surname);
    }

    @Override
    @Transactional
    public void createPetWalkerAnnouncement(PetWalker petWalker, String suitableDate) throws ParseException {
        PetWalkerAnnouncement petWalkerAnnouncement =  new PetWalkerAnnouncement();
        petWalkerAnnouncement.setPetWalker(petWalker);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(suitableDate);
        Date date = simpleDateFormat.parse(suitableDate);
        petWalkerAnnouncement.setSuitableDate(date);
        petWalkerAnnouncementRepository.save(petWalkerAnnouncement);
        log.info("Pet walker announcement request created by {}",petWalker.getName());
        List<PetWalkingRequest> petWalkingRequests = petWalkingRequestRepository.findAllBySuitableDate(date);
        sendEmailToCustomers(petWalkingRequests,petWalker,suitableDate);
    }

    @Override
    public PetWalker findByEmail(String email) {
        User user = userRepository.findByUsername(email).orElseThrow(
                ()->new CustomNotFoundException("User not found"));
        return petWalkerRepository.findByUser(user).orElseThrow(()->new CustomNotFoundException("Pet walker not found"));
    }

    public void sendEmailToCustomers(List<PetWalkingRequest> petWalkingRequests, PetWalker petWalker, String date){
        for(PetWalkingRequest petWalkingRequest : petWalkingRequests){
            String message = petWalker.getUser().getUsername()+" looking for pet walking job on " +date;
            String email = petWalkingRequest.getCustomer().getUser().getUsername();
            mailSenderService.sendEmail(email,"NEW PET WALKER!",message);
        }
    }
}
