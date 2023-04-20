package org.ocm.rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ocm.dto.auth.AppUserDTO;
import org.ocm.rest.repository.AuthRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    private final PasswordEncoder pwdEnc;

    public String register(AppUserDTO newUser){
        Optional<AppUserDTO> appUser = authRepository.findById(newUser.getUsername());
        if (appUser.isPresent()){
            log.info("User is already registered: {}", newUser.getUsername());
            return "User is already registered";
        } else {
            try {
                String encPwd = pwdEnc.encode(newUser.getPassword());
                newUser.setPassword(encPwd);
                authRepository.save(newUser);
                log.info("User created: {}", newUser.getUsername());
                return String.format("User %s registered succesfully", newUser.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Error while persisting user/profile data for: {}", newUser.getUsername());
                return String.format("Error while persisting user/profile data for %s", newUser.getUsername());
            }
        }
    }

    public String profile(String username){
        ObjectMapper mapper = new ObjectMapper();
        Optional<AppUserDTO> user = authRepository.findById(username);
        try {
            if (user.isPresent()){
                return mapper.writeValueAsString(user.get());
            } else {
                log.info("Profile doesn't exist for {}", username);
                return "User not found !";
            }
        } catch (JsonProcessingException jpe) {
            return "Cannot format user profile: " + jpe.getMessage();
        }
    }

    public String update(AppUserDTO updUser){
        Optional<AppUserDTO> user = authRepository.findById(updUser.getUsername());
        if (user.isPresent()){
            AppUserDTO currUser = user.get();
            currUser.setFirstName(updUser.getFirstName());
            currUser.setLastName(updUser.getLastName());
            currUser.setEmail(updUser.getEmail());
            authRepository.save(currUser);
            log.info("User {} updated", updUser.getUsername());
            return "User succesfully updated: " + updUser.getUsername();
        } else {
            log.info("Cannot update inexistent user {}", updUser.getUsername());
            return "User not found !";
        }
    }
}
