package az.growlab.easypet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerSignUpRequest {
    private String name;
    private String surname;
    private String email;
    private String location;
    private String phoneNumber;
    private String password;
}
