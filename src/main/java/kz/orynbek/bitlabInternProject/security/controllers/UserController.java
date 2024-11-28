package kz.orynbek.bitlabInternProject.security.controllers;

import kz.orynbek.bitlabInternProject.security.dtos.*;
import kz.orynbek.bitlabInternProject.security.services.KeycloakService;
import kz.orynbek.bitlabInternProject.security.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor

public class UserController {
    private final KeycloakService keycloakService;

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ADMIN')")
    public UserRepresentation createUser(@RequestBody UserCreateDTO userCreateDTO) {
        return keycloakService.createUser(userCreateDTO);
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@RequestBody UserSignInDTO userSignInDTO) {
        return keycloakService.signIn(userSignInDTO);
    }


    @PostMapping(value = "/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        String currentUserName = UserUtils.getCurrentUsername();
        if (currentUserName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Couldn't Identify User");
        }
            try {
                keycloakService.changePassword(currentUserName, userChangePasswordDTO.getNewPassword());
                return ResponseEntity.ok("Password changed successfully");
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while changing password");
            }
        }


    @PostMapping("/refresh-token")
    public String refreshToken(@RequestBody UserRefreshTokenDTO refreshToken) {
        return keycloakService.refreshToken(refreshToken);
    }

    @PostMapping ("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUserData(@RequestBody UserUpdateDTO userUpdateDTO) {
        String currentUserName = UserUtils.getCurrentUsername();
        if (currentUserName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Couldn't Identify User");
        }
        try {
            keycloakService.updateUserData(currentUserName, userUpdateDTO);
            return ResponseEntity.ok("User data updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    }