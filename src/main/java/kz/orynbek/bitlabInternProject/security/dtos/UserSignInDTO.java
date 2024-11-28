package kz.orynbek.bitlabInternProject.security.dtos;

import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserSignInDTO {
    private String username;
    private String password;
}
