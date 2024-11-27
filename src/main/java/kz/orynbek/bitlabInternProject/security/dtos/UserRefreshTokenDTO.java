package kz.orynbek.bitlabInternProject.security.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRefreshToken {
    private String refreshToken;
}
