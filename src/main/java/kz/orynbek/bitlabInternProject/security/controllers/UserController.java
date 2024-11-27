package kz.orynbek.bitlabInternProject.controllers;

import kz.orynbek.bitlabInternProject.DTO.UserCreateDTO;
import kz.orynbek.bitlabInternProject.DTO.UserSignInDTO;
import kz.orynbek.bitlabInternProject.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor

public class UserController {
    private final KeycloakService keycloakService;
    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ADMIN')")
        public UserRepresentation createUser(@RequestBody UserCreateDTO userCreateDTO){
        return keycloakService.createUser(userCreateDTO);
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@RequestBody UserSignInDTO userSignInDTO){
        return keycloakService.signIn(userSignInDTO);
    }
}
