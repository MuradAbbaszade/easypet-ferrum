package az.growlab.easypet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VeterinarSignUpRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String location;
}
