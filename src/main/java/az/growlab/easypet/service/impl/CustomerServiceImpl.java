package az.growlab.easypet.service.impl;

import az.growlab.easypet.domain.*;
import az.growlab.easypet.dto.CustomerSignUpRequest;
import az.growlab.easypet.enums.UserAuthority;
import az.growlab.easypet.exception.CustomNotFoundException;
import az.growlab.easypet.mail.MailSenderService;
import az.growlab.easypet.repository.*;
import az.growlab.easypet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PetWalkingRequestRepository petWalkingRequestRepository;
    private final PetWalkerAnnouncementRepository petWalkerAnnouncementRepository;
    private final AuthorityRepository authorityRepository;
    private final MailSenderService mailSenderService;
    @Override
    @Transactional
    public void signUp(CustomerSignUpRequest signUpRequest) {
        User user = new User();
        userRepository.findByUsername(signUpRequest.getEmail()).ifPresent(account -> {
            throw new IllegalArgumentException("Email used");
        });
        user.setUsername(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Authority authority = authorityRepository.findByAuthority(UserAuthority.CUSTOMER.toString()).orElse(
                Authority.builder().authority(UserAuthority.CUSTOMER.toString()).build()
        );
        authorityRepository.save(authority);
        user.setAuthorities(Set.of(authority));
        Customer customer = new Customer();
        customer.setName(signUpRequest.getName());
        customer.setSurname(signUpRequest.getSurname());
        customer.setUser(user);
        userRepository.save(user);
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void createPetWalkingRequest(Customer customer, String suitableDate) throws ParseException {
        PetWalkingRequest petWalkingRequest =  new PetWalkingRequest();
        petWalkingRequest.setCustomer(customer);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(suitableDate);
        petWalkingRequest.setSuitableDate(date);
        petWalkingRequestRepository.save(petWalkingRequest);
        log.info("Pet walking request created by {}",customer.getName());
        List<PetWalkerAnnouncement> petWalkerAnnouncementList = petWalkerAnnouncementRepository.findAllBySuitableDate(date);
        sendEmailToPetWalkers(petWalkerAnnouncementList,customer,suitableDate);
    }
    public void sendEmailToPetWalkers(List<PetWalkerAnnouncement> petWalkerAnnouncementList, Customer customer, String date){
        for(PetWalkerAnnouncement petWalkerAnnouncement : petWalkerAnnouncementList){
            String message = customer.getUser().getUsername()+" looking for pet walker in" +date;
            String email = petWalkerAnnouncement.getPetWalker().getUser().getUsername();
            mailSenderService.sendEmail(email,"NEW CUSTOMER!",message);
        }
    }
    @Override
    public Customer findByEmail(String email) {
        User user = userRepository.findByUsername(email).orElseThrow(
                ()->new CustomNotFoundException("User not found"));
        return customerRepository.findByUser(user).orElseThrow(()->new CustomNotFoundException("Customer not found"));
    }

}
