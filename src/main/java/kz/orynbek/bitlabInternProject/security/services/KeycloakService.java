package kz.orynbek.bitlabInternProject.security.services;


import kz.orynbek.bitlabInternProject.security.dtos.UserCreateDTO;
import kz.orynbek.bitlabInternProject.security.dtos.UserRefreshTokenDTO;
import kz.orynbek.bitlabInternProject.security.dtos.UserSignInDTO;
import kz.orynbek.bitlabInternProject.security.dtos.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;
    private final RestTemplate restTemplate;
    @Value("${keycloak.url}")
    private String url;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client}")
    private String client;
    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public UserRepresentation createUser(UserCreateDTO user) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setEmail(user.getEmail());
        newUser.setEmailVerified(true);
        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());
        credential.setTemporary(false);
        newUser.setCredentials(List.of(credential));

        Response response = keycloak
                .realm(realm)
                .users()
                .create(newUser);

        if (response.getStatus() != HttpStatus.CREATED.value()) {//201status
            log.error("Error creating user:");
            throw new RuntimeException("Failed to create user");
        }

        List<UserRepresentation> searchUsers = keycloak
                .realm(realm).users().search(user.getUsername());
        return searchUsers.get(0);
    }


    public String signIn(UserSignInDTO userSignInDTO) {
        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", client);
        formData.add("client_secret", clientSecret);
        formData.add("username", userSignInDTO.getUsername());
        formData.add("password", userSignInDTO.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            log.error("Error signing in");
            throw new RuntimeException("Failed to authenticate user");
        }
        return (String) responseBody.get("access_token");
    }

    public void changePassword(String username, String newPassword) {
        List<UserRepresentation> users = keycloak
                .realm(realm)
                .users()
                .search(username);

        if (users.isEmpty()) {
            log.error("User not found to change password");
            throw new RuntimeException("User not found to change password" + username);
        }

        UserRepresentation userRepresentation = users.get(0);


        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(newPassword);
        credentialRepresentation.setTemporary(false);

        keycloak
                .realm(realm)
                .users()
                .get(userRepresentation.getId())
                .resetPassword(credentialRepresentation);

        log.info("Changed password");
    }

    public String refreshToken(UserRefreshTokenDTO userRefreshTokenDTO) {
        String tokenEndpoint = url + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", client);
        formData.add("client_secret", clientSecret);
        formData.add("username", userRefreshTokenDTO.getUsername());
        formData.add("password", userRefreshTokenDTO.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/x-www-form-urlencoded");

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, new HttpEntity<>(formData, headers), Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            log.error("Error refresh Token");
            throw new RuntimeException("Failed to refresh Token");
        }
        return (String) responseBody.get("refresh_token");
    }


    public void updateUserData(String username, UserUpdateDTO userUpdateDTO) {
        List<UserRepresentation> users = keycloak
                .realm(realm)
                .users()
                .search(username);

        if (users.isEmpty()) {
            log.error("User '{}' not found to update data", username);
            throw new RuntimeException("User not found to update data: " + username);
        }

        UserRepresentation userRepresentation = users.get(0);

        // Обновляем только те поля, которые были переданы в запросе
        if (userUpdateDTO.getFirstName() != null) {
            userRepresentation.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null) {
            userRepresentation.setLastName(userUpdateDTO.getLastName());
        }
        if (userUpdateDTO.getEmail() != null) {
            userRepresentation.setEmail(userUpdateDTO.getEmail());
        }

        keycloak
                .realm(realm)
                .users()
                .get(userRepresentation.getId())
                .update(userRepresentation);

        log.info("User data updated successfully for user: {}", username);
    }

}