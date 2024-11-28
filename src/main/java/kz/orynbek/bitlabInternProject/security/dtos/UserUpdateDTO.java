package kz.orynbek.bitlabInternProject.security.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
}
