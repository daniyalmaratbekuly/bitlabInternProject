package kz.orynbek.bitlabInternProject.security.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserCreateDTO {
    private String email;
    private boolean emailVerified;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
