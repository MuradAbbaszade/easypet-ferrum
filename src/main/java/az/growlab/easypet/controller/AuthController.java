package az.growlab.easypet.controller;

import az.growlab.easypet.dto.CustomerSignUpRequest;
import az.growlab.easypet.dto.PetWalkerSignUpRequest;
import az.growlab.easypet.dto.SignInRequest;
import az.growlab.easypet.dto.VeterinarSignUpRequest;
import az.growlab.easypet.service.CustomerService;
import az.growlab.easypet.service.PetWalkerService;
import az.growlab.easypet.service.VeterinarService;
import az.growlab.easypet.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final VeterinarService veterinarService;
    private final PetWalkerService petWalkerService;
    private final CustomerService customerService;

    @PostMapping("/customer/sign-up")
    public String signUpCustomer(@RequestBody CustomerSignUpRequest customerSignUpRequest) {
        customerService.signUp(customerSignUpRequest);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerSignUpRequest.getEmail(),
                        customerSignUpRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return token;
    }

    @PostMapping("/veterinary/sign-up")
    public String signUpVeterinary(@RequestBody VeterinarSignUpRequest veterinarSignUpRequest) {
        veterinarService.signUp(veterinarSignUpRequest);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        veterinarSignUpRequest.getEmail(),
                        veterinarSignUpRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return token;
    }

    @PostMapping("/petwalker/sign-up")
    public String signUpPetWalker(@RequestBody PetWalkerSignUpRequest petWalkerSignUpRequest) {
        petWalkerService.signUp(petWalkerSignUpRequest);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        petWalkerSignUpRequest.getEmail(),
                        petWalkerSignUpRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return token;
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInRequest signInRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);
        return token;
    }
}
