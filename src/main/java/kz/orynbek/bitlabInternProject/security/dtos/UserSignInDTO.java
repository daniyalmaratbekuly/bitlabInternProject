package kz.orynbek.bitlabInternProject.security.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSignInDTO {
    private String username;
    private String password;
}
