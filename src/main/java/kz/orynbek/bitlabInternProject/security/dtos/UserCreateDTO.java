package kz.orynbek.bitlabInternProject.security.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateDTO {
    private String email;
    private boolean emailVerified;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
