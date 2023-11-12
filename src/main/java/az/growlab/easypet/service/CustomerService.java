package az.growlab.easypet.service;

import az.growlab.easypet.domain.Customer;
import az.growlab.easypet.dto.CustomerSignUpRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface CustomerService {
    void signUp(CustomerSignUpRequest signUpRequest);
    Customer findByEmail(String email);
    void createPetWalkingRequest(Customer customer , String suitableDate) throws ParseException;
}
