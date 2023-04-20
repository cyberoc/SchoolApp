package org.ocm.rest.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.ocm.dto.auth.AppUserDTO;
import org.ocm.rest.service.AuthService;
import org.ocm.security.CustomUserDetailsService;
import org.ocm.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final AuthService authService;

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody String body, HttpServletResponse response) {
        log.info("sign in...");
        try {
            JSONObject userpass = new JSONObject(body);
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userpass.getString("username"), userpass.getString("password")));
            final UserDetails user = customUserDetailsService.loadUserByUsername(userpass.getString("username"));
            if (user != null) {
                String jwt = jwtUtils.generateToken(user);
                Cookie cookie = new Cookie("jwt", jwt);
                cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
                cookie.setHttpOnly(true);
                cookie.setPath("/"); // Global
                response.addCookie(cookie);
                return ResponseEntity.ok(jwt);
            }
            return ResponseEntity.status(400).body("Error authenticating");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody AppUserDTO newUser, BindingResult bindingResult) {
        try {
            if (newUser.getPassword() == null || newUser.getPassword().isBlank()){
                throw new Exception("PASSWORD is required");
            }
            return ok(authService.register(newUser));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity("Cannot register user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity("User data is not valid due to constrains: " + bindingResult.getAllErrors().stream().map(err-> err.getDefaultMessage()).collect(Collectors.toList()).toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/profile")
    public ResponseEntity profile(Principal principal){
        log.info("Retrieving profile for logged in user {}", principal.getName());
        return ok(authService.profile(principal.getName()));
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@Valid @RequestBody AppUserDTO user, BindingResult bindingResult, Principal principal) {
        try {
            user.setUsername(principal.getName());
            return ok(authService.update(user));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity("Cannot update user data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity("User data is not valid due to constrains: " + bindingResult.getAllErrors().stream().map(err-> err.getDefaultMessage()).collect(Collectors.toList()).toString(), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
