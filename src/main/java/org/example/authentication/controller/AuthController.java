package org.example.authentication.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.authentication.configuration.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    public AuthController(AuthenticationManager AuthManager, UserDetailsService userDetailsService) {
        this.authManager = AuthManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/token")
    public String generateToken(@RequestParam String username, @RequestParam String password) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        authManager.authenticate(auth);

        UserDetails user = userDetailsService.loadUserByUsername(username);
        return JwtUtil.generateToken(user.getUsername());
    }

    @GetMapping("/user")
    public String userAccess(@RequestHeader("Authorization") String header) {
        String token = header.substring(7); // skip "Bearer "
        String username = JwtUtil.validateToken(token);
        if (username != null)
            return "Hello " + username + "! You accessed a protected endpoint.";
        else
            return "Invalid or expired token!";
    }
}
