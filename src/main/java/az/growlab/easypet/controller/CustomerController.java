package az.growlab.easypet.controller;

import az.growlab.easypet.domain.Customer;
import az.growlab.easypet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping("announcement")
    public void createPetWalkingRequest(@RequestParam String suitableDate, Principal principal) throws ParseException {
        Customer customer = customerService.findByEmail(principal.getName());
        customerService.createPetWalkingRequest(customer, suitableDate);
    }
}
