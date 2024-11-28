package kz.orynbek.bitlabInternProject.security.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRefreshTokenDTO {
    private String username;
    private String password;
}
